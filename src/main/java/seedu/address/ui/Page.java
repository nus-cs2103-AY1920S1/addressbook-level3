package seedu.address.ui;

import javafx.scene.Scene;

// todo-this-week: get your page to implement this interface, refer to SamplePage for a concrete example
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
