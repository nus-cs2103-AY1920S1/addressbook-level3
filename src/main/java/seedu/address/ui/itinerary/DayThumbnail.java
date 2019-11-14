package seedu.address.ui.itinerary;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.itinerary.days.EnterDayCommand;
import seedu.address.logic.commands.itinerary.days.EnterEditDayCommand;
import seedu.address.model.itinerary.day.Day;
import seedu.address.ui.MainWindow;
import seedu.address.ui.UiPart;


/**
 * A component for displaying the details of a day.
 */
public class DayThumbnail extends UiPart<AnchorPane> {
    private static final String FXML = "itinerary/days/DayThumbnail.fxml";

    @FXML
    private Label destinationLabel;

    @FXML
    private Label indexLabel;

    @FXML
    private Button editButton;

    @FXML
    private Label dateLabel;

    @FXML
    private AnchorPane anchorPane;

    private Day day;
    private Index displayedIndex;
    private MainWindow mainWindow;

    public DayThumbnail(Day day, Index displayedIndex, MainWindow mainWindow) {
        super(FXML);
        this.day = day;
        this.displayedIndex = displayedIndex;
        this.mainWindow = mainWindow;
        fillDayThumbnailLabels();
    }

    /**
     * Fills the contents of the thumbnails.
     */
    private void fillDayThumbnailLabels() {
        indexLabel.setText("DAY " + displayedIndex.getOneBased() + "");
        dateLabel.setText(day.getStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
        destinationLabel.setWrapText(true);
        destinationLabel.setText(day.getDestination().toString());

        Boolean isPhotoPresent = day.getPhoto().isPresent();

        if (isPhotoPresent) {
            Image img = day.getPhoto().get().getImage();
            BackgroundImage bgImg = new BackgroundImage(img,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                            false, false, true, true));
            anchorPane.setBackground(new Background(bgImg));
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DayThumbnail)) {
            return false;
        }

        //state check
        DayThumbnail otherThumbnail = (DayThumbnail) other;
        return day.equals(otherThumbnail.day)
                && this.displayedIndex.equals(otherThumbnail.displayedIndex);
    }

    @FXML
    private void handleEditDay() {
        mainWindow.executeGuiCommand(EnterEditDayCommand.COMMAND_WORD + " " + displayedIndex.getOneBased());
    }

    @FXML
    private void handleEnterDay() {
        mainWindow.executeGuiCommand(EnterDayCommand.COMMAND_WORD + " " + displayedIndex.getOneBased());
    }

}
