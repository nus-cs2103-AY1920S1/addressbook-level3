package io.xpire.logic.commands;

import io.xpire.model.Model;

public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_SHORTHAND = "ex";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports the current list to a QR code.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_EXPORT_MESSAGE = "QR code generated.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_EXPORT_MESSAGE, true);
    }
}
