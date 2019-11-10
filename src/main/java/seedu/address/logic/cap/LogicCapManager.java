package seedu.address.logic.cap;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.image.Image;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.cap.commands.Command;
import seedu.address.logic.cap.commands.CommandResult;
import seedu.address.logic.cap.commands.exceptions.CommandException;
import seedu.address.logic.cap.parser.CapLogParser;
import seedu.address.logic.cap.parser.exceptions.ParseException;
import seedu.address.model.cap.Model;
import seedu.address.model.cap.ReadOnlyCapLog;
import seedu.address.model.cap.person.Semester;
import seedu.address.model.common.Module;
import seedu.address.storage.cap.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicCapManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicCapManager.class);

    private final Model model;
    private final Storage storage;
    private final CapLogParser capLogParser;

    public LogicCapManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        capLogParser = new CapLogParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        //Logging, safe to ignore
        logger.info("----------------[USER COMMAND][" + commandText + "]-----------------------");

        CommandResult commandResult;
        //Parse user input from String to a Command
        Command command = capLogParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            //We can deduce that the previous line of code modifies model in some way
            // since it's being stored here.
            storage.saveCapLog(model.getCapLog());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyCapLog getCapLog() {
        return model.getCapLog();
    }

    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return model.getFilteredModuleList();
    }

    @Override
    public ObservableList<Semester> getFilteredSemesterList() {
        return model.getFilteredSemesterList();
    }

    @Override
    public Path getCapLogFilePath() {
        return model.getCapLogFilePath();
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
    public double getFilteredCapInformation() {
        return model.getFilteredCapInformation();
    }

    @Override
    public double getFilteredMcInformation() {
        return model.getFilteredMcInformation();
    }

    @Override
    public ObservableList<PieChart.Data> getFilteredGradeCounts() {
        return model.getFilteredGradeCounts();
    }

    //=========== Achievements =============================================================

    @Override
    public boolean upRank() {
        return model.upRank();
    }

    @Override
    public boolean downRank() {
        return model.downRank();
    }

    @Override
    public Image getRankImage() {
        return model.getRankImage();
    }

    @Override
    public String getRankTitle() {
        return model.getRankTitle();
    }

    @Override
    public void updateRank(double cap) {
        model.updateRank(cap);
    }
}
