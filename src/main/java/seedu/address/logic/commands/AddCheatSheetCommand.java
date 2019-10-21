package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.ADD;
import static seedu.address.logic.commands.EditCheatSheetCommand.createEditedCheatSheet;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import java.util.HashSet;
import java.util.Set;

import javafx.collections.ObservableList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.cheatsheet.Content;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.FlashcardContainsTagPredicate;
import seedu.address.model.note.Note;
import seedu.address.model.note.NoteContainsTagPredicate;
import seedu.address.model.tag.Tag;

/**
 * Adds a cheatsheet to the address book.
 */
public class AddCheatSheetCommand extends Command {
    public static final String COMMAND_WORD = ADD;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a cheatsheet. "
            + "Parameters: "
            + PREFIX_TITLE + "TITLE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "midterm quiz "
            + PREFIX_TAG + "cs2103t ";

    public static final String MESSAGE_SUCCESS = "New cheatsheet added: %1$s";
    public static final String MESSAGE_DUPLICATE_CHEATSHEET = "This cheatsheet already exists";

    private final CheatSheet toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCheatSheetCommand(CheatSheet cheatsheet) {
        requireNonNull(cheatsheet);
        toAdd = cheatsheet;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasCheatSheet(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CHEATSHEET);
        }

        model.addCheatSheet(toAdd);
        EditCheatSheetCommand.EditCheatSheetDescriptor edit = new EditCheatSheetCommand.EditCheatSheetDescriptor();
        edit.setContents(getRelevantContents(toAdd.getTags(), model));
        CheatSheet editedCheatSheet = createEditedCheatSheet(toAdd, edit, true);



        model.setCheatSheet(toAdd, editedCheatSheet);

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedCheatSheet));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCheatSheetCommand // instanceof handles nulls
                && toAdd.equals(((AddCheatSheetCommand) other).toAdd));
    }

    /**
     * Retrieves all the notes with the relevant tags
     */
    public Set<Content> getRelevantContents(Set<Tag> tags, Model model) {
        Content.resetCounter();
        Set<Content> contentList = new HashSet<>();

        // get all notes
        NoteContainsTagPredicate noteTagPredicate = new NoteContainsTagPredicate(tags);
        model.updateFilteredNoteList(noteTagPredicate);
        ObservableList<Note> noteList = model.getFilteredNoteList();

        for (Note note: noteList) {
            contentList.add(new Content(note.getContent().toString()));
        }

        // get all flashcards
        FlashcardContainsTagPredicate flashcardTagPredicate = new FlashcardContainsTagPredicate(tags);
        model.updateFilteredFlashcardList(flashcardTagPredicate);
        ObservableList<Flashcard> flashcardList = model.getFilteredFlashcardList();

        for (Flashcard flashcard: flashcardList) {
            contentList.add(new Content(flashcard.getQuestion().toString(), flashcard.getAnswer().toString()));
        }

        return contentList;
    }
}
