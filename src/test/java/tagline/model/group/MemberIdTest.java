//@@author e0031374
package tagline.model.group;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class MemberIdTest {
    @Test
    public void constructor_invalidMemberId_throwsIllegalArgumentException() {
        String invalidMemberId1 = "";
        assertThrows(IllegalArgumentException.class, () -> new MemberId(invalidMemberId1));

        String invalidMemberId2 = "12asd";
        assertThrows(IllegalArgumentException.class, () -> new MemberId(invalidMemberId2));

        String invalidMemberId3 = "one";
        assertThrows(IllegalArgumentException.class, () -> new MemberId(invalidMemberId3));
    }

    @Test
    public void isValidNote() {
        // null phone number
        assertThrows(NullPointerException.class, () -> MemberId.isValidMemberId(null));

        // invalid noteId numbers
        assertFalse(MemberId.isValidMemberId("")); // empty string
        assertFalse(MemberId.isValidMemberId(" ")); // spaces only
        //assertFalse(MemberId.isValidMemberId("91")); // less than 3 numbers
        assertFalse(MemberId.isValidMemberId("phone")); // non-numeric
        assertFalse(MemberId.isValidMemberId("9011p041")); // alphabets within digits
        assertFalse(MemberId.isValidMemberId("9312 1534")); // spaces within digits
        assertFalse(MemberId.isValidMemberId("124293842033123")); // long overflow numbers
        assertFalse(MemberId.isValidMemberId("9312153")); // exactly 7 numbers
        assertFalse(MemberId.isValidMemberId("12345678")); // exactly 8 numbers
        assertFalse(MemberId.isValidMemberId("123456789")); // exactly 9 numbers

        // valid noteId numbers
        assertTrue(MemberId.isValidMemberId("911")); // exactly 3 numbers
        assertTrue(MemberId.isValidMemberId("1234")); // exactly 4 numbers
        assertTrue(MemberId.isValidMemberId("12345")); // exactly 5 numbers
    }

    @Test
    public void toString_test() {

        MemberId one = new MemberId("001");
        assertTrue(one.value.equals("1"));
        assertTrue(one.toString().equals("[00001]"));

        MemberId two = new MemberId("2");
        assertTrue(two.value.equals("2"));
        assertTrue(two.toString().equals("[00002]"));

        MemberId fifty = new MemberId("00050");
        assertTrue(fifty.value.equals("50"));
        assertTrue(fifty.toString().equals("[00050]"));

        MemberId hundred = new MemberId("100");
        assertTrue(hundred.value.equals("100"));
        assertTrue(hundred.toString().equals("[00100]"));

        MemberId twoThousand = new MemberId("02000");
        assertTrue(twoThousand.value.equals("2000"));
        assertTrue(twoThousand.toString().equals("[02000]"));

        MemberId fortyTwoThousand = new MemberId("42000");
        assertTrue(fortyTwoThousand.value.equals("42000"));
        assertTrue(fortyTwoThousand.toString().equals("[42000]"));

        MemberId fiveNines = new MemberId("99999");
        assertTrue(fiveNines.value.equals("99999"));
        assertTrue(fiveNines.toString().equals("[99999]"));

        MemberId countdown = new MemberId("54321");
        assertTrue(countdown.value.equals("54321"));
        assertTrue(countdown.toString().equals("[54321]"));

    }

    @Test
    public void equals_test() {

        MemberId one = new MemberId("10");
        assertTrue(one.equals(new MemberId("10")));

        MemberId two = new MemberId("2");
        assertTrue(two.equals(new MemberId("2")));

        MemberId fifty = new MemberId("00050");
        assertTrue(fifty.equals(new MemberId("50")));

    }
}
