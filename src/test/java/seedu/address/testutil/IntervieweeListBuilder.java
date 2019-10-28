package seedu.address.testutil;

import seedu.address.model.IntervieweeList;
import seedu.address.model.person.Interviewee;

/**
 * A utility class to help with building IntervieweeList objects.
 * Example usage: <br>
 *     {@code IntervieweeList iveelist = new IntervieweeListBuilder().withInterviewee("John", "Doe").build();}
 */
public class IntervieweeListBuilder {

    private IntervieweeList intervieweeList;

    public IntervieweeListBuilder() {
        this.intervieweeList = new IntervieweeList();
    }

    public IntervieweeListBuilder(IntervieweeList intervieweeList) {
        this.intervieweeList = intervieweeList;
    }

    /**
     * Adds a new {@code Interviewee} to the {@code IntervieweeList} that we are building.
     */
    public IntervieweeListBuilder withInterviewee(Interviewee interviewee) {
        this.intervieweeList.addInterviewee(interviewee);
        return this;
    }

    public IntervieweeList build() {
        return this.intervieweeList;
    }
}
