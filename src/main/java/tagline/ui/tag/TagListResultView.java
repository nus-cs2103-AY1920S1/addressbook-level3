package tagline.ui.tag;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

import tagline.model.tag.Tag;
import tagline.ui.ResultView;

/**
 * The UI component that displays the group list as a result.
 */
public class TagListResultView extends ResultView {

    private static final String FXML = "TagListResultView.fxml";

    private TagListPanel tagListPanel;

    @FXML
    private StackPane tagListPanelPlaceholder;

    public TagListResultView() {
        super(FXML);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    public void fillInnerParts(ObservableList<Tag> tagList) {
        tagListPanel = new TagListPanel(tagList);
        tagListPanelPlaceholder.getChildren().add(tagListPanel.getRoot());
    }
}
