package seedu.address.logic.commands.loadCommands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FILEPATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WORD;
import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.LoadCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wordbank.WordBank;

import java.io.File;


/**
 * Removes a word bank identified using it's unique name.
 */
public class ExportCommand extends LoadCommand {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Export the word bank selected to a folder.\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_WORD + "sample "
            + PREFIX_FILEPATH + "~/downloads";

    public static final String MESSAGE_EXPORT_CARD_SUCCESS = "Exported word bank: %1$s to location : %2$s";

    public static String wordBankName;
    public static File directory;
    public static WordBank wordBank;

    public ExportCommand(String wordBankName, File directory) {
        this.wordBankName = wordBankName;
        this.directory = directory;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        WordBank wb = model.getWordBankList().getWordBank(wordBankName);
        this.wordBank = wb;
        if (wb == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_NAME);
        }

        return new CommandResult(String.format(MESSAGE_EXPORT_CARD_SUCCESS, wordBankName, directory));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.logic.commands.loadCommands.ExportCommand // instanceof handles nulls
                && wordBankName.equals(((seedu.address.logic.commands.loadCommands.ExportCommand) other).wordBankName)); // state check
    }
}