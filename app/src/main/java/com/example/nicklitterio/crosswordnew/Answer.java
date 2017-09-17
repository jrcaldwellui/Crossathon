package com.example.nicklitterio.crosswordnew;

public class Answer {
    private Clue clue;
    private final String answer;
    private final int length;

    public Answer(Clue clue, String answer) {
        this.clue=clue;
        this.answer=answer;
        length=answer.length();
    }
}
