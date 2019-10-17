package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.CardBookParser;
import seedu.address.logic.parser.FileBookParser;
import seedu.address.logic.parser.NoteBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CardBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyFileBook;
import seedu.address.model.ReadOnlyNoteBook;
import seedu.address.model.card.Card;
import seedu.address.model.file.EncryptedFile;
import seedu.address.model.note.Note;
import seedu.address.model.person.Person;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;
    private final FileBookParser fileBookParser;
    private final CardBookParser cardBookParser;
    private final NoteBookParser noteBookParser;

    private String mode;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
        fileBookParser = new FileBookParser(storage.getStoragePassword());
        cardBookParser = new CardBookParser();
        mode = "home";
        noteBookParser = new NoteBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        CommandResult commandResult;
        Command command;
        switch (mode) {
        case "file":
            command = fileBookParser.parseCommand(commandText);
            break;
        case "card":
            command = cardBookParser.parseCommand(commandText);
            break;
        case "note":
            command = noteBookParser.parseCommand(commandText);
            break;
        default:
            command = addressBookParser.parseCommand(commandText);
            break;
        }

        commandResult = command.execute(model);

        try {
            switch (mode) {
            case "file":
                storage.saveFileBook(model.getFileBook());
                break;
            case "card":
                // storage.saveCardBook(model.getCardBook());
                break;
            case "note":
                storage.saveNoteBook(model.getNoteBook());
                break;
            default:
                storage.saveAddressBook(model.getAddressBook());
                break;
            }
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ReadOnlyNoteBook getNoteBook() {
        return model.getNoteBook();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public ReadOnlyFileBook getFileBook() {
        return model.getFileBook();
    }

    @Override
    public ObservableList<EncryptedFile> getFilteredFileList() {
        return model.getFilteredFileList();
    }

    @Override
    public ObservableList<Note> getFilteredNoteList() {
        return model.getFilteredNoteList();

    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public CardBook getCardBook() {
        return model.getCardBook();
    }

    @Override
    public ObservableList<Card> getFilteredCardList() {
        return model.getFilteredCardList();
    }

    @Override
    public Path getCardBookFilePath() {
        return model.getCardBookFilePath();
    }

    public Path getNoteBookFilePath() {
        return model.getNoteBookFilePath();
    }
    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public void setMode(String newMode) {
        mode = newMode;
    }

    @Override
    public String getMode() {
        return mode;
    }
}
