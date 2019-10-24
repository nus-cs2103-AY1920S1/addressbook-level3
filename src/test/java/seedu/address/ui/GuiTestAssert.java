package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.cards.CustomerCardHandle;
import guitests.guihandles.cards.OrderCardHandle;
import guitests.guihandles.cards.PersonCardHandle;
import guitests.guihandles.panels.PersonListPanelHandle;

import seedu.address.model.customer.Customer;
import seedu.address.model.order.Order;
import seedu.address.model.person.Person;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(PersonCardHandle expectedCard, PersonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getTags(), actualCard.getTags());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(Person expectedPerson, PersonCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getEmail().value, actualCard.getEmail());
        assertEquals(expectedPerson.getAddress().value, actualCard.getAddress());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).sorted().collect(Collectors.toList()),
                actualCard.getTags());
    }

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
     * Asserts that {@code actualCard} displays the details of {@code expectedOrder}.
     */
    public static void assertCardDisplaysOrder(Order expectedOrder, OrderCardHandle actualCard) {
        assertEquals(expectedOrder.getCustomer().getCustomerName(), actualCard.getCustomerName());
        assertEquals(expectedOrder.getCustomer().getContactNumber().value, actualCard.getContactNumber());
        assertEquals(expectedOrder.getPrice().value, actualCard.getOrderPrice());
        assertEquals(expectedOrder.getStatus().toString(), actualCard.getOrderStatus());
        assertEquals(expectedOrder.getTags().stream().map(tag -> tag.tagName).sorted().collect(Collectors.toList()),
                actualCard.getTags());
    //havent added phone equals here
    }


    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, Person... persons) {
        for (int i = 0; i < persons.length; i++) {
            personListPanelHandle.navigateToCard(i);
            assertCardDisplaysPerson(persons[i], personListPanelHandle.getPersonCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(PersonListPanelHandle personListPanelHandle, List<Person> persons) {
        assertListMatching(personListPanelHandle, persons.toArray(new Person[0]));
    }

    /**
     * Asserts the size of the list in {@code personListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(PersonListPanelHandle personListPanelHandle, int size) {
        int numberOfPeople = personListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
