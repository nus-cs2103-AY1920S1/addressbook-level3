package seedu.address.commons.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

/**
 * Utility for generating Statistics.
 */
public class StatisticsUtil {
    public static <T> XYChart.Series<String, Number> getMonthDataSeries(
            ObservableList<T> observableList, Function<T, MonthData> toMonthDataFunction) {

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        // add chart data
        StatisticsUtil.streamOfMonthDataFromEvent(observableList, toMonthDataFunction)
                .map(monthData ->
                        new XYChart.Data<String, Number>(
                                monthData.getValue(), monthData.getKey()))
                .forEach(data -> series.getData().add(data));
        return series;
    }

    public static <T> XYChart.Series<Number, String> getCountryDataSeries(
            ObservableList<T> observableList, Function<T, CountryData> toCountryDataFunction) {
        XYChart.Series<Number, String> series = new XYChart.Series<>();
        StatisticsUtil.streamOfCountryDataFromEvent(observableList, toCountryDataFunction)
                .map(ip -> new XYChart.Data<Number, String>(ip.getKey(), ip.getValue()))
                .forEach(data -> series.getData().add(data));
        return series;
    }

    public static <K, V> ObservableList<PieChart.Data> getFinancialPieChartData(
            HashMap<K, V> hashMap, Predicate<Map.Entry<K, V>> predicate,
            Function<Map.Entry<K, V>, PieChart.Data> toPieChartDataFunction) {
        // initialise pie chart data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        // get statistics from hashmap
        hashMap.entrySet().stream()
                .filter(entry -> predicate.test(entry))
                .forEach(entrySet -> {
                    pieChartData.add(toPieChartDataFunction.apply(entrySet));
                });
        return pieChartData;
    }

    public static <K, V> XYChart.Series<String, Number> getFinancialBarChartData(
            HashMap<K, V> hashMap, Predicate<Map.Entry<K, V>> predicate,
            Function<Map.Entry<K, V>, XYChart.Data<String, Number>> toBarChartDataFunction) {
        //initialise bar chart data
        XYChart.Series barChartData = new XYChart.Series();

        hashMap.entrySet()
                .stream()
                .filter(entry -> predicate.test(entry))
                .forEach(entry -> barChartData.getData().add(toBarChartDataFunction.apply(entry)));
        return barChartData;
    }

    /**
     * Generate a stream from an observable list applying {@code toMonthDataFunction} to obtain a {@link MonthData} for
     * each entry in the observable list
     *
     * @param observableList      observable list containing data
     * @param toMonthDataFunction function to map data to {@link MonthData}
     * @param <T>
     * @return stream of month data
     */
    private static <T> Stream<MonthData> streamOfMonthDataFromEvent(ObservableList<T> observableList,
                                                                    Function<T, MonthData> toMonthDataFunction) {
        TreeUtil<MonthData> treeUtil = new TreeUtil<>();
        FXCollections.unmodifiableObservableList(observableList).stream().forEach(t -> {
            MonthData defaultIfMissing = toMonthDataFunction.apply(t);
            Function<MonthData, MonthData> incrementFunction =
                monthData -> new MonthData(monthData.getKey() + 1, monthData.getValue());
            treeUtil.add(defaultIfMissing.getValue(), defaultIfMissing, incrementFunction);
        });
        return treeUtil.ascendingStream();
    }

    /**
     * Generate a stream from an observable list applying {@code toCountryDataFunction} to obtain a {@link CountryData}
     * for each entry in the observable list
     *
     * @param observableList        observable list containing data
     * @param toCountryDataFunction function to map data to {@link CountryData}
     * @param <T>
     * @return stream of month data
     */
    private static <T> Stream<CountryData> streamOfCountryDataFromEvent(
            ObservableList<T> observableList, Function<T, CountryData> toCountryDataFunction) {
        TreeUtil<CountryData> treeUtil = new TreeUtil();

        Function<CountryData, CountryData> incrementFunction =
            countryData -> new CountryData(countryData.getKey() + 1, countryData.getValue());

        //for each country, update its frequency count in the hashmap
        observableList.stream().forEach(t -> {
            CountryData defaultIfMissing = toCountryDataFunction.apply(t);
            String country = defaultIfMissing.getValue();
            treeUtil.add(country, defaultIfMissing, incrementFunction);
        });

        return treeUtil.descendingStream();
    }
}
