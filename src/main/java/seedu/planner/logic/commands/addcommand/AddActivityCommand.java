package seedu.planner.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.planner.logic.commands.util.CommandUtil.findIndexOfActivity;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_COST;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.autocomplete.CommandInformation;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.ResultInformation;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.logic.commands.util.HelpExplanation;
import seedu.planner.model.Model;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.contact.Contact;
import seedu.planner.model.field.Cost;

/**
 * Adds an activity to the itinerary.
 */
public class AddActivityCommand extends AddCommand {

    public static final String SECOND_COMMAND_WORD = "activity";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "Adds an Activity to the itinerary.",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " "
                    + PREFIX_NAME + "NAME "
                    + PREFIX_ADDRESS + "ADDRESS "
                    + PREFIX_DURATION + "DURATION "
                    + "[" + PREFIX_COST + "COST] "
                    + "[" + PREFIX_PRIORITY + "PRIORITY] "
                    + "[" + PREFIX_TAG + "TAG]...",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " "
                    + PREFIX_NAME + "visit mt Fuji "
                    + PREFIX_ADDRESS + "Mount Fuji "
                    + PREFIX_DURATION + "120 "
                    + PREFIX_TAG + "sightseeing"
    );

    public static final CommandInformation COMMAND_INFORMATION = new CommandInformation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            Arrays.asList(PREFIX_NAME.toString(), PREFIX_ADDRESS.toString(), PREFIX_DURATION.toString()),
            new ArrayList<>(),
            Arrays.asList(PREFIX_PHONE.toString(), PREFIX_COST.toString(), PREFIX_PRIORITY.toString()),
            Arrays.asList(PREFIX_TAG.toString())
    );

    public static final String MESSAGE_SUCCESS = "New activity added: %1s";
    public static final String MESSAGE_DUPLICATE_ACTIVITY = "This activity already exists in the itinerary.";

    private final Index index;
    private final Activity toAdd;
    private final boolean isUndoRedo;

    /**
     * Creates an AddActivityCommand to add the specified {@Activity}
     */
    public AddActivityCommand(Activity activity, boolean isUndoRedo) {
        requireNonNull(activity);
        toAdd = activity;
        index = null;
        this.isUndoRedo = isUndoRedo;
    }

    // Constructor used to undo DeleteActivityEvent
    public AddActivityCommand(Index index, Activity activity) {
        requireAllNonNull(index, activity);
        toAdd = activity;
        this.index = index;
        this.isUndoRedo = true;
    }

    public Activity getToAdd() {
        return toAdd;
    }

    @Override
    public String getSecondCommandWord() {
        return SECOND_COMMAND_WORD;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Activity activityAdded;
        if (model.hasActivity(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACTIVITY);
        }

        // Check if new Activity's contact already exist in ContactManager's list. If true, use the existing
        // contact.
        if (toAdd.getContact().isPresent() && model.hasContact(toAdd.getContact().get())) {
            Contact contact = toAdd.getContact().get();
            Cost cost = toAdd.getCost().isPresent() ? toAdd.getCost().get() : null;
            activityAdded = new Activity(toAdd.getName(), toAdd.getAddress(), contact, cost, toAdd.getTags(),
                    toAdd.getDuration(), toAdd.getPriority());
        } else {
            activityAdded = toAdd;
        }

        if (index == null && !isUndoRedo) {
            // Not due to undo or redo method
            AddActivityCommand newCommand = new AddActivityCommand(activityAdded, isUndoRedo);
            updateEventStack(newCommand, model);
            model.addActivity(activityAdded);
        } else if (isUndoRedo && index != null) {
            // Due to undo method of DeleteActivityEvent
            model.addActivityAtIndex(index, activityAdded);
        } else {
            // Due to redo method of AddActivityEvent
            model.addActivity(activityAdded);
        }

        return new CommandResult(
                String.format(MESSAGE_SUCCESS, toAdd),
                new ResultInformation[]{
                    new ResultInformation(
                            toAdd,
                                findIndexOfActivity(model, toAdd),
                                String.format(MESSAGE_SUCCESS, "")
                        )
                },
                new UiFocus[]{UiFocus.ACTIVITY, UiFocus.INFO}
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddActivityCommand
                && toAdd.equals(((AddActivityCommand) other).toAdd));
    }
}
