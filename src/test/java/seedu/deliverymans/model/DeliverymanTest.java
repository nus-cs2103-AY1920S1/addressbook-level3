package seedu.deliverymans.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.deliverymans.model.deliveryman.deliverymanstatus.UniqueStatusList.AVAILABLE_STATUS;
import static seedu.deliverymans.model.deliveryman.deliverymanstatus.UniqueStatusList.UNAVAILABLE_STATUS;
import static seedu.deliverymans.model.util.SampleDataUtil.getMenu;
import static seedu.deliverymans.model.util.SampleDataUtil.getTagSet;

import org.junit.jupiter.api.Test;

import java.util.Set;

import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Phone;
import seedu.deliverymans.model.Tag;
import seedu.deliverymans.model.deliveryman.Deliveryman;
import seedu.deliverymans.model.deliveryman.deliverymanstatus.StatusTag;

public class DeliverymanTest {

    public static final Deliveryman TEST_DELIVERYMAN = new Deliveryman(new Name("TestDeliveryman"),
            new Phone("92345678"),
            getTagSet("same"),
            new StatusTag(AVAILABLE_STATUS));

    @Test
    public void isSameDeliveryman() {
        Deliveryman editedTestDeliveryman;

        // same object -> returns true
        assertTrue(TEST_DELIVERYMAN.isSameDeliveryman(TEST_DELIVERYMAN));

        // null -> returns false
        assertFalse(TEST_DELIVERYMAN.isSameDeliveryman(null));

        // different name -> returns false
        editedTestDeliveryman = new Deliveryman(new Name("DifferentTestDeliveryman"),
                new Phone("92345678"),
                getTagSet("same"),
                new StatusTag(AVAILABLE_STATUS));
        assertFalse(TEST_DELIVERYMAN.isSameDeliveryman(editedTestDeliveryman));

        // same name, different phone number -> returns false
        editedTestDeliveryman = new Deliveryman(new Name("TestDeliveryman"),
                new Phone("91111111"),
                getTagSet("same"),
                new StatusTag(AVAILABLE_STATUS));
        assertFalse(TEST_DELIVERYMAN.isSameDeliveryman(editedTestDeliveryman));

        // same name and phone number, different tags and status tag -> returns true
        editedTestDeliveryman = new Deliveryman(new Name("TestDeliveryman"),
                new Phone("92345678"),
                getTagSet("different"),
                new StatusTag(UNAVAILABLE_STATUS));
    }

    @Test
    public void equals() {
        Deliveryman editedTestDeliveryman;

        // same values -> returns true
        editedTestDeliveryman = new Deliveryman(new Name("TestDeliveryman"),
                new Phone("92345678"),
                getTagSet("same"),
                new StatusTag(AVAILABLE_STATUS));
        assertTrue(TEST_DELIVERYMAN.equals(editedTestDeliveryman));

        // same object -> returns true
        assertTrue(TEST_DELIVERYMAN.equals(TEST_DELIVERYMAN));

        // null -> returns false
        assertFalse(TEST_DELIVERYMAN.equals(null));

        // different type -> returns false
        assertFalse(TEST_DELIVERYMAN.equals(1));

        // different name -> returns false
        editedTestDeliveryman = new Deliveryman(new Name("DifferentTestDeliveryman"),
                new Phone("92345678"),
                getTagSet("same"),
                new StatusTag(AVAILABLE_STATUS));
        assertFalse(TEST_DELIVERYMAN.equals(editedTestDeliveryman));

        // different phone number -> returns false
        editedTestDeliveryman = new Deliveryman(new Name("TestDeliveryman"),
                new Phone("91111111"),
                getTagSet("same"),
                new StatusTag(AVAILABLE_STATUS));
        assertFalse(TEST_DELIVERYMAN.equals(editedTestDeliveryman));

        // different tags -> returns false
        editedTestDeliveryman = new Deliveryman(new Name("TestDeliveryman"),
                new Phone("92345678"),
                getTagSet("different"),
                new StatusTag(AVAILABLE_STATUS));
        assertFalse(TEST_DELIVERYMAN.equals(editedTestDeliveryman));

        // different status tag -> returns false
        editedTestDeliveryman = new Deliveryman(new Name("TestDeliveryman"),
                new Phone("92345678"),
                getTagSet("same"),
                new StatusTag(UNAVAILABLE_STATUS));
        assertFalse(TEST_DELIVERYMAN.equals(editedTestDeliveryman));
    }

}
