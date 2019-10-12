package com.dukeacademy.model.question;

import static com.dukeacademy.commons.util.CollectionUtil.requireAllNonNull;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import com.dukeacademy.model.question.exceptions.DuplicateQuestionException;
import com.dukeacademy.model.question.exceptions.QuestionNotFoundException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of questions that enforces uniqueness between its elements and does
 * not allow nulls.
 * A question is considered unique by comparing using {@code Question#isSameQuestion(Question)}.
 * As such, adding and updating of
 * questions uses Question#isSameQuestion(Question) for equality so as to ensure
 * that the question being added or updated is
 * unique in terms of identity in the UniqueQuestionList. However, the removal of
 * a question uses Question#equals(Object) so
 * as to ensure that the question with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Question#isSameQuestion(Question)
 */
public class UniqueQuestionList implements Iterable<Question> {

    private final ObservableList<Question> internalList = FXCollections.observableArrayList();
    private final ObservableList<Question> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent question as the given argument.
     */
    public boolean contains(Question toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameQuestion);
    }

    /**
     * Adds a question to the list.
     * The question must not already exist in the list.
     */
    public void add(Question toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateQuestionException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the question {@code target} in the list with {@code editedQuestion}.
     * {@code target} must exist in the list.
     * The question identity of {@code editedQuestion} must not be the same as another existing question in the list.
     */
    public void setQuestion(Question target, Question editedQuestion) {
        requireAllNonNull(target, editedQuestion);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new QuestionNotFoundException();
        }

        if (!target.isSameQuestion(editedQuestion) && contains(editedQuestion)) {
            throw new DuplicateQuestionException();
        }

        internalList.set(index, editedQuestion);
    }

    /**
     * Removes the equivalent question from the list.
     * The question must exist in the list.
     */
    public void remove(Question toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new QuestionNotFoundException();
        }
    }

    public void setQuestions(UniqueQuestionList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code questions}.
     * {@code questions} must not contain duplicate questions.
     */
    public void setQuestions(List<Question> questions) {
        requireAllNonNull(questions);
        if (!questionsAreUnique(questions)) {
            throw new DuplicateQuestionException();
        }

        internalList.setAll(questions);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Question> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Question> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueQuestionList
                        // instanceof handles nulls
                        && internalList.equals(((UniqueQuestionList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code questions} contains only unique questions.
     */
    private boolean questionsAreUnique(List<Question> questions) {
        for (int i = 0; i < questions.size() - 1; i++) {
            for (int j = i + 1; j < questions.size(); j++) {
                if (questions.get(i).isSameQuestion(questions.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
