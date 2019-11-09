package seedu.weme.logic;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import seedu.weme.commons.core.GuiSettings;
import seedu.weme.commons.core.LogsCenter;
import seedu.weme.logic.commands.Command;
import seedu.weme.logic.commands.CommandResult;
import seedu.weme.logic.commands.exceptions.CommandException;
import seedu.weme.logic.parser.contextparser.WemeParser;
import seedu.weme.logic.parser.exceptions.ParseException;
import seedu.weme.logic.parser.util.ParserUtil;
import seedu.weme.logic.prompter.contextprompter.WemePrompter;
import seedu.weme.logic.prompter.exceptions.PromptException;
import seedu.weme.logic.prompter.prompt.CommandPrompt;
import seedu.weme.logic.prompter.util.PrompterUtil;
import seedu.weme.model.Model;
import seedu.weme.model.ModelContext;
import seedu.weme.model.ReadOnlyWeme;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.template.MemeText;
import seedu.weme.model.template.Template;
import seedu.weme.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;

    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;

        ModelContext currentContext = model.getContext().getValue();
        WemeParser wemeParser = ParserUtil.forContext(currentContext);
        Command command = wemeParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveWeme(model.getWeme());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public void setContext(ModelContext context) {
        model.setContext(context);
    }

    @Override
    public ObservableValue<ModelContext> getContext() {
        return model.getContext();
    }

    @Override
    public ObservableList<MemeText> getMemeTextList() {
        return model.getMemeTextList();
    }

    @Override
    public Optional<BufferedImage> getMemeCreationImage() {
        return model.getMemeCreationImage();
    }

    @Override
    public CommandPrompt prompt(String commandText) throws PromptException {
        ModelContext currentContext = model.getContext().getValue();
        WemePrompter wemePrompter = PrompterUtil.forContext(currentContext);
        CommandPrompt commandPrompt = wemePrompter.promptCommand(model, commandText);

        return commandPrompt;
    }

    @Override
    public ReadOnlyWeme getWeme() {
        return model.getWeme();
    }

    @Override
    public ObservableList<Meme> getFilteredMemeList() {
        return model.getFilteredMemeList();
    }

    @Override
    public ObservableList<Meme> getStagedMemeList() {
        return model.getStagedMemeList();
    }

    @Override
    public ObservableList<Meme> getImportMemeList() {
        return model.getImportList();
    }

    @Override
    public ObservableList<Template> getFilteredTemplateList() {
        return model.getFilteredTemplateList();
    }

    @Override
    public ObservableValue<Meme> getViewableMeme() {
        return model.getViewableMeme();
    }

    @Override
    public Path getWemeFilePath() {
        return model.getDataFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public ObservableMap<String, String> getObservableUserPreferences() {
        return model.getObservableUserPreferences();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public ObservableMap<String, SimpleIntegerProperty> getObservableLikeData() {
        return model.getObservableLikeData();
    }

    @Override
    public ObservableMap<String, SimpleIntegerProperty> getObservableDislikeData() {
        return model.getObservableDislikeData();
    }

    @Override
    public void cleanUp() {
        new Thread(() -> model.cleanMemeStorage()).start();
        new Thread(() -> model.cleanTemplateStorage()).start();
    }
}
