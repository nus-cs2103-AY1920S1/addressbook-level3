package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.tag.PriorityTag;
import seedu.address.model.tag.Tag;

/**
 * Panel containing the list of study list tags.
 */
public class StudyPlanTagPanel extends UiPart<Region> {
    private static final String FXML = "StudyPlanTagPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(SemesterListPanel.class);

    @FXML
    private FlowPane tags;

    public StudyPlanTagPanel(ObservableList<Tag> tagList) {
        super(FXML);
        tagList.stream().forEach(tag -> {
            Label tagLabel = new Label(tag.getTagName());
            if (tag.isPriority()) {
                tagLabel.getStyleClass().add(((PriorityTag) tag).getPriorityTagType().getStyle());
            }
            tags.getChildren().add(tagLabel);
        });
    }

}
