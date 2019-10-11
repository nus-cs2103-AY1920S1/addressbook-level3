package seedu.address.model.question;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;

/**
 * Stores questions and provides functionality to manage them.
 */
public class QuestionBank {

    private ArrayList<Question> questions;

    /**
     * Creates a new QuestionList object.
     */
    public QuestionBank() {
        questions = new ArrayList<>();
    }

    /**
     * Add a new question to the question list.
     *
     * @param question to add to the list.
     */
    public void addQuestion(Question question) {
        if (!isRepeated(question)) {
            this.questions.add(question);
        }
        // TODO: Implement check if duplicated question AND answer is entered
    }

    /**
     * Deletes the question at the specified index in the list.
     *
     * @param index of the question in the list.
     * @return question object.
     */
    public Question deleteQuestion(Index index) {
        return questions.remove(index.getZeroBased());
    }

    /**
     * Returns the question object.
     *
     * @param index of the question in the list.
     * @return Question object.
     */
    public Question getQuestion(Index index) {
        return questions.get(index.getZeroBased());
    }

    /**
     * Sets the question object at the specified index in the list.
     *
     * @param index    of the question in the list.
     * @param question object.
     */
    public void setQuestion(Index index, Question question) {
        questions.set(index.getZeroBased(), question);
    }

    /**
     * Returns all the questions in a question bank in an ArrayList representation.
     * @return All the questions in the question bank in an ArrayList representation.
     */
    public ArrayList<Question> getAllQuestions() {
        return questions;
    }

    /**
     * Returns all the McqQuestions in a question bank in an ArrayList representation.
     * @return All the McqQuestions in the question bank in an ArrayList representation.
     */
    public ArrayList<Question> getMcqQuestions() {
        ArrayList<Question> mcqQuestions = new ArrayList<>();
        for (Question q : questions) {
            if (q instanceof McqQuestion) {
                mcqQuestions.add(q);
            }
        }
        return mcqQuestions;
    }

    /**
     * Returns all the OpenEndedQuestions in a question bank in an ArrayList representation.
     * @return All the OpenEndedQuestions in the question bank in an ArrayList representation.
     */
    public ArrayList<Question> getOpenEndedQuestions() {
        ArrayList<Question> openEndedQuestions = new ArrayList<>();
        for (Question q : questions) {
            if (q instanceof OpenEndedQuestion) {
                openEndedQuestions.add(q);
            }
        }
        return openEndedQuestions;
    }

    /**
     * Printing out the list of questions and how many are there.
     *
     * @return Summary of questions.
     */
    public String getQuestionsSummary() {
        String summary = "There are currently " + questions.size() + " questions saved.\n"
            + "Here is the list of questions:\n";

        for (int i = 0; i < questions.size(); i++) {
            summary += (i + 1) + ". " + "\"" + questions.get(i) + "\"";

            if ((i + 1) != questions.size()) {
                summary += "\n";
            }
        }

        return summary;
    }

    /**
     * Returns true if a question has been repeated, else false.
     * @param question The question to be checked.
     * @return True if the question has been repeated, else false.
     */
    private boolean isRepeated(Question question) {
        String otherQuestion = question.question;
        for (Question q : questions) {
            String currentQuestion = q.question;
            if (currentQuestion.equals(otherQuestion)) {
                return true;
            }
        }
        return false;
    }
}
