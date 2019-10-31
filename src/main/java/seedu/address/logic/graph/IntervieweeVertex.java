package seedu.address.logic.graph;

import seedu.address.model.person.Interviewee;

/**
 * A vertex that wraps an interviewee into it.
 */
public class IntervieweeVertex extends Vertex<Interviewee, InterviewSlotVertex> {
    public IntervieweeVertex(Interviewee interviewee, int index) {
        super(interviewee, index);
    }
}
