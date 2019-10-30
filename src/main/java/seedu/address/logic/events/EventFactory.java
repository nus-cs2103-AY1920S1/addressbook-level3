package seedu.address.logic.events;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.events.add.AddEventFactory;
import seedu.address.logic.events.delete.DeleteEventFactory;
import seedu.address.logic.events.edit.EditEventFactory;
import seedu.address.logic.events.exceptions.EventException;
import seedu.address.model.Model;

/**
 * A factory class to generate the corresponding events according to the command input.
 */
public class EventFactory {
    private static final String MESSAGE_COMMAND_ERROR = "\'%1$s\' command is not undoable.";

    /**
     * A static method to parse the command and generate the corresponding event.
     * @param command Command to be parsed.
     * @param model Current model of the application.
     * @return Corresponding event for the command parsed.
     * @throws EventException
     */
    public static Event parse(UndoableCommand command, Model model) throws EventException {
        requireNonNull(command);
        requireNonNull(model);

        String commandWord = command.getCommandWord();

        switch (commandWord) {
        case (AddCommand.COMMAND_WORD):
            return AddEventFactory.parse((AddCommand) command);

        case (DeleteCommand.COMMAND_WORD):
            return DeleteEventFactory.parse((DeleteCommand) command, model);

        case (EditCommand.COMMAND_WORD):
            return EditEventFactory.parse((EditCommand) command, model);
        /*
        case (ClearCommand.COMMAND_WORD):
            generateClearCommandEvent(model);
            break;

        case (InitCommand.COMMAND_WORD):
            parseInitCommand(command);
            break;
        }
        */
        default:
            throw new EventException(String.format(MESSAGE_COMMAND_ERROR, commandWord));
        }
    }
}
