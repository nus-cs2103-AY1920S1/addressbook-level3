package seedu.address.model.quiz;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.question.Question;
import seedu.address.model.question.SavedQuestions;

/**
 * Wraps all data at the questions level Duplicates are not allowed (by .isRepeated comparison)
 */
public class SavedQuizzes implements ReadOnlyQuizzes {

    private final QuizBank quizzes;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        quizzes = new QuizBank();
    }

    public SavedQuizzes() {
    }

    /**
     * Creates a list of SavedQuizzes using the Quizzes in the {@code toBeCopied}
     */
    public SavedQuizzes(ReadOnlyQuizzes toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Returns a quiz bank.
     * @return The quiz bank.
     */
    public QuizBank getQuizBank() {
        return quizzes;
    }

    /**
     * Replaces the contents of the Quiz list with {@code Quizzes}. {@code Quizzes} must not
     * contain duplicate Quizzes.
     */
    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes.setQuizzes(quizzes);
    }

    /**
     * Resets the existing data of this {@code SavedQuizzes} with {@code newData}.
     */
    public void resetData(ReadOnlyQuizzes newData) {
        requireNonNull(newData);

        setQuizzes(newData.getSavedQuizzes());
    }

    //// Quiz-level operations

    /**
     * Creates a quiz manually.
     * @param quizId The identifier of the quiz to be created.
     * @param questionNumbers The question numbers to be added to the quiz.
     * @param savedQuestions The saved questions.
     * @return True is quiz has been created, false if not.
     */
    public boolean createQuizManually(String quizId, ArrayList<Integer> questionNumbers,
                                   SavedQuestions savedQuestions) {
        return QuizManager.createQuizManually(quizId, questionNumbers, savedQuestions, quizzes);
    }

    /**
     * Creates a quiz automatically.
     * @param quizId The identifier of the quiz to be created.
     * @param numQuestions The number of questions to be added to the quiz.
     * @param type The type of questions to be added to the quiz.
     * @param savedQuestions The saved questions.
     * @return True if the quiz has been created, false if not.
     */
    public boolean createQuizAutomatically(String quizId, int numQuestions, String type,
                                        SavedQuestions savedQuestions) {
        return QuizManager.createQuizAutomatically(quizId, numQuestions, type, savedQuestions, quizzes);
    }

    /**
     * Adds a question to a quiz.
     * @param quizId The identifier of the quiz to be handled.
     * @param questionNumber The question number of the question to be added.
     * @param quizQuestionNumber The quiz question number for the added question.
     * @param savedQuestions The saved questions.
     * @return True if the question is not a duplicate, else false.
     */
    public boolean addQuizQuestion(String quizId, int questionNumber, int quizQuestionNumber,
                                   SavedQuestions savedQuestions) {
        return QuizManager.addQuizQuestion(quizId, questionNumber, quizQuestionNumber, savedQuestions,
                                        quizzes);
    }

    /**
     * Removes a question from a quiz.
     * @param quizId The identifier of the quiz to be handled.
     * @param questionNumber The question number of the question to be removed.
     * @return True if the question can be removed, else false.
     */
    public boolean deleteQuizQuestion(String quizId, int questionNumber) {
        return QuizManager.deleteQuizQuestion(quizId, questionNumber, quizzes);
    }

    /**
     * Returns a String representation of a quiz's questions and answers.
     * @param quizId The identifier of the quiz to be handled.
     * @return The String representation of the quiz's questions and answers.
     */
    public String getQuestionsAndAnswers(String quizId) {
        return QuizManager.getQuestionsAndAnswers(quizId, quizzes);
    }

    public ObservableList<Question> getObservableListQuestionsFromQuiz() {
        return QuizManager.getObservableListQuestionsFromQuiz(quizzes);
    }

    public boolean checkQuizExists(String quizId) {
        return QuizManager.checkQuizExists(quizId, quizzes);
    }

    /**
     * Exports a quiz to a html file.
     * @param quizId The identifier of the quiz to be exported.
     */
    public boolean exportQuiz(String quizId) throws IOException {
        return QuizManager.exportQuiz(quizId, quizzes);
    }

    /**
     * Adds a Quiz to the saved quizzes.
     * @param quiz The quiz to be added.
     */
    public void addQuiz(Quiz quiz) {
        quizzes.addQuiz(quiz);
    }

    //// util methods

    @Override
    public String toString() {
        return quizzes.asUnmodifiableObservableList().size() + " Quizzes";
        // TODO: refine later
    }

    @Override
    public ObservableList<Quiz> getSavedQuizzes() {
        return quizzes.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SavedQuizzes // instanceof handles nulls
            && quizzes.equals(((SavedQuizzes) other).quizzes));
    }

    @Override
    public int hashCode() {
        return quizzes.hashCode();
    }
}
