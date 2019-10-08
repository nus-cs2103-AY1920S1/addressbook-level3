package seedu.address.model.question;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;

/**
 * Stores questions and provides functionality to manage them.
 */
public class QuestionList {

    private ArrayList<Question> questions;

    /**
     * Creates a new QuestionList object.
     */
    public QuestionList() {
        questions = new ArrayList<>();
    }

    /**
     * Add a new question to the question list.
     *
     * @param question to add to the list.
     */
    public void addQuestion(Question question) {
        this.questions.add(question);
        // TODO: Implement check if duplicated question AND answer is entered
    }

    /**
     * Returns the question object.
     *
     * @param index of the question in the list.
     * @return Question object.
     */
    public Question getQuestion(Index index){
        return questions.get(index.getZeroBased());
    }

    /**
     * Sets the question object at the specified index in the list.
     *
     * @param index of the question in the list.
     * @param question object.
     */
    public void setQuestion(Index index, Question question){
        questions.set(index.getZeroBased(), question);
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
            summary += questions.get(i);

            if ((i + 1) != questions.size()) {
                summary += "\n";
            }
        }

        return summary;
    }
}
