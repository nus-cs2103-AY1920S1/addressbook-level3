package seedu.scheduler.logic.graph;

import seedu.scheduler.model.person.Slot;

/**
 * A vertex that wraps an interview slot into it.
 */
public class InterviewerSlotVertex extends Vertex<InterviewerSlot, IntervieweeVertex> implements
        Comparable<InterviewerSlotVertex> {
    public InterviewerSlotVertex(InterviewerSlot item, int index) {
        super(item, index);
    }

    @Override
    public int compareTo(InterviewerSlotVertex other) {
        Slot thisSlot = this.getItem().getSlot();
        Slot otherSlot = other.getItem().getSlot();

        return thisSlot.compareTo(otherSlot);
    }
}
