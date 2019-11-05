package seedu.jarvis.ui.cca;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Shape;
import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.model.cca.Equipment;
import seedu.jarvis.model.cca.ccaprogress.CcaMilestone;
import seedu.jarvis.model.cca.exceptions.CcaProgressAtMaxException;
import seedu.jarvis.model.cca.exceptions.CcaProgressNotIncrementedException;
import seedu.jarvis.ui.UiPart;

/**
 * An UI component that displays information of a {@code Cca}.
 */
public class CcaCard extends UiPart<Region> {

    private static final String FXML = "CcaListCard3.fxml";
    private static final String TEXT_CCA_PROGRESS_NOT_SET_YET = "Cca progress not set yet";
    private static final String TEXT_CCA_PROGRESS_NOT_INCREMENTED_YET = "Cca progress not incremented yet";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private final Cca cca;

    @FXML
    private BorderPane borderPane;
    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label ccaType;
    @FXML
    private Label progressTitle;
    @FXML
    private Label equipmentTitle;
    @FXML
    private StackPane progressLevel;
    @FXML
    private Label progressCount;
    @FXML
    private Label progressName;
    @FXML
    private Label nextProgressName;
    @FXML
    private ListView<Equipment> equipmentListView;
    @FXML
    private ListView<CcaMilestone> progressListView;


    public CcaCard(Cca cca, int displayedIndex) {
        super(FXML);
        this.cca = cca;
        name.setText(String.valueOf(displayedIndex) + ".  " + cca.getName().fullName);
        ccaType.setText("Type: " + cca.getCcaType().toString());
        progressTitle.setText("Progress tracker");
        equipmentTitle.setText("Equipment list");
        setProgressCount();
        setProgressLevel();
        setProgressName();
        setEquipmentList();
        setMilestoneList();
    }

    private void setProgressCount() {
        if (cca.ccaMilestoneListIsEmpty()) {
            return;
        }

        progressCount.setText(String.valueOf(cca.getCcaCurrentProgressAsInt()) + "/"
                + String.valueOf(cca.getMilestoneList().size()) + " milestones completed.");
        return;
    }

    /**
     * Sets the progress level of the Cca.
     */
    private void setProgressLevel() {
        if (cca.ccaMilestoneListIsEmpty()) {
            StackPane circleContainer = createCircle(100, 8, "X");
            progressLevel.getChildren().add(circleContainer);
        } else {
            String ccaProgressPercentage = String.format("%.1f", cca.getCcaProgressPercentage() * 100.0) + "%";
            progressLevel.getChildren().add(createCircle(100, 8, ccaProgressPercentage));
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
            return;
        } else if (cca.getCcaCurrentProgressAsInt() == 0) {
            progressName.setText(TEXT_CCA_PROGRESS_NOT_INCREMENTED_YET);
            return;
        }

        try {
            progressName.setText("Current milestone: " + cca.getCurrentCcaMilestone().toString());
            nextProgressName.setText("Next milestone: " + cca.getNextCcaMilestone().toString());
        } catch (CcaProgressNotIncrementedException e) {
            progressName.setText(TEXT_CCA_PROGRESS_NOT_INCREMENTED_YET);
        } catch (CcaProgressAtMaxException e) {
            nextProgressName.setText("Congratulations! All milestones completed!");
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

    /**
     * Sets the milestone list.
     */
    private void setMilestoneList() {
        progressListView.setItems(cca.getMilestoneList());
        progressListView.setCellFactory(listView -> new MilestoneListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Equipment} using a {@code equipmentCard}.
     */
    class MilestoneListViewCell extends ListCell<CcaMilestone> {

        @Override
        protected void updateItem(CcaMilestone ccaMilestone, boolean empty) {
            super.updateItem(ccaMilestone, empty);

            if (empty || cca == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CcaMilestoneCard(ccaMilestone).getRoot());
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
