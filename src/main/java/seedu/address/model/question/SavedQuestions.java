package seedu.address.model.question;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;

/**
 * Wraps all data at the questions level Duplicates are not allowed (by .isRepeated comparison)
 */
public class SavedQuestions implements ReadOnlyQuestions {

    private final QuestionBank questions;
    private ObservableList<Question> slideshowQuestions = FXCollections.observableArrayList();

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        questions = new QuestionBank();
    }

    public SavedQuestions() {
    }

    /**
     * Creates a list of SavedQuestions using the Questions in the {@code toBeCopied}
     */
    public SavedQuestions(ReadOnlyQuestions toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Returns a question bank.
     * @return The question bank.
     */
    public QuestionBank getQuestionBank() {
        return questions;
    }

    /**
     * Returns all questions currently stored.
     * @return The question list.
     */
    public ObservableList<Question> getAllQuestions() {
        return questions.getAllQuestions();
    }

    /**
     * Returns all result of questions searched.
     * @return The searched questions list.
     */
    public ObservableList<Question> getSearchQuestions() {
        return questions.getSearchQuestions();
    }

    /**
     * Replaces the contents of the Question list with {@code Questions}. {@code Questions} must not
     * contain duplicate Questions.
     */
    public void setQuestions(List<Question> questions) {
        this.questions.setQuestions(questions);
    }

    /**
     * Resets the existing data of this {@code SavedQuestions} with {@code newData}.
     */
    public void resetData(ReadOnlyQuestions newData) {
        requireNonNull(newData);

        setQuestions(newData.getSavedQuestions());
    }

    /**
     * Returns true if a Question with the same identity as {@code Question} exists in the saved
     * questions.
     */
    public boolean hasQuestion(Question question) {
        requireNonNull(question);
        return questions.contains(question);
    }

    /**
     * Adds a Question to the saved questions. The Question must not already exist in the saved
     * questions.
     */
    public void addQuestion(Question p) {
        questions.addQuestion(p);
    }

    /**
     * Returns the question object.
     *
     * @param index of the question in the list.
     * @return Question object.
     */
    public Question getQuestion(Index index) {
        return questions.getQuestion(index);
    }

    /**
     * Returns the summary of questions searched.
     *
     * @param textToFind text to find from questions list.
     * @return Summary of questions searched.
     */
    public String searchQuestions(String textToFind) {
        return questions.searchQuestions(textToFind);
    }

    /**
     * Returns the questions summary.
     *
     * @return Summary of questions.
     */
    public String getQuestionsSummary() {
        return questions.getQuestionsSummary();
    }

    /**
     * Returns the questions to be used in slideshow.
     *
     * @return Slideshow Question List.
     */
    public ObservableList<Question> getSlideshowQuestionList() {
        return slideshowQuestions;
    }

    /**
     * Replaces the given Question {@code target} in the list with {@code editedQuestion}. {@code
     * target} must exist in saved questions. The Question identity of {@code editedQuestion} must
     * not be the same as another existing Question in the saved questions.
     */
    public void setQuestion(Index index, Question editedQuestion) {
        requireNonNull(editedQuestion);

        questions.setQuestion(index, editedQuestion);
    }

    /**
     * Sets slideshow questions based on the list of question indexes passed in.
     *
     * @param questionsIndexes list of question indexes.
     */
    public void setSlideshowQuestions(List<Index> questionsIndexes) {
        slideshowQuestions.clear();

        for (int i = 0; i < questionsIndexes.size(); i++) {
            Question question = getQuestion(questionsIndexes.get(i));
            slideshowQuestions.add(question);
        }
    }

    /**
     * Removes {@code key} from this {@code SavedQuestions}. {@code key} must exist in saved
     * questions.
     */
    public void deleteQuestion(Question key) {
        questions.deleteQuestion(key);
    }

    /**
     * Removes {@code key} from this {@code SavedQuestions}. {@code key} must exist in saved
     * questions.
     */
    public Question deleteQuestion(Index index) {
        return questions.deleteQuestion(index);
    }

    //// util methods

    @Override
    public String toString() {
        return questions.asUnmodifiableObservableList().size() + " Questions";
    }

    @Override
    public ObservableList<Question> getSavedQuestions() {
        return questions.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SavedQuestions // instanceof handles nulls
            && questions.equals(((SavedQuestions) other).questions));
    }

    @Override
    public int hashCode() {
        return questions.hashCode();
    }
}
