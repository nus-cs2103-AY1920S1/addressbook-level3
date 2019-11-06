package seedu.address.websocket.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.FileUtil;

class ImageQueryTest {

    @Test
    void execute_guradInvalidUrl() {
        assertDoesNotThrow(()->ImageQuery.execute("foo", "foo"));
    }

    @Test
    void execute_happyFlow() {
        String tempPath = "src/test/data/ApiStubsTest/TestImage.png";
        ImageQuery.execute("https://raw.githubusercontent.com/AY1920S1-CS2103T-T10-4/main/master/"
                + "src/main/resources/ApiResponseCache/GoogleMapsApi/GmapsImages/NUS_AS1.png",
                tempPath);
        assertTrue(FileUtil.isFileExists(Path.of("src/test/data/ApiStubsTest/TestImage.png")));
        File file = new File(tempPath);
        assertTrue(file.delete());
    }
}
