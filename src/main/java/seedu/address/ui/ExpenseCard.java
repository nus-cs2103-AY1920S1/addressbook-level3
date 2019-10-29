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
        if (expenseDescription.length() == 0) {
            // Remove the description label if this expense has no description
            detailsContainer.getChildren().remove(0);
        } else {
            description.setText(expense.getDescription());
        }

        amount.setText(String.format("$%s", expense.getAmount().toString()));
        // If the expense was soft-deleted, strike out the amount
        if (expense.isDeleted()) {
            amount.setStyle("-fx-strikethrough: true");
        }

        Optional<Person> expenseOwnerOpt = activityParticipants.stream()
                .filter((participant) -> participant.getPrimaryKey() == expense.getPersonId())
                .findFirst();
        assert expenseOwnerOpt.isPresent();

        Person expenseOwner = expenseOwnerOpt.get();
        // Expense owner's label always appears first in the FlowPane and is coloured differently
        paidBy.setText(String.format("Paid: %s", expenseOwner.getName().toString()));

        Set<Integer> involvedIds = Arrays.stream(expense.getInvolved())
                .boxed()
                .collect(Collectors.toUnmodifiableSet());

        activityParticipants.stream()
                .filter((participant) -> involvedIds.contains(participant.getPrimaryKey()))
                .map((participant) -> participant.getName().toString())
                .forEach(name -> sharedBy.getChildren().add(new Label(name)));
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
