package seedu.address.model.quiz;

import java.util.ArrayList;

import seedu.address.model.question.Question;

/**
 * Represents a question list for a quiz.
 */
public class QuestionList {

    private ArrayList<Question> questions;

    /**
     * Creates a QuestionList instance with the appropriate attributes.
     */
    public QuestionList() {
        this.questions = new ArrayList<>();
    }

    /**
     * Adds a question to a question list, provided it is not already in the question list.
     * @param question The question to be added to the question list.
     * @return True if the question is not a repeat, else false.
     */
    public boolean addQuestion(Question question) {
        if (!isRepeated(question)) {
            questions.add(question);
            return true;
        }
        return false;
    }

    /**
     * Adds a question to a question list, provided it is not already in the question list.
     * @param questionNumber The index to add the question to.
     * @param question The question to be added to the question list.
     * @return True if the question is not a repeat, else false.
     */
    public boolean addQuestion(int questionNumber, Question question) {
        int questionIndex = questionNumber - 1;
        if (!isRepeated(question)) {
            questions.add(questionIndex, question);
            return true;
        }
        return false;
    }

    /**
     * Removes a question from a question list.
     * @param questionNumber The number of the question to be removed.
     * @return The question which was removed.
     */
    public Question removeQuestion(int questionNumber) {
        int questionIndex = questionNumber - 1;
        Question removedQuestion = questions.remove(questionIndex);
        return removedQuestion;
    }

    /**
     * Returns the questions for a quiz.
     * @return The questions for the quiz in an ArrayList.
     */
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    /**
     * Returns the answers for a quiz.
     * @return The answers for the quiz in an ArrayList.
     */
    public ArrayList<String> getAnswers() {
        ArrayList<String> answers = new ArrayList<>();
        for (Question q : questions) {
            answers.add(q.getAnswer());
        }
        return answers;
    }

    /**
     * Returns true if a question is already in a question list, else false.
     * @param question The question to be checked.
     * @return True if the question is already in the question list, else false.
     */
    private boolean isRepeated(Question question) {
        String otherQuestion = question.getQuestion();
        for (Question q : questions) {
            String currentQuestion = q.getQuestion();
            if (currentQuestion.equals(otherQuestion)) {
                return true;
            }
        }
        return false;
    }
}
