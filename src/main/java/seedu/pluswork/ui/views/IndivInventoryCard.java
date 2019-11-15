package seedu.pluswork.ui.views;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.pluswork.model.inventory.Inventory;
import seedu.pluswork.model.member.Member;
import seedu.pluswork.model.task.Task;
import seedu.pluswork.ui.UiPart;

public class IndivInventoryCard extends UiPart<Region> {
    private static final String FXML = "InventoryListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on ProjectDashboard level 4</a>
     */

    public final Inventory inventory;

    @javafx.fxml.FXML
    private HBox cardPane;
    @FXML
    private Label InvName;
    @FXML
    private Label Price;
    @FXML
    private Label id;
    @FXML
    private Label Member;
    @FXML
    private Label Task;
    @FXML
    private ImageView imageView;
    @FXML
    private FlowPane tags;
    @FXML
    private Text listTasks;
    @FXML
    private SplitPane split_pane_indiv;

    public IndivInventoryCard(Inventory inventory, Member member, int displayedIndex, Task task) {
        super(FXML);
        this.inventory = inventory;
        id.setText(displayedIndex + ". ");
        InvName.setText(inventory.getName().fullName);
        Price.setText(inventory.getPrice().toString());
        Member.setText(member.getName().fullName);
        Task.setText(task.getName().fullName);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TaskCard)) {
            return false;
        }

        // state check
        IndivInventoryCard card = (IndivInventoryCard) other;
        return id.getText().equals(card.id.getText())
                && inventory.equals(card.inventory);
    }
}
