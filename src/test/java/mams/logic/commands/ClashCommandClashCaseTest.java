package mams.logic.commands;

import static mams.testutil.TypicalModules.CS1010;
import static mams.testutil.TypicalModules.CS1020;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ClashCommandClashCaseTest {

    private ClashCommand.ClashCase clashCase;
    private ArrayList<Integer> clashingSlots;

    @BeforeEach
    public void setUp() {
        clashCase = new ClashCommand.ClashCase();
        clashingSlots = new ArrayList<>();
    }

    @Test
    public void equals() {

        clashCase.setModuleA(CS1010);
        clashCase.setModuleB(CS1020);
        clashingSlots.addAll(Arrays.asList(1, 2, 45, 46, 47));
        clashCase.setClashingSlots(clashingSlots);

        // same object -> returns true
        assertTrue(clashCase.equals(clashCase));

        // different types -> returns false
        assertFalse(clashCase.equals(0));
        assertFalse(clashCase.equals(CS1010));

        // null -> returns false
        assertFalse(clashCase.equals(null));

        ClashCommand.ClashCase anotherClashCase = new ClashCommand.ClashCase();
        ArrayList<Integer> anotherClashingSlots = new ArrayList<>();
        anotherClashingSlots.addAll(Arrays.asList(1, 2));

        // same value -> returns true
        anotherClashCase.setModuleA(CS1010);
        anotherClashCase.setModuleB(CS1020);
        anotherClashCase.setClashingSlots(clashingSlots);
        assertTrue(clashCase.equals(anotherClashCase));

        // different modules -> returns false
        anotherClashCase.setModuleA(CS1020);
        assertFalse(clashCase.equals(anotherClashCase));

        // different clashing slots -> returns false
        anotherClashCase.setModuleA(CS1010);
        anotherClashCase.setClashingSlots(anotherClashingSlots);
        assertFalse(clashCase.equals(anotherClashCase));

    }

    @Test
    public void setter_setsCorrectFields() {

        clashCase.setModuleA(CS1010);
        clashCase.setModuleB(CS1020);
        clashingSlots.addAll(Arrays.asList(1, 2, 45, 46, 47));
        clashCase.setClashingSlots(clashingSlots);

        // first module
        assertEquals("CS1010", clashCase.getModuleCodeA());

        // second module
        assertEquals("CS1020", clashCase.getModuleCodeB());

        // clashing slots
        assertEquals(CS1010.getModuleTimeTableToString(), clashCase.getClashingSlots());
    }
}
