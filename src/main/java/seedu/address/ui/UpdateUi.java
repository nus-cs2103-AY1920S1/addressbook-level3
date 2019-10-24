package seedu.address.ui;

import java.util.logging.Logger;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.util.ModeEnum;
import seedu.address.model.appsettings.ThemeEnum;

/**
 * Class to handle updating of Ui.
 */
public class UpdateUi {

    private ModularDisplay modularDisplay;
    private CurrentModeFooter currentModeFooter;
    private final Logger logger = LogsCenter.getLogger(UpdateUi.class);

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
            scene.getStylesheets().clear();
            scene.getStylesheets().add(theme.getThemeUrl());
            scene.getStylesheets().add(theme.getExtensionUrl());
        } else {
            scene.getStylesheets().clear();
            scene.getStylesheets().add(theme.getThemeUrl());
            scene.getStylesheets().add(theme.getExtensionUrl());
        }

        logger.info("----------------[UpdateUI][Setting Stylesheets]"
                + "\n" + "Current Stylesheet is: " + theme.toString());
    }

}
