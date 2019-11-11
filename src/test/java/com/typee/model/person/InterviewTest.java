package com.typee.model.person;

import static com.typee.logic.commands.CommandTestUtil.VALID_DESCRIPTION_GOOGLE_INTERVIEW;
import static com.typee.testutil.TypicalEngagements.GOOGLE_INTERVIEW;
import static com.typee.testutil.TypicalEngagements.TYPICAL_INTERVIEW;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.typee.model.engagement.Engagement;
import com.typee.testutil.EngagementBuilder;

public class InterviewTest {

    @Test
    public void isSameInterview() {
        // same object -> returns true
        assertTrue(TYPICAL_INTERVIEW.isSameEngagement(TYPICAL_INTERVIEW));

        // null -> returns false
        assertFalse(TYPICAL_INTERVIEW.isSameEngagement(null));

        // different description -> returns false
        Engagement editedInterview = new EngagementBuilder(TYPICAL_INTERVIEW)
                .withDescription(VALID_DESCRIPTION_GOOGLE_INTERVIEW)
                .buildAsInterview();
        assertFalse(TYPICAL_INTERVIEW.isSameEngagement(editedInterview));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Engagement interviewCopy = new EngagementBuilder(TYPICAL_INTERVIEW).buildAsInterview();
        assertTrue(TYPICAL_INTERVIEW.equals(interviewCopy));

        // same object -> returns true
        assertTrue(TYPICAL_INTERVIEW.equals(TYPICAL_INTERVIEW));

        // null -> returns false
        assertFalse(TYPICAL_INTERVIEW.equals(null));

        // different type -> returns false
        assertFalse(TYPICAL_INTERVIEW.equals(5));

        // different interview -> returns false
        assertFalse(TYPICAL_INTERVIEW.equals(GOOGLE_INTERVIEW));

        // different description -> returns false
        Engagement editedInterview = new EngagementBuilder(TYPICAL_INTERVIEW)
                .withDescription(VALID_DESCRIPTION_GOOGLE_INTERVIEW)
                .buildAsInterview();
        assertFalse(TYPICAL_INTERVIEW.equals(editedInterview));
    }

}
