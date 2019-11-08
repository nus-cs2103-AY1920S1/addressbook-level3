package seedu.sugarmummy.ui.aesthetics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import seedu.sugarmummy.model.aesthetics.Background;

/**
 * Helper class that helps in managing the styles used for the GUI of the program.
 */
public class StyleManager {

    private static final String SEPARATOR = System.getProperty("os.name").startsWith("Windows") ? "\\" : "/";
    private static final String MY_STYLE_SHEET_NAME = "MyStyleSheet.css";
    private static final String STYLESHEET_DIRECTORY_NAME = "temp";
    private static final String STYLESHEET_DIRECTORY_PATH_NAME = System.getProperty("user.dir") + SEPARATOR
            + STYLESHEET_DIRECTORY_NAME;

    private Scene scene;
    private VBox mainWindowPlaceholder;
    private File myStyleSheet;
    private Background background;

    private List<String> styleSheetUriPaths;

    // Variables used for reading and writing css files
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;
    private FileWriter fileWriter;
    private File tempOutputCss;
    private String lineReadFromReader;
    private String linesToWriteViaWriter;
    private List<String> fieldsToIgnore = new ArrayList<>(List.of("#lineChart", ".commonScrollPane .",
            ".lineChartScrollPaneSecondVersion .", ".card_big_label", ".card_small_label"));

    public StyleManager(Scene scene, VBox mainWindowPlaceholder) {
        this.scene = scene;
        this.mainWindowPlaceholder = mainWindowPlaceholder;
        this.styleSheetUriPaths = new ArrayList<>();
        addToStyleSheetUriPaths(getSceneStylesheets().get(0));
    }

    /**
     * Resets the current styleSheets used and deletes the styleSheet folder
     */
    public void resetStyleSheets() {
        getSceneStylesheets().set(0, styleSheetUriPaths.get(0));
        File styleSheetDirectory = new File(STYLESHEET_DIRECTORY_PATH_NAME);
        if (styleSheetDirectory.exists()) {
            Arrays.asList(Objects.requireNonNull(styleSheetDirectory.listFiles())).forEach(File::delete);
            styleSheetDirectory.delete();
        }
    }

    /**
     * Returns the list of stylesheets stored in the scene attribute of this object.
     *
     * @return List of stylesheets stored in the scene attribute of this object.
     */
    private ObservableList<String> getSceneStylesheets() {
        return scene.getStylesheets();
    }

