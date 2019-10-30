//package seedu.address.ui;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.scene.chart.CategoryAxis;
//import javafx.scene.chart.LineChart;
//import javafx.scene.chart.NumberAxis;
//import javafx.scene.chart.XYChart;
//import org.junit.jupiter.api.Test;
//import seedu.address.model.entity.body.Body;
//import seedu.address.testutil.TypicalBodies;
//
////@@author dalisc
//
//public class LineChartPanelTest {
//    private static final long DAY_IN_MS = 1000 * 60 * 60 * 24;
//    final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
//
//    List<Body> bodyList = TypicalBodies.getTypicalBodies();
//    ObservableList<Body> oListBodies = FXCollections.observableArrayList(bodyList);
//    LineChartPanel expected = new LineChartPanel(oListBodies);
//
//    @Test
//    public void equals() {
//        try {
//            // defining the axes
//            final CategoryAxis xAxis = new CategoryAxis();
//            final NumberAxis yAxis = new NumberAxis();
//            //creating the chart
//            final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
//            // defining a series
//            XYChart.Series<String, Number> series = new XYChart.Series<>();
//            //populating the series with data
//
//            // Fill in the missing dates
//            Date now = new Date();
//            Date tenDaysAgo = new Date(now.getTime() - (10 * DAY_IN_MS));
//            for (Date date = now; date.after(tenDaysAgo); date = new Date(date.getTime() - DAY_IN_MS)) {
//                Date noTimeDate = simpleDateFormat.parse(simpleDateFormat.format(date));
//                series.getData().add(new XYChart.Data(noTimeDate, 0));
//            }
//
//            for (Body body: bodyList) {
//                Date noTimeDate = simpleDateFormat.parse(simpleDateFormat.format(body.getDateOfAdmission()));
//                series.getData().add(new XYChart.Data(noTimeDate, 1));
//
//            }
//
//            lineChart.getData().add(series);
//
//            assertTrue(expected.getLineChart().equals(lineChart));
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
//}
