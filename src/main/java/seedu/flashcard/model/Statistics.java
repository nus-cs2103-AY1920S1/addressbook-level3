package seedu.flashcard.model;

import javafx.scene.chart.XYChart;
import seedu.flashcard.model.flashcard.Flashcard;
import seedu.flashcard.model.flashcard.Score;

import java.util.ArrayList;
import java.util.List;

/**
 * Statistics object that contains all desired statistics.
 */
public class Statistics {
    private Integer totalCorrect;
    private Integer totalWrong;
    private Integer totalAttempted;
    private Integer totalUnattempted;
    private ArrayList<String> unattemptedList;
    private ArrayList<XYChart.Series> flashcardStats;

    public Statistics() {
        totalCorrect = 0;
        totalWrong = 0;
        totalAttempted = 0;
        totalUnattempted = 0;
        unattemptedList = new ArrayList<>();
        flashcardStats = new ArrayList<>();
    }

    public void calculate (List<Flashcard> target) {
        for (Flashcard flashcard : target){
            Score score = flashcard.getScore();
            totalCorrect += score.getCorrectAnswers();
            totalWrong += score.getWrongAnswers();

            if (score.getTotalAttempts() > 0) {
                totalAttempted++;
            } else {
               totalUnattempted++;
               unattemptedList.add(flashcard.getWord().toString());
            }

            XYChart.Series<String, Integer> series = new XYChart.Series<String, Integer>();
            series.setName(flashcard.getWord().toString());
            series.getData().add(new XYChart.Data<String, Integer>("correct", score.getCorrectAnswers()));
            series.getData().add(new XYChart.Data<String, Integer>("incorrect", score.getWrongAnswers()));
            flashcardStats.add(series);
        }
    }

    public String results(){
        return "Displaying results for selected parameters. In summary:\n"
                + "Total correct:" + totalCorrect
                + "Total Wrong:" + totalWrong
                + "Total Attempted:" + totalAttempted
                + "Total Unattempted:" + totalUnattempted
                + "Total cards in list:" + (totalUnattempted + totalAttempted)
                + "List of cards that have not been attempted yet:\n" + concatUnattempted();
    }

    private String concatUnattempted (){
        String list = "";
        for (String s : unattemptedList){
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

    public ArrayList<XYChart.Series> getFlashcardStats() {
        return flashcardStats;
    }
}
