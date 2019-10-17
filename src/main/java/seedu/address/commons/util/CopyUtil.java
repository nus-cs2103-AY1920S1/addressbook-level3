package seedu.address.commons.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.CopyError;

/**
 * Deep copies an object
 */
public class CopyUtil {

    /**
     * Returns a deep copy of the specified object.
     *
     * @param instance  the object to copy
     * @param <T>  the generic type of the object to copy
     * @return  the copied object
     * @throws CopyError  if an error occurs while copying
     */
    public static <T> T deepCopy(T instance) throws CopyError {
        if (instance instanceof Serializable) {
            return (T) deepCopySerializable((Serializable) instance);
        }
        return deepCopyGeneric(instance);
    }

    /**
     * Returns a deep copy of the specified object. The copy is performed by using Jackson to serialize and then
     * deserialize the target object. Jackson may have issues with some classes so
     * {@link #deepCopySerializable(Serializable)} is preferred if the object is {@link Serializable}.
     *
     * @param instance  the object to copy
     * @param <T>  the generic type of the object to copy
     * @return  the copied object
     * @throws CopyError  if an error occurs while copying
     */
    private static <T> T deepCopyGeneric(T instance) throws CopyError {
        try {
            return (T) JsonUtil.fromJsonString(JsonUtil.toJsonString(instance), instance.getClass());
        } catch (IOException e) {
            throw new CopyError("Error copying object", e);
        }
    }

    /**
     * Returns a deep copy of the specified serializable object. The copy is performed by serializing the target object
     * with a stream and then deserializing it.
     *
     * @param serializable  the object to copy
     * @param <T>  class that implements Serializable
     * @return  the copied object
     * @throws CopyError  if an error occurs while copying
     */
    private static <T extends Serializable> T deepCopySerializable(T serializable) throws CopyError {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(serializable);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new CopyError("Error copying serializable object", e);
        }
    }

    /**
     * Returns a deep copy of the specified {@code ObservableList}. The copy is identical to the original except for
     * the listeners which will not be carried over. Any changes to the copied list or its elements will not affect
     * the original and vice versa.
     */
    public static <E> ObservableList<E> deepCopyOfObservableList(ObservableList<E> list) {
        List<E> listCopy = new ArrayList<>();
        for (E item : list) {
            listCopy.add(deepCopy(item));
        }
        return FXCollections.observableArrayList(listCopy);
    }
}
