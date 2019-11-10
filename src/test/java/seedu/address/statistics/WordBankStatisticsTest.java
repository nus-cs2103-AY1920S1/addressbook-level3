package seedu.address.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalCards.ABRA;
import static seedu.address.testutil.TypicalCards.BUTTERFREE;
import static seedu.address.testutil.TypicalCards.CHARIZARD;
import static seedu.address.testutil.TypicalCards.DITTO;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.appsettings.DifficultyEnum;
import seedu.address.model.wordbankstats.CardStatistics;
import seedu.address.model.wordbankstats.ScoreData;
import seedu.address.model.wordbankstats.WordBankStatistics;

public class WordBankStatisticsTest {

    @Test
    public void getEmpty_valid_success() {
        WordBankStatistics emptyWbStats = WordBankStatistics.getEmpty("empty");
        WordBankStatistics emptyWbStats1 = new WordBankStatistics("empty",
                0,
                Optional.empty(),
                Collections.emptyList(),
                Collections.emptyList(),
                false, false, false);
        assertEquals(emptyWbStats, emptyWbStats1);
    }

    @Test
    public void update_valid_success() {
        WordBankStatistics wbStats = WordBankStatistics.getEmpty("title");
        wbStats.update(new GameStatistics("title", ScoreData.MAX_SCORE,
                10.0,
                List.of(ABRA, BUTTERFREE), // list of correct cards
                Collections.emptyList()), // list of wrong cards
                DifficultyEnum.HARD);
        WordBankStatistics wbStats1 = new WordBankStatistics("title", 1,
                Optional.of(10.0),
                List.of(new CardStatistics(ABRA.getId(), 1, 1),
                        new CardStatistics(BUTTERFREE.getId(), 1, 1)),
                List.of(new ScoreData(ScoreData.MAX_SCORE)),
                false, false, true);
        assertEquals(wbStats, wbStats1);

        wbStats.update(new GameStatistics("title", ScoreData.MAX_SCORE - 1,
                        10.0,
                        List.of(ABRA, CHARIZARD), // list of correct cards
                        List.of(DITTO)), // list of wrong cards
                DifficultyEnum.MEDIUM);
        WordBankStatistics wbStats2 = new WordBankStatistics("title", 2,
                Optional.of(10.0),
                List.of(new CardStatistics(ABRA.getId(), 2, 2),
                        new CardStatistics(BUTTERFREE.getId(), 1, 1),
                        new CardStatistics(DITTO.getId(), 1, 0),
                        new CardStatistics(CHARIZARD.getId(), 1, 1)),
                List.of(new ScoreData(ScoreData.MAX_SCORE),
                        new ScoreData(ScoreData.MAX_SCORE - 1)),
                false, false, true);
        assertEquals(wbStats, wbStats2);
    }
}
