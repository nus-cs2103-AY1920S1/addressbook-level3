package seedu.planner.logic.events.delete;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.DeleteAccommodationCommand;
import seedu.planner.logic.commands.DeleteActivityCommand;
import seedu.planner.logic.commands.DeleteCommand;
import seedu.planner.logic.commands.DeleteContactCommand;
import seedu.planner.logic.commands.DeleteDayCommand;
import seedu.planner.logic.events.Event;
import seedu.planner.logic.events.exceptions.EventException;
import seedu.planner.model.Model;

/**
 * A factory class to generate the corresponding delete Events according to the delete Commands parsed.
 */
public class DeleteEventFactory {
    public static final String MESSAGE_NOT_UNDOABLE = "The following command \'%1$s\' \'%2$s\' is not undoable";

    /**
     * A static method to generate the delete events based on the delete commands parsed.
     * @param command Delete Command to be parsed.
     * @return Corresponding event representing the delete command parsed.
     * @throws EventException
     */
    public static Event parse(DeleteCommand command, Model model) throws EventException {
        String secondCommandWord = command.getSecondCommandWord();

        switch(secondCommandWord) {
        case (DeleteAccommodationCommand.SECOND_COMMAND_WORD):
            DeleteAccommodationCommand tempCommand1 = (DeleteAccommodationCommand) command;
            return generateDeleteAccommodationEvent(tempCommand1.getTargetIndex(), model);

        case (DeleteActivityCommand.SECOND_COMMAND_WORD):
            DeleteActivityCommand tempCommand2 = (DeleteActivityCommand) command;
            return generateDeleteActivityEvent(tempCommand2.getTargetIndex(), model);

        case (DeleteContactCommand.SECOND_COMMAND_WORD):
            DeleteContactCommand tempCommand3 = (DeleteContactCommand) command;
            return generateDeleteContactEvent(tempCommand3.getTargetIndex(), model);

        case (DeleteDayCommand.SECOND_COMMAND_WORD):
            DeleteDayCommand tempCommand4 = (DeleteDayCommand) command;
            return generateDeleteDayEvent(tempCommand4.getTargetIndex(), model);

        default:
            throw new EventException(
                    String.format(MESSAGE_NOT_UNDOABLE, command.getCommandWord(), command.getSecondCommandWord())
            );
        }
    }

    public static DeleteAccommodationEvent generateDeleteAccommodationEvent(Index index, Model model)
            throws EventException {
        return new DeleteAccommodationEvent(index, model);
    }

    public static DeleteActivityEvent generateDeleteActivityEvent(Index index, Model model) throws EventException {
        return new DeleteActivityEvent(index, model);
    }

    public static DeleteContactEvent generateDeleteContactEvent(Index index, Model model) throws EventException {
        return new DeleteContactEvent(index, model);
    }

    public static DeleteDayEvent generateDeleteDayEvent(Index index, Model model) throws EventException {
        return new DeleteDayEvent(index, model);
    }
}
