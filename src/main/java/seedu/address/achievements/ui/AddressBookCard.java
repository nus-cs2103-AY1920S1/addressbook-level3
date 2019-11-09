package seedu.address.achievements.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import seedu.address.address.model.util.AddressBookStatistics;

/**
 * An UI component that displays the various achievements in a card.
 */
public class AddressBookCard {

    /**
     * Card for {@link AddressBookStatistics}.
     * @param addressBookStatistics
     * @return observable list of nodes that makes up the {@code AddressBookCard}
     */
    public static ObservableList<Node> make(AddressBookStatistics addressBookStatistics) {
        return FXCollections.observableArrayList(
                new AchievementsTitleLabel("Address Book").getRoot(),
                new AchievementsDataLabel("Total Contacts: ",
                        addressBookStatistics.getTotalPersons() + " / 200").getRoot(),
                new AchievementsProgressBar(addressBookStatistics.getTotalPersons() / 200.0).getRoot(),
                new AchievementsHorizontalBarChart("Contacts", "Number", "Countries",
                        addressBookStatistics.getAddressChartData()).getRoot());
    }
}
