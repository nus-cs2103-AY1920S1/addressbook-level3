package seedu.address.model;

/**
 * interface for state history
 */

public interface ElisaStateHistory {
    public void pushCommand(ElisaState currentState);

    public ElisaState popCommand();

    public ElisaState peekCommand();

    public int size();
}
