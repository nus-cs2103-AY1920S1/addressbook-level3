package seedu.address.ui.systemtray;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;

import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;

/**
 * Controls interactions between the main program and the System Tray.
 */
public class SystemTrayCommunicator {
    private static final Logger logger = LogsCenter.getLogger(SystemTrayCommunicator.class);
    private static final String TRAY_ICON_NAME = "Horo";
    private static final String TRAY_ICON_IMAGE_PATH = "/images/system_tray_icon.png";
    private static final String NOTIFICATIONS_ON_TOOLTIP = "Notifications switched on";
    private static final String NOTIFICATIONS_OFF_TOOLTIP = "Notifications switched off";

    private static TrayIcon trayIcon;
    private static boolean systemTrayIsSupported;

    private boolean notificationsSwitchedOn = true;

    public SystemTrayCommunicator() {
        this.initialise();
    }

    /**
     * Sets up the System Tray app, if applicable.
     */
    private void initialise() {
        systemTrayIsSupported = SystemTray.isSupported();

        if (systemTrayIsSupported) {
            logger.info("System tray is supported.");

            SystemTray tray = SystemTray.getSystemTray();
            trayIcon = getTrayIcon();
            resetToolTipMessage();

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                logger.warning(e.toString());
            }
        } else {
            logger.warning("System tray is not supported. Popup notifications will not be posted.");
        }
    }

    /**
     * Posts a new notification to the System Tray.
     *
     * @param name The name of the notification.
     * @param description The description of the notification.
     */
    public void postNewNotification(String name, String description) {
        if (systemTrayIsSupported) {
            trayIcon.displayMessage(name, description, MessageType.INFO);
        }
    }

    /**
     * Tells the System Tray Communicator that notifications are switched off.
     */
    public void switchOnNotifications() {
        // This is purely a cosmetic change
        notificationsSwitchedOn = true;
        resetToolTipMessage();
    }

    /**
     * Tells the SystemTrayCommunicator that notifications are switched off.
     */
    public void switchOffNotifications() {
        // This is purely a cosmetic change
        notificationsSwitchedOn = false;
        resetToolTipMessage();
    }

    /**
     * Changes the tooltip message based on whether notifications are switched on or off.
     */
    private void resetToolTipMessage() {
        if (notificationsSwitchedOn) {
            trayIcon.setToolTip(NOTIFICATIONS_ON_TOOLTIP);
        } else {
            trayIcon.setToolTip(NOTIFICATIONS_OFF_TOOLTIP);
        }
    }

    /**
     * Creates a new icon on the System Tray, and returns its reference.
     *
     * @return The reference to a new System Tray icon.
     */
    private static TrayIcon getTrayIcon() {
        TrayIcon trayIcon = new TrayIcon(getImage(), TRAY_ICON_NAME, getPopupMenu());
        trayIcon.addActionListener(getNotificationClickActionListener());

        return trayIcon;
    }

    /**
     * Locates and returns the image to be used as the Tray Icon.
     *
     * @return the image to be used as the Tray Icon.
     */
    private static Image getImage() {
        return Toolkit.getDefaultToolkit().getImage(MainApp.class.getResource(TRAY_ICON_IMAGE_PATH));
    }

    /**
     * Creates and returns the PopupMenu designed for this app.
     *
     * @return The PopupMenu designed for the System Tray app.
     */
    private static PopupMenu getPopupMenu() {
        PopupMenu popup = new PopupMenu();

        // Create pop-up menu items
        MenuItem aboutItem = new MenuItem("Hi this is Horo.");

        // Add created items to the pop-up menu
        popup.add(aboutItem);

        return popup;
    }

    /**
     * Creates and returns an instance of the ClickActionListener used for this app.
     *     The listener will be called whenever the user clicks on a notification.
     *
     * @return An instance of the ClickActionListener used for this app.
     */
    private static NotificationClickActionListener getNotificationClickActionListener() {
        return new NotificationClickActionListener();
    }
}
