package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.LocalDateTime;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.display.scheduledisplay.ScheduleState;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDescriptor;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Adds a person.
 */
public class AddPersonCommand extends Command {

    public static final String COMMAND_WORD = "addperson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + PREFIX_NAME + "NAME" + " "
            + "[" + PREFIX_PHONE + "PHONE]" + " "
            + "[" + PREFIX_EMAIL + "EMAIL]" + " "
            + "[" + PREFIX_ADDRESS + "ADDRESS]" + " "
            + "[" + PREFIX_REMARK + "REMARK]" + " "
            + "[" + PREFIX_TAG + "TAG] ... ";

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
            model.updateScheduleWithUser(LocalDateTime.now(), ScheduleState.HOME);

            // update side panel
            model.updateSidePanelDisplay(SidePanelDisplayType.PERSON);

            return new CommandResultBuilder(String.format(MESSAGE_SUCCESS, addedPerson.getName().toString())).build();

        } catch (DuplicatePersonException e) {
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_DUPLICATE_PERSON)).build();
        }

    }

    @Override
    public boolean equals(Command command) {
        return command == this // short circuit if same object
                || (command instanceof AddPersonCommand // instanceof handles nulls
                && personDescriptor.equals(((AddPersonCommand) command).personDescriptor));
    }
}
