package io.xpire.ui;

import io.xpire.commons.util.DateUtil;
import io.xpire.model.item.Item;
import io.xpire.model.item.XpireItem;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * An UI component that displays information of a {@code XpireItem}.
 */
public class ItemCard extends UiPart<Region> {

    private static final String FXML = "ItemCard.fxml";
    private static final String EXPIRED = "expired";
    private static final String REMIND = "remind";
    private static final String HEALTHY = "healthy";
    private static final String EXPIRY_DATE = "Expiry date: ";
    private static final String QUANTITY = "Quantity: ";
    private static final String REMINDER = "Reminder: ";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     */

    private XpireItem xpireItem;
    private Item replenishItem;

    @FXML
    private HBox cardPane;
    @FXML
    private HBox box;
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
    @FXML
    private Label status;
    @FXML
    private StackPane days;

    public ItemCard(XpireItem item, int displayedIndex) {
        super(FXML);
        this.xpireItem = item;
        this.id.setText(displayedIndex + ". ");
        this.name.setText(item.getName().toString());
        this.expiryDate.setText(EXPIRY_DATE + item.getExpiryDate().toString());
        this.quantity.setText(QUANTITY + item.getQuantity().toString());
        if (item.hasReminderThreshold()) {
            this.reminder.setText(REMINDER + getReminderDate());
        } else {
            this.reminder.setVisible(false);
        }
        this.xpireItem.getTags()
                .forEach(tag -> this.tags.getChildren().add(new Label(tag.getTagName())));

        this.status.setText(item.getExpiryDate().getStatus());

        this.box.setOnMouseClicked(e -> this.box.requestFocus());
        this.setColour();
    }
    //@@author febee99
    public ItemCard(Item replenishItem, int displayedIndex) {
        super(FXML);
        this.replenishItem = replenishItem;
        this.id.setText(displayedIndex + ". ");
        this.name.setText(replenishItem.getName().toString());
        this.expiryDate.setVisible(false);
        this.quantity.setVisible(false);
        this.reminder.setVisible(false);
        this.status.setVisible(false);
        this.replenishItem.getTags()
                .forEach(tag -> this.tags.getChildren().add(new Label(tag.getTagName())));
        this.box.setOnMouseClicked(e -> box.requestFocus());
    }

    //@@author xiaoyu-nus
    private String getReminderDate() {
        return DateUtil.convertDateToString(
                DateUtil.getPreviousDate(
                        this.xpireItem.getExpiryDate().getDate(),
                        this.xpireItem.getReminderThreshold().getValue())
                );
    }

    private void setColour() {
        if (xpireItem.isExpired()) {
            days.getStyleClass().add(EXPIRED);
        } else if (xpireItem.isReminding()) {
            days.getStyleClass().add(REMIND);
        } else {
            days.getStyleClass().add(HEALTHY);
        }
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
                && xpireItem.equals(card.xpireItem);
    }
}
