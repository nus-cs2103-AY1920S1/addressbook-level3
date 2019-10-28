package seedu.address.model.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.IntervieweeList;
import seedu.address.model.InterviewerList;
import seedu.address.model.ReadOnlyIntervieweeList;
import seedu.address.model.ReadOnlyInterviewerList;
import seedu.address.model.Schedule;
import seedu.address.model.person.Department;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Interviewer;
import seedu.address.model.person.Slot;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    private static String[][] sampleFilledTable =
            new String[][]{
                    {"10/9/2019", "Welfare - Hazel", "Technical - Johnathan", "Publicity - Lucia"},
                    {"18:00-18:30", "John", "Steven", "0"},
                    {"18:30-19:00", "Alex", "Clark", "John"},
                    {"19:00-19:30", "Alicia", "0", "charlie"},
                    {"19:30-20:00", "Charlie", "0", "Selina"},
                    {"20:00-20:30", "Selina", "0", "0"},
                    {"20:30-21:00", "Natal", "0", "0"}};

    public static Interviewee[] getSampleInterviewees() {
        // TODO: Expand this sample Interviewees list
        return new Interviewee[0];
    }

    public static Interviewer[] getSampleInterviewers() {
        // TODO: Expand this sample Interviewers list
        return new Interviewer[0];
    }

    public static ReadOnlyIntervieweeList getSampleIntervieweeList() {
        IntervieweeList sampleIntervieweeList = new IntervieweeList();

        for (Interviewee sampleInterviewee : getSampleInterviewees()) {
            sampleIntervieweeList.addInterviewee(sampleInterviewee);
        }

        return sampleIntervieweeList;
    }

    public static ReadOnlyInterviewerList getSampleInterviewerList() {
        InterviewerList sampleInterviewerList = new InterviewerList();

        for (Interviewer sampleInterviewer : getSampleInterviewers()) {
            sampleInterviewerList.addInterviewer(sampleInterviewer);
        }

        return sampleInterviewerList;
    }

    public static List<Schedule> getSampleSchedulesList() {
        LinkedList<Schedule> sampleSchedulesList = new LinkedList<>();
        String date = sampleFilledTable[0][0];
        LinkedList<LinkedList<String>> sampleData = toTwoDimensionalLinkedList(sampleFilledTable);
        sampleSchedulesList.add(new Schedule(date, sampleData));

        return sampleSchedulesList;
    }

    /**
     * Returns the given two dimensional array of strings as a two dimensional LinkedList of strings.
     */
    private static LinkedList<LinkedList<String>> toTwoDimensionalLinkedList(String[][] table) {
        LinkedList<LinkedList<String>> tableCopy = new LinkedList<>();
        for (String[] row : table) {
            LinkedList<String> rowCopy = new LinkedList<>(Arrays.asList(row));
            tableCopy.add(rowCopy);
        }
        return tableCopy;
    }
    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
    /**
     * Returns a department list containing the list of strings given.
     */
    public static List<Department> getDepartmentList(String... strings) {
        return Arrays.stream(strings)
                .map(Department::new)
                .collect(Collectors.toList());
    }

    /**
     * Returns a time slot list containing the list of strings given.
     */
    public static List<Slot> getTimeslotList(String...timeslots) {
        return Arrays.stream(timeslots)
                .map(Slot::new)
                .collect(Collectors.toList());
    }
}

