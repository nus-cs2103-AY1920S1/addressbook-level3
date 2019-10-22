package seedu.flashcard.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.flashcard.commons.exceptions.IllegalValueException;
import seedu.flashcard.model.flashcard.Answer;
import seedu.flashcard.model.flashcard.Choice;
import seedu.flashcard.model.flashcard.Definition;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.Question;
import seedu.flashcard.model.flashcard.ShortAnswerFlashcard;
import seedu.flashcard.model.tag.Tag;

/**
 * Make the flashcard item more json friendly.
 */
public class JsonAdaptedShortAnswerFlashcard extends JsonAdaptedFlashcard{

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Flashcard's %s field is missing.";

    /**
     * Constructs a {@code JsonAdaptedFlashcard} with the given flashcard details.
     */
    @JsonCreator
    public JsonAdaptedShortAnswerFlashcard(@JsonProperty("question") String question,
                                           @JsonProperty("definition") String definition,
                                           @JsonProperty("tagged") List<JsonAdaptedTag> tagged,
                                           @JsonProperty("answer") String answer) {
        super(question, definition, tagged, answer);
    }

    /**
     * Converts a given {@code Flashcard} into this class for Jackson use.
     */
    public JsonAdaptedShortAnswerFlashcard(Flashcard source) {
       super(source);
    }

    /**
     * Converts this Jackson-friendly adapted flashcard object into the model's {@code Flashcard} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted flashcard.
     */
    @Override
    public ShortAnswerFlashcard toModelType() throws IllegalValueException {
        Flashcard card = super.toModelType();
        return (ShortAnswerFlashcard)card;
    }
}
