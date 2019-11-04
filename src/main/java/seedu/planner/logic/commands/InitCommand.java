package seedu.planner.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.logic.commands.util.HelpExplanation;
import seedu.planner.logic.parser.ParserUtil;
import seedu.planner.model.Model;
import seedu.planner.model.field.Name;

/**
 * Adds a person to the address book.
 */
public class InitCommand extends Command {

    public static final String COMMAND_WORD = "init";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD,
            ": Initialises the Planner with a name and trip start date.",
            COMMAND_WORD + " "
                    + PREFIX_NAME + "NAME "
                    + PREFIX_START_DATE + "START_DATE",
            COMMAND_WORD + " " + PREFIX_NAME + "An amazing trip to Germany "
                    + PREFIX_START_DATE + "23-7-2020"
    );
    public static final String MESSAGE_SUCCESS = "Planner initialised with name:%1$s and"
            + " start date:%2$s";
    public static final String MESSAGE_NAME_IS_TOO_LONG = "Please keep the name of the planner equal to or "
            + "under 30 characters";

    private final Name name;
    private final LocalDate startDate;

    /**
     * Creates an AddContactCommand to add the specified {@code Person}
     */
    public InitCommand(Name name, LocalDate date) {
        requireAllNonNull(name, date);
        this.name = name;
        this.startDate = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (this.name.name.length() > 30) {
            throw new CommandException(MESSAGE_NAME_IS_TOO_LONG);
        }

        model.setItineraryName(this.name);
        LocalDate oldStartDate = model.getStartDate();
        long differenceInDaysBetweenOldAndNew = ChronoUnit.DAYS.between(oldStartDate, startDate);
        model.setItineraryStartDate(this.startDate);
        model.shiftDatesInItineraryByDay(differenceInDaysBetweenOldAndNew);
        String dateInString = this.startDate.format(ParserUtil.DATE_FORMATTER_1);
        return new CommandResult(
                String.format(MESSAGE_SUCCESS, name, dateInString),
                new UiFocus[] {UiFocus.AGENDA}
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InitCommand // instanceof handles nulls
                && this.name.equals(((InitCommand) other).name)
                && this.startDate.equals(((InitCommand) other).startDate));
    }
}
