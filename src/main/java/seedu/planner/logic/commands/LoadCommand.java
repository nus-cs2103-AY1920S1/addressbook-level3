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
import seedu.planner.model.Model;
import seedu.planner.model.field.Name;

/**
 * Loads an existing trip.
 */
public class LoadCommand extends Command {

    public static final String COMMAND_WORD = "load";
    public static final String DIRECTORY_OPS_ERROR_MESSAGE = "Could not list files in planner: ";
    public static final String MESSAGE_NO_NAME = "Planner name not specified";
    public static final String NO_PLANNER_MESSAGE = "This planner has not been saved";
    public static final String EMPTY_PLANNER_MESSAGE = "This planner does not have any files";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD,
            ": Loads an existing Planner with given name.",
            COMMAND_WORD + " " + PREFIX_NAME + "NAME",
            COMMAND_WORD + " " + PREFIX_NAME + "Greenland"
    );

    public static final CommandInformation COMMAND_INFORMATION = new CommandInformation(
            COMMAND_WORD,
            Arrays.asList(PREFIX_NAME.toString()),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>()
    );

    public static final String MESSAGE_SUCCESS = "Planner loaded!";

    private final Name name;

    /**
     * Creates a LoadCommand to load the specified planner
     */
    public LoadCommand(Name name) {
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Path newPlannerFilePath = model.getPlannerFilePath().resolveSibling(this.name.name);

        if (!Files.exists(newPlannerFilePath)) {
            throw new CommandException(NO_PLANNER_MESSAGE);
        }

        try {
            if (Files.list(newPlannerFilePath).findAny().isEmpty()) {
                throw new CommandException(EMPTY_PLANNER_MESSAGE);
            }
        } catch (IOException ioe) {
            throw new CommandException(DIRECTORY_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        model.setPlannerFilePath(newPlannerFilePath.getFileName());
        model.setItineraryName(this.name);

        return new CommandResult(
                MESSAGE_SUCCESS,
                new UiFocus[] {UiFocus.AGENDA}
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoadCommand // instanceof handles nulls
                && this.name.equals(((LoadCommand) other).name));
    }
}
