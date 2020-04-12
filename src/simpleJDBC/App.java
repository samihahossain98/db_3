package simpleJDBC;

import java.sql.* ;
import java.util.Scanner;
import java.util.Date;

public class App {
	static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args) throws SQLException{
		mainMenu();
	}
		
	public static void mainMenu() throws SQLException{
			
			System.out.println();
			System.out.println("MAIN MENU");
			System.out.println("1. BOOK SEARCH");
			System.out.println("2. RETURN BOOK");
			System.out.println("3. BORROW BOOK");
			System.out.println("4. VIEW ACCOUNT");
			System.out.println("5. EXIT");
			System.out.println();
			System.out.print("ENTER A NUMBER: ");
			
			int num = in.nextInt();
				
			if(num == 1) {
				searchBook();
			}
				
//			else if(num == 2)
//				returnBook();
//			else if(num == 3)
//				borrowBook();
//			else if (num == 4)
//				viewAccountMenu(-1);
//			else if(num == 5){
//				in.close();
//				System.exit(0);
//			}
			else{
				System.out.println("Not an option. Please try again");
				mainMenu();
			}
	}
	
	public static void searchBook() throws SQLException{
		System.out.print("ENTER YEAR: ");
		String year = in.next();
	    
		int sqlCode=0;      // Variable to hold SQLCODE
	    String sqlState="00000";  // Variable to hold SQLSTATE

		
		// Register the driver.  You must register the driver before you can use it.
	    try {
	    DriverManager.registerDriver ( new org.postgresql.Driver() ) ;
	    } catch (Exception cnfe){
	    System.out.println("Class not found");
	    }

	// This is the url you must use for Postgresql.
	//Note: This url may not valid now !
	String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
	Connection con = DriverManager.getConnection (url, "cs421g17", "Mcgillg17@") ;
	Statement statement = con.createStatement ( ) ;
	
	// Querying a table
		try {
			//SELECT TITLE FROM BOOK WHERE YEAR < '2010-01-20';
		    String querySQL = "SELECT TITLE from BOOK WHERE YEAR  " + year;
		    System.out.println (querySQL) ;
		    java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
		    while ( rs.next ( ) ) {
			int id = rs.getInt ( "id" ) ;
			String name = rs.getString ("name");
			System.out.println ("id:  " + id);
			System.out.println ("name:  " + name);
		    }
		    System.out.println ("DONE");
		} catch (SQLException e)
		    {
			sqlCode = e.getErrorCode(); // Get SQLCODE
			sqlState = e.getSQLState(); // Get SQLSTATE
	                
			// Your code to handle errors comes here;
			// something more meaningful than a print would be good
			System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
		    }
	
	}
}

