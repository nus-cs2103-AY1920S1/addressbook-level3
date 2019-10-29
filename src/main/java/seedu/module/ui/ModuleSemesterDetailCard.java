package seedu.module.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.module.model.module.SemesterDetail;

/**
 * An UI component that displays semester details of a {@code Module}.
 */
public class ModuleSemesterDetailCard extends UiPart<Region> {

    private static final String FXML = "ModuleSemesterDetail.fxml";
    private static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("EEE, dd MMM YYYY hh:mma");

    @FXML
    private Label semesterLabel;
    @FXML
    private HBox examDetailBox;
    @FXML
    private Label examDateLabel;
    @FXML
    private Label examDurationLabel;
    @FXML
    private Label notOfferedLabel;
    @FXML
    private Label noExamLabel;

    /**
     * Constructs the graphic representation of {@code SemesterDetail}.
     */
    public ModuleSemesterDetailCard(SemesterDetail semesterDetail) {
        super(FXML);
        semesterLabel.setText(prettifySemesterIndex(semesterDetail.getSemester()));

        if (!semesterDetail.isOffered()) {
            examDetailBox.setVisible(false);
            noExamLabel.setVisible(false);
            return;
        }

        if (semesterDetail.getExamDate().isEmpty()) {
            examDetailBox.setVisible(false);
            notOfferedLabel.setVisible(false);
            return;
        }

        examDateLabel.setText(prettifyExamDate(semesterDetail.getExamDate().get()));
        examDurationLabel.setText(prettifyExamDuration(semesterDetail.getExamDuration()));
        noExamLabel.setVisible(false);
        notOfferedLabel.setVisible(false);
    }

    /**
     * Prettifies the {@code semester} index.
     */
    private String prettifySemesterIndex(int semester) {
        switch (semester) {
        case 1:
        case 2:
            return String.format("Semester %d", semester);
        case 3:
        case 4:
            return String.format("Special Term %d", semester - 2);
        default:
            return "Invalid semester";
        }
    }

    /**
     * Prettifies the {@code examDate}.
     */
    private String prettifyExamDate(LocalDateTime examDate) {
        if (examDate == null) {
            return "Invalid Exam Date";
        }

        // TODO: Extract offset to config and use ZonedDateTime if possible
        return examDate.plusHours(8).format(dateTimeFormat);
    }

    /**
     * Prettifies the {@code examDuration}.
     */
    private String prettifyExamDuration(int examDuration) {
        int hours = examDuration / 60;
        int minutes = examDuration % 60;

        StringBuilder sb = new StringBuilder();

        if (hours > 0) {
            sb.append(hours);
            sb.append(" hour");
            if (hours > 1) {
                sb.append("s");
            }
        }

        if (minutes > 0) {
            sb.append(" and ");
            sb.append(minutes);
            sb.append(" minutes"); // shouldn't have 0/1 minute cases
        }

        return sb.toString();
    }

}
