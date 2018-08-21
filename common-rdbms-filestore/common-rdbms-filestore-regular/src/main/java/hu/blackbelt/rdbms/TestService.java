package hu.blackbelt.rdbms;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.nativejdbc.C3P0NativeJdbcExtractor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.UUID;

import static java.sql.Types.VARCHAR;

@Service("testService")
public class TestService {

    @Autowired
    private BasicDataSource dataSource;

    @Autowired
    private DefaultLobHandler lobhandler;

    public String save(String name, InputStream data) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        final String id = UUID.randomUUID().toString();
//        Connection connection = jdbcTemplate.getNativeJdbcExtractor().getNativeConnection(dataSource.getConnection());
//        PreparedStatement statement = connection.prepareStatement("INSERT INTO FILES(ID, NAME, DATA) VALUES(?, ?, ?)");
//        statement.setString(1, id);
//        statement.setString(2, name);
//        statement.setBinaryStream(3, data);
        PreparedStatementCreatorFactory factory = new PreparedStatementCreatorFactory("INSERT INTO FILES(FILE_ID, NAME, DATA) VALUES(?, ?, ?)");
        factory.setNativeJdbcExtractor(new C3P0NativeJdbcExtractor());
//        PreparedStatementCreator statementCreator = factory.newPreparedStatementCreator();
//        PreparedStatement preparedStatement = statementCreator.createPreparedStatement(dataSource.getConnection());
//        preparedStatement.setString(1, id);
//        preparedStatement.setString(2, name);
//        preparedStatement.setBinaryStream(3, data);
//        preparedStatement.execute();
        InputStreamReader clobReader = new InputStreamReader(data);

        jdbcTemplate.execute(
                "INSERT INTO FILES(FILE_ID, NAME, DATA) VALUES(?, ?, ?)",
                new AbstractLobCreatingPreparedStatementCallback(lobhandler) {
                    protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
                        ps.setString(1, id);
                        ps.setString(2, name);
                        try {
                            ps.setBinaryStream(3, data, data.available());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
//                        try {
//                            lobCreator.setClobAsCharacterStream(ps, 3, clobReader, data.available());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        lobCreator.setBlobAsBinaryStream(ps, 3, blobIs, (int)blobIn.length());
                    }
                }
        );
        return id;
    }
}
