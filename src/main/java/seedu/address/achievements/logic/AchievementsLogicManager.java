package seedu.address.achievements.logic;

import java.util.function.Supplier;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import seedu.address.achievements.logic.parser.AchievementsParser;
import seedu.address.achievements.model.StatisticsModel;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * The main AchievementsLogicManager of the app.
 */
public class AchievementsLogicManager implements AchievementsLogic {
    private final AchievementsParser achievementsParser;
    private final Logger logger = LogsCenter.getLogger(AchievementsLogicManager.class);
    private final Supplier<StatisticsModel> statisticsModelSupplier;

    public AchievementsLogicManager(Supplier<StatisticsModel> statisticsModelSupplier) {
        this.achievementsParser = new AchievementsParser();
        this.statisticsModelSupplier = statisticsModelSupplier;
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command<StatisticsModel> command = achievementsParser.parseCommand(commandText);
        commandResult = command.execute(statisticsModelSupplier.get());

        return commandResult;
    }

    @Override
    public int getTotalPersons() {
        return statisticsModelSupplier.get().getTotalPersons();
    }

    @Override
    public XYChart.Series<Number, String> getAddressChartData() {
        return statisticsModelSupplier.get().getAddressChartData();
    }

    @Override
    public int getTotalDiaryEntries() {
        return statisticsModelSupplier.get().getTotalDiaryEntries();
    }

    @Override
    public XYChart.Series<String, Number> getDiaryChartData() {
        return statisticsModelSupplier.get().getDiaryChartData();
    }

    @Override
    public ObservableList<PieChart.Data> getFinancialPieChartData() {
        return statisticsModelSupplier.get().getFinancialPieChartData();
    }

    @Override
    public XYChart.Series<String, Number> getFinancialBarChartData() {
        return statisticsModelSupplier.get().getFinancialBarChartData();
    }

    @Override
    public XYChart.Series<String, Number> getItineraryLineChartData() {
        return statisticsModelSupplier.get().getItineraryLineChartData();
    }

    @Override
    public int getTotalItineraryEntries() {
        return statisticsModelSupplier.get().getTotalItineraryEntries();
    }

    @Override
    public long getNumberOfDaysTrip() {
        return statisticsModelSupplier.get().getNumberOfDaysTrip();
    }

    @Override
    public long getNumberOfDaysVacation() {
        return statisticsModelSupplier.get().getNumberOfDaysVacation();
    }

    @Override
    public long getNumberOfTrip() {
        return statisticsModelSupplier.get().getNumberOfTrip();
    }

    @Override
    public double getPercentageTrip() {
        return statisticsModelSupplier.get().getPercentageTrip();
    }

}
