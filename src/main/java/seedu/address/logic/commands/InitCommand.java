package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.time.LocalDate;

import seedu.address.logic.commands.exceptions.CommandException;
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
            + PREFIX_START_DATE + "START_DATE\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "An amazing trip to Germany "
            + PREFIX_START_DATE + "23-7-2020";

    public static final String MESSAGE_SUCCESS = "Planner initialised with name:%1$s and"
            + " start date:%2$s";

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
        model.setItineraryName(this.name);
        model.setItineraryStartDate(this.startDate);
        String dateInString = this.startDate.format(ParserUtil.DATE_FORMATTER_1);
        return new CommandResult(String.format(MESSAGE_SUCCESS, name, dateInString));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof InitCommand // instanceof handles nulls
                && this.name.equals(((InitCommand) other).name)
                && this.startDate.equals(((InitCommand) other).startDate));
    }
}
