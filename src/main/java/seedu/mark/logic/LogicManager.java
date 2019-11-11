package seedu.mark.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.Logger;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import seedu.mark.commons.core.GuiSettings;
import seedu.mark.commons.core.LogsCenter;
import seedu.mark.logic.commands.Command;
import seedu.mark.logic.commands.exceptions.CommandException;
import seedu.mark.logic.commands.results.CommandResult;
import seedu.mark.logic.parser.MarkParser;
import seedu.mark.logic.parser.exceptions.ParseException;
import seedu.mark.model.Model;
import seedu.mark.model.ReadOnlyMark;
import seedu.mark.model.annotation.OfflineDocument;
import seedu.mark.model.annotation.Paragraph;
import seedu.mark.model.autotag.SelectiveBookmarkTagger;
import seedu.mark.model.bookmark.Bookmark;
import seedu.mark.model.bookmark.Url;
import seedu.mark.model.folderstructure.FolderStructure;
import seedu.mark.model.reminder.Reminder;
import seedu.mark.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final MarkParser markParser;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        markParser = new MarkParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = markParser.parseCommand(commandText);
        commandResult = command.execute(model, storage);

        try {
            storage.saveMark(model.getMark());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyMark getMark() {
        return model.getMark();
    }

    @Override
    public ObservableList<Bookmark> getFilteredBookmarkList() {
        return model.getFilteredBookmarkList();
    }

    @Override
    public ObservableList<Bookmark> getFavoriteBookmarkList() {
        return model.getFavoriteBookmarkList();
    }

    @Override
    public FolderStructure getFolderStructure() {
        return model.getMark().getFolderStructure();
    }

    @Override
    public Path getMarkFilePath() {
        return model.getMarkFilePath();
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
    public SimpleObjectProperty<Url> getCurrentUrlProperty() {
        return model.getCurrentUrlProperty();
    }

    @Override
    public void setCurrentUrl(Url url) {
        model.setCurrentUrl(url);
    }

    @Override
    public ObservableList<Paragraph> getObservableDocument() {
        return model.getObservableDocument();
    }

    @Override
    public void updateDocument(OfflineDocument doc) {
        model.updateDocument(doc);
    }

    @Override
    public ObservableValue<String> getObservableOfflineDocNameCurrentlyShowing() {
        return model.getObservableOfflineDocNameCurrentlyShowing();
    }

    @Override
    public void setOfflineDocNameCurrentlyShowing(String name) {
        model.setOfflineDocNameCurrentlyShowing(name);
    }

    @Override
    public ObservableList<Reminder> getReminderList() {
        return model.getMark().getReminderList();
    }

    @Override
    public ObservableList<SelectiveBookmarkTagger> getAutotags() {
        return model.getMark().getAutotags();
    }

    @Override
    public SimpleObjectProperty<Bookmark> getBookmarkDisplayingCacheProperty() {
        return model.getBookmarkDisplayingCacheProperty();
    }

    @Override
    /**
     * Delete mark's timer.
     */
    public void startMarkTimer(ScheduledExecutorService executor) {
        model.startTimer(executor);
    }

    @Override
    public Bookmark getBookmarkFromReminder(Reminder reminder) {
        return model.getBookmarkFromReminder(reminder);
    }


}
