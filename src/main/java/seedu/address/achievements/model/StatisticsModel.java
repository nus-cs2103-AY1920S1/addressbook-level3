package seedu.address.achievements.model;

import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 * The API of the StatisticsModel component.
 */
public interface StatisticsModel {

    ObservableList<Node> getStatisticsView();
}
