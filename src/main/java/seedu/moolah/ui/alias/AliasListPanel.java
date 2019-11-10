package seedu.moolah.ui.alias;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import seedu.moolah.commons.core.LogsCenter;
import seedu.moolah.model.alias.Alias;
import seedu.moolah.model.alias.AliasMappings;
import seedu.moolah.ui.panel.Panel;
import seedu.moolah.ui.panel.PanelName;

/**
 * Panel containing the list of aliases.
 */
public class AliasListPanel extends Panel {
    public static final PanelName PANEL_NAME = new PanelName("Aliases");
    private static final String FXML = "AliasListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AliasListPanel.class);

    @FXML
    private StackPane titlePlaceHolder;
    @FXML
    private ListView<Alias> aliasListView;

    private int maxLength;

    public AliasListPanel(AliasMappings aliasMappings) {
        super(FXML);
        aliasMappings.getAliases();
        titlePlaceHolder.getChildren().add(new Label("User Defined Aliases"));
        aliasListView.getItems().addAll(aliasMappings.getAliases());
        maxLength = aliasMappings.getAliases().stream().map(x -> x.getAliasName().length()).reduce(0, Math::max);
        aliasListView.setCellFactory(aliasListView -> new AliasListCell());
    }

    @Override
    public void view() {
        getRoot().setVisible(true);
        getRoot().setDisable(false);
    }

    @Override
    public void hide() {
        getRoot().setVisible(false);
        getRoot().setDisable(true);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Alias} using a {@code TextFlow}.
     */
    class AliasListCell extends ListCell<Alias> {
        @Override
        protected void updateItem(Alias alias, boolean empty) {
            super.updateItem(alias, empty);

            if (empty || alias == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new AliasCard(alias, maxLength).getRoot());
            }
        }
    }

}
