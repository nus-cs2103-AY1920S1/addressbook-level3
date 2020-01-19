package seedu.mark.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import seedu.mark.commons.core.LogsCenter;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Folder;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.folderstructure.FolderStructure;

/**
 * Panel containing the tree of folders.
 */
public class FolderStructureTreeView extends UiPart<Region> {

    private static final String FXML = "FolderStructure.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private TreeView<String> treeView;
    private HashMap<Folder, TreeItem<String>> mapOfFolderToTreeItem = new HashMap<>();
    private HashMap<TreeItem<String>, Url> mapOfTreeItemToUrl = new HashMap<>();
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
                                   ObservableList<Bookmark> bookmarks,
                                   Consumer<Url> currentUrlChangeHandler) {
        super(FXML);
        this.bookmarks = bookmarks;
        root = buildTree(folderStructure);
        populateTreeWithBookmarks();
        bookmarks.addListener((ListChangeListener<? super Bookmark>) change -> {
            while (change.next()) {
                populateTreeWithBookmarks();
            }
        });
        treeView.setRoot(root);
        treeView.setShowRoot(false);

        // Update current bookmark url when a bookmark is clicked
        treeView.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                setNewUrl(currentUrlChangeHandler);
            }
        });

        // Update current bookmark url when a bookmark is clicked
        treeView.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                setNewUrl(currentUrlChangeHandler);
            }
        });

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
                for (FolderStructure oldFolderStructure : change.getRemoved()) {
                    TreeItem<String> oldFolderTreeItem = mapOfFolderToTreeItem.get(oldFolderStructure.getFolder());
                    oldFolderTreeItem.getParent().getChildren().remove(oldFolderTreeItem);
                    mapOfFolderToTreeItem.remove(oldFolderStructure.getFolder());
                    populateTreeWithBookmarks();
                }
                for (FolderStructure newFolderStructure : change.getAddedSubList()) {
                    TreeItem<String> newBuilt = buildTree(newFolderStructure);
                    mapOfFolderToTreeItem.put(newFolderStructure.getFolder(), newBuilt);
                    built.getChildren().add(newBuilt);
                    populateTreeWithBookmarks();
                }
            }
        });
        return built;
    }

    /**
     * Populates the folder tree with bookmarks.
     */
    private void populateTreeWithBookmarks() {
        for (TreeItem<String> oldBookmarkTreeItem: bookmarkTreeItems) {
            oldBookmarkTreeItem.getParent().getChildren().remove(oldBookmarkTreeItem);
        }
        bookmarkTreeItems = new ArrayList<>();
        mapOfTreeItemToUrl = new HashMap<>();
        for (Bookmark bookmark: bookmarks) {
            // if the folder is not found, we default it to the root
            TreeItem<String> treeItem = new TreeItem<>("Bookmark: " + bookmark);
            mapOfFolderToTreeItem.getOrDefault(bookmark.getFolder(), root)
                    .getChildren().add(treeItem);
            bookmarkTreeItems.add(treeItem);
            mapOfTreeItemToUrl.put(treeItem, bookmark.getUrl());
        }
    }

    /**
     * Expands/collapses the tree by a certain amount.
     * @param levels expands by this much if positive, collapses by this much if negative.
     */
    public void expand(int levels) {
        if (levels > 0) {
            expand(root, levels);
        } else {
            collapse(root, -levels);
            root.setExpanded(true); // make sure root is not collapsed.
        }
    }

    /**
     * Expands the node by a certain amount.
     * @param node the node to expand
     * @param levels the number of levels to expand
     */
    private void expand(TreeItem<String> node, int levels) {
        if (levels <= 0) {
            return;
        }
        if (node.isExpanded()) {
            node.getChildren().forEach(child -> expand(child, levels));
        } else {
            node.expandedProperty().set(true); // we expand it
            // make sure all children are not expanded
            node.getChildren().forEach(child -> child.setExpanded(false));
            expand(node, levels - 1);
        }
    }

    /**
     * Collapses the node by a certain amount.
     * @param node the node to collapse
     * @param levels the number of levels to collapse
     * @return the number of times collapsed
     */
    private int collapse(TreeItem<String> node, int levels) {
        if (levels <= 0 || node.getChildren().isEmpty()) {
            return 0;
        }
        if (node.isExpanded() && node.getChildren().stream().noneMatch(TreeItem::isExpanded)) {
            // if node is expanded, and all its children are not expanded and it's not the root, we collapse this node.
            node.setExpanded(false);
            return 1;
        } else if (node.isExpanded()) {
            /* if node is expanded but there are unexpanded children,
             * we recursively expand each child and find out how many levels it collapsed.
             * (the optional will never be empty, because if its empty the stream is empty and the previous
             * condition will succeed).
             *
             * if the maximum that all the children collapse is still less than level, we can collapse this
             * if not, we don't collapse it.
             */
            int max = node.getChildren().stream().mapToInt(child -> collapse(child, levels)).max().getAsInt();
            if (max < levels) { // still can collapse
                node.setExpanded(false);
                return max + 1;
            }
            return max;
        } else {
            // if it's not expanded we got nothing to collapse.
            return 0;
        }
    }

    private void setNewUrl(Consumer<Url> currentUrlChangeHandler) {
        TreeItem<String> treeItem = treeView.getSelectionModel().getSelectedItem();
        // Do nothing when selection is cleared or when it's a folder item
        if (treeItem == null || !mapOfTreeItemToUrl.containsKey(treeItem)) {
            return;
        }
        logger.info("Selection in folder structure tree view changed to: " + treeItem);
        currentUrlChangeHandler.accept(mapOfTreeItemToUrl.get(treeItem));
    }
}
