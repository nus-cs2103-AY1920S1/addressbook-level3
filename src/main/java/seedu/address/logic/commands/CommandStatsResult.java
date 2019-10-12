package seedu.address.logic.commands;

import seedu.address.statistic.Statistic;

public class CommandStatsResult extends CommandResult {

    private final String startingDate;
    private final String endingDate;
    private final StatisticType statisticType;
    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser} and specified {@Code type},
     * and set other fields to their default value.
     * @param feedbackToUser display to user
     * @param startingDate starting date of query
     * @param endingDate ending date of query
     * @param type
     */
    public CommandStatsResult(String feedbackToUser,
                              String startingDate, String endingDate,
                              StatisticType statisticType, UiChange... type) {
        super(feedbackToUser, type);
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.statisticType = statisticType;
    }

    @Override
    public String getStartingDate() {
        return this.startingDate;
    }

    @Override
    public String getEndingDate() {
        return this.endingDate;
    }

    @Override
    public StatisticType getStatisticType() {
        return this.statisticType;
    }
}
