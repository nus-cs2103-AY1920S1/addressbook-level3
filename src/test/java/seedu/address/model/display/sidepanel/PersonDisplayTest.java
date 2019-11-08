package seedu.address.model.display.sidepanel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.BENSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.mapping.Role;
import seedu.address.model.person.Person;

class PersonDisplayTest {

    private PersonDisplay personDisplay;
    private PersonDisplay personDisplay2;
    private PersonDisplay personDisplay3;


    @BeforeEach
    void init() {
        personDisplay = new PersonDisplay(
                new Person(ALICE),
                new Role("role")
        );

        personDisplay2 = new PersonDisplay(
                new Person(BENSON),
                new Role("role2")
        );

        personDisplay3 = new PersonDisplay(
                ALICE.getName(),
                ALICE.getPhone(),
                ALICE.getEmail(),
                ALICE.getAddress(),
                ALICE.getRemark(),
                BENSON.getTags()
        );


    }

    @Test
    void getName() {
        assertEquals(ALICE.getName(), personDisplay.getName());
    }

    @Test
    void getPhone() {
        assertEquals(ALICE.getPhone(), personDisplay.getPhone());
    }

    @Test
    void getEmail() {
        assertEquals(ALICE.getEmail(), personDisplay.getEmail());
    }

    @Test
    void getAddress() {
        assertEquals(ALICE.getAddress(), personDisplay.getAddress());
    }

    @Test
    void getRemark() {
        assertEquals(ALICE.getRemark(), personDisplay.getRemark());
    }

    @Test
    void getTags() {
        assertEquals(ALICE.getTags(), personDisplay.getTags());
    }

    @Test
    void getRole() {
        assertEquals(new Role("role").toString(), personDisplay.getRole().toString());
    }

    @Test
    void equals() {
        assertTrue(personDisplay.equals(personDisplay));
        assertFalse(personDisplay.equals(null));
        assertFalse(personDisplay.equals(personDisplay2));
        assertFalse(personDisplay.equals(personDisplay3));

    }
}
