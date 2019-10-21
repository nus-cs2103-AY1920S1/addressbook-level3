package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.util.List;

import seedu.address.export.CsvUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Exports all Person data from the AddressBook
 */

public class ExportAllCommand extends Command {

    public static final String COMMAND_WORD = "export-all";

    public static final String MESSAGE_SUCCESS = "Export success!";
    public static final String MESSAGE_FAILURE = "Export failed.";

    /**
     * v1.2 implementation: export all persons on the currently shown list
     * @param model {@code Model} which the command should operate on.
     * @return
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getStagedPersonList();
        try {
            CsvUtil.writePersonsToCsv(lastShownList);
        } catch (IOException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
