package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;

import java.io.File;
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
 * Copies an existing trip.
 */
public class CopyToCommand extends Command {
    public static final String COMMAND_WORD = "copyto";
    public static final String DUPLICATE_PLANNER_MESSAGE = "This planner already exists";
    public static final String MESSAGE_NO_NAME = "Planner name not specified";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD,
            ": Creates a copy of the current Planner with given name.",
            COMMAND_WORD + " " + PREFIX_NAME + "NAME",
            COMMAND_WORD + " " + PREFIX_NAME + "London"
    );

    public static final CommandInformation COMMAND_INFORMATION = new CommandInformation(
            COMMAND_WORD,
            Arrays.asList(PREFIX_NAME.toString()),
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>()
    );

    public static final String MESSAGE_SUCCESS = "Planner copied!";
    public static final String MESSAGE_NAME_IS_TOO_LONG = "Please keep the name of the planner equal to or "
            + "under 30 characters";
    public static final int MAX_NAME_LENGTH = 30;

    private final Name name;

    /**
     * Creates a CopytoCommand to copy the current planner
     */
    public CopyToCommand(Name name) {
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (this.name.name.length() > MAX_NAME_LENGTH) {
            throw new CommandException(MESSAGE_NAME_IS_TOO_LONG);
        }

        Path newPlannerFilePath = model.getPlannerFilePath().resolveSibling(this.name.name);
        File newPlannerFile = newPlannerFilePath.toFile();

        if (newPlannerFile.exists() && !newPlannerFile.delete()) {
            throw new CommandException(DUPLICATE_PLANNER_MESSAGE);
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
                || (other instanceof CopyToCommand // instanceof handles nulls
                && this.name.equals(((CopyToCommand) other).name));
    }
}
