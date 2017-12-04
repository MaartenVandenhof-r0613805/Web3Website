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
	String page = "Controller";
	String color = "yellow";
	
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
			this.page = "Controller";
			doel = "index.jsp";
			break;
		case "signUp":
			Cookie[] cookies = request.getCookies();
			for(Cookie cookie : cookies) {
				if (cookie.getName().equals("actual")) {
					this.page = "signUp";
					this.index = null;
					this.overview = null;
					this.products = null;
					this.addProduct = null;
					this.products = null;
					this.signUp = "actual";
				}
			}
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
			Cookie[] cookiesP = request.getCookies();
			for(Cookie cookie : cookiesP) {
				if (cookie.getName().equals("actual")) {
					this.page = "addProduct";
					this.index = null;
					this.overview = null;
					this.products = null;
					this.addProduct = "actual";
					this.products = null;
					this.signUp = null;
				}
			}
			
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
			this.page = "Controller";
			this.index = "actual";
			this.overview = null;
			this.products = null;
			this.addProduct = null;
			this.products = null;
			this.signUp = null;

			doel = "index.jsp";
		}
		
		Cookie cookie = new Cookie("actual", this.page);
		response.addCookie(cookie);
		
		Cookie cookie2 = new Cookie("color", this.color);
		response.addCookie(cookie2);
		
		request.setAttribute("cssStyle", css);
		request.setAttribute("index", index);
		request.setAttribute("overview", overview);
		request.setAttribute("products", products);
		request.setAttribute("addProductPage", addProduct);
		request.setAttribute("signUpPage", signUp);
		
		RequestDispatcher rd = request.getRequestDispatcher(doel);
		rd.forward(request, response);
	}

	private String Overview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			request.setAttribute("Databank", this.databank.getPersons());
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if (cookie.getName().equals("actual")) {
				this.page = "overview";
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
					this.page = "Controller";
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
					this.page = "signUp";
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
					this.page = "signUp";
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
		} catch(Exception e) {
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
				this.page = "signUp";
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
				this.page = "products";
				this.index = null;
				this.overview = null;
				this.products = "actual";
				this.addProduct = null;
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
		
		
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if (cookie.getName().equals("actual")) {
				this.page = "addProduct";
				this.index = null;
				this.overview = null;
				this.products = null;
				this.addProduct = "actual";
				this.products = null;
				this.signUp = null;
			}
		}
		

		
		request.setAttribute("producterrors", errorLijst);
		
		if(errorLijst == null || errorLijst.isEmpty()) {
			addProductToDatabse(product, request, errorLijst);
			return productOverview(request, response);
		} else {
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
				this.page = "addProduct";
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
					this.page = "addProduct";
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
		String id = request.getParameter("id");
		boolean correct = false;
		boolean loggedIn = false;
		String doel = "index.jsp";
		
		try {
			
			Person user = this.databank.getPerson(id);
			String password = request.getParameter("password");
			
			correct = user.checkPassword(password);
			
			if(!correct) {
				throw new DomainException("Incorrect password");
			} else {
				loggedIn = true;
			}
			
		} catch(DomainException e) {
			errorLijst.add(e.getMessage());
		}
		
		
		request.setAttribute("errors", errorLijst);
		
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			if (cookie.getName().equals("actual")) {
				this.page = "Controller";
				this.index = "actual";
				this.overview = null;
				this.products = null;
				this.addProduct = null;
				this.products = null;
				this.signUp = null;
			}
		}
		
		if(loggedIn) {
			return Overview(request, response);
		} else {
			return doel;
		}
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
					this.color = "yellow";
					System.out.println("zit in red");
				} else {
					this.css = "css/red.css";
					this.color = "red";
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
			return "addProduct.jsp";
		} if(actual.equals("signUp")){
			return "signUp.jsp";
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
