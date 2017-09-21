package gen;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

public class ImgTools 
{
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
	        	Point center = calcContourCenter(contour);
	        	Imgproc.circle(img1, center, 10, new Scalar(255,0,0));
	        }
        }        
        Imgproc.drawContours(img1, contours, -1, new Scalar(0,255,0),5);
        DispImg(img1,title,sz);
	}
	
	public static Point calcContourCenter(MatOfPoint contour)
	{
		Moments m = Imgproc.moments(contour);
		double x = m.get_m10() / m.get_m00();
		double y = m.get_m01() / m.get_m00();
		return new Point(x,y);
		
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
