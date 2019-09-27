package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.BENSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonDescriptorTest {

    private PersonDescriptor personDescriptorEmpty;
    private PersonDescriptor alice;

    @BeforeEach
    void init() {
        personDescriptorEmpty = new PersonDescriptor();
        alice = ALICE;
    }

    @Test
    void isAnyFieldEdited_false() {
        // empty descriptor -> false
        assertFalse(personDescriptorEmpty.isAnyFieldEdited());
    }

    @Test
    void isAnyFieldEdited_onlyNameTrue() {
        // only name field edited -> true
        personDescriptorEmpty.setName(ALICE.getName());
        assertTrue(personDescriptorEmpty.isAnyFieldEdited());
    }

    @Test
    void isAnyFieldEdited_allFieldsTrue() {
        // all fields edited -> true
        assertTrue(alice.isAnyFieldEdited());
    }


    @Test
    void getName() {
        assertEquals(alice.getName(), ALICE.getName());
        assertNotEquals(alice.getName(), BENSON.getName());
    }

    @Test
    void getPhone() {
        assertEquals(alice.getPhone(), ALICE.getPhone());
        assertNotEquals(alice.getPhone(), BENSON.getPhone());
    }

    @Test
    void getEmail() {
        assertEquals(alice.getEmail(), ALICE.getEmail());
        assertNotEquals(alice.getEmail(), BENSON.getEmail());
    }

    @Test
    void getAddress() {
        assertEquals(alice.getAddress(), ALICE.getAddress());
        assertNotEquals(alice.getAddress(), BENSON.getAddress());
    }

    @Test
    void getRemark() {
        assertEquals(alice.getRemark(), ALICE.getRemark());
        assertNotEquals(alice.getRemark(), BENSON.getRemark());
    }

    @Test
    void getTags() {
        assertEquals(alice.getTags(), ALICE.getTags());
        assertNotEquals(alice.getTags(), BENSON.getTags());
    }
}
