package seedu.revision.model.quiz;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * To compute lists of historical scores according to difficulty levels.
 */
public class GraphList {

    private final ObservableList<Double> totalScoreList = FXCollections.observableArrayList();
    private final ObservableList<Double> diff1List = FXCollections.observableArrayList();
    private final ObservableList<Double> diff2List = FXCollections.observableArrayList();
    private final ObservableList<Double> diff3List = FXCollections.observableArrayList();
    private final ObservableList<ObservableList<Double>> graphList = FXCollections.observableArrayList();
    private Iterator<Statistics> statisticsIterator;

    public GraphList(ObservableList<Statistics> statisticsList) {
        statisticsIterator = statisticsList.iterator();
        while (statisticsIterator.hasNext()) {
            Statistics next = statisticsIterator.next();
            totalScoreList.add(next.getResult());
            diff1List.add(next.getResult1());
            diff2List.add(next.getResult2());
            diff3List.add(next.getResult3());
        }
        graphList.add(totalScoreList);
        graphList.add(diff1List);
        graphList.add(diff2List);
        graphList.add(diff3List);
    }

    public ObservableList<ObservableList<Double>> getGraphList() {
        return graphList;
    }


}
