package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AccommodationManager;
import seedu.address.model.ActivityManager;
import seedu.address.model.ContactManager;
import seedu.address.model.Itinerary;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Data has been cleared!";

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAccommodations(new AccommodationManager());
        model.setActivities(new ActivityManager());
        model.setContacts(new ContactManager());
        model.setItinerary(new Itinerary());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
