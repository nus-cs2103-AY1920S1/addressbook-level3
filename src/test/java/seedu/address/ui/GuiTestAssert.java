package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Collectors;

import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.cards.CustomerCardHandle;

import seedu.address.model.customer.Customer;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedCustomer}.
     */
    public static void assertCardDisplaysCustomer(Customer expectedCustomer, CustomerCardHandle actualCard) {
        assertEquals(expectedCustomer.getCustomerName().fullName, actualCard.getName());
        assertEquals(expectedCustomer.getContactNumber().value, actualCard.getContactNumber());
        assertEquals(expectedCustomer.getEmail().value, actualCard.getEmail());
        assertEquals(expectedCustomer.getTags().stream().map(tag -> tag.tagName).sorted().collect(Collectors.toList()),
                actualCard.getTags());
    }


    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
