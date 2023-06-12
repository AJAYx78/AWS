package com.aws.rds;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.aws.rds.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class Handler implements RequestHandler<Course, Boolean> {

    private Logger logger = Logger.getLogger(Handler.class.getName());

    static Connection connection;
    static {
        try {   

            
            String username = System.getenv("admin");

            String password = System.getenv("admin123");

            String URL = System.getenv("jdbc:mysql://localhost:3306/aws_rds?serverTimezone = UTC");

            connection = DriverManager.getConnection(URL, username, password);

        } catch (SQLException e) {

        }
    }

    @Override
    public Boolean handleRequest(Course input, Context context) {
        try {

            Statement stmt = connection.createStatement();
            stmt.executeUpdate("INSERT INTO aws_rds.course (name,code) values('" + input.getName() + "','"
                    + input.getCode() + "')");

            return true;
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage());
            
        }
        return false;
    }
}
