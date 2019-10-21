package seedu.address.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import javafx.collections.ObservableList;
import javafx.scene.Scene;

public class ThemeManager {

    private static final String SEPARATOR = System.getProperty("os.name").startsWith("Windows") ? "\\" : "/";
    private static final String DEFAULT_THEME_NAME = "default";

    private Scene scene;

    private String themeName = DEFAULT_THEME_NAME;
    private String fontColour = "yellow";
    private String backgroundColour;

    private HashMap<String, String> themeNameToStyleSheetPathMap;

    public ThemeManager(Scene scene) {
        this.scene = scene;
//        themeNameToStyleSheetPathMap = new HashMap<>();
//        themeNameToStyleSheetPathMap.put(DEFAULT_THEME_NAME, getActiveStyleSheetPath());
    }

    /**
     * Returns the list of stylesheets stored in the scene attribute of this object.
     * @return List of stylesheets stored in the scene attribute of this object.
     */
    public ObservableList<String> getStylesheets() {
        return scene.getStylesheets();
    }
//
//    /**
//     * Returns the styleSheet that is currently displayed to the user.
//     * @returnt Current styleSheet displayed to the user.
//     */
//    public String getActiveStyleSheetPath() {
//        return getStylesheets().get(0);
//    }
//
//    /**
//     * Returns the String representation of the path of the dark theme.
//     * @return String representation of the path of the dark theme.
//     */
//    public Path getDarkThemePath() {
//        String darkThemeUri = getActiveStyleSheetPath();
//        try {
//            return Paths.get(getClass().getResource("/view/DarkTheme.css").toURI());
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * Returns a new theme path name, given a specified name.
//     * If name is null, the current tentative theme will be replaced by the new theme to be set.
//     * @return New path name of theme with given specified theme name.
//     */
//    public String getNewThemePathName(String themeName) {
//        this.themeName = themeName;
//        return getDarkThemePath().getParent().toString() + SEPARATOR + themeName + ".css";
//    }
//
//    /**
//     * Calls the other constructor to return a new theme path name, with "MyTheme" as the file name.
//     * A new theme is not saved to the map of themes, but the existing theme that is not the default theme gets
//     * replaced.
//     * @return New path name of theme with standard specified theme name.
//     */
//    public String getNewThemePathName() {
//        themeName = "MyTheme";
//        return getNewThemePathName("MyTheme");
//    }
//
    public void createNewStyleSheet() {
//        String themePathName = getNewThemePathName();
        File theme = new File(System.getProperty("user.dir") + SEPARATOR + "MyTheme.css");
        changeFontColour(theme);
        setTheme(theme);
    }
//
    public void setTheme(File theme) {
        String themePath = theme.toURI().toString();
        getStylesheets().set(0, themePath);
    }

    public void changeFontColour(File outputCss) {
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
//            String darkTheme = new String(is.readAllBytes(), StandardCharsets.UTF_8);

            String s;
            String totalStr = "";
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                while ((s = br.readLine()) != null) {
                    int textFillIndex = s.indexOf("-fx-text-fill: ");
                    if (textFillIndex != -1) {
                        String subText = s.substring(textFillIndex, s.length());
                        int semiColonIndex = subText.indexOf(":");
                        int exclamationIndex = subText.indexOf("!");
                        String toReplace;
                        if (exclamationIndex == -1) {
                            int colonIndex = subText.indexOf(";");
                            toReplace = s.substring(textFillIndex + semiColonIndex + 1, textFillIndex + colonIndex);
                            s = s.replace(toReplace, " " + replaceWith);
                        } else {
                            toReplace = s.substring(textFillIndex + semiColonIndex + 1, textFillIndex + exclamationIndex);
                            s = s.replace(toReplace, " " + replaceWith + " ");
                        }
                    }
                    totalStr += s + "\n";
                }
                FileWriter fw = new FileWriter(outputCss);
                fw.write(totalStr);
                fw.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setFontColour(String colour) {
        this.fontColour = colour;
    }

    public HashMap<String, String> getThemeNameToStyleSheetPathMap() {
        return themeNameToStyleSheetPathMap;
    }
}
