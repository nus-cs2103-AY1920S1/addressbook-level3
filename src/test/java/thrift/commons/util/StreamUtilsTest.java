package thrift.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static thrift.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;


public class StreamUtilsTest {

    @Test
    public void iteratorNull_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> StreamUtils.asStream(null));
        assertThrows(NullPointerException.class, ()
            -> StreamUtils.asStream(null, true));
        assertThrows(NullPointerException.class, ()
            -> StreamUtils.asStream(null, false));
    }

    @Test
    public void convertedIteratorStreamMatchesOriginalStream_success() {
        List<Integer> testList = new ArrayList<>();
        testList.addAll(Arrays.asList(IntStream.range(1, 10).boxed().toArray(Integer[]::new)));

        Stream<Integer> testStream = StreamUtils.asStream(testList.iterator());
        Stream<Integer> actualStream = testList.stream();

        Iterator<Integer> testStreamIterator = testStream.iterator();
        Iterator<Integer> actualStreamIterator = actualStream.iterator();
        while (testStreamIterator.hasNext() && actualStreamIterator.hasNext()) {
            assertEquals(testStreamIterator.next(), actualStreamIterator.next());
        }
        assert !testStreamIterator.hasNext() && !actualStreamIterator.hasNext();
    }

}
