package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.VisitList;
import seedu.address.model.person.VisitReport;
import seedu.address.model.tag.Tag;

/**
 * Helper functions for handling Person data for displaying in ProfileWindow.
 */
public class ProfileUtil {

    /**
     * Returns the String representation of the Person's Name attribute.
     * @param name a Name instance of a Person. Cannot be null
     * @return a String representing the Person's Name attribute
     */
    public static String stringifyName(Name name) {
        requireNonNull(name);

        return name.fullName;
    }

    /**
     * Returns the String representation of the tags associated with a Person from their &lt;SetTag&gt; attribute.
     * @param tagSet Set of Tag objects attributed to the Person instance called in setup. Cannot be null
     * @return a String representing all Tags in the Person's Tag attribute
     */
    public static String stringifyTags(Set<Tag> tagSet) {
        requireNonNull(tagSet);

        StringBuilder sb = new StringBuilder();

        if (tagSet.size() > 0) {
            for (Tag tag : tagSet) {
                sb.append(tag.tagName);
                sb.append("; ");
            }
        }

        return sb.toString();
    }

    /**
     * Returns the String representation of the Phone instance associated with a Person.
     * @param phone Phone attribute of the Person in called in setup. Cannot be null
     * @return a String representing the Person's Phone attribute
     */
    public static String stringifyPhone(Phone phone) {
        requireNonNull(phone);
        return phone.value;
    }

    /**
     * Returns the String representation of the Email instance associated with a Person.
     * @param email Email attribute of the Person in called in setup. Cannot be null
     * @return a String representing the Person's Email attribute
     */
    public static String stringifyEmail(Email email) {
        requireNonNull(email);
        return email.value;
    }

    /**
     * Returns the String representation of the Address instance associated with a Person.
     * @param address Phone attribute of the Person in called in setup. Cannot be null
     * @return a String representing the Person's Phone attribute
     */
    public static String stringifyAddress(Address address) {
        requireNonNull(address);
        return address.value;
    }

    /**
     * Returns the String representation of the VisitList instance associated with a Person.
     * Visits are separated with a line of '=', each block detailing the full 'Diagnosis',
     * 'Medication' and 'Remarks' fields from the VisitReport instance. Returns "-" if null.
     * @param visitList VisitList attribute of a Person containing VisitReports
     * @return a String representing the entire VisitList
     */
    public static String stringifyVisit(VisitList visitList) {
        if (visitList == null) {
            return "-";
        }

        String line = "==================================================================\n";
        ArrayList<VisitReport> visits = visitList.getRecords();

        if (visits.size() == 0) {
            return "-";
        }

        StringBuilder output = new StringBuilder();

        for (VisitReport visit : visits) {
            output.append(stringifyVisitReport(visit));
            output.append(line);
            output.append("\n");
        }

        return output.toString();
    }

    /**
     * Returns the String representation of a VisitReport, detailing the full 'Diagnosis',
     * 'Medication' and 'Remarks' fields from the VisitReport instance.
     * @param report the VisitReport instance to be represented. Cannot be null
     * @return a String representing the VisitReport
     */
    public static String stringifyVisitReport(VisitReport report) {
        requireNonNull(report);

        String date = report.date;
        String diagnosis = report.getDiagnosis();
        String medication = report.getMedication();
        String remarks = report.getRemarks();

        StringBuilder output = new StringBuilder();

        // [Report on the XX/XX/2XXX]
        output.append("[ Report on the " + date + "]\n\n");

        // *Diagnosis*: DIAGNOSIS
        output.append("*Diagnosis*:\n" + diagnosis + "\n\n");

        // *Medication prescribed*: MEDICATION
        output.append("*Medication prescribed*:\n" + medication + "\n\n");

        // *Remarks*: REMARKS
        output.append("*Remarks*:\n" + remarks + "\n\n");

        return output.toString();
    }

}
