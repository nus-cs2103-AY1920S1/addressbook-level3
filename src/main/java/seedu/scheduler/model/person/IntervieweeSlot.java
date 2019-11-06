package seedu.scheduler.model.person;

/**
 * Represents a container for a slot and the interviewee which will be interviewing at the timing of the slot.
 */
public class IntervieweeSlot implements Comparable<IntervieweeSlot> {
    private Interviewee interviewee;
    private Slot slot;

    public IntervieweeSlot(Interviewee interviewee, Slot slot) {
        this.interviewee = interviewee;
        this.slot = slot;
    }

    public Interviewee getInterviewee() {
        return interviewee;
    }

    public Slot getSlot() {
        return slot;
    }

    @Override
    public int compareTo(IntervieweeSlot other) {
        return this.slot.compareTo(other.slot);
    }

    @Override
    public String toString() {
        return String.format("%s, %s", interviewee, slot);
    }
}
