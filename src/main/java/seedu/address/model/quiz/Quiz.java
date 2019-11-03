package seedu.address.model.quiz;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.question.McqQuestion;
import seedu.address.model.question.Question;

/**
 * Represents a quiz, including a question list and an answer list.
 */
public class Quiz {

    private String quizId;
    private QuestionList questionList;

    /**
     * Creates a Quiz instance with the appropriate attributes.
     * @param quizId The identifier of the quiz, in String representation.
     */
    public Quiz(String quizId) {
        requireNonNull(quizId);
        this.quizId = quizId;
        this.questionList = new QuestionList();
    }

    /**
     * Returns a String representation of the quiz identifier.
     * @return The String representation of the quiz identifier.
     */
    public String getQuizId() {
        return quizId;
    }

    /**
     * Adds a question to the question list of a quiz.
     * @param question The question to be added to the question list of the quiz.
     * @return True if the question is not a repeat, else false.
     */
    public boolean addQuestion(Question question) {
        return questionList.addQuestion(question);
    }

    /**
     * Adds a question to the question list of a quiz.
     * @param questionNumber The question number to add the question to.
     * @param question The question to be added to the question list of the quiz.
     * @return True if the question is not a repeat, else false.
     */
    public boolean addQuestion(int questionNumber, Question question) {
        return questionList.addQuestion(questionNumber, question);
    }

    /**
     * Removes a question from the question list of a quiz.
     * @param questionNumber The question to be removed from the question list of the quiz.
     * @return The removed question.
     */
    public Question removeQuestion(int questionNumber) {
        return questionList.removeQuestion(questionNumber);
    }

    /**
     * Returns a quiz's question list.
     * @return The quiz's question list.
     */
    public QuestionList getQuestionList() {
        return questionList;
    }

    /**
     * Sets a quiz's question list.
     * @param questionList The question list to be set.
     */
    public void setQuestionList(QuestionList questionList) {
        this.questionList = questionList;
    }

    public ObservableList<Question> getObservableListQuestions() {
        ArrayList<Question> questions = questionList.getQuestions();
        ObservableList<Question> questionObservableList = FXCollections.observableArrayList();

        for (Question question : questions) {
            questionObservableList.add(question);
        }

        return questionObservableList;
    }

    /**
     * Returns the quiz questions, formatted in String representation for writing to a text file.
     * @return The quiz questions, formatted in String representation for writing to the text file.
     */
    public String getFormattedQuestions() {
        ArrayList<Question> questions = questionList.getQuestions();
        String formattedQuestions = quizId + ".\n\n";
        formattedQuestions += "Questions:\n";

        int listSize = questions.size();
        for (int i = 0; i < listSize; i++) {
            int questionNumber = i + 1;
            Question question = questions.get(i);
            String questionText = question.getQuestion();
            formattedQuestions += questionNumber + ". " + questionText + "\n";

            if (isMcqQuestion(question)) {
                McqQuestion mcqQuestion = (McqQuestion) question;
                formattedQuestions += "A. " + mcqQuestion.getOptionA() + "\n";
                formattedQuestions += "B. " + mcqQuestion.getOptionB() + "\n";
                formattedQuestions += "C. " + mcqQuestion.getOptionC() + "\n";
                formattedQuestions += "D. " + mcqQuestion.getOptionD() + "\n";
            }

            formattedQuestions += "\n";
        }

        return formattedQuestions;
    }

    /**
     * Returns the quiz answers, formatted in String representation for writing to a text file.
     * @return The quiz answers, formatted in String representation for writing to the text file.
     */
    public String getFormattedAnswers() {
        ArrayList<String> answers = questionList.getAnswers();
        String formattedAnswers = "Answers [Teacher's Copy]:\n";

        int listSize = answers.size();
        for (int i = 0; i < listSize; i++) {
            int questionNumber = i + 1;
            String answer = answers.get(i);
            formattedAnswers += questionNumber + ". " + answer + "\n";
            formattedAnswers += "\n";
        }

        return formattedAnswers;
    }

    /**
     * Returns a String representation of a quiz's questions.
     * @return The String representation of the quiz's questions.
     */
    public String getQuestionsForExport() {
        ArrayList<Question> questions = questionList.getQuestions();
        String formattedQuestions = "<!DOCTYPE html>\n<html>\n<body>\n" + "<center><h2> ";
        formattedQuestions += quizId + "</h2></center>\n";

        int listSize = questions.size();
        for (int i = 0; i < listSize; i++) {
            int questionNumber = i + 1;
            Question question = questions.get(i);
            String questionText = question.getQuestion();
            formattedQuestions += "<font size=\"5\">" + questionNumber + ". " + questionText + "</font>\n";

            if (isMcqQuestion(question)) {
                McqQuestion mcqQuestion = (McqQuestion) question;
                String optionA = mcqQuestion.getOptionA();
                String optionB = mcqQuestion.getOptionB();
                String optionC = mcqQuestion.getOptionC();
                String optionD = mcqQuestion.getOptionD();
                formattedQuestions += "<br><br>\n";
                formattedQuestions += "<font size=\"4\">A) " + optionA
                                        + "</font><input type=\"radio\" name =" + "\"" + questionText
                                        + "\" value=\"" + optionA + "\"><br>\n";
                formattedQuestions += "<font size=\"4\">B) " + optionB
                                        + "</font><input type=\"radio\" name =" + "\"" + questionText
                                        + "\" value=\"" + optionB + "\"><br>\n";
                formattedQuestions += "<font size=\"4\">C) " + optionC
                                        + "</font><input type=\"radio\" name =" + "\"" + questionText
                                        + "\" value=\"" + optionC + "\"><br>\n";
                formattedQuestions += "<font size=\"4\">D) " + optionD
                                        + "</font><input type=\"radio\" name =" + "\"" + questionText
                                        + "\" value=\"" + optionD + "\"><br><br>\n";
            } else {
                formattedQuestions += "<form>\n"
                        + "  <br><font size=\"4\">Answer:</font>"
                        + " <input type=\"text\" name=\"answer\" size=\"100\"><br><br>\n"
                        + "</form>";
            }
        }

        formattedQuestions += "</body>\n" + "</html>";

        return formattedQuestions;
    }

    /**
     * Sets the String represented questions as the questions of a question list.
     * @param questions The questions in String representation.
     */
    public void setStringQuestions(String questions) {
        questionList.setStringQuestions(questions);
    }

    /**
     * Gets the questions of a question list in String representation.
     * @return The questions of the question list in String representation.
     */
    public String getStringQuestions() {
        return questionList.getStringQuestions();
    }

    /**
     * Returns true if a question is an McqQuestion, else false.
     * @param question The question to be checked.
     * @return True if the question is an McqQuestion, else false.
     */
    private boolean isMcqQuestion(Question question) {
        return (question instanceof McqQuestion);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Quiz)) {
            return false;
        }

        Quiz otherQuiz = (Quiz) other;
        return otherQuiz.getQuizId().equals(this.getQuizId());
    }
}
