package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import static seedu.address.testutil.TypicalPhones.getTypicalPhones;
import static seedu.address.ui.GuiTestAssertPhone.assertCardDisplaysPhone;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import guitests.guihandles.cards.PhoneCardHandle;
import guitests.guihandles.panels.PhoneListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.phone.Brand;
import seedu.address.model.phone.Capacity;
import seedu.address.model.phone.Colour;
import seedu.address.model.phone.Cost;
import seedu.address.model.phone.IdentityNumber;
import seedu.address.model.phone.Phone;
import seedu.address.model.phone.PhoneName;
import seedu.address.model.phone.SerialNumber;
import seedu.address.ui.panels.PhoneListPanel;

public class PhoneListPanelTest extends GuiUnitTest {
    private static final ObservableList<Phone> TYPICAL_PHONE =
            FXCollections.observableList(getTypicalPhones());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private final SimpleObjectProperty<Phone> selectedPhone = new SimpleObjectProperty<>();
    private PhoneListPanelHandle phoneListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_PHONE);

        for (int i = 0; i < TYPICAL_PHONE.size(); i++) {
            phoneListPanelHandle.navigateToCard(TYPICAL_PHONE.get(i));
            Phone expectedPhone = TYPICAL_PHONE.get(i);
            PhoneCardHandle actualCard = phoneListPanelHandle.getPhoneCardHandle(i);

            assertCardDisplaysPhone(expectedPhone, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }


    /**
     * Verifies that creating and deleting large number of phones in {@code phoneistPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */

    @Test
    public void performanceTest() {
        ObservableList<Phone> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of person cards exceeded time limit");
    }


    /**
     * Returns a list of phones containing {@code phoneCount} phone that is used to populate the
     * {@code PhoneListPanel}.
     */
    private ObservableList<Phone> createBackingList(int personCount) {
        ObservableList<Phone> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < personCount; i++) {
            IdentityNumber idNum = new IdentityNumber("222222222222222");
            PhoneName phoneName = new PhoneName("TestIphone");
            SerialNumber serialNumber = new SerialNumber("182n8x81");
            Brand brand = new Brand("APPLE");
            Cost cost = new Cost("$1000");
            Colour colour = new Colour("black");
            Phone customer = new Phone(idNum, serialNumber, phoneName, brand, Capacity.SIZE_8GB, colour,
                    cost, Collections.emptySet());
            backingList.add(customer);
        }
        return backingList;
    }

    /**
     * Initializes {@code phoneListPanelHandle} with a {@code PhoneListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code PhoneListPanel}.
     */

    private void initUi(ObservableList<Phone> backingList) {
        PhoneListPanel phoneListPanel =
                new PhoneListPanel(backingList);
        uiPartExtension.setUiPart(phoneListPanel);

        phoneListPanelHandle = new PhoneListPanelHandle(getChildNode(phoneListPanel.getRoot(),
                PhoneListPanelHandle.PHONE_LIST_VIEW));
    }
}
