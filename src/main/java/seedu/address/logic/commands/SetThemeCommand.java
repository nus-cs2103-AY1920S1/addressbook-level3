//@@author shutingy
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.ui.MainWindow;

/**
 * Change the theme of the GUI
 */
public class SetThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";

    public static final String DARKTHEME = "view/DarkTheme.css";
    public static final String LIGHTTHEME = "view/LightTheme.css";
    public static final String PINKTHEME = "view/PinkTheme.css";
    public static final String BLUETHEME = "view/BlueTheme.css";
    public static final String HACKERTHEME = "view/HackerTheme.css";
    public static final String NUSTHEME = "view/NUSTheme.css";

    public static final String MESSAGE_SUCCESS = "Theme changed to %s";

    private String styleSheet;
    /**
     * Creates a SetThemeCommand to change the theme of the gui
     */
    public SetThemeCommand(String styleSheet) {
        this.styleSheet = styleSheet;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        /* todo: gui version
        System.out.println(Main.class.getResourceAsStream("/images/Light.png"));
        Image light = new Image(Main.class.getResourceAsStream("/images/Light.png"));
        Image dark = new Image(Main.class.getResourceAsStream("/images/Dark.png"));
        System.out.println(light.getUrl());
        ImageView iv = new ImageView();
        AnchorPane an = new AnchorPane();
        an.getChildren().add(iv);
        Scene scene = new Scene(an);
        Scene scene = new Scene(new ThemeWindow().getView());
        Stage stage = new Stage();
        stage.setTitle("Theme");
        stage.show();

        GuiSettings guiSettings = model.getGuiSettings();
        GuiSettings newGuiSettings = new GuiSettings(guiSettings.getWindowWidth(),
                guiSettings.getWindowHeight(), guiSettings.getWindowCoordinates().x,
                guiSettings.getWindowCoordinates().y, styleSheet);
        model.setGuiSettings(newGuiSettings);

        */
        requireNonNull(model);
        MainWindow.setStylesheet(styleSheet);
        model.setStyleSheet(styleSheet);
        return new CommandResult(String.format(MESSAGE_SUCCESS, getStyleSheetName(styleSheet)));
    }

    private String getStyleSheetName(String styleSheet) {
        requireNonNull(styleSheet);
        String temp = styleSheet.split("view/")[1];
        temp = temp.split(".css")[0];
        return temp;
    }
}
