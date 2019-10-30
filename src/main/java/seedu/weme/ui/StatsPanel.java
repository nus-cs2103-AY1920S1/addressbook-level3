package seedu.weme.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;

import seedu.weme.model.ReadOnlyWeme;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.statistics.TagWithStats;

/**
 * Panel containing the statistical data about memes in Weme.
 */
public class StatsPanel extends UiPart<Region> {
    private static final String FXML = "StatsPanel.fxml";
    private static final String COUNT_CHART_TITLE = "Tag usage";
    private static final String LIKE_CHART_TITLE = "Likes per Tag";
    private static final int LABEL_LINE_LENGTH = 10;

    @FXML
    private PieChart tagCountChart;
    @FXML
    private PieChart tagLikeChart;

    public StatsPanel(ReadOnlyWeme weme) {
        super(FXML);
        renderCharts(weme);
        weme.getMemeList().addListener((ListChangeListener<Meme>) change -> renderCharts(weme));
        weme.getStats().getObservableLikeData().addListener((MapChangeListener<String, Integer>) change ->
                renderCharts(weme));
    }

    /**
     * Renders the charts with Chart generator methods.
     */
    private void renderCharts(ReadOnlyWeme weme) {
        generateTagCountChart(weme);
        generateTagLikeChart(weme);
    }

    /**
     * Generates a {@code PieChart} with a given {@code MemeBook} and {@code Stats}.
     *
     * <p>Styling is mainly done in the CSS file.</p>
     */
    private void generateTagCountChart(ReadOnlyWeme weme) {
        generatePieChart(weme.getTagsWithCountList(), tagCountChart, COUNT_CHART_TITLE);
    }

    /**
     * Generates a {@code PieChart} with a given {@code Weme}.
     *
     * <p>Styling is mainly done in the CSS file.</p>
     */
    private void generateTagLikeChart(ReadOnlyWeme weme) {
        generatePieChart(weme.getTagsWithLikeCountList(), tagLikeChart, LIKE_CHART_TITLE);
    }

    /**
     * Generates pie chart with a list of tags with stats and the title.
     */
    private void generatePieChart(List<? extends TagWithStats> tagsWithStats, PieChart chart, String title) {
        List<PieChart.Data> data = new ArrayList<>();
        for (TagWithStats tag : tagsWithStats) {
            data.add(new PieChart.Data(tag.getTag().tagName, tag.getData()));
        }
        ObservableList<PieChart.Data> pieChartData = data.stream()
                .map(chartData -> bindValueToLabel(chartData))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        chart.setData(pieChartData);
        chart.setTitle(title);
        chart.setLabelLineLength(LABEL_LINE_LENGTH);
        chart.setLegendSide(Side.LEFT);
    }

    private PieChart.Data bindValueToLabel(PieChart.Data data) {
        data.nameProperty().bind(Bindings.concat(data.getName(), ": ", Math.round(data.getPieValue())));
        return data;
    }

}
