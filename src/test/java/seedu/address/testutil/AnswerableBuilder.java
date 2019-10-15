package seedu.address.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.answerable.Answer;
import seedu.address.model.answerable.Answerable;
import seedu.address.model.category.Category;
import seedu.address.model.answerable.Difficulty;
import seedu.address.model.answerable.Mcq;
import seedu.address.model.answerable.AnswerSet;
import seedu.address.model.answerable.Question;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Answerable objects.
 */
public class AnswerableBuilder {

    public static final String DEFAULT_QUESTION = "Greenfield projects are easier than greenfield projects";
    public static final String DEFAULT_DIFFICULTY = "1";
    public static final String DEFAULT_CATEGORY = "brownfield";
    private static final Answer correctAnswer = new Answer("CORRECT");
    private static final Set<Answer> correctAnswerSet = new HashSet<>(Arrays.asList(correctAnswer));
    private static final Answer wrongAnswer = new Answer("WRONG");
    private static final Set<Answer> wrongAnswerSet = new HashSet<>(Arrays.asList(wrongAnswer));
    private static final AnswerSet defaultAnswerSet = new AnswerSet(correctAnswerSet, wrongAnswerSet);

    private Question question;
    //TODO: Implement Answerable
    private AnswerSet answer;
    private Difficulty difficulty;
    private Set<Category> categories;

    public AnswerableBuilder() {
        question = new Question(DEFAULT_QUESTION);
        //TODO: Implement Answerable
        answer = defaultAnswerSet;
        difficulty = new Difficulty(DEFAULT_DIFFICULTY);
        categories = new HashSet<>();
    }

    /**
     * Initializes the AnswerableBuilder with the data of {@code answerableToCopy}.
     */
    public AnswerableBuilder(Answerable answerableToCopy) {
        question = answerableToCopy.getQuestion();
        //TODO: Implement Answerable
        answer = answerableToCopy.getAnswerSet();
        difficulty = answerableToCopy.getDifficulty();
        categories = new HashSet<>(answerableToCopy.getCategories());
    }

    /**
     * Sets the {@code Question} of the {@code Answerable} that we are building.
     */
    public AnswerableBuilder withQuestion(String name) {
        this.question = new Question(name);
        return this;
    }

    /**
     * Sets the {@code Question} of the {@code Answerable} that we are building.
     */
    //TODO: Implement Answerable
    public AnswerableBuilder withAnswerSet(AnswerSet answerSet) {
        this.answer = answerSet;
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Answerable} that we are building.
     */
    public AnswerableBuilder withTags(String ... tags) {
        this.categories = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code Answerable} that we are building.
     */
    public AnswerableBuilder withCategory(String address) {
        this.category = new Category(address);
        return this;
    }

    /**
     * Sets the {@code Difficulty} of the {@code Answerable} that we are building.
     */
    public AnswerableBuilder withDifficulty(String difficulty) {
        this.difficulty = new Difficulty(difficulty);
        return this;
    }

    public Answerable build() {
        //TODO: Implement Answerable
        return new Mcq(question, answer, difficulty, categories);
    }

}
