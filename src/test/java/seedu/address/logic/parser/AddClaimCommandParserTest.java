package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CASH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.AddClaimCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.commonvariables.Id;
import seedu.address.testutil.ClaimBuilder;

class AddClaimCommandParserTest {
    private AddClaimCommandParser parser = new AddClaimCommandParser();

    @Test
    void parse_allFieldsPresent_success() throws ParseException {
        Id.setIdCount(1);
        String validWithoutTags = new TestAddClaimCommand().withAllFields().build();
        String validWithTags = new TestAddClaimCommand().withAllFieldsAndTags().build();

        ClaimBuilder claimWithoutTags = new ClaimBuilder()
                .withName(TestAddClaimCommand.VALID_TEST_NAME)
                .withDescription(TestAddClaimCommand.VALID_TEST_DESCRIPTION)
                .withAmount(TestAddClaimCommand.VALID_TEST_CASH)
                .withDate(TestAddClaimCommand.VALID_TEST_DATE);
        ClaimBuilder claimWithTags = new ClaimBuilder()
                .withName(TestAddClaimCommand.VALID_TEST_NAME)
                .withDescription(TestAddClaimCommand.VALID_TEST_DESCRIPTION)
                .withAmount(TestAddClaimCommand.VALID_TEST_CASH)
                .withDate(TestAddClaimCommand.VALID_TEST_DATE)
                .withTags(TestAddClaimCommand.VALID_TEST_TAGS_FRIENDS)
                .withTags(TestAddClaimCommand.VALID_TEST_TAGS_COMPUTING);

        assertEquals(parser.parse(validWithoutTags),
                new AddClaimCommand(claimWithoutTags.withId("2").build()));
        assertEquals(parser.parse(validWithTags),
                new AddClaimCommand(claimWithTags.withId("3").build()));
    }

    @Test
    void parse_missingFields_failure() {
        TestAddClaimCommand template = new TestAddClaimCommand().withAllFields();
        String missingName = template.withName("").build();
        String missingDescription = template.withDescription("").build();
        String missingCash = template.withCash("").build();
        String missingDate = template.withDate("").build();

        assertThrows(ParseException.class, () -> parser.parse(missingName)); // no name prefix
        assertThrows(ParseException.class, () -> parser.parse(missingDescription)); // no description prefix
        assertThrows(ParseException.class, () -> parser.parse(missingCash)); // no cash prefix
        assertThrows(ParseException.class, () -> parser.parse(missingDate)); // no date prefix
    }

    class TestAddClaimCommand {

        static final String VALID_TEST_NAME = "Amy Bee";
        static final String VALID_TEST_DESCRIPTION = "Logistics";
        static final String VALID_TEST_CASH = "150.20";
        static final String VALID_TEST_DATE = "15-12-2019";
        static final String VALID_TEST_TAGS_FRIENDS = "friends";
        static final String VALID_TEST_TAGS_COMPUTING = "computing";

        String name = "";
        String description = "";
        String cash = "";
        String date = "";
        List<String> tags = new ArrayList<>();

        TestAddClaimCommand withAllFields() {
            return this.withName(VALID_TEST_NAME)
                    .withDescription(VALID_TEST_DESCRIPTION)
                    .withCash(VALID_TEST_CASH)
                    .withDate(VALID_TEST_DATE);
        }

        TestAddClaimCommand withAllFieldsAndTags() {
            return this.withName(VALID_TEST_NAME)
                    .withDescription(VALID_TEST_DESCRIPTION)
                    .withCash(VALID_TEST_CASH)
                    .withDate(VALID_TEST_DATE)
                    .withTag(VALID_TEST_TAGS_COMPUTING);
        }

        TestAddClaimCommand withName(String name) {
            this.name = name;
            return this;
        }

        TestAddClaimCommand withDescription(String description) {
            this.description = description;
            return this;
        }

        TestAddClaimCommand withCash(String cash) {
            this.cash = cash;
            return this;
        }

        TestAddClaimCommand withDate(String date) {
            this.date = date;
            return this;
        }

        TestAddClaimCommand withTag(String ... tags) {
            for (String s: tags) {
                this.tags.add(s);
            }
            return this;
        }

        String build() {
            StringBuilder s = new StringBuilder(" ");
            if (!name.equals("")) {
                s.append(PREFIX_NAME + name + " ");
            }
            if (!description.equals("")) {
                s.append(PREFIX_DESCRIPTION + description + " ");
            }
            if (!cash.equals("")) {
                s.append(PREFIX_CASH + cash + " ");
            }
            if (!date.equals("")) {
                s.append(PREFIX_DATE + date + " ");
            }
            if (!tags.isEmpty()) {
                for (String x: tags) {
                    s.append(PREFIX_TAG + x + " ");
                }
            }

            return s.toString();
        }
    }
}