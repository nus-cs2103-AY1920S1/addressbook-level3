package seedu.address.model.quiz;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.question.Question;
import seedu.address.model.question.QuestionBank;
import seedu.address.model.question.SavedQuestions;

/**
 * Represents a manager for quizzes.
 */
public class QuizManager {

    /**
     * Creates a QuizManager instance with the appropriate attributes.
     */
    public QuizManager() {
    }

    /**
     * Creates a quiz manually.
     * @param quizId The identifier of the quiz to be created.
     * @param questionNumbers The question numbers to be added to the quiz.
     * @param savedQuestions The saved questions.
     */
    public static void createQuizManually(String quizId, ArrayList<Integer> questionNumbers,
                                        SavedQuestions savedQuestions, QuizBank quizBank) {
        Quiz quiz = new Quiz(quizId);
        QuestionBank questionBank = savedQuestions.getQuestionBank();

        ArrayList<Question> questions = new ArrayList<>();
        for (Integer i : questionNumbers) {
            questions.add(questionBank.getQuestion(Index.fromOneBased(i)));
        }

        for (Question q : questions) {
            quiz.addQuestion(q);
        }

        quizBank.addQuiz(quiz);
    }

    /**
     * Creates a quiz automatically.
     * @param quizId The identifier of the quiz to be created.
     * @param numQuestions The number of questions to be added to the quiz.
     * @param type The type of questions to be added to the quiz.
     * @param savedQuestions The saved questions.
     */
    public static void createQuizAutomatically(String quizId, int numQuestions, String type,
                                            SavedQuestions savedQuestions, QuizBank quizBank) {
        Quiz quiz = new Quiz(quizId);
        QuestionBank questionBank = savedQuestions.getQuestionBank();

        ObservableList<Question> relevantQuestions = FXCollections.observableArrayList();
        switch (type) {
        case "mcq":
            relevantQuestions = questionBank.getMcqQuestions();
            break;
        case "open":
            relevantQuestions = questionBank.getOpenEndedQuestions();
            break;
        case "all":
            relevantQuestions = questionBank.getAllQuestions();
            break;
        default:
            break;
        }

        int listSize = relevantQuestions.size();

        if (listSize > numQuestions) {
            for (int i = 0; i < numQuestions; i++) {
                int randomQuestionIndex = getRandomQuestionIndex(listSize);
                Question randomQuestion = relevantQuestions.get(randomQuestionIndex);
                boolean isSuccess = quiz.addQuestion(randomQuestion);
                while (!isSuccess) {
                    randomQuestionIndex = getRandomQuestionIndex(listSize);
                    randomQuestion = relevantQuestions.get(randomQuestionIndex);
                    isSuccess = quiz.addQuestion(randomQuestion);
                }
            }
        } else {
            for (Question q : relevantQuestions) {
                quiz.addQuestion(q);
            }
        }

        quizBank.addQuiz(quiz);
    }

    /**
     * Adds a question to a quiz.
     * @param quizId The identifier of the quiz to be handled.
     * @param questionNumber The question number of the question to be added.
     * @param quizQuestionNumber The quiz question number for the added question.
     * @param savedQuestions The saved questions.
     * @return True if the question is not a duplicate, else false.
     */
    public static boolean addQuizQuestion(String quizId, int questionNumber, int quizQuestionNumber,
                                        SavedQuestions savedQuestions, QuizBank quizBank) {
        QuestionBank questionBank = savedQuestions.getQuestionBank();

        int questionIndex = questionNumber - 1;
        Question question = questionBank.getQuestion(Index.fromZeroBased(questionIndex));

        int quizIndex = quizBank.getQuizIndex(quizId);
        if (quizIndex != -1) {
            Quiz quiz = quizBank.getQuiz(quizIndex);
            return quiz.addQuestion(quizQuestionNumber, question);
        }
        return false;
    }

    /**
     * Removes a question from a quiz.
     * @param quizId The identifier of the quiz to be handled.
     * @param questionNumber The question number of the question to be removed.
     */
    public static void removeQuizQuestion(String quizId, int questionNumber, QuizBank quizBank) {
        int quizIndex = quizBank.getQuizIndex(quizId);
        if (quizIndex != -1) {
            Quiz quiz = quizBank.getQuiz(quizIndex);
            quiz.removeQuestion(questionNumber);
        }
    }

    /**
     * Returns a String representation of a quiz's questions and answers.
     * @param quizId The identifier of the quiz to be handled.
     * @return The String representation of the quiz's questions and answers.
     */
    public static String getQuestionsAndAnswers(String quizId, QuizBank quizBank) {
        String questions = "";
        String answers = "";
        int quizIndex = quizBank.getQuizIndex(quizId);
        if (quizIndex != -1) {
            Quiz quiz = quizBank.getQuiz(quizIndex);
            questions = quiz.getFormattedQuestions();
            answers = quiz.getFormattedAnswers();
        }
        return questions + answers;
    }

    /**
     * Returns a random question index.
     * @param listSize The upper limit for the random value.
     * @return The random question index.
     */
    private static int getRandomQuestionIndex(int listSize) {
        return (int) Math.floor(Math.random() * listSize);
    }
}
