package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTIVITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandSubType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Context;
import seedu.address.model.Model;
import seedu.address.model.activity.Activity;
import seedu.address.model.person.Person;

/**
 * Updates the GUI to list all entries of a specified type to the user.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Switches the current view to show the details of a contact or activity, "
            + "identified by their display index (a positive integer) in the respective list.\n"
            + "Parameters: " + PREFIX_CONTACT + "CONTACT_INDEX OR " + PREFIX_ACTIVITY + "ACTIVITY_INDEX\n"
            + "Example: view " + PREFIX_CONTACT + "1";

    public static final String MESSAGE_SUCCESS = "Showing the details of %s %s";
    public static final String MESSAGE_UNKNOWN_VIEW_TYPE = "View command has unknown type!";

    private final Index targetIndex;
    private final CommandSubType type;

    public ViewCommand(CommandSubType type, Index targetIndex) {
        requireAllNonNull(type, targetIndex);
        this.type = type;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Contextual behaviour
        switch (this.type) {
        case CONTACT:
            List<Person> listedPersons = model.getFilteredPersonList();

            if (targetIndex.getOneBased() > listedPersons.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAY_INDEX);
            }

            Person personToView = listedPersons.get(targetIndex.getZeroBased());
            Context newContactContext = new Context(personToView);
            model.setContext(newContactContext);

            return new CommandResult(String.format(MESSAGE_SUCCESS, "contact", personToView.getName()),
                    newContactContext);
        case ACTIVITY:
            List<Activity> listedActivities = model.getFilteredActivityList();

            if (targetIndex.getOneBased() > listedActivities.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_ACTIVITY_DISPLAY_INDEX);
            }

            Activity activityToView = listedActivities.get(targetIndex.getZeroBased());
            Context newActivityContext = new Context(activityToView);
            model.setContext(newActivityContext);

            return new CommandResult(String.format(MESSAGE_SUCCESS, "activity", activityToView.getTitle()),
                    newActivityContext);
        default:
            throw new CommandException(MESSAGE_UNKNOWN_VIEW_TYPE);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand)) {
            return false;
        }

        // state check
        ViewCommand v = (ViewCommand) other;
        return type.equals(v.type)
            && targetIndex.equals(v.targetIndex);
    }
}
