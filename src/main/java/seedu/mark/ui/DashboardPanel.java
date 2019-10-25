package seedu.mark.ui;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.mark.commons.core.LogsCenter;
import seedu.mark.logic.Logic;
import seedu.mark.model.bookmark.Url;
import javafx.scene.input.MouseEvent;
import seedu.mark.model.bookmark.Bookmark;

/**
 * The Dashboard panel of Mark.
 */
public class DashboardPanel extends UiPart<Region> {
    private static final String FXML = "DashboardPanel.fxml";
    public final FolderStructureTreeView folderStructureTreeView;
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private StackPane folderStructurePlaceholder;
    @FXML
    private StackPane reminderListPlaceholder;
    @FXML
    private FlowPane favoriteBookmarkList;
    private ObservableList<Bookmark> favoriteBookmarks;

    public DashboardPanel(Logic logic, Consumer<Url> currentUrlChangeHandler) {
        super(FXML);
        FolderStructureTreeView folderStructureTreeView = new FolderStructureTreeView(
                logic.getFolderStructure(), logic.getFilteredBookmarkList(), currentUrlChangeHandler);
        this.folderStructureTreeView = folderStructureTreeView;
        folderStructurePlaceholder.getChildren().add(folderStructureTreeView.getRoot());
        ReminderListPanel reminderListPanel = new ReminderListPanel(logic.getReminderList());
        reminderListPlaceholder.getChildren().add(reminderListPanel.getRoot());
        favoriteBookmarks = logic.getFavoriteBookmarkList();
        favoriteBookmarks.forEach(bookmark -> {
            Label bookmarkLabel = new Label(bookmark.getName().value);
            bookmarkLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent arg0) {
                    currentUrlChangeHandler.accept(bookmark.getUrl());
                }
            });
            favoriteBookmarkList.getChildren().add(bookmarkLabel);
        });
    }
}
