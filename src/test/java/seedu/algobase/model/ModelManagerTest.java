package seedu.algobase.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.algobase.testutil.Assert.assertThrows;
import static seedu.algobase.testutil.TypicalProblems.ALICE;
import static seedu.algobase.testutil.TypicalProblems.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.algobase.commons.core.GuiSettings;
import seedu.algobase.model.Problem.NameContainsKeywordsPredicate;
import seedu.algobase.testutil.AlgoBaseBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new AlgoBase(), new AlgoBase(modelManager.getAlgoBase()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAlgoBaseFilePath(Paths.get("description/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setAlgoBaseFilePath(Paths.get("new/description/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setAlgoBaseFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAlgoBaseFilePath(null));
    }

    @Test
    public void setAlgoBaseFilePath_validPath_setsAlgoBaseFilePath() {
        Path path = Paths.get("description/book/file/path");
        modelManager.setAlgoBaseFilePath(path);
        assertEquals(path, modelManager.getAlgoBaseFilePath());
    }

    @Test
    public void hasProblem_nullProblem_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasProblem(null));
    }

    @Test
    public void hasProblem_problemNotInAlgoBase_returnsFalse() {
        assertFalse(modelManager.hasProblem(ALICE));
    }

    @Test
    public void hasProblem_problemInAlgoBase_returnsTrue() {
        modelManager.addProblem(ALICE);
        assertTrue(modelManager.hasProblem(ALICE));
    }

    @Test
    public void getFilteredProblemList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredProblemList().remove(0));
    }

    @Test
    public void equals() {
        AlgoBase algoBase = new AlgoBaseBuilder().withProblem(ALICE).withProblem(BENSON).build();
        AlgoBase differentAlgoBase = new AlgoBase();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(algoBase, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(algoBase, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different algoBase -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAlgoBase, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredProblemList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(algoBase, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredProblemList(PREDICATE_SHOW_ALL_PERSONS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAlgoBaseFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(algoBase, differentUserPrefs)));
    }
}
