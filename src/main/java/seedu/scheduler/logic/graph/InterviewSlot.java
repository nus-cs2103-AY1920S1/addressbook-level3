package seedu.scheduler.logic.graph;

import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.Slot;

/**
 * Represents an interview slot with information regarding the interviewer that will be doing the interview
 * during that slot.
 */
public class InterviewSlot {
    private Interviewer interviewer;
    private Slot slot;

    public InterviewSlot(Slot slot, Interviewer interviewer) {
        this.slot = slot;
        this.interviewer = interviewer;
    }

    public Interviewer getInterviewer() {
        return interviewer;
    }

    public Slot getSlot() {
        return slot;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else {
            return this.slot.equals(((InterviewSlot) other).getSlot())
                    && this.interviewer.equals(((InterviewSlot) other).getInterviewer());
        }
    }
}
