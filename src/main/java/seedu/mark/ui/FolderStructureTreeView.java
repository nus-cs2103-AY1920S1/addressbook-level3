package seedu.mark.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Region;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.model.folderstructure.FolderStructure;

/**
 * Panel containing the tree of folders.
 */
public class FolderStructureTreeView extends UiPart<Region> {

    private static final String FXML = "FolderStructure.fxml";

    @FXML
    private TreeView<String> treeView;
    private HashMap<Folder, TreeItem<String>> mapOfFolderToTreeItem = new HashMap<>();
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
        TreeItem<String> built = new TreeItem<>(toBuild.getFolder().folderName);
        ObservableList<FolderStructure> subfolders = toBuild.getSubfolders();
        for (FolderStructure subfolder: subfolders) {
            TreeItem<String> builtChild = buildTree(subfolder);
            mapOfFolderToTreeItem.put(subfolder.getFolder(), builtChild);
            built.getChildren().add(builtChild);
        }
        subfolders.addListener((ListChangeListener<? super FolderStructure>) change -> {
            while (change.next()) {
                for (FolderStructure newFolderStructure : change.getAddedSubList()) {
                    TreeItem<String> newBuilt = buildTree(newFolderStructure);
                    mapOfFolderToTreeItem.put(newFolderStructure.getFolder(), newBuilt);
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
            mapOfFolderToTreeItem.getOrDefault(bookmark.getFolder(), root)
                    .getChildren().add(treeItem);
            bookmarkTreeItems.add(treeItem);
        }
    }

}
