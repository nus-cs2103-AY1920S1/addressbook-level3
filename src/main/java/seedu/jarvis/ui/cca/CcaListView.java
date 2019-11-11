package seedu.jarvis.ui.cca;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import seedu.jarvis.logic.Logic;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.ui.MainWindow;
import seedu.jarvis.ui.template.View;

/**
 * A View representing the list of {@code Cca}.
 */
public class CcaListView extends View<AnchorPane> {
    private static final String FXML = "CcaListView.fxml";

    @FXML
    private ListView<Cca> ccaListView;

    public CcaListView(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
    }

    @Override
    public void fillPage() {
        ccaListView.setItems(model.getFilteredCcaList());
        ccaListView.setCellFactory(listView -> new CcaListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Cca} using a {@code CcaCard}.
     */
    class CcaListViewCell extends ListCell<Cca> {

        @Override
        protected void updateItem(Cca cca, boolean empty) {
            super.updateItem(cca, empty);

            if (empty || cca == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CcaCard(cca, getIndex() + 1).getRoot());
            }
        }
    }
}
