package seedu.weme.ui;

import java.util.List;
import java.util.stream.Collectors;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;

import seedu.weme.model.ReadOnlyWeme;
import seedu.weme.model.meme.Meme;
import seedu.weme.model.statistics.TagWithCount;

/**
 * Panel containing the statistical data about memes in Weme.
 */
public class StatsPanel extends UiPart<Region> {
    private static final String FXML = "StatsPanel.fxml";
    private static final String PIE_CHART_TITLE = "Tag usage";
    private static final int LABEL_LINE_LENGTH = 10;

    @FXML
    private PieChart piechart;

    public StatsPanel(ReadOnlyWeme weme) {
        super(FXML);
        generatePieChart(weme);
        weme.getMemeList().addListener((ListChangeListener<Meme>) change -> generatePieChart(weme));
    }

    /**
     * Generates a {@code PieChart} with a given {@code MemeBook} and {@code Stats}.
     *
     * <p>Styling is mainly done in the CSS file.</p>
     */
    private void generatePieChart(ReadOnlyWeme weme) {
        List<TagWithCount> tagsWithCount = weme.getTagsWithCountList();
        ObservableList<PieChart.Data> pieChartData =
                tagsWithCount.stream()
                        .map(tagWithCount -> new PieChart.Data(tagWithCount.getTag().tagName, tagWithCount.getCount()))
                        .map(data -> bindLikeCountToLabel(data))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
        piechart.setData(pieChartData);
        piechart.setTitle(PIE_CHART_TITLE);
        piechart.setLabelLineLength(LABEL_LINE_LENGTH);
        piechart.setLegendSide(Side.LEFT);
    }

    private PieChart.Data bindLikeCountToLabel(PieChart.Data data) {
        data.nameProperty().bind(Bindings.concat(data.getName(), ": ", Math.round(data.getPieValue())));
        return data;
    }

}
