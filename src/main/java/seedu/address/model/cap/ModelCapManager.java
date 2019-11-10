package seedu.address.model.cap;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.chart.PieChart;
import javafx.scene.image.Image;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.cap.module.Semester;
import seedu.address.model.cap.util.GradeHash;
import seedu.address.model.common.Module;

/**
 * Represents the in-memory model of the Cap Log data.
 */
public class ModelCapManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelCapManager.class);

    private final CapLog capLog;
    private final CapUserPrefs capUserPrefs;
    private final FilteredList<Semester> filteredSemesters;
    private final AchievementManager achievementManager;
    private FilteredList<Module> filteredModules;
    private boolean isPromoted;
    private boolean isDowngraded;

    /**
     * Initializes a ModelManager with the given CapLog and userPrefs.
     */
    public ModelCapManager(ReadOnlyCapLog capLog, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(capLog, userPrefs);

        logger.fine("Initializing with cap log: " + capLog + " and user prefs " + userPrefs);

        this.capLog = new CapLog(capLog);
        this.capUserPrefs = new CapUserPrefs(userPrefs);
        filteredSemesters = new FilteredList<>(this.capLog.getSemesterList());
        filteredModules = new FilteredList<>(this.capLog.getModuleList());
        achievementManager = new AchievementManager();
    }

    public ModelCapManager() {
        this(new CapLog(), new CapUserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setCapUserPrefs(ReadOnlyUserPrefs capUserPrefs) {
        requireNonNull(capUserPrefs);
        this.capUserPrefs.resetData(capUserPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getCapUserPrefs() {
        return capUserPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return capUserPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        capUserPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getCapLogFilePath() {
        return capUserPrefs.getCapLogFilePath();
    }

    @Override
    public void setCapLogFilePath(Path capLogFilePath) {
        requireNonNull(capLogFilePath);
        capUserPrefs.setCapLogFilePath(capLogFilePath);
    }

    //=========== CapLog ================================================================================

    @Override
    public void setCapLog(ReadOnlyCapLog capLog) {
        this.capLog.resetData(capLog);
    }

    @Override
    public ReadOnlyCapLog getCapLog() {
        return capLog;
    }

    @Override
    public boolean hasSemester (Semester semester) {
        requireNonNull(semester);
        return capLog.hasSemester(semester);
    }

    @Override
    public void deleteSemester(Semester target) {
        capLog.removeSemester(target);
    }

    @Override
    public void addSemester (Semester semester) {
        capLog.addSemester(semester);
        updateFilteredSemesterList(PREDICATE_SHOW_ALL_SEMESTERS);
    }

    @Override
    public void setSemester(Semester target, Semester editedModule) {
        requireAllNonNull(target, editedModule);
        capLog.setSemester(target, editedModule);
    }

    @Override
    public ObservableList<Semester> getFilteredSemesterList() {
        return filteredSemesters;
    }

    public int getModuleCount() {
        return filteredModules.size();
    }

    @Override
    public void updateFilteredSemesterList(Predicate<Semester> predicate) {
        requireNonNull(predicate);
        filteredSemesters.setPredicate(predicate);
    }
    //=========== Filtered Module List Accessors =============================================================

    @Override
    public boolean hasModule (Module module) {
        requireNonNull(module);
        return capLog.hasModule(module);
    }

    @Override
    public void deleteModule(Module target) {
        capLog.removeModule(target);
    }

    @Override
    public void addModule (Module module) {
        capLog.addModule(module);
        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void setModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule);

        capLog.setModule(target, editedModule);
    }

    @Override
    public ObservableList<Module> getFilteredModuleList() {
        return filteredModules;
    }

    @Override
    public void updateFilteredModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredModules.setPredicate(predicate);
    }

    @Override
    public FilteredList<Module> getFilteredListbyTime() {
        return new FilteredList<Module>(new SortedList<Module>(this.capLog.getModuleList(),
                new Comparator<Module>() {
                    @Override
                    public int compare(Module targetModule, Module otherModule) {
                        String academicYearTarget = targetModule.getSemester().getAcademicYear().getAcademicYear();
                        String academicYearOther = otherModule.getSemester().getAcademicYear().getAcademicYear();

                        int semesterTarget = targetModule.getSemester().getSemesterPeriod().getSemesterPeriod();
                        int semesterOther = otherModule.getSemester().getSemesterPeriod().getSemesterPeriod();

                        if (Integer.parseInt(academicYearTarget) > Integer.parseInt(academicYearOther)) {
                            return 1;
                        } else if (Integer.parseInt(academicYearTarget) < Integer.parseInt(academicYearOther)) {
                            return -1;
                        }

                        return semesterTarget - semesterOther;
                    }
                }));
    }

    @Override
    public void setSortedList() {
        filteredModules = getFilteredListbyTime();
        capLog.setModules(filteredModules);

        updateFilteredModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelCapManager)) {
            return false;
        }

        // state check
        ModelCapManager other = (ModelCapManager) obj;
        return capLog.equals(other.capLog)
                && capUserPrefs.equals(other.capUserPrefs)
                && filteredModules.equals(other.filteredModules);
    }



    //=========== CAP =============================================================

    @Override
    public double getFilteredCapInformation() {
        logger.info("Calculating CAP information...");

        double result = 0.0;
        String letterGrade;
        GradeHash gradeConverter = new GradeHash();
        double numerator = 0.0;
        double denominator = 0.0;
        double modularCredit;
        if (getModuleCount() != 0) {
            for (Module module : getFilteredModuleList()) {
                letterGrade = module.getGrade().getGrade();
                modularCredit = (double) module.getCredit().getCredit();
                numerator += gradeConverter.convertToGradePoint(letterGrade) * modularCredit;
                denominator += modularCredit;
            }
        }

        if (denominator != 0.0) {
            result = numerator / denominator;
        }
        updateRank(result);
        return result;
    }

    @Override
    public double getFilteredMcInformation() {
        logger.fine("Calculating total number of modular credit done...");

        double result = 0.0;
        if (getModuleCount() != 0) {
            for (Module module : getFilteredModuleList()) {
                result += Integer.valueOf(module.getCredit().getCredit());
            }
        }

        return result;
    }

    //=========== PieChart =============================================================

    public ObservableList<PieChart.Data> getFilteredGradeCounts() {
        logger.info("Calibrating pie chart for the grades...");

        ObservableList<PieChart.Data> result = FXCollections.observableArrayList();
        ObservableList<Module> filteredModules = getFilteredModuleList();

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

    //=========== Achievements =============================================================

    @Override
    public boolean upRank() {
        return isPromoted;
    }

    @Override
    public boolean downRank() {
        return isDowngraded;
    }

    /**
     * updates the rank for the student based on his/her new CAP.
     */
    public void updateRank(double cap) {
        achievementManager.updateCap(cap);
        if (achievementManager.hasAchievementChanged()) {
            isPromoted = achievementManager.isPromoted();
            isDowngraded = achievementManager.isDownGraded();
        } else {
            isDowngraded = false;
            isPromoted = false;
        }
    }

    @Override
    public Image getRankImage() {
        return new Image(achievementManager.getRankImageFilePath());
    }

    @Override
    public String getRankTitle() {
        return achievementManager.getCurrentRank().toString();
    }
}
