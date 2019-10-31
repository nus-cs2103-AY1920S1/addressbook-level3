package seedu.scheduler.logic.commands;

import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_NUS_WORK_EMAIL;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_PERSONAL_EMAIL;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_SLOT;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.scheduler.logic.parser.CliSyntax.PREFIX_YEAR_OF_STUDY;

import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.person.DefaultValues;

/**
 * Adds a person to the scheduler book.
 */
public abstract class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the scheduler book. "
            + "Parameters: "
            + PREFIX_ROLE + "ROLE "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_FACULTY + "FACULTY] "
            + "[" + PREFIX_YEAR_OF_STUDY + "YEAR OF STUDY] "
            + "[" + PREFIX_DEPARTMENT + "DEPARTMENT]... "
            + "[" + PREFIX_SLOT + "TIMESLOTS]... "
            + "[" + PREFIX_PERSONAL_EMAIL + "PERSONAL_EMAIL] "
            + "[" + PREFIX_NUS_WORK_EMAIL + "NUS_WORK_EMAIL]\n"
            + "Example: "
            + COMMAND_WORD + " "
            + PREFIX_ROLE + DefaultValues.DEFAULT_INTERVIEWEE_ROLE + " "
            + PREFIX_NAME + DefaultValues.DEFAULT_NAME + " "
            + PREFIX_PHONE + DefaultValues.DEFAULT_PHONE + " "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney "
            + PREFIX_FACULTY + DefaultValues.DEFAULT_FACULTY + " "
            + PREFIX_YEAR_OF_STUDY + DefaultValues.DEFAULT_YEAR_OF_STUDY + " "
            + PREFIX_DEPARTMENT + DefaultValues.DEFAULT_DEPARTMENT + " "
            + PREFIX_SLOT + DefaultValues.DEFAULT_SLOT + " "
            + PREFIX_PERSONAL_EMAIL + DefaultValues.DEFAULT_PERSONAL_EMAIL + " "
            + PREFIX_NUS_WORK_EMAIL + DefaultValues.DEFAULT_NUS_WORK_EMAIL;

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the scheduler book";

    @Override
    public abstract CommandResult execute(Model model) throws CommandException;
}
