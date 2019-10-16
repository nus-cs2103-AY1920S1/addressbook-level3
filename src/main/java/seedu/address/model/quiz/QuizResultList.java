package seedu.address.model.quiz;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class QuizResultList implements Iterable<QuizResult> {
    private final ObservableList<QuizResult> internalList = FXCollections.observableArrayList();
    private final ObservableList<QuizResult> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public void add(QuizResult quizResult) {
        requireNonNull(quizResult);
        internalList.add(quizResult);
    }

    public QuizResult get(int index) {
        return internalList.get(index);
    }

    public ObservableList<QuizResult> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<QuizResult> iterator() {
        return internalList.iterator();
    }
}
