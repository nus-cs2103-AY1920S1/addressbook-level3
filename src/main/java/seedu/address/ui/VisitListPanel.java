package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.VisitReport;

/**
 * Panel containing the list of persons.
 */
public class VisitListPanel extends UiPart<Stage> {
    private static final String FXML = "VisitListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<VisitReport> visitListView;

    public VisitListPanel(Stage root) {
        super(FXML, root);
        setup(FXCollections.observableArrayList());
    }

    /**
     * Creates a new HelpWindow.
     */
    public VisitListPanel() {
        this(new Stage());
    }

    public void setup(ObservableList<VisitReport> visitList) {
        visitListView.setItems(visitList);
        visitListView.setCellFactory(listView -> new VisitListViewCell());
    }


    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class VisitListViewCell extends ListCell<VisitReport> {
        @Override
        protected void updateItem(VisitReport report, boolean empty) {
            super.updateItem(report, empty);

            if (empty || report == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new VisitCard(report, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing report form.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

}
