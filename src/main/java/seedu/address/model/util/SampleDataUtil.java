package seedu.address.model.util;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.entity.PhoneNumber;
import seedu.address.model.entity.Sex;
import seedu.address.model.entity.worker.Photo;
import seedu.address.model.entity.worker.Worker;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;

//@@author shaoyi1997
/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Worker[] getSampleWorkers() {
        Date dateNow = null;
        try {
            dateNow = ParserUtil.parseDate("12/12/2019");
        } catch (ParseException e) {
            // should not get here
            LogsCenter.getLogger(SampleDataUtil.class).warning("Date incorrect in SampleDataUtil.java");
        }
        return new Worker[] {
            new Worker(new Name("Alex Yeoh"), new PhoneNumber("87438807"), Sex.MALE, "Working",
                dateNow, dateNow, "Cleaner", new Photo(Photo.PATH_TO_EXAMPLE_PHOTO)),
            new Worker(new Name("Bernice Yu"), new PhoneNumber("99272758"), Sex.FEMALE, "On paid leave",
                dateNow, dateNow, "Coroner", null),
            new Worker(new Name("Charlotte Oliveiro"), new PhoneNumber("93210283"), Sex.FEMALE, "Working",
                dateNow, dateNow, "Cleaner", new Photo(Photo.PATH_TO_EXAMPLE_PHOTO)),
            new Worker(new Name("David Li"), new PhoneNumber("91031282"), Sex.MALE, "Working",
                dateNow, dateNow, "Coroner assistant", null),
            new Worker(new Name("Irfan Ibrahim"), new PhoneNumber("92492021"), Sex.MALE,
                    "On paternity leave", dateNow, dateNow, "Cleaner", null),
            new Worker(new Name("Roy Balakrishnan"), new PhoneNumber("92624417"), Sex.MALE, "Working",
                dateNow, dateNow, "Cleaner", null)
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Worker sampleWorker : getSampleWorkers()) {
            sampleAb.addEntity(sampleWorker);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
//@@author
