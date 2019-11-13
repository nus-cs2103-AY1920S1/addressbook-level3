package seedu.planner.logic.events.delete;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.deletecommand.DeleteAccommodationCommand;
import seedu.planner.logic.commands.deletecommand.DeleteActivityCommand;
import seedu.planner.logic.commands.deletecommand.DeleteCommand;
import seedu.planner.logic.commands.deletecommand.DeleteContactCommand;
import seedu.planner.logic.commands.deletecommand.DeleteDayCommand;
import seedu.planner.logic.events.Event;
import seedu.planner.model.accommodation.Accommodation;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.contact.Contact;
import seedu.planner.model.day.Day;
//@@author OneArmyj
/**
 * A factory class to generate the corresponding delete Events according to the delete Commands parsed.
 */
public class DeleteEventFactory {
    /**
     * A static method to generate the delete events based on the delete commands parsed.
     * @param command Delete Command to be parsed.
     * @return Corresponding event representing the delete command parsed.
     */
    public static Event parse(DeleteCommand command) {
        String secondCommandWord = command.getSecondCommandWord();

        switch(secondCommandWord) {
        case (DeleteAccommodationCommand.SECOND_COMMAND_WORD):
            DeleteAccommodationCommand tempCommand1 = (DeleteAccommodationCommand) command;
            return generateDeleteAccommodationEvent(tempCommand1.getTargetIndex(), tempCommand1.getToDelete());

        case (DeleteActivityCommand.SECOND_COMMAND_WORD):
            DeleteActivityCommand tempCommand2 = (DeleteActivityCommand) command;
            return generateDeleteActivityEvent(tempCommand2.getTargetIndex(), tempCommand2.getToDelete());

        case (DeleteContactCommand.SECOND_COMMAND_WORD):
            DeleteContactCommand tempCommand3 = (DeleteContactCommand) command;
            return generateDeleteContactEvent(tempCommand3.getTargetIndex(), tempCommand3.getToDelete());

        case (DeleteDayCommand.SECOND_COMMAND_WORD):
            DeleteDayCommand tempCommand4 = (DeleteDayCommand) command;
            return generateDeleteDayEvent(tempCommand4.getTargetIndex(), tempCommand4.getToDelete());

        default:
            return null;
        }
    }

    public static DeleteAccommodationEvent generateDeleteAccommodationEvent(Index index, Accommodation accommodation) {
        return new DeleteAccommodationEvent(index, accommodation);
    }

    public static DeleteActivityEvent generateDeleteActivityEvent(Index index, Activity activity) {
        return new DeleteActivityEvent(index, activity);
    }

    public static DeleteContactEvent generateDeleteContactEvent(Index index, Contact contact) {
        return new DeleteContactEvent(index, contact);
    }

    public static DeleteDayEvent generateDeleteDayEvent(Index index, Day day) {
        return new DeleteDayEvent(index, day);
    }
}
