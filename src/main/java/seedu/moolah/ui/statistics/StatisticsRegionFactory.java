package seedu.moolah.ui.statistics;

import javafx.scene.layout.Region;

/**
 * The API of a Statistics factory to produce the corresponding visual representation for the Statistics Panel
 */
public interface StatisticsRegionFactory {

    Region createRegion();

    String getTitle();
}
