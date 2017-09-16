import java.util.ArrayList;
import java.util.HashMap;

public class FindAnswer {
    private HashMap<Integer,Clue> clues;
    public FindAnswer(HashMap clues)
    {
        this.clues=clues;
    }
    public static ArrayList<String> getAnswer(HashMap<Integer,Clue> clues, String clue)
    {
        if(clues.containsKey(clue.hashCode()))
        {
               return  clues.get(clue.hashCode()).getAnswers();
        }
       return null;
    }
}
