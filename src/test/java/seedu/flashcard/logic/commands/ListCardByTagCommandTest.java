package seedu.flashcard.logic.commands;

import static seedu.flashcard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.flashcard.logic.commands.CommandTestUtil.showFlashcardAtIndex;
import static seedu.flashcard.testutil.TypicalFlashcard.getTypicalFlashcardList;
import static seedu.flashcard.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.flashcard.logic.CommandHistory;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.ModelManager;
import seedu.flashcard.model.UserPrefs;
import seedu.flashcard.model.tag.Tag;



public class ListCardByTagCommandTest {

    private Model model;
    private Model expectedModel;
    private Set<Tag> testTags;
    private CommandHistory commandHistory = new CommandHistory();

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalFlashcardList(), new UserPrefs());
        expectedModel = new ModelManager(model.getFlashcardList(), new UserPrefs());
        testTags = new HashSet<>();
        Tag testTag1 = new Tag("Geography");
        Tag testTag2 = new Tag("Civil Engineering");
        testTags.add(testTag1);
        testTags.add(testTag2);

    }

    @Test
    public void execute_listIsNotFilteredandPresentTags_showsSameList() {
        expectedModel.updateFilteredFlashcardList(model.getHasTagPredicate(testTags));
        assertCommandSuccess(new ListCardByTagCommand(testTags), model,
                commandHistory, new CommandResult(ListCardByTagCommand.MESSAGE_SUCCESS), expectedModel);
    }

    @Test
    public void execute_listIsFilteredandPresentTags_showsEverything() {
        showFlashcardAtIndex(model, INDEX_FIRST_FLASHCARD);
        expectedModel.updateFilteredFlashcardList(model.getHasTagPredicate(testTags));
        assertCommandSuccess(new ListCardByTagCommand(testTags), model,
                commandHistory, new CommandResult(ListCardByTagCommand.MESSAGE_SUCCESS), expectedModel);
    }

    @Test
    public void execute_listIsNotFilteredwithNonExistantTags_showsSameList() {
        Set<Tag> wrongTags = new HashSet<>();
        Tag wrongTag = new Tag("NonExistant");
        wrongTags.add(wrongTag);
        expectedModel.updateFilteredFlashcardList(model.getHasTagPredicate(wrongTags));
        assertCommandSuccess(new ListCardByTagCommand(wrongTags), model,
                commandHistory, new CommandResult(ListCardByTagCommand.MESSAGE_SUCCESS), expectedModel);
    }
}
