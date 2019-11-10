package seedu.address.itinerary.logic.commands;

import java.util.ArrayList;

import javafx.collections.transformation.SortedList;
import seedu.address.itinerary.model.Model;
import seedu.address.itinerary.model.event.Event;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * clear event command for itinerary.
 */
public class ClearEventCommand extends Command<Model> {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Processing...\nDone!\n"
            + "Your events has been wiped off from the face of this Earth. Yay! =U\n"
            + "Now you are ready to travel to outer space! :3";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Clears all event entries in the itinerary list\n";
    public static final String MESSAGE_WARNING = "Currently viewing clear window warning.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.clearEvent();
        return new CommandResult(MESSAGE_WARNING, true);
    }


    /*
        While clearing the even list for the user is useful, it might be better to allow the users to actually
        delete their events based on the index range of the event list instead of clearing the whole list as
        done by what this command is called right now. There is one implementation of the deleteRange function
        that is provided as a backup should there be a need to allow the users to delete there list in the range
        instead of clearing the whole list instead. Since this is because it allows the user to retain only some
        of the events that he really wants to keep and the rest can be deleted and he does not need to delete
        all of his events one by one by continuously calling the delete function x amount of times where x is the
        number of events in the range that the user would wish to delete at. However, one consideration to take
        note in the deleteRange event is that due to the increase number of flexibility in the code while implementing
        the delete function, the range might result in a bug. Not only that by looking at the implementation
        of the current deleteRange function, the method does not alter the state of the current list but instead,
        it will create a new sortedlist object before starting to copy the events that are not within the range.
        The thought process is to ensure that from the start it will copy until the first number indicating the
        start of the range. Then it will not copy those events up until the end index. Before it will start copying
        again until the end.

        However, do also take note that the clear feature while it is much more efficient and bug free
        since we only call the clear function which is inbuilt in the sort list class, it is not reversible and user
        might potentially lose alot of data if he were to clear a large amount of data.

        Maybe one other consideration to make the user confirm whether he really wants to clear all the data in
        the event list that he has painstakingly inserted and made. This will ensure that there is no accidental
        deletion of all the events also which can turn out to be quite disastrous for the user since he does
        not want his data to be erased but in the process done so because of a happy little accident. ~Bob Ross

        With the clear all function maybe there could also be a warning to tell the user that they are wiping
        out all of their information in the itinerary list and that they should be careful whether they would wish
        to do that. This will act as a caveat to whether they want to really delete all the information from the
        itinerary list or whether they have only accidentally call on that command.

        One other cool feature to consider is that one the user actually confirms to clear his whole itinerary event
        list when he selects the "ok" button during the popup we can do a notification pop up with a check sign
        showing that the list has been successfully cleared.
     */

    /**
     * Trim the event list based on the delete range.
     * @param start starting index of the delete range.
     * @param end ending index of the delete range.
     * @param sortedList the original sorted list.
     * @return the trimmed version of the list.
     */
    public ArrayList<Event> deleteRange(int start, int end, SortedList<Event> sortedList) {
        ArrayList<Event> result = new ArrayList<>();

        for (int i = 0; i < start; i++) {
            Event currEvent = sortedList.get(i);
            result.add(currEvent);
        }

        for (int j = end + 1; j < sortedList.size(); j++) {
            Event currEvent = sortedList.get(j);
            result.add(currEvent);
        }

        return result;
    }


}
