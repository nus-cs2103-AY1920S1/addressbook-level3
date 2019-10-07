package seedu.address.model.display.mainwindow;

/**
 * Main window display model.
 */
public class MainWindowDisplay {

    private WeekSchedule weekSchedule;
    private MainWindowDisplayType mainWindowDisplayType;

    public MainWindowDisplay(WeekSchedule weekSchedule, MainWindowDisplayType mainWindowDisplayType) {
        this.mainWindowDisplayType = mainWindowDisplayType;
        this.weekSchedule = weekSchedule;
    }

    public MainWindowDisplay() {
        this.weekSchedule = null;
        this.mainWindowDisplayType = MainWindowDisplayType.EMPTY;
    }

    public MainWindowDisplayType getMainWindowDisplayType() {
        return mainWindowDisplayType;
    }

    public WeekSchedule getWeekSchedule() {
        return weekSchedule;
    }
}
