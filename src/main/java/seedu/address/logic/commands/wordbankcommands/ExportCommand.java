package seedu.address.logic.commands.wordbankcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILEPATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WORD;

import java.io.File;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wordbank.WordBank;

/**
 * Removes a word bank identified using it's unique name.
 */
public class ExportCommand extends WordBankCommand {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " w/BANKNAME f/FILEPATH\n"
            + "Eg: " + COMMAND_WORD + " "
            + PREFIX_WORD + "sample "
            + PREFIX_FILEPATH + "~/downloads";

    private static final String MESSAGE_EXPORT_CARD_SUCCESS = "Exported word bank: %1$s to location : %2$s";

    private static String wordBankName;
    private static File directory;
    private static WordBank wordBank;

    public ExportCommand(String wordBankName, File directory) {
        this.wordBankName = wordBankName;
        this.directory = directory;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        WordBank wb = model.getWordBankList().getWordBankFromName(wordBankName);
        this.wordBank = wb;
        if (wb == null) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_WORD_BANK_NAME);
        }

        return new ExportCommandResult(String.format(MESSAGE_EXPORT_CARD_SUCCESS, wordBankName, directory.toString()),
                wordBankName, directory.toPath());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.logic.commands.wordbankcommands.ExportCommand // instanceof handles nulls
                && wordBankName.equals(((seedu.address.logic.commands.wordbankcommands.ExportCommand) other).wordBankName));
    }


    public static WordBank getWordBank() {
        return wordBank;
    }

    public static File getDirectory() {
        return directory;
    }

    public static String getWordBankName() {
        return wordBankName;
    }
}
