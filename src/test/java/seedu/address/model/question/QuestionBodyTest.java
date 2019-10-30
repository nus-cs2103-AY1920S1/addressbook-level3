package seedu.address.model.question;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class QuestionBodyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new QuestionBody(null));
    }

    @Test
    public void constructor_invalidQuestionBody_throwsIllegalArgumentException() {
        String invalidQuestionBody = "";
        assertThrows(IllegalArgumentException.class, () -> new QuestionBody(invalidQuestionBody));
    }

    @Test
    void isValidQuestionBody() {
        // null difficulty
        assertThrows(NullPointerException.class, () -> QuestionBody.isValidQuestionBody(null));

        // invalid difficulty
        assertFalse(QuestionBody.isValidQuestionBody("")); // empty string
        assertFalse(QuestionBody.isValidQuestionBody(" ")); // spaces only

        // valid phone numbers
        assertTrue(QuestionBody.isValidQuestionBody("Why?"));
        assertTrue(QuestionBody.isValidQuestionBody("Is it real?"));
        assertTrue(QuestionBody.isValidQuestionBody("A huge file is transferred over an existing TCP connection "
                + "(i.e., 3-way handshake is already done). The connection is still open after transmission. "
                + "The first and last TCP segments have the sequence numbers 12,345 and 2,105 respectively. MSS is "
                + "1,024 bytes and TCP sends as much data as possible in a segment.\n"
                + "How many TCP segments are used to transfer the file, assuming the communication channel "
                + "is perfectly reliable?")); // long question body
    }
}
