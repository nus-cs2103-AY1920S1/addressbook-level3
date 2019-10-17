package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TestUtil.makeModelStack;

import java.util.Optional;
import java.util.Stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Hybrid (Unit and Integration) tests for ModelHistory.
 */
public class ModelHistoryTest {

    private ModelHistory history;

    @BeforeEach
    public void setup() {
        history = new ModelHistory();
    }

    @Test
    public void emptyConstructor() {
        assertTrue(history.isPastModelsEmpty());
        assertTrue(history.isFutureModelsEmpty());
    }

    @Test
    public void copyConstructor() {
        assertEquals(history, new ModelHistory(history));
    }

    @Test
    public void resetData_nullArgument_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> history.resetData(null));
    }

    @Test
    public void resetData_success() {
        ModelHistory other = new ModelHistory(makeModelStack(new ModelManager()), makeModelStack(new ModelManager()));
        history.resetData(other);
        assertEquals(other, history);
    }

    @Test
    public void setPastModels_nullArgument_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> history.setPastModels(null));
    }

    @Test
    public void setPastModels_success() {
        Stack<Model> pastModels = makeModelStack(new ModelManager());
        history.setPastModels(pastModels);
        assertEquals(pastModels, history.getPastModels());
    }

    @Test
    public void setFutureModels_nullArgument_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> history.setFutureModels(null));
    }

    @Test
    public void setFutureModels_success() {
        Stack<Model> futureModels = makeModelStack(new ModelManager());
        history.setFutureModels(futureModels);
        assertEquals(futureModels, history.getFutureModels());
    }

    @Test
    public void addToPastModels_nullArgument_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> history.addToPastModels(null));
    }

    @Test
    public void addToPastModels_success() {
        Model model = new ModelManager();
        history.addToPastModels(model);
        assertEquals(makeModelStack(model), history.getPastModels());
    }

    @Test
    public void addToFutureModels_nullArgument_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> history.addToPastModels(null));
    }

    @Test
    public void addToFutureModels_success() {
        Model model = new ModelManager();
        history.addToFutureModels(new ModelManager(model));
        assertEquals(makeModelStack(model), history.getFutureModels());
    }

    @Test
    public void clearFutureModels_success() {
        history.clearFutureModels();
        assertTrue(history.isFutureModelsEmpty());
    }

    @Test
    public void getPrevModel_noModel_returnsEmptyOptional() {
        assertTrue(history.getPrevModel().isEmpty());
    }

    @Test
    public void getPrevModel_hasModel_returnsModel() {
        Model model = new ModelManager();
        history.setPastModels(makeModelStack(model));
        Optional<Model> prevModel = history.getPrevModel();
        assertTrue(prevModel.isPresent());
        assertEquals(model, prevModel.get());
    }

    @Test
    public void getNextModel_noModel_returnsEmptyOptional() {
        assertTrue(history.getNextModel().isEmpty());
    }

    @Test
    public void getNextModel_hasModel_returnsModel() {
        Model model = new ModelManager();
        history.setFutureModels(makeModelStack(model));
        Optional<Model> nextModel = history.getNextModel();
        assertTrue(nextModel.isPresent());
        assertEquals(model, nextModel.get());
    }

    @Test
    public void equals() {
        Stack<Model> oneModelInStack = makeModelStack(new ModelManager());

        // To same object
        assertEquals(history, history);
        // To null
        assertNotEquals(history, null);
        // To objects from other classes
        assertNotEquals(history, 1);
        // To ModelHistory objects but different in past models
        assertNotEquals(history, new ModelHistory(oneModelInStack, history.getFutureModels()));
        // To ModelHistory objects but different in future models
        assertNotEquals(history, new ModelHistory(history.getPastModels(), oneModelInStack));
        // To ModelHistory objects but different in past and future models
        assertNotEquals(history, new ModelHistory(oneModelInStack, oneModelInStack));
        // To ModelHistory objects and equal in both models
        assertEquals(history, new ModelHistory(history.getPastModels(), history.getFutureModels()));
    }
}
