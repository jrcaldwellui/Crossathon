/* Sept16-17, 2017 UIowa Big Data Hackathon*/

import com.asprise.ocr.Ocr;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ReadHints{

    //contains number method
    public static boolean containsNumber(String string){
        if(string.contains("1")){return true;}
        if(string.contains("2")){return true;}
        if(string.contains("3")){return true;}
        if(string.contains("4")){return true;}
        if(string.contains("5")){return true;}
        if(string.contains("6")){return true;}
        if(string.contains("7")){return true;}
        if(string.contains("8")){return true;}
        else{return false;}
    }

    //is a char a number
    public static boolean isNumber(char a){
        if(a =='1'){return true;}
        if(a =='2'){return true;}
        if(a =='3'){return true;}
        if(a =='4'){return true;}
        if(a =='5'){return true;}
        if(a =='6'){return true;}
        if(a =='7'){return true;}
        if(a =='8'){return true;}
        else{return false;}
    }

    public static void main(String[] args) throws FileNotFoundException {

        // Word[] across;
        // Word[] down;
        int numberOfTotalWords;

        String previousLine;
        String clues = null; //number + clue

        //isAcross bool is the string we are processing is from across or down groups
        boolean isAcross = false;
        boolean isDown = false;

        String currentClue;
        int lineCount=0;

        Ocr.setUp();
        Ocr ocr = new Ocr();

        //can change to speed_slow irl
        ocr.startEngine("eng", Ocr.SPEED_SLOW);

        //replace the following 3 files with pictures the user takes
        //assume that the user has three pictures: Grid, Across clues, Down clues
        File grid = new File("crossword.png");
        File across = new File("across.png");
        File down = new File("down.png");

        //recognize grid
        // might try using recognize method with different param eg input file or image
        //String s = ocr.recognize(new File[] {new File("grid.png")}, Ocr.RECOGNIZE_TYPE_TEXT,
        //      Ocr.OUTPUT_FORMAT_PLAINTEXT);

        //System.out.println("Grid: " + s);

        //recognize across
        String acrossClues = ocr.recognize(new File[]{new File("across.png")}, Ocr.RECOGNIZE_TYPE_TEXT,
                Ocr.OUTPUT_FORMAT_PLAINTEXT);

        //System.out.print("Across: " + acrossClues);

        //recognize down
        String downClues = ocr.recognize(new File[]{new File("down.png")}, Ocr.RECOGNIZE_TYPE_TEXT,
                Ocr.OUTPUT_FORMAT_PLAINTEXT, Ocr.PROP_PAGE_TYPE_SINGLE_BLOCK);

        //System.out.println("Down: " + downClues);

        ocr.stopEngine();

        // group hints into Across and Down
        // format is:  X letters  YY letters X letters
        // where X, YY are numbers  and letters are.... letters

        Scanner scanner = new Scanner(acrossClues);
        PrintWriter writer = new PrintWriter("string.txt");

        while (scanner.hasNext()) {
            String line = scanner.nextLine();

            //check if we spot ACROSS or DOWN labels --all caps version is most common on
            // google images, can check for all lower and first capital too -Do Later
            if (line.contains("ACROSS")) { isAcross = true;}
            else if (line.contains("DOWN")) { isDown = true;}

            System.out.println(line);

            // FINISH check and replace * with x, u, 9, 0
            if(line.contains("*")){
                if(line.contains("q")){
                    if(line.indexOf("q")-line.indexOf("*")==1){ line.replace("*","u");}
                }
            }
            char[] lineCharArray = line.toCharArray();

            //tracking previous&upcoming words by numbers to deal with free trial * problems 0,9,u,x
            //scheme:    a, b, current, d, e
            char cminus2;
            char cminus1;
            char c;
            char cplus1;
            char cplus2;

            char[] restofline = new char[30];
            //build up clue line
            if(containsNumber(line)){
                for(int x=0; x<lineCharArray.length; x++){
                    char temp = lineCharArray[x];
                    c = temp;
                    if(x>0){ cminus1 = lineCharArray[x-1];
                        if(isNumber(c)&&isNumber(cminus1)){
                            for(int y=0; y<(line.length()-x); y++) {
                                restofline[y] = c;
                            }
                        }
                    }
                    if(isNumber(c)&&((x+1)<line.length())&&isNumber(lineCharArray[x+1])){

                    }
                }
            }

            if((lineCount<1)&& containsNumber(line)){
                for(int i=0; i<line.length(); i++){

                }
            }
            String s = restofline.toString();

            //System.out.println(s);
            String stringArray[] = line.split(" ");

            //System.out.print(stringArray);

            previousLine=line;
            lineCount++;
        }


    }

}

