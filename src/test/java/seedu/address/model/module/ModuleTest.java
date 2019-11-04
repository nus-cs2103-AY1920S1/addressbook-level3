package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.websocket.Cache;

class ModuleTest {
    private Module module1;
    private Module module2;
    private Module module3;

    @BeforeEach
    void setUp() {
        module1 = Cache.loadModule(new ModuleId("2019/2020", "CS2030")).get();
        module2 = Cache.loadModule(new ModuleId("2019/2020", "CS2030")).get();
        module3 = Cache.loadModule(new ModuleId("2019/2020", "CS2103T")).get();
    }

    @Test
    void testGetters() {
        assertEquals(new ModuleId("2019/2020", "CS2030"), module1.getModuleId());
        assertEquals(new ModuleCode("CS2030"), module1.getModuleCode());
        assertEquals(new AcadYear("2019/2020"), module1.getAcadYear());
    }

    @Test
    void getSemester() {
        Semester sem1 = module1.getSemester(SemesterNo.SEMESTER_1);
        assertEquals(SemesterNo.SEMESTER_1, sem1.getSemesterNo());
    }

    @Test
    void testToString() {
        assertEquals("AY2019/2020 CS2030 Programming Methodology II", module1.toString());
    }

    @Test
    void testEquals() {
        // same values -> return true
        assertTrue(module1.equals(module2));

        // same object -> return true
        assertTrue(module1.equals(module1));

        // null -> returns false
        assertFalse(module1.equals(null));

        // different values -> returns false
        assertFalse(module1.equals(module3));
    }

    @Test
    void testHashCode() {
        assertEquals(module1.hashCode(), module1.hashCode());
        assertEquals(module1.hashCode(), module2.hashCode());
        assertNotEquals(module1.hashCode(), module3.hashCode());
    }
}
