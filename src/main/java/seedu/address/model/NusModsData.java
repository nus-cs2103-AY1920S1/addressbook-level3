package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.module.AcadCalendar;
import seedu.address.model.module.Holidays;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleList;

/**
 * Contains all the information the app needs from NUSMods such as modules, venues, etc.
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

    public void addModule(Module module) {
        this.moduleList.addModule(module);
    }

    public ModuleList getModuleList() {
        return this.moduleList;
    }

    public AcadCalendar getAcadCalendar() {
        return this.acadCalendar;
    }

    public void setAcadCalendar(AcadCalendar acadCalendar) {
        this.acadCalendar = acadCalendar;
    }

    public Holidays getHolidays() {
        return this.holidays;
    }

    public void setHolidays(Holidays holidays) {
        this.holidays = holidays;
    }

    public ObservableList<Module> getUnmodifiableModuleList() {
        return moduleList.asUnmodifiableObservableList();
    }
}
