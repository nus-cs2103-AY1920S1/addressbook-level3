package seedu.address.ui;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.activity.Expense;
import seedu.address.model.person.Person;

/**
 * An UI component that displays activity-specific information of an {@code Expense}.
 */
public class ExpenseCard extends UiPart<Region> {
    private static final String FXML = "ExpenseCard.fxml";

    private static final String DELETED_LABEL_CLASS = "deleted-label";
    private static final String DELETED_TAGS_CLASS = "deleted-tags";
    private static final String SETTLEMENT_CARD_CLASS = "settlement-card";
    private static final String SETTLEMENT_RECIPIENT_CLASS = "settlement-recipient-tag";

    private final int index;
    private final Expense expense;

    @FXML
    private VBox expenseCard;
    @FXML
    private VBox detailsContainer;
    @FXML
    private Label description;
    @FXML
    private Label paidBy;
    @FXML
    private FlowPane sharedBy;
    @FXML
    private Label amount;

    public ExpenseCard(Expense expense, List<Person> activityParticipants, int displayIndex) {
        super(FXML);
        this.index = displayIndex;
        this.expense = expense;

        // If this expense was soft-deleted, apply the style classes for soft-deleted expenses
        if (expense.isDeleted()) {
            setSoftDeletedStyle();
        }

        description.setText(getFormattedDescription());
        amount.setText(String.format("$%s", expense.getAmount().toString()));

        int expenseOwnerId = expense.getPersonId();
        Optional<Person> expenseOwnerOpt = activityParticipants.stream()
                .filter((participant) -> expenseOwnerId == participant.getPrimaryKey())
                .findFirst();
        assert expenseOwnerOpt.isPresent() : "Expense is missing a participant as owner!";

        Person expenseOwner = expenseOwnerOpt.get();
        // Expense owner's label always appears first in the FlowPane and is coloured differently
        paidBy.setText(String.format("Paid by: %s", expenseOwner.getName().toString()));

        Set<Integer> involvedIds = Arrays.stream(expense.getInvolved())
                .boxed()
                .collect(Collectors.toUnmodifiableSet());

        // Retrieve the name of each participant and create a separate tag to display each
        activityParticipants.stream()
                .filter((participant) -> involvedIds.contains(participant.getPrimaryKey()))
                .map((participant) -> participant.getName().toString())
                .forEach(name -> sharedBy.getChildren().add(new Label(name)));

        if (expense.isSettlement()) {
            // Excludes the settlement owner
            assert expense.getInvolved().length == 1 : "Settlement should only involve one other participant!";
            setSettlementStyle();
        }
    }

    /**
     * Sets the component styles of this expense card to indicate a soft-deleted expense or
     * settlement.
     */
    private void setSoftDeletedStyle() {
        // Grey out amount and description labels
        amount.getStyleClass().add(DELETED_LABEL_CLASS);
        description.getStyleClass().add(DELETED_LABEL_CLASS);

        // Grey out all participant tags in the FlowPane of this expense card
        sharedBy.getStyleClass().add(DELETED_TAGS_CLASS);
    }

    /**
     * Sets the component styles of this expense card to visually distinguish a setlement from a
     * typical expense.
     */
    private void setSettlementStyle() {
        expenseCard.getStyleClass().add(SETTLEMENT_CARD_CLASS);

        // Apply settlement styling directly to the tag with recipient's name
        Node recipientTag = sharedBy.getChildren().get(1);
        recipientTag.getStyleClass().add(SETTLEMENT_RECIPIENT_CLASS);
    }

    /**
     * Formats the description of this {@code Expense} into a {@code String} for this expense card
     * to display.
     */
    private String getFormattedDescription() {
        String expenseDescription = expense.getDescription();
        boolean hasDescription = expenseDescription.length() > 0;

        if (hasDescription) {
            return String.format("#%d: %s", index, expenseDescription);
        } else {
            return String.format("#%d", index);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExpenseCard)) {
            return false;
        }

        // state check
        ExpenseCard card = (ExpenseCard) other;
        return expense.equals(card.expense);
    }
}
