package seedu.jarvis.ui.cca;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.model.cca.Equipment;
import seedu.jarvis.ui.UiPart;

/**
 * An UI component that displays information of a {@code Cca}.
 */
public class CcaCard extends UiPart<Region> {

    private static final String FXML = "CcaListCard.fxml";
    private static final String TEXT_CCA_PROGRESS_NOT_SET_YET = "Not set";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private final Cca cca;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label ccaType;
    @FXML
    private StackPane progressLevel;
    @FXML
    private Label progressName;
    @FXML
    private ListView<Equipment> equipmentListView;


    public CcaCard(Cca cca, int displayedIndex) {
        super(FXML);
        this.cca = cca;
        id.setText(displayedIndex + ". ");
        name.setText(cca.getName().fullName);
        ccaType.setText("Type: " + cca.getCcaType().toString());
        setProgressLevel();
        setProgressName();
        setEquipmentList();
    }

    /**
     * Sets the progress level of the Cca.
     */
    private void setProgressLevel() {
        if (cca.ccaMilestoneListIsEmpty()) {
            progressLevel.getChildren().add(createCircle(50, 2, "X"));
        } else {
            double ccaProgressPercentage = cca.getCcaProgressPercentage() * 100;
            String ccaProgressPercentageString = Double.toString(ccaProgressPercentage) + "%";
            progressLevel.getChildren().add(createCircle(50, 2, ccaProgressPercentageString));
        }
    }

    /**
     * Returns a circle that is used to represent the CcaProgress.
     */
    private StackPane createCircle(double size, double borderSize, String circleContent) {
        StackPane circleContainer = new StackPane();
        Ellipse outer = new Ellipse(size / 2, size / 2);
        Ellipse inner = new Ellipse(size / 2 - borderSize, size / 2 - borderSize);
        final Shape circle = Shape.subtract(outer, inner);
        circle.setStyle("-fx-fill: white");
        Label circleLabel = new Label(circleContent);
        circleContainer.getChildren().addAll(circle, circleLabel);
        return circleContainer;
    }

    /**
     * Sets the progress name.
     */
    private void setProgressName() {
        if (cca.ccaMilestoneListIsEmpty()) {
            progressName.setText(TEXT_CCA_PROGRESS_NOT_SET_YET);
        } else {
            progressName.setText("Current milestone: " + cca.getCurrentCcaMilestone().toString());
        }
    }

    /**
     * Sets the equipment list.
     */
    private void setEquipmentList() {
        equipmentListView.setItems(cca.getFilteredEquipmentList());
        equipmentListView.setCellFactory(listView -> new EquipmentListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Equipment} using a {@code equipmentCard}.
     */
    class EquipmentListViewCell extends ListCell<Equipment> {

        @Override
        protected void updateItem(Equipment equipment, boolean empty) {
            super.updateItem(equipment, empty);

            if (empty || cca == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EquipmentCard(equipment).getRoot());
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CcaCard)) {
            return false;
        }

        // state check
        CcaCard card = (CcaCard) other;
        return id.getText().equals(card.id.getText())
                && cca.equals(card.cca);
    }
}
