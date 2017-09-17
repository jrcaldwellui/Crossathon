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
    public static ArrayList<String> anagram(String input, HashMap<Integer,ArrayList<String>> variations)
    {
        char[] temp=input.toCharArray();
        Arrays.sort(temp);
        int tempHashVal = new String(temp).hashCode();
        ArrayList<String> output = variations.get(tempHashVal);
        output.remove(input);
        return output;
    }
}
