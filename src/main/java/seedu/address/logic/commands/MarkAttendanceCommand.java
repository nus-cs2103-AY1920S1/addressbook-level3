package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Performance;
import seedu.address.model.person.Person;
import seedu.address.model.project.Meeting;
import seedu.address.model.project.Project;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.*;

public class MarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "markAttendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the attendance for meeting specified by the index number used in the displayed meeting list\n"
            + "Parameters: MEETING_INDEX PERSON_INDEX... (INDEX must be positive integer)\n"
            + "Example: " + COMMAND_WORD + "2 1 2 4 (MEETING_INDEX: 2, PERSON_INDEX:1, 2, 4)";

    public static final String MESSAGE_MARK_ATTENDANCE_SUCCESS = "Attendance for meeting(%1$s on %2$s) is marked for %3$s";
    public static final String MESSAGE_DUPLICATE_ATTENDANCE = "Attendance already marked for %1$s";
    public static final String MESSAGE_NO_MEMBER_SPECIFIED = "No members are specified, attendance cannot be marked.";

    private final List<Index> targetIndexes;

    public MarkAttendanceCommand(List<Index> targetIndexes) {
        this.targetIndexes = targetIndexes;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.isCheckedOut()) {
            throw new CommandException(MESSAGE_NOT_CHECKED_OUT);
        }
        Project currWorkingProject = model.getWorkingProject().get();
        List<Meeting> meetingSet = currWorkingProject.getListOfMeeting();
        //Getting the list shown to the user so that the index input matches the position of the meeting
        List<Meeting> meetingListShown = meetingSet.stream()
                .sorted(Comparator.comparing(m -> m.getTime().getDate())).collect(Collectors.toList());

        //Finding the corresponding Persons in the contacts based on the names in the project
        List<String> personNameList = model.getWorkingProject().get().getMemberNames();
        List<Person> personList = new ArrayList<>();
        for (String personName : personNameList) {
            String[] nameKeywords = personName.trim().split("\\s+");

            //Filters the model person list one by one based on each name to find the relevant Person object
            // since project only keeps members as strings
            personList.add(model.getFilteredPersonList()
                    .filtered(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords))).get(0));
        }

        Index meetingIndex = targetIndexes.remove(0);

        if (meetingIndex.getZeroBased() >= meetingListShown.size()) {
            throw new CommandException(MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
        }

        Meeting meeting = meetingListShown.get(meetingIndex.getZeroBased());
        List<Person> personsToMark = new ArrayList<>();
        List<Index> personIndexList = targetIndexes;

        if (personIndexList.isEmpty()) {
            throw new CommandException(MESSAGE_NO_MEMBER_SPECIFIED);
        }
        for (Index personIndex : personIndexList) {
            if (personIndex.getZeroBased() >= personList.size()) {
                throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX + ": " + personIndex.getOneBased());
            }
            personsToMark.add(personList.get(personIndex.getZeroBased()));
        }

        List<Person> markedPersons = markAttendanceOf(personsToMark, meeting, currWorkingProject);
        setPersons(personsToMark, markedPersons, model);

        return new CommandResult(String.format(MESSAGE_MARK_ATTENDANCE_SUCCESS,
                meeting.getDescription().toString(),
                meeting.getTime().toString(),
                getAsStringPersons(markedPersons)), COMMAND_WORD);
    }

    private List<Person> markAttendanceOf(List<Person> personsToMark, Meeting meeting, Project currWorkingProject) throws CommandException {
        List<Person> markedPersons = new ArrayList<>();
        String projectTitle = currWorkingProject.getTitle().title;

        for (Person personToMark : personsToMark) {
            Performance previousPerformance = personToMark.getPerformance();
            HashMap<String, List<Meeting>> meetingsAttended = previousPerformance.getMeetingsAttended();

            if (meetingsAttended.containsKey(projectTitle)) {
                if (meetingsAttended.get(projectTitle).contains(meeting)) {
                    throw new CommandException(String.format(MESSAGE_DUPLICATE_ATTENDANCE, personToMark.getName().fullName));
                }
                meetingsAttended.get(currWorkingProject.getTitle().title).add(meeting);
            } else {
                meetingsAttended.put(projectTitle, new ArrayList<>());
                meetingsAttended.get(projectTitle).add(meeting);
            }

            HashMap<String, List<Meeting>> updatedMeetingsAttended = previousPerformance.getMeetingsAttended();

            Performance updatedPerformance = previousPerformance.setMeetingsAttended(updatedMeetingsAttended);

            Person editedPerson = new Person(personToMark.getName(), personToMark.getPhone(), personToMark.getEmail(),
                    personToMark.getProfilePicture(), personToMark.getAddress(),
                    personToMark.getTags(), personToMark.getTimetable(), updatedPerformance);
            editedPerson.getProjects().addAll(personToMark.getProjects());

            markedPersons.add(editedPerson);
        }

        return markedPersons;
    }
}
