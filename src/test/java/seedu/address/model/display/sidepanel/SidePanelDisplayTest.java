package seedu.address.model.display.sidepanel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.grouputil.TypicalGroups.GROUP1;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.group.Group;
import seedu.address.testutil.personutil.PersonBuilder;

class SidePanelDisplayTest {

    private ArrayList<PersonDisplay> personDisplays;
    private ArrayList<GroupDisplay> groupDisplays;
    private SidePanelDisplay sidePanelDisplayPerson;
    private SidePanelDisplay sidePanelDisplayGroup;

    @BeforeEach
    void init() {

        personDisplays = new ArrayList<>();
        personDisplays.add(new PersonDisplay(new PersonBuilder(ALICE).build()));

        groupDisplays = new ArrayList<>();
        groupDisplays.add(new GroupDisplay(new Group(GROUP1)));

        sidePanelDisplayPerson = new SidePanelDisplay(
                personDisplays,
                groupDisplays,
                SidePanelDisplayType.PERSON
        );

        sidePanelDisplayGroup = new SidePanelDisplay(
                personDisplays,
                groupDisplays,
                SidePanelDisplayType.GROUP
        );
    }

    @Test
    void getPersonDisplay() {
        assertEquals(personDisplays, sidePanelDisplayPerson.getPersonDisplay());
        assertEquals(personDisplays, sidePanelDisplayGroup.getPersonDisplay());
        assertNotEquals(groupDisplays, sidePanelDisplayGroup.getPersonDisplay());

    }

    @Test
    void getGroupDisplay() {
        assertEquals(groupDisplays, sidePanelDisplayPerson.getGroupDisplay());
        assertEquals(groupDisplays, sidePanelDisplayGroup.getGroupDisplay());
        assertNotEquals(personDisplays, sidePanelDisplayGroup.getGroupDisplay());

    }

    @Test
    void getSidePanelDisplayType() {
        assertEquals(SidePanelDisplayType.PERSON,
                sidePanelDisplayPerson.getSidePanelDisplayType());

        assertEquals(SidePanelDisplayType.GROUP,
                sidePanelDisplayGroup.getSidePanelDisplayType());

        assertNotEquals(SidePanelDisplayType.GROUP,
                sidePanelDisplayPerson.getSidePanelDisplayType());
    }
}
