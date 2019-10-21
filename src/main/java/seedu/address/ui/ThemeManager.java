package seedu.address.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.collections.ObservableList;
import javafx.scene.Scene;

public class ThemeManager {

    private static final String SEPARATOR = System.getProperty("os.name").startsWith("Windows") ? "\\" : "/";
    private static final String DEFAULT_THEME_NAME = "default";

    private Scene scene;

    private String themeName = DEFAULT_THEME_NAME;
    private String fontColour = "yellow";
    private String backgroundColour;

    public ThemeManager(Scene scene) {
        this.scene = scene;
    }

    /**
     * Returns the list of stylesheets stored in the scene attribute of this object.
     * @return List of stylesheets stored in the scene attribute of this object.
     */
    public ObservableList<String> getStylesheets() {
        return scene.getStylesheets();
    }

    public File getStyleSheet() {
        File theme = new File(System.getProperty("user.dir") + SEPARATOR + "MyTheme.css");
        setTheme(theme);
        return theme;
    }

    public void setTheme(File theme) {
        String themePath = theme.toURI().toString();
        getStylesheets().set(0, themePath);
        this.fontColour = fontColour;
    }

    public void setFontColour(String fontColour) {
        File outputCss = getStyleSheet();
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
        setTheme(outputCss);
    }
}
