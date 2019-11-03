package seedu.address.model.question;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;

/**
 * Stores questions and provides functionality to manage them.
 */
public class QuestionBank implements Iterable<Question> {

    private final ObservableList<Question> questions = FXCollections.observableArrayList();
    private final ObservableList<Question> questionsFiltered = FXCollections.observableArrayList();
    private final ObservableList<Question> questionsUnmodifiableList =
        FXCollections.unmodifiableObservableList(questions);

    /**
     * Replaces the contents of this list with {@code Question}. {@code questions} must not contain
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
        /*
        if (!isRepeated(question)) {
            this.questions.add(question);
        }
        */
        this.questions.add(question);
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
     * Returns all the questions in a question bank in an ObservableList representation.
     *
     * @return All the questions in the question bank in an ObservableList representation.
     */
    public ObservableList<Question> getAllQuestions() {
        return questions;
    }

    /**
     * Returns all the questions in a question bank in an ObservableList representation.
     *
     * @return All the questions in the question bank in an ObservableList representation.
     */
    public ObservableList<Question> getSearchQuestions() {
        return questionsFiltered;
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
     * Returns the summary of questions searched.
     *
     * @param textToFind text to find from questions list.
     * @return Summary of questions searched.
     */
    public String searchQuestions(String textToFind) {
        questionsFiltered.clear();
        int textToFindSize = textToFind.length();
        int similarityThreshold = (int) (textToFindSize * 0.4); // 40% match
        ArrayList<Question> similiarAl = new ArrayList<>(); // Used for grouping similiar terms

        // 2-levels of searching occurs here
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i).duplicate();
            Index index = Index.fromZeroBased(i);

            String questionStr = question.getQuestion();
            int difference = LevenshteinDistance.getDefaultInstance()
                .apply(textToFind, questionStr);

            if (StringUtils.containsIgnoreCase(questionStr,
                textToFind)) { // Search if text forms a subset of the question
                question.setQuestion(index.getOneBased() + ". " + question.getQuestion());
                questionsFiltered.add(question);
            } else if (difference <= similarityThreshold) { // Search for similar terms
                question.setQuestion(index.getOneBased() + ". " + question.getQuestion());
                similiarAl.add(question);
            }
        }

        questionsFiltered.addAll(similiarAl);
        questionsFiltered.sort(Comparator.comparingInt(o -> o.getQuestion().length()));

        return "Displaying results for \'" + textToFind + "\' and similar terms.\n"
            + "Found " + questionsFiltered.size() + " results";
    }

    /**
     * Returns the questions summary.
     *
     * @return Summary of questions.
     */
    public String getQuestionsSummary() {
        String summary = "Below is the list of questions.\n"
            + "There are currently " + questions.size() + " questions saved.\n";
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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof QuestionBank // instanceof handles nulls
            && questions.equals(((QuestionBank) other).questions));
    }
}
