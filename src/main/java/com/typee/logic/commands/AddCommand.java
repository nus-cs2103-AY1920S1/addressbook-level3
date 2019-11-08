package com.typee.logic.commands;

import static com.typee.logic.parser.CliSyntax.PREFIX_ATTENDEES;
import static com.typee.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static com.typee.logic.parser.CliSyntax.PREFIX_END_TIME;
import static com.typee.logic.parser.CliSyntax.PREFIX_ENGAGEMENT_TYPE;
import static com.typee.logic.parser.CliSyntax.PREFIX_LOCATION;
import static com.typee.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static com.typee.logic.parser.CliSyntax.PREFIX_START_TIME;
import static java.util.Objects.requireNonNull;

import com.typee.logic.commands.exceptions.CommandException;
import com.typee.model.Model;
import com.typee.model.ReadOnlyEngagementList;
import com.typee.model.engagement.Engagement;

/**
 * Adds an engagement to the engagement list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an engagement to the engagement list. "
            + "Parameters: "
            + PREFIX_ENGAGEMENT_TYPE + "ENGAGEMENT_TYPE "
            + PREFIX_START_TIME + "START TIME "
            + PREFIX_END_TIME + "END TIME "
            + PREFIX_LOCATION + "LOCATION "
            + PREFIX_ATTENDEES + "ATTENDEES "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_PRIORITY + "PRIORITY"
            + "\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_ENGAGEMENT_TYPE + "Meeting "
            + PREFIX_START_TIME + "18/10/2019/1500 "
            + PREFIX_END_TIME + "18/10/2019/1800 "
            + PREFIX_LOCATION + "COM-2-B1-03 "
            + PREFIX_ATTENDEES + "John, Elijah, Sam "
            + PREFIX_DESCRIPTION + "CS2103T Discussion "
            + PREFIX_PRIORITY + "High";

    public static final String MESSAGE_SUCCESS = "New engagement added: %1$s";
    public static final String MESSAGE_DUPLICATE_ENGAGEMENT = "This engagement clashes with an already existing one";

    private final Engagement toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Engagement}
     */
    public AddCommand(Engagement engagement) {
        requireNonNull(engagement);
        toAdd = engagement;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasEngagement(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_ENGAGEMENT);
        }

        if (isConflicting(model)) {
            throw new CommandException(MESSAGE_DUPLICATE_ENGAGEMENT);
        }

        model.addEngagement(toAdd);
        model.saveEngagementList();
        model.updateSortedEngagementList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }

    private boolean isConflicting(Model model) {
        ReadOnlyEngagementList readOnlyEngagementList = model.getEngagementList();
        return readOnlyEngagementList.isConflictingEngagement(toAdd);
    }
}
