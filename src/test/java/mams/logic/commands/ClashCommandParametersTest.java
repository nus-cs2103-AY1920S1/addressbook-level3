package mams.logic.commands;

import static mams.testutil.TypicalIndexes.INDEX_FIRST;
import static mams.testutil.TypicalIndexes.INDEX_SECOND;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import mams.commons.core.index.Index;



public class ClashCommandParametersTest {

    private ClashCommand.ClashCommandParameters params;

    @BeforeEach
    public void setUp() {
        params = new ClashCommand.ClashCommandParameters();
    }

    @Test
    public void equals() {

        params.setStudentIndex(INDEX_FIRST);

        // same object -> returns true
        assertTrue(params.equals(params));

        // different types -> returns false
        assertFalse(params.equals(0));

        // null -> returns false
        assertFalse(params.equals(null));

        params.setAppealIndex(INDEX_FIRST);
        params.setModuleIndices(INDEX_FIRST, INDEX_SECOND);
        params.setModuleCodes("cs1010", "cs1020");

        ClashCommand.ClashCommandParameters firstParams = new ClashCommand.ClashCommandParameters();
        firstParams.setAppealIndex(INDEX_FIRST);
        firstParams.setModuleIndices(INDEX_FIRST, INDEX_SECOND);
        firstParams.setModuleCodes("cs1010", "cs1020");
        firstParams.setStudentIndex(INDEX_FIRST);

        // same value -> returns true
        assertTrue(params.equals(firstParams));


        ClashCommand.ClashCommandParameters secondParams = new ClashCommand.ClashCommandParameters();
        secondParams.setAppealIndex(INDEX_SECOND);
        secondParams.setModuleIndices(INDEX_FIRST, INDEX_SECOND);
        secondParams.setModuleCodes("cs1010", "cs1020");
        secondParams.setStudentIndex(INDEX_FIRST);

        // different appeal index -> returns false
        assertFalse(params.equals(secondParams));

        // different module indices -> returns false
        secondParams.setAppealIndex(INDEX_FIRST);
        secondParams.setModuleIndices(INDEX_FIRST, INDEX_FIRST);
        assertFalse(params.equals(secondParams));

        // different module codes -> returns false
        secondParams.setModuleIndices(INDEX_FIRST, INDEX_SECOND);
        secondParams.setModuleCodes("cs1010", "cs2030");
        assertFalse(params.equals(secondParams));

        // different student index -> returns false
        secondParams.setModuleCodes("cs1010", "cs1020");
        secondParams.setStudentIndex(INDEX_SECOND);
        assertFalse(params.equals(secondParams));
    }

    @Test
    public void setter_setsCorrectFields() {

        // appeal index
        params.setAppealIndex(INDEX_FIRST);
        assertEquals(INDEX_FIRST, params.getAppealIndex().get());
        params.setAppealIndex(INDEX_SECOND);
        assertEquals(INDEX_SECOND, params.getAppealIndex().get());

        // module indices
        params.setModuleIndices(INDEX_FIRST, INDEX_SECOND);
        ArrayList<Index> moduleIndices = new ArrayList<>();
        moduleIndices.add(INDEX_FIRST);
        moduleIndices.add(INDEX_SECOND);
        assertEquals(moduleIndices, params.getModuleIndices().get());
        assertEquals(INDEX_FIRST, params.getFirstModuleIndex());
        assertEquals(INDEX_SECOND, params.getSecondModuleIndex());

        // module codes
        params.setModuleCodes("cs1010", "cs1020");
        ArrayList<String> moduleCodes = new ArrayList<>();
        moduleCodes.add("cs1010");
        moduleCodes.add("cs1020");
        assertEquals("cs1010", params.getFirstModuleCode());
        assertEquals("cs1020", params.getSecondModuleCode());

        // student index
        params.setStudentIndex(INDEX_FIRST);
        assertEquals(INDEX_FIRST, params.getStudentIndex().get());
        params.setStudentIndex(INDEX_SECOND);
        assertEquals(INDEX_SECOND, params.getStudentIndex().get());

    }
}
