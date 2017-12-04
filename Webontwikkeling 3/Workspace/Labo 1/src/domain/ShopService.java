package domain;

import java.util.List;

import db.PersonDbDatabase;
import db.PersonDbInMemory;

public class ShopService {
	private PersonDbDatabase personDb = new PersonDbDatabase();

	public ShopService(){
	}
	
	public Person getPerson(String personId) {
		Person person = getPersonDb().get(personId);
		if (person == null) throw new DomainException("Invalid id");
		return person;
	}

	public List<Person> getPersons() {
		return getPersonDb().getAll();
	}

	public void addPerson(Person person) {
		getPersonDb().add(person);
	}

	public void updatePersons(Person person) {
		getPersonDb().update(person);
	}

	public void deletePerson(String id) {
		getPersonDb().delete(id);
	}

	private PersonDbDatabase getPersonDb() {
		return personDb;
	}
}
