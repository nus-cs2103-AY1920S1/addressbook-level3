package unrealunity.visit.logic.commands;

import static unrealunity.visit.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import unrealunity.visit.commons.core.Messages;
import unrealunity.visit.commons.core.index.Index;
import unrealunity.visit.logic.commands.exceptions.CommandException;
import unrealunity.visit.model.Model;
import unrealunity.visit.model.person.Person;
import unrealunity.visit.model.person.VisitReport;

/**
 * Changes the visitList of an existing person in the address book.
 */
public class ProfileCommand extends Command {
    public static final String COMMAND_WORD = "profile";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Opens the detailed profile of the patient identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_VIEW_PROFILE_SUCCESS = "Generated profile view of : %1$s";

    private final Index index;

    /**
     * @param index of the person in the last listing whose profile is to be viewed
     */
    public ProfileCommand(Index index) {
        requireAllNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        // Get person list based on existing filter
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        // Extract the person from list
        Person personProfileToShow = lastShownList.get(index.getZeroBased());

        ObservableList<VisitReport> personReportList = personProfileToShow.getVisitList().getObservableRecords();

        return new CommandResult(String.format(MESSAGE_VIEW_PROFILE_SUCCESS, personProfileToShow),
                personProfileToShow, personReportList);
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProfileCommand)) {
            return false;
        }

        // state check
        ProfileCommand e = (ProfileCommand) other;
        return index.equals(e.index);
    }
}
