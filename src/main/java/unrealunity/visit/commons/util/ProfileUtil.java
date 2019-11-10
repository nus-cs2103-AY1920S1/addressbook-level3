package unrealunity.visit.commons.util;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Set;

import unrealunity.visit.model.person.Address;
import unrealunity.visit.model.person.Email;
import unrealunity.visit.model.person.Name;
import unrealunity.visit.model.person.Phone;
import unrealunity.visit.model.person.VisitList;
import unrealunity.visit.model.person.VisitReport;
import unrealunity.visit.model.tag.Tag;

/**
 * Helper functions for handling various {@code Person} data for displaying in {@code ProfileWindow}.
 */
public class ProfileUtil {

    // Strings for formatting of .txt Profile generation
    public static final String BREAKLINE = "=======================================================================\n";
    public static final String HEADER = BREAKLINE + "=========================== Patient Profile =============="
            + "=============\n" + BREAKLINE + "\n";
    public static final String FOOTER = BREAKLINE + BREAKLINE + BREAKLINE;

    // Header Strings for Patient Attributes
    public static final String HEADER_NAME = "Name:";
    public static final String HEADER_TAG = "Tags:";
    public static final String HEADER_PHONE = "Contact Number:";
    public static final String HEADER_EMAIL = "Email:";
    public static final String HEADER_ADDRESS = "Address:";
    public static final String HEADER_VISIT = "Visits:";

    // Header Strings for Visit Report
    public static final String HEADER_DIAG = "*Diagnosis*";
    public static final String HEADER_MED = "*Medication*";
    public static final String HEADER_REMARK = "*Remarks*";


    /**
     * Returns the {@code String} representation of a {@code Name} instance.
     *
     * @param name a {@code Name} instance to be shown as a {@code String}. Cannot be null
     * @return a {@code String} representing the {@code Name} instance
     */
    public static String stringifyName(Name name) {
        requireNonNull(name, "Name cannot be null.");

        return name.fullName;
    }

    /**
     * Returns the {@code String} representation of {@code Set&lt;Tag&gt;} instance.
     * Each {@code Tag} is separated with a ";" in the returned String representation.
     *
     * @param tagSet {@code Set} of {@code Tag} instances to be represented as a {@code String}. Cannot be null
     * @return a {@code String} representing the {@code Set&lt;Tag&gt;}
     */
    public static String stringifyTags(Set<Tag> tagSet) {
        requireNonNull(tagSet, "Set<Tag> cannot be null.");

        StringBuilder sb = new StringBuilder();

        if (tagSet.size() > 0) {
            for (Tag tag : tagSet) {
                sb.append(tag.tagName);
                sb.append("; ");
            }
        } else {
            return "-";
        }

        return sb.toString();
    }

    /**
     * Returns the {@code String} representation of a {@code Phone} instance.
     *
     * @param phone {@code Phone} instance to be shown as a {@code String}. Cannot be null
     * @return a {@code String} representing the {@code Phone} instance
     */
    public static String stringifyPhone(Phone phone) {
        requireNonNull(phone, "Phone cannot be null.");
        return phone.value;
    }

    /**
     * Returns the {@code String} representation of a {@code Email} instance. Returns "-" if blank.
     *
     * @param email {@code Email} instance to be shown as a {@code String}. Cannot be null
     * @return a {@code String} representing the {@code Email} instance
     */
    public static String stringifyEmail(Email email) {
        requireNonNull(email, "Email cannot be null.");
        return email.value;
    }

    /**
     * Returns the {@code String} representation of a {@code Address} instance.
     *
     * @param address {@code Address} instance to be shown as a {@code String}. Cannot be null
     * @return a {@code String} representing the {@code Address} instance
     */
    public static String stringifyAddress(Address address) {
        requireNonNull(address, "Address cannot be null.");
        return address.value;
    }

    /**
     * Returns the {@code String} representation of a {@code VisitList} instance. Each {@code VisitReport} in contained
     * in the {@code VisitList} is represented in a {@code String} separated with a breakline of '='.
     * Returns "-" if null.
     *
     * @param visitList {@code VisitList} instance to be shown as a {@code String}
     * @return a {@code String} representing the {@code VisitList} instance
     */
    public static String stringifyVisit(VisitList visitList) {
        if (visitList == null) {
            return "-";
        }

        // Get each all VisitReport instances
        ArrayList<VisitReport> visits = visitList.getRecords();

        if (visits.size() == 0) {
            return "-";
        }

        // Build String by iterating through all VisitReports
        StringBuilder output = new StringBuilder();

        for (VisitReport visit : visits) {
            output.append(BREAKLINE);
            output.append(stringifyVisitReport(visit));
            output.append("\n");
        }

        return output.toString();
    }

