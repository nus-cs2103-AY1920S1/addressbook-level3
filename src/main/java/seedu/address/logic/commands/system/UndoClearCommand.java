package seedu.address.logic.commands.system;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.result.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAccommodation;
import seedu.address.model.ReadOnlyActivity;
import seedu.address.model.ReadOnlyContact;
import seedu.address.model.ReadOnlyItinerary;

/**
 * A command that is not usable by a User, only exist to assist in undoing the effects of Clear command.
 */
public class UndoClearCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "undoclear";
    public static final String MESSAGE_SUCCESS = "Clear command was successfully undone.";

    private final ReadOnlyAccommodation accommodation;
    private final ReadOnlyActivity activity;
    private final ReadOnlyContact contact;
    private final ReadOnlyItinerary itinerary;

    public UndoClearCommand(ReadOnlyAccommodation accommodation, ReadOnlyActivity activity,
                            ReadOnlyContact contact, ReadOnlyItinerary itinerary) {
        this.accommodation = accommodation;
        this.activity = activity;
        this.contact = contact;
        this.itinerary = itinerary;
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAccommodations(accommodation);
        model.setActivities(activity);
        model.setContacts(contact);
        model.setItinerary(itinerary);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
