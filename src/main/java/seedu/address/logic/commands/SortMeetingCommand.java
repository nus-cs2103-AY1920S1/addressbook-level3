package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.finance.Finance;
import seedu.address.model.project.Meeting;
import seedu.address.model.project.Project;
import seedu.address.model.util.SortingOrder;

import java.util.*;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PROJECTS;
import static seedu.address.commons.core.Messages.MESSAGE_NOT_CHECKED_OUT;

/**
 * Sorts the meetings in the current project.
 */
public class SortMeetingCommand extends Command {
    public static final String COMMAND_WORD = "sortMeeting";
    public static final String LIST_VALID_INDEX = "1 - Sorts by alphabetical order.\n"
            + "2 - Sorts by increasing date/time.\n";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the meetings from the current project according to given index.\n"
            + LIST_VALID_INDEX + "\n"
            + "Parameters: INDEX (must be a positive integer between 1-2)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_SORT_MEETING_SUCCESS = "Meetings sorted by%1$s";
    public static final String MESSAGE_SAME_INDEX = "Meetings already sorted in this order! Select a different ordering. Here's the list of sorting orders.\n"
            + LIST_VALID_INDEX;


    public final Index index;

    public SortMeetingCommand(Index index) {
        requireNonNull(index);
        this.index = index;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {

        requireNonNull(model);

        if (!model.isCheckedOut()) {
            throw new CommandException(MESSAGE_NOT_CHECKED_OUT);
        }

        int num = index.getOneBased();
        if (num == SortingOrder.getMeetingCurrentIndex()) {
            throw new CommandException(MESSAGE_SAME_INDEX);
        }
        Project projectToEdit = model.getWorkingProject().get();
        List<String> members = projectToEdit.getMemberNames();
        List<Meeting> meetings = projectToEdit.getListOfMeeting();
        String sortType = "";


        switch (num) {
        case 1:
            sortType = " alphabetical order.";
            SortingOrder.setCurrentMeetingSortingOrderByAlphabeticalOrder();
            break;

        case 2:
            sortType = " increasing date/time.";
            SortingOrder.setCurrentMeetingSortingOrderByDate();
            break;

        default:
            throw new CommandException(Messages.MESSAGE_INVALID_SORT_MEETING_DISPLAYED_INDEX);
        }

        List<Meeting> meetingList = new ArrayList<>();
        meetingList.addAll(meetings);
        sortMeeting(meetingList, SortingOrder.getCurrentSortingOrderForMeeting());
        Finance finance = projectToEdit.getFinance();

        Project editedProject = new Project(projectToEdit.getTitle(), projectToEdit.getDescription(), new ArrayList<String>(), projectToEdit.getTasks(), finance, projectToEdit.getGeneratedTimetable());
        editedProject.getMemberNames().addAll(members);
        editedProject.setListOfMeeting(meetingList);


        model.setProject(projectToEdit, editedProject);
        model.updateFilteredProjectList(PREDICATE_SHOW_ALL_PROJECTS);
        return new CommandResult(String.format(MESSAGE_SORT_MEETING_SUCCESS, sortType), COMMAND_WORD);
    }

    public void sortMeeting(List<Meeting> list, Comparator<Meeting> meetingComparator) {
        Collections.sort(list, meetingComparator);
    }


    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortMeetingCommand)) {
            return false;
        }

        // state check
        SortMeetingCommand e = (SortMeetingCommand) other;
        return index.equals(e.index);
    }
}
