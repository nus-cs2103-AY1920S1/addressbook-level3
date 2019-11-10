package budgetbuddy.logic.script;

import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;

import java.lang.reflect.Array;

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

    public ScriptObjectWrapper(Object[] varargs) {
        if (varargs != null && varargs.length > 0) {
            Object obj = varargs[0];
            if (obj instanceof ScriptObjectMirror) {
                so = (ScriptObjectMirror) obj;
                return;
            }
        }

        so = null;
    }

    /**
     * Gets the object corresponding to the key in the map if it is of the right type.
     */
    public <A> A get(String key, Class<A> clazz, boolean shouldThrow) {
        requireAllNonNull(key, clazz);
        if (!has(key)) {
            return null;
        }
        Object a = so.get(key);
        if (clazz.isInstance(a)) {
            return (A) a;
        } else if (a != null && shouldThrow) {
            throw new IllegalArgumentException(String.format(MESSAGE_WRONG_TYPE, key, clazz.getSimpleName(),
                    a.getClass().getSimpleName()));
        }

        return null;
    }

    /**
     * Gets the object corresponding to the key in the map if it is of the right type.
     */
    public <A> A get(String key, Class<A> clazz) {
        return get(key, clazz, true);
    }

    /**
     * Gets the array corresponding to the key in the map if it is of the right type.
     */
    public <A> A[] getArray(String key, Class<A> clazz, boolean shouldThrow) {
        requireAllNonNull(key, clazz);
        if (!has(key)) {
            return null;
        }
        Object a = so.get(key);
        if (a != null && a.getClass().isArray() && a.getClass().getComponentType().equals(clazz)) {
            // the array is already a Java array, just return it
            return (A[]) a;
        } else if (a instanceof ScriptObjectMirror) {
            // it's a JavaScript object
            ScriptObjectMirror som = (ScriptObjectMirror) a;
            if (som.isArray()) {
                // it's a JavaScript array
                int len = (int) som.get("length");
                A[] ret = (A[]) Array.newInstance(clazz, len);

                // manually take each element in the array and check its type
                for (int i = 0; i < len; ++i) {
                    Object elem = som.get(i);
                    if (elem == null) {
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

                return ret;
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

        return null;
    }

    /**
     * Gets the array corresponding to the key in the map if it is of the right type.
     */
    public <A> A[] getArray(String key, Class<A> clazz) {
        return getArray(key, clazz, true);
    }

    public Long getIntegral(String key) {
        if (!has(key)) {
            return null;
        }

        Integer retInt = get(key, Integer.class, false);
        if (retInt != null) {
            return retInt.longValue();
        }

        String retStr = get(key, String.class, false);
        if (retStr != null) {
            try {
                return Long.parseLong(retStr, 10);
            } catch (NumberFormatException e) {
                // silence
            }
        }

        throw new IllegalArgumentException(String.format(MESSAGE_WRONG_TYPE, "int, or a long in a String",
                so.get(key).getClass().getSimpleName()));
    }

    public boolean has(String key) {
        return so != null && so.containsKey(key);
    }
}
