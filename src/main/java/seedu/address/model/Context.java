package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.model.activity.Activity;
import seedu.address.model.person.Person;

/**
 * Represents an immutable application context.
 */
public class Context {

    /**
     * Represents the various types of contexts that can exist.
     */
    enum Type {
        MAIN,
        VIEW_CONTACT,
        VIEW_ACTIVITY,
        LIST_CONTACT,
        LIST_ACTIVITY;
    }

    private final Optional<Object> object;
    private final Type type;

    /**
     * Default constructor where context type is MAIN.
     */
    Context() {
        object = Optional.empty();
        type = Type.MAIN;
    }

    /**
     * Constructor for a VIEW_ACTIVITY context.
     */
    Context(Activity activity) {
        requireNonNull(activity);
        object = Optional.ofNullable(activity);
        type = Type.VIEW_ACTIVITY;
    }

    /**
     * Constructor for a VIEW_CONTACT context.
     */
    Context(Person person) {
        requireNonNull(person);
        object = Optional.ofNullable(person);
        type = Type.VIEW_CONTACT;
    }

    /**
     * Constructor for a LIST_ACTIVITY context.
     */
    Context(Activity... activityList) {
        requireNonNull(activityList);
        ArrayList<Activity> arr = new ArrayList<>();
        for (Activity a : activityList) {
            arr.add(a);
        }
        object = Optional.ofNullable(arr);
        type = Type.LIST_ACTIVITY;
    }

    /**
     * Constructor for a LIST_CONTACT context.
     */
    Context(Person... personList) {
        requireNonNull(personList);
        ArrayList<Person> arr = new ArrayList<>();
        for (Person p : personList) {
            arr.add(p);
        }
        object = Optional.ofNullable(arr);
        type = Type.LIST_CONTACT;
    }

    public Type getType() {
        return type;
    }

    public Optional<Activity> getActivity() {
        return object.filter(x -> type == Type.VIEW_ACTIVITY).map(x->(Activity) x);
    }

    public Optional<Person> getContact() {
        return object.filter(x -> type == Type.VIEW_CONTACT).map(x->(Person) x);
    }

    public Optional<List<Activity>> getActivityList() {
        return object.filter(x -> type == Type.LIST_ACTIVITY).map(x->(List<Activity>) x);
    }

    public Optional<List<Person>> getContactList() {
        return object.filter(x -> type == Type.LIST_CONTACT).map(x->(List<Person>) x);
    }
}
