package seedu.planner.logic.events.add;

import seedu.planner.logic.commands.addcommand.AddAccommodationCommand;
import seedu.planner.logic.commands.addcommand.AddActivityCommand;
import seedu.planner.logic.commands.addcommand.AddCommand;
import seedu.planner.logic.commands.addcommand.AddContactCommand;
import seedu.planner.logic.commands.addcommand.AddDayCommand;
import seedu.planner.logic.events.Event;
import seedu.planner.model.accommodation.Accommodation;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.contact.Contact;
//@@author OneArmyj
/**
 * A factory class to generate the corresponding add Events according to the add Commands parsed.
 */
public class AddEventFactory {
    /**
     * A static method to generate the add events based on the add commands parsed.
     * @param command Add Command to be parsed.
     * @return Corresponding event representing the add command parsed.
     */
    public static Event parse(AddCommand command) {
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

        case(AddDayCommand.SECOND_COMMAND_WORD):
            AddDayCommand tempCommand4 = (AddDayCommand) command;
            return generateAddDayEvent(tempCommand4.getToAdd());
        default:
            return null;
        }
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

    public static AddDayEvent generateAddDayEvent(int numberOfDays) {
        return new AddDayEvent(numberOfDays);
    }
}
