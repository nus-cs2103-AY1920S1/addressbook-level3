package seedu.address.statistic;

/**
 * API of statistics component
 * contains methods that CommandResult will call to calculate
 */
public interface Statistic {

    /**
     * calculate total Cost
     */
    void calculateTotalCost();

    /**
     * calculate total Profit
     */
    void calculateTotalProfit();

    /**
     * calculate total Gross revenue
     */
    void calculateTotalRevenue();
}
