package seedu.address.ui;

import javafx.scene.Scene;

/**
 * Page for a specified feature / extension.
 */
public interface Page {

    /**
     * Gets the scene for {@code this} page
     * @return scene for {@code this} page
     */
    Scene getScene();

    /**
     * Gets the page type of {@code this} page
     * @return page type of {@code this} page
     */
    PageType getPageType();
}
