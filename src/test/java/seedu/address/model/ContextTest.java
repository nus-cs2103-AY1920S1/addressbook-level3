package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TypicalActivities;
import seedu.address.testutil.TypicalPersons;

public class ContextTest {

    @Test
    public void getMethods_incorrectType_returnsOptionalEmpty() {
        assertEquals(new Context().getActivity(), Optional.empty());
        assertEquals(new Context(TypicalPersons.ALICE).getActivity(), Optional.empty());
        assertEquals(Context.newListActivityContext().getActivity(), Optional.empty());
        assertEquals(Context.newListContactContext().getActivity(), Optional.empty());

        assertEquals(new Context().getContact(), Optional.empty());
        assertEquals(new Context(TypicalActivities.BREAKFAST).getContact(), Optional.empty());
        assertEquals(Context.newListActivityContext().getContact(), Optional.empty());
        assertEquals(Context.newListContactContext().getContact(), Optional.empty());
    }

    @Test
    public void getMethods_correctType_returnsOptionalContainingType() {
        assertEquals(new Context(TypicalPersons.ALICE).getContact(), Optional.of(TypicalPersons.ALICE));
        assertEquals(new Context(TypicalActivities.BREAKFAST).getActivity(), Optional.of(TypicalActivities.BREAKFAST));
    }

    @Test
    public void getType_allTypes_returnsCorrectType() {
        assertEquals(new Context().getType(), ContextType.LIST_CONTACT);
        assertEquals(new Context(TypicalPersons.ALICE).getType(), ContextType.VIEW_CONTACT);
        assertEquals(new Context(TypicalActivities.BREAKFAST).getType(), ContextType.VIEW_ACTIVITY);
        assertEquals(Context.newListActivityContext().getType(), ContextType.LIST_ACTIVITY);
        assertEquals(Context.newListContactContext().getType(), ContextType.LIST_CONTACT);
    }

    @Test
    public void equals() {
        Context listContext = Context.newListActivityContext();
        Context viewActivityContext = new Context(TypicalActivities.BREAKFAST);
        Context viewPersonContext = new Context(TypicalPersons.ALICE);

        // identity -> returns true
        assertTrue(listContext.equals(listContext));
        assertTrue(viewPersonContext.equals(viewPersonContext));

        // different ContextType -> returns false
        assertFalse(listContext.equals(Context.newListContactContext()));

        // same ContextType but different Activity -> returns false
        assertFalse(viewActivityContext.equals(new Context(TypicalActivities.LUNCH)));

        // same ContextType but different Person -> returns false
        assertFalse(viewPersonContext.equals(new Context(TypicalPersons.BENSON)));
    }

    @Test
    public void hashcode() {
        Context listContext = Context.newListActivityContext();
        Context viewContext = new Context(TypicalPersons.ALICE);

        // same Context values -> returns same hashcode
        assertEquals(listContext.hashCode(), Context.newListActivityContext().hashCode());
        assertEquals(viewContext.hashCode(), new Context(TypicalPersons.ALICE).hashCode());

        // different Context values -> returns different hashcodes
        assertNotEquals(listContext.hashCode(), Context.newListContactContext().hashCode());
        assertNotEquals(viewContext.hashCode(), new Context(TypicalPersons.AMY).hashCode());
        assertNotEquals(listContext.hashCode(), viewContext.hashCode());
    }
}
