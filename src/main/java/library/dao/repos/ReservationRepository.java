package library.dao.repos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import library.domain.Person;
import library.domain.Reservation;
import library.domain.ReservationItem;

public class ReservationRepository {
	
	String url = "jdbc:hsqldb:hsql://localhost/workdb";
	
	Connection connection;
	
	private boolean tableExists;
	PreparedStatement insert;
	PreparedStatement count;
	PreparedStatement lastId;
	PreparedStatement selectPage;
	PreparedStatement delete;
	PreparedStatement update;
	
	public ReservationRepository(){
		
		try {
			
			connection = DriverManager.getConnection(url);
			
			insert = connection.prepareStatement(""
					+ "INSERT INTO reservation(reservation_date, retrieval_date, real_date) VALUES (?,?,?)"
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
					+ "SELECT * FROM reservation_item OFFSET ? LIMIT ?"
					+ "");
			delete = connection.prepareStatement(""
					+ "DELETE FROM reservation_item WHERE id=?"
					+ "");
			
			update = connection.prepareStatement(""
					+ "UPDATE reservation_item SET (reservation_date,retrieval_date,real_date) = (?,?) WHERE id=?"
					+ "");
			
			
			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			
			while(rs.next()){
				if(rs.getString("TABLE_NAME").equalsIgnoreCase("reservation"))
					tableExists=true;
				
			}
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void add(Reservation reservation ){
		
		try {

			insert.setDate(1,new Date(reservation.getReservationDate().getTimeInMillis()));
			insert.setDate(2,  new Date(reservation.getRetirvalDate().getTimeInMillis()));
			insert.setDate(3,  new Date(reservation.getRealDate().getTimeInMillis()));
			insert.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	public void update(Reservation reservation){
		
		try {
			
			
			update.setDate(1,new Date(reservation.getReservationDate().getTimeInMillis()));
			update.setDate(2, new Date(reservation.getRetirvalDate().getTimeInMillis()));
			update.setDate(3,new Date(reservation.getRealDate().getTimeInMillis()));
			update.setInt(4, reservation.getId());
			update.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void delete(Reservation reservation){
		
		try {
			delete.setInt(1, reservation.getId());
			delete.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createTable(){
		
		String sql = "CREATE TABLE reservation("
				+ "id bigint GENERATED BY DEFAULT AS IDENTITY,"
				+ "reservation_date date,"
				+ "retrieval_date date,"
				+ "real_date date"
				+ ")";
		
		try {
			Statement createTable = connection.createStatement();
			if(!tableExists)
				createTable.executeUpdate(sql);
			
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
	public List<Reservation> getPage(int offset, int limit){
		List<Reservation> result = new ArrayList<Reservation>();
		try {
			selectPage.setInt(1, offset);
			selectPage.setInt(1, limit);
			ResultSet rs = selectPage.executeQuery();
			while(rs.next()){
			Reservation p = new Reservation();
			p.setId(rs.getInt("id"));
			p.setReservationDate(rs.getDate("reservation_date"));
			p.setRetirvalDate(rs.getDate("retrival_date"));
			p.setRealDate(rs.getDate("real_date"));
			result.add(p);
		}
		} catch (SQLException e) {
		e.printStackTrace();
		}
			return result;
		}
	
}
