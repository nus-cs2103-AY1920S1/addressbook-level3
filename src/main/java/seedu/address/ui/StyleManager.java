package seedu.address.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.collections.ObservableList;
import javafx.scene.Scene;

/**
 * Helper class that helps in managing the styles used for the GUI of the program.
 */
public class StyleManager {

    private static final String SEPARATOR = System.getProperty("os.name").startsWith("Windows") ? "\\" : "/";
    private static final String DEFAULT_THEME_NAME = "default";

    private Scene scene;

    private String themeName = DEFAULT_THEME_NAME;
    private String fontColour = "yellow";
    private String backgroundColour;

    public StyleManager(Scene scene) {
        this.scene = scene;
    }

    /**
     * Returns the list of stylesheets stored in the scene attribute of this object.
     * @return List of stylesheets stored in the scene attribute of this object.
     */
    public ObservableList<String> getStylesheets() {
        return scene.getStylesheets();
    }

    /**
     * Returns the new styleSheet to be used by the program for rendering nodes of this
     * style manager's scene.
     * @return New styleSheet to be used by the program for rendering.
     */
    public File getNewStyleSheet() {
        File styleSheet = new File(System.getProperty("user.dir") + SEPARATOR
                + "stylesheets" + SEPARATOR + "MyTheme.css");
        setStyleSheet(styleSheet);
        return styleSheet;
    }

    /**
     * Sets the given styleSheet to be the active styleSheet used by the program for rendering.
     * @param styleSheet Css file to bew used as the active styleSheet.
     */
    public void setStyleSheet(File styleSheet) {
        String themePath = styleSheet.toURI().toString();
        getStylesheets().set(0, themePath);
        this.fontColour = fontColour;
    }

    /**
     * Sets the font colour of this style manager's scene.
     * @param fontColour String representation of a CSS font colour.
     */
    public void setFontColour(String fontColour) {
        File outputCss = getNewStyleSheet();
        String replaceWith = fontColour;

        if (!outputCss.exists()) {
            try {
                outputCss.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            InputStream is = this.getClass().getResourceAsStream("/view/DarkTheme.css");
            String lineReadFromReader;
            String lineToWriteViaWriter = "";
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                while ((lineReadFromReader = br.readLine()) != null) {
                    int textFillIndex = lineReadFromReader.indexOf("-fx-text-fill: ");
                    if (textFillIndex != -1) {
                        String subText = lineReadFromReader.substring(textFillIndex, lineReadFromReader.length());
                        int semiColonIndex = subText.indexOf(":");
                        int exclamationIndex = subText.indexOf("!");
                        String toReplace;
                        if (exclamationIndex == -1) {
                            int colonIndex = subText.indexOf(";");
                            toReplace = lineReadFromReader.substring(textFillIndex + semiColonIndex + 1, textFillIndex
                                    + colonIndex);
                            lineReadFromReader = lineReadFromReader.replace(toReplace, " " + replaceWith);
                        } else {
                            toReplace = lineReadFromReader.substring(textFillIndex + semiColonIndex + 1,
                                    textFillIndex + exclamationIndex);
                            lineReadFromReader = lineReadFromReader.replace(toReplace, " " + replaceWith
                                    + " ");
                        }
                    }
                    lineToWriteViaWriter += lineReadFromReader + "\n";
                }
                FileWriter fw = new FileWriter(outputCss);
                fw.write(lineToWriteViaWriter);
                fw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setStyleSheet(outputCss);
    }
}
