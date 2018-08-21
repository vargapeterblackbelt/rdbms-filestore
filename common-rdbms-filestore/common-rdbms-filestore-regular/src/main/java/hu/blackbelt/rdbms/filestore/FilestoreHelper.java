package hu.blackbelt.rdbms.filestore;

public final class FilestoreHelper {
    public static final String TABLE_NAME = "FILESTORE";
    public static final String FILE_ID_FIELD = "FILE_ID";
    public static final String FILENAME_FIELD = "FILENAME";
    public static final String MIME_TYPE_FIELD = "MIME_TYPE";
    public static final String DATA_FIELD = "DATA";
    public static final String SIZE_FIELD = "SIZE";
    public static final String CREATE_TIME_FIELD = "CREATE_TIME";

    public static final String VARCHAR256 = "VARCHAR(256)";
    public static final String BYTEA = "BYTEA";
    public static final String BIGINT = "BIGINT";
    public static final String DATE = "DATE";

    public static final String NOT_FOUND_MESSAGE = "No file found with the given id.";

    public static final String CREATE_TABLE_IF_NOT_EXISTS =
            "CREATE TABLE IF NOT EXISTS FILESTORE (" +
                    "FILE_ID VARCHAR(256) UNIQUE PRIMARY KEY," +
                    "FILENAME VARCHAR(256)," +
                    "MIME_TYPE VARCHAR(256)," +
                    "SIZE BIGINT," +
                    "CREATE_TIME TIMESTAMP," +
                    "DATA BYTEA" +
                    ");";

    public static final String INSERT_INTO =
            "INSERT INTO FILESTORE(FILE_ID, FILENAME, MIME_TYPE, SIZE, CREATE_TIME, DATA)" +
                    "VALUES(?, ?, ?, ?, ?, ?);";

    public static String countString(String fileId) {
        return "SELECT COUNT(*) FROM FILESTORE WHERE FILE_ID = '" + fileId + "'";
    }

    public static String readString(String fileId, String colName) {
        return "SELECT "+ colName +" FROM FILESTORE WHERE FILE_ID = '" + fileId + "'";
    }
    private FilestoreHelper(){};
}
