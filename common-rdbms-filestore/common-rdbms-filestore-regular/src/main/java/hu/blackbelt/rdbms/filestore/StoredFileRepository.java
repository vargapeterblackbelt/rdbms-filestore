package hu.blackbelt.rdbms.filestore;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoredFileRepository extends CrudRepository<StoredFile, String> {

}
