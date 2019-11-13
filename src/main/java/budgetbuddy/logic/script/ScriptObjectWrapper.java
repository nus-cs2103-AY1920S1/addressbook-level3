package budgetbuddy.logic.script;

import static budgetbuddy.commons.util.AppUtil.getDateFormatter;
import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Optional;
import java.util.OptionalLong;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

/**
 * Wraps a Nashorn object into something more convenient to use from Java.
 */
@SuppressWarnings({"removal", "unchecked"})
public class ScriptObjectWrapper {
    public static final String MESSAGE_WRONG_TYPE =
            "Optional argument %s is of the wrong type (expected: %s, actual: %s)";
    public static final String MESSAGE_WRONG_ARRAY_TYPE =
            "Optional argument %s is of the wrong type (expected: %s[], actual: %s)";

    private final ScriptObjectMirror so;

    private ScriptObjectWrapper(ScriptObjectMirror so) {
        this.so = so;
    }

    /**
     * Creates a {@link ScriptObjectWrapper} from the varargs array argument of a script helper function.
     *
     * @throws IllegalArgumentException if more than one object is in the array
     */
    public static ScriptObjectWrapper fromOptionalVarargs(Object[] varargs) {
        if (varargs != null) {
            if (varargs.length == 1) {
                Object obj = varargs[0];
                if (obj instanceof ScriptObjectMirror) {
                    return new ScriptObjectWrapper((ScriptObjectMirror) obj);
                }
            } else if (varargs.length > 1) {
                throw new IllegalArgumentException("Too many optional arguments provided to function");
            }
        }

        return new ScriptObjectWrapper(null);
    }

    /**
     * Gets the object corresponding to the key in the map if it is of the right type.
     */
    public <A> Optional<A> get(String key, Class<A> clazz, boolean shouldThrow) {
        requireAllNonNull(key, clazz);
        if (!has(key)) {
            return Optional.empty();
        }
        Object a = so.get(key);
        if (clazz.isInstance(a)) {
            return Optional.of((A) a);
        } else if (a != null && shouldThrow) {
            throw new IllegalArgumentException(String.format(MESSAGE_WRONG_TYPE, key, clazz.getSimpleName(),
                    a.getClass().getSimpleName()));
        }

        return Optional.empty();
    }

    /**
     * Gets the object corresponding to the key in the map if it is of the right type.
     */
    public <A> Optional<A> get(String key, Class<A> clazz) {
        return get(key, clazz, true);
    }

    /**
     * Gets the array corresponding to the key in the map if it is of the right type.
     */
    public <A> Optional<A[]> getArray(String key, Class<A> clazz, boolean shouldThrow) {
        requireAllNonNull(key, clazz);
        if (!has(key)) {
            return Optional.empty();
        }
        Object a = so.get(key);
        if (a != null && a.getClass().isArray() && a.getClass().getComponentType().equals(clazz)) {
            // the array is already a Java array, just return it
            return Optional.of((A[]) a);
        } else if (a instanceof ScriptObjectMirror) {
            // it's a JavaScript object
            ScriptObjectMirror som = (ScriptObjectMirror) a;
            if (som.isArray()) {
                // it's a JavaScript array
                int len = (int) som.get("length");
                A[] ret = (A[]) Array.newInstance(clazz, len);

                // manually take each element in the array and check its type
                for (int i = 0; i < len; ++i) {
                    Object elem = som.getSlot(i);
                    if (elem == null || ScriptObjectMirror.isUndefined(elem)) {
                        ret[i] = null;
                        continue;
                    }
                    if (clazz.isInstance(elem)) {
                        ret[i] = (A) elem;
                        continue;
                    }
                    if (shouldThrow) {
                        throw new IllegalArgumentException(
                                String.format(MESSAGE_WRONG_ARRAY_TYPE, key, clazz.getSimpleName(),
                                        "mixed array of types including " + elem.getClass().getSimpleName()));
                    }
                }

                return Optional.ofNullable(ret);
            } else if (shouldThrow) {
                throw new IllegalArgumentException(
                        String.format(MESSAGE_WRONG_ARRAY_TYPE, key, clazz.getSimpleName(),
                                som.getClassName()));
            }
        }

        if (a != null && shouldThrow) {
            throw new IllegalArgumentException(
                    String.format(MESSAGE_WRONG_ARRAY_TYPE, key, clazz.getSimpleName(),
                            a.getClass().getSimpleName()));
        }

        return Optional.empty();
    }

    /**
     * Gets the array corresponding to the key in the map if it is of the right type.
     */
    public <A> Optional<A[]> getArray(String key, Class<A> clazz) {
        return getArray(key, clazz, true);
    }

    /**
     * Gets the integral value corresponding to the key in the map.
     * <p>
     * The value can be represented as an {@link Integer} or a {@link String}, if the value is too large
     * to fit in an <code>int</code>.
     */
    public OptionalLong getIntegral(String key) {
        if (!has(key)) {
            return OptionalLong.empty();
        }

        Optional<Integer> retInt = get(key, Integer.class, false);
        if (retInt.isPresent()) {
            return OptionalLong.of(retInt.get().longValue());
        }

        Optional<String> retStr = get(key, String.class, false);
        if (retStr.isPresent()) {
            try {
                return OptionalLong.of(Long.parseLong(retStr.get(), 10));
            } catch (NumberFormatException e) {
                // silence
            }
        }

        throw new IllegalArgumentException(String.format(MESSAGE_WRONG_TYPE, "int, or a long in a String",
                so.get(key).getClass().getSimpleName()));
    }

    /**
     * Gets the {@link LocalDate} corresponding to the key in the map.
     */
    public Optional<LocalDate> getDate(String key) {
        if (!has(key)) {
            return Optional.empty();
        }

        return get(key, LocalDate.class, false).or(() -> get("date", String.class)
                .map(dateStr -> LocalDate.parse(dateStr, getDateFormatter())));
    }

    public boolean has(String key) {
        return so != null && so.containsKey(key);
    }
}
