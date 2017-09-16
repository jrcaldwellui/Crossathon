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
}
