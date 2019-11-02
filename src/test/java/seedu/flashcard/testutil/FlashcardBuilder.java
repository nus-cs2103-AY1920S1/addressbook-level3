package seedu.flashcard.testutil;

import seedu.flashcard.model.flashcard.Answer;
import seedu.flashcard.model.flashcard.Choice;
import seedu.flashcard.model.flashcard.Definition;
import seedu.flashcard.model.flashcard.McqFlashcard;
import seedu.flashcard.model.flashcard.Question;
import seedu.flashcard.model.flashcard.ShortAnswerFlashcard;
import seedu.flashcard.model.tag.Tag;
import seedu.flashcard.model.util.SampleDataUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FlashcardBuilder {

    public static final String DEFAULT_QUESTION = "Is watermelon sweet?";
    public static final String DEFULT_ANSWER = "Yes";
    public static final String DEFAULT_DEFINITION = "Watermelon is green";

    private Question question;
    private Answer answer;
    private Definition definition;
    private Set<Tag> tags;
    private List<Choice> choices;

    public FlashcardBuilder() {
        question = new Question(DEFAULT_QUESTION);
        answer = new Answer(DEFULT_ANSWER);
        definition = new Definition(DEFAULT_DEFINITION);
        tags = new HashSet<Tag>();
        choices = new ArrayList<Choice>();
    }

    /**
     * For short answer flashcards only.
     * Initializes the FlashcardBuilder with the data of {@code flashcard}.
     */
    public FlashcardBuilder(ShortAnswerFlashcard flashcard) {
        this.question = flashcard.getQuestion();
        this.answer = flashcard.getAnswer();
        this.tags = flashcard.getTags();
        this.definition = flashcard.getDefinition();
    }

    /**
     * For MCQ answer flashcards only.
     * Initializes the FlashcardBuilder with the data of {@code flashcard}.
     */
    public FlashcardBuilder(McqFlashcard flashcard) {
        this.question = flashcard.getQuestion();
        this.answer = flashcard.getAnswer();
        this.tags = flashcard.getTags();
        this.definition = flashcard.getDefinition();
        this.choices = flashcard.getChoices();
    }

    /**
     * Sets the {@code Question} of the {@code Flashcard} that we are building.
     */
    public FlashcardBuilder withQuestion(String question) {
        this.question = new Question(question);
        return this;
    }

    /**
     * Sets the {@code Definition} of the {@code Flashcard} that we are building.
     */
    public FlashcardBuilder withDefinition(String definition) {
        this.definition = new Definition(definition);
        return this;
    }

    /**
     * Sets the {@code Answer} of the {@code Flashcard} that we are building.
     */
    public FlashcardBuilder withAnswer(String answer) {
        this.answer = new Answer(answer);
        return this;
    }

    /**
     * Sets the {@code Set<Tag>} of the {@code Flashcard} that we are building.
     */
    public FlashcardBuilder withTag(String... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Set<Choice>} of the {@code Flashcard} that we are building.
     */
    public FlashcardBuilder withChoice(String... choices) {
        this.choices = SampleDataUtil.getChoiceList(choices);
        return this;
    }

    public ShortAnswerFlashcard buildShortAnswerFlashcard() {
        return new ShortAnswerFlashcard(question, definition, tags, answer);
    }

    public McqFlashcard buildMcqFlashcard() {
        return new McqFlashcard(question, choices, definition, tags, answer);
    }
}
