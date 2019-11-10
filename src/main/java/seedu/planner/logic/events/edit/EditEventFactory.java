package seedu.planner.logic.events.edit;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.editcommand.EditAccommodationCommand;
import seedu.planner.logic.commands.editcommand.EditAccommodationCommand.EditAccommodationDescriptor;
import seedu.planner.logic.commands.editcommand.EditActivityCommand;
import seedu.planner.logic.commands.editcommand.EditActivityCommand.EditActivityDescriptor;
import seedu.planner.logic.commands.editcommand.EditCommand;
import seedu.planner.logic.commands.editcommand.EditContactCommand;
import seedu.planner.logic.commands.editcommand.EditContactCommand.EditContactDescriptor;
import seedu.planner.logic.events.Event;
import seedu.planner.model.accommodation.Accommodation;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.contact.Contact;
//@@author OneArmyj
/**
 * A factory class to generate the corresponding edit Events according to the edit Commands parsed.
 */
public class EditEventFactory {
    /**
     * A static method to generate the edit events based on the edit commands parsed.
     * @param command Edit Command to be parsed.
     * @return Corresponding event representing the edit command parsed.
     */
    public static Event parse(EditCommand command) {
        String secondCommandWord = command.getSecondCommandWord();

        switch(secondCommandWord) {
        case (EditAccommodationCommand.SECOND_COMMAND_WORD):
            EditAccommodationCommand tempCommand1 = (EditAccommodationCommand) command;
            return generateEditAccommodationEvent(tempCommand1.getIndex(),
                    tempCommand1.getEditAccommodationDescriptor(), tempCommand1.getAccommodation());

        case (EditActivityCommand.SECOND_COMMAND_WORD):
            EditActivityCommand tempCommand2 = (EditActivityCommand) command;
            return generateEditActivityEvent(tempCommand2.getIndex(),
                    tempCommand2.getEditActivityDescriptor(), tempCommand2.getActivity());

        case (EditContactCommand.SECOND_COMMAND_WORD):
            EditContactCommand tempCommand3 = (EditContactCommand) command;
            return generateEditContactEvent(tempCommand3.getIndex(),
                    tempCommand3.getEditContactDescriptor(), tempCommand3.getContact());

        default:
            return null;
        }
    }

    public static EditAccommodationEvent generateEditAccommodationEvent(
            Index index, EditAccommodationDescriptor editInfo, Accommodation accommodation) {
        return new EditAccommodationEvent(index, editInfo, accommodation);
    }

    public static EditActivityEvent generateEditActivityEvent(
            Index index, EditActivityDescriptor editInfo, Activity activity) {
        return new EditActivityEvent(index, editInfo, activity);
    }

    public static EditContactEvent generateEditContactEvent(
            Index index, EditContactDescriptor editInfo, Contact contact) {
        return new EditContactEvent(index, editInfo, contact);
    }
}