    /**
     * Create a directory structure for stylesheets, if it does not already exist.
     */
    private void createFolders() {
        File directory = new File(STYLESHEET_DIRECTORY_PATH_NAME);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    /**
     * Creates a new stylesheet with input file name.
     *
     * @param name Name of stylesheet to be created, including extensions.
     * @return File to contain data of new stylesheet.
     */
    private File createNewStyleSheet(String name) {
        createFolders();
        File styleSheet = new File(STYLESHEET_DIRECTORY_PATH_NAME + SEPARATOR + name);

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
     * Returns the new styleSheet to be used by the program for rendering nodes of this style manager's scene.
     *
     * @return New styleSheet to be used by the program for rendering.
     */
    private File getMyStyleSheet() {
        if (myStyleSheet == null) {
            myStyleSheet = createNewStyleSheet(MY_STYLE_SHEET_NAME);
        }
        return myStyleSheet;
    }

    /**
     * Sets the given styleSheet to be the active styleSheet used by the program for rendering.
     *
     * @param styleSheet Css file to bew used as the active styleSheet.
     */
    private void setStyleSheet(File styleSheet) {
        String themePath = styleSheet.toURI().toString();
        getSceneStylesheets().set(0, themePath);
    }

    /**
     * Adds a stylesheet URI path to the List of paths representing CSS stylesheet locations. This function may be used
     * by future developers, alongside storage implementations, to allow users to save themes.
     *
     * @param string String representation of a URI path referencing a CSS stylesheet.
     */
    private void addToStyleSheetUriPaths(String string) {
        styleSheetUriPaths.add(string);
    }

    /**
     * Initialises a new variable to store the output file and generates streams responsible for handling the process to
     * modify this scene's CSS stylesheets.
     *
     * @throws FileNotFoundException If creation of the streams is unsuccessful.
     */
    private void initialiseOutputCssAndStreams() throws FileNotFoundException {
        if (myStyleSheet != null && myStyleSheet.exists()) {
            String nameWithoutCssExtension = getFileNameWithoutExtension(myStyleSheet);
            File copy = createNewStyleSheet(nameWithoutCssExtension + " copy.css");
            inputStream = new FileInputStream(getMyStyleSheet());
            tempOutputCss = copy;
        } else {
            inputStream = this.getClass().getResourceAsStream("/view/DarkTheme.css");
            tempOutputCss = getMyStyleSheet();
        }

        inputStreamReader = new InputStreamReader(inputStream);
        bufferedReader = new BufferedReader(inputStreamReader);

        lineReadFromReader = "";
        linesToWriteViaWriter = "";
    }

    /**
     * Writes the line to be written via this StyleManager's writer stream to the output file.
     *
     * @throws IOException If the writing process is unsuccessful.
     */
    private void writeToOutput() throws IOException {
        fileWriter = new FileWriter(tempOutputCss);
        fileWriter.write(linesToWriteViaWriter);
    }

    /**
     * Closes the streams of this StyleManager that have previously been opened to modify the CSS required CSS files to
     * be modified.
     *
     * @throws IOException If closing of any of the streams is unsuccessful.
     */
    private void closeStreams() throws IOException {
        fileWriter.close();
        bufferedReader.close();
        inputStream.close();
        inputStreamReader.close();
    }

    /**
     * Copies the temporary output file to this active stylesheet belonging to the scene of this StyleManager.
     *
     * @throws IOException If the copying process is unsuccessful.
     */
    private void copyTempOutputToMyStyleSheet() throws IOException {
        if ((myStyleSheet != null && myStyleSheet.exists())
                && tempOutputCss != null && tempOutputCss.exists()
                && myStyleSheet.getPath() != tempOutputCss.getPath()) {
            bufferedReader = new BufferedReader(new FileReader(tempOutputCss));
            myStyleSheet.delete();
            myStyleSheet = getMyStyleSheet();
            fileWriter = new FileWriter(myStyleSheet);
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                fileWriter.write(line + "\n");
            }
            bufferedReader.close();
            fileWriter.close();
            tempOutputCss.delete();
            tempOutputCss = null;
        }
    }

    /**
     * Writes the styling content to be written and sets the file with the content as the CSS stylesheet to be used.
     *
     * @throws IOException If writing to the files are unsuccessful.
     */
    public void writeAndSave() throws IOException {
        writeToOutput();
        closeStreams();
        copyTempOutputToMyStyleSheet();
        setStyleSheet(myStyleSheet);
    }

    /**
     * Sets the font family of this style manager's scene.
     *
     * @param fontFamily String representation of a CSS font family.
     */
    public void setFontFamily(String fontFamily) {
        try {
            initialiseOutputCssAndStreams();
            while ((lineReadFromReader = bufferedReader.readLine()) != null) {
                ignoreTextToBeIgnored();
                if (lineReadFromReader != null) {
                    linesToWriteViaWriter += getLineAfterReplacement(lineReadFromReader, "fx-font-family",
                            fontFamily) + "\n";
                }
            }
            writeAndSave();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the font colour of this style manager's scene.
     *
     * @param fontColour String representation of a CSS font colour.
     */
    public void setFontColour(String fontColour) {
        try {
            initialiseOutputCssAndStreams();
            while ((lineReadFromReader = bufferedReader.readLine()) != null) {
                ignoreTextToBeIgnored();
                if (lineReadFromReader != null) {
                    String changedTextFill = getLineAfterReplacement(lineReadFromReader, "fx-text-fill",
                            fontColour);
                    String changedFill = getLineAfterReplacement(changedTextFill,
                            "fx-fill",
                            fontColour);
                    linesToWriteViaWriter += changedFill + "\n";
                }
            }
            writeAndSave();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ignores text to be ignored in each line read by this style manager's reader.
     *
     * @throws IOException If consumption of lines to be ignored is unsuccessful.
     */
    private void ignoreTextToBeIgnored() throws IOException {
        for (String fieldToIgnore : fieldsToIgnore) {
            if (lineReadFromReader != null && lineReadFromReader.startsWith(fieldToIgnore)) {
                ignoreUntilNextField();
            }
        }
    }

    /**
     * Sets the background of this style manager's scene.
     *
     * @param background String representation of a CSS background.
     */
    public void setBackground(Background background) {
        try {
            initialiseOutputCssAndStreams();
            String backgroundColourForImages = "transparent";
            String backgroundColour = background.isBackgroundColour()
                    ? background.toString()
                    : backgroundColourForImages;
            while ((lineReadFromReader = bufferedReader.readLine()) != null) {
                ignoreTextToBeIgnored();
                if (lineReadFromReader != null) {
                    String changedBackgroundFields = getLineAfterReplacement(lineReadFromReader, "fx-background",
                            backgroundColour);
                    String changedBackgroundColourFields = getLineAfterReplacement(changedBackgroundFields,
                            "fx-background-color",
                            backgroundColour);
                    linesToWriteViaWriter += changedBackgroundColourFields + "\n";
                }
            }

            if (!background.isBackgroundColour()) {
                setBackgroundImage(background);
            }
            writeAndSave();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the background image of main window placeholder of this instance of style manager.
     *
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
     * Returns a String representation of the given file's name without extensions.
     *
     * @param file File with name to be extracted without extensions.
     * @return Given file's name without extension as a String.
     */
    private String getFileNameWithoutExtension(File file) {
        return file.getName().replaceFirst("[.][^.]+$", "");
    }

    /**
     * Returns the output String after replacing the values of the specified fields in a stylesheet.
     *
     * @param lineReadFromReader Line of string in which content may have to be possibly replace.
     * @param field              Field of a stylesheet with values to be replaced.
     * @param replacement        Replacement string for the field which requires values to be replaced.
     * @return Output String after replacing the values of the specified fields in a stylesheet.
     */
    private String getLineAfterReplacement(String lineReadFromReader, String field, String replacement) {
        String outputLine = lineReadFromReader;
        int textFillIndex = outputLine.indexOf("-" + field + ": ");
        if (textFillIndex != -1) {
            String subText = lineReadFromReader.substring(textFillIndex);
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
     * Consumes the next whole field from this current onwards.
     *
     * @throws IOException If reading of lines of text is unsuccessful.
     */
    private void ignoreUntilNextField() throws IOException {
        boolean foundOpeningCurlyBraces = lineReadFromReader.contains("{");
        boolean foundClosingCurlyBraces = lineReadFromReader.contains("}");

        while (!foundOpeningCurlyBraces && lineReadFromReader != null) {
            linesToWriteViaWriter += lineReadFromReader + "\n";
            if (lineReadFromReader.contains("{")) {
                foundOpeningCurlyBraces = true;
            }
            lineReadFromReader = bufferedReader.readLine();
        }

        while (!foundClosingCurlyBraces && lineReadFromReader != null) {
            linesToWriteViaWriter += lineReadFromReader + "\n";
            if (lineReadFromReader.contains("}")) {
                foundClosingCurlyBraces = true;
            }
            lineReadFromReader = bufferedReader.readLine();
        }
    }

}
