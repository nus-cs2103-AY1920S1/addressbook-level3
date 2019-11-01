package seedu.address.model.quiz;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.model.question.Question;
import seedu.address.model.question.QuestionBank;
import seedu.address.model.question.SavedQuestions;
import seedu.address.storage.export.HtmlExporter;

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
     * @param quizBank The quiz bank.
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
     * @param quizBank The quiz bank.
     * @return True if the quiz has been created, false if not.
     */
    public static boolean createQuizAutomatically(String quizId, int numQuestions, String type,
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

        if(listSize < numQuestions) {
            return false;
        }

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
        return true;
    }

    /**
     * Adds a question to a quiz.
     * @param quizId The identifier of the quiz to be handled.
     * @param questionNumber The question number of the question to be added.
     * @param quizQuestionNumber The quiz question number for the added question.
     * @param savedQuestions The saved questions.
     * @param quizBank The quiz bank.
     * @return True if the question is not a duplicate, else false.
     */
    public static boolean addQuizQuestion(String quizId, int questionNumber, int quizQuestionNumber,
                                        SavedQuestions savedQuestions, QuizBank quizBank) {
        QuestionBank questionBank = savedQuestions.getQuestionBank();

        int questionIndex = questionNumber - 1;
        Question question = questionBank.getQuestion(Index.fromZeroBased(questionIndex));

        int quizIndex = quizBank.getQuizIndex(quizId);
        Quiz quiz = quizBank.getQuiz(quizIndex);
        return quiz.addQuestion(quizQuestionNumber, question);
    }

    /**
     * Removes a question from a quiz.
     * @param quizId The identifier of the quiz to be handled.
     * @param questionNumber The question number of the question to be removed.
     * @param quizBank The quiz bank.
     * @return True if the question can be removed, else false.
     */
    public static boolean deleteQuizQuestion(String quizId, int questionNumber, QuizBank quizBank) {
        int quizIndex = quizBank.getQuizIndex(quizId);
        Quiz quiz = quizBank.getQuiz(quizIndex);
        int numQuestions = quiz.getQuestionList().getQuestions().size();
        if (questionNumber < 0 || questionNumber > numQuestions + 1) {
            return false;
        }
        quiz.removeQuestion(questionNumber);
        return true;
    }

    /**
     * Returns a String representation of a quiz's questions and answers.
     * @param quizId The identifier of the quiz to be handled.
     * @param quizBank The quiz bank.
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
     * Returns an observable list of questions from a quiz.
     * @param quizBank The quiz bank.
     * @return The observable list of questions from the quiz.
     */
    public static ObservableList<Question> getObservableListQuestionsFromQuiz(QuizBank quizBank) {
        String quizId = QuizBank.getCurrentlyQueriedQuiz();
        int quizIndex = quizBank.getQuizIndex(quizId);
        ObservableList<Question> questionList = null;
        if (quizIndex != -1) {
            Quiz quiz = quizBank.getQuiz(quizIndex);
            questionList = quiz.getObservableListQuestions();
        }
        return questionList;
    }

    /**
     * Returns true if a quiz exists, else false.
     * @param quizId The identifier of the quiz.
     * @param quizBank The quiz bank.
     * @return True if the quiz exists, else false.
     */
    public static boolean checkQuizExists(String quizId, QuizBank quizBank) {
        int quizIndex = quizBank.getQuizIndex(quizId);
        return quizIndex != -1;
    }

    /**
     * Exports a quiz to a html file.
     * @param quizId The identifier of the quiz.
     * @param quizBank The quiz bank.
     * @return True if the file does not exist, else false.
     * @throws IOException The exception to be thrown.
     */
    public static boolean exportQuiz(String quizId, QuizBank quizBank) throws IOException {
        String quizInfo = "";
        int quizIndex = quizBank.getQuizIndex(quizId);
        if (quizIndex != -1) {
            Quiz quiz = quizBank.getQuiz(quizIndex);
            quizInfo = quiz.getQuestionsForExport();
        }
        return HtmlExporter.export(quizId, quizInfo);
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
