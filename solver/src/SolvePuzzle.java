import java.util.ArrayList;
import java.util.HashMap;

public class SolvePuzzle {
    static HashMap<Integer,Clue> clues = new HashMap<>();
    static HashMap<Integer,Word> words = new HashMap<>();
    static HashMap<Integer,ArrayList<String>> dictAnagrams = new HashMap<>();

    public static String response(String clue,int len, String blanks)
    {
        String like;
        clue = clue.trim();
        clue = clue.toLowerCase();

        ArrayList<String> output = FindAnswer.getAnswer(SolvePuzzle.clues,SolvePuzzle.words, clue);
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
        if (output != null )
        {
            output = FindAnswer.narrowAnswerLen(output,len);
            output = FindAnswer.narrowAnswerKnown(output,blanks);
            return output.get(0);
        }
        return clue;
    }
}
