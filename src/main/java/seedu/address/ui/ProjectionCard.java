package seedu.address.ui;

import java.util.stream.IntStream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.projection.Projection;

/**
 * An UI component that displays information of a {@code Projection}.
 */
public class ProjectionCard extends UiPart<Region> {
    private static final String FXML = "ProjectionListCard.fxml";

    public final Projection projection;

    @FXML
    private HBox cardPane;
    @FXML
    private Label amount;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label category;
    @FXML
    private FlowPane budgetInfo;

    ProjectionCard(Projection projection, int displayedIndex) {
        super(FXML);
        this.projection = projection;
        id.setText(displayedIndex + ". ");
        amount.setText(projection.getProjection().toString());
        date.setText(projection.getDate().toString());
        if (projection.getCategory() != null) {
            category.setText(projection.getCategory().toString());
        } else {
            category.setVisible(false);
        }
        if (projection.getBudgets() != null) {
            IntStream.range(0, projection.getBudgets().size()).forEach(x -> {
                budgetInfo.getChildren().add(new Label(projection.getBudgets().get(x).toLabelText()));
                Label budgetForecastLabel = new Label(projection.getBudgetForecastAbbreviatedText(x));
                budgetInfo.getChildren().add(budgetForecastLabel);
                if (projection.getBudgetProjection(x).getIntegerValue() >= 0) {
                    budgetForecastLabel.setId("surplus");
                } else {
                    budgetForecastLabel.setId("deficit");
                }
            });
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProjectionCard)) {
            return false;
        }

        // state check
        ProjectionCard card = (ProjectionCard) other;
        return id.getText().equals(card.id.getText())
                && projection.equals(card.projection);
    }
}
