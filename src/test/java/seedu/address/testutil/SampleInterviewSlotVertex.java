package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.graph.InterviewSlotVertex;
import seedu.address.model.person.InterviewSlot;
import seedu.address.model.person.Interviewer;

/**
 * Provides sample interview slot vertices.
 */
public class SampleInterviewSlotVertex {
    public static List<InterviewSlotVertex> getSampleInterviewSlotVerticesGraph1() {
        List<Interviewer> interviewers = SampleInterviewer.getSampleInterviewersForGraph1();

        InterviewSlot interviewSlot0 = new InterviewSlot("26/10/2019", "18:00", "18:30",
                interviewers.get(0));
        InterviewSlot interviewSlot1 = new InterviewSlot("26/10/2019", "18:30", "19:00",
                interviewers.get(0));
        InterviewSlot interviewSlot2 = new InterviewSlot("27/10/2019", "20:00", "20:30",
                interviewers.get(1));
        InterviewSlot interviewSlot3 = new InterviewSlot("27/10/2019", "20:30", "21:00",
                interviewers.get(1));
        InterviewSlot interviewSlot4 = new InterviewSlot("28/10/2019", "19:00", "19:30",
                interviewers.get(2));

        InterviewSlotVertex interviewSlotVertex0 = new InterviewSlotVertex(interviewSlot0, 0);
        InterviewSlotVertex interviewSlotVertex1 = new InterviewSlotVertex(interviewSlot1, 1);
        InterviewSlotVertex interviewSlotVertex2 = new InterviewSlotVertex(interviewSlot2, 2);
        InterviewSlotVertex interviewSlotVertex3 = new InterviewSlotVertex(interviewSlot3, 3);
        InterviewSlotVertex interviewSlotVertex4 = new InterviewSlotVertex(interviewSlot4, 4);

        List<InterviewSlotVertex> interviewSlotsVertices = new ArrayList<>();
        interviewSlotsVertices.add(interviewSlotVertex0);
        interviewSlotsVertices.add(interviewSlotVertex1);
        interviewSlotsVertices.add(interviewSlotVertex2);
        interviewSlotsVertices.add(interviewSlotVertex3);
        interviewSlotsVertices.add(interviewSlotVertex4);

        return interviewSlotsVertices;
    }
}
