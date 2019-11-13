package seedu.planner.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;
import static seedu.planner.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.planner.logic.commands.util.CommandUtil.findIndexOfContact;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.planner.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.autocomplete.CommandInformation;
import seedu.planner.logic.commands.exceptions.CommandException;
import seedu.planner.logic.commands.result.CommandResult;
import seedu.planner.logic.commands.result.ResultInformation;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.logic.commands.util.HelpExplanation;
import seedu.planner.model.Model;
import seedu.planner.model.contact.Contact;

/**
 * Adds a person to the address book.
 */
public class AddContactCommand extends AddCommand {

    public static final String SECOND_COMMAND_WORD = "contact";

    public static final HelpExplanation MESSAGE_USAGE = new HelpExplanation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            "Adds a contact to the contact list.",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " "
                    + PREFIX_NAME + "NAME "
                    + PREFIX_PHONE + "PHONE "
                    + "[" + PREFIX_EMAIL + "EMAIL] "
                    + "[" + PREFIX_ADDRESS + "ADDRESS] "
                    + "[" + PREFIX_TAG + "TAG]...",
            COMMAND_WORD + " " + SECOND_COMMAND_WORD + " "
                    + PREFIX_NAME + "John Doe "
                    + PREFIX_PHONE + "98765432 "
                    + PREFIX_EMAIL + "johnd@example.com "
                    + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
                    + PREFIX_TAG + "friends "
                    + PREFIX_TAG + "owesMoney "
    );

    public static final CommandInformation COMMAND_INFORMATION = new CommandInformation(
            COMMAND_WORD + " " + SECOND_COMMAND_WORD,
            Arrays.asList(PREFIX_NAME.toString(), PREFIX_PHONE.toString()),
            new ArrayList<>(),
            Arrays.asList(PREFIX_ADDRESS.toString(), PREFIX_EMAIL.toString()),
            Arrays.asList(PREFIX_TAG.toString())
    );

    public static final String MESSAGE_SUCCESS = "New contact added: %1$s";
    public static final String MESSAGE_DUPLICATE_CONTACT = "This contact already exists in the contact list";

    private final Index index;
    private final Contact toAdd;
    private final boolean isUndoRedo;

    /**
     * Creates an AddContactCommand to add the specified {@code contact}.
     */
    public AddContactCommand(Contact contact, boolean isUndoRedo) {
        requireNonNull(contact);
        toAdd = contact;
        index = null;
        this.isUndoRedo = isUndoRedo;
    }

    // Constructor used to undo DeleteContactEvent
    public AddContactCommand(Index index, Contact contact) {
        requireAllNonNull(index, contact);
        toAdd = contact;
        this.index = index;
        this.isUndoRedo = true;
    }

    public Contact getToAdd() {
        return toAdd;
    }

    @Override
    public String getSecondCommandWord() {
        return SECOND_COMMAND_WORD;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasContact(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONTACT);
        }
        if (index == null && !isUndoRedo) {
            // Not due to undo method
            updateEventStack(this, model);
            model.addContact(toAdd);
        } else if (isUndoRedo && index != null) {
            // Due to undo method of DeleteContactEvent
            model.addContactAtIndex(index, toAdd);
        } else {
            // Due to redo method AddContactEvent
            model.addContact(toAdd);
        }
        return new CommandResult(
            String.format(MESSAGE_SUCCESS, toAdd),
            new ResultInformation[] {
                new ResultInformation(
                        toAdd,
                        findIndexOfContact(model, toAdd),
                        String.format(MESSAGE_SUCCESS, "")
                )
            },
            new UiFocus[]{ UiFocus.CONTACT, UiFocus.INFO }
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddContactCommand // instanceof handles nulls
                && toAdd.equals(((AddContactCommand) other).toAdd));
    }
}
