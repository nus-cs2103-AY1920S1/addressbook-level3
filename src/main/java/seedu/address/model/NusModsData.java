package seedu.address.model;

import seedu.address.model.module.AcadCalendar;
import seedu.address.model.module.DetailedModuleList;
import seedu.address.model.module.Holidays;
import seedu.address.model.module.ModuleCondensedList;

/**
 * Contains all the information the app needs from NUSMods such as modules, academic calendar, holidays, etc.
 */
public class NusModsData {

    private ModuleCondensedList moduleCondensedList;
    private DetailedModuleList detailedModuleList;
    private AcadCalendar acadCalendar;
    private Holidays holidays;

    //TODO: constructors, getters & settors
    public NusModsData() {
        this.detailedModuleList = new DetailedModuleList();
    }

    public NusModsData(DetailedModuleList detailedModuleList) {
        this.detailedModuleList = detailedModuleList;
    }

    public ModuleCondensedList getModuleCondensedList() {
        return moduleCondensedList;
    }

    public void setModuleCondensedList(ModuleCondensedList moduleCondensedList) {
        this.moduleCondensedList = moduleCondensedList;
    }

    public DetailedModuleList getDetailedModuleList() {
        return detailedModuleList;
    }

    public void setDetailedModuleList(DetailedModuleList detailedModuleList) {
        this.detailedModuleList = detailedModuleList;
    }

    public AcadCalendar getAcadCalendar() {
        return acadCalendar;
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
