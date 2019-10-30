package seedu.address.logic.events.add;

import seedu.address.logic.commands.AddAccommodationCommand;
import seedu.address.logic.commands.AddActivityCommand;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddContactCommand;
import seedu.address.logic.events.Event;
import seedu.address.logic.events.exceptions.EventException;
import seedu.address.model.contact.Contact;
import seedu.address.model.itineraryitem.accommodation.Accommodation;
import seedu.address.model.itineraryitem.activity.Activity;

/**
 * A factory class to generate the corresponding add Events according to the add Commands parsed.
 */
public class AddEventFactory {
    public static final String MESSAGE_NOT_UNDOABLE = "The following command \'%1$s\' \'%2$s\' is not undoable";

    /**
     * A static method to generate the add events based on the add commands parsed.
     * @param command Add Command to be parsed.
     * @return Corresponding event representing the add command parsed.
     * @throws EventException
     */
    public static Event parse(AddCommand command) throws EventException {
        String secondCommandWord = command.getSecondCommandWord();

        switch(secondCommandWord) {
        case (AddAccommodationCommand.SECOND_COMMAND_WORD):
            AddAccommodationCommand tempCommand1 = (AddAccommodationCommand) command;
            return generateAddAccommodationEvent(tempCommand1.getToAdd());

        case (AddActivityCommand.SECOND_COMMAND_WORD):
            AddActivityCommand tempCommand2 = (AddActivityCommand) command;
            return generateAddActivityEvent(tempCommand2.getToAdd());

        case (AddContactCommand.SECOND_COMMAND_WORD):
            AddContactCommand tempCommand3 = (AddContactCommand) command;
            return generateAddContactEvent(tempCommand3.getToAdd());

        default:
            throw new EventException(
                    String.format(MESSAGE_NOT_UNDOABLE, command.getCommandWord(), command.getSecondCommandWord())
            );
        }

        /*
        case(AddDayCommand.SECOND_COMMAND_WORD):
            AddDayCommand tempCommand4 = (AddDayCommand)command;
            generateAddDayEvent(tempCommand4.getToAdd());
        }
        */
    }

    public static AddAccommodationEvent generateAddAccommodationEvent(Accommodation accommodationAdded) {
        return new AddAccommodationEvent(accommodationAdded);
    }

    public static AddActivityEvent generateAddActivityEvent(Activity activityAdded) {
        return new AddActivityEvent(activityAdded);
    }

    public static AddContactEvent generateAddContactEvent(Contact contactAdded) {
        return new AddContactEvent(contactAdded);
    }

    /*
    public static AddDayEvent generateAddDayEvent(int daysAdded) {
        return new AddDayEvent(daysAdded);
    }
    */
}
