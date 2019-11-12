package seedu.address.logic.commands.cheatsheet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.cheatsheet.AddCheatSheetCommand.MESSAGE_SUCCESSFUL_AUTOGENERATE;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelManager;
import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.flashcard.Answer;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Question;
import seedu.address.model.flashcard.Title;
import seedu.address.model.note.Content;
import seedu.address.model.note.Note;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CheatSheetBuilder;

public class AddCheatSheetCommandTest {
    @Test
    public void constructor_nullCheatSheet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCheatSheetCommand(null));
    }

    @Test
    public void execute_cheatSheetAcceptedByModel_addSuccessful() throws Exception {
        ModelManager model = new ModelManager();
        CheatSheet validCheatSheet = new CheatSheetBuilder().build();
        CommandResult commandResult = new AddCheatSheetCommand(validCheatSheet).execute(model);

        assertEquals(
                String.format(AddCheatSheetCommand.MESSAGE_SUCCESS, validCheatSheet)
                        + "\n" + 0 + MESSAGE_SUCCESSFUL_AUTOGENERATE,
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validCheatSheet).toString(), model.getFilteredCheatSheetList().toString());
    }

    @Test
    public void execute_duplicateCheatSheet_throwsCommandException() {
        CheatSheet validCheatSheet = new CheatSheetBuilder().build();
        AddCheatSheetCommand addCheatSheetCommand = new AddCheatSheetCommand(validCheatSheet);

        ModelManager model = new ModelManager();
        model.addCheatSheet(validCheatSheet);

        assertThrows(CommandException.class,
                AddCheatSheetCommand.MESSAGE_DUPLICATE_CHEATSHEET, () -> addCheatSheetCommand.execute(model));
    }

    @Test
    public void equals() {
        CheatSheet cs1 = new CheatSheetBuilder().withTitle("cs1").build();
        CheatSheet cs2 = new CheatSheetBuilder().withTitle("cs2").build();
        AddCheatSheetCommand addCs1Command = new AddCheatSheetCommand(cs1);
        AddCheatSheetCommand addCs2Command = new AddCheatSheetCommand(cs2);

        // same object -> returns true
        assertTrue(addCs1Command.equals(addCs1Command));

        // same values -> returns true
        AddCheatSheetCommand addAliceCommandCopy = new AddCheatSheetCommand(cs1);
        assertTrue(addCs1Command.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addCs1Command.equals(1));

        // null -> returns false
        assertFalse(addCs1Command.equals(null));

        // different person -> returns false
        assertFalse(addCs1Command.equals(addCs2Command));
    }

    @Test
    public void method_getRelevantContents_returnsTrue() {
        Set<Tag> tagList1 = new HashSet<>();
        Set<Tag> tagList2 = new HashSet<>();
        Set<Tag> tagList3 = new HashSet<>();

        tagList1.add(new Tag("tag1"));
        tagList2.add(new Tag("tag2"));
        tagList3.add(new Tag("tag3"));
        tagList3.add(new Tag("tag4"));

        Flashcard flashcard = new Flashcard(new Question("question1"), new Answer("answer1"),
                new Title("fctitle1"), tagList1);

        Flashcard flashcard1 = new Flashcard(new Question("question2"), new Answer("answer2"),
                new Title("fctitle2"), tagList3);

        Note note = new Note(new seedu.address.model.note.Title("ntitle1"),
                new Content("content1"), tagList2);

        Note note1 = new Note(new seedu.address.model.note.Title("ntitle2"),
                new Content("content2"), tagList3);

        ModelManager model = new ModelManager();
        model.addFlashcard(flashcard);
        model.addFlashcard(flashcard1);
        model.addNote(note);
        model.addNote(note1);

        CheatSheet cs = new CheatSheetBuilder().build();
        AddCheatSheetCommand addCsCommand = new AddCheatSheetCommand(cs);

        Set<seedu.address.model.cheatsheet.Content> content1 = new HashSet<>();
        content1.add(new seedu.address.model.cheatsheet.Content(flashcard.getQuestion().toString(),
                flashcard.getAnswer().toString(), flashcard.getTags()));

        Set<seedu.address.model.cheatsheet.Content> content2 = new HashSet<>();
        content2.add(new seedu.address.model.cheatsheet.Content(
                note.getContentCleanedFromTags().toString(), note.getTags()));

        Set<seedu.address.model.cheatsheet.Content> content3 = new HashSet<>();
        content3.add(new seedu.address.model.cheatsheet.Content(flashcard1.getQuestion().toString(),
                flashcard1.getAnswer().toString(), flashcard1.getTags()));
        content3.add(new seedu.address.model.cheatsheet.Content(
                note1.getContentCleanedFromTags().toString(), note1.getTags()));

        // only fc
        assertEquals(content1, addCsCommand.getRelevantContents(tagList1, model));

        // only note
        assertEquals(content2, addCsCommand.getRelevantContents(tagList2, model));

        // TO-BE-TESTED: only note fragment

        // both note and fc
        assertEquals(content3, addCsCommand.getRelevantContents(tagList3, model));

    }
}
