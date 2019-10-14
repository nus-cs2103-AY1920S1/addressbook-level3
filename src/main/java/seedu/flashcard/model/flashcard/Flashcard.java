package seedu.flashcard.model.flashcard;

import static seedu.flashcard.commons.util.CollectionUtil.requireAllNonNull;

import seedu.flashcard.model.tag.Tag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a Flashcard in the flashcard list.
 * Guarantees: details are present and not null, field values are validated, immutable
 */
<<<<<<< HEAD
public class Flashcard {
=======
public abstract class Flashcard {

    private Question question;
    private Answer answer;
    private Score score;
    private CardId id;
    private ArrayList<Tag> tags;
    private String type; // new instance field to flashcard

    /**
     * Question and Answer must be specified.
     */
    public Flashcard(Question question, Answer answer) {
        this.question = question;
        this.answer = answer;
        this.score = new Score();
        this.id = new CardId();
        this.tags = new ArrayList<>();
        this.type = "Haven't assigned type";
    }

    public Question getQuestion() {
        return question;
    }

    public Answer getAnswer() {
        return answer;
    }

    public Score getScore() {
        return score;
    }
>>>>>>> d1c57e0ab067dc917c6d7d3f6953a795e5a47fae

    // Identity fields
    private final Word word;

    // Data fields
    private final Definition definition;
    private final Set<Tag> tags = new HashSet<>();

    public Flashcard(Word word, Definition definitions, Set<Tag> tags) {
        requireAllNonNull(word, definitions, tags);
        this.word = word;
        this.definition = definitions;
        this.tags.addAll(tags);
    }

    public Word getWord() {
        return word;
    }

    public String getType() {
        return type;
    };

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns an immutable definition set, which throws {@code UnsupportedOperationException}
     * if modification is attempted
     */
    public Definition getDefinition() {
        return definition;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Defines that if and only if two flashcards containing the same word can be considered as the same.
     */
    public boolean isSameFlashcard(Flashcard otherFlashcard) {
        if (otherFlashcard == this) {
            return true;
        }
<<<<<<< HEAD
        return otherFlashcard != null
                && otherFlashcard.getWord().equals(getWord());
=======
    };

    /**
     * Checks if flashcard has a specific tag
     * @param tagName
     * @return
     */
    public boolean hasTag (String tagName) {
        for (Tag tag : tags) {
            if (tag.getName().equals(tagName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * While searching for a model, decide that whether this model contains the keyword or not.
     * @param s the keyword we are looking for
     * @return true if question, answer or the id contains the keyword, false otherwise
     */
    public boolean contains(String s) {
        return question.contains(s) || answer.contains(s) || id.contains(s);
>>>>>>> d1c57e0ab067dc917c6d7d3f6953a795e5a47fae
    }

    /**
     * Returns true if both the word and the definitions and the tags are the same.
     * This is stronger than {@code isSameFlashCard}
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Flashcard)) {
            return false;
        }
        Flashcard otherFlashcard = (Flashcard) other;
<<<<<<< HEAD
        return otherFlashcard.getWord().equals(getWord())
                && otherFlashcard.getDefinition().equals(getDefinition())
                && otherFlashcard.getTags().equals(getTags());
=======
        return otherFlashcard.getId() == this.getId() && otherFlashcard.getQuestion() == this.getQuestion()
                                                      && otherFlashcard.getAnswer() == this.getAnswer()
                                                      && otherFlashcard.getType().equals(this.getType());
>>>>>>> d1c57e0ab067dc917c6d7d3f6953a795e5a47fae
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getWord())
                .append(" Definitions: ")
                .append(getDefinition())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, definition, tags);
    }
}
