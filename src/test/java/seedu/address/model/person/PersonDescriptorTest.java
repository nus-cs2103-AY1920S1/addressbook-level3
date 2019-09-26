package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonDescriptorBuilder;

class PersonDescriptorTest {

    private PersonDescriptor personDescriptorEmpty = new PersonDescriptor();
    private PersonDescriptor personDescriptorAlice = new PersonDescriptorBuilder(ALICE).build();

    @Test
    void isAnyFieldEdited() {
        // empty descriptor -> false
        assertFalse(personDescriptorEmpty.isAnyFieldEdited());

        // only name field edited -> true
        personDescriptorEmpty.setName(new Name(VALID_NAME_AMY));
        assertTrue(personDescriptorEmpty.isAnyFieldEdited());

        // all fields edited -> true
        assertTrue(personDescriptorAlice.isAnyFieldEdited());
    }


    @Test
    void getName() {
        assertEquals(personDescriptorAlice.getName(), ALICE.getName());
        assertNotEquals(personDescriptorAlice.getName(), BENSON.getName());
    }

    @Test
    void getPhone() {
        assertEquals(personDescriptorAlice.getPhone(), ALICE.getPhone());
        assertNotEquals(personDescriptorAlice.getPhone(), BENSON.getPhone());
    }

    @Test
    void getEmail() {
        assertEquals(personDescriptorAlice.getEmail(), ALICE.getEmail());
        assertNotEquals(personDescriptorAlice.getEmail(), BENSON.getEmail());
    }

    @Test
    void getAddress() {
        assertEquals(personDescriptorAlice.getAddress(), ALICE.getAddress());
        assertNotEquals(personDescriptorAlice.getAddress(), BENSON.getAddress());
    }

    @Test
    void getRemark() {
        assertEquals(personDescriptorAlice.getRemark(), ALICE.getRemark());
        assertNotEquals(personDescriptorAlice.getRemark(), BENSON.getRemark());
    }

    @Test
    void getTags() {
        assertEquals(personDescriptorAlice.getTags(), ALICE.getTags());
        assertNotEquals(personDescriptorAlice.getTags(), BENSON.getTags());
    }
}
