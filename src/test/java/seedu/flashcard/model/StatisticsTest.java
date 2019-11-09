package seedu.flashcard.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.flashcard.testutil.TypicalFlashcard.getTypicalStatsFlashcards;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import seedu.flashcard.model.flashcard.Flashcard;



public class StatisticsTest {

    private Statistics statsTester;
    private ObservableList<Flashcard> flashcardsSample;

    @BeforeEach
    public void setUp() {
        statsTester = new Statistics();
        flashcardsSample = FXCollections.observableList(getTypicalStatsFlashcards());
    }

    @Test
    public void reset_resetAllValues_success() {
        statsTester.reset();
        assertEquals(0, statsTester.getTotalCorrect());
        assertEquals(0, statsTester.getTotalWrong());
        assertEquals(0, statsTester.getTotalUnattempted());
        assertEquals(0, statsTester.getTotalAttempted());
        assertEquals(0, statsTester.getUnattemptedList().size());
        assertEquals(new XYChart.Series<String, Number>().getData(), statsTester.getCorrectSeries().getData());
        assertEquals(new XYChart.Series<String, Number>().getData(), statsTester.getWrongSeries().getData());
    }

    @Test
    public void calculate_standardListOfCards_success() {
        statsTester.calculate(flashcardsSample);
        assertEquals(2, statsTester.getTotalCorrect());
        assertEquals(2, statsTester.getTotalWrong());
        assertEquals(1, statsTester.getTotalUnattempted());
        assertEquals(1, statsTester.getTotalAttempted());
        assertEquals(1, statsTester.getUnattemptedList().size());
        assertEquals(flashcardsSample.get(1).getQuestion().toString(), statsTester.getUnattemptedList().get(0));
    }

    @Test
    public void results_generateStatisticalReply_success() {
        statsTester.calculate(flashcardsSample);
        String correctReply = "Displaying getResults for selected parameters. In summary:\n"
                + "Total correct:2"
                + "\nTotal Wrong:2"
                + "\nTotal Attempted:1"
                + "\nTotal Unattempted:1"
                + "\nTotal cards in list:2"
                + "\nList of cards that have not been attempted yet:\n" + " -How many airports are there in Tokyo?\n";
        assertEquals(correctReply, statsTester.getResults());
    }



}
