package seedu.address.model.display.sidepanel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_DESCRIPTION1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_DESCRIPTION2;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME1;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP_NAME2;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.BENSON;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.group.GroupDescription;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Person;
import seedu.address.testutil.personutil.PersonBuilder;

class GroupDisplayTest {

    private GroupDisplay groupDisplay1;
    private GroupDisplay groupDisplay2;


    @BeforeEach
    void init() {
        groupDisplay1 = new GroupDisplay(
                GROUP_NAME1,
                GROUP_DESCRIPTION1
        );

        groupDisplay2 = new GroupDisplay(
                GROUP_NAME2,
                GROUP_DESCRIPTION2
        );
    }

    @Test
    void constructor_arrayListOfPersons() {
        ArrayList<Person> persons = new ArrayList<>();
        persons.add(new PersonBuilder(ALICE).build());
        persons.add(new PersonBuilder(BENSON).build());

        GroupDisplay groupDisplayPersons = new GroupDisplay(persons);

        GroupDisplay groupDisplayCopy = new GroupDisplay(
                new GroupName("Temporary Group"),
                new GroupDescription("A group of:\n" + ALICE.getName().toString() + "\n"
                        + BENSON.getName().toString() + "\n")
        );

        assertTrue(groupDisplayCopy.equals(groupDisplayPersons));
    }

    @Test
    void equals() {
        assertTrue(groupDisplay1.equals(groupDisplay1));
        assertFalse(groupDisplay1.equals(null));
        assertFalse(groupDisplay1.equals(groupDisplay2));
    }

    @Test
    void getGroupName() {
        assertEquals(GROUP_NAME1, groupDisplay1.getGroupName());
    }

    @Test
    void getGroupDescription() {
        assertEquals(GROUP_DESCRIPTION1, groupDisplay1.getGroupDescription());
    }
}
