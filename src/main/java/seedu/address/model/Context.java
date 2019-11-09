package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.Optional;

import seedu.address.model.activity.Activity;
import seedu.address.model.person.Person;

/**
 * Represents an immutable application context.
 */
public class Context {

    private final Optional<Object> object;
    private final ContextType type;

    /**
     * Default constructor where context type is MAIN.
     */
    public Context() {
        this.object = Optional.empty();
        this.type = ContextType.LIST_CONTACT;
    }

    private Context(ContextType type) {
        this.object = Optional.empty();
        this.type = type;
    }

    /**
     * Constructor for a VIEW_ACTIVITY context.
     */
    public Context(Activity activity) {
        requireNonNull(activity);
        object = Optional.ofNullable(activity);
        type = ContextType.VIEW_ACTIVITY;
    }

    /**
     * Constructor for a VIEW_CONTACT context.
     */
    public Context(Person person) {
        requireNonNull(person);
        object = Optional.ofNullable(person);
        type = ContextType.VIEW_CONTACT;
    }

    /**
     * Factory constructor for a LIST_ACTIVITY context.
     */
    public static Context newListActivityContext() {
        return new Context(ContextType.LIST_ACTIVITY);
    }

    /**
     * Factory constructor for a LIST_CONTACT context.
     */
    public static Context newListContactContext() {
        return new Context(ContextType.LIST_CONTACT);
    }

    public ContextType getType() {
        return type;
    }

    public Optional<Activity> getActivity() {
        return object.filter(x -> type == ContextType.VIEW_ACTIVITY).map(x->(Activity) x);
    }

    public Optional<Person> getContact() {
        return object.filter(x -> type == ContextType.VIEW_CONTACT).map(x->(Person) x);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, object);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Context)) {
            return false;
        }

        Context con = (Context) other;
        return this.type == con.type
                && this.getActivity().equals(con.getActivity())
                && this.getContact().equals(con.getContact());
    }
}
