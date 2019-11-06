package seedu.address.ui.listpanel;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Statistics;
import seedu.address.ui.UiPart;

/**
 * Panel containing the list of persons.
 */
public class StatisticsListPanel extends UiPart<Region> {
    private static final String FXML = "StatisticsListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EntityListPanel.class);

    private Statistics statistics;
    @FXML
    private Label totalTeamsLabel;

    @FXML
    private Label totalMentorsLabel;

    @FXML
    private Label totalParticipantsLabel;

    @FXML
    private BarChart<String, Number> teamBarChart;

    @FXML
    private BarChart<String, Number> mentorBarChart;

    @FXML
    private CategoryAxis subjectAxis;

    @FXML
    private CategoryAxis specialisationAxis;

    @FXML
    private NumberAxis teamYAxis;

    @FXML
    private NumberAxis mentorYAxis;

    public StatisticsListPanel(Statistics statistics) {
        super(FXML);
        statistics = statistics;

        //Set the text for the Label
        totalTeamsLabel.setText(String.valueOf(statistics.getTotalTeams()));
        totalMentorsLabel.setText(String.valueOf(statistics.getTotalMentors()));
        totalParticipantsLabel.setText(String.valueOf(statistics.getTotalParticipants()));

        //Set the data of the Bar Chart for teamBarChart
        XYChart.Series<String, Number> subjectSeries = new XYChart.Series<String, Number>();
        subjectSeries.getData().add(new XYChart.Data<String, Number>("Environmental", statistics.getEnvTeams()));
        subjectSeries.getData().add(new XYChart.Data<String, Number>("Social", statistics.getSocialTeams()));
        subjectSeries.getData().add(new XYChart.Data<String, Number>("Health", statistics.getHealthTeams()));
        subjectSeries.getData().add(new XYChart.Data<String, Number>("Education", statistics.getEduTeams()));
        teamBarChart.getData().add(subjectSeries);

        //Set the data of the Bar Chart for mentorBarChart
        XYChart.Series<String, Number> specialisationSeries = new XYChart.Series<String, Number>();
        specialisationSeries.getData().add(
                new XYChart.Data<String, Number>("Environmental", statistics.getEnvMentors())
        );
        specialisationSeries.getData().add(new XYChart.Data<String, Number>("Social", statistics.getSocialMentors()));
        specialisationSeries.getData().add(new XYChart.Data<String, Number>("Health", statistics.getHealthMentors()));
        specialisationSeries.getData().add(new XYChart.Data<String, Number>("Education", statistics.getEduMentors()));

        mentorBarChart.getData().add(specialisationSeries);


    }


}

