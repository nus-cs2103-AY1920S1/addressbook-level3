package com.dukeacademy.storage;

import static com.dukeacademy.testutil.TypicalQuestions.getTypicalQuestionBank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.dukeacademy.commons.core.GuiSettings;
import com.dukeacademy.model.prefs.UserPrefs;
import com.dukeacademy.model.question.Question;
import com.dukeacademy.model.question.QuestionBank;
import com.dukeacademy.model.question.StandardQuestionBank;
import com.dukeacademy.storage.prefs.JsonUserPrefsStorage;
import com.dukeacademy.storage.question.JsonQuestionBankStorage;

import javafx.collections.transformation.SortedList;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonQuestionBankStorage
            addressBookStorage = new JsonQuestionBankStorage(getTempFilePath("qb"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(addressBookStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void addressBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonQuestionBankStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonQuestionBankStorageTest} class.
         */
        StandardQuestionBank original = getTypicalQuestionBank();
        storageManager.saveQuestionBank(original);
        QuestionBank retrieved = storageManager.readQuestionBank().get();
        assertTrue(this.checkQuestionBanksEqual(original, retrieved));
    }

    @Test
    public void getAddressBookFilePath() {
        assertNotNull(storageManager.getQuestionBankFilePath());
    }

    /**
     * Helper method to check if the questions in two question  banks are equal.
     * @param bank1 the first question bank to be checked.
     * @param bank2 the second question bank to be checked.
     * @return true if the questions in both banks are equal.
     */
    private boolean checkQuestionBanksEqual(QuestionBank bank1, QuestionBank bank2) {
        SortedList<Question> list1 = bank1.getReadOnlyQuestionListObservable()
                .sorted((q1, q2) -> q1.getTitle().compareTo(q2.getTitle()));
        SortedList<Question> list2 = bank2.getReadOnlyQuestionListObservable()
                .sorted((q1, q2) -> q1.getTitle().compareTo(q2.getTitle()));

        if (list1.size() != list2.size()) {
            return false;
        }

        if (list1.size() == 0) {
            return true;
        }

        return IntStream.range(0, list1.size())
                .mapToObj(i -> list1.get(i).getTitle().equals(list2.get(i).getTitle()))
                .reduce((x, y) -> x && y).get();
    }
}
