package seedu.guilttrip.commons.util;

/**
 * Operates similarly to property change listener.
 */
public interface ListenerSupport {
    abstract void propertyChange(ObservableSupport.Evt evt);
}
