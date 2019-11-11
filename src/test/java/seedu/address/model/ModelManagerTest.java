package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_FLASHCARDS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalFlashCards.DELAY;
import static seedu.address.testutil.TypicalFlashCards.PROTOCOL;
import static seedu.address.testutil.TypicalFlashCards.STORE_AND_FORWARD;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.flashcard.FlashCard;
import seedu.address.model.flashcard.QuestionContainsAnyKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.FlashCardTestListBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new KeyboardFlashCards(), new KeyboardFlashCards(modelManager.getKeyboardFlashCards()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setKeyboardFlashCardsFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setKeyboardFlashCardsFilePath(Paths.get("new/address/book/file/path"));
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
    public void setKeyboardFlashCardsFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setKeyboardFlashCardsFilePath(null));
    }

    @Test
    public void setKeyboardFlashCardsFilePath_validPath_setsKeyboardFlashCardsFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setKeyboardFlashCardsFilePath(path);
        assertEquals(path, modelManager.getKeyboardFlashCardsFilePath());
    }

    @Test
    public void hasFlashCard_nullFlashCard_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasFlashcard(null));
    }

    @Test
    public void hasFlashCard_flashCardNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasFlashcard(STORE_AND_FORWARD));
    }

    @Test
    public void hasFlashCard_flashCardInAddressBook_returnsTrue() {
        modelManager.addFlashCard(STORE_AND_FORWARD);
        assertTrue(modelManager.hasFlashcard(STORE_AND_FORWARD));
    }

    @Test
    public void getFilteredFlashCardList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredFlashCardList().remove(0));
    }

    @Test
    public void equals() {
        KeyboardFlashCards keyboardFlashCards =
                new AddressBookBuilder().withFlashCard(STORE_AND_FORWARD).withFlashCard(DELAY).build();
        KeyboardFlashCards differentKeyboardFlashCards = new KeyboardFlashCards();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(keyboardFlashCards, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(keyboardFlashCards, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different keyboardFlashCards -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentKeyboardFlashCards, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = PROTOCOL.getQuestion().fullQuestion.split("\\s+");
        modelManager.updateFilteredFlashCardList(new QuestionContainsAnyKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(keyboardFlashCards, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredFlashCardList(PREDICATE_SHOW_ALL_FLASHCARDS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setKeyboardFlashCardsFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(keyboardFlashCards, differentUserPrefs)));
    }

    //@@author keiteo
    @Test
    public void hasTestFlashCard_containsFlashCards_returnTrue() {
        List<FlashCard> testList = new FlashCardTestListBuilder().build();
        modelManager.initializeTestModel(testList);
        assertTrue(modelManager.hasTestFlashCard());
    }

    @Test
    public void hasTestFlashCard_noFlashCards_returnFalse() {
        List<FlashCard> testList = new LinkedList<>();
        modelManager.initializeTestModel(testList);
        assertFalse(modelManager.hasTestFlashCard());
    }

    @Test
    public void hasTestFlashCard_nullList_nullPointerException() {
        modelManager.initializeTestModel(null);
        assertThrows(NullPointerException.class, () -> {
            modelManager.hasTestFlashCard();
        });
    }

    @Test
    public void getTestQuestion_containsFlashCards_success() {
        List<FlashCard> testList = new FlashCardTestListBuilder().build();
        List<FlashCard> dummyList = new FlashCardTestListBuilder().build();
        modelManager.initializeTestModel(testList);
        for (FlashCard fc : dummyList) {
            String qn = fc.getQuestion().toString();
            modelManager.setTestFlashCard();
            assertEquals(qn, modelManager.getTestQuestion());
        }
    }

    @Test
    public void getTestAnswer_containsFlashCards_success() {
        List<FlashCard> testList = new FlashCardTestListBuilder().build();
        List<FlashCard> dummyList = new FlashCardTestListBuilder().build();
        modelManager.initializeTestModel(testList);
        for (FlashCard fc : dummyList) {
            String qn = fc.getAnswer().toString();
            modelManager.setTestFlashCard();
            assertEquals(qn, modelManager.getTestAnswer());
        }
    }
}
