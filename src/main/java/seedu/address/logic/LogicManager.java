package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Mode;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.CardBookParser;
import seedu.address.logic.parser.FileBookParser;
import seedu.address.logic.parser.NoteBookParser;
import seedu.address.logic.parser.PasswordBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyCardBook;
import seedu.address.model.ReadOnlyFileBook;
import seedu.address.model.ReadOnlyNoteBook;
import seedu.address.model.ReadOnlyPasswordBook;
import seedu.address.model.card.Card;
import seedu.address.model.card.ExpiringCard;
import seedu.address.model.file.EncryptedFile;
import seedu.address.model.note.Note;
import seedu.address.model.password.Password;
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
    private final PasswordBookParser passwordBookParser;

    private Mode mode;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        mode = Mode.PASSWORD;
        addressBookParser = new AddressBookParser();
        fileBookParser = new FileBookParser(storage.getStoragePassword());
        cardBookParser = new CardBookParser();
        noteBookParser = new NoteBookParser();
        passwordBookParser = new PasswordBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = parseCommand(commandText);
        CommandResult commandResult;
        try {
            commandResult = command.execute(model);
        } catch (CommandException e) {
            saveToBook();
            throw e;
        }
        saveToBook();
        return commandResult;
    }

    /**
     * Parses the command text using the appropriate parser.
     */
    private Command parseCommand(String commandText) throws ParseException {
        switch (mode) {
        case FILE:
            return fileBookParser.parseCommand(commandText);
        case CARD:
            return cardBookParser.parseCommand(commandText);
        case NOTE:
            return noteBookParser.parseCommand(commandText);
        case PASSWORD:
            return passwordBookParser.parseCommand(commandText);
        default:
            return addressBookParser.parseCommand(commandText);
        }
    }

    /**
     * Saves the appropriate book to storage.
     */
    private void saveToBook() throws CommandException {
        try {
            switch (mode) {
            case FILE:
                storage.saveFileBook(model.getFileBook());
                break;
            case CARD:
                storage.saveCardBook(model.getCardBook());
                break;
            case NOTE:
                storage.saveNoteBook(model.getNoteBook());
                break;
            case PASSWORD:
                storage.savePasswordBook(model.getPasswordBook());
                break;
            default:
                storage.saveAddressBook(model.getAddressBook());
                break;
            }
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
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
    public Path getFileBookFilePath() {
        return model.getFileBookFilePath();
    }

    @Override
    public ReadOnlyCardBook getCardBook() {
        return model.getCardBook();
    }

    @Override
    public ObservableList<Card> getFilteredCardList() {
        return model.getFilteredCardList();
    }

    @Override
    public ObservableList<ExpiringCard> getExpiringCardList() {
        return model.getFilteredCardList()
                .stream()
                .map(ExpiringCard::of)
                .filter(card -> card.getMonthToExp() >= 0 && card.getMonthToExp() <= 2)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    @Override
    public Path getCardBookFilePath() {
        return model.getCardBookFilePath();
    }

    @Override
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

    public void setMode(Mode newMode) {
        mode = newMode;
    }

    @Override
    public Mode getMode() {
        return mode;
    }

    public ReadOnlyPasswordBook getPasswordBook() {
        return model.getPasswordBook();
    }

    @Override
    public Path getPasswordBookFilePath() {
        return model.getPasswordBookFilePath();
    }

    @Override
    public ObservableList<Password> getFilteredPasswordList() {
        return model.getFilteredPasswordList();
    }
}
