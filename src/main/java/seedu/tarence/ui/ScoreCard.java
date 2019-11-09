package seedu.tarence.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import seedu.tarence.model.student.Student;


/**
 * An UI component that displays information of a student's assignment score.
 */
public class ScoreCard extends UiPart<Region> {
    private static final String FXML = "ScoreListCard.fxml";
    private Student student;
    private long lowerPercentile;
    private long upperPercentile;

    @FXML
    private HBox cardPane;

    @FXML
    private Label name;

    @FXML
    private Label score;

    @FXML
    private Label remark;

    public ScoreCard(Student student, Integer score, long lowerPencentile, long upperPercentile) {
        super(FXML);
        this.student = student;
        this.lowerPercentile = lowerPencentile;
        this.upperPercentile = upperPercentile;
        this.name.setText(student.getName().fullName);
        if (score == -1) {
            this.score.setText("");
        } else {
            this.score.setText(Integer.toString(score));
        }
        setRemark(score);
    }

    private void setRemark(Integer score) {
        if (score == -1) {
            this.remark.setStyle("-fx-background-color: #606060FF");
            this.remark.setText("Score is not set");
        } else if (score >= upperPercentile) {
            this.remark.setStyle("-fx-background-color: #2BAE66FF");
            this.remark.setText("75th Percentile");
        } else if (score <= lowerPercentile) {
            this.remark.setStyle("-fx-background-color: #E94B3CFF");
            this.remark.setText("25th Percentile");
        } else {
            this.remark.setStyle("-fx-background-color: #ffa333");
            this.remark.setText("Average");
        }
    }

}
