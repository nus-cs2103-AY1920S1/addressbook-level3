package seedu.address.model.question;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;

/**
 * Stores questions and provides functionality to manage them.
 */
public class QuestionBank implements Iterable<Question> {

    private final ObservableList<Question> questions = FXCollections.observableArrayList();
    private final ObservableList<Question> questionsUnmodifiableList =
        FXCollections.unmodifiableObservableList(questions);

    /**
     * Replaces the contents of this list with {@code Students}. {@code Students} must not contain
     * duplicate Students.
     */
    public void setQuestions(List<Question> questions) {
        requireAllNonNull(questions);
        if (!isRepeated(questions)) {
            //throw new DuplicateStudentException();
        }

        this.questions.setAll(questions);
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
     * Deletes the question object from the list.
     *
     * @param question object.
     */
    public void deleteQuestion(Question question) {
        requireNonNull(question);
        if (!questions.remove(question)) {
            //throw new StudentNotFoundException();
        }
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
     * Sets the question object in the list using a specified question object.
     *
     * @param target         of the question in the list.
     * @param editedQuestion to replace target.
     */
    public void setQuestion(Question target, Question editedQuestion) {
        requireAllNonNull(target, editedQuestion);

        int index = questions.indexOf(target);
        if (index == -1) {
            //throw new StudentNotFoundException();
        }

        if (!target.isSameQuestion(editedQuestion) && contains(editedQuestion)) {
            //throw new DuplicateStudentException();
        }

        questions.set(index, editedQuestion);
    }

    /**
     * Returns all the questions in a question bank in an ArrayList representation.
     *
     * @return All the questions in the question bank in an ArrayList representation.
     */
    public ObservableList<Question> getAllQuestions() {
        return questions;
    }

    /**
     * Returns all the McqQuestions in a question bank in an ObservableList representation.
     *
     * @return mcq questions
     */
    public ObservableList<Question> getMcqQuestions() {
        ObservableList<Question> mcqQuestions = FXCollections.observableArrayList();
        for (Question q : questions) {
            if (q instanceof McqQuestion) {
                mcqQuestions.add(q);
            }
        }
        return mcqQuestions;
    }

    /**
     * Returns all the OpenEndedQuestions in a question bank in an ArrayList representation.
     *
     * @return open ended questions
     */
    public ObservableList<Question> getOpenEndedQuestions() {
        ObservableList<Question> openEndedQuestions = FXCollections.observableArrayList();
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
     *
     * @param question The question to be checked.
     * @return True if the question has been repeated, else false.
     */
    private boolean isRepeated(Question question) {
        for (Question q : questions) {
            if (q.isSameQuestion(question)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if a question has been repeated, else false.
     *
     * @param questions the list of questions to check.
     * @return True if the question has been repeated, else false.
     */
    private boolean isRepeated(List<Question> questions) {
        for (int i = 0; i < questions.size() - 1; i++) {
            for (int j = i + 1; j < questions.size(); j++) {
                if (questions.get(i).isSameQuestion(questions.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns true if the list contains an equivalent Student as the given argument.
     */
    public boolean contains(Question toCheck) {
        requireNonNull(toCheck);
        return questions.stream().anyMatch(toCheck::isSameQuestion);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Question> asUnmodifiableObservableList() {
        return questionsUnmodifiableList;
    }

    @Override
    public Iterator<Question> iterator() {
        return questions.iterator();
    }
}
