package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DescriptionTest {

    public static final Description MILK = new Description("Milk");
    public static final Description MAN = new Description("Man");

    @Test
    public void validDescription_true() {
        assertTrue(Description.isValidDescription("a"));
        assertTrue(Description.isValidDescription("0"));
        assertTrue(Description.isValidDescription("abd"));
        assertTrue(Description.isValidDescription("MiLk"));
        assertTrue(Description.isValidDescription("miLK 2"));
        assertTrue(Description.isValidDescription("Milk Man"));
        assertTrue(Description.isValidDescription("Milk Man 2"));
    }

    @Test
    public void validDescription_false() {
        assertFalse(Description.isValidDescription(""));
        assertFalse(Description.isValidDescription(" "));
        assertFalse(Description.isValidDescription("!"));
        assertFalse(Description.isValidDescription("a@"));
        assertFalse(Description.isValidDescription(" !"));
        assertFalse(Description.isValidDescription("Milk Man !"));
        assertFalse(Description.isValidDescription("Milk ! Man"));
        assertFalse(Description.isValidDescription("Milk 0!"));
        assertFalse(Description.isValidDescription("Poor_Milk"));
    }

    @Test
    public void equals_true() {
        assertTrue(MILK.equals(MILK));
    }

    @Test
    public void equals_false() {
        assertFalse(MILK.equals(MAN));
        assertFalse(MAN.equals(MILK));
        assertFalse(MAN.equals(null));
    }

    @Test
    public void hashCode_returnsTrue() {
        assertTrue(MILK.hashCode() == new Description("Milk").hashCode());
    }

    @Test
    public void hashCode_returnsFalse() {
        assertFalse(MILK.hashCode() == new Description("MilkMan").hashCode());
    }
}
