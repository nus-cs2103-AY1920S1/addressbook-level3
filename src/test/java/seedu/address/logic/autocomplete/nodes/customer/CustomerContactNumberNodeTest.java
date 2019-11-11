package seedu.address.logic.autocomplete.nodes.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCustomers.CUSTOMERONE;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.customer.Customer;

class CustomerContactNumberNodeTest {

    @Test
    void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CustomerContactNumberNode(null));
    }

    @Test
    void getValues_emptyList_emptySet() {
        CustomerContactNumberNode node = new CustomerContactNumberNode(Collections.emptyList());
        assertEquals(node.getValues(), Collections.emptySortedSet());
    }

    @Test
    void getValues_nonEmptyList_nonEmptySet() {
        List<Customer> customerList = Collections.singletonList(CUSTOMERONE);
        CustomerContactNumberNode node = new CustomerContactNumberNode(customerList);
        assertTrue(() -> !node.getValues().isEmpty());
    }

}
