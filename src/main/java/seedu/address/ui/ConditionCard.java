package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.reminders.conditions.ClassCondition;
import seedu.address.model.reminders.conditions.Condition;
import seedu.address.model.reminders.conditions.DateCondition;
import seedu.address.model.reminders.conditions.KeyWordsCondition;
import seedu.address.model.reminders.conditions.QuotaCondition;
import seedu.address.model.reminders.conditions.TagsCondition;
/**
 * An UI component that displays information of a {@code Condition}.
 */
public class ConditionCard extends UiPart<Region> {

    private static final String FXML = "ConditionListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
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
        String descWithType = "[" + conditionType + "] ";
        type.setText(descWithType);

        switch(conditionType.toLowerCase()) {
        case "class condition":
            params.getChildren().add(new Label(((ClassCondition) condition).getEntryType().toString()));
            break;
        case "date condition":
            params.getChildren().add(new Label("Start: " + ((DateCondition) condition).getStart().toString()));
            params.getChildren().add(new Label("End: " + ((DateCondition) condition).getEnd().toString()));
            break;
        case "keyword condition":
            KeyWordsCondition keyWordsCondition = (KeyWordsCondition) condition;
            keyWordsCondition.getKeywords().stream()
                    .sorted()
                    .forEach(keyword -> params.getChildren().add(new Label(keyword)));
            break;
        case "quota condition":
            params.getChildren().add(new Label(String.valueOf(((QuotaCondition) condition).getQuota())));
            break;
        case "tags condition":
            TagsCondition tagsCondition = (TagsCondition) condition;
            tagsCondition.getTagList().stream()
                    .sorted(Comparator.comparing(tag -> tag.tagName))
                    .forEach(tag -> params.getChildren().add(new Label(tag.tagName)));
            break;
        default:
        }
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
