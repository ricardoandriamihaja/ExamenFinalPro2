package hei.com.examenfinalprog2.repository;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DatabaseConnection {

    public Connection getConnection() throws RuntimeException {
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/examen",
                    "Audrey",
                    ""
            );
        } catch (SQLException e) {
            System.out.println("Error while connecting to the database: " + e.getMessage());
            throw new RuntimeException("Ooops something went wrong: " + e.getMessage());
        }
    }
}

