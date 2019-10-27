package seedu.address.importexport;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.importexport.exceptions.ExportingException;
import seedu.address.model.person.Person;
import seedu.address.storage.JsonAdaptedPerson;

/**
 * Reads and writes Java based Person objects to and fro .csv files
 */
public class CsvUtil {
    private static final Logger logger = LogsCenter.getLogger(CsvUtil.class);

    private static final String MESSAGE_OVERRIDING_FORBIDDEN = "File with given filename already exists,"
            + "overriding is not allowed";
    // Folder paths
    private static final String exportFolder = "exports";
    private static final Path importFilePath = Paths.get("imports/import.csv");

    //=========== Writing/Export functions =============================================================

    /**
     * Writes a list of Persons into a .csv file
     * @param persons
     * @return string of the path that was written to
     * @throws IOException
     */
    public static String writePersonsToCsv(List<Person> persons, String filename)
            throws IOException, ExportingException {
        String pathString = exportFolder + "/" + filename + ".csv";
        Path writePath = Paths.get(pathString);
        if (FileUtil.isFileExists(writePath)) {
            throw new ExportingException(MESSAGE_OVERRIDING_FORBIDDEN);
        }
        String toWrite = getCsvStringFromPersons(persons);
        FileUtil.createFile(writePath);
        logger.info("Writing export data to: " + writePath);
        FileUtil.writeToFile(writePath, toWrite);
        return pathString;
    }

    /**
     * Converts a List of person objects into a String to be written into a .csv file
     * @param persons
     * @return csv friendly string
     * @throws JsonProcessingException
     */
    private static String getCsvStringFromPersons(List<Person> persons) throws JsonProcessingException {
        CsvMapper mapper = new CsvMapper();
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true)
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .addMixIn(JsonAdaptedPerson.class, PersonMixIn.class);
        CsvSchema schema = mapper.schemaFor(JsonAdaptedPerson.class).withHeader().withArrayElementSeparator("\n");
        ObjectWriter writer = mapper.writer(schema);
        return writer.writeValueAsString(convertToCsvAdaptedList(persons));
    }

    /**
     * Converts a list of Person objects to a list of Jackson .csv friendly objects
     * @param persons
     * @return csv adapted persons
     */
    private static List<JsonAdaptedPerson> convertToCsvAdaptedList(List<Person> persons) {
        List<JsonAdaptedPerson> csvAdaptedList = new ArrayList<>();
        for (Person person : persons) {
            csvAdaptedList.add(convertToCsvAdaptedPerson(person));
        }
        return csvAdaptedList;
    }

    /**
     * Converts a Person object to a Jackson .csv friendly object
     * @param person
     * @return JsonAdaptedPerson
     */
    private static JsonAdaptedPerson convertToCsvAdaptedPerson(Person person) {
        return new JsonAdaptedPerson(person);
    }

    //=========== Reading/Import functions ===============================================================

    /**
     * Reads data from a .csv file and converts it to a list of {@Code Person} objects
     * @return a list of persons
     * @throws IOException
     * @throws IllegalValueException if illegal values exist in the .csv file
     */

    public static List<Person> readPersonsFromCsv() throws IOException, IllegalValueException {
        CsvMapper mapper = new CsvMapper();
        mapper.addMixIn(JsonAdaptedPerson.class, PersonMixIn.class)
                // when an empty field is encountered, create a null object
                .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        CsvSchema schema = mapper.schemaFor(JsonAdaptedPerson.class)
                    .withHeader()
                    // list elements are separated by a new line
                    .withArrayElementSeparator("\n");
        MappingIterator<JsonAdaptedPerson> iter = mapper.readerFor(JsonAdaptedPerson.class)
                    .with(schema)
                    .readValues(importFilePath.toFile());
        List<JsonAdaptedPerson> importedCsvPersons = iter.readAll();

        return convertImportedPersonsToPersonList(importedCsvPersons);
    }

    /**
     * Converts a {@Code JsonAdaptedPerson} read from a csv file into a {@Code Person} object
     */
    private static Person convertToPerson(JsonAdaptedPerson person) throws IllegalValueException {
        return person.toModelType();
    }

    /**
     * Converts a list of {@Code JsonAdaptedPerson} objects read from a csv file
     * into a list of {@Code Person} objects
     */
    private static List<Person> convertImportedPersonsToPersonList(List<JsonAdaptedPerson> persons)
            throws IllegalValueException {
        List<Person> newPersonList = new ArrayList<>();
        for (JsonAdaptedPerson person : persons) {
            newPersonList.add(convertToPerson(person));
        }
        return newPersonList;
    }

}
