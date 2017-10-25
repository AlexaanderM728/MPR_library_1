package library.dao.repos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import library.domain.Reservation;
import library.domain.ReservationItem;

public class ReservationItemRepository {
	
	String url = "jdbc:hsqldb:hsql://localhost/workdb";
	
	Connection connection;
	
	private boolean tableExists;
	PreparedStatement insert;
	PreparedStatement count;
	PreparedStatement lastId;
	PreparedStatement selectPage;
	PreparedStatement update;
	PreparedStatement delete;
	
	public ReservationItemRepository(){
		
		try {
			
			connection = DriverManager.getConnection(url);
			
			insert = connection.prepareStatement(""
					+ "INSERT INTO reservation_item(reservation_id, book_id) VALUES (?,?)"
					+ "");
			count = connection.prepareStatement(""
					+"SELECT COUNT(*) FROM reservation_item"
					+""
					);
			lastId=connection.prepareCall(""
					+"SELECT MAX(id) from reservation_item"
					+""
					);
			selectPage = connection.prepareStatement(""
					+ "SELECT * FROM person OFFSET ? LIMIT ?"
					+ ""
					);
			delete = connection.prepareStatement(""
					+ "DELETE FROM reservation_item WHERE id=?"
					+ ""
					);
			update = connection.prepareStatement(""
					+ "UPDATE reservation_item SET (reservationId,bookId) = (?,?) WHERE id=?"
					+ ""
					);
					
					
			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			
			while(rs.next()){
				if(rs.getString("TABLE_NAME").equalsIgnoreCase("reservation_item"))
					tableExists=true;
				
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int lastId(){
		try {
			ResultSet rs = lastId.executeQuery();
			while(rs.next()){
			return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			}
		return 0;
		}
	public int count(){
		try {
			ResultSet rs = count.executeQuery();
			while(rs.next()){
			return rs.getInt(1);
			}
		} catch (SQLException e) {
		e.printStackTrace();
		}
			return 0;
		}
	public List<ReservationItem> getPage(int offset, int limit){
		List<ReservationItem> result = new ArrayList<ReservationItem>();
		try {
			selectPage.setInt(1, offset);
			selectPage.setInt(1, limit);
			ResultSet rs = selectPage.executeQuery();
			while(rs.next()){
			ReservationItem p = new ReservationItem();
			p.setId(rs.getInt("id"));
			p.setReservationId(rs.getInt("reservation_id"));
			p.setBookId(rs.getInt("book_id"));
			result.add(p);
		}
		} catch (SQLException e) {
		e.printStackTrace();
		}
			return result;
		}
	public void add(ReservationItem reservationItem ){
		
		try {
			insert.setInt(1, reservationItem.getReservation().getId());
			insert.setInt(2, reservationItem.getBook().getId());
			insert.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}

	public void update(ReservationItem reservationItem){

		try {

			update.setInt(1, reservationItem.getReservationId());
			update.setInt(2, reservationItem.getBookId());
			update.setInt(3, reservationItem.getId());
			update.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delete(ReservationItem reservationItem){

		try {
			delete.setInt(1, reservationItem.getId());
			delete.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createTable(){
		
		String sql = "CREATE TABLE reservation_item("
				+ "id bigint GENERATED BY DEFAULT AS IDENTITY,"
				+ "reservation_id bigint,"
				+ "book_id bigint"
				+ ")";
		
		try {
			Statement createTable = connection.createStatement();
			if(!tableExists)
				createTable.executeUpdate(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
