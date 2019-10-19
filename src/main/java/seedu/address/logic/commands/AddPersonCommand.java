package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.time.LocalDateTime;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.detailwindow.DetailWindowDisplayType;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDescriptor;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Adds a person.
 */
public class AddPersonCommand extends Command {

    public static final String COMMAND_WORD = "addperson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_NAME + " NAME";

    public static final String MESSAGE_SUCCESS = "New person added: %s";
    public static final String MESSAGE_FAILURE = "Unable to add person: %s";

    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    private final PersonDescriptor personDescriptor;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddPersonCommand(PersonDescriptor personDescriptor) {
        requireNonNull(personDescriptor);
        this.personDescriptor = personDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            Person addedPerson = model.addPerson(personDescriptor);

            // update main window
            model.updateDetailWindowDisplay(addedPerson.getName(), LocalDateTime.now(), DetailWindowDisplayType.PERSON);

            // update side panel
            model.updateSidePanelDisplay(SidePanelDisplayType.PERSONS);

            return new CommandResult(String.format(MESSAGE_SUCCESS, addedPerson.getName().toString()));

        } catch (DuplicatePersonException e) {
            return new CommandResult(String.format(MESSAGE_FAILURE, MESSAGE_DUPLICATE_PERSON));
        }

    }

    @Override
    public boolean equals(Command command) {
        return command == this // short circuit if same object
                || (command instanceof AddPersonCommand // instanceof handles nulls
                && personDescriptor.equals(((AddPersonCommand) command).personDescriptor));
    }

}
