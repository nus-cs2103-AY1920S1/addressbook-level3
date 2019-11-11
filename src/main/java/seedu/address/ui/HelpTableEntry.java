package seedu.address.ui;

import javafx.beans.property.SimpleStringProperty;

/**
 * Represents the data for each row in the table within the {@code HelpWindow}.
 */
public class HelpTableEntry {
    private final SimpleStringProperty command;
    private final SimpleStringProperty usage;

    HelpTableEntry(String command, String usage) {
        this.command = new SimpleStringProperty(command);
        this.usage = new SimpleStringProperty(usage);
    }

    public String getCommand() {
        return command.get();
    }

    public String getUsage() {
        return usage.get();
    }
}
