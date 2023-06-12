package com.aws.rds;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Handler1 implements RequestHandler<Course, Boolean> {

	@Override
	public Boolean handleRequest(Course input, Context context) {
		 Connection connection;
		 Logger logger = Logger.getLogger(Handler.class.getName());
		     String url = "jdbc:mysql://aws-test-database-1.clxkzbj7vrqw.ap-south-1.rds.amazonaws.com";
		    String username = "admin";
		      String password = "admin123";

		 
		 try {
               connection = DriverManager.getConnection(url, username, password);

	        } catch (SQLException e) {

	        }
		 
		 
		 try {
        	 connection = DriverManager.getConnection(url, username, password);

            Statement stmt = connection.createStatement();
            String sql = "DELETE FROM aws_rds.course WHERE id = '" + input.getId() + "' ";
            
            stmt.executeUpdate(sql);
            
//            stmt.executeUpdate("INSERT INTO aws_rds.course (id,name,code) values('" + input.getId() + "','" + input.getName() + "','"
//                    + input.getCode() + "')");
//            

            return true;
        } catch (Exception e) {
            logger.info(e.getLocalizedMessage());
        }
        return false;
		 
		
	}

}
