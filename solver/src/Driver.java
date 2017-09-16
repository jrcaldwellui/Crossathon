import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Driver {
    public static void main (String args[]) throws IOException {
        //File from https://github.com/donohoe/nyt-crossword/blob/master/clues.txt
        URL path = Driver.class.getResource("allClues.txt");
        FileReader file =  new FileReader(path.getFile());
        BufferedReader buffer = new BufferedReader(file);
       // BufferedWriter writer = new BufferedWriter(new FileWriter("allClues.txt"));

        String tempLine;
        String clue = "";
        String answer = "";
        int tempVal;
        boolean foundAnswer=false;
        boolean foundClue = false;
        HashMap<Integer,Clue> clues = new HashMap<>();
        int tempHashVal;
        Clue tempClue;
        boolean blank = false;

        //allClues.txt
        while ((tempLine = buffer.readLine())!=null)
        {
            foundAnswer=false;
            foundClue=false;
            answer="";
            clue="";
           // tempLine = buffer.readLine();
            for(int j=0; j<tempLine.length(); j++)
            {
                tempVal = (int)tempLine.charAt(j);
                if(tempVal==40) //(
                {
                    foundClue=true;
                }
                else if(tempVal==41) //)
                {
                    foundClue=false;
                }
                else if(foundClue)
                {
                    clue=clue+tempLine.charAt(j);
                }

                if(tempVal==91) //[
                {
                    foundAnswer=true;
                }
                else if(tempVal==93) //]
                {
                    foundAnswer=false;
                }
                else if(foundAnswer)
                {
                    answer=answer+tempLine.charAt(j);
                }

            }
            clue=clue.toLowerCase();
            clue=clue.trim();
            answer=answer.toLowerCase();
            answer=answer.trim();
            //System.out.println(clue);
            //System.out.println(answer);
             //adds clue and answer to hash
            tempClue = new Clue(clue);
            tempClue.addAnswer(answer);
            clues.put(clue.hashCode(),tempClue);

        }
       // writer.close();
        Scanner input = new Scanner(System.in);
        String tempStr = "";
        while(!tempStr.equals("-1")) {
            System.out.println("Enter the clue:");
            tempStr = input.nextLine();
            tempStr = tempStr.trim();
            tempStr = tempStr.toLowerCase();
            //System.out.println(tempStr);
            ArrayList<String> output = FindAnswer.getAnswer(clues, tempStr);
            if (output != null) {
                for (int i = 0; i < output.size(); i++) {
                    System.out.println(output.get(i));
                }
            } else {
                System.out.println("Invalid clue");
            }
        }
        /*
        //for crossClues1.txt
        while ((tempLine = buffer.readLine())!=null) {
            foundAnswer = false;
            blank = false;
            answer = "";
            clue = "";
            //tempLine = buffer.readLine();
            // System.out.println(tempLine);
            for (int j = 0; j < tempLine.length(); j++) {
                tempVal = (int) tempLine.charAt(j);
                if (tempVal <= 90 && tempVal >= 65 && j != 0) {
                    tempVal = (int) tempLine.charAt(j - 1);
                    //System.out.print(" " + tempVal);
                    if ((tempVal <= 90 && tempVal >= 65) || tempVal == 9) {
                        foundAnswer = true;
                        answer = answer + tempLine.charAt(j);
                    } else {
                        clue = clue + tempLine.charAt(j);
                    }
                } else if (!foundAnswer) {
                    tempVal = (int) tempLine.charAt(j);
                    if (tempVal == 95 && !blank) {
                        blank = true;
                        clue = clue + "_";
                    } else if (tempVal != 95) {
                        clue = clue + tempLine.charAt(j);
                    }
                }
            }
            clue = clue.toLowerCase();
            clue = clue.trim();
            answer = answer.toLowerCase();
            writer.write("(" + clue + ") ");
            writer.write("[" + answer + "]");
            writer.newLine();
        }
        */
            //tempHashVal=clue.hashCode();

            /* adds clue and answer to hash
            tempClue = new Clue(clue);
            tempClue.addAnswer(answer);
            clues.put(clue.hashCode(),tempClue);
            */
           // System.out.println(clue);
            //System.out.println(answer);
            //System.out.println(buffer.readLine());


      //  while (buffer.readLine()!=null)
        //{

        //}
    }
}
