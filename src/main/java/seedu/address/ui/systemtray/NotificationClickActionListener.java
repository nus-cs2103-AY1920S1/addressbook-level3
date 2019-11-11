package seedu.address.ui.systemtray;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * The Listener responding to a Notification being clicked.
 */
public class NotificationClickActionListener implements ActionListener {
    private static final Logger logger = LogsCenter.getLogger(SystemTrayCommunicator.class);

    /**
     * This Listener's reaction to an ActionEvent it is listening to being called.
     *     Specifically for this class, this is called whenever a notification is clicked on.
     *
     * @param e The ActionEvent that this Listener is listening to.
     */
    public void actionPerformed(ActionEvent e) {
        // This is currently undefined behaviour for the current milestone.
        // This comment should be removed if ever this class' behaviour is defined.
    }
}
