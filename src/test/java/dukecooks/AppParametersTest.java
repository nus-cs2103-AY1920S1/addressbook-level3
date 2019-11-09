package dukecooks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import javafx.application.Application;

public class AppParametersTest {

    private final ParametersStub parametersStub = new ParametersStub();
    private final AppParameters expected = new AppParameters();

    @Test
    public void parse_validConfigPath_success() {
        parametersStub.namedParameters.put("config", "config.json");
        expected.setConfigPath(Paths.get("config.json"));
        assertEquals(expected, AppParameters.parse(parametersStub));
    }

    @Test
    public void parse_nullConfigPath_success() {
        parametersStub.namedParameters.put("config", null);
        assertEquals(expected, AppParameters.parse(parametersStub));
    }

    @Test
    public void parse_invalidConfigPath_success() {
        parametersStub.namedParameters.put("config", "a\0");
        expected.setConfigPath(null);
        assertEquals(expected, AppParameters.parse(parametersStub));
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

    @Test
    public void test_equals() {
        AppParameters testAppParameters = new AppParameters();
        AppParameters differentConfigPathAppParameters = new AppParameters();
        differentConfigPathAppParameters.setConfigPath(Paths.get("some random path"));

        // same object
        assertTrue(testAppParameters.equals(testAppParameters));

        // different object, same fields
        assertTrue(testAppParameters.equals(expected));

        // different objects and fields
        assertFalse(testAppParameters.equals(null));
        assertFalse(testAppParameters.equals(differentConfigPathAppParameters));
    }

    @Test
    public void test_hashCode() {
        AppParameters testAppParameters = new AppParameters();
        testAppParameters.setConfigPath(Paths.get("config.json"));
        expected.setConfigPath(Paths.get("config.json"));
        assertEquals(testAppParameters.hashCode(), expected.hashCode());
    }
}
