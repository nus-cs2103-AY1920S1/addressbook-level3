package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals(new Context().getType(), ContextType.MAIN);
        assertEquals(new Context(TypicalPersons.ALICE).getType(), ContextType.VIEW_CONTACT);
        assertEquals(new Context(TypicalActivities.BREAKFAST).getType(), ContextType.VIEW_ACTIVITY);
        assertEquals(Context.newListActivityContext().getType(), ContextType.LIST_ACTIVITY);
        assertEquals(Context.newListContactContext().getType(), ContextType.LIST_CONTACT);
    }
}
