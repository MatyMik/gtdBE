package com.codebeforedawn.be;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class DatabaseCleaner {

    private final DataSource dataSource;

    public DatabaseCleaner(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void clean() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            // Temporarily disable foreign key checks
            statement.execute("SET session_replication_role = 'replica'");

            // Fetch all tables in the public schema
            ResultSet tables = connection.getMetaData().getTables(null, "public", "%", new String[]{"TABLE"});

            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                statement.execute("TRUNCATE TABLE " + tableName + " RESTART IDENTITY CASCADE");
            }

            // Re-enable foreign key checks
            statement.execute("SET session_replication_role = 'origin'");

        } catch (SQLException e) {
            throw new RuntimeException("Failed to clean database", e);
        }
    }
}
