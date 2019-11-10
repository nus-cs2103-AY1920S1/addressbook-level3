package seedu.address.ui;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.entity.worker.Worker;

//@@author shaoyi1997
/**
 * An UI component that displays information of a {@code Worker}.
 */
public class WorkerCard extends UiPart<Region> {

    private static final String FXML = "WorkerListCard.fxml";
    private static final Logger logger = LogsCenter.getLogger(WorkerCard.class);
    private static final int OFFET_FOR_PASTEL = 127;
    private static Random random = new Random();
    private static HashMap<Worker, String> workerToColorMap = new HashMap<>();
    private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");


    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Worker worker;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label workerId;
    @FXML
    private Label sex;
    @FXML
    private Label phoneNumber;
    @FXML
    private Label dateJoined;
    @FXML
    private Label dateOfBirth;
    @FXML
    private Label designation;
    @FXML
    private Label employmentStatus;
    @FXML
    private StackPane displayPhotoPlaceholder;
    @FXML
    private ImageView displayPhoto;

    public WorkerCard(Worker worker, int displayedIndex) {
        super(FXML);
        this.worker = worker;
        id.setText(displayedIndex + ". ");
        name.setText(worker.getName().toString());
        sex.setText(worker.getSex().toString());
        workerId.setText(worker.getIdNum().toString());
        dateJoined.setText(formatDate(worker.getDateJoined()));
        dateOfBirth.setText(worker.getDateOfBirth().isPresent() ? formatDate(worker.getDateOfBirth().get()) : "-");
        phoneNumber.setText(worker.getPhone().isPresent() ? worker.getPhone().get().toString() : "-");
        designation.setText(worker.getDesignation().isPresent() ? worker.getDesignation().get() : "-");
        employmentStatus.setText(worker.getEmploymentStatus().isPresent() ? worker.getEmploymentStatus().get() : "-");
        setDisplayPhoto();
    }

    public static String formatDate(Date date) {
        return format.format(date);
    }

    private void setDisplayPhoto() {
        // first, create the clipping circle
        double radiusAndXCentre = displayPhoto.getFitWidth() / 2;
        double yCentre = displayPhoto.getFitHeight() / 2;
        Circle circle = new Circle(radiusAndXCentre, yCentre, radiusAndXCentre);

        // second, sets the display photo of the worker if user supplies a photo
        // else, circle is embedded with the initials of the worker's name
        if (worker.getPhoto().isPresent()) {
            displayPhoto.setImage(new Image(worker.getPhoto().get().getPathToDataDirectory()));
            displayPhoto.setClip(circle);
        } else {
            circle.setFill(Paint.valueOf(generateColor()));
            Label initialsOfName = new Label(getInitials(worker.getName().toString()));
            displayPhotoPlaceholder.getChildren().addAll(circle, initialsOfName);
        }
    }

    /**
     * Generates a random pastel color for the display photo of each worker.
     * @return String of hex value of generated color
     */
    private String generateColor() {
        // The unique generated color for each worker will be stored in a HashMap,
        // so that whenever there is an event change to WorkerListPanel and WorkerCard,
        // the same color will be produced for each worker instead of generating a new color.
        if (workerToColorMap.containsKey(worker)) {
            return workerToColorMap.get(worker);
        } else {
            // generate random pastel color
            // pastel colors fall when the rgb values are close in the middle ranges of 0-256
            // an int between 0-128 (as opposed to 0-255) is randomised for each tone and 127 is added
            int red = random.nextInt(128);
            int green = random.nextInt(128);
            int blue = random.nextInt(128);
            Color color = new Color(red + OFFET_FOR_PASTEL, green + OFFET_FOR_PASTEL,
                blue + OFFET_FOR_PASTEL);
            String colorInHex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
            workerToColorMap.put(worker, colorInHex);
            return colorInHex;
        }
    }

    /**
     * Extracts out the initials of the given {@code name}.
     * Maxmimum of two initials only.
     */
    private String getInitials(String name) {
        if (name.isEmpty()) {
            logger.fine("Empty name. No initial is displayed in Worker photo.");
            return "";
        }
        String[] wordsInName = name.split("\\s+");
        String initials;
        if (wordsInName.length == 1) {
            initials = "" + wordsInName[0].charAt(0);
        } else {
            initials = "" + wordsInName[0].charAt(0) + wordsInName[1].charAt(0);
        }
        return initials.toUpperCase();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof WorkerCard)) {
            return false;
        }

        // state check
        WorkerCard card = (WorkerCard) other;
        return id.getText().equals(card.id.getText())
                && worker.equals(card.worker);
    }
}
//@@author
