package seedu.mark.model.folderstructure;

import static java.util.Objects.requireNonNull;
import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.mark.model.bookmark.Folder;

/**
 * Represents a folder structure in Mark.
 * Guarantees: details are present and not null, field values
 * are validated, immutable.
 */
public class FolderStructure {

    private final Folder folder;
    private final ObservableList<FolderStructure> subfolders = FXCollections.observableArrayList();

    /**
     * Instantiates a new Folder structure.
     * @param folder     the folder
     * @param subfolders the subfolders
     */
    public FolderStructure(Folder folder, List<FolderStructure> subfolders) {
        requireAllNonNull(folder, subfolders);
        this.folder = folder;
        this.subfolders.addAll(subfolders);
    }

    /**
     * Gets the folder.
     *
     * @return the folder
     */
    public Folder getFolder() {
        return folder;
    }

    /**
     * Gets subfolders.
     *
     * @return the subfolders
     */
    public ObservableList<FolderStructure> getSubfolders() {
        return subfolders;
    }

    /**
     * Finds the FolderStructure that the specified folder belongs to, return null if not found.
     *
     * @param folder the folder
     * @return the folder structure that the folder belongs to, null if no folder structure contains the folder
     */
    public FolderStructure find(Folder folder) {
        if (this.folder.equals(folder)) {
            return this;
        }
        for (FolderStructure subfolder: subfolders) {
            FolderStructure found = subfolder.find(folder);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    /**
     * Finds the FolderStructure that the specified folder's parent belongs to, return null if not found.
     *
     * @param folder the folder
     * @return the folder structure that the folder's parent belongs to,
     * null if no folder structure has the folder as a child
     */
    private FolderStructure findParent(Folder folder) {
        for (FolderStructure subfolder: subfolders) {
            if (subfolder.folder.equals(folder)) {
                return this;
            }
            FolderStructure found = subfolder.findParent(folder);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    /**
     * Checks if this folder structure contains the specified folder.
     *
     * @param folder the folder
     * @return true if this folder structure contains the specified folder, otherwise false
     */
    public boolean hasFolder(Folder folder) {
        return find(folder) != null;
    }

    /**
     * Adds the specified folder under the parent folder.
     *
     * @param folder the folder to be added
     * @param parentFolder the parent folder
     */
    public void addFolder(Folder folder, Folder parentFolder) {
        find(parentFolder).getSubfolders().add(new FolderStructure(folder, new ArrayList<>()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FolderStructure)) {
            return false;
        }

        FolderStructure otherFolderStructure = (FolderStructure) other;

        return otherFolderStructure.getFolder().equals(getFolder())
                && new HashSet<>(otherFolderStructure.getSubfolders()).equals(
                        new HashSet<>(getSubfolders()));
    }

    /**
     * Helper function for isValidFolderStructure
     */
    private static boolean isValidFolderStructure(FolderStructure test, Set<Folder> seenSoFar) {
        if (seenSoFar.contains(test.getFolder())) {
            return false;
        }
        seenSoFar.add(test.getFolder());
        for (FolderStructure folderStructure: test.getSubfolders()) {
            if (!FolderStructure.isValidFolderStructure(folderStructure, seenSoFar)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if folder structure has no duplicates.
     *
     * @param test the structure to test
     * @return true if the folder structure has no duplicates, otherwise false
     */
    public static boolean isValidFolderStructure(FolderStructure test) {
        return FolderStructure.isValidFolderStructure(test, new HashSet<>());
    }

    /**
     * Clones a folder structure.
     */
    public FolderStructure clone() {
        return new FolderStructure(
                folder, subfolders.stream().map(FolderStructure::clone).collect(Collectors.toList()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(folder, subfolders);
    }

    /**
     * Renames a folder from {@code from} to {@code to}.
     * @param from must exist
     * @param to must not exist
     */
    public void renameFolder(Folder from, Folder to) {
        FolderStructure parent = findParent(from);
        FolderStructure previous = find(from);
        requireNonNull(parent);
        parent.subfolders.set(parent.subfolders.indexOf(
                find(from)), new FolderStructure(to, previous.subfolders));
    }

    /**
     * Deletes {@code folder}.
     * @param folder
     */
    public void deleteFolder(Folder folder) {
        FolderStructure parent = findParent(folder);
        requireNonNull(parent);
        FolderStructure folderStructure =
                parent.getSubfolders().stream().filter(fs -> fs.folder.equals(folder)).findFirst().get();
        parent.getSubfolders().remove(folderStructure);
    }
}
