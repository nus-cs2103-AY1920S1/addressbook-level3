package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.module.Module;
import seedu.address.model.module.UniqueModuleList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.testutil.TagBuilder;
import seedu.address.ui.ResultViewType;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");
        CommandResult<Tag> commandResultWithView = new CommandResult<Tag>("feedback",
                ResultViewType.TAG, new UniqueTagList().asUnmodifiableObservableList());
        CommandResult<Module> commandResultWithDifferentView = new CommandResult<Module>("feedback",
                ResultViewType.MODULE, new UniqueModuleList().asUnmodifiableObservableList());

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));
        assertTrue(commandResultWithView.equals(commandResultWithView));

        // null -> returns false
        assertFalse(commandResult.equals(null));
        assertFalse(commandResultWithView.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));
        assertFalse(commandResultWithView.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));
        assertFalse(commandResultWithView.equals(new CommandResult<Tag>("different", ResultViewType.TAG,
                new UniqueTagList().asUnmodifiableObservableList())));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true)));

        // one with result view type and one without -> returns false
        assertFalse(commandResult.equals(commandResultWithView));

        // different result view type -> returns false
        assertFalse(commandResultWithView.equals(commandResultWithDifferentView));

        // different result content
        UniqueTagList listWithDifferentContent = new UniqueTagList();
        listWithDifferentContent.addTag(new TagBuilder().buildTestUserTag());
        assertFalse(commandResultWithView.equals(new CommandResult<Tag>("feedback", ResultViewType.TAG,
                listWithDifferentContent.asUnmodifiableObservableList())));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");
        CommandResult<Tag> commandResultWithView = new CommandResult<Tag>("feedback",
                ResultViewType.TAG, new UniqueTagList().asUnmodifiableObservableList());
        CommandResult<Module> commandResultWithDifferentView = new CommandResult<Module>("feedback",
                ResultViewType.MODULE, new UniqueModuleList().asUnmodifiableObservableList());

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());
        assertEquals(commandResultWithView.hashCode(), new CommandResult<Tag>("feedback",
                ResultViewType.TAG, new UniqueTagList().asUnmodifiableObservableList()).hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());
        assertNotEquals(commandResultWithView.hashCode(), new CommandResult<Tag>("different",
                ResultViewType.TAG, new UniqueTagList().asUnmodifiableObservableList()));

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true).hashCode());

        // one with result view type and one without -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), commandResultWithView.hashCode());

        // different result view type -> returns false
        assertNotEquals(commandResultWithView.hashCode(), commandResultWithDifferentView.hashCode());

        // different result content
        UniqueTagList listWithDifferentContent = new UniqueTagList();
        listWithDifferentContent.addTag(new TagBuilder().buildTestUserTag());
        assertNotEquals(commandResultWithView.hashCode(), (new CommandResult<Tag>("feedback",
                ResultViewType.TAG, listWithDifferentContent.asUnmodifiableObservableList())).hashCode());

    }
}
