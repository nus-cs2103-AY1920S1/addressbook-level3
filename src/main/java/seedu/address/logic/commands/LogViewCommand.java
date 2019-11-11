package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_LOG_VIEW_SUCCESS;

import java.util.Objects;

import seedu.address.logic.UiManager;
import seedu.address.ui.UserOutput;

//@@author Kyzure
/**
 * Represents a Command which adds an EventSource to the Model.
 */
public class LogViewCommand extends Command {

    private final UiManager uiManager;

    LogViewCommand(LogViewCommandBuilder builder) {
        uiManager = Objects.requireNonNull(builder.getUiManager());
    }

    public static CommandBuilder newBuilder(UiManager uiManager) {
        return new LogViewCommandBuilder(uiManager).init();
    }

    @Override
    public UserOutput execute() {
        uiManager.viewLog();
        return new UserOutput(String.format(MESSAGE_LOG_VIEW_SUCCESS));
    }
}
