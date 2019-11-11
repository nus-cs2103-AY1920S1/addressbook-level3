package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import seedu.planner.logic.autocomplete.CommandInformation;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.logic.commands.util.HelpExplanation;
import seedu.planner.model.AccommodationManager;
import seedu.planner.model.ActivityManager;
import seedu.planner.model.ContactManager;
import seedu.planner.model.Itinerary;
import seedu.planner.model.Model;
import seedu.planner.model.field.Name;

/**
 * Creates a new trip.
 */
public class NewCommand extends Command {

    public static final String COMMAND_WORD = "new";
    public static final String DIRECTORY_OPS_ERROR_MESSAGE = "Could not list files in planner: ";
    public static final String DUPLICATE_PLANNER_MESSAGE = "This planner already exists";
    public static final String MESSAGE_NO_NAME = "Planner name not specified";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD,
            ": Creates a new Planner with given name.",
            COMMAND_WORD + " " + PREFIX_NAME + "NAME",
            COMMAND_WORD + " " + PREFIX_NAME + "Iceland"
    );

    public static final CommandInformation COMMAND_INFORMATION = new CommandInformation(
            COMMAND_WORD,
            Arrays.asList(PREFIX_NAME.toString()),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>()
    );

    public static final String MESSAGE_SUCCESS = "Planner created!";
    public static final String MESSAGE_NAME_IS_TOO_LONG = "Please keep the name of the planner equal to or "
            + "under 30 characters";
    public static final int MAX_NAME_LENGTH = 30;

    private final Name name;

    /**
     * Creates a NewCommand to add the specified planner
     */
    public NewCommand(Name name) {
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (this.name.name.length() > MAX_NAME_LENGTH) {
            throw new CommandException(MESSAGE_NAME_IS_TOO_LONG);
        }

        Path newPlannerFilePath = model.getPlannerFilePath().resolveSibling(this.name.name);

        try {
            if (Files.exists(newPlannerFilePath) && Files.list(newPlannerFilePath).findAny().isPresent()) {
                throw new CommandException(DUPLICATE_PLANNER_MESSAGE);
            }
        } catch (IOException ioe) {
            throw new CommandException(DIRECTORY_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        model.setPlannerFilePath(newPlannerFilePath.getFileName());
        model.setContacts(new ContactManager());
        model.setAccommodations(new AccommodationManager());
        model.setActivities(new ActivityManager());
        model.setItinerary(new Itinerary(this.name));

        return new CommandResult(
                MESSAGE_SUCCESS,
                new UiFocus[] {UiFocus.AGENDA}
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NewCommand // instanceof handles nulls
                && this.name.equals(((NewCommand) other).name));
    }
}
