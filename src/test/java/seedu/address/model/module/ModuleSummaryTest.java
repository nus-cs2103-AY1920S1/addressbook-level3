package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModuleSummaryTest {
    private ModuleSummary moduleSummary;
    private ModuleSummary moduleSummary2;

    @BeforeEach
    void setup() {
        moduleSummary = new ModuleSummary(
                new ModuleId("2018/2019", "CS2030"),
                new Title("Programming Methodology II"),
                List.of(1, 2, 3, 4));
        moduleSummary2 = new ModuleSummary(
                new ModuleId("2018/2019", "CS2103T"),
                new Title("Software Engineering"),
                List.of(1, 2));
    }

    @Test
    void testGetSemesters() {
        assertEquals(moduleSummary.getSemesters(), List.of(1, 2, 3, 4));
    }

    @Test
    void testGetTitle() {
        assertEquals(moduleSummary.getTitle(), new Title("Programming Methodology II"));
    }

    @Test
    void testGetModuleId() {
        assertEquals(moduleSummary.getModuleId(), new ModuleId("2018/2019", "CS2030"));
    }

    @Test
    void testEquals() {
        // same values -> return true
        assertTrue(moduleSummary.equals(new ModuleSummary(
                new ModuleId("2018/2019", "CS2030"),
                new Title("Programming Methodology II"),
                List.of(1, 2, 3, 4))));

        // same object -> return true
        assertTrue(moduleSummary.equals(moduleSummary));

        // null -> returns false
        assertFalse(moduleSummary.equals(null));

        // different moduleSummary -> returns false
        assertFalse(moduleSummary.equals(moduleSummary2));
    }

    @Test
    void testHashCode() {
        ModuleSummary modSum = new ModuleSummary(
                new ModuleId("2018/2019", "CS2030"),
                new Title("Programming Methodology II"),
                List.of(1, 2, 3, 4));
        assertEquals(moduleSummary.hashCode(), moduleSummary.hashCode());
        assertEquals(moduleSummary.hashCode(), modSum.hashCode());
        assertNotEquals(moduleSummary.hashCode(), moduleSummary2.hashCode());
    }
}
