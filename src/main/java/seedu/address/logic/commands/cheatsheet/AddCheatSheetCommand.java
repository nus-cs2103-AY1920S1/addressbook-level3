package seedu.address.logic.commands.cheatsheet;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.ADD;
import static seedu.address.logic.commands.cheatsheet.EditCheatSheetCommand.createEditedCheatSheet;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_NOTES;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javafx.collections.ObservableList;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.CheatSheetCommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.cheatsheet.CheatSheet;
import seedu.address.model.cheatsheet.Content;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.FlashcardContainsTagPredicate;
import seedu.address.model.note.Note;
import seedu.address.model.note.NoteContainsTagPredicate;
import seedu.address.model.note.NoteFragment;
import seedu.address.model.tag.Tag;

/**
 * Adds a cheatsheet to the address book.
 */
public class AddCheatSheetCommand extends Command {
    public static final String COMMAND_WORD = ADD;

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a cheatsheet. "
            + "\nParameters: "
            + PREFIX_TITLE + "TITLE "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TITLE + "midterm quiz "
            + PREFIX_TAG + "cs2103t ";

    public static final String MESSAGE_SUCCESS = "New cheatsheet added: %1$s";
    public static final String MESSAGE_DUPLICATE_CHEATSHEET = "This cheatsheet already exists";
    public static final String MESSAGE_SUCCESSFUL_AUTOGENERATE =
            " content(s) have been successfully generated from the other modes.";
    public static final String MESSAGE_TAG_RESTRICTION = "Each cheatsheet must have at least 1 tag specified.";

    private final CheatSheet toAdd;

    private final Logger logger = LogsCenter.getLogger(AddCheatSheetCommand.class.getName());

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCheatSheetCommand(CheatSheet cheatsheet) {
        requireNonNull(cheatsheet);
        toAdd = cheatsheet;

        logger.info("Add cheatsheet command created.");
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasCheatSheet(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CHEATSHEET);
        }

        int currentAmountOfCheatSheets = model.getFilteredCheatSheetList().size();
        model.addCheatSheet(toAdd);
        int newAmountOfCheatSheets = model.getFilteredCheatSheetList().size();

        // to assert that one cheatsheet got added
        assert(newAmountOfCheatSheets == currentAmountOfCheatSheets + 1);

        EditCheatSheetCommand.EditCheatSheetDescriptor edit = new EditCheatSheetCommand.EditCheatSheetDescriptor();
        edit.setContents(getRelevantContents(toAdd.getTags(), model));
        CheatSheet editedCheatSheet = createEditedCheatSheet(toAdd, edit, true);

        model.setCheatSheet(toAdd, editedCheatSheet);
        int numberOfContentPulled = editedCheatSheet.getContents().size();

        logger.info("Cheatsheet is created.");
        return new CheatSheetCommandResult(String.format(MESSAGE_SUCCESS, editedCheatSheet)
        + "\n" + numberOfContentPulled + MESSAGE_SUCCESSFUL_AUTOGENERATE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCheatSheetCommand // instanceof handles nulls
                && toAdd.equals(((AddCheatSheetCommand) other).toAdd));
    }

    /**
     * Retrieves all the notes and flashcards with the relevant tags
     */
    public Set<Content> getRelevantContents(Set<Tag> tags, Model model) {
        logger.info("Automatically retrieve contents from flashcards and notes");

        Set<Content> contentList = new HashSet<>();

        // get all notes
        NoteContainsTagPredicate noteTagPredicate = new NoteContainsTagPredicate(tags);
        model.updateFilteredNoteList(noteTagPredicate);
        ObservableList<Note> noteList = model.getFilteredNoteList();

        int numOfIgnored = 0;

        for (Note note: noteList) {
            try {
                contentList.add(new Content(note.getContentCleanedFromTags().toString(), note.getTags()));
            } catch (IllegalArgumentException ignored) {
                // ignore invalid content
                numOfIgnored++;
            }
        }

        //assert make sure that all notes matched the requirements are added into the list
        assert(contentList.size() == noteList.size() - numOfIgnored);

        // get all note fragments
        model.updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);
        noteList = model.getFilteredNoteList();

        for (Note note : noteList) {
            List<NoteFragment> noteFragList = note.getFilteredNoteFragments(noteTagPredicate);
            int initialSize = contentList.size();
            numOfIgnored = 0;

            for (Note noteFrag : noteFragList) {
                try {
                    contentList.add(new Content(noteFrag.getContent().toString(), noteFrag.getTags()));
                } catch (IllegalArgumentException ignored) {
                    // ignore invalid content
                    numOfIgnored++;
                }
            }

            //assert make sure that all note fragments matched the requirements are added into the list
            assert(contentList.size() == initialSize + noteFragList.size() - numOfIgnored);
        }

        // get all flashcards
        FlashcardContainsTagPredicate flashcardTagPredicate = new FlashcardContainsTagPredicate(tags);
        model.updateFilteredFlashcardList(flashcardTagPredicate);
        ObservableList<Flashcard> flashcardList = model.getFilteredFlashcardList();

        int initialSize = contentList.size();

        for (Flashcard flashcard: flashcardList) {
            contentList.add(new Content(flashcard.getQuestion().toString(), flashcard.getAnswer().toString(),
                    flashcard.getTags()));
        }

        //assert make sure that all flashcards matched the requirements are added into the list
        assert(contentList.size() == initialSize + flashcardList.size());

        return contentList;
    }
}
