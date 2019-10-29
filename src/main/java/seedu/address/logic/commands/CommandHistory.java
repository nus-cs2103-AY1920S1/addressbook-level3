package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.Optional;

public class CommandHistory {
    private static ArrayList<Command> commandHistory = new ArrayList<>();

    public void addCommand(Command commandToAdd) {
        this.commandHistory.add(commandToAdd);
    }

    public static Optional<Command> getLastCommand() {
        int size = commandHistory.size();
        if (commandHistory.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(commandHistory.get(size - 1));
        }
    }

}
