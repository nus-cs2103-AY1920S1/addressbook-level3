package seedu.address.model.order;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOrders.ORDERONE;
import static seedu.address.testutil.TypicalOrders.ORDERTWO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.order.exceptions.DuplicateOrderException;
import seedu.address.model.order.exceptions.OrderNotFoundException;

class UniqueOrderListTest {

    private UniqueOrderList uniqueOrderList = new UniqueOrderList();

    @Test
    void contains_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueOrderList.contains(null));
    }

    @Test
    void contains_orderNotInList_returnsFalse() {
        assertFalse(uniqueOrderList.contains(ORDERONE));
    }

    @Test
    void contains_orderInList_returnsTrue() {
        uniqueOrderList.add(ORDERONE);
        assertTrue(uniqueOrderList.contains(ORDERONE));
    }

    @Test
    void add_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueOrderList.add(null));
    }

    @Test
    void add_duplicateOrder_throwsDuplicateOrderException() {
        uniqueOrderList.add(ORDERONE);
        assertThrows(DuplicateOrderException.class, () -> uniqueOrderList.add(ORDERONE));
    }

    @Test
    void add_uniqueOrder_success() {
        uniqueOrderList.add(ORDERONE);
        assertTrue(uniqueOrderList.contains(ORDERONE));
    }

    @Test
    void setOrder_nullTarget_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueOrderList.setOrder(null, ORDERONE));
    }

    @Test
    void setOrder_nullEditedOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueOrderList.setOrder(ORDERONE, null));
    }

    @Test
    void setOrder_targetNotInList_throwsOrderNotFoundException() {
        assertThrows(OrderNotFoundException.class, () -> uniqueOrderList.setOrder(ORDERONE, ORDERTWO));
    }

    @Test
    void setOrder_targetSameAsEdited_success() {
        uniqueOrderList.add(ORDERONE);
        uniqueOrderList.setOrder(ORDERONE, ORDERONE);
        uniqueOrderList.contains(ORDERONE);
    }

    @Test
    void setOrder_targetNotSameAsEditedAndEditedIsDuplicate_throwsDuplicateOrderException() {
        uniqueOrderList.add(ORDERONE);
        uniqueOrderList.add(ORDERTWO);
        assertThrows(DuplicateOrderException.class, () -> uniqueOrderList.setOrder(ORDERTWO, ORDERONE));
    }

    @Test
    void setOrder_targetNotSameAsEditedAndEditedIsNotDuplicate_success() {
        uniqueOrderList.add(ORDERONE);
        uniqueOrderList.setOrder(ORDERONE, ORDERTWO);
        assertTrue(uniqueOrderList.contains(ORDERTWO));
    }

    @Test
    void remove_nullOrder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueOrderList.remove(null));
    }

    @Test
    void remove_orderNotInList_throwsOrderNotFoundException() {
        assertThrows(OrderNotFoundException.class, () -> uniqueOrderList.remove(ORDERONE));
    }

    @Test
    void remove_orderInList_success() {
        uniqueOrderList.add(ORDERONE);
        uniqueOrderList.remove(ORDERONE);
        assertFalse(uniqueOrderList.contains(ORDERONE));
    }

    @Test
    void setOrders_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueOrderList.setOrders((List<Order>) null));
    }

    @Test
    void setOrders_nullUniqueOrderList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueOrderList.setOrders((UniqueOrderList) null));
    }

    @Test
    void setOrders_uniqueOrderList_success() {
        UniqueOrderList newList = new UniqueOrderList();
        newList.add(ORDERONE);
        uniqueOrderList.setOrders(newList);
        assertTrue(uniqueOrderList.contains(ORDERONE));
    }

    @Test
    void setOrders_listWithDuplicates_throwsDuplicateOrderException() {
        List<Order> newList = Arrays.asList(ORDERONE, ORDERONE);
        assertThrows(DuplicateOrderException.class, () -> uniqueOrderList.setOrders(newList));
    }

    @Test
    void setOrders_listWithNoDuplicates_success() {
        List<Order> newList = Collections.singletonList(ORDERONE);
        uniqueOrderList.setOrders(newList);
        assertTrue(uniqueOrderList.contains(ORDERONE));
    }

    @Test
    void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueOrderList.asUnmodifiableObservableList().remove(0));
    }

}
