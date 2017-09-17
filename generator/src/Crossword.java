import java.util.ArrayList;

import org.opencv.core.Point;
import org.opencv.core.Size;

public class Crossword
{
	private ArrayList<ArrayList<Character>> crossword = new ArrayList<ArrayList<Character>>();
	private ArrayList<ArrayList<Integer>> numbers = new ArrayList<ArrayList<Integer>>();
	private int numRows;
	private int numCols;
	
	
	//
	public void calculateBoxNumbers()
	{
		
		if(numbers.size() == 0)
		{
			for(int col = 0; col < numCols; col++)
			{
				numbers.add(new ArrayList<Integer>());
				for(int row = 0; row < numRows; row++)
				{
					numbers.get(col).add(-1);
				}
			}
		}
		
		int count = 1;
		for(int row = 0; row < numRows; row++)
		{
			for(int col = 0; col < numCols; col++)
			{ 
				char current = this.get(row,col);
				if(current == '1' )
				{
					if(row == 0 || col == 0)
					{
						numbers.get(col).set(row, count);
						count++;
					}else if(this.get(row - 1,col) == '0' || this.get(row,col - 1) == '0')
					{
						numbers.get(col).set(row, count);
						count++;
					}
				}
				
			}
		}
	}
	
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
		for(Box box: boxs)
		{
			this.set((int)box.row, (int)box.col, '1');
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
	//prints crossword to console
	public void printNumbers()
	{
		System.out.println("size" + numbers.get(0).get(0));
		//System.out.println(numRows + " , " + numCols);
		for(int row = 0; row < numRows; row++)
		{
			for(int col = 0; col < numCols; col++)
			{ 
				System.out.print( numbers.get(col).get(row)  );
			}
			System.out.print("\n");
		}
	}
	
	private class gridPoint
	{
		public int row = -1;
		public int col = -1;
		public gridPoint(int row,int col)
		{
			this.row = row;
			this.col = col;
		}
	}
	
	private gridPoint findNumInCrossword(int num)
	{
		for(int col = 0; col < numCols; col++)
		{
			for(int row = 0; row < numRows; row++)
			{
				if(numbers.get(col).get(row) == num)
				{
					return new gridPoint(row,col);
				}
			}
		}
		return null;
	}
	
	public int getWordLengthAcross(int num)
	{
		gridPoint loc = findNumInCrossword(num);
		if(loc != null)
		{
			int length = 1;
			for(int col = loc.col + 1; col < numCols; col++)
			{
				if(this.get(loc.row,col) == '0')
				{
					return length;
				}
				length++;
			}
			return length;
		}
		return 0;
		

	}
	
	public int getWordLengthDown(int num)
	{
		gridPoint loc = findNumInCrossword(num);
		if(loc != null)
		{
			int length = 1;
			for(int row = loc.row + 1; row < numRows; row++)
			{
				if(this.get(row,loc.col) == '0')
				{
					return length;
				}
				length++;
			}
		}
		return 0;
	}
	
	
}