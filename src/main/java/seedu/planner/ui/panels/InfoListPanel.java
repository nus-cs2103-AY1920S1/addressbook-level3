package seedu.planner.ui.panels;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.result.ResultInformation;
import seedu.planner.model.accommodation.Accommodation;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.contact.Contact;
import seedu.planner.model.day.ActivityWithTime;
import seedu.planner.ui.UiPart;
import seedu.planner.ui.cards.AccommodationCardFull;
import seedu.planner.ui.cards.ActivityCardFull;
import seedu.planner.ui.cards.ActivityWithTimeCardFull;
import seedu.planner.ui.cards.ContactCardFull;

//@@author 1nefootstep
/**
 * Panel containing activity/accommodation/contact/day information.
 */
public class InfoListPanel extends UiPart<Region> {
    private static final String FXML = "InfoListPanel.fxml";

    @FXML
    private ListView<Node> infoListView;

    public InfoListPanel() {
        super(FXML);
    }

    /**
     * Displays the relevant information in infoList from the command executed.
     */
    public void changeInfo(ResultInformation[] resultInformation) {
        infoListView.getItems().clear();
        for (ResultInformation i : resultInformation) {
            i.getAccommodation().ifPresent(accommodation ->
                    addAccommodationInfo(accommodation, i.getIndex(), i.getDescription().orElse(""))
            );
            i.getActivity().ifPresent(activity ->
                    addActivityInfo(activity, i.getIndex(), i.getDescription().orElse(""))
            );
            i.getContact().ifPresent(contact ->
                    addContactInfo(contact, i.getIndex(), i.getDescription().orElse(""))
            );
            i.getActivityWithTime().ifPresent(activityWithTime ->
                    addActivityWithTimeInfo(activityWithTime, i.getIndex())
            );
        }
    }

    private void addAccommodationInfo(Accommodation accommodation, Index displayedIndex, String description) {
        infoListView.getItems().add(
                new AccommodationCardFull(accommodation, displayedIndex.getOneBased(), description).getRoot());
    }

    private void addContactInfo(Contact contact, Index displayedIndex, String description) {
        infoListView.getItems().add(
                new ContactCardFull(contact, displayedIndex.getOneBased(), description).getRoot());
    }

    private void addActivityInfo(Activity activity, Index displayedIndex, String description) {
        infoListView.getItems().add(
                new ActivityCardFull(activity, displayedIndex.getOneBased(), description).getRoot());
    }

    private void addActivityWithTimeInfo(ActivityWithTime activityWithTime, Index displayedIndex) {
        infoListView.getItems().add(
                new ActivityWithTimeCardFull(activityWithTime, displayedIndex.getOneBased()).getRoot());
    }
}
