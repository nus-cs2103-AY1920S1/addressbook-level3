//@@author CarbonGrid
package seedu.address.commons.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum for OmniPanel's Tab.
 */
public enum OmniPanelTab {

    PATIENTS_TAB("patientsTab", 0),
    APPOINTMENTS_TAB("appointmentsTab", 1),
    DOCTORS_TAB("doctorsTab", 2),
    DUTY_SHIFT_TAB("dutyShiftsTab", 3);

    private static final Map<String, OmniPanelTab> BY_ID = new HashMap<>();
    private static final Map<Integer, OmniPanelTab> BY_INDEX = new HashMap<>();

    private final String id;
    private final int tabBarIndex;

    static {
        for (OmniPanelTab omniPanelTab: values()) {
            BY_ID.put(omniPanelTab.id, omniPanelTab);
            BY_INDEX.put(omniPanelTab.tabBarIndex, omniPanelTab);
        }
    }

    OmniPanelTab(String id, int tabBarIndex) {
        this.id = id;
        this.tabBarIndex = tabBarIndex;
    }

    public static OmniPanelTab tabOfId(String id) {
        return BY_ID.get(id);
    }

    public static OmniPanelTab tabOfIndex(int index) {
        return BY_INDEX.get(index);
    }

    public int getTabBarIndex() {
        return tabBarIndex;
    }

    public String getId() {
        return id;
    }
}