    /**
     * Returns the {@code String} representation of a {@code VisitReport}, detailing the full 'Diagnosis',
     * 'Medication' and 'Remarks' fields from the {@code VisitReport} instance.
     *
     * @param report {@code VisitReport} instance to be shown as a {@code String}
     * @return a {@code String} representing the {@code VisitReport} instance
     */
    public static String stringifyVisitReport(VisitReport report) {
        requireNonNull(report, "VisitReport cannot be null.");

        String date = report.date;
        String diagnosis = report.getDiagnosis();
        String medication = report.getMedication();
        String remarks = report.getRemarks();

        StringBuilder output = new StringBuilder();

        // Date Stamp for Visit
        output.append("[ Report on the " + date + " ]\n\n");

        // Appending all report details
        output.append(paragraph(HEADER_DIAG, diagnosis));
        output.append(paragraph(HEADER_MED, medication));
        output.append(paragraph(HEADER_REMARK, remarks));

        return output.toString();
    }

    /**
     * Returns the {@code String} representation of a {@code LocalDateTime} instance in the "dd-MM-yyyy HH-mm-ss"
     * format. The resulting {@code String} is file system friendly (No illegal characters for file naming).
     *
     * @param time {@code LocalDateTime} instance to be represented as a {@code String}.
     * @return {@code String} representation of a {@code LocalDateTime} instance in the "dd-MM-yyyy HH-mm-ss"
     * format.
     */
    public static String stringifyDate(LocalDateTime time) {
        requireNonNull(time, "LocalDateTime time cannot be null.");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss"); // Windows Unix naming safe
        return time.format(formatter);
    }

    /**
     * Builds content of a Profile Report given the following fields. The content is assembled as a {@code String}.
     *
     * @param name {@code String} representing the {@code Name} on the report.
     * @param tags {@code String} representing the {@code Tag} instances on the report.
     * @param phone {@code String} representing the {@code Phone} number on the report.
     * @param email {@code String} representing the {@code Email} on the report.
     * @param address {@code String} representing the {@code Address} on the report.
     * @param visits {@code String} representing the {@code VisitList} on the report.
     * @param time {@code String} representing the {@code LocalDateTime} in which the report is generated.
     * @return {@code String} representing entire content of the report.
     */
    public static String buildProfileReport(String name, String tags, String phone, String email,
                                            String address, String visits, String time) {
        assert StringUtil.allStringsNotNullOrBlank(name, tags, phone, email,
                address, visits, time) : "All fields should not be blank or null";
        CollectionUtil.requireAllNonNull(name, tags, phone, email, address, visits, time);

        StringBuilder output = new StringBuilder();

        output.append(HEADER);
        output.append("[Profile generated at " + time + ".]\n\n");

        output.append(paragraph(HEADER_NAME, name));
        output.append(paragraph(HEADER_TAG, tags));
        output.append(paragraph(HEADER_PHONE, phone));
        output.append(paragraph(HEADER_EMAIL, email));
        output.append(paragraph(HEADER_ADDRESS, address));

        output.append("\n");

        output.append(paragraph(HEADER_VISIT, visits));

        output.append(FOOTER);

        return output.toString();
    }

    /**
     * Arranges two {@code String} instances in a paragraph format. Where:<br>
     *     Example:<pre>
     *         header
     *         content
     *                  // Break line
     *         </pre>
     *
     * @param header {@code String} representing the header or title of the section/paragraph
     * @param content {@code String} representing the content of the section/paragraph
     * @return {@code String} representing a complete paragraph composed of {@code header} and {@code content}
     */
    public static String paragraph(String header, String content) {
        assert StringUtil.allStringsNotNullOrBlank(header, content) : "Header or content should not be null/blank";
        StringBuilder paragraph = new StringBuilder();

        paragraph.append(header + "\n");
        paragraph.append(content + "\n\n");

        return paragraph.toString();
    }

}
