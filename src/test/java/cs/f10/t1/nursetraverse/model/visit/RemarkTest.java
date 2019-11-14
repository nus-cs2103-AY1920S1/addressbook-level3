package cs.f10.t1.nursetraverse.model.visit;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

public class RemarkTest {

    @Test
    public void constructor_null_noException() {
        assertDoesNotThrow(() -> new Remark(null));
    }

    @Test
    public void constructor_emptyRemark_noException() {
        assertDoesNotThrow(() -> new Remark(""));
    }

    @Test
    public void constructor_normalRemark_noException() {
        assertDoesNotThrow(() -> new Remark("Anything legitimate !@#$%^&*()_+{}:\"<>?1234567890-=`~,./;'[]\\|/"));
    }
}
