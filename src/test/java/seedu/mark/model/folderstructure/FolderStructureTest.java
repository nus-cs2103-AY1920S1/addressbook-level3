package seedu.mark.model.folderstructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.mark.logic.commands.CommandTestUtil.VALID_FOLDER_CS2101;
import static seedu.mark.logic.commands.CommandTestUtil.VALID_FOLDER_CS2103T;
import static seedu.mark.model.bookmark.Folder.ROOT_FOLDER;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.mark.model.bookmark.Folder;

public class FolderStructureTest {
    private static final Folder FOLDER_CS2103T = new Folder(VALID_FOLDER_CS2103T);
    private static final Folder FOLDER_CS2101 = new Folder(VALID_FOLDER_CS2101);
    private static final Folder FOLDER_READINGS = new Folder("Important Readings");
    private static final Folder FOLDER_PPP = new Folder("PPP");

    private static final Folder FOLDER_NTU = new Folder("NTU");

    private FolderStructure asLeaf(Folder folder) {
        return new FolderStructure(folder, new ArrayList<>());
    }

    private FolderStructure getTestFolderStructure() {
        return new FolderStructure(ROOT_FOLDER,
                List.of(asLeaf(FOLDER_CS2101),
                        new FolderStructure(FOLDER_CS2103T, List.of(asLeaf(FOLDER_PPP), asLeaf(FOLDER_READINGS)))));
    }

    @Test
    public void findAndHasFolder() {
        // hasFolder is implemented in terms of find, so we test both together.
        FolderStructure root = getTestFolderStructure();

        assertEquals(root.find(ROOT_FOLDER).getFolder(), ROOT_FOLDER);
        assertTrue(root.hasFolder(ROOT_FOLDER));

        assertEquals(root.find(FOLDER_CS2103T).getFolder(), FOLDER_CS2103T);
        assertTrue(root.hasFolder(FOLDER_CS2103T));

        assertEquals(root.find(FOLDER_CS2101).getFolder(), FOLDER_CS2101);
        assertTrue(root.hasFolder(FOLDER_CS2101));

        assertEquals(root.find(FOLDER_READINGS).getFolder(), FOLDER_READINGS);
        assertTrue(root.hasFolder(FOLDER_READINGS));

        assertEquals(root.find(FOLDER_PPP).getFolder(), FOLDER_PPP);
        assertTrue(root.hasFolder(FOLDER_PPP));

        // folders not inside
        assertNull(root.find(FOLDER_NTU));
        assertFalse(root.hasFolder(FOLDER_NTU));
    }

    @Test
    public void addFolder() {
        FolderStructure root = getTestFolderStructure();

        assertFalse(root.hasFolder(FOLDER_NTU));

        root.addFolder(FOLDER_NTU, ROOT_FOLDER);

        assertTrue(root.hasFolder(FOLDER_NTU));
    }

    @Test
    public void testEquals() {
        // same folder structure
        assertEquals(getTestFolderStructure(), getTestFolderStructure());

        FolderStructure root = getTestFolderStructure();
        root.deleteFolder(FOLDER_PPP);
        assertNotEquals(getTestFolderStructure(), root);
    }

    @Test
    public void isValidFolderStructure() {
        assertTrue(FolderStructure.isValidFolderStructure(getTestFolderStructure()));

        FolderStructure root = getTestFolderStructure();

        //add duplicate folder
        root.addFolder(FOLDER_PPP, FOLDER_CS2101);

        assertFalse(FolderStructure.isValidFolderStructure(root));
    }

    @Test
    public void testClone() {
        FolderStructure root = getTestFolderStructure();
        FolderStructure clone = root.clone();

        // must be equal
        assertEquals(root, clone);

        // must not ==
        assertNotSame(root, clone);
    }

    @Test
    public void renameFolder() {
        FolderStructure root = getTestFolderStructure();
        Folder from = FOLDER_READINGS;
        Folder to = new Folder("Too many readings");

        assertTrue(root.hasFolder(from));
        assertFalse(root.hasFolder(to));

        root.renameFolder(from, to);

        assertFalse(root.hasFolder(from));
        assertTrue(root.hasFolder(to));
    }

    @Test
    public void deleteFolder() {
        FolderStructure root = getTestFolderStructure();

        assertTrue(root.hasFolder(FOLDER_READINGS));
        root.deleteFolder(FOLDER_READINGS);
        assertFalse(root.hasFolder(FOLDER_READINGS));
    }
}
