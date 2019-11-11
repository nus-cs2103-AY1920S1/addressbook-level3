package com.typee.model;

import static com.typee.testutil.TypicalReports.TYPICAL_REPORT;
import static com.typee.testutil.TypicalReports.TYPICAL_REPORT_DIFF;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.itextpdf.text.DocumentException;
import com.typee.commons.core.GuiSettings;
import com.typee.logic.commands.exceptions.DeleteDocumentException;
import com.typee.logic.commands.exceptions.GenerateExistingReportException;
import com.typee.testutil.TypicalEngagements;

public class ModelManagerTest {

    @TempDir
    public File tempDir;

    private ModelManager modelManager = new ModelManager();

    @BeforeEach
    public void setUp() throws DocumentException, GenerateExistingReportException, IOException {
        modelManager.saveReport(tempDir.toPath(), TYPICAL_REPORT);
    }

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new EngagementList(), new EngagementList(modelManager.getEngagementList()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void saveReport_repeating() {
        assertThrows(GenerateExistingReportException.class, () -> modelManager.saveReport(tempDir.toPath(),
                TYPICAL_REPORT));
    }

    @Test
    public void deleteReport() throws DeleteDocumentException {
        assertEquals(true, modelManager.deleteReport(tempDir.toPath(), TYPICAL_REPORT));
    }

    @Test
    public void deleteReport_invalid() {
        assertThrows(DeleteDocumentException.class, () -> modelManager.deleteReport(tempDir.toPath(),
                TYPICAL_REPORT_DIFF));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAddressBookFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }
    @Test
    public void equals() {
        EngagementList engagementList = TypicalEngagements.getTypicalEngagementList();
        EngagementList differentAddressBook = new EngagementList();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(engagementList, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(engagementList, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different engagementList -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(engagementList, differentUserPrefs)));
    }

    /*

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAddressBookFilePath(null));
    }

    @Test
    public void setAddressBookFilePath_validPath_setsAddressBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setAddressBookFilePath(path);
        assertEquals(path, modelManager.getAddressBookFilePath());
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasEngagement(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasEngagement(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addEngagement(ALICE);
        assertTrue(modelManager.hasEngagement(ALICE));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredEngagementList().remove(0));
    }
     */
}
