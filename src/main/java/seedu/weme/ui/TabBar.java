package seedu.weme.ui;

import static seedu.weme.logic.parser.ParserUtil.MESSAGE_INVALID_CONTEXT;

import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.weme.commons.core.LogsCenter;
import seedu.weme.model.ModelContext;

/**
 * Bar containing all available tabs.
 */
public class TabBar extends UiPart<Region> {
    private static final String FXML = "TabBar.fxml";
    private final Logger logger = LogsCenter.getLogger(TabBar.class);

    @FXML
    private VBox memesTab;
    @FXML
    private VBox templatesTab;
    @FXML
    private VBox archiveTab;
    @FXML
    private VBox statisticsTab;
    @FXML
    private VBox storageTab;

    private ObservableValue<ModelContext> context;

    public TabBar(ObservableValue<ModelContext> context) {
        super(FXML);
        this.context = context;
        select(context.getValue()); // Select initial tab
        this.context.addListener(((observable, oldValue, newValue) -> {
            deselect(oldValue);
            select(newValue);
        }));
    }

    private void select(ModelContext context) {
        getContextTab(context).pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
    }

    private void deselect(ModelContext context) {
        getContextTab(context).pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
    }

    private VBox getContextTab(ModelContext context) {
        switch (context) {
        case CONTEXT_MEMES:
            return memesTab;
        case CONTEXT_TEMPLATES:
            return templatesTab;
        case CONTEXT_ARCHIVE:
            return archiveTab;
        case CONTEXT_STATISTICS:
            return statisticsTab;
        case CONTEXT_STORAGE:
            return storageTab;
        default:
            throw new IllegalArgumentException(MESSAGE_INVALID_CONTEXT);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TabBar)) {
            return false;
        }

        // state check
        TabBar card = (TabBar) other;
        return context.getValue().equals(card.context.getValue());
    }

}
