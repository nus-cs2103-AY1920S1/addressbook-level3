package seedu.address.ui;

import static seedu.address.Paths.MENTOR_ICON;
import static seedu.address.Paths.SCORE_ICON;
import static seedu.address.Paths.STUDENT_ICON;
import static seedu.address.Paths.TEAM_ICON;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
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
    private Label score;
    @FXML
    private ImageView idIcon;
    @FXML
    private GridPane internalPane;
    @FXML
    private FlowPane membersPane; //Only initialised for TeamCards, when there are members

    /**
     * Constructs a new instance of Entity Card.
     *
     * @param entity         Entity to make a card out of.
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
        Label subjectLabel = new Label("Subject: " + mentor.getSubject().toString());
        subjectLabel.setStyle("-fx-background-color: #58427c");
        labels.getChildren().add(subjectLabel);
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
        Label mentorLabel = new Label(
                teamMentor.isEmpty()
                        ? "Mentor not assigned" : "Mentor: " + teamMentor.get().getName().toString());
        mentorLabel.setStyle("-fx-background-color: #827f9c");
        labels.getChildren().add(mentorLabel);

        Label subjectLabel = new Label("Subject: " + team.getSubject().toString());
        subjectLabel.setStyle("-fx-background-color: #58427c");
        labels.getChildren().add(subjectLabel);
        Label projectLabel = new Label("Project Name: " + team.getProjectName().toString());
        projectLabel.setStyle("-fx-background-color: #483d8b; ");
        labels.getChildren().add(projectLabel);

        Label locationLabel = new Label("Location: " + team.getLocation().toString());
        locationLabel.setStyle("-fx-background-color: #26428b");
        labels.getChildren().add(locationLabel);
        List<Participant> participants = team.getParticipants();
        logger.info("Number of Members in team: " + participants.size());
        membersPane.getChildren().add(new Label("Members: "));

        logger.info("Size of membersPane before adding Participants: " + membersPane.getChildren().size());
        participants.stream().forEach(p -> membersPane.getChildren().add(new Label(p.getName().toString() + " | ")));
        this.type = PrefixType.T;
        addScoreIcon(team);
    }

    /**
     * Adds a new Score Icon to GUI, with "SCORE:" label and actual Score value.
     *
     * @param team Team of which score will be displayed.
     */
    private void addScoreIcon(Team team) {
        StackPane scoreIcon = new StackPane();
        scoreIcon.setAlignment(Pos.CENTER);

        //VBox to set labels for score(score title and score value).
        VBox scoreLabels = new VBox(new Label("SCORE:"), new Label(team.getScore().toString()));
        scoreLabels.setAlignment(Pos.CENTER);
        scoreLabels.setPrefHeight(70.0);
        scoreLabels.setPrefWidth(70.0);

        //Image for icon
        ImageView icon = new ImageView();
        icon.setImage(getImage(SCORE_ICON));
        icon.setFitHeight(100.0);
        icon.setFitWidth(130.0);
        icon.setOpacity(0.8);

        //Initialise scoreIcon StackPane
        scoreIcon.getChildren().add(icon);
        //Add scoreLabels
        scoreIcon.getChildren().add(scoreLabels);

        //Place Score Icon inside Card
        internalPane.add(scoreIcon, 2, 0);
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
