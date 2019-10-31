package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.scene.image.WritableImage;

/**
 * Represents a printable command result which carries the target file path
 */
public class PrintableCommandResult extends CommandResult {
    private static String targetFilePath;

    public PrintableCommandResult(String feedbackToUser) {
        super(feedbackToUser);
    }

    public PrintableCommandResult(String feedbackToUser, CommandResultType commandResultType) {
        super(feedbackToUser, commandResultType);
    }

    public PrintableCommandResult(String feedbackToUser, CommandResultType commandResultType,
                                  String targetFilePath) {
        super(feedbackToUser, commandResultType);
        this.targetFilePath = targetFilePath;
    }

    public String getTargetFilePath() {
        return this.targetFilePath;
    }
}
