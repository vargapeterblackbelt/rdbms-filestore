package hu.blackbelt.rdbms.filestore;

import com.google.common.io.ByteStreams;
import org.apache.sling.commons.mime.MimeTypeService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.Mockito.when;

@ContextConfiguration("/context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DefaultFilestoreTest{

    private InputStream data;
    private static final String FILENAME = "test.txt";
    private static final String TEXT_PLAIN = "text/plain";

    @Autowired
    @InjectMocks
    DefaultFilestoreService target;

    @Mock
    MimeTypeService mimeTypeServiceMock;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        data = this.getClass().getClassLoader().getResourceAsStream("test.txt");
    }

    @Test
    public void testPutAndGetWithNullMimeType() throws IOException {

        when(mimeTypeServiceMock.getExtension("text/plain")).thenReturn("txt");
        when(mimeTypeServiceMock.getMimeType(endsWith(".txt"))).thenReturn("text/plain");

        String fileId = target.put(this.getClass().getClassLoader().getResourceAsStream("test.txt"), "test.txt", null);
        assertThat(target.exists(fileId), equalTo(true));
        assertThat(target.getFileName(fileId), equalTo("test.txt"));
        assertThat(target.getMimeType(fileId), equalTo("text/plain"));
        assertThat(new String(ByteStreams.toByteArray(target.get(fileId))), equalTo("test"));
    }

    @Test
    public void testPutAndGetWithNullMimeTypeAndFileName() throws IOException {
        when(mimeTypeServiceMock.getExtension("application/octetstream")).thenReturn("bin");
        when(mimeTypeServiceMock.getMimeType(endsWith(".bin"))).thenReturn("application/octetstream");

        String fileId = target.put(this.getClass().getClassLoader().getResourceAsStream("test.txt"), null, null);
        assertThat(target.exists(fileId), equalTo(true));
        assertThat(target.getFileName(fileId), equalTo(fileId + ".bin"));
        assertThat(target.getMimeType(fileId), equalTo("application/octetstream"));
        assertThat(new String(ByteStreams.toByteArray(target.get(fileId))), equalTo("test"));
    }

    @Test
    public void testPutAndGetWithMimeTypeAndNullFileName() throws IOException {
        when(mimeTypeServiceMock.getExtension("text/plain")).thenReturn("txt");
        when(mimeTypeServiceMock.getMimeType(endsWith(".txt"))).thenReturn("text/plain");

        String fileId = target.put(this.getClass().getClassLoader().getResourceAsStream("test.txt"), null, "text/plain");
        assertThat(target.exists(fileId), equalTo(true));
        assertThat(target.getFileName(fileId), equalTo(fileId + ".txt"));
        assertThat(target.getMimeType(fileId), equalTo("text/plain"));
        assertThat(new String(ByteStreams.toByteArray(target.get(fileId))), equalTo("test"));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGetIllegalFileId() {
        assertThat(target.exists("notexists"), equalTo(false));
        thrown.expect(IllegalArgumentException.class);
        target.getFileName("notexists");
        target.getMimeType("notexists");
        target.get("notexists");
    }

    @Test
    public void testGetNullFileId() {
        thrown.expect(IllegalArgumentException.class);
        target.exists(null);
        target.getFileName(null);
        target.getMimeType(null);
        target.get(null);
    }

}
