package seedu.planner.logic.events.edit;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.EditAccommodationCommand;
import seedu.planner.logic.commands.EditAccommodationCommand.EditAccommodationDescriptor;
import seedu.planner.logic.commands.EditActivityCommand;
import seedu.planner.logic.commands.EditActivityCommand.EditActivityDescriptor;
import seedu.planner.logic.commands.EditCommand;
import seedu.planner.logic.commands.EditContactCommand;
import seedu.planner.logic.commands.EditContactCommand.EditContactDescriptor;
import seedu.planner.logic.events.Event;
import seedu.planner.logic.events.exceptions.EventException;
import seedu.planner.model.Model;

/**
 * A factory class to generate the corresponding edit Events according to the edit Commands parsed.
 */
public class EditEventFactory {
    public static final String MESSAGE_NOT_UNDOABLE = "The following command \'%1$s\' \'%2$s\' is not undoable";

    /**
     * A static method to generate the edit events based on the edit commands parsed.
     * @param command Edit Command to be parsed.
     * @param model Current model of the application.
     * @return Corresponding event representing the edit command parsed.
     * @throws EventException
     */
    public static Event parse(EditCommand command, Model model) throws EventException {
        String secondCommandWord = command.getSecondCommandWord();

        switch(secondCommandWord) {
        case (EditAccommodationCommand.SECOND_COMMAND_WORD):
            EditAccommodationCommand tempCommand1 = (EditAccommodationCommand) command;
            return generateEditAccommodationEvent(tempCommand1.getIndex(),
                    tempCommand1.getEditAccommodationDescriptor(), model);

        case (EditActivityCommand.SECOND_COMMAND_WORD):
            EditActivityCommand tempCommand2 = (EditActivityCommand) command;
            return generateEditActivityEvent(tempCommand2.getIndex(),
                    tempCommand2.getEditActivityDescriptor(), model);

        case (EditContactCommand.SECOND_COMMAND_WORD):
            EditContactCommand tempCommand3 = (EditContactCommand) command;
            return generateEditContactEvent(tempCommand3.getIndex(),
                    tempCommand3.getEditContactDescriptor(), model);

        default:
            throw new EventException(
                    String.format(MESSAGE_NOT_UNDOABLE, command.getCommandWord(), command.getSecondCommandWord())
            );
        }

        /*
        case(EditDayCommand.SECOND_COMMAND_WORD):
            EditDayCommand tempCommand4 = (EditDayCommand)command;
            generateEditDayEvent(tempCommand4.getToEdit());
        }
        */
    }

    public static EditAccommodationEvent generateEditAccommodationEvent(
            Index index, EditAccommodationDescriptor editInfo, Model model) {
        return new EditAccommodationEvent(index, editInfo, model);
    }

    public static EditActivityEvent generateEditActivityEvent(
            Index index, EditActivityDescriptor editInfo, Model model) {
        return new EditActivityEvent(index, editInfo, model);
    }

    public static EditContactEvent generateEditContactEvent(
            Index index, EditContactDescriptor editInfo, Model model) {
        return new EditContactEvent(index, editInfo, model);
    }

    /*
    public static EditDayEvent generateEditDayEvent(int daysEdited) {
        return new EditDayEvent(daysEdited);
    }
    */
}
