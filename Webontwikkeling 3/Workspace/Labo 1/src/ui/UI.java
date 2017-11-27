package ui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import domain.Person;

public class UI {
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		String url = "jdbc:postgresql://gegevensbanken.khleuven.be:51617/1TX37?currentSchema=r0613805_administration";
		Properties properties = new Properties();
		
		properties.setProperty("user", "r0613805");
		properties.setProperty("password", "Mvdh123456");
		properties.setProperty("ssl", "true");
		properties.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		
		Class.forName("org.postgresql.Driver");
		Connection connection = DriverManager.getConnection(url, properties);
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery("SELECT * FROM person;");
		System.out.println("test");
		while(result.next()) {
			System.out.println("test2");

			String userid = result.getString(1);
			String firstName = result.getString(2);
			String lastName = result.getString(3);
			String email = result.getString(4);
			String password = result.getString(5);
			Person person = new Person(userid, email, password, firstName, lastName);
			System.out.println(person);
		}
		
		statement.close();
		connection.close();
	}
}
