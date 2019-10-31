package seedu.address.testutil;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import seedu.address.model.person.Department;
import seedu.address.model.person.Interviewee;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Slot;

/**
 * Provides sample interviewees.
 */
public class SampleInterviewee {
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
}
