package seedu.address.export;

import java.util.Iterator;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Jackson .csv friendly version of (@link Person)
 */
@JsonPropertyOrder({"name", "phone", "email", "address", "tagged"})
public class CsvAdaptedPerson {

    @JsonUnwrapped
    private final Name name;
    @JsonUnwrapped
    private final Phone phone;
    @JsonUnwrapped
    private final Email email;
    @JsonUnwrapped
    private final Address address;
    @JsonProperty("tagged")
    private final Set<Tag> tagged;

    public CsvAdaptedPerson(Person source) {
        this.name = source.getName();
        this.phone = source.getPhone();
        this.email = source.getEmail();
        this.address = source.getAddress();
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
