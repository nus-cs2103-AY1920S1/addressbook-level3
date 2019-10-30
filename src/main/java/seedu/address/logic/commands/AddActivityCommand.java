package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.contact.Contact;
import seedu.address.model.itineraryitem.activity.Activity;

/**
 * Adds an activity to the itinerary.
 */
public class AddActivityCommand extends AddCommand {

    public static final String SECOND_COMMAND_WORD = "activity";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + SECOND_COMMAND_WORD + " "
            + ": Adds an Activity to the itinerary."
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: add " + COMMAND_WORD + " "
            + PREFIX_NAME + "visit mt Fuji "
            + PREFIX_ADDRESS + "Mount Fuji "
            + PREFIX_TAG + "sight-seeing";

    public static final String MESSAGE_SUCCESS = "New activity added: %1s";
    public static final String MESSAGE_DUPLICATE_ACTIVITY = "This activity already exists in the itinerary.";

    private final Index index;
    private final Activity toAdd;

    /**
     * Creates an AddActivityCommand to add the specified {@Activity}
     */
    public AddActivityCommand(Activity activity) {
        requireNonNull(activity);
        toAdd = activity;
        index = null;
    }

    public AddActivityCommand(Index index, Activity activity) {
        requireAllNonNull(index, activity);
        toAdd = activity;
        this.index = index;
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

        if (model.hasActivity(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ACTIVITY);
        }

        if (toAdd.getContact().isPresent()) {
            if (model.hasPhone(toAdd.getContact().get().getPhone())) {
                Contact contact = model.getContactByPhone(toAdd.getContact().get().getPhone()).get();
                model.addActivity(new Activity(toAdd.getName(), toAdd.getAddress(), contact,
                        toAdd.getTags()));
            } else {
                if (index == null) {
                    model.addActivity(toAdd);
                } else {
                    model.addActivityAtIndex(index, toAdd);
                }
            }
        } else {
            if (index == null) {
                model.addActivity(toAdd);
            } else {
                model.addActivityAtIndex(index, toAdd);
            }
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddActivityCommand
                && toAdd.equals(((AddActivityCommand) other).toAdd));
    }
}
