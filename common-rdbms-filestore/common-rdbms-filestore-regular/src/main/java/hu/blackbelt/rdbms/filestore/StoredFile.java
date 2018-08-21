package hu.blackbelt.rdbms.filestore;

import lombok.Data;

@Data
class StoredFile {
    private String name;
    private String fileId;
    private String mimeType;
    private byte[] data;
    private long size;
}
