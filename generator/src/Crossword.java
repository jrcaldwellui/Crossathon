import java.util.ArrayList;

import org.opencv.core.Point;
import org.opencv.core.Size;

public class Crossword
{
	private ArrayList<ArrayList<Character>> crossword = new ArrayList<ArrayList<Character>>();
	private int numRows;
	private int numCols;
	
	//returns an example crossword
	public static Crossword ExampleCrossword()
	{
		ArrayList<Box> testBoxs = new ArrayList<Box>();
		//testBoxs.add(new Box(0,0));
		//testBoxs.add(new Box(1,1));
		//testBoxs.add(new Box(2,2));
		testBoxs.add(new Box(0,2));
		//testBoxs.add(new Box(2,0));
		return new Crossword(testBoxs,3,3);
	}
	
	public Crossword(ArrayList<Box> boxs,int numRows,int numCols)
	{
		this.numCols = numCols;
		this.numRows = numRows;
		for(int col = 0; col < numCols; col++)
		{
			crossword.add(new ArrayList<Character>());
			for(int row = 0; row < numRows; row++)
			{
				crossword.get(col).add('0');
			}
		}
		this.print();
		for(Box box: boxs)
		{
			System.out.println(box.row + "," + box.col);
			this.set((int)box.row, (int)box.col, '*');
		}
		
	}
	

	//Set the character at row,col to value
	public void set(int row, int col,char value)
	{
		crossword.get(col).set(row,value);
	}
	
	//get the char at row,col
	// returns * then there is a white entry box at the given row col
	// returns 0 then there is a black space at the given row col
	public char get(int row, int col)
	{
		return crossword.get(numRows-1-row).get(numCols-1-col);
	}
	
	//prints crossword to console
	public void print()
	{
		//System.out.println(numRows + " , " + numCols);
		for(int row = 0; row < numRows; row++)
		{
			for(int col = 0; col < numCols; col++)
			{ 
				System.out.print( this.get(row,col) );
			}
			System.out.print("\n");
		}
	}
	
}