package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Collectors;

import guitests.guihandles.cards.PhoneCardHandle;
import seedu.address.model.phone.Phone;

/**
 * assertion for Phone class (not just customer phone)
 * will integrate with main test assert class  when person code removed
 */
public class GuiTestAssertPhone {
    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPhone}.
     */
    public static void assertCardDisplaysPhone(Phone expectedPhone, PhoneCardHandle actualCard) {
        assertEquals(expectedPhone.getPhoneName().fullName, actualCard.getName());
        assertEquals(expectedPhone.getIdentityNumber().value, actualCard.getIdentityNumber());
        assertEquals(expectedPhone.getSerialNumber().value, actualCard.getSerialNumber());
        assertEquals(expectedPhone.getBrand().value, actualCard.getBrand());
        assertEquals(expectedPhone.getCapacity().value, actualCard.getCapacity());
        assertEquals(expectedPhone.getColour().value, actualCard.getColour());
        assertEquals(expectedPhone.getCost().value, actualCard.getCost());
        assertEquals(expectedPhone.getTags().stream().map(tag -> tag.tagName).sorted().collect(Collectors.toList()),
                actualCard.getTags());
    }

}
