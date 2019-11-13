package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.planner.logic.autocomplete.CommandInformation;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.logic.commands.util.HelpExplanation;
import seedu.planner.model.AccommodationManager;
import seedu.planner.model.ActivityManager;
import seedu.planner.model.ContactManager;
import seedu.planner.model.Itinerary;
import seedu.planner.model.Model;

//@@author OneArmyj
/**
 * Clears the planner.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Data has been cleared!";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD,
            "Clear all data from the program.",
            COMMAND_WORD,
            COMMAND_WORD
    );

    public static final CommandInformation COMMAND_INFORMATION = new CommandInformation(COMMAND_WORD);
    private final boolean isUndoRedo;

    public ClearCommand(boolean isUndoRedo) {
        this.isUndoRedo = isUndoRedo;
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (!isUndoRedo) {
            updateEventStack(this, model);
        }

        model.setContacts(new ContactManager());
        model.setAccommodations(new AccommodationManager());
        model.setActivities(new ActivityManager());
        model.setItinerary(new Itinerary(model.getName()));

        return new CommandResult(MESSAGE_SUCCESS, new UiFocus[]{UiFocus.AGENDA});
    }
}
