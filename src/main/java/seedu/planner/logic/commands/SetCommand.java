package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
 * Sets the name and start date of the trip.
 */
public class SetCommand extends Command {

    public static final String COMMAND_WORD = "set";
    public static final String DIRECTORY_OPS_ERROR_MESSAGE = "Could not list files in planner: ";
    public static final String DUPLICATE_PLANNER_MESSAGE = "This planner already exists";
    public static final String MESSAGE_NOTHING_TO_SET = "Neither name nor start date is being set.";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD,
            ": Set the Planner's name and trip start date.",
            COMMAND_WORD + " "
                    + "[" + PREFIX_NAME + "NAME] "
                    + "[" + PREFIX_START_DATE + "START_DATE]",
            COMMAND_WORD + " " + PREFIX_NAME + "An amazing trip to Germany "
                    + PREFIX_START_DATE + "23-7-2020"
    );

    public static final CommandInformation COMMAND_INFORMATION = new CommandInformation(
            COMMAND_WORD,
            new ArrayList<>(),
            new ArrayList<>(),
            Arrays.asList(PREFIX_NAME.toString(), PREFIX_START_DATE.toString()),
            new ArrayList<>()
    );

    public static final String MESSAGE_SUCCESS = "Settings of planner changed!";
    public static final String MESSAGE_NAME_IS_TOO_LONG = "Please keep the name of the planner equal to or "
            + "under 30 characters";
    public static final int MAX_NAME_LENGTH = 30;

    private final Name name;
    private final LocalDate startDate;

    /**
     * Creates an AddContactCommand to add the specified {@code Person}
     */
    public SetCommand(Name name, LocalDate date) {
        this.name = name;
        this.startDate = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!(name == null)) {
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
            model.setItineraryName(this.name);
        }
        if (!(startDate == null)) {
            LocalDate oldStartDate = model.getStartDate();
            long differenceInDaysBetweenOldAndNew = ChronoUnit.DAYS.between(oldStartDate, startDate);
            model.setItineraryStartDate(this.startDate);
            model.shiftDatesInItineraryByDay(differenceInDaysBetweenOldAndNew);
        }
        return new CommandResult(
                MESSAGE_SUCCESS,
                new UiFocus[] {UiFocus.AGENDA}
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SetCommand // instanceof handles nulls
                && this.name.equals(((SetCommand) other).name)
                && this.startDate.equals(((SetCommand) other).startDate));
    }
}
