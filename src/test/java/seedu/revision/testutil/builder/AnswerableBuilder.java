package seedu.revision.testutil.builder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import seedu.revision.model.answerable.Answer;
import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Difficulty;
import seedu.revision.model.answerable.Question;
import seedu.revision.model.category.Category;
import seedu.revision.model.util.SampleDataUtil;

/**
 * @author wilfredbtan
 * A utility class to help with building Answerable objects.
 */
public abstract class AnswerableBuilder<T extends Answerable> {

    public static final String DEFAULT_QUESTION = "Is this a question?";
    public static final String DEFAULT_DIFFICULTY = "1";
    public static final String DEFAULT_CATEGORY = "CATEGORY";

    protected Question question;
    protected ArrayList<Answer> correctAnswerList;
    protected ArrayList<Answer> wrongAnswerList;
    protected Difficulty difficulty;
    protected Set<Category> categories;

    public AnswerableBuilder() {
        question = new Question(DEFAULT_QUESTION);
        difficulty = new Difficulty(DEFAULT_DIFFICULTY);
        correctAnswerList = new ArrayList<>();
        correctAnswerList.add(new Answer("CORRECT"));
        categories = new HashSet<>();
        categories.add(new Category(DEFAULT_CATEGORY));
    }

    /**
     * Initializes the AnswerableBuilder with the data of {@code answerableToCopy}.
     */
    public AnswerableBuilder(Answerable answerableToCopy) {
        question = answerableToCopy.getQuestion();
        correctAnswerList = new ArrayList<>(answerableToCopy.getCorrectAnswerList());
        difficulty = answerableToCopy.getDifficulty();
        categories = new HashSet<>(answerableToCopy.getCategories());
    }

    /**
     * Sets the {@code Question} of the {@code Answerable} that we are building.
     */
    public AnswerableBuilder<T> withQuestion(String question) {
        this.question = new Question(question);
        return this;
    }

    /**
     * Sets the Correct Answer Set of the {@code Answerable} that we are building.
     */
    public AnswerableBuilder<T> withCorrectAnswerList(ArrayList<Answer> correctAnswerList) {
        this.correctAnswerList = correctAnswerList;
        return this;
    }

    /**
     * Sets the Wrong Answer Set of the {@code Answerable} that we are building.
     */
    public abstract AnswerableBuilder<T> withWrongAnswerList(ArrayList<Answer> wrongAnswerList);

    /**
     * Parses the {@code categories} into a {@code Set<Category>} and set it to the {@code Answerable}
     * that we are building.
     */
    public AnswerableBuilder<T> withCategories(String ... categories) {
        this.categories = SampleDataUtil.getCategorySet(categories);
        return this;
    }

    /**
     * Sets the {@code Difficulty} of the {@code Answerable} that we are building.
     */
    public AnswerableBuilder<T> withDifficulty(String difficulty) {
        this.difficulty = new Difficulty(difficulty);
        return this;
    }

    /**
     * Returns a subclass of  answerable.
     * @return the subclass of answerable.
     */
    public abstract T build();
}
