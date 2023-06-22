package dev.pack;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = DatabaseConnectionTest.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DatabaseConnectionTest {
    
    @Autowired
    DataSource dataSource;
    
    Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        connection = dataSource.getConnection();
    }

    @Test
    void shouldConnectToDatabaseConnection() throws Exception {

        boolean isConnect = connection.isValid(5);

        assertTrue(isConnect);
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (connection != null){
            connection.close();
        }
        
        assertTrue(connection.isClosed());
    }
}
