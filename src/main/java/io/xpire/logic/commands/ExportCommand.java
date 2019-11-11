package io.xpire.logic.commands;

import static io.xpire.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import io.xpire.commons.util.StringUtil;
import io.xpire.logic.commands.exceptions.CommandException;
import io.xpire.model.Model;
import io.xpire.model.item.Item;
import io.xpire.model.state.StateManager;
import javafx.collections.ObservableList;

//@@author JermyTan
/**
 * Exports the list through a QR code.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_SHORTHAND = "ex";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the current list through a QR code.\n"
            + "Example: " + COMMAND_WORD;

    /** Message to be shown to the user. */
    public static final String SHOWING_EXPORT_MESSAGE = "QR code generated.";

    /** Pretty formatting of the exported data. */
    public static final String BORDER = "* * * * * * * * * * * * * * * * * * * * * * * * *\n";

    /** Resolution size of the QR code image. */
    public static final int RESOLUTION_SIZE = 800;

    @Override
    public CommandResult execute(Model model, StateManager stateManager) throws CommandException {
        requireAllNonNull(model, stateManager);
        this.requireNonEmptyCurrentList(model);

        ObservableList<? extends Item> currentList = model.getCurrentList();
        String formattedOutput = listToString(currentList);
        byte[] pngData = StringUtil.getQrCode(formattedOutput, RESOLUTION_SIZE);

        return new CommandResult(SHOWING_EXPORT_MESSAGE, true, pngData);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else {
            return obj instanceof ExportCommand;
        }
    }

    /**
     * Converts the item list to its string representation.
     *
     * @param list Item list.
     * @return String representation of item list.
     */
    private String listToString(List<? extends Item> list) {
        StringBuilder formattedOutput = new StringBuilder(BORDER);
        for (int index = 1; index <= list.size(); index++) {
            formattedOutput.append(String.format("%d. %s\n", index, list.get(index - 1).toString()));
            formattedOutput.append(BORDER);
        }
        return formattedOutput.toString();
    }
}
