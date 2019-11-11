package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDIT;
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
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDescriptor;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.NoPersonFieldsEditedException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditPersonCommand extends Command {

    public static final String COMMAND_WORD = "editperson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + PREFIX_EDIT + "PERSON_NAME" + " "
            + "[" + PREFIX_NAME + "NAME]" + " "
            + "[" + PREFIX_PHONE + "PHONE]" + " "
            + "[" + PREFIX_EMAIL + "EMAIL]" + " "
            + "[" + PREFIX_ADDRESS + "ADDRESS]" + " "
            + "[" + PREFIX_REMARK + "REMARK]" + " "
            + "[" + PREFIX_TAG + "TAG] ..."
            + "\n" + "Note: At least one field must be edited";

    public static final String MESSAGE_SUCCESS = "Edit Person success: %s edited";
    public static final String MESSAGE_FAILURE = "Unable to edit person: %s";

    public static final String MESSAGE_PERSON_NOT_FOUND = "Unable to find person";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final Name name;
    private final PersonDescriptor personDescriptor;

    public EditPersonCommand(Name name, PersonDescriptor personDescriptor) {
        requireNonNull(name);
        requireNonNull(personDescriptor);

        this.name = name;
        this.personDescriptor = personDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            Person person = model.editPerson(name, personDescriptor);

            // update main window display
            model.updateScheduleWithPerson(person.getName(), LocalDateTime.now(), ScheduleState.PERSON);

            // update side panel display
            model.updateSidePanelDisplay(SidePanelDisplayType.PERSON);

            return new CommandResultBuilder(String.format(MESSAGE_SUCCESS, name.toString())).build();

        } catch (PersonNotFoundException e) {
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_PERSON_NOT_FOUND)).build();
        } catch (NoPersonFieldsEditedException e) {
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_NOT_EDITED)).build();
        } catch (DuplicatePersonException e) {
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_DUPLICATE_PERSON)).build();
        }

    }

    @Override
    public boolean equals(Command command) {
        if (command == null) {
            return false;
        } else if (!(command instanceof EditPersonCommand)) {
            return false;
        } else if (((EditPersonCommand) command).name.equals(this.name)
                && ((EditPersonCommand) command).personDescriptor.equals(this.personDescriptor)) {
            return true;
        } else {
            return false;
        }
    }
}

