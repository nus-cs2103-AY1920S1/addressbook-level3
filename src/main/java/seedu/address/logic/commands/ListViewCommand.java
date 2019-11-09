package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_LIST_VIEW_SUCCESS;

import java.util.Objects;

import seedu.address.logic.UiManager;
import seedu.address.ui.UserOutput;

//@@author Kyzure
/**
 * Represents a Command which adds an EventSource to the Model.
 */
public class ListViewCommand extends Command {

    private final UiManager uiManager;

    ListViewCommand(ListViewCommandBuilder builder) {
        uiManager = Objects.requireNonNull(builder.getUiManager());
    }

    public static CommandBuilder newBuilder(UiManager uiManager) {
        return new ListViewCommandBuilder(uiManager).init();
    }

    @Override
    public UserOutput execute() {
        uiManager.viewList();
        return new UserOutput(String.format(MESSAGE_LIST_VIEW_SUCCESS));
    }
}
