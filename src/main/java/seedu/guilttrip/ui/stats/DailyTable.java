//package seedu.guilttrip.ui.stats;
//
//import static javafx.application.ConditionalFeature.FXML;
//
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.layout.Region;
//import seedu.guilttrip.model.statistics.CategoryStatistics;
//import seedu.guilttrip.model.statistics.DailyStatistics;
//import seedu.guilttrip.ui.UiPart;
//
//public class DailyTable extends UiPart<Region> {
//    private static final String FXML = "statistics/DailyTable.fxml";
//    @javafx.fxml.FXML
//    private TableView<DailyStatistics> statsTable;
//
//    public DailyTable(ObservableList<DailyStatistics> statsMap) {
//        super(FXML);
//        this.statsTable.getColumns().clear();
//        for (int i = 0; i < statsMap.size(); i++) {
//            DailyStatistics ds = statsMap.get(i);
//            TableColumn<DailyStatistics, Double> tc = new TableColumn<DailyStatistics, Double>("1");
//            tc.setCellValueFactory(p ->  );
//        }
//        this.statsTable.getColumns().add(x)
//        totalLabel.setText(type);
//        statsMap.addListener(new ListChangeListener<CategoryStatistics>() {
//            @Override
//            public void onChanged(Change<? extends CategoryStatistics> change) {
//                updateStatisticsTable(statsMap);
//            }
//        });
//        updateStatisticsTable(statsMap);
//    }
//
//    /**
//     * Updates the statistics table based the CategoryStatisticsList.
//     * @param statsMap contains the list of statistics by category
//     */
//    public void updateStatisticsTable(ObservableList<CategoryStatistics> statsMap) {
//        this.statsTable.setItems(statsMap);
//        categoryName.setCellValueFactory(p -> p.getValue().getCategoryNameProperty());
//        amt.setCellValueFactory(p -> p.getValue().getAmountCalculatedProperty().asObject());
//    }
//}
