package com.example.nicklitterio.crosswordnew;

import android.content.Context;

import java.io.*;
import java.net.URL;
import java.util.*;

public class Driver {
    public static void main () throws IOException {
        //File from https://github.com/donohoe/nyt-crossword/blob/master/clues.txt
        //Dict from https://raw.githubusercontent.com/adambom/dictionary/master/dictionary.txt
        //Thes from https://justenglish.me/2014/04/18/synonyms-for-the-96-most-commonly-used-words-in-english/
        //mobyThes from http://www.gutenberg.org/files/3202/
        //Dictionary from https://raw.githubusercontent.com/sujithps/Dictionary/master/Oxford%20English%20Dictionary.txt
        //Driver.mContext=mContext;
        //InputStream path =null;
        //path=mContext.getAssets().open("allClues.txt");
        //path.read()
      //  FileInputStream file = new FileInputStream("allClues.txt");
        //URL path = Driver.class.getResource("allClues.txt");
        FileReader file =  new FileReader("allClues.txt");
        BufferedReader buffer = new BufferedReader(file);

        String tempLine;
        String clue;
        String answer;
        int tempVal;
        boolean foundAnswer;
        boolean foundClue;
        int tempHashVal;
        Clue tempClue;

        //allClues.txt : stored as (clue) [answer]
        while ((tempLine = buffer.readLine())!=null)
        {
            foundAnswer=false;
            foundClue=false;
            answer="";
            clue="";
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

            //Vary blank for variations
            if(clue.contains("_"))
            {
                tempClue = new Clue(clue);
                tempClue.addAnswer(answer);
                if (SolvePuzzle.clues.containsKey(clue.hashCode()))
                {
                    SolvePuzzle.clues.get(clue.hashCode()).addAnswer(answer);
                }
                else {
                    SolvePuzzle.clues.put(clue.hashCode(), tempClue);
                }
                clue = clue.replace("_","__");
                tempClue = new Clue(clue);
                tempClue.addAnswer(answer);
                if (SolvePuzzle.clues.containsKey(clue.hashCode()))
                {
                    SolvePuzzle.clues.get(clue.hashCode()).addAnswer(answer);
                }
                else {
                    SolvePuzzle.clues.put(clue.hashCode(), tempClue);
                }
                clue = clue.replace("__","___");
                tempClue = new Clue(clue);
                tempClue.addAnswer(answer);
                if (SolvePuzzle.clues.containsKey(clue.hashCode()))
                {
                    SolvePuzzle.clues.get(clue.hashCode()).addAnswer(answer);
                }
                else {
                    SolvePuzzle.clues.put(clue.hashCode(), tempClue);
                }
                clue = clue.replace("___","___ ");
                tempClue = new Clue(clue);
                tempClue.addAnswer(answer);
                if (SolvePuzzle.clues.containsKey(clue.hashCode()))
                {
                    SolvePuzzle.clues.get(clue.hashCode()).addAnswer(answer);
                }
                else {
                    SolvePuzzle.clues.put(clue.hashCode(), tempClue);
                }
                clue = clue.replace("___ ","__ ");
                tempClue = new Clue(clue);
                tempClue.addAnswer(answer);
                if (SolvePuzzle.clues.containsKey(clue.hashCode()))
                {
                    SolvePuzzle.clues.get(clue.hashCode()).addAnswer(answer);
                }
                else {
                    SolvePuzzle.clues.put(clue.hashCode(), tempClue);
                }
                clue = clue.replace("__ ","_ ");
                tempClue = new Clue(clue);
                tempClue.addAnswer(answer);
                if (SolvePuzzle.clues.containsKey(clue.hashCode()))
                {
                    SolvePuzzle.clues.get(clue.hashCode()).addAnswer(answer);
                }
                else {
                    SolvePuzzle.clues.put(clue.hashCode(), tempClue);
                }
            }
             //adds clue and answer to hash
            tempClue = new Clue(clue);
            tempClue.addAnswer(answer);
            if (SolvePuzzle.clues.containsKey(clue.hashCode()))
            {
                SolvePuzzle.clues.get(clue.hashCode()).addAnswer(answer);
            }
            else {
                SolvePuzzle.clues.put(clue.hashCode(), tempClue);
            }
            /* If want to flip
            if (!clue.contains(" ")) {
                tempClue = new Clue(answer);
                tempClue.addAnswer(clue);
                if (SolvePuzzle.clues.containsKey(answer.hashCode())) {
                    SolvePuzzle.clues.get(answer.hashCode()).addAnswer(clue);
                } else {
                    SolvePuzzle.clues.put(answer.hashCode(), tempClue);
                }
            }
            */
        }

        //read in thesaurus
        //path = Driver.class.getResource("mobyThes.txt");
        file =  new FileReader("mobyThes.txt");
        buffer = new BufferedReader(file);
        boolean onWord;
        String word="";
        ArrayList<String> wordsOnLine = new ArrayList<>();

        //allThes.txt :stored as syn,syn,syn ... per line
        while ((tempLine = buffer.readLine())!=null)
        {
            tempLine=tempLine.toLowerCase();
            tempLine=tempLine.trim();
            word="";
            onWord=false;
            tempLine=tempLine.toLowerCase();
            tempLine=tempLine.trim();
            for(int j=0; j<tempLine.length(); j++)
            {
                if(((int)tempLine.charAt(j)>122 ||(int)tempLine.charAt(j)<97) &&(
                        (int)tempLine.charAt(j)!=32 && tempLine.charAt(j)!='-'))
                {
                    if(onWord)
                    {
                        word=word.trim();
                        wordsOnLine.add(word);
                    }
                    onWord=false;
                    word="";
                }
                else
                {
                    onWord=true;
                }
                if(onWord && (int)tempLine.charAt(j)!=32 && tempLine.charAt(j)!='-')
                {
                    word=word+tempLine.charAt(j);
                }
            }
            word=word.trim();
            wordsOnLine.add(word);
            /* if want more */

           for(int j=0; j<wordsOnLine.size(); j++)
            {
                if(!SolvePuzzle.words.containsKey(wordsOnLine.get(j).hashCode()))
                {
                    SolvePuzzle.words.put(wordsOnLine.get(j).hashCode(), new Word(wordsOnLine.get(j), wordsOnLine));
                }
            }



            /* or if want to limit */
            /*
            if(wordsOnLine.size()>1)
            {
                word = wordsOnLine.get(0);
                wordsOnLine.remove(word);
                SolvePuzzle.words.put(word.hashCode(), new Word(word, wordsOnLine));
            }
            */

            wordsOnLine.clear();
        }

        //Ability to do anagrams
        //path = Driver.class.getResource("dict.txt");
        file =  new FileReader("dict.txt");
        buffer = new BufferedReader(file);
        int tempLen=0;
        char[] temp;


        //read in dict.txt file : one word per line
        while ((tempLine = buffer.readLine())!=null)
        {
            temp=tempLine.toCharArray();
            Arrays.sort(temp);
            tempHashVal = new String(temp).hashCode();
            if(SolvePuzzle.dictAnagrams.containsKey(tempHashVal))
            {
                SolvePuzzle.dictAnagrams.get(tempHashVal).add(tempLine);
            }
            else
            {
                SolvePuzzle.dictAnagrams.put(tempHashVal,new ArrayList<String>());
                SolvePuzzle.dictAnagrams.get(tempHashVal).add(tempLine);
            }
            tempLen=tempLine.length();
            if(SolvePuzzle.dictByLen.containsKey(tempLen))
            {
                SolvePuzzle.dictByLen.get(tempLen).add(tempLine);
            }
            else
            {
                SolvePuzzle.dictByLen.put(tempLen,new ArrayList<String>());
                SolvePuzzle.dictByLen.get(tempLen).add(tempLine);
            }
        }

        //Ability to dictionary lookup
        //path = Driver.class.getResource("dictAndDef.txt");
        file =  new FileReader("dictAndDef.txt");
        buffer = new BufferedReader(file);
        boolean foundDef;
        String definition;
        boolean empty;

        //read in dict.txt file : one word per line
        while ((tempLine = buffer.readLine())!=null)
        {
            answer="";
            definition="";
            empty=false;
            tempLine=tempLine.trim();
            tempLine=tempLine.toLowerCase();
            foundDef=false;
            for(int j=0; j<tempLine.length(); j++)
            {
                if(j==0 && (int)tempLine.charAt(j)!=32)
                {
                    foundDef=true;
                }
                if(j==0 && ((int)tempLine.charAt(j)>122 ||(int)tempLine.charAt(j)<97))
                {
                    j=tempLine.length();
                    empty=true;
                }
                else if(((int)tempLine.charAt(j)>126 || ((int)tempLine.charAt(j)<97)))
                {
                    foundDef = false;
                    definition = definition + tempLine.substring(j, tempLine.length());
                    j = tempLine.length();
                }
                if(foundDef &&!empty)
                {
                    answer=answer+tempLine.charAt(j);
                }
            }
            if(!empty) {
                answer=answer.trim();
                definition=definition.trim();

                tempHashVal=answer.hashCode();
                if(!SolvePuzzle.dict.containsKey(tempHashVal))
                {
                    SolvePuzzle.dict.put(tempHashVal,definition);
                }
            }
        }

        //Temporary user input that will be changed later
        //just need to call SolvePuzzle.response after running driver to get the answer
        /*
        Scanner input = new Scanner(System.in);
        String clueStr = "";
        int len;
        String like;

        while(!clueStr.equals("-1")) {
            System.out.println("Enter the clue:");
            clueStr = input.nextLine();
            System.out.println("Enter the len:");
            len = input.nextInt();
            System.out.println("Enter the blanks:");
            like=input.next();
            System.out.println(SolvePuzzle.response(clueStr,len,like));
            input.nextLine();
        }
        */
    }
}
