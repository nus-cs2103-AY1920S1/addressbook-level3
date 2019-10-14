package seedu.address.model.quiz;

import java.util.ArrayList;

/**
 * Represents a storage class which holds all the quizzes created up to date.
 */
public class QuizBank {

    private ArrayList<Quiz> quizzes;

    /**
     * Creates a QuizBank instance with the appropriate attributes.
     */
    public QuizBank() {
        quizzes = new ArrayList<>();
    }

    /**
     * Adds a quiz to a quiz bank.
     * @param quiz The quiz to be added to the quiz bank.
     */
    public void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
    }

    /**
     * Removes a quiz from a quiz bank.
     * @param quizId The quiz to be removed from the quiz bank.
     */
    public void removeQuiz(String quizId) {
        for (Quiz q : quizzes) {
            if (q.getQuizId().equals(quizId)) {
                quizzes.remove(q);
            }
        }
    }

    /**
     * Returns the quizIndex of a quiz if found, else -1.
     * @param quizId The quiz identifier of the quiz.
     * @return The quizIndex of the quiz.
     */
    public int getQuizIndex(String quizId) {
        for (int i = 0; i < quizzes.size(); i++) {
            Quiz currentQuiz = quizzes.get(i);
            if (currentQuiz.getQuizId().equals(quizId)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns a quiz from a quiz bank.
     * @param quizIndex The quiz index.
     * @return The quiz from the quiz bank.
     */
    public Quiz getQuiz(int quizIndex) {
        return quizzes.get(quizIndex);
    }
}
