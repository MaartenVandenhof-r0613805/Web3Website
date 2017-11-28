package domain;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person {
	private String userid;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private Role role;

	
	public Person(String userid, String email, String password, String firstName, String lastName, String role) {
		setUserid(userid);
		setEmail(email);
		setPassword(password);
		setFirstName(firstName);
		setLastName(lastName);
		setRole(role);
		
	}
	public Person(String userid, String email, String password, String firstName, String lastName) {
		setUserid(userid);
		setEmail(email);
		setPassword(password);
		setFirstName(firstName);
		setLastName(lastName);
		setRole("customer");
	}
	
	public Person() {
		setRole("customer");
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		if(userid.isEmpty() || userid == null || userid.trim().isEmpty()){
			System.out.println(" isEmpty ");
			throw new DomainException("No userid given");
		}
		this.userid = userid;
	}

	public void setEmail(String email) {
		if(email.isEmpty()){
			throw new DomainException("No email given");
		}
		String USERID_PATTERN = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern p = Pattern.compile(USERID_PATTERN);
		Matcher m = p.matcher(email);
		if (!m.matches()) {
			throw new DomainException("Email not valid");
		}
		this.email = email;
	}

	
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public boolean isCorrectPassword(String password) {
		if(password.isEmpty()){
			throw new DomainException("No password given");
		}
		return getPassword().equals(password);
	}

	public void setPassword(String password) {
		if(password.isEmpty()){
			throw new DomainException("No password given");
		}
		
		this.password = hashPassword(password);;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if(firstName.isEmpty()){
			throw new DomainException("No firstname given");
		}
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		if(lastName.isEmpty()){
			throw new DomainException("No last name given");
		}
		this.lastName = lastName;
	}
	
	private String hashPassword(String password){
		String passwordHashed = null;
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("SHA-512");
			byte[] hashedBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
			BigInteger digestAsBigInteger = new BigInteger(1, hashedBytes);
			passwordHashed = digestAsBigInteger.toString(16);
			return passwordHashed;
		} catch (NoSuchAlgorithmException e) {
			
		}		
		return passwordHashed;
	}
	
	public boolean checkPassword(String password) {
		boolean check = false;
		System.out.println("CheckedPassword " + hashPassword(password));
		if(this.password.equals(hashPassword(hashPassword(password)))) {
			
			check = true;
		}
		
		return check;
	}
	
	private void setRole(String string) {
		if(string.isEmpty()){
			throw new DomainException("No role name given");
		}
		this.role = Role.valueOf(string.toUpperCase());
	}
	
	public String getRole() {
		return this.role.toString();
	}
	
	
	
	@Override
	public String toString(){
		return getFirstName() + " " + getLastName() + ": " + getUserid() + ", " + getEmail();
	}	
}
