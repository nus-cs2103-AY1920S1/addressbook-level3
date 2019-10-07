package seedu.mark.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Region;
import seedu.mark.commons.core.LogsCenter;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.folderstructure.FolderStructure;

/**
 * Panel containing the list of persons.
 */
public class FolderStructureTreeView extends UiPart<Region> {

    private static final String FXML = "FolderStructure.fxml";
    private final Logger logger = LogsCenter.getLogger(FolderStructureTreeView.class);

    @FXML
    private TreeView<String> treeView;
    private HashMap<String, TreeItem<String>> mapOfFolderToTreeItem = new HashMap<>();
    private TreeItem<String> root;
    private ObservableList<Bookmark> bookmarks;
    private List<TreeItem<String>> bookmarkTreeItems = new ArrayList<>();

    /**
     * Instantiates a new Folder structure tree view.
     *
     * @param folderStructure the folder structure
     * @param bookmarks       the bookmarks
     */
    public FolderStructureTreeView(FolderStructure folderStructure,
            ObservableList<Bookmark> bookmarks) {
        super(FXML);
        this.bookmarks = bookmarks;
        root = buildTree(folderStructure);
        populateTreeWithBookmarks();
        bookmarks.addListener((ListChangeListener<? super Bookmark>) change -> {
            while (change.next()) {
                for (TreeItem<String> oldBookmarkTreeItem: bookmarkTreeItems) {
                    oldBookmarkTreeItem.getParent().getChildren().remove(oldBookmarkTreeItem);
                }
                populateTreeWithBookmarks();
            }
        });
        treeView.setRoot(root);
        treeView.setShowRoot(false);
    }


    /**
     * Builds the tree.
     * @param toBuild
     * @return
     */
    private TreeItem<String> buildTree(FolderStructure toBuild) {
        TreeItem<String> built = new TreeItem<>(toBuild.getName());
        ObservableList<FolderStructure> subfolders = toBuild.getSubfolders();
        for (FolderStructure subfolder: subfolders) {
            TreeItem<String> builtChild = buildTree(subfolder);
            mapOfFolderToTreeItem.put(subfolder.getName(), builtChild);
            built.getChildren().add(builtChild);
        }
        subfolders.addListener((ListChangeListener<? super FolderStructure>) change -> {
            while (change.next()) {
                for (FolderStructure newFolderStructure : change.getAddedSubList()) {
                    TreeItem<String> newBuilt = buildTree(newFolderStructure);
                    mapOfFolderToTreeItem.put(newFolderStructure.getName(), newBuilt);
                    built.getChildren().add(newBuilt);
                }
            }
        });
        return built;
    }

    /**
     * Populates the folder tree with bookmarks.
     */
    private void populateTreeWithBookmarks() {
        bookmarkTreeItems = new ArrayList<>();
        for (Bookmark bookmark: bookmarks) {
            // if the folder is not found, we default it to the root
            TreeItem<String> treeItem = new TreeItem<>("Bookmark: " + bookmark);
            mapOfFolderToTreeItem.getOrDefault(bookmark.getFolder().folderName, root)
                    .getChildren().add(treeItem);
            bookmarkTreeItems.add(treeItem);
        }
    }

}
