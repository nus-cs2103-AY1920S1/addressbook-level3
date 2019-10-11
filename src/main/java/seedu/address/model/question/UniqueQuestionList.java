package seedu.address.model.question;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * A list of questions, todo: relevant modification methods will be implemented later.
 */
public class UniqueQuestionList implements Iterable<Question> {
    private final ObservableList<Question> internalList = FXCollections.observableArrayList();
    private final ObservableList<Question> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public Question get(int index) {
        return internalList.get(index);
    }

    @Override
    public Iterator<Question> iterator() {
        return internalList.iterator();
    }
}
