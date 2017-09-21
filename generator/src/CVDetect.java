package gen;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

//import org.opencv.core.Core.line;
import org.opencv.core.*;

import org.opencv.videoio.VideoCapture;   // VideoCapture
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import reader.Reader;


public class CVDetect {
	    public static Crossword genCrossword(String imgPath, int sizeWindow) {
	    	System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

	    	Mat greyImg = new Mat();
	        Mat blurImg = new Mat();
	        Mat threshImg = new Mat();
	        Mat heir = new Mat();  
	        Mat hughOut = new Mat();  
	        
	        
	        Mat img = Imgcodecs.imread(imgPath);
	        Imgproc.cvtColor(img,greyImg,Imgproc.COLOR_BGR2GRAY);
	        Imgproc.GaussianBlur(greyImg, blurImg, new Size(sizeWindow,sizeWindow),0);
	        Imgproc.adaptiveThreshold(blurImg, threshImg, 255, Imgproc.ADAPTIVE_THRESH_GAUSSIAN_C, Imgproc.THRESH_BINARY, 135, 10);
	        
	        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();         
	        Imgproc.findContours(threshImg, contours, heir, Imgproc.RETR_LIST , Imgproc.CHAIN_APPROX_SIMPLE );
	        
	        
	        //Only keep contours that are approximately quadrilaterals
	        List<MatOfPoint> rect_contours = new ArrayList<MatOfPoint>();
	        for (MatOfPoint contour : contours) 
	        {
	        	if( isContourQuadrilateral(contour) )
	        	{
	        		rect_contours.add(contour);
	        	}
	        }
	        
	        //sort contours by area
	        Collections.sort(rect_contours,new Comparator<MatOfPoint>() {
	            @Override
	            public int compare(MatOfPoint contour2, MatOfPoint contour1)
	            {
	            	double area1 = Imgproc.contourArea(contour1);
	            	double area2 = Imgproc.contourArea(contour2);
	            	return (int)(Math.signum(area1 - area2) * Math.ceil(Math.abs(area1 - area2)));
	            }
	         });
	        	       
        	        
	        //remove contours with very large and very small areas 
	        int middle = rect_contours.size()/2; 
	        double medianArea = Imgproc.contourArea(rect_contours.get(middle));
	        double upperBound = medianArea + medianArea * 0.3; 
	        double lowerBound = medianArea - medianArea * 0.3; 
	        List<MatOfPoint> crossword_contours = new ArrayList<MatOfPoint>();
	        for (MatOfPoint contour : contours)
	        {
	        	double area = Imgproc.contourArea(contour);
	        	if(area >= lowerBound && area <= upperBound)
	        	{
	        		crossword_contours.add(contour);
	        	}
	        }
	        
	        //Debug img processing
	       /* DispImg(blurImg,"blur",new Size(1000,750));
	        DispContourOnImg(img,contours,true,"original contours",new Size(1000,750));
	        DispContourOnImg(img,rect_contours,true,"quad contours",new Size(1000,750));
	        */DispContourOnImg(img,crossword_contours,true,"final contours",new Size(800,600));
	       
	        //Create list of data about crossword boxes
	        ArrayList<Box> boxs = new ArrayList<Box>();
	        for (MatOfPoint contour : crossword_contours)
	        {
	        	boxs.add(new Box(calcCenter(contour)));
	        }
	        int numRows = calcRows(boxs);
	        int numCols = calcCols(boxs);	
	        
	        Crossword myCrossword = new Crossword(boxs,numRows,numCols);
	        myCrossword.calculateBoxNumbers();
	        return myCrossword;
		        
	}
	
	    
	/*
	 * 
	 */
	public static int findSignificantXChange( ArrayList<Double> nums)
	{
		int sum = 0;
		for(double num: nums)
		{
			sum += num;
		}
		double mean = sum / nums.size();
		
		for(int i = 0; i < nums.size(); i++)
		{
			if( nums.get(i) > mean)
			{
				return i;
			}
		}
  
        return -1;
	}


	    
	//based on http://www.pyimagesearch.com/2016/02/08/opencv-shape-detection/
	/*
	 * Simplifies contour into smaller number of vertices(on corners of shape)
	 * if a contour has 4 vertices it is considered a quadrilateral. 
	 * 
	 * returns true if contour is classified as quadrilateral
	 */
	public static boolean isContourQuadrilateral (MatOfPoint contour){
		MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
		double arcLen = Imgproc.arcLength(contour2f, true);
		MatOfPoint2f approx = new MatOfPoint2f();
		Imgproc.approxPolyDP(contour2f,approx,0.04 * arcLen,true);
		if(approx.rows() == 4)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static int calcRows(ArrayList<Box> boxs)
	{
		Collections.sort(boxs,new Comparator<Box>() {
            @Override
            public int compare(Box box2, Box box1)
            {
            	return -1 * (int)(Math.signum(box1.loc.x - box2.loc.x) * Math.ceil(Math.abs(box1.loc.x - box2.loc.x)));
            }
         });
        
     
       
        ArrayList<Double> diffs = new ArrayList<Double>();
        for (int i = 1; i < boxs.size(); i++) 
        {
        	boxs.get(i-1).diffx = boxs.get(i).loc.x - boxs.get(i-1).loc.x; 
        	diffs.add(Math.abs( boxs.get(i).loc.x - boxs.get(i-1).loc.x) );
        }
        Collections.sort(diffs);
        double sum = 0;
		for( Double dif: diffs)
		{
			sum += dif;
		} 
        
        int colChange = findSignificantXChange(diffs);
        
        double rowWidth = Math.abs( diffs.get(colChange) ) * 0.95;
        ArrayList<Integer> locationOf = new ArrayList<Integer>();
        int row = 0;
        
        for (Box box : boxs) 
        {
        	box.col = row;
        	if(Math.abs(box.diffx) > rowWidth)
        	{
        		
        		row++;
        	}
        }
        return row + 1;
	}
	
	public static int calcCols(ArrayList<Box> boxs)
	{
		ArrayList<Double> diffs = new ArrayList<Double>();
        //Determine cols for each box in puzzle
        Collections.sort(boxs,new Comparator<Box>() {
            @Override
            public int compare(Box box2, Box box1)
            {
            	return  -1*(int)(Math.signum(box1.loc.y - box2.loc.y) * Math.ceil(Math.abs(box1.loc.y - box2.loc.y)));
            }
         });
        for (int i = 1; i < boxs.size(); i++) 
        {
        	boxs.get(i-1).diffy =  Math.abs(boxs.get(i).loc.y - boxs.get(i-1).loc.y); 
        	diffs.add( Math.abs( boxs.get(i).loc.y - boxs.get(i-1).loc.y ));
        }
        Collections.sort(diffs);
        int rowChange = findSignificantXChange(diffs);
        double colWidth = Math.abs( diffs.get(rowChange) ) * 0.95;
        int col = 0;
        for (Box box : boxs) 
        {
        	box.row = col;
        	if(Math.abs(box.diffy) > colWidth)
        	{
        		col++;
        	}
        }
        return col + 1;
	}
	    
	public static Point calcCenter(MatOfPoint contour){
		Moments m = Imgproc.moments(contour);
		double x = m.get_m10() / m.get_m00();
		double y = m.get_m01() / m.get_m00();
		return new Point(x,y);
		
	}
	
	public static double Mean( Mat m)
	{
        double sum = 0;
        for(int i = 0; i < m.height(); i++)
        {
        	for(int j = 0; j < m.width(); j++)
        	{
        		sum += m.get(i,j)[0];
        	}
        }
        return sum / ( m.width() * m.height());
	}
	
	//Displays OpenCV mat img on screen title is window label
	public static void DispImg(Mat img,String title){
        JFrame jframe = new JFrame(title);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel vidPanel = new JLabel();
        jframe.setContentPane(vidPanel);
        jframe.setSize( img.cols(),img.rows());
        jframe.setVisible(true);
        ImageIcon disp_image = new ImageIcon(Mat2BufferedImage(img));
        vidPanel.setIcon(disp_image);
        vidPanel.repaint();
	}	
	
	//Displays OpenCV mat img on screen title is window label
	public static void DispImg(Mat img,String title,Size sz){
        JFrame jframe = new JFrame(title);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel vidPanel = new JLabel();
        jframe.setContentPane(vidPanel);
        Mat resizedImg = new Mat();
        Imgproc.resize(img, resizedImg, sz);
        jframe.setSize( resizedImg.cols(),resizedImg.rows());
        jframe.setVisible(true);
        ImageIcon disp_image = new ImageIcon(Mat2BufferedImage(resizedImg));
        vidPanel.setIcon(disp_image);
        vidPanel.repaint();
	}
	
	public static void DispContourOnImg(Mat img,List<MatOfPoint> contours,boolean dispCenterPoints, String title,Size sz)
	{
        Mat img1 = img.clone();
        
        if(dispCenterPoints)
        {
	        for (MatOfPoint contour : contours) 
	        {
	        	Point center = calcCenter(contour);
	        	Imgproc.circle(img1, center, 10, new Scalar(255,0,0));
	        }
        }
        
        Imgproc.drawContours(img1, contours, -1, new Scalar(0,255,0),5);
        DispImg(img1,title,sz);
	}
	 
	//Converts the OpenCV matrix into format that can be displayed in java swing gui
	//source: https://stackoverflow.com/questions/17401852/open-video-file-with-opencv-java#19289392
    public static BufferedImage Mat2BufferedImage(Mat m) {
        //Method converts a Mat to a Buffered Image
        int type = BufferedImage.TYPE_BYTE_GRAY;
         if ( m.channels() > 1 ) {
             type = BufferedImage.TYPE_3BYTE_BGR;
         }
         int bufferSize = m.channels()*m.cols()*m.rows();
         byte [] b = new byte[bufferSize];
         m.get(0,0,b); // get all the pixels
         BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
         final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
         System.arraycopy(b, 0, targetPixels, 0, b.length);  
         return image;
        }
	    
}
