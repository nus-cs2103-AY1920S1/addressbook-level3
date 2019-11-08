//@author shawnpunchew11

package dream.fcard.logic.exam;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ResultTest {

    @Test
    void testGetScore() {
        Result testResult = new Result(10);
        assertEquals("0/10", testResult.getScore());
    }

    void testMark_correctAnswer() {
        Result testResult = new Result(10);
        testResult.mark(true);
        assertEquals("1/10", testResult.getScore());
    }

    void testMark_incorrectAnswer() {
        Result testResult = new Result(10);
        testResult.mark(false);
        assertEquals("0/10", testResult.getScore());
    }

}
