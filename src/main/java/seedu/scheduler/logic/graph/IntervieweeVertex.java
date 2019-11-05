package seedu.scheduler.logic.graph;

import seedu.scheduler.model.person.Interviewee;

/**
 * A vertex that wraps an interviewee into it.
 */
public class IntervieweeVertex extends Vertex<Interviewee, InterviewerSlotVertex> {
    public IntervieweeVertex(Interviewee interviewee, int index) {
        super(interviewee, index);
    }
}
