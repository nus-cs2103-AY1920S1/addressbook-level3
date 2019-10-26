package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEPARTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUS_WORK_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSONAL_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SLOT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_YEAR_OF_STUDY;
import static seedu.address.model.person.DefaultValues.DEFAULT_DEPARTMENT;
import static seedu.address.model.person.DefaultValues.DEFAULT_FACULTY;
import static seedu.address.model.person.DefaultValues.DEFAULT_INTERVIEWEE_ROLE;
import static seedu.address.model.person.DefaultValues.DEFAULT_NAME;
import static seedu.address.model.person.DefaultValues.DEFAULT_NUS_WORK_EMAIL;
import static seedu.address.model.person.DefaultValues.DEFAULT_PERSONAL_EMAIL;
import static seedu.address.model.person.DefaultValues.DEFAULT_PHONE;
import static seedu.address.model.person.DefaultValues.DEFAULT_SLOT;
import static seedu.address.model.person.DefaultValues.DEFAULT_YEAR_OF_STUDY;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
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
            + PREFIX_ROLE + DEFAULT_INTERVIEWEE_ROLE + " "
            + PREFIX_NAME + DEFAULT_NAME + " "
            + PREFIX_PHONE + DEFAULT_PHONE + " "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney "
            + PREFIX_FACULTY + DEFAULT_FACULTY + " "
            + PREFIX_YEAR_OF_STUDY + DEFAULT_YEAR_OF_STUDY + " "
            + PREFIX_DEPARTMENT + DEFAULT_DEPARTMENT + " "
            + PREFIX_SLOT + DEFAULT_SLOT + " "
            + PREFIX_PERSONAL_EMAIL + DEFAULT_PERSONAL_EMAIL + " "
            + PREFIX_NUS_WORK_EMAIL + DEFAULT_NUS_WORK_EMAIL;

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        // TODO: eventually phase this out to addInterviewee / addInterviewer
        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
