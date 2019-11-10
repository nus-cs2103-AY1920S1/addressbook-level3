// @@author chrischenhui
package seedu.address.logic.commands.wordbankcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILEPATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WORD;

import java.io.File;
import java.nio.file.Paths;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

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

    private String wordBankName;
    private String directoryString;

    public ExportCommand(String wordBankName, File directory) {
        this.wordBankName = wordBankName;
        this.directoryString = directory.toString();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasWordBank(wordBankName)) {
            throw new CommandException(Messages.MESSAGE_NON_EXISTENT_WORD_BANK_NAME);

        } else {
            return new ExportCommandResult(
                    String.format(MESSAGE_EXPORT_CARD_SUCCESS, wordBankName, directoryString),
                    wordBankName, Paths.get(directoryString));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                // instanceof handles nulls
                || (other instanceof seedu.address.logic.commands.wordbankcommands.ExportCommand
                && wordBankName
                .equals(((seedu.address.logic.commands.wordbankcommands.ExportCommand) other).wordBankName));
    }

}
