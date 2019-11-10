package seedu.planner.logic.commands.system;

import static java.util.Objects.requireNonNull;

import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.model.Model;
import seedu.planner.model.ReadOnlyAccommodation;
import seedu.planner.model.ReadOnlyActivity;
import seedu.planner.model.ReadOnlyContact;
import seedu.planner.model.ReadOnlyItinerary;
//@@author OneArmyj
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
        model.setContacts(contact);
        model.setAccommodations(accommodation);
        model.setActivities(activity);
        model.setItinerary(itinerary);
        return new CommandResult(
                MESSAGE_SUCCESS,
                new UiFocus[] {UiFocus.AGENDA}
        );
    }
}
