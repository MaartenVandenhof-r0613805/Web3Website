package db;
import domain.DomainException;
import domain.Person;
import domain.Role;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.bind.DataBindingException;


public class PersonDbDatabase {

		Properties properties = new Properties();
		String url = "jdbc:postgresql://gegevensbanken.khleuven.be:51617/1TX37?currentSchema=r0613805_test2";
		
		
		public PersonDbDatabase() {
			properties.setProperty("user", "r0613805");
			properties.setProperty("password", "Mvdh123456");
			properties.setProperty("ssl", "true");
			properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
			
			try {
				Class.forName("org.postgresql.Driver");
			} catch(ClassNotFoundException e) {
				System.out.println(e.getMessage());
				throw new DbException();
			}
		}
		
		
		
		public ArrayList<Person> getAll(){
			ArrayList<Person> list = new ArrayList<Person>();
			try(Connection connection = DriverManager.getConnection(url, properties);
			Statement statement = connection.createStatement();) {
				
				System.out.println("in the method");
				
				ResultSet result = statement.executeQuery("SELECT * FROM r0613805_test2.person");
				
				while(result.next()) {
					String id = result.getString("user_id");
					String firstName = result.getString("firstName");
					String lastName = result.getString("lastName");
					String email = result.getString("email");
					String password = result.getString("password");
					String role = result.getString("role");
					
					
					Person person = new Person(id, email, password, firstName, lastName, role);
					list.add(person);
				}
				
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
			
			return list;
		}
		
		public void add(Person person) {
			try (Connection connection = DriverManager.getConnection(url, properties);
					
			    Statement statement = connection.createStatement();) {
				statement.execute("INSERT INTO r0613805_test2.person (user_id, firstName, lastName, email, password, role) "
						+ "VALUES ('"+person.getUserid() + "','" + person.getFirstName() + "','" +   person.getLastName() 
						+"','"+person.getEmail() + "','" + person.getPassword() +"','"+ person.getRole() + "');");

			} catch (SQLException e) {
				throw new DbException(e.getMessage(), e);
			}
		}
		
		public void update(Person person) {
			try(Connection connection = DriverManager.getConnection(url, properties)){
				Statement statement = connection.createStatement();
				statement.executeQuery("UPDATE r0613805_test2.person SET (firstName, lastName, email, password, role) "
						+ "VALUES ('"+ person.getFirstName() + "','" + person.getLastName() 
						+ "','" + person.getEmail() +"','"+  person.getPassword()  +"','"+ person.getRole() + "');");
			} catch (SQLException e) {
				throw new DbException(e.getMessage());			}
		}
		
		public Person get(String id) {
			Person person = null;
			
			if(id == null || id.trim().isEmpty()) {
				throw new DomainException("Fill in an id");
			}
			
			try(Connection connection = DriverManager.getConnection(url, properties)){
				Statement statement = connection.createStatement();
				ResultSet result = statement.executeQuery("SELECT * FROM r0613805_test2.person WHERE user_id= '" + id + "'");
				
				while(result.next()) {
					String userid = result.getString("user_id");
					String firstName = result.getString("firstname");
					String lastName = result.getString("lastname");
					String email = result.getString("email");
					String password = result.getString("password");
					String role = result.getString("role");
					
					person = new Person(userid, email, password,firstName, lastName, role);
				}
				
				return person;
				
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
			
		}
		
		public void delete(String id) {
			try(Connection connection = DriverManager.getConnection(url, properties)){
				Statement statement = connection.createStatement();
				statement.executeUpdate("DELETE FROM r0613805_test2.person WHERE user_id = '"+id+"'");
			} catch (SQLException e) {
				throw new DbException(e.getMessage());			}
		}
		
		
	
}
