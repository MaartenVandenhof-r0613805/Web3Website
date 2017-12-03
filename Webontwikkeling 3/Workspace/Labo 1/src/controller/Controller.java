package controller;

import java.awt.List;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import db.DbException;
import db.PersonDbDatabase;
import db.PersonDbInMemory;
import db.ProductDbInMemory;
import db.ProductSQLDatabase;
import domain.DomainException;
import domain.Person;
import domain.Product;
import domain.ShopService;


/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private ShopService databank = new ShopService();
	private ProductSQLDatabase productDb = new ProductSQLDatabase();
	private String css = "css/yellow.css";
	private static final long serialVersionUID = 1L;
	String index;
	String overview;
	String products;
	String addProduct;
	String signUp;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		frontController(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	protected void frontController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String doel;
		String action;
		
		
		if (request.getParameter("action") == null || request.getParameter("action").isEmpty()) {
			action = "index";
		} else {
			action = request.getParameter("action");
		}
		
		boolean exists = false;
		for(Cookie cookie : request.getCookies()) {
			if(cookie.getName().equals("actual")) {
				exists = true;
				System.out.println("Boolean word op true gezet met value " +cookie.getValue());
			}
		}
		
		if(!exists) {
			Cookie cookie = new Cookie("actual", "Controller");
			response.addCookie(cookie);
			System.out.println("Cookie with value Controller added");
		}
		
		switch(action) {
		case "overview":
			doel = Overview(request, response);
			break;
		case "index":
			this.index = "actual";
			this.overview = null;
			this.products = null;
			this.addProduct = null;
			this.products = null;
			this.signUp = null;

			doel = "index.jsp";
			break;
		case "signUp":
			doel = "signUp.jsp";
			break;
		case "voegToe":
			doel = voegToe(request, response);
			break;
		case "update":
			doel = update(request, response);
			break;
		case "Update":
			doel = updatePersonDB(request, response);
			break;
		case "updateProductToDB":
			doel = updateProductToDB(request, response);
		case "productOverview":
			doel = productOverview(request, response);
			break;
		case "addProduct":
			doel = "addProduct.jsp";
			break;
		case "addProductToDB":
			doel = addProduct(request, response);
			break;
		case "updateProduct":
			doel = updateProduct(request, response);
			break;
		case "removePerson":
			doel = removePerson(request, response);
			break;
		case "removeProduct":
			doel = removeProduct(request, response);
			break;
		case "login":
			doel = login(request, response);
			break;
		case "switchColor":
			doel = switchColor(request, response);
			break;
		default:
			this.index = "actual";
			this.overview = null;
			this.products = null;
			this.addProduct = null;
			this.products = null;
			this.signUp = null;

			doel = "index.jsp";
		}
		
		request.setAttribute("cssStyle", css);
		request.setAttribute("index", index);
		request.setAttribute("overview", overview);
		request.setAttribute("products", products);
		request.setAttribute("addProductsPage", addProduct);
		request.setAttribute("signUpPage", signUp);
		
		RequestDispatcher rd = request.getRequestDispatcher(doel);
		rd.forward(request, response);
	}

	private String Overview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			request.setAttribute("Databank", this.databank.getPersons());
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if (cookie.getName().equals("actual")) {
				cookie.setValue("overview");
				this.index = null;
				this.overview = "actual";
				this.products = null;
				this.addProduct = null;
				this.products = null;
				this.signUp = null;
			}
		}
		
		return "personoverview.jsp";
	}
	
	private String voegToe(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 ArrayList<String> errorLijst = new ArrayList<String>();
		
		
		Person persoon = new Person();
		System.out.println(request.getParameter("userid"));
		if (request.getParameter("action").equals("Update")) {
			this.databank.deletePerson(request.getParameter("userid"));
			System.out.println(request.getParameter("userid"));
		}
		setId(persoon, request, errorLijst);
		setFirstName(persoon, request, errorLijst);
		setLastName(persoon, request, errorLijst);
		setEmail(persoon, request, errorLijst);
		setPassword(persoon, request, errorLijst);		
		
		
		
		request.setAttribute("errors", errorLijst);
		
		if(errorLijst == null || errorLijst.isEmpty()) {
			addPerson(persoon, request, errorLijst);
			Cookie[] cookies = request.getCookies();
			for(Cookie cookie : cookies) {
				if (cookie.getName().equals("actual")) {
					cookie.setValue("Controller");
					this.index = "actual";
					this.overview = null;
					this.products = null;
					this.addProduct = null;
					this.products = null;
					this.signUp = null;
				}
			}
			return "index.jsp";
			
		} else {
			Cookie[] cookies = request.getCookies();
			for(Cookie cookie : cookies) {
				if (cookie.getName().equals("actual")) {
					cookie.setValue("signUp");
					this.index = null;
					this.overview = null;
					this.products = null;
					this.addProduct = null;
					this.products = null;
					this.signUp = "actual";
				}
			}
			return "signUp.jsp";
		}
		
	}
	
	private String updatePersonDB(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 ArrayList<String> errorLijst = new ArrayList<String>();
		
		
		Person persoon = new Person();
		
		setId(persoon, request, errorLijst);
		setFirstName(persoon, request, errorLijst);
		setLastName(persoon, request, errorLijst);
		setEmail(persoon, request, errorLijst);
		setPassword(persoon, request, errorLijst);		
		
		this.databank.updatePersons(persoon);
		request.setAttribute("errors", errorLijst);
		
		if(errorLijst == null || errorLijst.isEmpty()) {
			return Overview(request, response);
		} else {
			Cookie[] cookies = request.getCookies();
			for(Cookie cookie : cookies) {
				if (cookie.getName().equals("actual")) {
					cookie.setValue("signUp");
					this.index = null;
					this.overview = null;
					this.products = null;
					this.addProduct = null;
					this.products = null;
					this.signUp = "actual";
				}
			}

			return "signUp.jsp";
		}
		
	}
	
	private void setId(Person persoon, HttpServletRequest request, ArrayList<String> errorLijst){
		String ID = request.getParameter("userid");
		try {
			persoon.setUserid(ID);
		} catch(DomainException e) {
			errorLijst.add(e.getMessage());
		}
	}
	
	private void setFirstName(Person persoon, HttpServletRequest request, ArrayList<String> errorLijst){
		String firstName = request.getParameter("firstName");
		try {
			persoon.setFirstName(firstName);;
		} catch(DomainException e) {
			errorLijst.add(e.getMessage());
		}
	}
	
	private void setLastName(Person persoon, HttpServletRequest request, ArrayList<String> errorLijst){
		String lastName = request.getParameter("lastName");
		try {
			persoon.setLastName(lastName);;
		} catch(DomainException e) {
			errorLijst.add(e.getMessage());
		}
	}
	
	private void setEmail(Person persoon, HttpServletRequest request, ArrayList<String> errorLijst){
		String email = request.getParameter("email");
		try {
			persoon.setEmail(email);;
		} catch(DomainException e) {
			errorLijst.add(e.getMessage());
		}
	}
	
	private void setPassword(Person persoon, HttpServletRequest request, ArrayList<String> errorLijst){
		String password = request.getParameter("password");
		try {
			persoon.setPassword(password);;
		} catch(DomainException e) {
			errorLijst.add(e.getMessage());
		}
	}
	
	private void addPerson(Person persoon, HttpServletRequest request, ArrayList<String> errorLijst){
		
		try {
			this.databank.addPerson(persoon);
		} catch(DbException e) {
			errorLijst.add(e.getMessage());
		}
	}
	
	private String update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		Person persoon = databank.getPerson(id);
		request.setAttribute("person", persoon);
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if (cookie.getName().equals("actual")) {
				cookie.setValue("signUp");
				this.index = null;
				this.overview = null;
				this.products = null;
				this.addProduct = null;
				this.products = null;
				this.signUp = "actual";
			}
		}

		return "update.jsp";
	}
	
	private String productOverview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("ProductDatabank", productDb.getAll());
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if (cookie.getName().equals("actual")) {
				cookie.setValue("products");
				this.index = null;
				this.overview = null;
				this.products = "actual";
				this.addProduct = null;
				this.products = null;
				this.signUp = null;
			}
		}
		
		return "productoverview.jsp";
	}
	
	private String addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<String> errorLijst = new ArrayList<String>();
		
		
		Product product = new Product();
		
		
		try {
			setProductId(product, request, errorLijst);
		} catch(NumberFormatException e) {
			errorLijst.add(e.getMessage());
		}
		
		setName(product, request, errorLijst);
		setDescription(product, request, errorLijst);
		setPrice(product, request, errorLijst);
		setRating(product, request, errorLijst);
		
		
		
		
		request.setAttribute("producterrors", errorLijst);
		
		if(errorLijst == null || errorLijst.isEmpty()) {
			addProductToDatabse(product, request, errorLijst);
			return productOverview(request, response);
		} else {
			
			Cookie[] cookies = request.getCookies();
			for(Cookie cookie : cookies) {
				if (cookie.getName().equals("actual")) {
					cookie.setValue("addProduct");
					this.index = null;
					this.overview = null;
					this.products = null;
					this.addProduct = "actual";
					this.products = null;
					this.signUp = null;
				}
			}
			
			return "addProduct.jsp";
		}
	}
	
	private void setProductId(Product product, HttpServletRequest request, ArrayList<String> errorLijst){
		String id = request.getParameter("productid");
		
		try {
			
			product.setProductId(Integer.parseInt(id));
		} catch(DomainException e) {
			errorLijst.add(e.getMessage());
		}
	}
	
	private void setRating(Product product, HttpServletRequest request, ArrayList<String> errorLijst){
		String rating = request.getParameter("rating");
		
		try {
			
			Integer.parseInt(rating); 
			try {
				product.setRating(Integer.parseInt(rating)); 
			} catch(DomainException e) {
				errorLijst.add(e.getMessage());
			}
			
		} catch(NumberFormatException e) {
			errorLijst.add("Fill in a number");
		}
	}
	
	private void setName(Product product, HttpServletRequest request, ArrayList<String> errorLijst){
		String name = request.getParameter("name");
		try {
			product.setName(name);
		} catch(DomainException e) {
			errorLijst.add(e.getMessage());
		}
	}
	
	private void setDescription(Product product, HttpServletRequest request, ArrayList<String> errorLijst){
		String description = request.getParameter("description");
		try {
			product.setDescription(description);
		} catch(DomainException e) {
			errorLijst.add(e.getMessage());
		}
	}
	
	private void setPrice(Product product, HttpServletRequest request, ArrayList<String> errorLijst){
		String price = request.getParameter("price");
		try {
			product.setPrice(price);
		} catch(DomainException e) {
			errorLijst.add(e.getMessage());
		}
	}
	
	private void addProductToDatabse(Product product, HttpServletRequest request, ArrayList<String> errorLijst){
		
		try {
			this.productDb.add(product);
		} catch(DbException e) {
			errorLijst.add(e.getMessage());
		}
	}
	
	private String updateProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		System.out.println(request.getParameter("id"));
		
		Product product = this.productDb.get(id);
		request.setAttribute("product", product);
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if (cookie.getName().equals("actual")) {
				cookie.setValue("addProduct");
				this.index = null;
				this.overview = null;
				this.products = null;
				this.addProduct = "actual";
				this.products = null;
				this.signUp = null;
			}
		}
		return "updateProduct.jsp";
	}
	
	private String removePerson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		System.out.println(request.getParameter("id"));
		
		this.databank.deletePerson(id);
		return Overview(request, response);
	}
	
	private String removeProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		System.out.println(request.getParameter("id"));
		
		this.productDb.delete(Integer.parseInt(id));
		return productOverview(request, response);
	}
	
	private String updateProductToDB(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		ArrayList<String> errorLijst = new ArrayList<String>();
		
		
		Product product = new Product();
		
		
		
		setProductId(product, request, errorLijst);
		setName(product, request, errorLijst);
		setDescription(product, request, errorLijst);
		setPrice(product, request, errorLijst);
		setRating(product, request, errorLijst);
		
		
		this.productDb.update(product);
		
		request.setAttribute("producterrors", errorLijst);
		
		if(errorLijst == null || errorLijst.isEmpty()) {
			return productOverview(request, response);
		} else {
			Cookie[] cookies = request.getCookies();
			for(Cookie cookie : cookies) {
				if (cookie.getName().equals("actual")) {
					cookie.setValue("addProduct");
					this.index = null;
					this.overview = null;
					this.products = null;
					this.addProduct = "actual";
					this.products = null;
					this.signUp = null;
				}
			}
			return "addProduct.jsp";
		}
	}
	
	private String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		ArrayList<String> errorLijst = new ArrayList<String>();
		String email = request.getParameter("email");
		Person persoon = new Person();
		boolean correct = false;
		
		setEmail(persoon, request, errorLijst);
		setPassword(persoon, request, errorLijst);
		
		
		
		for(Person databankPerson : this.databank.getPersons()) {
			if(databankPerson.getEmail().equals(email)) {
				
				String password = request.getParameter("password");
				System.out.println("DatabankPassword " + databankPerson.getPassword());
			
				if(databankPerson.checkPassword(password)) {
					correct = true;
				} else {
					errorLijst.add("Wrong password");
				}
			}
		}
		
		request.setAttribute("errors", errorLijst);
		
		if(correct) {
			if(errorLijst.isEmpty() || errorLijst == null) {
				System.out.println("FINALLY");
				return Overview(request, response);
			}
		}
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if (cookie.getName().equals("actual")) {
				cookie.setValue("Controller");
				this.index = "actual";
				this.overview = null;
				this.products = null;
				this.addProduct = null;
				this.products = null;
				this.signUp = null;
			}
		}
		return "index.jsp";
	}
	
	private String switchColor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Cookie[] cookies = request.getCookies();
		boolean foundCookie = false;
		String actual = "Controller";
		String theCookie = "cookie";
		
		for(Cookie cookie : cookies) {
			if (cookie.getName().equals("color")){
				foundCookie = true;
				
				if(cookie.getValue().toString().equals("red")) {
					this.css = "css/yellow.css";
					cookie.setValue("yellow");
					System.out.println("zit in red");
				} else {
					this.css = "css/red.css";
					cookie.setValue("red");
					System.out.println("zit in yellow");

				}
				
				theCookie = cookie.getValue();
			} 
			
			if (cookie.getName().equals("actual")){
				actual = cookie.getValue();
			} 
		}
		
		System.out.println(theCookie);
		
		if(!foundCookie) {
			Cookie newCookie = new Cookie("color", "red");
			this.css = "css/red.css";
			response.addCookie(newCookie);
		}
		System.out.println(actual);
		 if (actual.equals("overview")) {
			return Overview(request, response);
		} if(actual.equals("products")){
			return productOverview(request, response);
		} if(actual.equals("addProduct")){
			return addProduct(request, response);
		} if(actual.equals("signUp")){
			return voegToe(request, response);
		}
		
		
		this.index = "actual";
		this.overview = null;
		this.products = null;
		this.addProduct = null;
		this.products = null;
		this.signUp = null;
		
		return "index.jsp";
				
		
	}
}
