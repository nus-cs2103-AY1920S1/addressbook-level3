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
     * @param modeEnum The mode that the app is currently in.
     * @param modularDisplayPlaceholder {@code modularDisplayPlaceholder} in {@code MainWindow} that gets updated.
     */
    public void updateModularDisplay(ModeEnum modeEnum, StackPane modularDisplayPlaceholder) {
        switch (modeEnum) {
            case OPEN:
                modularDisplay.swapToBankDisplay(modularDisplayPlaceholder);
                break;
            case HOME:
                modularDisplay.swapToLoadDisplay(modularDisplayPlaceholder);
                break;
            case SETTINGS:
                modularDisplay.swapToSettings(modularDisplayPlaceholder);
                break;
            default:
                break;
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
