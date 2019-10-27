package seedu.flashcard.model.flashcard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import seedu.flashcard.model.tag.Tag;

/**
 * Represents a MCQ Flashcard with a list of choices.
 */
public class McqFlashcard extends Flashcard {

    private final List<Choice> choices = new ArrayList<>();

    /**
     * This is the initializer when user is creating a new flashcard
     */
    public McqFlashcard(Question question, List<Choice> choices, Definition definitions, Set<Tag> tags, Answer answer) {
        super(question, definitions, tags, answer);
        this.choices.addAll(choices);
    }

    /**
     * This initializer should only be accessed by the storage package, because this initializer ensures the
     * flashcard scores can be reloaded
     */
    public McqFlashcard(Question question, List<Choice> choices, Definition definitions,
                        Set<Tag> tags, Answer answer, Score score) {
        super(question, definitions, tags, answer, score);
        this.choices.addAll(choices);
    }

    /**
     * Returns an immutable choice list, which throws {@code UnsupportedOperationException}
     * if modification is attempted
     */
    public List<Choice> getChoices() {
        return Collections.unmodifiableList(choices);
    }

    /**
     * Returns true if any choices is from the choice list
     */
    public boolean hasAnyChoice(List<Choice> choices) {
        for (Choice choice : choices) {
            if (getChoices().contains(choice)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isMcq() {
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getQuestion()).append("\n")
                .append("\nDefinitions:").append("\n")
                .append(getDefinition()).append("\n");
        if (!tags.isEmpty()) {
            builder.append("\nTags:").append("\n");
            getTags().forEach(builder::append);
        }
        if (!choices.isEmpty()) {
            builder.append("\nChoices:\n");
            int index = 1;
            for (Choice choice : getChoices()) {
                builder.append(Integer.toString(index) + ". " + choice);
                index++;
            }
        }
        return builder.toString();
    }
}
