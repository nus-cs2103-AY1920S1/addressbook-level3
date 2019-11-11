package seedu.guilttrip.ui.condition;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.guilttrip.model.reminders.conditions.Condition;
import seedu.guilttrip.model.reminders.conditions.DateCondition;
import seedu.guilttrip.model.reminders.conditions.KeyWordsCondition;
import seedu.guilttrip.model.reminders.conditions.QuotaCondition;
import seedu.guilttrip.model.reminders.conditions.TypeCondition;

import seedu.guilttrip.ui.UiPart;

/**
 * An UI component that displays information of a {@code Condition}.
 */
public class ConditionCard extends UiPart<Region> {

    private static final String FXML = "condition/ConditionListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on GuiltTrip level 4</a>
     */

    public final Condition condition;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label type;
    @FXML
    private Label id;
    @FXML
    private FlowPane params;

    public ConditionCard(Condition condition, int displayedIndex) {
        super(FXML);
        this.condition = condition;
        id.setText(displayedIndex + ". ");
        String conditionType = condition.getConditionType();
        String descWithType = "";
        switch(conditionType.toLowerCase()) {
        case "class condition":
            descWithType += "[ Entry Type: ] ";
            params.getChildren().add(new Label(((TypeCondition) condition).getEntryType().toString()));
            break;
        case "date condition":
            DateCondition dateCondition = (DateCondition) condition;
            if (dateCondition.isStart()) {
                descWithType += "[ Starting Date: ] ";
                params.getChildren().add(new Label("Start: " + ((DateCondition) condition).getDate().toString()));
            } else {
                descWithType += "[ Ending Date: ] ";
                params.getChildren().add(new Label("End: " + ((DateCondition) condition).getDate().toString()));
            }
            break;
        case "Tags Condition":
            descWithType += "[ Tags: ] ";
            KeyWordsCondition keyWordsCondition = (KeyWordsCondition) condition;
            keyWordsCondition.getKeywords().stream()
                    .sorted()
                    .forEach(keyword -> params.getChildren().add(new Label(keyword)));
            break;
        case "quota condition":
            QuotaCondition quotaCondition = (QuotaCondition) condition;
            if (quotaCondition.isLowerBound()) {
                descWithType += "[ Lower Bound: ] ";
            } else {
                descWithType += "[ Upper Bound: ] ";
            }
            params.getChildren().add(new Label(String.valueOf(((QuotaCondition) condition).getQuota())));
            break;
        default:
        }
        type.setText(descWithType);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ConditionCard)) {
            return false;
        }

        // state check
        ConditionCard card = (ConditionCard) other;
        return id.getText().equals(card.id.getText())
                && condition.equals(card.condition);
    }
}
