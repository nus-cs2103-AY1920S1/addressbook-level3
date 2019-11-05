package seedu.scheduler.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.scheduler.logic.graph.InterviewerSlot;
import seedu.scheduler.logic.graph.InterviewerSlotVertex;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.Slot;

/**
 * Provides sample interview slot vertices.
 */
public class SampleInterviewSlotVertex {
    public static List<InterviewerSlotVertex> getSampleInterviewSlotVerticesGraph1() {
        List<Interviewer> interviewers = SampleInterviewer.getSampleInterviewersForGraph1();

        InterviewerSlot interviewerSlot0 = new InterviewerSlot(new Slot("26/10/2019", "18:00", "18:30"),
                interviewers.get(0));
        InterviewerSlot interviewerSlot1 = new InterviewerSlot(new Slot("26/10/2019", "18:30", "19:00"),
                interviewers.get(0));
        InterviewerSlot interviewerSlot2 = new InterviewerSlot(new Slot("27/10/2019", "20:00", "20:30"),
                interviewers.get(1));
        InterviewerSlot interviewerSlot3 = new InterviewerSlot(new Slot("27/10/2019", "20:30", "21:00"),
                interviewers.get(1));
        InterviewerSlot interviewerSlot4 = new InterviewerSlot(new Slot("28/10/2019", "19:00", "19:30"),
                interviewers.get(2));

        InterviewerSlotVertex interviewerSlotVertex0 = new InterviewerSlotVertex(interviewerSlot0, 0);
        InterviewerSlotVertex interviewerSlotVertex1 = new InterviewerSlotVertex(interviewerSlot1, 1);
        InterviewerSlotVertex interviewerSlotVertex2 = new InterviewerSlotVertex(interviewerSlot2, 2);
        InterviewerSlotVertex interviewerSlotVertex3 = new InterviewerSlotVertex(interviewerSlot3, 3);
        InterviewerSlotVertex interviewerSlotVertex4 = new InterviewerSlotVertex(interviewerSlot4, 4);

        List<InterviewerSlotVertex> interviewSlotsVertices = new ArrayList<>();
        interviewSlotsVertices.add(interviewerSlotVertex0);
        interviewSlotsVertices.add(interviewerSlotVertex1);
        interviewSlotsVertices.add(interviewerSlotVertex2);
        interviewSlotsVertices.add(interviewerSlotVertex3);
        interviewSlotsVertices.add(interviewerSlotVertex4);

        return interviewSlotsVertices;
    }
}
