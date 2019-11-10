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
import seedu.address.model.person.PersonDescriptor;
import seedu.address.model.person.User;
import seedu.address.model.person.exceptions.NoPersonFieldsEditedException;

/**
 * Edits the details of the User.
 */
public class EditUserCommand extends Command {

    public static final String COMMAND_WORD = "edituser";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " "
            + "[" + PREFIX_NAME + "NAME]" + " "
            + "[" + PREFIX_PHONE + "PHONE]" + " "
            + "[" + PREFIX_EMAIL + "EMAIL]" + " "
            + "[" + PREFIX_ADDRESS + "ADDRESS]" + " "
            + "[" + PREFIX_REMARK + "REMARK]" + " "
            + "[" + PREFIX_TAG + "TAG] ..."
            + "\n" + "Note: At least one field must be edited";

    public static final String MESSAGE_SUCCESS = "Edit User success: %s edited";
    public static final String MESSAGE_FAILURE = "Unable to edit user: %s";

    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";

    private final PersonDescriptor personDescriptor;

    public EditUserCommand(PersonDescriptor personDescriptor) {
        requireNonNull(personDescriptor);

        this.personDescriptor = personDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        try {
            User user = model.editUser(personDescriptor);

            model.updateScheduleWithUser(LocalDateTime.now(), ScheduleState.PERSON);

            return new CommandResultBuilder(String.format(MESSAGE_SUCCESS, user.getName().toString())).build();

        } catch (NoPersonFieldsEditedException e) {
            return new CommandResultBuilder(String.format(MESSAGE_FAILURE, MESSAGE_NOT_EDITED)).build();
        }


    }

    @Override
    public boolean equals(Command command) {
        if (command == null) {
            return false;
        } else if (!(command instanceof EditUserCommand)) {
            return false;
        } else if (((EditUserCommand) command).personDescriptor.equals(this.personDescriptor)) {
            return true;
        } else {
            return false;
        }
    }
}
