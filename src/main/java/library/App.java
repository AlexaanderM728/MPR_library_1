package library;

import library.dao.repos.AuthorRepository;
import library.dao.repos.BookRepository;
import library.dao.repos.PersonRepository;
import library.dao.repos.ReservationItemRepository;
import library.dao.repos.ReservationRepository;

public class App 
{
    public static void main( String[] args )
    {
    	PersonRepository repository = new PersonRepository();
    	repository.createTable();
    	
    	ReservationRepository reservation_repository = new ReservationRepository();
    	reservation_repository.createTable();
    	
    	ReservationItemRepository reservation_item_repository = new ReservationItemRepository();
    	reservation_item_repository.createTable();
    	
    	BookRepository bookRepository = new BookRepository();
    	bookRepository.createTable();
    	
    	AuthorRepository authorRepository = new AuthorRepository();
    	authorRepository.createTable();
    	
        System.out.println( "koniec" );
    }
}
