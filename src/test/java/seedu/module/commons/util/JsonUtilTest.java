package seedu.module.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;

import seedu.module.testutil.SerializableTestClass;
import seedu.module.testutil.TestUtil;

/**
 * Tests JSON Read and Write
 */
public class JsonUtilTest {

    private static final Path SERIALIZATION_FILE = TestUtil.getFilePathInSandboxFolder("serialize.json");

    @Test
    public void serializeObjectToJsonFile_noExceptionThrown() throws IOException {
        SerializableTestClass serializableTestClass = new SerializableTestClass();
        serializableTestClass.setTestValues();

        JsonUtil.serializeObjectToJsonFile(SERIALIZATION_FILE, serializableTestClass);

        assertEquals(FileUtil.readFromFile(SERIALIZATION_FILE), SerializableTestClass.JSON_STRING_REPRESENTATION);
    }

    @Test
    public void deserializeObjectFromJsonFile_noExceptionThrown() throws IOException {
        FileUtil.writeToFile(SERIALIZATION_FILE, SerializableTestClass.JSON_STRING_REPRESENTATION);

        SerializableTestClass serializableTestClass = JsonUtil
                .deserializeObjectFromJsonFile(SERIALIZATION_FILE, SerializableTestClass.class);

        assertEquals(serializableTestClass.getName(), SerializableTestClass.getNameTestValue());
        assertEquals(serializableTestClass.getListOfLocalDateTimes(), SerializableTestClass.getListTestValues());
        assertEquals(serializableTestClass.getMapOfIntegerToString(), SerializableTestClass.getHashMapTestValues());
    }

    @Test
    public void fromJsonString_noExceptionThrown() throws IOException {
        FileUtil.writeToFile(SERIALIZATION_FILE, SerializableTestClass.JSON_ARRAY_STRING_REPRESENTATION);

        List<SerializableTestClass> serializableTestClassList = JsonUtil.fromJsonString(
            FileUtil.readFromFile(SERIALIZATION_FILE), new TypeReference<List<SerializableTestClass>>(){});

        assertEquals(serializableTestClassList.get(0).getName(),
            SerializableTestClass.getNameTestValue());
        assertEquals(serializableTestClassList.get(0).getListOfLocalDateTimes(),
            SerializableTestClass.getListTestValues());
        assertEquals(serializableTestClassList.get(0).getMapOfIntegerToString(),
            SerializableTestClass.getHashMapTestValues());
        assertEquals(serializableTestClassList.get(1).getName(),
            SerializableTestClass.getNameTestValue());
        assertEquals(serializableTestClassList.get(1).getListOfLocalDateTimes(),
            SerializableTestClass.getListTestValues());
        assertEquals(serializableTestClassList.get(1).getMapOfIntegerToString(),
            SerializableTestClass.getHashMapTestValues());
    }

    //TODO: @Test jsonUtil_readJsonStringToObjectInstance_correctObject()

    //TODO: @Test jsonUtil_writeThenReadObjectToJson_correctObject()
}
