package tagline.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import tagline.commons.core.GuiSettings;
import tagline.commons.core.LogsCenter;
import tagline.logic.commands.Command;
import tagline.logic.commands.CommandResult;
import tagline.logic.commands.exceptions.CommandException;
import tagline.logic.parser.Prompt;
import tagline.logic.parser.TaglineParser;
import tagline.logic.parser.exceptions.ParseException;
import tagline.model.Model;
import tagline.model.contact.Contact;
import tagline.model.contact.ReadOnlyAddressBook;
import tagline.model.group.Group;
import tagline.model.group.ReadOnlyGroupBook;
import tagline.model.note.Note;
import tagline.model.note.ReadOnlyNoteBook;
import tagline.model.tag.ReadOnlyTagBook;
import tagline.model.tag.Tag;
import tagline.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final TaglineParser taglineParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        taglineParser = new TaglineParser();
    }

    @Override
    public CommandResult execute(String commandText, List<Prompt> filledPrompts)
            throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = taglineParser.parseCommand(commandText, filledPrompts);
        commandResult = command.execute(model);

        try {
            storage.saveAddressBook(model.getAddressBook());
            storage.saveNoteBook(model.getNoteBook());
            storage.saveGroupBook(model.getGroupBook());
            storage.saveTagBook(model.getTagBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        return execute(commandText, Collections.emptyList());
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Contact> getFilteredContactList() {
        return model.getFilteredContactList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public ReadOnlyNoteBook getNoteBook() {
        return model.getNoteBook();
    }

    @Override
    public ObservableList<Note> getFilteredNoteList() {
        return model.getFilteredNoteList();
    }

    @Override
    public Path getNoteBookFilePath() {
        return model.getNoteBookFilePath();
    }

    @Override
    public ReadOnlyGroupBook getGroupBook() {
        return model.getGroupBook();
    }

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return model.getFilteredGroupList();
    }

    @Override
    public Path getGroupBookFilePath() {
        return model.getGroupBookFilePath();
    }

    @Override
    public ReadOnlyTagBook getTagBook() {
        return model.getTagBook();
    }

    @Override
    public ObservableList<Tag> getFilteredTagList() {
        return model.getFilteredTagList();
    }

    @Override
    public Path getTagBookFilePath() {
        return model.getTagBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
