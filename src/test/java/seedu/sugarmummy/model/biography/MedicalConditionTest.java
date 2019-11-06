package seedu.sugarmummy.model.biography;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MedicalConditionTest {

    @Test
    public void isValidMedicalCondition_emptyString() {
        assertFalse(MedicalCondition.isValidMedicalCondition(""));
    }

    @Test
    public void isValidMedicalCondition_firstExampleMedicalCondition() {
        assertTrue(MedicalCondition.isValidMedicalCondition("Type II Diabetes"));
    }

    @Test
    public void isValidMedicalCondition_secondExampleMedicalCondition() {
        assertTrue(MedicalCondition.isValidMedicalCondition("High Blood Pressure"));
    }

    @Test
    public void testToString() {
        assertEquals("Type II Diabetes", (new MedicalCondition("Type II Diabetes")).toString());
    }

    @Test
    public void testEquals_sameMedicalCondition() {
        assertEquals(new MedicalCondition("Type II Diabetes"),
                new MedicalCondition("Type II Diabetes"));
    }

    @Test
    public void testEquals_differentMedicalCondition() {
        assertNotEquals(new MedicalCondition("Type II Diabetes"),
                new MedicalCondition("Type I Diabetes"));
    }

    @Test
    public void testEquals_object() {
        assertNotEquals(new Object(),
                new MedicalCondition("Type II Diabetes"));
    }

    @Test
    public void testHashCode_sameMedicalCondition() {
        assertEquals(new MedicalCondition("Type II Diabetes").hashCode(),
                new MedicalCondition("Type II Diabetes").hashCode());
    }

    @Test
    public void testHashCode_differentMedicalCondition() {
        assertNotEquals(new MedicalCondition("Type II Diabetes").hashCode(),
                new MedicalCondition("Type I Diabetes").hashCode());
    }

    @Test
    public void testHashCode_object() {
        assertNotEquals(new Object().hashCode(),
                new MedicalCondition("Type II Diabetes").hashCode());
    }
}
