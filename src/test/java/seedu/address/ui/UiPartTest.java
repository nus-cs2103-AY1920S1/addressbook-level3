package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.Assert.assertThrows;

import java.net.URL;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javafx.fxml.FXML;
import seedu.address.MainApp;

public class UiPartTest {

    private static final String MISSING_FILE_PATH = "UiPartTest/missingFile.fxml";
    private static final String MISSING_FILE_PATH_FINANCE = "UiPartTest/finance/missingFile.fxml";
    private static final String INVALID_FILE_PATH = "UiPartTest/invalidFile.fxml";
    private static final String INVALID_FILE_PATH_FINANCE = "UiPartTest/finance/invalidFile.fxml";
    private static final String VALID_FILE_PATH = "UiPartTest/validFile.fxml";
    private static final String VALID_FILE_PATH_FINANCE = "UiPartTest/validFile.fxml";
    private static final String VALID_FILE_WITH_FX_ROOT_PATH = "UiPartTest/validFileWithFxRoot.fxml";
    private static final String VALID_FILE_WITH_FX_ROOT_PATH_FINANCE = "UiPartTest/finance/validFileWithFxRoot.fxml";
    private static final TestFxmlObject VALID_FILE_ROOT = new TestFxmlObject("Hello World!");

    @TempDir
    public Path testFolder;

