package seedu.scheduler.testutil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import seedu.scheduler.model.person.DefaultValues;
import seedu.scheduler.model.person.Department;
import seedu.scheduler.model.person.Email;
import seedu.scheduler.model.person.Emails;
import seedu.scheduler.model.person.Faculty;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.model.person.Phone;
import seedu.scheduler.model.person.Slot;

/**
 * Provides sample interviewees.
 */
public class SampleInterviewee {
    private static final String[] ALPHABETS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

    /**
     * Returns sample interviewees for the graph 1 in the sample graph data.
     */
    public static List<Interviewee> getSampleIntervieweesForGraph1() {
        List<Slot> slots = SampleSlot.getSampleSlotsForGraph1();

        List<Slot> slots1 = new LinkedList<>();
        slots1.add(slots.get(0));

        List<Slot> slots2 = new LinkedList<>();
        slots2.add(slots.get(0));
        slots2.add(slots.get(1));
        slots2.add(slots.get(2));

        List<Slot> slots3 = new LinkedList<>();
        slots3.add(slots.get(0));
        slots3.add(slots.get(3));

        List<Slot> slots4 = new LinkedList<>();
        slots4.add(slots.get(1));
        slots4.add(slots.get(4));

        List<Slot> slots5 = new LinkedList<>();
        slots5.add(slots.get(2));
        slots5.add(slots.get(3));

        List<Department> technical = new LinkedList<>();
        technical.add(new Department("Technical"));

        Interviewee interviewee1 = new Interviewee.IntervieweeBuilder(new Name("Bernard"), new Phone("11112222"),
                new HashSet<>()).availableTimeslots(slots1).departmentChoices(technical).build();
        Interviewee interviewee2 = new Interviewee.IntervieweeBuilder(new Name("Jessie"), new Phone("11112222"),
                new HashSet<>()).availableTimeslots(slots2).departmentChoices(technical).build();
        Interviewee interviewee3 = new Interviewee.IntervieweeBuilder(new Name("Charlie"), new Phone("11112222"),
                new HashSet<>()).availableTimeslots(slots3).departmentChoices(technical).build();
        Interviewee interviewee4 = new Interviewee.IntervieweeBuilder(new Name("Bane"), new Phone("11112222"),
                new HashSet<>()).availableTimeslots(slots4).departmentChoices(technical).build();
        Interviewee interviewee5 = new Interviewee.IntervieweeBuilder(new Name("HemsWorth"), new Phone("11112222"),
                new HashSet<>()).availableTimeslots(slots5).departmentChoices(technical).build();

        List<Interviewee> interviewees = new LinkedList<>();
        interviewees.add(interviewee1);
        interviewees.add(interviewee2);
        interviewees.add(interviewee3);
        interviewees.add(interviewee4);
        interviewees.add(interviewee5);

        return interviewees;
    }

    /**
     * Generates a sample interviewee list.
     * @return a sample interviewee list.
     */
    public static ArrayList<Interviewee> getSampleIntervieweeList() {
        ArrayList<Interviewee> expectedInterviewees = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Name name = new Name("John " + ALPHABETS[i]);
            Emails emails = new Emails(new HashMap<>());
            emails.addNusEmail(new Email("john" + ALPHABETS[i] + "@u.nus.edu"));
            emails.addPersonalEmail(new Email("john" + ALPHABETS[i] + "@hotmail.com"));
            Phone phone = new Phone("9999999" + i);
            Faculty faculty = new Faculty("NUS");
            Integer yearOfStudy = 1;
            ArrayList<Department> choiceOfDepartments = new ArrayList<>();
            choiceOfDepartments.add(new Department("publicity"));
            List<Slot> availableTimeSlots = new ArrayList<>();
            availableTimeSlots.add(Slot.fromString("09/10/2019 18:30-19:00"));
            availableTimeSlots.add(Slot.fromString("10/10/2019 19:00-19:30"));
            availableTimeSlots.add(Slot.fromString("11/10/2019 20:00-20:30"));
            Interviewee interviewee = new Interviewee.IntervieweeBuilder(name, phone, DefaultValues.DEFAULT_TAGS)
                    .availableTimeslots(availableTimeSlots)
                    .departmentChoices(choiceOfDepartments)
                    .emails(emails)
                    .yearOfStudy(yearOfStudy)
                    .faculty(faculty)
                    .build();
            expectedInterviewees.add(interviewee);
        }
        return expectedInterviewees;
    }
}
