package seedu.address.model;

public interface ElisaStateHistory {
    public void pushCommand(ElisaState currentState);
    public ElisaState popCommand();
    public ElisaState peekCommand();
}
