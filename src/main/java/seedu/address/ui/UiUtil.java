package seedu.address.ui;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNotEmpty;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Window;

/**
 * Contains utility methods used by the various *UiPart classes.
 */
public class UiUtil {
    /**
     * Schedules the {@code onSceneReady} code to run when the {@code targetNode} is added to a {@link Scene}
     *
     * @param targetNode   The {@link Node} whose {@link Node#sceneProperty()} to wait on.
     * @param onSceneReady The code to execute once the {@code targetNode}'s {@link Node#sceneProperty()} is ready.
     */
    public static void onSceneReady(final Node targetNode, final Consumer<Scene> onSceneReady) {
        requireAllNonNull(targetNode, onSceneReady);

        oncePropertyNotNull(targetNode.sceneProperty(), onSceneReady::accept);
    }

    /**
     * Schedules the {@code onWindowReady} code to run when the {@code targetNode} is added to a {@link Window}
     *
     * @param targetNode    The {@link Node} whose {@link Node#sceneProperty()} to wait on.
     * @param onWindowReady The code to execute once the {@code targetNode}'s {@link Node#sceneProperty()} is ready.
     */
    public static void onWindowReady(final Node targetNode, final Consumer<Window> onWindowReady) {
        requireAllNonNull(targetNode, onWindowReady);

        oncePropertyNotNull(targetNode.sceneProperty(), (scene) -> {
            oncePropertyNotNull(scene.windowProperty(), onWindowReady::accept);
        });
    }

    /**
     * Schedules the {@code onPropertyNotNull} code to run only once, when the {@code property}'s
     * {@link ObservableValue#getValue()} is not null.
     *
     * @param property          The {@link ObservableValue} whose {@link ObservableValue#getValue()} to wait on.
     * @param onPropertyNotNull The code to execute once the {@code property}'s {@link ObservableValue#getValue()} is
     *                          not null.
     */
    private static <T> void oncePropertyNotNull(final ObservableValue<T> property,
                                                final Consumer<T> onPropertyNotNull) {
        requireAllNonNull(property, onPropertyNotNull);

        final T value = property.getValue();
        if (value != null) {
            onPropertyNotNull.accept(value);
        } else {
            property.addListener((unused1, oldValue, newValue) -> {
                if (!(oldValue == null && newValue != null)) {
                    return;
                }
                onPropertyNotNull.accept(newValue);
            });
        }
    }

