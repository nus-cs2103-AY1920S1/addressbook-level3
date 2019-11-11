package seedu.address.model.display.sidepanel;

import java.util.ArrayList;

/**
 * Side Panel display model.
 */
public class SidePanelDisplay {

    private ArrayList<PersonDisplay> personDisplays;
    private ArrayList<GroupDisplay> groupDisplays;
    private SidePanelDisplayType sidePanelDisplayType;

    public SidePanelDisplay(ArrayList<PersonDisplay> personDisplays, ArrayList<GroupDisplay> groupDisplays,
                            SidePanelDisplayType sidePanelDisplayType) {
        this.personDisplays = personDisplays;
        this.groupDisplays = groupDisplays;
        this.sidePanelDisplayType = sidePanelDisplayType;
    }

    public ArrayList<PersonDisplay> getPersonDisplay() {
        return personDisplays;
    }

    public ArrayList<GroupDisplay> getGroupDisplay() {
        return groupDisplays;
    }

    public SidePanelDisplayType getSidePanelDisplayType() {
        return sidePanelDisplayType;
    }
}
