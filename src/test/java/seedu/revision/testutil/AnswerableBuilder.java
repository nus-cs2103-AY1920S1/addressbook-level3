package seedu.revision.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.revision.model.answerable.Answerable;
import seedu.revision.model.answerable.Difficulty;
import seedu.revision.model.answerable.Mcq;
import seedu.revision.model.answerable.Question;
import seedu.revision.model.answerable.answer.Answer;
import seedu.revision.model.category.Category;
import seedu.revision.model.util.SampleDataUtil;

/**
 * A utility class to help with building Answerable objects.
 */
public class AnswerableBuilder {

    public static final String DEFAULT_QUESTION = "Greenfield projects are easier than brownfield projects";
    public static final String DEFAULT_DIFFICULTY = "1";
    public static final String DEFAULT_CATEGORY = "CATEGORY";
    private static final Answer defaultCorrectAnswerSet = new Answer("CORRECT");
    private static final Answer defaultWrongAnswerSet = new Answer("WRONG");

    private Question question;
    private ArrayList<Answer> correctAnswerList;
    private ArrayList<Answer> wrongAnswerList;
    private Difficulty difficulty;
    private Set<Category> categories;

    public AnswerableBuilder() {
        question = new Question(DEFAULT_QUESTION);
        //TODO: Implement Answerable
        difficulty = new Difficulty(DEFAULT_DIFFICULTY);
        correctAnswerList = new ArrayList<>(Arrays.asList(defaultCorrectAnswerSet));
        wrongAnswerList = new ArrayList<>();
        wrongAnswerList.add(new Answer("Wrong answer A"));
        wrongAnswerList.add(new Answer("Wrong answer B"));
        wrongAnswerList.add(new Answer("Wrong answer C"));
        categories = new HashSet<>();
        categories.add(new Category(DEFAULT_CATEGORY));
    }

    /**
     * Initializes the AnswerableBuilder with the data of {@code answerableToCopy}.
     */
    public AnswerableBuilder(Answerable answerableToCopy) {
        question = answerableToCopy.getQuestion();
        //TODO: Implement Answerable
        correctAnswerList = new ArrayList<>(answerableToCopy.getCorrectAnswerList());
        wrongAnswerList = new ArrayList<>(answerableToCopy.getWrongAnswerList());
        difficulty = answerableToCopy.getDifficulty();
        categories = new HashSet<>(answerableToCopy.getCategories());
    }

    /**
     * Sets the {@code Question} of the {@code Answerable} that we are building.
     */
    public AnswerableBuilder withQuestion(String question) {
        this.question = new Question(question);
        return this;
    }

    /**
     * Sets the Correct Answer Set of the {@code Answerable} that we are building.
     */
    //TODO: Implement Answerable
    public AnswerableBuilder withCorrectAnswerList(ArrayList<Answer> correctAnswerList) {
        this.correctAnswerList = correctAnswerList;
        return this;
    }

    /**
     * Sets the Wrong Answer Set of the {@code Answerable} that we are building.
     */
    //TODO: Implement Answerable
    public AnswerableBuilder withWrongAnswerList(ArrayList<Answer> wrongAnswerList) {
        this.wrongAnswerList = wrongAnswerList;
        return this;
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>} and set it to the {@code Answerable}
     * that we are building.
     */
    public AnswerableBuilder withCategories(String ... categories) {
        this.categories = SampleDataUtil.getCategorySet(categories);
        return this;
    }

    /**
     * Sets the {@code Difficulty} of the {@code Answerable} that we are building.
     */
    public AnswerableBuilder withDifficulty(String difficulty) {
        this.difficulty = new Difficulty(difficulty);
        return this;
    }

    /**
     * Returns an answerable.
     * @return the answerable.
     */
    public Answerable build() {
        //TODO: Implement Answerable
        return new Mcq(question, correctAnswerList, wrongAnswerList, difficulty, categories);
    }

}
