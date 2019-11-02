package seedu.address.ui.modules;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.util.StringConverter;
import seedu.address.commons.util.AppUtil;
import seedu.address.model.globalstatistics.GlobalStatistics;
import seedu.address.model.globalstatistics.WeeklyPlayed;
import seedu.address.model.wordbankstats.WordBankStatistics;
import seedu.address.ui.AvatarImageUtil;
import seedu.address.ui.UiPart;

/**
 * A UI component for the main title panel, containing title and global statistics.
 */
public class MainTitlePanel extends UiPart<Region> {

    private static final String FXML = "MainTitlePanel.fxml";
    private static final Image LOGO = AppUtil.getImage("/images/logo.png");

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ImageView logoPlaceholder;

    @FXML
    private Label playedTimesText;

    @FXML
    private Label timesText;

    @FXML
    private Label favoriteWordBankText;

    @FXML
    private StackPane weeklyBarChartPlaceholder;

    @FXML
    private ImageView avatarImageView;

    @FXML
    private Circle avatarShadow;

    public MainTitlePanel(GlobalStatistics globalStats, Optional<WordBankStatistics> maxWbStats,
                          int avatarId) {
        super(FXML);

        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        logoPlaceholder.setImage(LOGO);
        logoPlaceholder.setScaleX(3.0);
        logoPlaceholder.setScaleY(3.0);
        int playedTimes = globalStats.getNumPlayed();
        playedTimesText.setText(playedTimes + "");
        timesText.setText(playedTimes == 1 ? " time" : " times");

        favoriteWordBankText.setText(" - ");
        maxWbStats.ifPresent(statistics -> favoriteWordBankText.setText(statistics.getWordBankName()));

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setTickLabelFormatter(new StringConverter<>() { // set to only showing integer values
            @Override
            public String toString(Number object) {
                if (object.intValue() != object.doubleValue()) {
                    return "";
                }
                return object.intValue() + "";
            }

            @Override
            public Number fromString(String string) {
                Number val = Double.parseDouble(string);
                return val.intValue();
            }
        });
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        WeeklyPlayed weeklyPlayed = globalStats.getWeeklyPlayed();
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        List<Map.Entry<DayOfWeek, Integer>> sortedData = new ArrayList<>(weeklyPlayed.getNumPlayed().entrySet());
        sortedData.sort(Comparator.comparing(Map.Entry::getKey));
        for (Map.Entry<DayOfWeek, Integer> entry : sortedData) {
            dataSeries.getData().add(new XYChart.Data<>(entry.getKey().toString().substring(0, 3),
                    entry.getValue()));
        }
        barChart.getData().add(dataSeries);
        weeklyBarChartPlaceholder.getChildren().add(barChart);

        Image avatarImage = AvatarImageUtil.get(avatarId);
        avatarImageView.setImage(avatarImage);

        avatarShadow.setId("avatar-shadow");
    }
}
