package seedu.pluswork.ui.views;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.pluswork.commons.util.DateTimeUtil;
import seedu.pluswork.model.calendar.Meeting;
import seedu.pluswork.model.member.MemberName;
import seedu.pluswork.ui.UiPart;

public class IndivMeetingCard extends UiPart<Region> {
    private static final String FXML = "IndivMeetingCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on ProjectDashboard level 4</a>
     */

    private final Meeting meeting;

    @FXML
    private HBox cardPane;
    @FXML
    private Label displayIndex;
    @FXML
    private Label meetingTime;
    @FXML
    private Label memberListDisplay;
    @FXML
    private SplitPane split_pane_indiv;

    public IndivMeetingCard(int index, Meeting meeting) {
        super(FXML);
        this.meeting = meeting;

        displayIndex.setText(index + ". ");

        meetingTime.setText(DateTimeUtil.displayDateTime(meeting.getStartTime()) + " - "
                + meeting.getEndTime().toLocalTime().toString());
        ObservableList<MemberName> memberList = meeting.getMemberNameList();
        String memberListString = "";
        for (int i = 0; i < memberList.size(); i++) {
            memberListString += (i + 1) + ". " + memberList.get(i).fullName + "\n";
        }
        memberListDisplay.setText(memberListString);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof IndivMeetingCard)) {
            return false;
        }

        // state check
        IndivMeetingCard card = (IndivMeetingCard) other;
        return meeting.equals(card.meeting);
    }
}
