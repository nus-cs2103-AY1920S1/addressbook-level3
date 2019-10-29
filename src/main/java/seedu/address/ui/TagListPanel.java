package seedu.address.ui;

//import java.util.logging.Logger;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

//import seedu.address.commons.core.LogsCenter;

/**
 * Panel containing the list of tags.
 */
public class TagListPanel extends UiPart<Region> {
    private static final String FXML = "TagListPanel.fxml";
    //private final Logger logger = LogsCenter.getLogger(TagListPanel.class);

    @FXML
    private ListView<Tag> tagListView;

    public TagListPanel(ObservableList<Tag> tags) {
        super(FXML);

        List<Tag> tagList = tags.stream().sorted((tag1, tag2) -> {
            if (tag1.isDefault()) {
                if (tag2.isDefault()) {
                    return tag1.getTagName().compareTo(tag2.getTagName());
                } else {
                    return -1;
                }
            } else {
                if (tag2.isDefault()) {
                    return 1;
                } else {
                    return tag1.getTagName().compareTo(tag2.getTagName());
                }
            }
        })
                .collect(Collectors.toUnmodifiableList());

        UniqueTagList uniqueTagList = new UniqueTagList();
        uniqueTagList.setTags(tagList);
        ObservableList<Tag> observableTags = uniqueTagList.asUnmodifiableObservableList();

        tagListView.setItems(observableTags);
        tagListView.setCellFactory(listView -> new TagListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Tag} using a {@code Label}.
     */
    class TagListViewCell extends ListCell<Tag> {
        @Override
        protected void updateItem(Tag tag, boolean empty) {
            super.updateItem(tag, empty);

            if (empty || tag == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TagListCard(tag).getRoot());
            }
        }
    }

}
