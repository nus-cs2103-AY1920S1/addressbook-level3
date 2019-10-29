package seedu.address.model;

import java.time.LocalDate;

import seedu.address.model.module.AcadCalendar;
import seedu.address.model.module.AcadYear;
import seedu.address.model.module.Holidays;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleList;
import seedu.address.model.module.ModuleSummaryList;
import seedu.address.model.module.SemesterNo;

/**
 * Contains all the information the app needs from NUSMods such as modules, academic calendar, holidays, etc.
 */
public class NusModsData {

    private ModuleSummaryList moduleSummaryList;
    private ModuleList moduleList;
    private AcadCalendar acadCalendar;
    private Holidays holidays;

    public NusModsData() {
        this.moduleList = new ModuleList();
    }

    public NusModsData(ModuleList moduleList) {
        this.moduleList = moduleList;
    }

    public ModuleSummaryList getModuleSummaryList() {
        return moduleSummaryList;
    }

    public void setModuleSummaryList(ModuleSummaryList moduleSummaryList) {
        this.moduleSummaryList = moduleSummaryList;
    }

    public ModuleList getModuleList() {
        return moduleList;
    }

    public void setModuleList(ModuleList moduleList) {
        this.moduleList = moduleList;
    }

    public void addModule(Module module) {
        moduleList.addModule(module);
    }

    public AcadCalendar getAcadCalendar() {
        return acadCalendar;
    }

    public void setAcadCalendar(AcadCalendar acadCalendar) {
        this.acadCalendar = acadCalendar;
    }

    public LocalDate getStartDate(AcadYear acadYear, SemesterNo semesterNo) {
        return this.acadCalendar.getStartDate(acadYear, semesterNo);
    }

    public Holidays getHolidays() {
        return holidays;
    }

    public void setHolidays(Holidays holidays) {
        this.holidays = holidays;
    }
}
