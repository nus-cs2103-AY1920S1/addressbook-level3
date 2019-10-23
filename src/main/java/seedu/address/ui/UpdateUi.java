package seedu.address.ui;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import seedu.address.logic.util.ModeEnum;
import seedu.address.model.appsettings.ThemeEnum;


/**
 * Class to handle updating of Ui.
 */
public class UpdateUi {

    private ModularDisplay modularDisplay;
    private CurrentModeFooter currentModeFooter;

    public UpdateUi(ModularDisplay modularDisplay, CurrentModeFooter currentModeFooter) {
        this.modularDisplay = modularDisplay;
        this.currentModeFooter = currentModeFooter;
    }

    /**
     * Updates the {@code ModularDisplay} in {@code MainWindow}.
     *
     * @param command The command that triggers the update.
     * @param modularDisplayPlaceholder {@code modularDisplayPlaceholder} in {@code MainWindow} that gets updated.
     */
    public void updateModularDisplay(String command, ModeEnum modeEnum, StackPane modularDisplayPlaceholder) {
        String firstArg = command.split(" ")[0];
        if (firstArg.equals("load")) {
            modularDisplay.swapToBanks(modularDisplayPlaceholder);
        } else if (firstArg.equals("bank")) {
            modularDisplay.swapToBankDisplay(modularDisplayPlaceholder);
        } else if (firstArg.equals("list")) {
            modularDisplay.swapToList(modularDisplayPlaceholder);
        } else if (firstArg.equals("skip")) {

        } else if (modeEnum.equals(ModeEnum.SETTINGS)) {
            modularDisplay.swapToSettings(modularDisplayPlaceholder);
        } else {
            if (modeEnum.equals(ModeEnum.GAME)) {
                // Swapping to load display by default disabled when in game mode (by Yida).
                return;
            }
            modularDisplay.swapToLoadDisplay(modularDisplayPlaceholder);
        }
    }

    public void setTheme(ThemeEnum theme, Scene scene) {
        if (theme.equals(ThemeEnum.DARK)) {
            scene.getStylesheets().remove(ThemeEnum.LIGHT.getThemeUrl());
            scene.getStylesheets().remove(ThemeEnum.LIGHT.getExtensionUrl());
            scene.getStylesheets().add(theme.getThemeUrl());
            scene.getStylesheets().add(theme.getExtensionUrl());
        } else {
            scene.getStylesheets().remove(ThemeEnum.DARK.getThemeUrl());
            scene.getStylesheets().remove(ThemeEnum.DARK.getExtensionUrl());
            scene.getStylesheets().add(theme.getThemeUrl());
            scene.getStylesheets().add(theme.getExtensionUrl());
        }
    }

}
