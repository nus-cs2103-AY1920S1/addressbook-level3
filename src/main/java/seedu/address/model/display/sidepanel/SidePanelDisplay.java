package seedu.address.model.display.sidepanel;

import java.util.ArrayList;

public class SidePanelDisplay {

    private ArrayList<Display> display;
    private SidePanelDisplayType sidePanelDisplayType;

    public SidePanelDisplay(ArrayList<Display> display, SidePanelDisplayType sidePanelDisplayType) {
        this.display = display;
        this.sidePanelDisplayType = sidePanelDisplayType;
    }

    public SidePanelDisplay() {
        this.display = null;
        this.sidePanelDisplayType = SidePanelDisplayType.EMPTY;
    }

    public ArrayList<Display> getDisplay() {
        return display;
    }

    public SidePanelDisplayType getSidePanelDisplayType() {
        return sidePanelDisplayType;
    }
}
