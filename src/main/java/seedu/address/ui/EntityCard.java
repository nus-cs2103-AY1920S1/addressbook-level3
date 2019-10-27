package seedu.address.ui;

import static seedu.address.Paths.MENTOR_ICON;
import static seedu.address.Paths.STUDENT_ICON;
import static seedu.address.Paths.TEAM_ICON;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.Team;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class EntityCard extends UiPart<Region> {

    private static final String FXML = "EntityCard.fxml";

    public final Entity entity;

    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private PrefixType type;

    @FXML
    private HBox cardPane;
    @FXML
    private VBox labels;
    @FXML
    private Label name; //can be team name, mentor name or participant name
    @FXML
    private Label index;
    @FXML
    private Label id;
    @FXML
    private ImageView idIcon;

    /**
     * Constructs a new instance of Entity Card.
     *
     * @param entity Entity to make a card out of.
     * @param displayedIndex the index of the card.
     */
    public EntityCard(Entity entity, int displayedIndex) {
        super(FXML);

        this.entity = entity;
        index.setText(displayedIndex + ". ");
        id.setText(entity.getId().toString());
        name.setText(entity.getName().toString());
        if (entity instanceof Participant) {
            this.generateParticipantCard(entity);
        } else if (entity instanceof Mentor) {
            this.generateMentorCard(entity);
        } else {
            this.generateTeamCard(entity);
        }
    }

    /**
     * Generates participant Card according to entity type.
     *
     * @param entity type of Entity.
     */
    private void generateParticipantCard(Entity entity) {
        this.idIcon.setImage(getImage(STUDENT_ICON));
        logger.info("The participant icon has been changed to: " + idIcon);
        Participant participant = (Participant) entity;
        labels.getChildren().add(new Label(participant.getPhone().value));
        labels.getChildren().add(new Label(participant.getEmail().value));
        this.type = PrefixType.P;
    }

    /**
     * Generates Mentor Card according to entity type.
     *
     * @param entity type of Entity.
     */
    private void generateMentorCard(Entity entity) {
        this.idIcon.setImage(getImage(MENTOR_ICON));
        Mentor mentor = (Mentor) entity;
        labels.getChildren().add(new Label(mentor.getOrganization().toString()));
        labels.getChildren().add(new Label(mentor.getSubject().toString()));
        this.type = PrefixType.M;
    }

    /**
     * Generates Team Card according to entity type.
     *
     * @param entity type of Entity.
     */
    private void generateTeamCard(Entity entity) {
        this.idIcon.setImage(getImage(TEAM_ICON));
        Team team = (Team) entity;
        FlowPane participantPane = new FlowPane();
        team.getParticipants().stream()
                .sorted(Comparator.comparing(pt -> pt.getName().toString()))
                .forEach(p -> participantPane.getChildren().add(new Label(p.getName().toString())));
        Optional<Mentor> teamMentor = team.getMentor();
        labels.getChildren().add(
                new Label(teamMentor.isEmpty() ? "Mentor not assigned" : teamMentor.get().getName().toString()));
        labels.getChildren().add(new Label(team.getSubject().toString()));
        labels.getChildren().add(new Label(team.getProjectName().toString()));
        labels.getChildren().add(new Label(team.getLocation().toString()));
        labels.getChildren().add(new Label("Score: " + team.getScore().toString()));
        this.type = PrefixType.T;
    }

    private Image getImage(String imagePath) {
        logger.info("File path of image is: " + imagePath);
        logger.info(this.getClass().getResourceAsStream(imagePath).toString());
        return new Image(Objects.requireNonNull(this.getClass().getResourceAsStream(imagePath)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EntityCard)) {
            return false;
        }

        // state check(if two EntityCard are equal)
        EntityCard card = (EntityCard) other;
        return id.getText().equals(card.id.getText())
                && entity.equals(card.entity)
                && name.getText().equals(card.name.getText());
    }
}
