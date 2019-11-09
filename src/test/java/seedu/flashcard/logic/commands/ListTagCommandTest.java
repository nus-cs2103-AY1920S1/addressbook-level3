package seedu.flashcard.logic.commands;

import static seedu.flashcard.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.flashcard.testutil.FlashcardsWithoutTag.getTaglessFlashcardList;
import static seedu.flashcard.testutil.TypicalFlashcard.getTypicalFlashcardList;

import org.junit.jupiter.api.Test;

import seedu.flashcard.logic.CommandHistory;
import seedu.flashcard.model.Model;
import seedu.flashcard.model.ModelManager;
import seedu.flashcard.model.UserPrefs;
import seedu.flashcard.model.tag.Tag;



public class ListTagCommandTest {
    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_tagsExistInSystem_showsSameList() {
        model = new ModelManager(getTypicalFlashcardList(), new UserPrefs());
        expectedModel = new ModelManager(model.getFlashcardList(), new UserPrefs());
        String tagNameList = "";
        for (Tag tag : model.getAllSystemTags()) {
            tagNameList = tagNameList + tag.tagName + "\n";
        }
        assertCommandSuccess(new ListTagCommand(), model,
                commandHistory,
                new CommandResult(ListTagCommand.MESSAGE_SUCCESS + tagNameList), expectedModel);
    }

    @Test
    public void execute_noTagsInSystem_showsEverything() {
        model = new ModelManager(getTaglessFlashcardList(), new UserPrefs());
        expectedModel = new ModelManager(model.getFlashcardList(), new UserPrefs());
        assertCommandSuccess(new ListTagCommand(), model, commandHistory,
                new CommandResult(ListTagCommand.MESSAGE_SUCCESS), expectedModel);
    }
}
