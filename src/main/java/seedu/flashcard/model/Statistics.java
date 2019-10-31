package seedu.flashcard.model;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.Score;


/**
 * Statistics object that contains all desired statistics.
 */
public class Statistics {
    private Integer totalCorrect;
    private Integer totalWrong;
    private Integer totalAttempted;
    private Integer totalUnattempted;
    private ArrayList<String> unattemptedList;
    private XYChart.Series<String, Number> correctSeries;
    private XYChart.Series<String, Number> wrongSeries;

    public Statistics() {
        reset();
    }

    /**
     * Calculates the parameters for each statistic and stores the data
     * @param target list of flashcard to be calculated
     */
    public void calculate (ObservableList<Flashcard> target) {
        reset();
        for (Flashcard flashcard : target) {
            Score score = flashcard.getScore();
            totalCorrect += score.getCorrectAnswers();
            totalWrong += score.getWrongAnswers();

            if (score.getTotalAttempts() > 0) {
                totalAttempted++;
            } else {
                totalUnattempted++;
                unattemptedList.add(flashcard.getQuestion().toString());
            }

            correctSeries.setName("correct");
            correctSeries.getData().add(new XYChart.Data<>(flashcard.getQuestion().shortenForLabel(),
                    score.getCorrectAnswers()));

            wrongSeries.setName("wrong");
            wrongSeries.getData().add(new XYChart.Data<>(flashcard.getQuestion().shortenForLabel(),
                score.getWrongAnswers()));
        }
    }

    /**
     * resets variables for new calculation
     */
    private void reset() {
        totalCorrect = 0;
        totalWrong = 0;
        totalAttempted = 0;
        totalUnattempted = 0;
        unattemptedList = new ArrayList<>();
        correctSeries = new XYChart.Series<>();
        wrongSeries = new XYChart.Series<>();
    }

    /**
     * Creates a string of summary statistics for feedback to user
     * @return returns a feedback string
     */
    public String results() {
        return "Displaying results for selected parameters. In summary:\n"
                + "Total correct:" + totalCorrect
                + "\nTotal Wrong:" + totalWrong
                + "\nTotal Attempted:" + totalAttempted
                + "\nTotal Unattempted:" + totalUnattempted
                + "\nTotal cards in list:" + (totalUnattempted + totalAttempted)
                + "\nList of cards that have not been attempted yet:\n" + concatUnattempted();
    }

    /**
     * Concatenates a list of cards that have not yet been attempted
     * @return a string of cards
     */
    private String concatUnattempted () {
        String list = "";
        for (String s : unattemptedList) {
            list += " -" + s + "\n";
        }
        return list;
    }

    public Integer getTotalCorrect() {
        return totalCorrect;
    }

    public Integer getTotalWrong() {
        return totalWrong;
    }

    public Integer getTotalAttempted() {
        return totalAttempted;
    }

    public Integer getTotalUnattempted() {
        return totalUnattempted;
    }

    public ArrayList<String> getUnattemptedList() {
        return unattemptedList;
    }

    public XYChart.Series<String, Number> getCorrectSeries() {
        return correctSeries;
    }

    public XYChart.Series<String, Number> getWrongSeries() {
        return wrongSeries;
    }
}
