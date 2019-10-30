package seedu.address.statistic;

import java.util.Calendar;
import java.util.Date;

import seedu.address.logic.commands.statisticcommand.StatisticType;

/**
 *
 *  A utility class containing a list of {@code StatsPayload} objects to be used in tests.
 *
 */
public class TypicalStatsPayload {

    public static final Calendar STARTING_DATE_1 = new Calendar.Builder()
            .setDate(2018, 1, 1)
            .build();
    public static final Calendar STARTING_DATE_2 = new Calendar.Builder()
            .setDate(2018, 2, 3)
            .build();
    public static final Calendar STARTING_DATE_3 = new Calendar.Builder()
            .setDate(2018, 3, 4)
            .build();
    public static final Calendar STARTING_DATE_4 = new Calendar.Builder()
            .setDate(2018, 5, 10)
            .build();
    public static final Calendar STARTING_DATE_5 = new Calendar.Builder()
            .setDate(2018, 10, 29)
            .build();
    public static final Calendar ENDING_DATE_1 = new Calendar.Builder()
            .setDate(2018, 1, 1)
            .build();
    public static final Calendar ENDING_DATE_2 = new Calendar.Builder()
            .setDate(2018, 3, 4)
            .build();
    public static final Calendar ENDING_DATE_3 = new Calendar.Builder()
            .setDate(2019, 4, 24)
            .build();
    public static final Calendar ENDING_DATE_4 = new Calendar.Builder()
            .setDate(2020, 4, 17)
            .build();
    public static final Calendar ENDING_DATE_5 = new Calendar.Builder()
            .setDate(2019, 11, 29)
            .build();

    public static final Calendar MIN_DATE_TEST = new Calendar
            .Builder()
            .setInstant(new Date(Long.MIN_VALUE))
            .build();

    public static final Calendar MAX_DATE_TEST = new Calendar
            .Builder()
            .setInstant(new Date(Long.MAX_VALUE))
            .build();


    public static final Calendar STARTING_DATE_2019 = new Calendar.Builder()
            .setDate(2019, 00, 01)
            .build();
    public static final Calendar ENDING_DATE_2019 = new Calendar.Builder()
            .setDate(2019, 11, 29)
            .build();


    public static final StatsPayload DEFAULT_STATS_PAYLOAD_REVENUE_1 =
            new StatsPayloadBuilder().withStatsType(StatisticType.REVENUE)
                    .withStartingDate(MIN_DATE_TEST)
                    .withEndingDate(MAX_DATE_TEST).build();

    public static final StatsPayload DEFAULT_STATS_PAYLOAD_REVENUE_2 =
            new StatsPayloadBuilder().withStatsType(StatisticType.REVENUE)
                    .withStartingDate(STARTING_DATE_2)
                    .withEndingDate(ENDING_DATE_2).build();

    public static final StatsPayload DEFAULT_STATS_PAYLOAD_PROFIT_1 =
            new StatsPayloadBuilder().withStatsType(StatisticType.PROFIT)
                    .withStartingDate(MIN_DATE_TEST)
                    .withEndingDate(MAX_DATE_TEST).build();

    public static final StatsPayload DEFAULT_STATS_PAYLOAD_PROFIT_2 =
            new StatsPayloadBuilder().withStatsType(StatisticType.PROFIT)
                    .withStartingDate(STARTING_DATE_2)
                    .withEndingDate(ENDING_DATE_2).build();

    public static final StatsPayload DEFAULT_STATS_PAYLOAD_COST_1 =
            new StatsPayloadBuilder().withStatsType(StatisticType.COST)
                    .withStartingDate(MIN_DATE_TEST)
                    .withEndingDate(MAX_DATE_TEST).build();

    public static final StatsPayload DEFAULT_STATS_PAYLOAD_COST_2 =
            new StatsPayloadBuilder().withStatsType(StatisticType.COST)
                    .withStartingDate(STARTING_DATE_2)
                    .withEndingDate(ENDING_DATE_2).build();

    public static final StatsPayload DEFAULT_STATS_PAYLOAD_GRAPH =
            new StatsPayloadBuilder().withStatsType(StatisticType.COST)
                    .withStartingDate(STARTING_DATE_2019)
                    .withEndingDate(ENDING_DATE_2019).build();

    public static final StatsPayload DEFAULT_STATS_PAYLOAD_GRAPH2 =
            new StatsPayloadBuilder().withStatsType(StatisticType.COST)
                    .withStartingDate(STARTING_DATE_1)
                    .withEndingDate(ENDING_DATE_2).build();
    public static final StatsPayload DEFAULT_STATS_PAYLOAD_GRAPH3 =
            new StatsPayloadBuilder().withStatsType(StatisticType.COST)
                    .withStartingDate(STARTING_DATE_4)
                    .withEndingDate(ENDING_DATE_5).build();



    public static final StatsPayload DEFAULT_STATS_PAYLOAD_STATS_PAYLOAD_TEST =
            new StatsPayloadBuilder().withStatsType(StatisticType.COST)
                    .withStartingDate(STARTING_DATE_4)
                    .withEndingDate(ENDING_DATE_4).build();

    public static final StatsPayload DEFAULT_STATS_PAYLOAD_STATS_PAYLOAD_TEST_IS_DEFAULT =
            new StatsPayloadBuilder().withStatsType(StatisticType.COST)
                    .withStartingDate(MIN_DATE_TEST)
                    .withEndingDate(MAX_DATE_TEST).build();



}
