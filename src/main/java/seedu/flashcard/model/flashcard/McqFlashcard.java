package seedu.flashcard.model.flashcard;

import static seedu.flashcard.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.flashcard.model.tag.Tag;

public class McqFlashcard extends Flashcard {

    private final Set<Choice> choices = new HashSet<>();

    public McqFlashcard(Question question, Set<Choice> choices, Definition definitions, Set<Tag> tags, Answer answer) {
        super(question, definitions, tags, answer);
        this.choices.addAll(choices);
    }

    /**
     * Returns an immutable choice set, which throws {@code UnsupportedOperationException}
     * if modification is attempted
     */
    public Set<Choice> getChoices() {
        return Collections.unmodifiableSet(choices);
    }

    /**
     * Returns true if any choices is from the choice list
     */
    public boolean hasAnyChoice(Set<Choice> choices) {
        for (Choice choice : choices) {
            if (getChoices().contains(choice)) {
                return true;
            }
        }
        return false;
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
