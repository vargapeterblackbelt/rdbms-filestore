package hu.blackbelt.rdbms.filestore;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;

public interface FilestoreService {

    /**
     * Saves a file with the given <code>fileName</code> and <code>mimeType</code> to the data store. It returns the ID of the file which is used
     * to access.
     *
     * @param data
     * @param fileName
     * @param mimeType
     */
    String put(InputStream data, String fileName, String mimeType) throws IOException, SQLException;

    /**
     * Checks the existence of a file <code>fileId</code> (If any representation exists return true).
     * @param fileId
     * @throws IOException
     */
    boolean exists(String fileId) throws SQLException;

    /**
     * Fetches the file with the given <code>fileId</code>.
     *
     * @param fileId
     * @return
     */
    InputStream get(String fileId) throws IOException, SQLException;

    /**
     * Gets the mime type of the given fileId.
     *
     * @param fileId
     * @return
     * @throws IOException
     */
    String getMimeType(String fileId) throws IOException, SQLException;

    /**
     * Get the file name of the given fileId.
     * @param fileId
     * @return
     * @throws IOException
     */
    String getFileName(String fileId) throws IOException, SQLException;


    /**
     * Get the file size of the given fileId.
     * @param fileId
     * @return
     * @throws IOException
     */
    long getSize(String fileId) throws IOException, SQLException;

    /**
     * Get the create time of the given fileId.
     * @param fileId
     * @return
     * @throws IOException
     */
    LocalDateTime getCreateTime(String fileId) throws IOException;

    /**
     * Get absolute URL of file.
     * @return
     * @throws IOException
     */
    URL getAccessUrl(String fileId) throws IOException;


}
