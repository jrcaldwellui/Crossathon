//import java.lang.reflect.AnnotatedType;
import java.util.ArrayList;
import java.util.HashMap;

public class FindAnswer {

    /*
    * Generates synonyms for the clue and the answers
    */
    public static ArrayList<String> getAnswer(ArrayList<String> returnArray,HashMap<Integer,Clue> clues,
                                              HashMap<Integer,Word> syns, String clue)
    {
        String synToAdd="";
        if(clues.containsKey(clue.hashCode()))
        {
            if(syns.containsKey(clue.hashCode())) {
                for (int j = 0; j < syns.get(clue.hashCode()).getSynonyms().size(); j++) {
                    synToAdd = syns.get(clue.hashCode()).getSynonyms().get(j);
                    returnArray.add(synToAdd);

                }
            }
            for(int i=0; i<clues.get(clue.hashCode()).getAnswers().size(); i++) //ArrayList of Strings
            {
                //add each syn for the String
                if(syns.containsKey((clues.get(clue.hashCode()).getAnswers().get(i)).hashCode())) {
                    for (int j = 0; j < syns.get((clues.get(clue.hashCode()).getAnswers().get(i)).hashCode()).getSynonyms().size(); j++) {
                        synToAdd = syns.get((clues.get(clue.hashCode()).getAnswers().get(i)).hashCode()).getSynonyms().get(j);
                        returnArray.add(synToAdd);

                    }
                }
                if(!returnArray.contains(clues.get(clue.hashCode()).getAnswers().get(i)))
                {
                    returnArray.add(clues.get(clue.hashCode()).getAnswers().get(i));
                }
            }
        }
       return returnArray;
    }
    /*
    * Narrows down answer based on which characters are known/unknown
    */
    public static ArrayList<String> narrowAnswerKnown(ArrayList<String> answers,String known)
    {
        ArrayList<String> narrowedDown;
        narrowedDown=answers;
        String tempString;
        for(int i=0; i<narrowedDown.size(); i++) {
            tempString = narrowedDown.get(i);
            for (int j = 0; j < tempString.length(); j++) {
                if (tempString.charAt(j) != known.charAt(j) && known.charAt(j) != '?') {
                    narrowedDown.remove(i);
                    i--;
                    j = tempString.length();
                }
            }
        }
        return narrowedDown;
    }
    /*
    * narrows down answer based on length of the desired answer
    */
    public static ArrayList<String> narrowAnswerLen(ArrayList<String> answers,int desiredLength)
    {
        ArrayList<String> narrowedDown;
        narrowedDown=answers;
        String tempString;
        for(int i=0; i<narrowedDown.size(); i++)
        {
            tempString=narrowedDown.get(i);
            if(tempString.length()!=desiredLength)
            {
                narrowedDown.remove(i);
                i--;
            }
        }
        return narrowedDown;
    }
}
