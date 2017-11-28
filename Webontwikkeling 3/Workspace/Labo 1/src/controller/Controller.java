package controller;

import java.awt.List;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
	
	private static final long serialVersionUID = 1L;
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
			
		default:
			doel = "index.jsp";
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(doel);
		rd.forward(request, response);
	}

	private String Overview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			request.setAttribute("Databank", this.databank.getPersons());
		
			
		
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
			return "index.jsp";
			
		} else {
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
		return "update.jsp";
	}
	
	private String productOverview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("ProductDatabank", productDb.getAll());
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
			return "addProduct.jsp";
		}
	}
	
	private String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String email = request.getParameter("email");
		
		
		for(Person person : this.databank.getPersons()) {
			if(person.getEmail().equals(email)) {
				String password = request.getParameter("password");
				
			}
		}
		
		return productOverview(request, response);
	}
}
