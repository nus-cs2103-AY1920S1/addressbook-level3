package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILENAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEXES;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.importexport.CsvUtil;
import seedu.address.importexport.exceptions.ExportingException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Exports Person data from the AddressBook
 */

public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports persons data into a .csv file in /exports.\n"
            + "Persons are exported selectively by index.\n"
            + "If indexes are not provided, all persons will be exported.\n"
            + "File name must be new - overriding an existing file is not permitted.\n"
            + "Parameters: [" + PREFIX_FILENAME + "FILENAME] "
            + "[" + PREFIX_INDEXES + "INDEXES]...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_FILENAME + "patient_data "
            + PREFIX_INDEXES + "2 " + PREFIX_INDEXES + "4 " + PREFIX_INDEXES + "6";

    public static final String MESSAGE_SUCCESS = "Export success! File written to: ";
    public static final String MESSAGE_FAILURE = "Export failed.";
    public static final String MESSAGE_EMPTY = "Nothing to export!";
    public static final String MESSAGE_FILE_EXISTS =
            "File name already in use. Please delete the existing file or use a new file name";

    private final String exportFileName;
    private final Optional<Set<Index>> targetIndexes;

    /**
     * @param exportFileName of the .csv to create and export to
     * @param targetIndexes of the persons to selectively export, if any
     */
    public ExportCommand(String exportFileName, Optional<Set<Index>> targetIndexes) {
        this.exportFileName = exportFileName;
        this.targetIndexes = targetIndexes;
    }

    /**
     * export all persons specified by {@Code targetIndexes}
     * if no indexes are provided, all persons data are exported.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getStagedPersonList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY);
        }

        // Selectively export if indexes are provided
        if (targetIndexes.isPresent()) {

            if (!indexesAllInBounds(targetIndexes.get(), lastShownList.size())) {
                // If any index is out of bounds, throw an exception
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            // Else get a list of persons at the specified indexes
            lastShownList = model.getPersonsByIndexes(targetIndexes.get());
        }

        String pathString;
        try {
            pathString = CsvUtil.writePersonsToCsv(lastShownList, exportFileName);
        } catch (ExportingException e) {
            throw new CommandException(MESSAGE_FILE_EXISTS);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        return new CommandResult(MESSAGE_SUCCESS + pathString);
    }

    /**
     * Returns true if indexes in the set are all within bounds.
     */
    private boolean indexesAllInBounds(Set<Index> indexes, int bound) {
        for (Index index : indexes) {
            if (index.getZeroBased() >= bound) {
                return false;
            }
        }
        return true;
    }
}
