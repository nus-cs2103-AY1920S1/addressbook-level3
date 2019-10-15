package seedu.savenus.model.info;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RecommendInfoTest {

    @Test
    public void recommendInfo_differentClass() {
        RecommendInfo recommendInfo = new RecommendInfo();
        assertFalse(recommendInfo.equals(new RecommendInfo()));
    }

    @Test
    public void recommend_correctCommandWord() {
        assertTrue(RecommendInfo.COMMAND_WORD.equals("recommend"));
    }

    @Test
    public void recommend_wrongInformation() {
        assertFalse(RecommendInfo.INFORMATION.isEmpty());
    }

    @Test
    public void recommend_correctUsage() {
        assertTrue(RecommendInfo.USAGE.equals("recommend"));
    }

    @Test
    public void recommend_wrongOutput() {
        assertFalse(RecommendInfo.OUTPUT.isEmpty());
    }
}
