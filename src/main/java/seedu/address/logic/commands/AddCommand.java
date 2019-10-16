package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE_OF_BIRTH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.address.logic.commands.exceptions.DuplicatePersonWithMergeException;
import seedu.address.logic.commands.exceptions.DuplicatePersonWithoutMergeException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_DATE_OF_BIRTH + "DATE OF BIRTH\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_NRIC + "S000001J "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_DATE_OF_BIRTH + "12.12.1912 ";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book\n";
    public static final String MESSAGE_INPUT_INFORMATION_HEADER = "Your input:\n%1$s";
    public static final String DUPLICATE_PERSON_MERGE_PROMPT = "Do you wish to edit this person's profile?";
    public static final String NEW_LINE = "\n";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws DuplicatePersonWithoutMergeException,
            DuplicatePersonWithMergeException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            String exceptionMessage = "";
            if (toAdd.hasEqualCompulsoryFields(model.getPerson(toAdd))) {
                exceptionMessage = generateExceptionMessageWithoutMergePrompt(model.getPerson(toAdd));
                throw new DuplicatePersonWithoutMergeException(exceptionMessage);
            } else {
                exceptionMessage = generateExceptionMessageWithMergePrompt(model.getPerson(toAdd));
                throw new DuplicatePersonWithMergeException(exceptionMessage);
            }
        }

        model.addPerson(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    /**
     * Generates an exception message with a merge prompt.
     * @param original Original person in the addressbook.
     * @return The exception message to be thrown.
     */
    public String generateExceptionMessageWithMergePrompt(Person original) {
        StringBuilder exceptionMessage = new StringBuilder();
        exceptionMessage.append(MESSAGE_DUPLICATE_PERSON);
        exceptionMessage.append(original.toString() + NEW_LINE);
        exceptionMessage.append(String.format(MESSAGE_INPUT_INFORMATION_HEADER, toAdd.toString()) + NEW_LINE);
        exceptionMessage.append(DUPLICATE_PERSON_MERGE_PROMPT);
        return exceptionMessage.toString();
    }

    /**
     * Generates an exception message without a merge prompt. This method is used if the input person has all the same
     * compulsory data fields as the original person.
     * @param original Original person in the addressbook.
     * @return The exception message to be thrown.
     */
    public String generateExceptionMessageWithoutMergePrompt(Person original) {
        StringBuilder exceptionMessage = new StringBuilder();
        exceptionMessage.append(MESSAGE_DUPLICATE_PERSON);
        exceptionMessage.append(original.toString());
        return exceptionMessage.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
