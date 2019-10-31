package io.xpire.logic.commands;

import io.xpire.commons.util.StringUtil;
import io.xpire.model.Model;
import io.xpire.model.item.Item;
import javafx.collections.ObservableList;

public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_SHORTHAND = "ex";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the current list to a QR code.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_EXPORT_MESSAGE = "QR code generated.";

    @Override
    public CommandResult execute(Model model) {
        ObservableList<? extends Item> currentList = model.getCurrentFilteredItemList();
        return new CommandResult(SHOWING_EXPORT_MESSAGE, true, StringUtil.getQrCode(currentList.toString(), 500));
    }
}
