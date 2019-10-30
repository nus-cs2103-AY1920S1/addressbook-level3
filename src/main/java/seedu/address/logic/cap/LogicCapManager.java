package seedu.address.logic.cap;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
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
import seedu.address.model.cap.util.GradeHash;
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
        logger.info("----------------[USER COMMAND][" + commandText + "]");

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
        double result = 0.0;
        String letterGrade;
        GradeHash gradeConverter = new GradeHash();
        double numerator = 0.0;
        double denominator = 0.0;
        double modularCredit;
        if (model.getModuleCount() != 0) {
            for (Module module : model.getFilteredModuleList()) {
                letterGrade = module.getGrade().getGrade();
                modularCredit = (double) module.getCredit().getCredit();
                numerator += gradeConverter.convertToGradePoint(letterGrade) * modularCredit;
                denominator += modularCredit;
            }
        }

        if (denominator != 0.0) {
            result = numerator / denominator;
        }
        return result;
    }

    @Override
    public double getFilteredMcInformation() {
        double result = 0.0;
        if (model.getModuleCount() != 0) {
            for (Module module : model.getFilteredModuleList()) {
                result += Integer.valueOf(module.getCredit().getCredit());
            }
        }

        return result;
    }

    public ObservableList<Data> getFilteredGradeCounts() {
        ObservableList<Data> result = FXCollections.observableArrayList();
        ObservableList<Module> filteredModules = model.getFilteredModuleList();

        Module module;
        HashSet<String> set = new HashSet<>();
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < filteredModules.size(); i++) {
            module = filteredModules.get(i);
            String grade = module.getGrade().getGrade();
            list.add(grade);
            set.add(grade);
        }

        for (String grade : set) {
            result.add(new PieChart.Data(grade, Collections.frequency(list, grade)));
        }

        return result;
    }
}
