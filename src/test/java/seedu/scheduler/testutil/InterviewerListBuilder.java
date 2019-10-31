package seedu.scheduler.testutil;

import seedu.scheduler.model.InterviewerList;
import seedu.scheduler.model.person.Interviewer;

/**
 * A utility class to help with building InterviewerList objects.
 * Example usage: <br>
 *     {@code InterviewerList iverlist = new InterviewerListBuilder().withInterviewer("John", "Doe").build();}
 */
public class InterviewerListBuilder {

    private InterviewerList interviewerList;

    public InterviewerListBuilder() {
        this.interviewerList = new InterviewerList();
    }

    public InterviewerListBuilder(InterviewerList interviewerList) {
        this.interviewerList = interviewerList;
    }

    /**
     * Adds a new {@code Interviewer} to the {@code InterviewerList} that we are building.
     */
    public InterviewerListBuilder withInterviewer(Interviewer interviewer) {
        this.interviewerList.addEntity(interviewer);
        return this;
    }

    public InterviewerList build() {
        return this.interviewerList;
    }
}
