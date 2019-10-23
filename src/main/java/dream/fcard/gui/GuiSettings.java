//@@author nattanyz
package dream.fcard.gui;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * Class containing information pertaining to colours and font styles of the application.
 * Unless specified otherwise, the default colours and font styles are used.
 * Default colours and font styles can be overwritten using the setter methods provided.
 * Getters are used when generating UI components that match the prescribed styles.
 */
public class GuiSettings {

    // the one and only instance
    private static GuiSettings guiSettings = new GuiSettings();

    // default colours
    private static String primaryTextColour = "#333333";
    private static String primaryUIColour = "#ABDFF6";
    private static String secondaryUIColour = "#F0ECEB";
    private static String tertiaryUIColour = "#6C7476";

    // default font styles
    private static Font titleTextStyle = Font.font("Montserrat", FontWeight.BOLD, FontPosture.ITALIC, 36);

    // private constructor
    private GuiSettings() {
        // empty constructor body
    }

    /** Accessor for the GuiSettings instance */
    public static GuiSettings getInstance() {
        if (guiSettings == null) {
            guiSettings = new GuiSettings();
        }
        return guiSettings;
    }

    /** Get the primary text colour of the application. */
    public static String getPrimaryTextColour() {
        return primaryTextColour;
    }

    /** Set the primary text colour of the application. */
    public static void setPrimaryTextColour(String primaryTextColour) {
        GuiSettings.primaryTextColour = primaryTextColour;
    }

    /** Get the primary UI colour of the application. */
    public static String getPrimaryUIColour() {
        return primaryUIColour;
    }

    /** Set the primary UI colour of the application. */
    public static void setPrimaryUIColour(String primaryUIColour) {
        GuiSettings.primaryUIColour = primaryUIColour;
    }

    /** Get the secondary UI colour of the application. */
    public static String getSecondaryUIColour() {
        return secondaryUIColour;
    }

    /** Set the secondary UI colour of the application. */
    public static void setSecondaryUIColour(String secondaryUIColour) {
        GuiSettings.secondaryUIColour = secondaryUIColour;
    }

    /** Get the tertiary UI colour of the application. */
    public static String getTertiaryUIColour() {
        return tertiaryUIColour;
    }

    /** Set the tertiary UI colour of the application. */
    public static void setTertiaryUIColour(String tertiaryUIColour) {
        GuiSettings.tertiaryUIColour = tertiaryUIColour;
    }

    /** Get the title bar text style of the application. */
    public static Font getTitleTextStyle() {
        return titleTextStyle;
    }

    /** Set the title bar text style of the application. */
    public static void setTitleText(Font titleTextStyle) {
        GuiSettings.titleTextStyle = titleTextStyle;
    }
}
