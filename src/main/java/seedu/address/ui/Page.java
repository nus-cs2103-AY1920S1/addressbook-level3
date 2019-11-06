package seedu.address.ui;

import javafx.scene.Parent;

/**
 * Page for a specified feature / extension.
 */
public interface Page {

    /**
     * Gets the page type of {@code this} page
     * @return page type of {@code this} page
     */
    PageType getPageType();

    /**
     * Gets the parent node for {@code this} page
     * @return parent node for {@code this} page
     */
    Parent getParent();
}
