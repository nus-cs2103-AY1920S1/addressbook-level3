package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.activity.Activity;
import seedu.address.model.person.Person;

/**
 * Panel displaying details of a contact.
 */
public class ActivityDetailsPanel extends UiPart<Region> {
    private static final String FXML = "ActivityDetailsPanel.fxml";

    public final Activity activity;

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
        participantCount.setText(numParticipants + " " + pluralize("participant", numParticipants));

        double totalSpending = activity.getTotalSpending();
        spending.setText(String.format("$%.2f", totalSpending));

        activity.getExpenses().stream()
                .forEach(expense -> {
                    expenseHistory.getChildren().add(new ExpenseCard(expense, participants).getRoot());
                });

        List<Integer> participantIds = activity.getParticipantIds();
        Map<Integer, Person> idMapping = participants.stream()
                .collect(Collectors.toMap(p -> p.getPrimaryKey(), p -> p));

        ArrayList<ArrayList<Double>> transfersMatrix = activity.getTransferMatrix();
        for (int i = 0; i < numParticipants; i++) {
            ArrayList<Double> row = transfersMatrix.get(i);
            for (int j = i; j < numParticipants; j++) {
                // i and j do not owe each other any amount
                if (row.get(j) == 0.0) {
                    continue;
                }

                Person personI = idMapping.get(participantIds.get(i));
                Person personJ = idMapping.get(participantIds.get(j));
                if (row.get(j) < 0) {
                    // i owes j some amount (i --> j)
                    transferList.getChildren().add(new TransferCard(personI, personJ, -row.get(j)).getRoot());
                } else {
                    // j owes i some amount (j --> i)
                    transferList.getChildren().add(new TransferCard(personJ, personI, row.get(j)).getRoot());
                }
            }
        }
    }

    private String pluralize(String noun, int count) {
        assert count >= 0;
        return count != 1 ? noun : noun + "s";
    }
}
