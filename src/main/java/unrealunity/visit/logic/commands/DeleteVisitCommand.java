package unrealunity.visit.logic.commands;

import static unrealunity.visit.logic.parser.CliSyntax.PREFIX_DELETE_VISIT;
import static unrealunity.visit.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import javafx.collections.ObservableList;
import unrealunity.visit.commons.core.Messages;
import unrealunity.visit.commons.core.index.Index;
import unrealunity.visit.commons.util.CollectionUtil;
import unrealunity.visit.logic.commands.exceptions.CommandException;
import unrealunity.visit.model.Model;
import unrealunity.visit.model.person.Person;
import unrealunity.visit.model.person.VisitReport;

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
    public static final String MESSAGE_MISSING_INDEX_PROMPT = "Please specify index of report to be deleted\n"
            + "Usage: " + COMMAND_WORD + " [PERSON INDEX] "
            + PREFIX_DELETE_VISIT + "[REPORT INDEX]";;

    private static final int EMPTY_REPORT_INDICATOR = -1;

    private final Index index;
    private final int id;

    /**
     * @param index of the person in the filtered person list to edit the visitList
     * @param id of the report to be deleted
     */
    public DeleteVisitCommand(Index index, int id) {
        CollectionUtil.requireAllNonNull(index);

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
        if (id != EMPTY_REPORT_INDICATOR) {
            try {
                editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                        personToEdit.getAddress(), personToEdit.getVisitList().deleteRecord(id),
                        personToEdit.getTags());

                model.setPerson(personToEdit, editedPerson);
            } catch (IndexOutOfBoundsException e) {
                throw new CommandException(Messages.MESSAGE_INVALID_REPORT_INDEX);
            }
        } else {
            ObservableList<VisitReport> result = editedPerson.getVisitList().getObservableRecords();
            if (result.isEmpty()) {
                return new CommandResult("", result);
            } else {
                return new CommandResult(MESSAGE_MISSING_INDEX_PROMPT, result);
            }
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
