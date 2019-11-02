package seedu.address.logic.commands.wordbankcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILEPATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WORD;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Removes a word bank identified using it's unique name.
 */
public class ImportCommand extends WordBankCommand {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " w/WORDBANK f/FOLDER\n"
            + "Eg: " + COMMAND_WORD + " "
            + PREFIX_WORD + "sample "
            + PREFIX_FILEPATH + "~/downloads";

    public static final String MESSAGE_IMPORT_CARD_SUCCESS = "Imported word bank: %1$s from location : %2$s";

    private static String wordBankName;
    private static File directory;

    public ImportCommand(String wordBankName, File directory) {
        this.wordBankName = wordBankName;
        this.directory = directory;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Path finalPath = Paths.get(directory.toString(), wordBankName + ".json");

        Path p1 = Paths.get(directory.toString(), wordBankName + ".json");
//        File f =

        return new ImportCommandResult(String.format(MESSAGE_IMPORT_CARD_SUCCESS, wordBankName, directory),
                this.directory.toPath(), wordBankName);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                // instanceof handles nulls
                || (other instanceof seedu.address.logic.commands.wordbankcommands.ImportCommand
                && wordBankName
                .equals(((seedu.address.logic.commands.wordbankcommands.ImportCommand) other).wordBankName));
    }

    public static File getDirectory() {
        return directory;
    }

    public static String getWordBankName() {
        return wordBankName;
    }
}
