package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.time.LocalDateTime;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.Model;
import seedu.address.model.field.Name;

/**
 * Adds a person to the address book.
 */
public class InitCommand extends Command {

    public static final String COMMAND_WORD = "init";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + ": Initialises the Planner with a name and trip start date. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_START_DATE + "START_DATE "
            + "[" + PREFIX_DAY + "DAY]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "An amazing trip to Germany "
            + PREFIX_START_DATE + "23-7-2020 "
            + PREFIX_DAY + "7";

    public static final String MESSAGE_SUCCESS_WITHOUT_INIT_DAY = "Planner initialised with name:%1$s and"
            + " start date:%2$s";
    public static final String MESSAGE_SUCCESS_WITH_INIT_DAY = "Planner initialised with name:%1$s, start day:%2$s"
            + " and %3$d days.";

    private final Name name;
    private final LocalDateTime startDate;
    private final Integer numDays;

    /**
     * Creates an AddContactCommand to add the specified {@code Person}
     */
    public InitCommand(Name name, LocalDateTime date, Integer numDays) {
        requireAllNonNull(name, date);
        this.name = name;
        this.startDate = date;
        this.numDays = numDays;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setPlannerName(this.name);
        model.setPlannerStartDate(this.startDate);
        String dateInString = this.startDate.format(ParserUtil.DATE_FORMAT_1);
        if (numDays == null) {
            return new CommandResult(String.format(MESSAGE_SUCCESS_WITH_INIT_DAY, name, dateInString, numDays));
        } else {
            model.addDays(numDays);
            return new CommandResult(String.format(MESSAGE_SUCCESS_WITHOUT_INIT_DAY, name, dateInString));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        }

        if (!(other instanceof InitCommand)) { // instanceof handles nulls
            return false;
        }

        if (numDays != null) {
            return (name.equals(((InitCommand) other).name)
                    && startDate.equals(((InitCommand) other).startDate)
                    && numDays.equals(((InitCommand) other).numDays));
        } else {
            return name.equals(((InitCommand) other).name)
                    && startDate.equals(((InitCommand) other).startDate)
                    && ((InitCommand) other).numDays == null;
        }

    }
}
