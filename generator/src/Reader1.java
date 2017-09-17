import com.asprise.ocr.Ocr;

import java.io.File;

public class Reader1{

    private Word1[] across;
    private Word1[] down;
    private int numWords;

    public static String main(){

    Ocr.setUp();
    Ocr ocr = new Ocr();

    //can change to speed_slow irl
    ocr.startEngine("eng", Ocr.SPEED_SLOW);

    //replace the following 3 files with pictures the user takes
    //assume that the user has three pictures: Grid, Across clues, Down clues
    File grid = new File("res/crossword.png");
    File across = new File("res/across.png");
    File down = new File("res/down.png");

    //recognize grid
    // might try using recognize method with different param eg input file or image
    //String s = ocr.recognize(new File[] {new File("grid.png")}, Ocr.RECOGNIZE_TYPE_TEXT,
      //      Ocr.OUTPUT_FORMAT_PLAINTEXT);

        //System.out.println("Grid: " + s);


    //recognize across
    //String acrossClues = ocr.recognize(new File[] {new File("across.png")}, Ocr.RECOGNIZE_TYPE_TEXT,
    //        Ocr.OUTPUT_FORMAT_PLAINTEXT);

        //System.out.println("Across: " + acrossClues);

    //recognize down
    String downClues = ocr.recognize(new File[] {new File("down.png")}, Ocr.RECOGNIZE_TYPE_TEXT,
            Ocr.OUTPUT_FORMAT_PLAINTEXT, Ocr.PROP_PAGE_TYPE_SCATTERED);

       System.out.println("Down: " + downClues);
     System.out.println("***********************");

    ocr.stopEngine();
    return downClues;

    // group hints into Across and Down
    // format is:  X letters  YY letters X letters
    // where X, YY are numbers  and letters are.... letters

    //process Across clues




    }
}