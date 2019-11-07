package seedu.moolah;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Application;

public class AppParametersTest {

    private ParametersStub parametersStub;
    private AppParameters expected;

    @BeforeEach
    void setUp() {
        parametersStub = new ParametersStub();
        expected = new AppParameters();
    }

    @Test
    void parse_validConfigPath_success() {
        parametersStub.namedParameters.put("config", "config.json");
        expected.setConfigPath(Paths.get("config.json"));
        assertEquals(expected, AppParameters.parse(parametersStub));
    }

    @Test
    void parse_nullConfigPath_success() {
        parametersStub.namedParameters.put("config", null);
        assertEquals(expected, AppParameters.parse(parametersStub));
    }

    @Test
    void parse_invalidConfigPath_success() {
        parametersStub.namedParameters.put("config", "a\0");
        expected.setConfigPath(null);
        assertEquals(expected, AppParameters.parse(parametersStub));
    }

    @Test
    void testEquals() {
        assertEquals(expected, expected);
        assertNotEquals(expected, new AppParametersTest());
    }

    @Test
    void testHashCode() {
        parametersStub.namedParameters.put("config", "config.json");
        expected.setConfigPath(Paths.get("config.json"));
        assertEquals(expected.getConfigPath().hashCode(), expected.hashCode());
    }

    private static class ParametersStub extends Application.Parameters {
        private Map<String, String> namedParameters = new HashMap<>();

        @Override
        public List<String> getRaw() {
            throw new AssertionError("should not be called");
        }

        @Override
        public List<String> getUnnamed() {
            throw new AssertionError("should not be called");
        }

        @Override
        public Map<String, String> getNamed() {
            return Collections.unmodifiableMap(namedParameters);
        }
    }
}
