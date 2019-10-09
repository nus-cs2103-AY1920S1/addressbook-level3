package seedu.mark.model.folderstructure;

import static seedu.mark.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a folder structure in Mark. Guarantees: details are present and not null, field values
 * are validated, immutable.
 */
public class FolderStructure {

    private final String name;
    private final ObservableList<FolderStructure> subfolders = FXCollections.observableArrayList();

    /**
     * Instantiates a new Folder structure.
     *
     * @param name       the name
     * @param subfolders the subfolders
     */
    public FolderStructure(String name, List<FolderStructure> subfolders) {
        requireAllNonNull(name, subfolders);
        this.name = name;
        this.subfolders.addAll(subfolders);
    }

    /**
     * Gets name of folder
     *
     * @return the name
     */
    public String getName() {
        return name;
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
     * Find folder.
     *
     * @param folderName the folder name
     * @return the folder structure
     */
    public FolderStructure find(String folderName) {
        if (name.equals(folderName)) {
            return this;
        }
        for (FolderStructure subfolder: subfolders) {
            FolderStructure found = subfolder.find(folderName);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    /**
     * Contains folder boolean.
     *
     * @param folderName the folder name
     * @return the boolean
     */
    public boolean hasFolder(String folderName) {
        return find(folderName) != null;
    }

    /**
     * Create folder.
     *
     * @param folderName the folder name
     * @param parentName the parent name
     */
    public void createFolder(String folderName, String parentName) {
        find(parentName).getSubfolders().add(new FolderStructure(folderName, new ArrayList<>()));
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

        return otherFolderStructure.getName().equals(getName())
                && otherFolderStructure.getSubfolders().equals(getSubfolders());
    }

    /**
     * Helper function for isValidFolderStructure
     */
    private static boolean isValidFolderStructure(FolderStructure test, Set<String> seenSoFar) {
        if (seenSoFar.contains(test.getName())) {
            return false;
        }
        seenSoFar.add(test.getName());
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
     * @return valid?
     */
    public static boolean isValidFolderStructure(FolderStructure test) {
        return FolderStructure.isValidFolderStructure(test, new HashSet<>());
    }

    /**
     * Clones a folder structure.
     */
    public FolderStructure clone() {
        return new FolderStructure(
                name, subfolders.stream().map(FolderStructure::clone).collect(Collectors.toList()));
    }
}
