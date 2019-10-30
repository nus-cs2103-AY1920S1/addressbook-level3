package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person} when selected.
 */
public class InformationDisplay extends UiPart<Region> {

    public static final String NO_PERSON_SELECTED_MESSAGE = "";
    private static final String FXML = "InformationDisplay.fxml";

    public final Person person;

    @FXML
    private FlowPane tags;
    @FXML
    private GridPane informationBlock;
    @FXML
    private ImageView photo;
    @FXML
    private Label name;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label attendanceRate;
    @FXML
    private Label performance;
    @FXML
    private Label title;
    @FXML
    private Label secondaryTitle;
    @FXML
    private VBox imageHolder;
    @FXML
    private VBox informationBox;

    public InformationDisplay(Person selectedPerson, String attendance) {
        super(FXML);
        this.person = selectedPerson;
        name.setText(this.person.getName().fullName);
        phone.setText(this.person.getPhone().value);
        address.setText(this.person.getAddress().value);
        address.setPrefWidth(150);
        address.setWrapText(true);
        email.setText(this.person.getEmail().value);
        photo.setImage(new Image(this.person.getPhoto().filePath));
        photo.setPreserveRatio(true);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add((new TagLayout(tag.tagName)).getRoot()));
        //setText of attendance and performance. Should attendance and performance be tag to a person?
        attendanceRate.setText(attendance);
        performance.setText("superb");
        resizeImage();
    }

    /**
     * Resize the image when window size changes
     */
    public void resizeImage() {
        photo.fitHeightProperty().bind(imageHolder.heightProperty().subtract(50));
        photo.fitWidthProperty().bind(imageHolder.widthProperty().subtract(50));
    }
}
