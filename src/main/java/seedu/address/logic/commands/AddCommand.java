package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_BOOK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AUTHOR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENRE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SERIAL_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.book.Book;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends ReversibleCommand {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a book to the catalog.\n"
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + PREFIX_AUTHOR + "AUTHOR "
            + "[" + PREFIX_SERIAL_NUMBER + "SERIAL_NUMBER] "
            + "[" + PREFIX_GENRE + "GENRE]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "Harry Potter "
            + PREFIX_SERIAL_NUMBER + "B0001 "
            + PREFIX_AUTHOR + "J K Rowling "
            + PREFIX_GENRE + "Fiction "
            + PREFIX_GENRE + "Action ";

    public static final String MESSAGE_SUCCESS = "New book added: %1$s";

    private final Book toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Book}
     *
     * @param book to be added into the catalog.
     */
    public AddCommand(Book book) {
        requireNonNull(book);
        toAdd = book;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasBook(toAdd)) {
            // to handle books with same serial number here next time
            throw new CommandException(MESSAGE_DUPLICATE_BOOK);
        }

        undoCommand = new DeleteBySerialNumberCommand(toAdd.getSerialNumber());
        redoCommand = this;
        commandResult = new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));

        model.addBook(toAdd);
        return commandResult;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }

}