    @Test
    public void constructor_nullFileUrl_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TestCalendarUiPart<Object>((URL) null));
        assertThrows(NullPointerException.class, () -> new TestCalendarUiPart<Object>((URL) null, new Object()));

        assertThrows(NullPointerException.class, () -> new TestCapUiPart<Object>((URL) null));
        assertThrows(NullPointerException.class, () -> new TestCapUiPart<Object>((URL) null, new Object()));

        assertThrows(NullPointerException.class, () -> new TestQuizUiPart<Object>((URL) null));
        assertThrows(NullPointerException.class, () -> new TestQuizUiPart<Object>((URL) null, new Object()));

        assertThrows(NullPointerException.class, () -> new TestFinanceUiPart<Object>((URL) null));
        assertThrows(NullPointerException.class, () -> new TestFinanceUiPart<Object>((URL) null, new Object()));
    }

    @Test
    public void constructor_missingFileUrl_throwsAssertionError() throws Exception {
        URL missingFileUrl = new URL(testFolder.toUri().toURL(), MISSING_FILE_PATH);
        URL missingFileUrlFinance = new URL(testFolder.toUri().toURL(), MISSING_FILE_PATH_FINANCE);

        assertThrows(AssertionError.class, () -> new TestCalendarUiPart<Object>(missingFileUrl));
        assertThrows(AssertionError.class, () -> new TestCalendarUiPart<Object>(missingFileUrl, new Object()));

        assertThrows(AssertionError.class, () -> new TestCapUiPart<Object>(missingFileUrl));
        assertThrows(AssertionError.class, () -> new TestCapUiPart<Object>(missingFileUrl, new Object()));

        assertThrows(AssertionError.class, () -> new TestQuizUiPart<Object>(missingFileUrl));
        assertThrows(AssertionError.class, () -> new TestQuizUiPart<Object>(missingFileUrl, new Object()));

        assertThrows(AssertionError.class, () -> new TestFinanceUiPart<Object>(missingFileUrlFinance));
        assertThrows(AssertionError.class, () -> new TestFinanceUiPart<Object>(missingFileUrlFinance, new Object()));
    }

    @Test
    public void constructor_invalidFileUrl_throwsAssertionError() {
        URL invalidFileUrl = getTestFileUrl(INVALID_FILE_PATH);
        URL invalidFileUrlFinance = getTestFileUrl(INVALID_FILE_PATH_FINANCE);

        assertThrows(AssertionError.class, () -> new TestCalendarUiPart<Object>(invalidFileUrl));
        assertThrows(AssertionError.class, () -> new TestCalendarUiPart<Object>(invalidFileUrl, new Object()));

        assertThrows(AssertionError.class, () -> new TestCapUiPart<Object>(invalidFileUrl));
        assertThrows(AssertionError.class, () -> new TestCapUiPart<Object>(invalidFileUrl, new Object()));

        assertThrows(AssertionError.class, () -> new TestQuizUiPart<Object>(invalidFileUrl));
        assertThrows(AssertionError.class, () -> new TestQuizUiPart<Object>(invalidFileUrl, new Object()));

        assertThrows(AssertionError.class, () -> new TestFinanceUiPart<Object>(invalidFileUrlFinance));
        assertThrows(AssertionError.class, () -> new TestFinanceUiPart<Object>(invalidFileUrlFinance, new Object()));
    }

    @Test
    public void constructor_validFileUrl_loadsFile() {
        URL validFileUrl = getTestFileUrl(VALID_FILE_PATH);
        URL validFileUrlFinance = getTestFileUrl(VALID_FILE_PATH_FINANCE);

        assertEquals(VALID_FILE_ROOT, new TestCalendarUiPart<TestFxmlObject>(validFileUrl).getRoot());
        assertEquals(VALID_FILE_ROOT, new TestCapUiPart<TestFxmlObject>(validFileUrl).getRoot());
        assertEquals(VALID_FILE_ROOT, new TestQuizUiPart<TestFxmlObject>(validFileUrl).getRoot());
        assertEquals(VALID_FILE_ROOT, new TestFinanceUiPart<TestFxmlObject>(validFileUrlFinance).getRoot());
    }

    @Test
    public void constructor_validFileWithFxRootUrl_loadsFile() {
        URL validFileUrl = getTestFileUrl(VALID_FILE_WITH_FX_ROOT_PATH);
        URL validFileUrlFinance = getTestFileUrl(VALID_FILE_WITH_FX_ROOT_PATH_FINANCE);

        TestFxmlObject root = new TestFxmlObject();
        assertEquals(VALID_FILE_ROOT, new TestCalendarUiPart<TestFxmlObject>(validFileUrl, root).getRoot());
        assertEquals(VALID_FILE_ROOT, new TestCapUiPart<TestFxmlObject>(validFileUrl, root).getRoot());
        assertEquals(VALID_FILE_ROOT, new TestQuizUiPart<TestFxmlObject>(validFileUrl, root).getRoot());
        assertEquals(VALID_FILE_ROOT, new TestFinanceUiPart<TestFxmlObject>(validFileUrlFinance, root).getRoot());
    }

    @Test
    public void constructor_nullFileName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TestCalendarUiPart<Object>((String) null));
        assertThrows(NullPointerException.class, () -> new TestCalendarUiPart<Object>((String) null, new Object()));

        assertThrows(NullPointerException.class, () -> new TestCapUiPart<Object>((String) null));
        assertThrows(NullPointerException.class, () -> new TestCapUiPart<Object>((String) null, new Object()));

        assertThrows(NullPointerException.class, () -> new TestQuizUiPart<Object>((String) null));
        assertThrows(NullPointerException.class, () -> new TestQuizUiPart<Object>((String) null, new Object()));

        assertThrows(NullPointerException.class, () -> new TestFinanceUiPart<Object>((String) null));
        assertThrows(NullPointerException.class, () -> new TestFinanceUiPart<Object>((String) null, new Object()));
    }

    @Test
    public void constructor_missingFileName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TestCalendarUiPart<Object>(MISSING_FILE_PATH));
        assertThrows(NullPointerException.class, () -> new TestCalendarUiPart<Object>(MISSING_FILE_PATH, new Object()));

        assertThrows(NullPointerException.class, () -> new TestCapUiPart<Object>(MISSING_FILE_PATH));
        assertThrows(NullPointerException.class, () -> new TestCapUiPart<Object>(MISSING_FILE_PATH, new Object()));

        assertThrows(NullPointerException.class, () -> new TestQuizUiPart<Object>(MISSING_FILE_PATH));
        assertThrows(NullPointerException.class, () -> new TestQuizUiPart<Object>(MISSING_FILE_PATH, new Object()));

        assertThrows(NullPointerException.class, () -> new TestFinanceUiPart<Object>(MISSING_FILE_PATH_FINANCE));
        assertThrows(NullPointerException.class, () ->
                new TestFinanceUiPart<Object>(MISSING_FILE_PATH_FINANCE, new Object()));
    }

    @Test
    public void constructor_invalidFileName_throwsAssertionError() {
        assertThrows(AssertionError.class, () -> new TestCalendarUiPart<Object>(INVALID_FILE_PATH));
        assertThrows(AssertionError.class, () -> new TestCalendarUiPart<Object>(INVALID_FILE_PATH, new Object()));

        assertThrows(AssertionError.class, () -> new TestCapUiPart<Object>(INVALID_FILE_PATH));
        assertThrows(AssertionError.class, () -> new TestCapUiPart<Object>(INVALID_FILE_PATH, new Object()));

        assertThrows(AssertionError.class, () -> new TestQuizUiPart<Object>(INVALID_FILE_PATH));
        assertThrows(AssertionError.class, () -> new TestQuizUiPart<Object>(INVALID_FILE_PATH, new Object()));

        //assertThrows(AssertionError.class, () -> new TestFinanceUiPart<Object>(MISSING_FILE_PATH_FINANCE));
        //assertThrows(AssertionError.class, () ->
        //        new TestFinanceUiPart<Object>(MISSING_FILE_PATH_FINANCE, new Object()));
    }

    private URL getTestFileUrl(String testFilePath) {
        String testFilePathInView = "/view/" + testFilePath;
        URL testFileUrl = MainApp.class.getResource(testFilePathInView);
        assertNotNull(testFileUrl, testFilePathInView + " does not exist.");
        return testFileUrl;
    }

    /**
     * UiPart used for testing Calendar feature.
     * It should only be used with invalid FXML files or the valid file located at
     * {@code VALID_FILE_PATH}.
     */
    private static class TestCalendarUiPart<T> extends seedu.address.ui.calendar.UiPart<T> {

        @FXML
        private TestFxmlObject validFileRoot; // Check that @FXML annotations work

        TestCalendarUiPart(URL fxmlFileUrl, T root) {
            super(fxmlFileUrl, root);
        }

        TestCalendarUiPart(String fxmlFileName, T root) {
            super(fxmlFileName, root);
        }

        TestCalendarUiPart(URL fxmlFileUrl) {
            super(fxmlFileUrl);
            assertEquals(VALID_FILE_ROOT, validFileRoot);
        }

        TestCalendarUiPart(String fxmlFileName) {
            super(fxmlFileName);
            assertEquals(VALID_FILE_ROOT, validFileRoot);
        }
    }

    /**
     * UiPart used for testing Cap feature.
     * It should only be used with invalid FXML files or the valid file located at
     * {@code VALID_FILE_PATH}.
     */
    private static class TestCapUiPart<T> extends seedu.address.ui.cap.UiPart<T> {

        @FXML
        private TestFxmlObject validFileRoot; // Check that @FXML annotations work

        TestCapUiPart(URL fxmlFileUrl, T root) {
            super(fxmlFileUrl, root);
        }

        TestCapUiPart(String fxmlFileName, T root) {
            super(fxmlFileName, root);
        }

        TestCapUiPart(URL fxmlFileUrl) {
            super(fxmlFileUrl);
            assertEquals(VALID_FILE_ROOT, validFileRoot);
        }

        TestCapUiPart(String fxmlFileName) {
            super(fxmlFileName);
            assertEquals(VALID_FILE_ROOT, validFileRoot);
        }
    }

    /**
     * UiPart used for testing Quiz feature.
     * It should only be used with invalid FXML files or the valid file located at
     * {@code VALID_FILE_PATH}.
     */
    private static class TestQuizUiPart<T> extends seedu.address.ui.quiz.UiPart<T> {

        @FXML
        private TestFxmlObject validFileRoot; // Check that @FXML annotations work

        TestQuizUiPart(URL fxmlFileUrl, T root) {
            super(fxmlFileUrl, root);
        }

        TestQuizUiPart(String fxmlFileName, T root) {
            super(fxmlFileName, root);
        }

        TestQuizUiPart(URL fxmlFileUrl) {
            super(fxmlFileUrl);
            assertEquals(VALID_FILE_ROOT, validFileRoot);
        }

        TestQuizUiPart(String fxmlFileName) {
            super(fxmlFileName);
            assertEquals(VALID_FILE_ROOT, validFileRoot);
        }
    }

    /**
     * UiPart used for testing Finance feature.
     * It should only be used with invalid FXML files or the valid file located at
     * {@code VALID_FILE_PATH}.
     */
    private static class TestFinanceUiPart<T> extends seedu.address.ui.finance.UiPart<T> {

        @FXML
        private TestFxmlObject validFileRoot; // Check that @FXML annotations work

        TestFinanceUiPart(URL fxmlFileUrl, T root) {
            super(fxmlFileUrl, root);
        }

        TestFinanceUiPart(String fxmlFileName, T root) {
            super(fxmlFileName, root);
        }

        TestFinanceUiPart(URL fxmlFileUrl) {
            super(fxmlFileUrl);
            assertEquals(VALID_FILE_ROOT, validFileRoot);
        }

        TestFinanceUiPart(String fxmlFileName) {
            super(fxmlFileName);
            assertEquals(VALID_FILE_ROOT, validFileRoot);
        }
    }

}
