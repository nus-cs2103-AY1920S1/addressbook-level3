package seedu.scheduler.model.person;

import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.scheduler.testutil.Assert.assertThrows;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWEE;

import org.junit.jupiter.api.Test;

import seedu.scheduler.testutil.IntervieweeBuilder;

class IntervieweeTest {
    // TODO: Implementation
    @Test
    public void getTags_modifyUnderlyingTagSet_failure() {
        // The underlying tag set is unmodifiable, and will throw an exception if we try to change it.
        Interviewee interviewee = new IntervieweeBuilder(ALICE_INTERVIEWEE)
                .withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND).build();
        assertThrows(UnsupportedOperationException.class, () -> interviewee.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> true

        // null -> returns false
        // different phone and email -> returns false
        // different name -> returns false
        // same name, same phone, different attributes -> returns true
        // same name, same email, different attributes -> returns true
        // same name, same phone, same email, different attributes -> returns true
    }
}
