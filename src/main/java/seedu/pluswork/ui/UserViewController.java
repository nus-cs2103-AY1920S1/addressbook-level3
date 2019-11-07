package seedu.pluswork.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 * Controller class for the user views.
 */
public class UserViewController {

    /**
     * Holder of a switchable user view.
     */
    @FXML
    private Pane userViewHolder;

    public Pane getCurrentView() {
        return userViewHolder;
    }

    /**
     * Replaces the view displayed to the user with a new one.
     *
     * @param uiPart the UiPart to be swapped in.
     */
    public void setUserView(UiPart<Region> uiPart) {
        userViewHolder.getChildren().clear(); // clear the view
        userViewHolder.getChildren().add(uiPart.getRoot());
    }
}