    /**
     * Listens for {@link KeyEvent#KEY_PRESSED} events from {@code node} and passes it to {@code keyEventConsumer}
     * if {@code eventPredicate} returns true.
     *
     * @param node             The {@link Node} where the {@link KeyEvent#KEY_PRESSED} events will occur.
     * @param eventPredicate   A predicate that checks if a {@link KeyEvent} should be
     *                         handled by {@code keyEventConsumer}.
     * @param keyEventConsumer The code to execute if the {@link KeyEvent#KEY_PRESSED} events
     *                         passes the {@code eventPredicate}.
     */
    private static void matchKeyEvents(final Node node,
                                       final Function<KeyEvent, Boolean> eventPredicate,
                                       final Consumer<KeyEvent> keyEventConsumer) {
        requireAllNonNull(node, eventPredicate, keyEventConsumer);

        node.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            final Boolean isInterestedEvent = eventPredicate.apply(keyEvent);
            if (!isInterestedEvent) {
                return;
            }

            keyEventConsumer.accept(keyEvent);
        });
    }

    /**
     * Redirects {@link KeyEvent#KEY_PRESSED} events from {@code eventSource} to {@code eventTarget}
     * if it is in the {@code keyCodes}.
     *
     * @param eventSource The {@link Node} where the original {@link KeyEvent#KEY_PRESSED} events will occur.
     * @param eventTarget The {@link Node} where the {@link KeyEvent#KEY_PRESSED} events will be redirected to.
     * @param keyCodes    The {@link KeyCode}s that will be redirected to the {@code eventTarget}.
     */
    public static void redirectKeyCodeEvents(final Node eventSource, final Node eventTarget,
                                             final KeyCode... keyCodes) {
        requireAllNonNull(eventSource, eventTarget, keyCodes);

        addKeyCodeListener(eventSource, keyEvent -> {
            keyEvent.consume();
            eventTarget.fireEvent(keyEvent);
        }, keyCodes);
    }

    /**
     * Schedules the {@code keyEventConsumer} code to run when a {@link KeyEvent#KEY_PRESSED} event happens on the
     * {@code node} and the key pressed is in the {@code interestedKeyCodes}.
     *
     * @param node               The {@link Node} where the {@link KeyEvent#KEY_PRESSED} events will occur.
     * @param interestedKeyCodes The {@link KeyCode}s that will trigger the {@code keyEventConsumer}.
     * @param keyEventConsumer   The code to execute if the {@link KeyCode} is in the {@code keyCodes}.
     */
    public static void addKeyCodeListener(final Node node, final Collection<KeyCode> interestedKeyCodes,
                                          final Consumer<KeyEvent> keyEventConsumer) {
        requireAllNonNull(node, keyEventConsumer, interestedKeyCodes);

        final Function<KeyEvent, Boolean> keyCodeMatcher = (keyEvent -> {
            return interestedKeyCodes.contains(keyEvent.getCode());
        });
        matchKeyEvents(node, keyCodeMatcher, keyEventConsumer);
    }

    /**
     * Schedules the {@code keyEventConsumer} code to run when a {@link KeyEvent#KEY_PRESSED} event happens on the
     * {@code node} and the key pressed is in the {@code interestedKeyCodes}.
     *
     * @param node               The {@link Node} where the {@link KeyEvent#KEY_PRESSED} events will occur.
     * @param keyEventConsumer   The code to execute if the {@link KeyCode} is in the {@code keyCodes}.
     * @param interestedKeyCodes The {@link KeyCode}s that will trigger the {@code keyEventConsumer}.
     */
    public static void addKeyCodeListener(final Node node, final Consumer<KeyEvent> keyEventConsumer,
                                          final KeyCode... interestedKeyCodes) {
        addKeyCodeListener(node, List.of(interestedKeyCodes), keyEventConsumer);
    }

    /**
     * Schedules the {@code keyEventConsumer} code to run when a {@link KeyEvent#KEY_PRESSED} event happens on the
     * {@code node} and the key pressed matches the {@code interestedKeyCode}.
     *
     * @param node              The {@link Node} where the {@link KeyEvent#KEY_PRESSED} events will occur.
     * @param interestedKeyCode The {@link KeyCode} that will trigger the {@code keyEventConsumer}.
     * @param keyEventConsumer  The code to execute if the {@link KeyCode} is in the {@code keyCodes}.
     */
    public static void addKeyCodeListener(final Node node, final KeyCode interestedKeyCode,
                                          final Consumer<KeyEvent> keyEventConsumer) {
        addKeyCodeListener(node, keyEventConsumer, interestedKeyCode);
    }

    /**
     * Checks if the given {@code point} is located within what the user's screen(s) can show.
     *
     * @param point The point to check.
     * @return Whether the given {@code point} can be displayed on a screen.
     */
    public static boolean isPointUserVisible(final Point2D point) {
        return isPointUserVisible(point, Bounds.ALL);
    }

    /**
     * Checks if the given {@code point} is both located within what the user's screen(s) can show and
     * the desired bound.
     *
     * @param point The point to check.
     * @param bound The screen bound that the point will be checked against.
     * @return Whether the given {@code point} can be displayed on a screen, within the desired bound.
     */
    public static boolean isPointUserVisible(final Point2D point, Bounds bound) {
        return isPointUserVisible(point, EnumSet.of(bound));
    }

    /**
     * Checks if the given {@code point} is both located within what the user's screen(s) can show and
     * the desired bounds.
     *
     * @param point       The point to check.
     * @param checkBounds The screen bounds that the point will be checked against.
     * @return Whether the given {@code point} can be displayed on a screen, within the desired bounds.
     */
    public static boolean isPointUserVisible(final Point2D point, final EnumSet<Bounds> checkBounds) {
        requireAllNonNull(point, checkBounds);
        requireAllNotEmpty(checkBounds);

        return Screen.getScreens().stream().anyMatch(screen -> {
            final Rectangle2D visualBounds = screen.getVisualBounds();
            boolean userVisible = true;

            if (userVisible && checkBounds.contains(Bounds.TOP)) {
                userVisible = visualBounds.getMinY() <= point.getY();
            }

            if (userVisible && checkBounds.contains(Bounds.RIGHT)) {
                userVisible = point.getX() <= visualBounds.getMaxX();
            }

            if (userVisible && checkBounds.contains(Bounds.BOTTOM)) {
                userVisible = point.getY() <= visualBounds.getMaxY();
            }

            if (userVisible && checkBounds.contains(Bounds.LEFT)) {
                userVisible = visualBounds.getMinX() <= point.getX();
            }

            return userVisible;
        });
    }

    /**
     * Represents the four basic UI layout bounds
     */
    public enum Bounds {
        TOP, RIGHT, BOTTOM, LEFT;

        public static final EnumSet<Bounds> HORIZONTAL = EnumSet.of(LEFT, RIGHT);
        public static final EnumSet<Bounds> VERTICAL = EnumSet.of(TOP, BOTTOM);
        public static final EnumSet<Bounds> ALL = EnumSet.allOf(Bounds.class);
    }
}
