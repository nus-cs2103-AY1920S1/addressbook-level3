package seedu.billboard.commons.observable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import seedu.billboard.commons.core.observable.ObservableData;
import seedu.billboard.commons.core.observable.Observer;


public class ObservableDataTest {

    @Mock
    Observer<Object> observerMock;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAndSetValue() {
        // primitive
        ObservableData<Integer> intObservable = new ObservableData<>();
        intObservable.setValue(2);
        assertEquals(2, intObservable.getValue());

        // nulls
        ObservableData<Object> nullObservable = new ObservableData<>();
        nullObservable.setValue(null);
        assertNull(nullObservable.getValue());

        // object identity
        ObservableData<Object> objectObservable = new ObservableData<>();
        Object object = new Object();
        objectObservable.setValue(object);
        assertEquals(object, objectObservable.getValue());

        // set same value, no updates
        objectObservable.observe(observerMock);
        objectObservable.setValue(object);

        verify(observerMock, times(1)).onChanged(any()); // 1 from observe only
    }

    @Test
    public void observe() {
        // null input
        ObservableData<Object> observable = new ObservableData<>();
        assertThrows(NullPointerException.class, () -> observable.observe(null));

        Object object1 = new Object();
        Object object2 = new Object();
        observable.setValue(object1);

        // Test behavior
        observable.observe(observerMock);
        verify(observerMock).onChanged(ArgumentMatchers.eq(object1));

        observable.setValue(object2);
        verify(observerMock).onChanged(ArgumentMatchers.eq(object2));
    }

    @Test
    public void observe_observeDuplicate_shouldOnlyReceiveOneUpdate() {
        ObservableData<String> observable = new ObservableData<>();

        String test = "test";
        observable.setValue(test);

        // add duplicate, should still only get one update for each observe call (2 total)
        observable.observe(observerMock);
        observable.observe(observerMock);
        verify(observerMock, times(2)).onChanged(ArgumentMatchers.eq(test));
    }

    @Test
    public void removeObserver() {
        ObservableData<Integer> observable = new ObservableData<>();

        observable.observe(observerMock);
        observable.removeObserver(observerMock);

        observable.setValue(5);
        verify(observerMock, never()).onChanged(5);
    }
}
