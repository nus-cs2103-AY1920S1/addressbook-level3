package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import seedu.address.model.display.mainwindow.MainWindowDisplayType;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDescriptor;

import java.time.LocalDateTime;


/**
 * Edits the details of an existing person in the address book.
 */
public class EditPersonCommand extends Command {

    public static final String COMMAND_WORD = "editperson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + PREFIX_EDIT + " PERSON_NAME "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_REMARK + "REMARK] "
            + "[" + PREFIX_TAG + "TAG]...\n";

    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    public static final String MESSAGE_SUCCESS = "Edited Person: \n\n";
    public static final String MESSAGE_FAILURE = "Unable to edit person";

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

        if (!personDescriptor.isAnyFieldEdited()) {
            return new CommandResult(MESSAGE_NOT_EDITED);
        }

        if (model.findPerson(name) == null) {
            return new CommandResult(MESSAGE_FAILURE);
        }

        Person person = model.editPerson(name, personDescriptor);

        if (person == null) {
            return new CommandResult(MESSAGE_FAILURE);
        } else {
            // update main window display
            model.updateMainWindowDisplay(person.getName(), LocalDateTime.now(), MainWindowDisplayType.SCHEDULE);

            // update side panel display
            model.updateSidePanelDisplay(SidePanelDisplayType.PERSONS);

            return new CommandResult(MESSAGE_SUCCESS + person.details());
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

