package seedu.address.commons.util;

import java.io.IOException;

import seedu.address.commons.exceptions.CopyException;

/**
 * Deep copies an object
 */
public class CopyUtil {
    /**
     * Returns a deep copy of the specified object. The copy is performed by serializing the target object with a stream
     * and then deserializing it.
     *
     * @param instance  the object to copy
     * @param <T>  the generic type of the object to copy
     * @return  the copied object
     * @throws CopyException  if an error occurs while copying
     */
    public static <T> T deepCopy(T instance) throws CopyException {
        try {
            return (T) JsonUtil.fromJsonString(JsonUtil.toJsonString(instance), instance.getClass());
        } catch (IOException e) {
            throw new CopyException("Error copying object", e);
        }
    }
}
