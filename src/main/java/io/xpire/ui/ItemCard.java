package io.xpire.ui;

import java.util.Comparator;
import java.util.Optional;

import io.xpire.commons.util.DateUtil;
import io.xpire.model.item.Item;
import io.xpire.model.item.ReminderDate;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Item}.
 */
public class ItemCard extends UiPart<Region> {

    private static final String FXML = "ItemCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     */

    public final Item item;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label expiryDate;
    @FXML
    private Label quantity;
    @FXML
    private FlowPane tags;
    @FXML
    private Label reminder;

    public ItemCard(Item item, int displayedIndex) {
        super(FXML);
        this.item = item;
        this.id.setText(displayedIndex + ". ");
        this.name.setText(item.getName().toString());
        this.expiryDate.setText(item.getExpiryDate().toString());
        this.quantity.setText("Quantity: " + item.getQuantity().toString());
        Optional<ReminderDate> reminderDate = DateUtil.getReminderDate(
                item.getExpiryDate().getDate(), item.getReminderThreshold().getValue());
        if (reminderDate.isPresent()) {
            this.reminder.setText(reminderDate.get().toString());
        } else {
            this.reminder.setVisible(false);
        }
        this.item.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.getTagName()))
                .forEach(tag -> this.tags.getChildren().add(new Label(tag.getTagName())));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ItemCard)) {
            return false;
        }

        // state check
        ItemCard card = (ItemCard) other;
        return id.getText().equals(card.id.getText())
                && item.equals(card.item);
    }
}
