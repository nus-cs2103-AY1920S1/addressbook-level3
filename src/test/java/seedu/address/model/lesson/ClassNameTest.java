package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSNAME_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLASSNAME_MATH;

import org.junit.jupiter.api.Test;

public class ClassNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ClassName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new ClassName(invalidName));
    }

    @Test
    public void isValidClassName() {
        // null name
        assertThrows(NullPointerException.class, () -> ClassName.isValidClassName(null));

        // invalid name
        assertFalse(ClassName.isValidClassName("")); // empty string
        assertFalse(ClassName.isValidClassName(" ")); // spaces only
        assertFalse(ClassName.isValidClassName("^")); // only non-alphanumeric characters
        assertFalse(ClassName.isValidClassName("math*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(ClassName.isValidClassName("monday class")); // alphabets only
        assertTrue(ClassName.isValidClassName("math 4E7")); // alphanumeric characters
        assertTrue(ClassName.isValidClassName("English")); // with capital letters
    }

    @Test
    public void equals() {
        ClassName eng = new ClassName(VALID_CLASSNAME_ENGLISH);
        ClassName math = new ClassName(VALID_CLASSNAME_MATH);
        ClassName mon = new ClassName("Monday Class");
        ClassName anotherEng = new ClassName(VALID_CLASSNAME_ENGLISH);

        //null name-> returns false
        assertFalse(eng.equals(null));

        //same object-> returns true
        assertTrue(eng.equals(eng));
        assertTrue(eng.equals(anotherEng));

        //different object-> returns false
        assertFalse(eng.equals("Hi"));
        assertFalse(eng.equals(1));

        //different class name string -> returns false
        assertFalse(eng.equals(math));
        assertFalse(eng.equals(mon));
    }
}
