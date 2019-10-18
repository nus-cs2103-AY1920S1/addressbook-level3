package seedu.address.ui.modules;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;
import seedu.address.statistics.ScoreData;
import seedu.address.statistics.WordBankStatistics;
import seedu.address.ui.UiPart;

import java.util.Arrays;
import java.util.List;

/**
 * A ui component of the word bank progress chart.
 */
public class WordBankProgressChart extends UiPart<Region> {

    private static final String FXML = "WordBankProgressChart.fxml";

    @FXML
    StackPane progressChartPlaceholder;

    @FXML
    ComboBox<String> progressChartModeSelector;

    public WordBankProgressChart(WordBankStatistics wbStatistics) {
        super(FXML);
        NumGamesValues[] numGamesValues = NumGamesValues.values();
        for (NumGamesValues numGamesValue : numGamesValues) {
            progressChartModeSelector.getItems().add(numGamesValue.getMsg());
        }
        progressChartModeSelector.getSelectionModel().selectFirst();
        progressChartModeSelector.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Arrays.stream(NumGamesValues.values())
                        .filter(x -> x.getMsg().equals(newValue))
                        .findFirst()
                        .ifPresent(x -> initProgressChart(wbStatistics, x.getNumGames()));
            }
        });

//        progressChartModeSelector.on

        initProgressChart(wbStatistics, numGamesValues[0].getNumGames());
    }

    private void initProgressChart(WordBankStatistics wbStatistics, int numGames) {
        progressChartPlaceholder.getChildren().clear();
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                if (object.intValue() != object.doubleValue() || object.intValue() % 5 != 0) {
                    return "";
                }
                return "" + object.intValue();
            }

            @Override
            public Number fromString(String string) {
                Number val = Double.parseDouble(string);
                return val.intValue();
            }
        });
        xAxis.setTickUnit(1);
        xAxis.setMinorTickVisible(false);
        xAxis.setAutoRanging(false);
        xAxis.setUpperBound(Math.max(numGames, wbStatistics.getGamesPlayed()) + 0.5);
        xAxis.setLowerBound(Math.max(0, wbStatistics.getGamesPlayed() - numGames + 0.5));

        yAxis.setAutoRanging(false);
        yAxis.setUpperBound(ScoreData.MAX_SCORE + 2); // give some room at the top
        yAxis.setLowerBound(ScoreData.MIN_SCORE);
        yAxis.setTickUnit(10);

        LineChart<Number, Number> progressChart = new LineChart<>(xAxis, yAxis);
        XYChart.Series<Number, Number> series = new XYChart.Series<>();

        List<ScoreData> scoreStats = wbStatistics.getScoreStats();
        for (int i = Math.max(0, scoreStats.size() - numGames); i < scoreStats.size(); ++i) {
            int gameIndex = i + 1;
            int curScore = scoreStats.get(i).getScore();
            series.getData().add(new XYChart.Data<>(gameIndex, curScore));
        }

        progressChart.setLegendVisible(false);
        progressChart.getData().add(series);
        progressChart.setMinHeight(200);
        progressChart.setCreateSymbols(false);

        progressChartPlaceholder.getChildren().add(progressChart);
    }

    private enum NumGamesValues {
        THIRTY(30, "Last 30 games"),
        ONE_HUNDRED(100, "Last 100 games");

        int numGames;
        String msg;

        NumGamesValues(int numGames, String msg) {
            this.numGames = numGames;
            this.msg = msg;
        }

        int getNumGames() {
            return numGames;
        }

        String getMsg() {
            return msg;
        }
    }
}
