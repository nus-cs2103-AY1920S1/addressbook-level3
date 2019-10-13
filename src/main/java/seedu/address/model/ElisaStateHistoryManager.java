package seedu.address.model;

import java.util.Stack;

public class ElisaStateHistoryManager implements ElisaStateHistory {
    private Stack<ElisaState> undoStack;

    @Override
    public void pushCommand(ElisaState currentState) {
        undoStack.push(currentState);
    }

    @Override
    public ElisaState popCommand() {
        return undoStack.pop();
    }

    @Override
    public ElisaState peekCommand() {
        return undoStack.peek();
    }
}
