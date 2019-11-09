package seedu.address.ui;

import seedu.address.commons.core.OmniPanelTab;

/**
 * Managing Interface of OmniPanel.
 */
public interface OmniPanelManager {

    void setOmniPanelTab(OmniPanelTab omniPanelTab);

    void regainOmniPanelSelector();
}
