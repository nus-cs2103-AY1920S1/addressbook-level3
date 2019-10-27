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

    // application icon
    private static final String APPLICATION_ICON = "/images/address_book_32.png";

    // default sizing
    private static double padding = 20; // padding around elements
    private static double spacing = 10; // spacing between elements laid sequentially
    private static double minHeight = 400;
    private static double minWidth = 400;
    private static double radius = 5;

    // default colours
    private static String primaryTextColour = "#333333";
    private static String errorTextColour = "#F74D68"; // pinkish red accent colour used for errors
    private static String primaryUiColour = "#ABDFF6"; // blue accent colour used for flashcards
    private static String secondaryUiColour = "#F0ECEB"; // light grey used for title bar
    private static String tertiaryUiColour = "#6C7476"; // dark grey used for command box bg
    private static String backgroundColour = "#FFFFFF"; // white used for app background

    // default font styles
    // todo: embed fonts in JAR file
    private static Font titleTextStyle = Font.font("Montserrat", FontWeight.BOLD,
        FontPosture.ITALIC, 36);
    private static Font flashCardTextStyle = Font.font("Montserrat", FontWeight.NORMAL,
        FontPosture.REGULAR, 18);
    private static Font commandTextStyle = Font.font("Montserrat", FontWeight.LIGHT,
        FontPosture.REGULAR, 16);
    private static Font statusTextStyle = Font.font("Montserrat", FontWeight.LIGHT,
        FontPosture.REGULAR, 12);
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

    /** Return the path to the application icon. */
    public static String getApplicationIcon() {
        return APPLICATION_ICON;
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

    /** Get the default background radius for UI components in the application. */
    public static double getRadius() {
        return radius;
    }

    /** Set the default background radius for UI components in the application. */
    public static void setRadius(double radius) {
        GuiSettings.radius = radius;
    }

    /** Get the primary text colour of the application. */
    public static String getPrimaryTextColour() {
        return primaryTextColour;
    }

    /** Set the primary text colour of the application. */
    public static void setPrimaryTextColour(String primaryTextColour) {
        GuiSettings.primaryTextColour = primaryTextColour;
    }

    /** Get the error text colour of the application. */
    public static String getErrorTextColour() {
        return errorTextColour;
    }

    /** Set the error text colour of the application. */
    public static void setErrorTextColour(String errorTextColour) {
        GuiSettings.errorTextColour = errorTextColour;
    }

    /** Get the primary UI colour of the application. */
    public static String getPrimaryUiColour() {
        return primaryUiColour;
    }

    /** Set the primary UI colour of the application. */
    public static void setPrimaryUiColour(String primaryUiColour) {
        GuiSettings.primaryUiColour = primaryUiColour;
    }

    /** Get the secondary UI colour of the application. */
    public static String getSecondaryUiColour() {
        return secondaryUiColour;
    }

    /** Set the secondary UI colour of the application. */
    public static void setSecondaryUiColour(String secondaryUiColour) {
        GuiSettings.secondaryUiColour = secondaryUiColour;
    }

    /** Get the tertiary UI colour of the application. */
    public static String getTertiaryUiColour() {
        return tertiaryUiColour;
    }

    /** Set the tertiary UI colour of the application. */
    public static void setTertiaryUiColour(String tertiaryUiColour) {
        GuiSettings.tertiaryUiColour = tertiaryUiColour;
    }

    /** Get the background colour of the application. */
    public static String getBackgroundColour() {
        return backgroundColour;
    }

    /** Set the background colour of the application. */
    public static void setBackgroundColour(String backgroundColour) {
        GuiSettings.backgroundColour = backgroundColour;
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

    /** Get the status text style of the application. */
    public static Font getStatusTextStyle() {
        return statusTextStyle;
    }

    /** Set the status text style of the application. */
    public static void setStatusTextStyle(Font statusTextStyle) {
        GuiSettings.statusTextStyle = statusTextStyle;
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
