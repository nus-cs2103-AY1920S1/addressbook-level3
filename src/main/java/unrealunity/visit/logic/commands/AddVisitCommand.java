package unrealunity.visit.logic.commands;

import static unrealunity.visit.logic.parser.CliSyntax.PREFIX_VISIT;
import static unrealunity.visit.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import unrealunity.visit.commons.core.Messages;
import unrealunity.visit.commons.core.index.Index;
import unrealunity.visit.commons.util.CollectionUtil;
import unrealunity.visit.logic.commands.exceptions.CommandException;
import unrealunity.visit.model.Model;
import unrealunity.visit.model.person.Person;

/**
 * Changes the visitList of an elxisting person in the address book.
 */
public class AddVisitCommand extends Command {
    public static final String COMMAND_WORD = "addvisit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add the visitation record of the person identified "
            + "by the index number used in the last person listing. "
            + "Calls window popup for user to fill in details.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_VISIT + "[DATE i.e. DD/MM/YYYY]\n"
            + "Only dates from year 19xx to 2xxx are accepted\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_VISIT + "01/01/2019";

    public static final String MESSAGE_ADD_VISIT_PROMPT = "Please fill in the form";

    private final Index index;
    private final String date;

    /**
     * @param index of the person in the filtered person list to edit the visitList
     * @param date of the VisitReport
     */
    public AddVisitCommand(Index index, String date) {
        CollectionUtil.requireAllNonNull(index, date);

        this.index = index;
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_ADD_VISIT_PROMPT, personToEdit), index.getOneBased(), date);
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddVisitCommand)) {
            return false;
        }

        // state check
        AddVisitCommand e = (AddVisitCommand) other;
        return index.equals(e.index)
                && date.equals(e.date);
    }
}
