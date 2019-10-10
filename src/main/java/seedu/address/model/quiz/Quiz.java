package seedu.address.model.quiz;

import seedu.address.model.question.McqQuestion;
import seedu.address.model.question.Question;

import java.util.ArrayList;

/**
 * Represents a quiz, including a question list and an answer list.
 */
public class Quiz {

    private String quizID;
    private QuestionList questionList;

    /**
     * Creates a Quiz instance with the appropriate attributes.
     * @param quizID The identifier of the quiz, in String representation.
     */
    public Quiz(String quizID) {
        this.quizID = quizID;
        this.questionList = new QuestionList();
    }

    /**
     * Returns a String representation of the quiz identifier.
     * @return The String representation of the quiz identifier.
     */
    public String getQuizID() {
        return quizID;
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
     * Returns the quiz questions, formatted in String representation for writing to a text file.
     * @return The quiz questions, formatted in String representation for writing to the text file.
     */
    public String getFormattedQuestions() {
        ArrayList<Question> questions = questionList.getQuestions();
        String formattedQuestions = quizID + "\n";

        int listSize = questions.size();
        for(int i = 0; i < listSize; i++) {
            int questionNumber = i + 1;
            Question question = questions.get(i);
            String questionText = question.getQuestion();
            formattedQuestions += questionNumber + ". " + questionText + "\n";

            if(isMcqQuestion(question)) {
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
        String formattedAnswers = quizID + " Answers (Only for teachers!)\n";

        int listSize = answers.size();
        for(int i = 0; i < listSize; i++) {
            int questionNumber = i + 1;
            String answer = answers.get(i);
            formattedAnswers += questionNumber + ". " + answer + "\n";
            formattedAnswers += "\n";
        }

        return formattedAnswers;
    }

    /**
     * Returns true if a question is an McqQuestion, else false.
     * @param question The question to be checked.
     * @return True if the question is an McqQuestion, else false.
     */
    private boolean isMcqQuestion(Question question) {
        return (question instanceof McqQuestion);
    }
}
