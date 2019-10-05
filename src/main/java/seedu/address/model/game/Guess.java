package seedu.address.model.game;

import seedu.address.model.flashcard.Answer;

public class Guess {

    private final String guessString;

    public Guess(String guessString) {
        this.guessString = guessString;
    }

    public String getGuessString() {
        return this.guessString;
    }

    public boolean matches(Answer answer) {
//        System.out.println("Guess String is: " + guessString );
//        System.out.println("Answer String is: " + answer.getAnswerString());
        return guessString.toLowerCase().equals(answer.getAnswerString().toLowerCase());
    }
}
