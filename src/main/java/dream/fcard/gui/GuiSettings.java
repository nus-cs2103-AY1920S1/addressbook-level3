//@@author nattanyz
package dream.fcard.gui;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * Class containing information pertaining to sizing, colours and font styles of the application.
 * Unless specified otherwise, the default sizing, colours and font styles are used.
 * Default sizing, colours and font styles can be overwritten using the setter methods provided.
 * Getters are used when generating UI components that match the prescribed styles.
 */
public class GuiSettings {

    // the one and only instance of GuiSettings allowed
    private static GuiSettings guiSettings = new GuiSettings();

    // default sizing
    private static double padding = 10;
    private static double spacing = 10;
    private static double minHeight = 400;
    private static double minWidth = 400;

    // default colours
    private static String primaryTextColour = "#333333";
    private static String primaryUIColour = "#ABDFF6";
    private static String secondaryUIColour = "#F0ECEB";
    private static String tertiaryUIColour = "#6C7476";

    // default font styles
    private static Font titleTextStyle = Font.font("Montserrat", FontWeight.BOLD,
        FontPosture.ITALIC, 36);
    private static Font flashCardTextStyle = Font.font("Montserrat", FontWeight.NORMAL,
        FontPosture.REGULAR, 18);
    private static Font commandTextStyle = Font.font("Montserrat", FontWeight.LIGHT,
        FontPosture.REGULAR, 16);
    private static Font subtitleTextStyle = Font.font("Montserrat", FontWeight.LIGHT,
        FontPosture.REGULAR, 18);
    private static Font deckTitleTextStyle = Font.font("Montserrat", FontWeight.BOLD,
        FontPosture.REGULAR, 16);

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

    /** Get the default global padding of the application. */
    public static double getPadding() {
        return padding;
    }

    /** Set the default global padding of the application. */
    public static void setPadding(double padding) {
        GuiSettings.padding = padding;
    }

    /** Get the default global spacing of the application. */
    public static double getSpacing() {
        return spacing;
    }

    /** Set the default global spacing of the application. */
    public static void setSpacing(double spacing) {
        GuiSettings.spacing = spacing;
    }

    /** Get the default minimum height of the application window. */
    public static double getMinHeight() {
        return minHeight;
    }

    /** Set the default minimum height of the application window. */
    public static void setMinHeight(double minHeight) {
        GuiSettings.minHeight = minHeight;
    }

    /** Get the default minimum width of the application window. */
    public static double getMinWidth() {
        return minWidth;
    }

    /** Set the default minimum width of the application window. */
    public static void setMinWidth(double minWidth) {
        GuiSettings.minWidth = minWidth;
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

    /** Get the title text style of the application. */
    public static Font getTitleTextStyle() {
        return titleTextStyle;
    }

    /** Set the title text style of the application. */
    public static void setTitleText(Font titleTextStyle) {
        GuiSettings.titleTextStyle = titleTextStyle;
    }

    /** Get the flashcard text style of the application. */
    public static Font getFlashCardTextStyle() {
        return flashCardTextStyle;
    }

    /** Set the flashcard text style of the application. */
    public static void setFlashCardTextStyle(Font flashCardTextStyle) {
        GuiSettings.flashCardTextStyle = flashCardTextStyle;
    }

    /** Get the command text style of the application. */
    public static Font getCommandTextStyle() {
        return commandTextStyle;
    }

    /** Set the command text style of the application. */
    public static void setCommandTextStyle(Font commandTextStyle) {
        GuiSettings.commandTextStyle = commandTextStyle;
    }

    /** Get the subtitle text style of the application. */
    public static Font getSubtitleTextStyle() {
        return subtitleTextStyle;
    }

    /** Set the subtitle text style of the application. */
    public static void setSubtitleTextStyle(Font subtitleTextStyle) {
        GuiSettings.subtitleTextStyle = subtitleTextStyle;
    }

    /** Get the deck title text style of the application. */
    public static Font getDeckTitleTextStyle() {
        return deckTitleTextStyle;
    }

    /** Set the deck title text style of the application. */
    public static void setDeckTitleTextStyle(Font deckTitleTextStyle) {
        GuiSettings.deckTitleTextStyle = deckTitleTextStyle;
    }
}
