//@@author nattanyz
package dream.fcard.gui.components;

/**
 * Represents a distinct part of the UI.
 * @param <T> Type of Node contained in the UI Component.
 */
public interface UiComponent<T> {
    // todo

    default void setBackgroundColour(String colour) {
        // todo: want to use setStyle, but method cannot be resolved
    }
}
