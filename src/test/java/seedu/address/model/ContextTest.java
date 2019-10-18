package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.activity.Activity;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalActivities;
import seedu.address.testutil.TypicalPersons;

public class ContextTest {
    private Person[] personList = TypicalPersons.getTypicalPersons().toArray(new Person[1]);
    private Activity[] activityList = TypicalActivities.getTypicalActivities().toArray(new Activity[1]);

    @Test
    public void getMethods_incorrectType_returnsOptionalEmpty() {
        assertEquals(new Context().getActivity(), Optional.empty());
        assertEquals(new Context(TypicalPersons.ALICE).getActivity(), Optional.empty());
        assertEquals(new Context(personList).getActivity(), Optional.empty());
        assertEquals(new Context(activityList).getActivity(), Optional.empty());

        assertEquals(new Context().getContact(), Optional.empty());
        assertEquals(new Context(TypicalActivities.BREAKFAST).getContact(), Optional.empty());
        assertEquals(new Context(personList).getContact(), Optional.empty());
        assertEquals(new Context(activityList).getContact(), Optional.empty());

        assertEquals(new Context().getActivityList(), Optional.empty());
        assertEquals(new Context(TypicalPersons.ALICE).getActivityList(), Optional.empty());
        assertEquals(new Context(TypicalActivities.BREAKFAST).getActivityList(), Optional.empty());
        assertEquals(new Context(personList).getActivityList(), Optional.empty());

        assertEquals(new Context().getContactList(), Optional.empty());
        assertEquals(new Context(TypicalPersons.ALICE).getContactList(), Optional.empty());
        assertEquals(new Context(TypicalActivities.BREAKFAST).getContactList(), Optional.empty());
        assertEquals(new Context(activityList).getContactList(), Optional.empty());
    }

    @Test
    public void getMethods_correctType_returnsOptionalContainingType() {
        assertEquals(new Context(TypicalPersons.ALICE).getContact(), Optional.of(TypicalPersons.ALICE));
        assertEquals(new Context(TypicalActivities.BREAKFAST).getActivity(), Optional.of(TypicalActivities.BREAKFAST));
        assertEquals(new Context(personList).getContactList(), Optional.of(TypicalPersons.getTypicalPersons()));
        assertEquals(
                new Context(activityList).getActivityList(),
                Optional.of(TypicalActivities.getTypicalActivities()));
    }

    @Test
    public void getType_allTypes_returnsCorrectType() {
        assertEquals(new Context().getType(), Context.Type.MAIN);
        assertEquals(new Context(TypicalPersons.ALICE).getType(), Context.Type.VIEW_CONTACT);
        assertEquals(new Context(TypicalActivities.BREAKFAST).getType(), Context.Type.VIEW_ACTIVITY);
        assertEquals(new Context(personList).getType(), Context.Type.LIST_CONTACT);
        assertEquals(new Context(activityList).getType(), Context.Type.LIST_ACTIVITY);
    }
}
