import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Word {
    private final String word;
    private final ArrayList<String> synonyms;
    public Word(String word, ArrayList<String> synonyms)
    {
        this.word=word;
        this.synonyms=new ArrayList<>(synonyms);
    }

    public ArrayList<String> getSynonyms() {
        return synonyms;
    }

    /*
    * unscrambles the word/ anagram of word
    */
    public static ArrayList<String> anagram(String input, HashMap<Integer,ArrayList<String>> variations)
    {
        char[] temp=input.toCharArray();
        Arrays.sort(temp);
        String sortedTemp=new String(temp);
        sortedTemp=sortedTemp.trim();
        int tempHashVal =sortedTemp.hashCode();
        ArrayList<String> output = variations.get(tempHashVal);
        if(output.contains(input))
        {
            output.remove(input);
        }
        return output;
    }

    public String getWord() {
        return word;
    }
}
