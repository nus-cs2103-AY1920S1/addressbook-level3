package seedu.address.export;

import java.util.Iterator;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Jackson .csv friendly version of (@link Person)
 */
@JsonPropertyOrder({"name", "phone", "email", "address", "tagged"})
public class CsvAdaptedPerson {

    @JsonProperty("name")
    private final String name;
    @JsonProperty("phone")
    private final String phone;
    @JsonProperty("email")
    private final String email;
    @JsonProperty("address")
    private final String address;
    @JsonProperty("tagged")
    private final Set<Tag> tagged;

    public CsvAdaptedPerson(Person source) {
        this.name = source.getName().fullName;
        this.phone = source.getPhone().value;
        this.email = source.getEmail().value;
        this.address = source.getAddress().value;
        this.tagged = source.getTags();
    }

    /**
     * JsonGetter for unwrapping the set of tags
     */
    @JsonGetter("tagged")
    public String getTagStrings() {
        String splitter = "\n";
        StringBuilder sb = new StringBuilder();
        Iterator<Tag> iter = tagged.iterator();

        while (iter.hasNext()) {
            sb.append(iter.next().tagName);
            if (iter.hasNext()) {
                sb.append(splitter);
            }
        }

        return sb.toString();
    }

}
