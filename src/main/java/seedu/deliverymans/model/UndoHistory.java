package seedu.deliverymans.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Tracks history of an object for undo and redo.
 *
 * @param <T> Type of the object to track.
 */
class UndoHistory<T> {
    private List<State> history = new ArrayList<>();
    private int current;

    UndoHistory(T addressBook) {
        history.add(new State(null, addressBook));
    }

    /**
     * Notifies this undo history that the object may have been changed.
     *
     * An entry in the history is only added if the object is not equal to the previous object
     * according to its equals method.
     *
     * @param name Name of the action changing the object.
     * @param addressBook New version of the object.
     */
    void notifyChange(String name, T addressBook) {
        if (!addressBook.equals(history.get(current).getData())) {
            if (hasRedo()) {
                history.subList(current + 1, history.size()).clear();
            }
            history.get(current).subsequentCause = name;
            current++;
            history.add(new State(name, addressBook));
        }
    }

    boolean hasUndo() {
        return current != 0;
    }

    boolean hasRedo() {
        return current != history.size() - 1;
    }

    State undo() {
        current--;
        return history.get(current);
    }

    State redo() {
        current++;
        return history.get(current);
    }

    List<State> getStateList() {
        return List.copyOf(history);
    }

    int getCurrentPosition() {
        return current;
    }

    int size() {
        return history.size();
    }

    State undoTill(int position) {
        current = position;
        return history.get(current);
    }

    /**
     * Wrapper for the action causing the change to the object, the subsequent action, and the
     * object itself at this state.
     */
    class State {
        private String cause;
        private String subsequentCause;
        private T data;

        private State(String cause, T data) {
            this.cause = cause;
            this.data = data;
        }

        String getCause() {
            return cause;
        }

        String getSubsequentCause() {
            return subsequentCause;
        }

        public T getData() {
            return data;
        }
    }
}
