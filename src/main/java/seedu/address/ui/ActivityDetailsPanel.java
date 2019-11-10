package seedu.address.ui;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.util.Triplet;
import seedu.address.model.activity.Activity;
import seedu.address.model.activity.Expense;
import seedu.address.model.person.Person;
import seedu.address.ui.util.UiUtil;

/**
 * Panel displaying details of a contact.
 */
public class ActivityDetailsPanel extends UiPart<Region> {
    private static final String FXML = "ActivityDetailsPanel.fxml";

    private final Activity activity;

    @FXML
    private ScrollPane detailsPane;
    @FXML
    private Label title;
    @FXML
    private FlowPane participantTags;
    @FXML
    private Label participantCount;
    @FXML
    private Label spending;
    @FXML
    private VBox expenseHistory;
    @FXML
    private VBox transferList;

    public ActivityDetailsPanel(Activity viewedActivity, List<Person> participants) {
        super(FXML);
        this.activity = viewedActivity;

        title.setText(activity.getTitle().toString());

        participants.stream()
                .map((participant) -> participant.getName().toString())
                .forEach(name -> participantTags.getChildren().add(new Label(name)));

        int numParticipants = activity.getParticipantCount();
        participantCount.setText(UiUtil.formatParticipantCount(numParticipants));

        double totalSpending = activity.getTotalSpending();
        spending.setText(String.format("$%.2f", totalSpending));

        List<Expense> expenses = activity.getExpenses();
        IntStream.range(0, expenses.size())
                .forEach(index -> {
                    ExpenseCard newNode = new ExpenseCard(expenses.get(index), participants, index + 1);
                    expenseHistory.getChildren().add(newNode.getRoot());
                });

        Map<Integer, Person> idMapping = participants.stream()
                .collect(Collectors.toMap(p -> p.getPrimaryKey(), p -> p));

        // Retrieve required transfers to settle all debts within this activity
        List<Triplet<Integer, Integer, Double>> listTransfers = activity.getSolution();

        listTransfers.stream()
                .forEach(transfer -> {
                    Person sender = idMapping.get(transfer.getFirst());
                    Person recipient = idMapping.get(transfer.getSecond());
                    double transferAmt = transfer.getThird();

                    TransferCard newNode = new TransferCard(sender, recipient, transferAmt);
                    transferList.getChildren().add(newNode.getRoot());
                });
    }
}
