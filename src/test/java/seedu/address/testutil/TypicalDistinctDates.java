/*
@@author shihaoyap
 */

package seedu.address.testutil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import seedu.address.model.distinctdate.DistinctDate;

/**
 * A utility class containing a list of {@code DistinctDates} objects to be used in tests.
 */
public class TypicalDistinctDates {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    // Manually added
    public static final DistinctDate DATE_10_10_2019 = new DistinctDateBuilder()
            .withDate(LocalDate.parse("10/10/2019", FORMATTER))
            .withList(Arrays.asList(new EventBuilder().withName("John 21st Birthday Party")
                    .withVenue("Orchard Hotel").withManpowerNeeded("5")
                    .withStartDate(LocalDate.parse("10/10/2019", FORMATTER))
                    .withEndDate(LocalDate.parse("15/10/2019", FORMATTER))
                    .withTags("celebration").build())).build();

    public static final DistinctDate DATE_12_10_2019 = new DistinctDateBuilder()
            .withDate(LocalDate.parse("12/10/2019", FORMATTER))
            .withList(Arrays.asList(new EventBuilder().withName("Music Competition")
                    .withVenue("123, Jurong West Ave 6, #08-111").withManpowerNeeded("5")
                    .withStartDate(LocalDate.parse("12/10/2019", FORMATTER))
                    .withEndDate(LocalDate.parse("13/10/2019", FORMATTER))
                    .withTags("free").build())).build();

    public static final DistinctDate DATE_13_10_2019 = new DistinctDateBuilder()
            .withDate(LocalDate.parse("13/10/2019", FORMATTER))
            .withList(Arrays.asList(new EventBuilder().withName("Music Competition")
                    .withVenue("123, Jurong West Ave 6, #08-111").withManpowerNeeded("5")
                    .withStartDate(LocalDate.parse("12/10/2019", FORMATTER))
                    .withEndDate(LocalDate.parse("13/10/2019", FORMATTER))
                    .withTags("free").build())).build();

    public static final DistinctDate DATE_15_10_2019 = new DistinctDateBuilder()
            .withDate(LocalDate.parse("15/10/2019", FORMATTER))
            .withList(Arrays.asList(new EventBuilder().withName("John 21st Birthday Party")
                    .withVenue("Orchard Hotel").withManpowerNeeded("5")
                    .withStartDate(LocalDate.parse("10/10/2019", FORMATTER))
                    .withEndDate(LocalDate.parse("15/10/2019", FORMATTER))
                    .withTags("celebration").build())).build();

    public static final DistinctDate DATE_05_12_2019 = new DistinctDateBuilder()
            .withDate(LocalDate.parse("05/12/2019", FORMATTER))
            .withList(Arrays.asList(new EventBuilder().withName("NUS Run")
                    .withVenue("NUS Utown Green").withManpowerNeeded("20")
                    .withStartDate(LocalDate.parse("05/12/2019", FORMATTER))
                    .withEndDate(LocalDate.parse("07/12/2019", FORMATTER))
                    .withTags("sports").build())).build();

    public static final DistinctDate DATE_07_12_2019 = new DistinctDateBuilder()
            .withDate(LocalDate.parse("07/12/2019", FORMATTER))
            .withList(Arrays.asList(new EventBuilder().withName("NUS Run")
                    .withVenue("NUS Utown Green").withManpowerNeeded("20")
                    .withStartDate(LocalDate.parse("05/12/2019", FORMATTER))
                    .withEndDate(LocalDate.parse("07/12/2019", FORMATTER))
                    .withTags("sports").build())).build();

    public static final DistinctDate DATE_31_12_2019 = new DistinctDateBuilder()
            .withDate(LocalDate.parse("31/12/2019", FORMATTER))
            .withList(Arrays.asList(new EventBuilder().withName("New Year Countdown")
                    .withVenue("Marina Bay Sands").withManpowerNeeded("20")
                    .withStartDate(LocalDate.parse("31/12/2019", FORMATTER))
                    .withEndDate(LocalDate.parse("01/01/2020", FORMATTER))
                    .withTags("fun").build())).build();

    public static final DistinctDate DATE_01_01_2020 = new DistinctDateBuilder()
            .withDate(LocalDate.parse("01/01/2020", FORMATTER))
            .withList(Arrays.asList(new EventBuilder().withName("New Year Countdown")
                    .withVenue("Marina Bay Sands").withManpowerNeeded("20")
                    .withStartDate(LocalDate.parse("31/12/2019", FORMATTER))
                    .withEndDate(LocalDate.parse("01/01/2020", FORMATTER))
                    .withTags("fun").build())).build();

    private TypicalDistinctDates() {} // prevents instantiation

}
