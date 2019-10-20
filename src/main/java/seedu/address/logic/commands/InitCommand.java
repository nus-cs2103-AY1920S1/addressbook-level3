package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.field.Name;

import java.util.Date;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

/**
 * Adds a person to the address book.
 */
public class InitCommand extends Command {

    public static final String COMMAND_WORD = "init";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + ": Initialises the Planner with a name and trip start date. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_START_DATE + "START_DATE(DD/MM/YYYY) "
            + "[" + PREFIX_DAY + "DAY]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "An amazing trip to Germany "
            + PREFIX_START_DATE + "23/7/2020 "
            + PREFIX_DAY + "7";

    public static final String MESSAGE_SUCCESS = "Planner initialised with name:%1$s, start day:%2$s"
            + " and %3$d days.";

    private final Name name;
    private final Date startDate;
    private final Integer numDays;

    /**
     * Creates an AddContactCommand to add the specified {@code Person}
     */
    public InitCommand(Name name, Date date, Integer numDays) {
        requireAllNonNull(name, date);
        this.name = name;
        this.startDate = date;
        this.numDays = numDays;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.setPlannerName(this.name);
        model.setPlannerStartDate(this.startDate);
        if (numDays != null) {
            model.addDays(numDays);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, name, startDate, numDays));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InitCommand // instanceof handles nulls
                && name.equals(((InitCommand) other).name)
                && startDate.equals(((InitCommand) other).startDate)
                && numDays.equals(((InitCommand) other).numDays));
    }
}
