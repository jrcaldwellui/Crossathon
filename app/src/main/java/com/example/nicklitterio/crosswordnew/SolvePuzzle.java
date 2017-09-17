package com.example.nicklitterio.crosswordnew;

import javax.xml.transform.TransformerException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class SolvePuzzle {
    static HashMap<Integer,Clue> clues = new HashMap<>();
    static HashMap<Integer,Word> words = new HashMap<>();
    static HashMap<Integer,ArrayList<String>> dictAnagrams = new HashMap<>();
    static TreeMap<Integer,ArrayList<String>> dictByLen = new TreeMap<>();
    static HashMap<Integer,String> dict = new HashMap<>();

    /*Only function needed to be called for entire program after main is run
    *Clue is the clue of that is being looked for
    *len is how long the answer should be
    *String blanks is which letters are known:
    * ex: if 4 letters and first is an a: a???
    * ex: know 2nd and 3rd letters: ?xy?
    * ex: all unknown: ????
    * String must be ? or letters and must be size of len
    */
    public static String response(String clue,int len, String blanks)
    {
        ArrayList<String> output = new ArrayList<>();
        if (blanks.length()<len)
        {
            return "";
        }
        clue = clue.trim();
        clue = clue.toLowerCase();
        if(SolvePuzzle.clues.containsKey(clue.hashCode()))
        {
            for(int i=0; i<SolvePuzzle.clues.get(clue.hashCode()).getAnswers().size(); i++) {
                output.add(0, SolvePuzzle.clues.get(clue.hashCode()).getAnswers().get(i));
            }
        }
        output = FindAnswer.getAnswer(output,SolvePuzzle.clues,SolvePuzzle.words, clue);
        if(SolvePuzzle.words.containsKey(clue.hashCode()))
        {
            for(int i=0; i<SolvePuzzle.words.get(clue.hashCode()).getSynonyms().size(); i++)
            {
                if(!output.contains(SolvePuzzle.words.get(clue.hashCode()).getSynonyms().get(i)))
                {
                    output.add(SolvePuzzle.words.get(clue.hashCode()).getSynonyms().get(i));
                }
            }
        }
        ArrayList<String> anagrams;
        if(clue.contains("anagram of "))
        {
            anagrams = Word.anagram(clue.substring(clue.indexOf("anagram of ") + 11,clue.length()),SolvePuzzle.dictAnagrams);
            for(int i=0; i<anagrams.size(); i++)
            {
                output.add(anagrams.get(i));
            }
        }
        else if(clue.contains("unscramble "))
        {
            anagrams = Word.anagram(clue.substring(clue.indexOf("unscramble ")+11,clue.length()),SolvePuzzle.dictAnagrams);
            for(int i=0; i<anagrams.size(); i++)
            {
                output.add(anagrams.get(i));
            }
        }
        if (output != null)
        {
            if (output.size() > 0)
            {
                output = FindAnswer.narrowAnswerLen(output, len);
                output = FindAnswer.narrowAnswerKnown(output, blanks);
                if (output != null && output.size() > 0) {
                    return output.get(0);
                }
            }
            String tempDef;
            int count=0;
            int highestCount=0;
            int bestMatch =0;
            int start=0;
            String tempStr;
            //if no response found query dictionary
            output =  dictByLen.get(len);
            output = FindAnswer.narrowAnswerKnown(output, blanks);
            //returns the word that has the most words in definition that match
            //the clue. Ties are broken by alphabetical order. Each word is equally as relevant
            //at the moment.
            for(int i=0; i<output.size(); i++)
            {

                count=0;
                if(dict.containsKey(output.get(i).hashCode()))
                {
                    tempDef=dict.get(output.get(i).hashCode());
                    start=0;
                    for(int j=0; j<clue.length(); j++)
                    {
                        if((int)clue.charAt(j)==32)
                        {
                            tempStr=clue.substring(start,j);
                            if(tempDef.contains(tempStr))
                            {
                                if(!tempStr.equals("the") && !tempStr.equals("a") &&
                                        !tempStr.equals("of") && !tempStr.equals("and"))
                                {
                                    count++;
                                }
                            }
                            start=j+1;
                        }
                    }
                    if(count>highestCount)
                    {
                        highestCount=count;
                        bestMatch=i;
                    }
                }
            }
            return output.get(bestMatch);
        }
        return "";
    }
}
