package seedu.address.logic.graph;

import seedu.address.model.person.InterviewSlot;

/**
 * A vertex that wraps an interview slot into it.
 */
public class InterviewSlotVertex extends Vertex<InterviewSlot, IntervieweeVertex> implements
        Comparable<InterviewSlotVertex> {
    public InterviewSlotVertex(InterviewSlot item, int index) {
        super(item, index);
    }

    @Override
    public int compareTo(InterviewSlotVertex other) {
        InterviewSlot thisInterviewSlot = this.getItem();
        InterviewSlot otherInterviewSlot = other.getItem();

        return thisInterviewSlot.compareTo(otherInterviewSlot);
    }
}
