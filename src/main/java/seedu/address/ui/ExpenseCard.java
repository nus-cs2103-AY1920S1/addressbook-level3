package seedu.address.ui;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
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

    public final Expense expense;

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

    public ExpenseCard(Expense expense, List<Person> activityParticipants) {
        super(FXML);
        this.expense = expense;

        String expenseDescription = expense.getDescription();
        boolean hasDescription = expenseDescription.length() > 0;

        if (hasDescription) {
            description.setText(expense.getDescription());
        } else {
            // Remove the description label if this expense has no description
            detailsContainer.getChildren().remove(description);
        }

        amount.setText(String.format("$%s", expense.getAmount().toString()));

        Optional<Person> expenseOwnerOpt = activityParticipants.stream()
                .filter((participant) -> participant.getPrimaryKey() == expense.getPersonId())
                .findFirst();
        assert expenseOwnerOpt.isPresent();

        Person expenseOwner = expenseOwnerOpt.get();
        // Expense owner's label always appears first in the FlowPane and is coloured differently
        paidBy.setText(String.format("Paid by: %s", expenseOwner.getName().toString()));

        Set<Integer> involvedIds = Arrays.stream(expense.getInvolved())
                .boxed()
                .collect(Collectors.toUnmodifiableSet());

        activityParticipants.stream()
                .filter((participant) -> involvedIds.contains(participant.getPrimaryKey()))
                .map((participant) -> participant.getName().toString())
                .forEach(name -> sharedBy.getChildren().add(new Label(name)));

        // If this expense was soft-deleted, apply the style classes for soft-deleted expenses
        if (expense.isDeleted()) {
            setSoftDeletedStyle(hasDescription);
        }
    }

    /**
     * Sets the component styles of this expense card to indicate a soft-deleted expense or settlement.
     * @param hasDescription A {@code boolean} indicating if this expense card contains a description.
     */
    private void setSoftDeletedStyle(boolean hasDescription) {
        // Grey out amount and description labels
        amount.getStyleClass().add(DELETED_LABEL_CLASS);
        if (hasDescription) {
            description.getStyleClass().add(DELETED_LABEL_CLASS);
        }

        // Grey out all participant tags in the FlowPane of this expense card
        sharedBy.getStyleClass().add(DELETED_TAGS_CLASS);
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
