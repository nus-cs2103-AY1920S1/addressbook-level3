package seedu.billboard.commons.core.observable;

public interface Observer<T> {
    void onChanged(T t);
}
