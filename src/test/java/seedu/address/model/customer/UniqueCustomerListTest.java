package seedu.address.model.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCustomers.CUSTOMERONE;
import static seedu.address.testutil.TypicalCustomers.CUSTOMERTWO;
import static seedu.address.testutil.TypicalCustomers.DEFAULT_TAG_3;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.customer.exceptions.CustomerNotFoundException;
import seedu.address.model.customer.exceptions.DuplicateCustomerException;
import seedu.address.testutil.CustomerBuilder;

public class UniqueCustomerListTest {

    private final UniqueCustomerList uniqueCustomerList = new UniqueCustomerList();

    @Test
    public void contains_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.contains(null));
    }

    @Test
    public void contains_customerNotInList_returnsFalse() {
        assertFalse(uniqueCustomerList.contains(CUSTOMERONE));
    }

    @Test
    public void contains_customerInList_returnsTrue() {
        uniqueCustomerList.add(CUSTOMERONE);
        assertTrue(uniqueCustomerList.contains(CUSTOMERONE));
    }

    @Test
    public void contains_customerWithSameIdentityFieldsInList_returnsTrue() {
        uniqueCustomerList.add(CUSTOMERONE);
        Customer editedCustomer = new CustomerBuilder(CUSTOMERONE).withTags(DEFAULT_TAG_3).build();
        assertTrue(uniqueCustomerList.contains(editedCustomer));
    }

    @Test
    public void add_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.add(null));
    }

    @Test
    public void add_duplicateCustomer_throwsDuplicateCustomerException() {
        uniqueCustomerList.add(CUSTOMERONE);
        assertThrows(DuplicateCustomerException.class, () -> uniqueCustomerList.add(CUSTOMERONE));
    }

    @Test
    public void setCustomer_nullTargetCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.setCustomer(null, CUSTOMERONE));
    }

    @Test
    public void setCustomer_nullEditedCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.setCustomer(CUSTOMERONE, null));
    }

    @Test
    public void setCustomer_targetCustomerNotInList_throwsCustomerNotFoundException() {
        assertThrows(CustomerNotFoundException.class, () -> uniqueCustomerList.setCustomer(CUSTOMERONE, CUSTOMERONE));
    }

    @Test
    public void setCustomer_editedCustomerIsSameCustomer_success() {
        uniqueCustomerList.add(CUSTOMERONE);
        uniqueCustomerList.setCustomer(CUSTOMERONE, CUSTOMERONE);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        expectedUniqueCustomerList.add(CUSTOMERONE);
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setCustomer_editedCustomerHasSameIdentity_success() {
        uniqueCustomerList.add(CUSTOMERONE);
        Customer editedCustomer = new CustomerBuilder(CUSTOMERONE).withTags(DEFAULT_TAG_3).build();
        uniqueCustomerList.setCustomer(CUSTOMERONE, editedCustomer);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        expectedUniqueCustomerList.add(editedCustomer);
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void set_editedCustomerHasDifferentIdentity_success() {
        uniqueCustomerList.add(CUSTOMERONE);
        uniqueCustomerList.setCustomer(CUSTOMERONE, CUSTOMERTWO);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        expectedUniqueCustomerList.add(CUSTOMERTWO);
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setCustomer_editedCustomerHasNonUniqueIdentity_throwsDuplicateCustomerException() {
        uniqueCustomerList.add(CUSTOMERONE);
        uniqueCustomerList.add(CUSTOMERTWO);
        assertThrows(DuplicateCustomerException.class, () -> uniqueCustomerList.setCustomer(CUSTOMERONE, CUSTOMERTWO));
    }

    @Test
    public void remove_nullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.remove(null));
    }

    @Test
    public void remove_customerDoesNotExist_throwsCustomerNotFoundException() {
        assertThrows(CustomerNotFoundException.class, () -> uniqueCustomerList.remove(CUSTOMERONE));
    }

    @Test
    public void remove_existingCustomer_removesCustomer() {
        uniqueCustomerList.add(CUSTOMERONE);
        uniqueCustomerList.remove(CUSTOMERONE);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setCustomers_nullUniqueCustomerList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.setCustomers((UniqueCustomerList) null));
    }

    @Test
    public void setCustomers_uniqueCustomerList_replacesOwnListWithProvidedUniqueCustomerList() {
        uniqueCustomerList.add(CUSTOMERONE);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        expectedUniqueCustomerList.add(CUSTOMERTWO);
        uniqueCustomerList.setCustomers(expectedUniqueCustomerList);
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setCustomers_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCustomerList.setCustomers((List<Customer>) null));
    }

    @Test
    public void setCustomers_list_replacesOwnListWithProvidedList() {
        uniqueCustomerList.add(CUSTOMERONE);
        List<Customer> customerList = Collections.singletonList(CUSTOMERTWO);
        uniqueCustomerList.setCustomers(customerList);
        UniqueCustomerList expectedUniqueCustomerList = new UniqueCustomerList();
        expectedUniqueCustomerList.add(CUSTOMERTWO);
        assertEquals(expectedUniqueCustomerList, uniqueCustomerList);
    }

    @Test
    public void setCustomers_listWithDuplicateCustomers_throwsDuplicateCustomerException() {
        List<Customer> listWithDuplicateCustomers = Arrays.asList(CUSTOMERONE, CUSTOMERONE);
        assertThrows(DuplicateCustomerException.class, (
                ) -> uniqueCustomerList.setCustomers(listWithDuplicateCustomers)
        );
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, (
        ) -> uniqueCustomerList.asUnmodifiableObservableList().remove(0));
    }
}
