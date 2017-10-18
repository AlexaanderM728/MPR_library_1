package library;

import library.dao.repos.AuthorRepository;
import library.dao.repos.BookRepository;
import library.dao.repos.AddressRepository;
import library.dao.repos.PersonRepository;
import library.dao.repos.ReservationItemRepository;
import library.dao.repos.ReservationRepository;
import library.dao.repos.UserRepository;
import library.domain.Address;
import library.domain.Author;
import library.domain.Book;
import library.domain.Person;
import library.domain.User;

public class App 
{
    public static void main( String[] args )
    {
    	PersonRepository repository = new PersonRepository();
    	repository.createTable();
    	
    	Person jan = new Person();
    	jan.setName("jan");
    	jan.setSurname("Kowalski");
    	
    	repository.add(jan);
    	
    	ReservationRepository reservation_repository = new ReservationRepository();
    	reservation_repository.createTable();
    	
    	ReservationItemRepository reservation_item_repository = new ReservationItemRepository();
    	reservation_item_repository.createTable();
    	
    	BookRepository bookRepository = new BookRepository();
    	bookRepository.createTable();
    	
    	Book book = new Book();
    	book.setTitle("Book1");
    	book.setPublisher("Publisher1");
    	book.setYear(2017);
    	book.setisAvailable(true);
    	
    	bookRepository.add(book);
    	
    	AuthorRepository authorRepository = new AuthorRepository();
    	authorRepository.createTable();
    	
    	AddressRepository repositoryAddress = new AddressRepository();
    	repositoryAddress.createTable();
    	
    	Address address= new Address();
    	address.setStreet("xxx ");
    	address.setCity("yyyy");
    	address.setPostCode("1111");
    	address.setCountry("qqqq");
    	address.setHouseNumber("20");
    	address.setLocalNumber("21");
    	address.setPhone("000 333 111");
    	repositoryAddress.add(address);
    	Author author = new Author();
    	author.setFirst_name("Name");
    	author.setLast_name("Surname");
    	
    	AddressRepository repositoryAdderess = new AddressRepository();
    	repository.createTable();
    	UserRepository repositoryUser = new UserRepository();
    	repositoryUser.createTable();
    	User user = new User();
    	user.setLogin("XXX");
    	user.setPassword("qwerty");
    	user.setEmail("xxx@.pjatk.pl");
    	repositoryUser.add(user);
    	
    	
    	
    	
        System.out.println( "koniec" );
    }
}
