package seedu.address.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.model.aesthetics.Background;

/**
 * Helper class that helps in managing the styles used for the GUI of the program.
 */
public class StyleManager {

    private static final String SEPARATOR = System.getProperty("os.name").startsWith("Windows") ? "\\" : "/";

    private Scene scene;
    private VBox mainWindowPlaceholder;
    private StackPane mainDisplayPanePlaceholder;
    private File myStyleSheet;
    private Background background;

    public StyleManager(Scene scene, VBox mainWindowPlaceholder) {
        this.scene = scene;
        this.mainWindowPlaceholder = mainWindowPlaceholder;
    }

    /**
     * Returns the list of stylesheets stored in the scene attribute of this object.
     * @return List of stylesheets stored in the scene attribute of this object.
     */
    public ObservableList<String> getSceneStylesheets() {
        return scene.getStylesheets();
    }

    /**
     * Creates a new stylesheet with input file name.
     * @param name Name of stylesheet to be created, including extensions.
     * @return File to contain data of new stylesheet.
     */
    public File createNewStyleSheet(String name) {
        String directoryName = System.getProperty("user.dir") + SEPARATOR
                + "stylesheets";
        File directory = new File(directoryName);
        if (!directory.exists()) {
            directory.mkdir();
        }

        File styleSheet = new File(directoryName + SEPARATOR + name);

        if (!styleSheet.exists()) {
            try {
                styleSheet.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return styleSheet;
    }

    /**
     * Returns the new styleSheet to be used by the program for rendering nodes of this
     * style manager's scene.
     * @return New styleSheet to be used by the program for rendering.
     */
    public File getMyStyleSheet() {
        if (myStyleSheet == null) {
            myStyleSheet = createNewStyleSheet("MyStyleSheet.css");
        }
        return myStyleSheet;
    }

    /**
     * Sets the given styleSheet to be the active styleSheet used by the program for rendering.
     * @param styleSheet Css file to bew used as the active styleSheet.
     */
    public void setStyleSheet(File styleSheet) {
        String themePath = styleSheet.toURI().toString();
        getSceneStylesheets().set(0, themePath);
    }

    /**
     * Returns the output String after replacing the values of the specified fields in a stylesheet.
     * @param lineReadFromReader Line of string in which content may have to be possibly replace.
     * @param field Field of a stylesheet with values to be replaced.
     * @param replacement Replacement string for the field which requires values to be replaced.
     * @return Output String after replacing the values of the specified fields in a stylesheet.
     */
    private String getLineAfterReplacement(String lineReadFromReader, String field, String replacement) {
        String outputLine = lineReadFromReader;
        int textFillIndex = outputLine.indexOf("-" + field + ": ");
        if (textFillIndex != -1) {
            String subText = lineReadFromReader.substring(textFillIndex, lineReadFromReader.length());
            int semiColonIndex = subText.indexOf(":");
            int exclamationIndex = subText.indexOf("!");
            String toReplace;
            if (exclamationIndex == -1) {
                int colonIndex = subText.indexOf(";");
                toReplace = lineReadFromReader.substring(textFillIndex + semiColonIndex + 1, textFillIndex
                        + colonIndex);
                lineReadFromReader = lineReadFromReader.replace(toReplace, " " + replacement);
            } else {
                toReplace = lineReadFromReader.substring(textFillIndex + semiColonIndex + 1,
                        textFillIndex + exclamationIndex);
                lineReadFromReader = lineReadFromReader.replace(toReplace, " " + replacement
                        + " ");
            }
        }
        return lineReadFromReader;
    }

    /**
     * Sets the font colour of this style manager's scene.
     * @param fontColour String representation of a CSS font colour.
     */
    public void setFontColour(String fontColour) {
        try {
            InputStream is;
            File outputCss;
            if (myStyleSheet != null && myStyleSheet.exists()) {
                String fileName = myStyleSheet.getName();
                String nameWithoutCssExtension = fileName.substring(0, fileName.length() - 4);
                File copy = createNewStyleSheet(nameWithoutCssExtension + " copy.css");
                is = new FileInputStream(getMyStyleSheet());
                outputCss = copy;
            } else {
                is = this.getClass().getResourceAsStream("/view/DarkTheme.css");
                outputCss = getMyStyleSheet();
            }

            String lineReadFromReader;
            String lineToWriteViaWriter = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((lineReadFromReader = br.readLine()) != null) {
                String changedTextFill = getLineAfterReplacement(lineReadFromReader, "fx-text-fill",
                        fontColour);
                String changedFill = getLineAfterReplacement(changedTextFill,
                        "fx-fill",
                        fontColour);
                lineToWriteViaWriter += changedFill + "\n";
            }
            FileWriter fw = new FileWriter(outputCss);
            fw.write(lineToWriteViaWriter);
            fw.close();
            outputCss.renameTo(getMyStyleSheet());
            setStyleSheet(getMyStyleSheet());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the font family of this style manager's scene.
     * @param fontFamily String representation of a CSS font family.
     */
    public void setFontFamily(String fontFamily) {
        try {
            InputStream is;
            File outputCss;
            if (myStyleSheet != null && myStyleSheet.exists()) {
                String fileName = myStyleSheet.getName();
                String nameWithoutCssExtension = fileName.substring(0, fileName.length() - 4);
                File copy = createNewStyleSheet(nameWithoutCssExtension + " copy.css");
                is = new FileInputStream(getMyStyleSheet());
                outputCss = copy;
            } else {
                is = this.getClass().getResourceAsStream("/view/DarkTheme.css");
                outputCss = getMyStyleSheet();
            }

            String lineReadFromReader;
            String lineToWriteViaWriter = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((lineReadFromReader = br.readLine()) != null) {
                lineToWriteViaWriter += getLineAfterReplacement(lineReadFromReader, "fx-font-family",
                        fontFamily) + "\n";
            }
            FileWriter fw = new FileWriter(outputCss);
            fw.write(lineToWriteViaWriter);
            fw.close();
            outputCss.renameTo(getMyStyleSheet());
            setStyleSheet(getMyStyleSheet());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Consumes the next whole field from this current onwards.
     * @param br Bufferedreader that reads lines.
     * @param initialLine Initial line of text to determine whether or not to initiate reading.
     * @throws IOException If reading of lines of text is unsuccessful.
     */
    private void ignoreUntilNextField(BufferedReader br, String initialLine) throws IOException {
        boolean foundOpeningCurlyBraces = initialLine.contains("{");
        boolean foundClosingCurlyBraces = initialLine.contains("}");

        String lineConsumed;

        while (!foundOpeningCurlyBraces && (lineConsumed = br.readLine()) != null) {
            if (lineConsumed.contains("{")) {
                foundOpeningCurlyBraces = true;
            }
        }

        while (!foundClosingCurlyBraces && (lineConsumed = br.readLine()) != null) {
            if (lineConsumed.contains("}")) {
                foundClosingCurlyBraces = true;
            }
        }
    }

    /**
     * Sets the background image of main window placeholder of this instance of style manager.
     * @param background Background object representing background information of this application.
     */
    public void setBackgroundImage(Background background) {
        String filePath = background.getBackgroundPicPath();
        String bgSize = background.getBgSize();
        String bgRepeat = background.getBgRepeat();

        File file = new File(filePath);

        if (this.background == null || !this.background.equals(background)) {
            mainWindowPlaceholder.setStyle("-fx-background-image: url('" + file.toURI().toString() + "'); "
                    + "-fx-background-position: center center; "
                    + "-fx-background-repeat: " + bgRepeat + ";"
                    + "-fx-background-size: " + bgSize + ";");
            this.background = background;
        }
    }

    /**
     * Sets the background of this style manager's scene.
     * @param background String representation of a CSS background.
     */
    public void setBackground(Background background) {

        List<String> fieldsToIgnore = new ArrayList<>();
        //        fieldsToIgnore.add(".profile-scroll");
        //        fieldsToIgnore.add(".scroll-bar");

        try {
            InputStream is;
            File outputCss;
            if (myStyleSheet != null && myStyleSheet.exists()) {
                String fileName = myStyleSheet.getName();
                String nameWithoutCssExtension = fileName.substring(0, fileName.length() - 4);
                File copy = createNewStyleSheet(nameWithoutCssExtension + " copy.css");
                is = new FileInputStream(getMyStyleSheet());
                outputCss = copy;
            } else {
                is = this.getClass().getResourceAsStream("/view/DarkTheme.css");
                outputCss = getMyStyleSheet();
            }

            String lineReadFromReader;
            String lineToWriteViaWriter = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            //            String backgroundColourForImages = "rgba(255, 255, 255, .2);"
            String backgroundColourForImages = "transparent";
            String backgroundColour = background.isBackgroundColour()
                    ? background.toString()
                    : backgroundColourForImages;
            while ((lineReadFromReader = br.readLine()) != null) {
                for (String fieldToIgnore : fieldsToIgnore) {
                    if (lineReadFromReader.startsWith(fieldToIgnore)) {
                        ignoreUntilNextField(br, lineReadFromReader);
                    }
                }
                String changedBackgroundFields = getLineAfterReplacement(lineReadFromReader, "fx-background",
                        backgroundColour);
                String changedBackgroundColourFields = getLineAfterReplacement(changedBackgroundFields,
                        "fx-background-color",
                        backgroundColour);
                lineToWriteViaWriter += changedBackgroundColourFields + "\n";
            }

            if (!background.isBackgroundColour()) {
                setBackgroundImage(background);
            }

            FileWriter fw = new FileWriter(outputCss);
            fw.write(lineToWriteViaWriter);
            fw.close();
            outputCss.renameTo(getMyStyleSheet());
            setStyleSheet(getMyStyleSheet());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
