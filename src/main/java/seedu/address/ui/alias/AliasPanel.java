package seedu.address.ui.expense;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import seedu.address.commons.core.Alias;
import seedu.address.commons.core.AliasMappings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.expense.Expense;
import seedu.address.ui.panel.Panel;
import seedu.address.ui.panel.PanelName;

/**
 * Panel containing the list of expenses.
 */
public class AliasPanel extends Panel {
    public static final PanelName PANEL_NAME = new PanelName("Aliases");
    private static final String FXML = "ListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(AliasPanel.class);

    @FXML
    private StackPane titlePlaceHolder;
    @FXML
    private ListView<Alias> listView;

    private int maxLength;

    public AliasPanel(AliasMappings aliasMappings) {
        super(FXML);
        aliasMappings.getAliases();
        titlePlaceHolder.getChildren().add(new Label("User Defined Aliases"));
        listView.getItems().addAll(aliasMappings.getAliases());
        maxLength = aliasMappings.getAliases().stream().map(x -> {
            return x.getAliasName().length();
        }).reduce(0, Math::max);
        listView.setCellFactory(aliasListView -> new AliasListCell());
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
                int whiteSpaceCount = maxLength - alias.getAliasName().length();
                Label aliasLabel = new Label(" ".repeat(whiteSpaceCount) + alias.getAliasName());
                aliasLabel.setBackground(new Background(new BackgroundFill(Color.ORANGE, new CornerRadii(3), Insets.EMPTY)));
                aliasLabel.setTextFill(Color.WHITE);
                Label input = new Label("  " + alias.getInput());
                TextFlow graphic = new TextFlow(aliasLabel, input);
                setGraphic(graphic);
            }
        }
    }

}
