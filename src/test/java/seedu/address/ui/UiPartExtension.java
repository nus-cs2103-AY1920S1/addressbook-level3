package seedu.address.ui;

import java.util.concurrent.TimeoutException;

import org.testfx.api.FxToolkit;

import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Provides an isolated stage to test an individual {@code UiPart}.
 */
public class UiPartExtension extends StageExtension {
    private static final String[] CSS_FILES = {"view/SMLTheme.css", "view/Extensions.css"};

    public void setUiPart(final UiPart<? extends Parent> uiPart) {
        try {
            FxToolkit.setupScene(() -> {
                Scene scene = new Scene(uiPart.getRoot());
                scene.getStylesheets().setAll(CSS_FILES);
                return scene;
            });
            FxToolkit.showStage();
        } catch (TimeoutException te) {
            throw new AssertionError("Timeout should not happen.", te);
        }
    }
}
