package seedu.deliverymans.model.customer;

import static seedu.deliverymans.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Phone;
import seedu.deliverymans.model.Tag;

/**
 * Represents a Customer in the system.
 */
public class Customer {

    // Identity fields
    private final Name userName;
    private final Name name;
    private final Phone phone;
    private final Address address;

    // Data fields
    private final Set<Tag> tags;
    private final Map<Tag, Integer> totalTags;
    private final int noOfOrders;

    /**
     * Every field must be present and not null.
     */
    public Customer(Name userName, Name name, Phone phone, Address address) {
        requireAllNonNull(userName, phone);
        this.userName = userName;
        this.name = name;
        this.phone = phone;
        this.address = address;
        tags = Set.of();
        totalTags = Map.of();
        noOfOrders = 0;
    }

    /**
     * Constructor for SampleDataUtil.
     */
    public Customer(Name userName, Name name, Phone phone, Address address, Set<Tag> tags, int noOfOrders) {
        requireAllNonNull(userName, phone, address, tags);
        this.userName = userName;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.tags = Set.copyOf(tags);
        Map<Tag, Integer> totalTags = new HashMap<>();
        for (Tag tag : tags) {
            totalTags.put(tag, 1);
        }
        this.totalTags = Map.copyOf(totalTags);
        this.noOfOrders = noOfOrders;
    }

    /**
     * Constructor for saving to storage
     */
    public Customer(Name userName, Name name, Phone phone, Address address, Set<Tag> tags,
                    Map<Tag, Integer> totalTags, int noOfOrders) {
        requireAllNonNull(userName, phone, tags, address, totalTags);
        this.userName = userName;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.tags = Set.copyOf(tags);
        this.totalTags = Map.copyOf(totalTags);
        this.noOfOrders = noOfOrders;
    }

    public Name getUserName() {
        return userName;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Map<Tag, Integer> getTotalTags() {
        return Collections.unmodifiableMap(totalTags);
    }

    /**
     * Adds Customer's noOfOrders and adds the tags to {@code ObservableMap<Tag, Integer>} totalTags,
     * returning a new Customer as the result.
     */
    public Customer addOrder(Set<Tag> tags) {
        Map<Tag, Integer> totalTags = addTags(tags);
        Set<Tag> mainTags = getMainTags(totalTags);
        return new Customer(userName, name, phone, address, mainTags, totalTags, noOfOrders + 1);
    }

    /**
     * Reduces Customer's noOfOrders and deletes the tags from {@code ObservableMap<Tag, Integer>} totalTags,
     * returning a new Customer as the result.
     */
    public Customer deleteOrder(Set<Tag> tags) {
        Map<Tag, Integer> totalTags = deleteTags(tags);
        Set<Tag> mainTags = getMainTags(totalTags);
        return new Customer(userName, name, phone, address, mainTags, totalTags, noOfOrders - 1);
    }

    /**
     * Adds {@code Set<Tag>} tags into {@code ObservableMap<Tag, Integer>} totalTags and returns a new map.
     */
    private Map<Tag, Integer> addTags(Set<Tag> tags) {
        Map<Tag, Integer> totalTags = new HashMap<>(this.totalTags);
        for (Tag tag : tags) {
            Integer i = totalTags.get(tag);
            if (i != null) {
                totalTags.replace(tag, i, i + 1);
            } else {
                totalTags.put(tag, 1);
            }
        }
        return totalTags;
    }

    /**
     * Deletes {@code Set<Tag>} tags from totalTags and returns a new map.
     */
    private Map<Tag, Integer> deleteTags(Set<Tag> tags) {
        Map<Tag, Integer> totalTags = new HashMap<>(this.totalTags);
        for (Tag tag : tags) {
            Integer i = totalTags.get(tag);
            if (i == null) {

            } else if ((i - 1) == 0) {
                totalTags.remove(tag);
            } else {
                totalTags.replace(tag, i, i - 1);
            }
        }
        return totalTags;
    }

    /**
     *  Generates new tags for Customer depending on the number of occurrences of {@code Tag}.
     */
    private Set<Tag> getMainTags(Map<Tag, Integer> totalTags) {
        Set<Tag> newTags = new HashSet<>();
        if (!totalTags.isEmpty()) {
            List<Map.Entry<Tag, Integer>> list = new ArrayList<>(totalTags.entrySet());
            list.sort(Map.Entry.comparingByValue());
            newTags.add(list.get(list.size() - 1).getKey());
            if (list.size() > 1) {
                newTags.add(list.get(list.size() - 2).getKey());
            }
        }
        return newTags;
    }

    public Customer setNoOfOrders(int noOfOrders) {
        return new Customer(userName, name, phone, address, tags, totalTags, noOfOrders);
    }

    public int getNoOfOrders() {
        return noOfOrders;
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameCustomer(Customer otherCustomer) {
        if (otherCustomer == this) {
            return true;
        }

        return otherCustomer != null
                && otherCustomer.getUserName().equals(getUserName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Customer)) {
            return false;
        }

        Customer otherCustomer = (Customer) other;
        return otherCustomer.getUserName().equals(getUserName())
                && otherCustomer.getName().equals(getName())
                && otherCustomer.getPhone().equals(getPhone())
                && otherCustomer.getAddress().equals(getAddress())
                && otherCustomer.getTags().equals(getTags())
                && otherCustomer.getTotalTags().equals(getTotalTags())
                && otherCustomer.getNoOfOrders() == getNoOfOrders();
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(userName, name, phone, address, tags, totalTags, noOfOrders);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getUserName())
                .append(" Name: ")
                .append(getName().toString())
                .append(" Phone: ")
                .append(getPhone().toString())
                .append(" Address: ")
                .append(getAddress().toString())
                .append(" Favourite cuisine: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
