package seedu.address.export;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import seedu.address.commons.util.FileUtil;
import seedu.address.model.person.Person;

/**
 * Exports Java based Person objects into a .csv file
 */
public class CsvUtil {
    // Temporary static file path
    private static final Path csvFile = Paths.get("exports/export.csv");

    /**
     * Writes a list of Persons into a .csv file
     * @param persons
     * @throws IOException
     */
    public static void writePersonsToCsv(List<Person> persons) throws IOException {
        FileUtil.createIfMissing(csvFile);
        FileUtil.writeToFile(csvFile, getCsvStringFromPersons(persons));
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
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        CsvSchema schema = mapper.schemaFor(CsvAdaptedPerson.class).withHeader();
        ObjectWriter writer = mapper.writer(schema);
        return writer.writeValueAsString(convertToCsvAdaptedList(persons));
    }

    /**
     * Converts a list of Person objects to a list of Jackson .csv friendly objects
     * @param persons
     * @return csv adapted persons
     */
    private static List<CsvAdaptedPerson> convertToCsvAdaptedList(List<Person> persons) {
        List<CsvAdaptedPerson> csvAdaptedList = new ArrayList<>();
        for (Person person : persons) {
            csvAdaptedList.add(convertToCsvAdaptedPerson(person));
        }
        return csvAdaptedList;
    }

    /**
     * Converts a Person object to a Jackson .csv friendly object
     * @param person
     * @return CsvAdaptedPerson
     */
    private static CsvAdaptedPerson convertToCsvAdaptedPerson(Person person) {
        return new CsvAdaptedPerson(person);
    }
}
