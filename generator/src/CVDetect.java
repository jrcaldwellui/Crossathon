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
	        	if( ImgTools.isContourQuadrilateral(contour) )
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
	            	return (int)Math.signum(area1 - area2);
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
	        */
	        ImgTools.DispContourOnImg(img,crossword_contours,true,"final contours",new Size(800,600));
	       
	        //Create list of data about crossword boxes
	        ArrayList<Box> boxs = new ArrayList<Box>();
	        for (MatOfPoint contour : crossword_contours)
	        {
	        	boxs.add(new Box(ImgTools.calcContourCenter(contour)));
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
	public static int findSignificantChange( ArrayList<Double> nums)
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

	
	public static int calcRows(ArrayList<Box> boxs)
	{
		Collections.sort(boxs,new Comparator<Box>() {
            @Override
            public int compare(Box box2, Box box1)
            {
            	return (int) Math.signum(box2.loc.x - box1.loc.x);
            }
         });
        
        ArrayList<Double> diffs = new ArrayList<Double>();
        for (int i = 0; i < boxs.size(); i++) 
        {
        	Box currentBox = boxs.get(i);
        	Box nextBox = boxs.get(i+1);		
        			
        	currentBox.diffx = Math.abs( nextBox.loc.x - currentBox.loc.x ); 
        	diffs.add(currentBox.diffx);
        }
        
        Collections.sort(diffs);        
        int colChange = findSignificantChange(diffs);
        
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
            	return  (int) Math.signum(box2.loc.y - box1.loc.y);
            }
         });
        for (int i = 1; i < boxs.size(); i++) 
        {
        	boxs.get(i-1).diffy =  Math.abs(boxs.get(i).loc.y - boxs.get(i-1).loc.y); 
        	diffs.add( Math.abs( boxs.get(i).loc.y - boxs.get(i-1).loc.y ));
        }
        Collections.sort(diffs);
        int rowChange = findSignificantChange(diffs);
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

	    
}
