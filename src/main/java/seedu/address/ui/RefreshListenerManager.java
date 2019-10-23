package seedu.address.ui;

import seedu.address.logic.Logic;

/**
 * Refresh the Ui whenever new data is being imported to the Scheduler.
 */
public class RefreshListenerManager implements RefreshListener {

    private ScheduleViewPanel scheduleViewPanel;

    @Override
    public void dataImported(MainWindow window, Logic logic) {
        this.scheduleViewPanel = window.getScheduleViewPanel();
        this.scheduleViewPanel.fillPanel(logic.getTitlesLists(), logic.getObservableLists());
    }
}
