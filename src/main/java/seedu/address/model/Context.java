package seedu.address.model;

import java.util.Optional;

import seedu.address.model.activity.Activity;
import seedu.address.model.person.Person;


/**
 * Represents an application context.
 */
public enum Context {
    MAIN,
    VIEW_CONTACT,
    VIEW_ACTIVITY,
    LIST_CONTACT,
    LIST_ACTIVITY;

    private final Optional<Object> model;

    Context() {
        model = Optional.empty();
    }

    Context(Object model) {
        this.model = Optional.ofNullable(model);
    }

    public Optional<Activity> getActivity() {
        return model.filter(x -> this == Context.VIEW_ACTIVITY).map(x->(Activity) x);
    }

    public Optional<Person> getContact() {
        return model.filter(x -> this == Context.VIEW_CONTACT).map(x->(Person) x);
    }
}
