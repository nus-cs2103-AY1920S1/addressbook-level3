package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_VISIT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes record of patient by index.
 */
public class DeleteVisitCommand extends Command {
    public static final String COMMAND_WORD = "deletevisit";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the visitation record of the person identified "
            + "by the index number used in the last person listing.\n "
            + COMMAND_WORD + "[PERSON INDEX]"
            + PREFIX_DELETE_VISIT + "[REPORT INDEX]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DELETE_VISIT + "2";

    public static final String MESSAGE_DELETE_VISIT_SUCCESS = "Removed visit from Person: %1$s";
    public static final String MESSAGE_MISSING_INDEX_PROMPT = "Please specify index of report to be deleted";

    private final Index index;
    private final int id;

    /**
     * @param index of the person in the filtered person list to edit the visitList
     * @param id of the report to be deleted
     */
    public DeleteVisitCommand(Index index, int id) {
        requireAllNonNull(index);

        this.index = index;
        this.id = id;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = personToEdit;
        if (id != -1) {
            try {
                editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                        personToEdit.getAddress(), personToEdit.getVisitList().deleteRecord(id),
                        personToEdit.getTags());

                model.setPerson(personToEdit, editedPerson);
            } catch (IndexOutOfBoundsException e) {
                throw new CommandException(Messages.MESSAGE_INVALID_REPORT_INDEX);
            }
        } else {
            return new CommandResult(MESSAGE_MISSING_INDEX_PROMPT, editedPerson.getVisitList().getObservableRecords());
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_DELETE_VISIT_SUCCESS,
                personToEdit), editedPerson.getVisitList().getObservableRecords());
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteVisitCommand)) {
            return false;
        }

        // state check
        DeleteVisitCommand e = (DeleteVisitCommand) other;
        return index.equals(e.index)
                && id == e.id;
    }
}
