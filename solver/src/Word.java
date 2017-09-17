import java.util.ArrayList;

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
    public static String makeOneWord(String input)
    {
        String output="";
        for(int i=0; i<input.length(); i++)
        {
            if((int)input.charAt(i)<=122 &&(int)input.charAt(i)>=97)
            {
                output=output+input.charAt(i);
            }
        }
        return output;
    }
}
