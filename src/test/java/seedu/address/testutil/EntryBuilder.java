package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Amount;
import seedu.address.model.person.Description;
import seedu.address.model.person.Entry;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class EntryBuilder {

    public static final String DEFAULT_DESCRIPTION = "Alice Pauline";
//    public static final String DEFAULT_PHONE = "85355255";
//    public static final String DEFAULT_EMAIL = "alice@gmail.com";
//    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final double DEFAULT_AMOUNT = 5.60;

    private Description desc;
//    private Phone phone;
//    private Email email;
//    private Address address;
    private Amount amt;
    private Set<Tag> tags;

    public EntryBuilder() {
        desc = new Description(DEFAULT_DESCRIPTION);
//        phone = new Phone(DEFAULT_PHONE);
//        email = new Email(DEFAULT_EMAIL);
//        address = new Address(DEFAULT_ADDRESS);
        amt = new Amount(DEFAULT_AMOUNT);
        tags = new HashSet<>();
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public EntryBuilder(Entry entryToCopy) {
        desc = entryToCopy.getDesc();
        amt = entryToCopy.getAmount();
//        phone = personToCopy.getPhone();
//        email = personToCopy.getEmail();
//        address = personToCopy.getAddress();
        tags = new HashSet<>(entryToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public EntryBuilder withDesc(String desc) {
        this.desc = new Description(desc);
        return this;
    }

    public EntryBuilder withAmt(double amt) {
        this.amt = new Amount(amt);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public EntryBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

//    /**
//     * Sets the {@code Address} of the {@code Person} that we are building.
//     */
//    public PersonBuilder withAddress(String address) {
//        this.address = new Address(address);
//        return this;
//    }
//
//    /**
//     * Sets the {@code Phone} of the {@code Person} that we are building.
//     */
//    public PersonBuilder withPhone(String phone) {
//        this.phone = new Phone(phone);
//        return this;
//    }
//
//    /**
//     * Sets the {@code Email} of the {@code Person} that we are building.
//     */
//    public PersonBuilder withEmail(String email) {
//        this.email = new Email(email);
//        return this;


    public Entry build() {
        return new Entry(desc, amt, tags);
    }

}
