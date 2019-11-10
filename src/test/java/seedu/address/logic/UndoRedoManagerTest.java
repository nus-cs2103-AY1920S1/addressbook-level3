package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DateTime;
import seedu.address.model.ModelData;
import seedu.address.model.ModelManager;
import seedu.address.model.events.EventSource;

//@@author bruceskellator

class UndoRedoManagerTest {

    @Test
    void undo_nothingToUndo_exceptionThrown() {
        ModelManager modelManager = new ModelManager();
        UndoRedoManager undoRedoManager = new UndoRedoManager(modelManager);
        undoRedoManager.start();
        modelManager.addModelDataListener(undoRedoManager);
        assertThrows(CommandException.class, undoRedoManager::undo);
    }

    @Test
    void undo_nothingToRedo_exceptionThrown() {
        ModelManager modelManager = new ModelManager();
        UndoRedoManager undoRedoManager = new UndoRedoManager(modelManager);
        undoRedoManager.start();
        modelManager.addModelDataListener(undoRedoManager);
        assertThrows(CommandException.class, undoRedoManager::redo);

        String description = "description";
        DateTime start = DateTime.now();
        List<EventSource> events = new ArrayList<>(modelManager.getEvents());
        events.add(EventSource.newBuilder(description, start).build());
        modelManager.setModelData(new ModelData(events, modelManager.getTasks()));
        assertThrows(CommandException.class, undoRedoManager::redo);
    }

    @Test
    void onModelListChange_modelListChanged_writtenCorrectly() {
        ModelManager modelManager = new ModelManager();
        UndoRedoManager undoRedoManager = new UndoRedoManager(modelManager);
        undoRedoManager.start();
        modelManager.addModelDataListener(undoRedoManager);
        assertEquals(1, undoRedoManager.getUndoStateList().size());
        assertEquals(0, undoRedoManager.getUndoIndex());
        // Test whether a deep-copied version of ModelData in ModelManager has been stored to UndoRedoManager,
        // and not the original object itself
        assertTrue(undoRedoManager.getUndoStateList().get(0) != modelManager.getModelData());
        assertEquals(undoRedoManager.getUndoStateList().get(0), modelManager.getModelData());

        String description = "description";
        DateTime start = DateTime.now();
        List<EventSource> events = new ArrayList<>(modelManager.getEvents());
        events.add(EventSource.newBuilder(description, start).build());
        modelManager.setModelData(new ModelData(events, modelManager.getTasks()));
        assertEquals(2, undoRedoManager.getUndoStateList().size());
        assertEquals(1, undoRedoManager.getUndoIndex());

        // ModelData in ModelManager should no longer match the previous version in UndoRedoManager
        assertFalse(undoRedoManager.getUndoStateList().get(0).equals(modelManager.getModelData()));
        // Test whether a deep-copied version of ModelData in ModelManager has been stored to UndoRedoManager,
        // and not the original object itself
        assertTrue(undoRedoManager.getUndoStateList().get(1) != modelManager.getModelData());
        assertEquals(undoRedoManager.getUndoStateList().get(1), modelManager.getModelData());
    }

    @Test
    void undo_validUndo_writtenCorrectly() {
        ModelManager modelManager = new ModelManager();
        UndoRedoManager undoRedoManager = new UndoRedoManager(modelManager);
        undoRedoManager.start();
        modelManager.addModelDataListener(undoRedoManager);

        String description = "description";
        DateTime start = DateTime.now();
        List<EventSource> events = new ArrayList<>(modelManager.getEvents());
        events.add(EventSource.newBuilder(description, start).build());
        modelManager.setModelData(new ModelData(events, modelManager.getTasks()));

        assertDoesNotThrow(undoRedoManager::undo);
        assertEquals(0, undoRedoManager.getUndoIndex());
        // ModelData in ModelManager should no longer match the latest version in UndoRedoManager
        assertFalse(undoRedoManager.getUndoStateList().get(1).equals(modelManager.getModelData()));
        // Test whether ModelData in ModelManager has been updated to match the previous version in UndoRedoManager
        // (Value-equality but not reference equality)
        assertTrue(undoRedoManager.getUndoStateList().get(0) != modelManager.getModelData());
        assertEquals(undoRedoManager.getUndoStateList().get(0), modelManager.getModelData());
    }

    @Test
    void undo_validRedo_writtenCorrectly() {
        ModelManager modelManager = new ModelManager();
        UndoRedoManager undoRedoManager = new UndoRedoManager(modelManager);
        undoRedoManager.start();
        modelManager.addModelDataListener(undoRedoManager);

        String description = "description";
        DateTime start = DateTime.now();
        List<EventSource> events = new ArrayList<>(modelManager.getEvents());
        events.add(EventSource.newBuilder(description, start).build());
        modelManager.setModelData(new ModelData(events, modelManager.getTasks()));

        assertDoesNotThrow(undoRedoManager::undo);

        assertDoesNotThrow(undoRedoManager::redo);
        assertEquals(1, undoRedoManager.getUndoIndex());
        // ModelData in ModelManager should no longer match the previous version in UndoRedoManager
        assertFalse(undoRedoManager.getUndoStateList().get(0).equals(modelManager.getModelData()));
        // Test whether ModelData in ModelManager has been updated to match the previous version in UndoRedoManager
        // (Value-equality but not reference equality)
        assertTrue(undoRedoManager.getUndoStateList().get(1) != modelManager.getModelData());
        assertEquals(undoRedoManager.getUndoStateList().get(1), modelManager.getModelData());
    }

}
