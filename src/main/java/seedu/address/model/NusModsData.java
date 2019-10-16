package seedu.address.model;

import seedu.address.model.module.AcadCalendar;
import seedu.address.model.module.Holidays;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleList;

/**
 * Contains all the information the app needs from NUSMods such as modules, academic calendar, holidays, etc.
 */
public class NusModsData {

    private ModuleList moduleList;
    private AcadCalendar acadCalendar;
    private Holidays holidays;

    public NusModsData() {
        this.moduleList = new ModuleList();
    }

    public NusModsData(ModuleList moduleList) {
        this.moduleList = moduleList;
    }

    public ModuleList getModuleList() {
        return moduleList;
    }

    public void setModuleList(ModuleList moduleList) {
        this.moduleList = moduleList;
    }

    public AcadCalendar getAcadCalendar() {
        return acadCalendar;
    }

    public void addModule(Module module) {
        moduleList.addModule(module);
    }

    public void setAcadCalendar(AcadCalendar acadCalendar) {
        this.acadCalendar = acadCalendar;
    }

    public Holidays getHolidays() {
        return holidays;
    }

    public void setHolidays(Holidays holidays) {
        this.holidays = holidays;
    }
}
