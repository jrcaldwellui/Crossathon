package com.example.nicklitterio.crosswordnew;

import java.util.ArrayList;

public class Clue {
    private final String clue; //Stores the clue
    private ArrayList<String> answers = new ArrayList<>();
    public Clue(String clue)
    {
        this.clue=clue;
    }

    public String getClue() {
        return clue;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void addAnswer(String answer)
    {
        /*
        if(!answers.isEmpty()) {
            if (!answers.contains(answer)) {
                answers.add(answer);
            }
        }
        else
        {
            answers.add(answer);
        }
        */
        answers.add(answer);
    }
}
