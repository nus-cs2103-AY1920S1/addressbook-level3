package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import seedu.address.commons.core.LogsCenter;

import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.Team;

/**
 * Panel containing the list of persons.
 */
public class EntityListPanel extends UiPart<Region> {
    private static final String FXML = "ListPanel.fxml";
    private PrefixType prefix;
    private final Logger logger = LogsCenter.getLogger(EntityListPanel.class);

    @FXML
    private ListView<Entity> listView;
    @FXML
    private VBox panelContainer;

    public EntityListPanel(ObservableList<? extends Entity> entityList) {
        super(FXML);
        entityList.forEach(item -> listView.getItems().add(item));
        logger.info("Size of EntityList is: " + listView.getItems().size());
        logger.info("Size of EntityListView is: " + listView.getItems().size());
        if (!entityList.isEmpty()) {
            Entity firstItem = entityList.get(0);
            if (firstItem instanceof Participant) {
                prefix = PrefixType.P;
                listView.setCellFactory(listView -> new ParticipantListViewCell());
            } else if (firstItem instanceof Team) {
                prefix = PrefixType.T;
                listView.setCellFactory(listView -> new TeamListViewCell());
            } else if (firstItem instanceof Mentor) {
                prefix = PrefixType.M;
                listView.setCellFactory(listView -> new MentorListViewCell());
            }
            logger.info("EntityListView has prefix type: " + this.prefix);
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Mentor} using an {@code EntityCard}.
     */
    class MentorListViewCell extends ListCell<Entity> {
        @Override
        protected void updateItem(Entity entity, boolean isEmpty) {
            Mentor curr = (Mentor) entity;
            super.updateItem(curr, isEmpty);

            if (isEmpty || curr == null) {
                setGraphic(null);
                setText(null);
                logger.info("Item does not exist");
            } else {
                setGraphic(new EntityCard(curr, getIndex() + 1).getRoot());
                logger.info("Graphic is set to EntityCard of Mentor type");
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Participant} using an {@code EntityCard}.
     */
    class ParticipantListViewCell extends ListCell<Entity> {
        @Override
        protected void updateItem(Entity entity, boolean isEmpty) {
            Participant curr = (Participant) entity;
            super.updateItem(curr, isEmpty);

            if (isEmpty || curr == null) {
                setGraphic(null);
                setText(null);
                logger.info("Item does not exist");
            } else {
                setGraphic(new EntityCard(curr, getIndex() + 1).getRoot());
                logger.info("Graphic is set to EntityCard of Participant type");
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Team} using an {@code EntityCard}.
     */
    class TeamListViewCell extends ListCell<Entity> {
        @Override
        protected void updateItem(Entity entity, boolean isEmpty) {
            Team curr = (Team) entity;
            super.updateItem(curr, isEmpty);

            if (isEmpty || curr == null) {
                setGraphic(null);
                setText(null);
                logger.info("Item does not exist");
            } else {
                setGraphic(new EntityCard(curr, getIndex() + 1).getRoot());
                logger.info("Graphic is set to EntityCard of Team type");
            }
        }
    }
}
