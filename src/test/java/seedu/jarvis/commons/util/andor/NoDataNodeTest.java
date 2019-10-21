package seedu.jarvis.commons.util.andor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class NoDataNodeTest {
    /**
     * A NodeDataNode stub to test a method.
     *
     * @param <T> generic type
     */
    private static class NoDataNodeStub<T> extends NoDataNode<T> {
        @Override
        protected boolean fulfills(Collection<T> col) {
            return false;
        }

        @Override
        protected AndOrOperation type() {
            return null;
        }

        @Override
        public String toString() {
            return null;
        }
    }

    @Test
    public void getData_returnsOptionalEmpty() {
        NoDataNodeStub<String> noDataNodeStub = new NoDataNodeStub<>();
        assertEquals(noDataNodeStub.getData(), Optional.empty());
    }
}
