package seedu.flashcard.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.flashcard.commons.exceptions.IllegalValueException;
import seedu.flashcard.model.flashcard.Score;

/**
 * Json-friendly version of {@link seedu.flashcard.model.flashcard.Score}
 */
public class JsonAdaptedScore {

    private final String score;

    /**
     * Constructs a {@code JsonAdaptedScore} with given {@code score}
     */
    @JsonCreator
    public JsonAdaptedScore(String score) {
        this.score = score;
    }

    /**
     * Converts a given {@code score} into this class for Jackson use
     */
    public JsonAdaptedScore(Score score) {
        this.score = score.getCorrectAnswers() + " " + score.getWrongAnswers();
    }

    @JsonValue
    public String getScore() {
        return score;
    }

    /**
     * Converts this Jackson friendly score object into the model's {@code Score} object
     */
    public Score toModelType() throws IllegalValueException {
        if (!Score.isValidScore(score)) {
            throw new IllegalValueException(Score.MESSAGE_CONSTRAINTS);
        }
        String[] splitedScore = score.split(" ");
        int correctAnswers = Integer.parseInt(splitedScore[0]);
        int wrongAnswers = Integer.parseInt(splitedScore[1]);
        return new Score(correctAnswers, wrongAnswers);
    }
}
