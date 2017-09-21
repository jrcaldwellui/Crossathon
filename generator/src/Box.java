package gen;
import org.opencv.core.Point;

/*
 * Box represents a white box on a crossword. This class
 * is use map the box's pixel location on an image to the
 * correct row and column in the digital crossword.
 */
public class Box 
{
	public Point loc;
	public int row;
	public int col;
	public double diffx = 0.0;
	public double diffy = 0.0;

	/*
	 * pixelLocation - should be 
	 */
	public Box( Point pixelLocation)
	{
		this.loc = pixelLocation;
	}
	
	public Box(int row, int col)
	{
		this.row = row;
		this.col = col;
	}
}