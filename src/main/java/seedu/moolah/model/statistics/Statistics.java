package seedu.moolah.model.statistics;

import seedu.moolah.ui.statistics.StatisticsRegionFactory;

/**
 * The API of a Statistics object
 */
public interface Statistics {

    String getTitle();

    void setTitle(String title);

    void populateData();

    StatisticsRegionFactory createFactory();

}
