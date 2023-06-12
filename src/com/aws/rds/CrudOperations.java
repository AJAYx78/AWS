package com.aws.rds;



import java.sql.*;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;


public class CrudOperations implements RequestHandler<Map<String, Object>, Object> 

{

	private final String dbUrl = "jdbc:mysql://aws-test-database-1.clxkzbj7vrqw.ap-south-1.rds.amazonaws.com/aws_rds";
    private final String username = "admin";
    private final String password = "admin123";
    
  
        public Object handleRequest(Map<String, Object> input, Context context) {
            // Extract input parameters from the input map
            String operation = (String) input.get("operation");
            Map<String, Object> data = (Map<String, Object>) input.get("data");
            
            // Call appropriate CRUD operation method based on operation type
            
            String message = "";
            
            switch (operation) {
                case "create":
                	Course course=new Course();
                	course.setId((int) data.get("id"));
                	course.setName ((String) data.get("name"));
                	course.setCode((String) data.get("code"));
                     create(course);
                    
                    message = "course inserted succesfully";
                    
                    break;
                case "read":
                	int id = (int) data.get("id");
                    read(id);
                    break;
                case "update":
                    
                	id = (int) data.get("id");
                    String name = (String) data.get("name");
                    String code = (String) data.get("code");
                    update(id, name,code);
                    
                    message = "course updated successfully";
                    
                    break;
                    
                case "delete":
                    id = (int) data.get("id");
                    delete(id);
                    message = "course  deleted successfully";
                break;

                
                default:
                	throw new IllegalArgumentException("Invalid operation type");
                	
                	
            }
            
            // Return a response object if needed
            context.getLogger().log(message);
            return null;
        }
        
    
        
        
        
        
    
        public void create(Course course) {
            try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO course (id, name, code) VALUES (?, ?, ?)")) {
                preparedStatement.setInt(1, course.getId());
                preparedStatement.setString(2, course.getName());
                preparedStatement.setString(3, course.getCode());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
            
          
        
        
        
        
        public void read(int id) {
            try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM course WHERE id = ?")) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        System.out.println(resultSet.getInt("id") + ", " + resultSet.getString("name") + ", " + resultSet.getString("code"));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        
        
        public void update(int id, String name, String code) {
            try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
                    PreparedStatement preparedStatement = connection.prepareStatement("UPDATE course SET name = ?, code = ? WHERE id = ?")) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, code);
                preparedStatement.setInt(3, id); // change 5 to 3
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        
        
        public void delete(int id) {
            try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
                    PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM course WHERE id = ?")) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    
    
  
    
}
