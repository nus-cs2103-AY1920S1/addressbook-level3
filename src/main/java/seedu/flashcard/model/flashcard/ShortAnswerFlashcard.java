package seedu.flashcard.model.flashcard;

import java.util.Set;

import seedu.flashcard.model.tag.Tag;

public class ShortAnswerFlashcard extends Flashcard {
    public ShortAnswerFlashcard(Question question, Definition definitions, Set<Tag> tags, Answer answer) {
        super(question, definitions, tags, answer);
    }
}
