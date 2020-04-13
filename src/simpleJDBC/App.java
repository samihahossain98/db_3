package simpleJDBC;

import java.sql.* ;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;
import java.util.Locale;

public class App {
	static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args) throws SQLException, ParseException{
		mainMenu();
	}
		
	public static void mainMenu() throws SQLException, ParseException{
			
			System.out.println();
			System.out.println("MAIN MENU");
			System.out.println("1. BOOK SEARCH");
			System.out.println("2. ADD BOOK");
			System.out.println("3. ADD CUSTORMER");
			System.out.println("4. UPDATE PASSWORD");
			System.out.println("5. LIST TOP SPEND CUSTOMER");
			System.out.println("6. EXIST");
			System.out.println();
			System.out.print("ENTER A NUMBER: ");
			
			int num = in.nextInt();
				
			if(num == 1) {
				searchBook();
			}
				
			else if(num == 2)
				addBook();
			else if(num == 3)
				addCustomer();
			else if (num == 4)
				updatePassword();
			else if (num == 5) {
				topCustomer();
			}
			else if(num == 6){
				in.close();
				System.out.print("EXITED" );
				System.exit(0);
			}
			else{
				System.out.println("The option is not in our list, Please try again");
				mainMenu();
			}
	}
	
	public static void searchBook() throws SQLException, ParseException{
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
		    String querySQL = "SELECT TITLE from BOOK WHERE YEAR < " + year;
		    System.out.println (querySQL) ;
		    java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
		    while ( rs.next ( ) ) {
			String titleResult = rs.getString ("title");
			System.out.println ("title:  " + titleResult);
			
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
		con.close();
		mainMenu();
	}
	
	
	public static void addBook() throws SQLException, ParseException {
		
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
	
	    int bookid;
	    float retailprice, wholesaleprice;
		String isbn, title, year;
		
		
		
		String output = "";

		//Get the input from the user for all the attributes in the Book table
		System.out.print("Enter the bookid: ");
		bookid = in.nextInt();
		System.out.print("Enter the isbn: " );
		isbn = in.next();
		System.out.print("Enter the title: ");
		in.nextLine();
		title = in.nextLine();
		
		System.out.print("Enter retailprice: ");
		retailprice = in.nextFloat();
		
		System.out.print("Enter year (should use YYYY-MM-DD format): ");
		year = in.next();
		System.out.print("Enter wholesaleprice: ");
		wholesaleprice = in.nextFloat();
		
		
		

		//Date dt = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).parse(String.valueOf(year));
//		SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
//		Date date = originalFormat.parse(year.toString());
		try{
			//Execute the insert
			String sqlInsert = "INSERT INTO Book VALUES ( " +  bookid + ","+  isbn + ",'" +title + "'," + retailprice +  ",'" + year + "'," +  
					wholesaleprice + ')';
			System.out.println(sqlInsert);
			statement.executeUpdate(sqlInsert);
			output = "Insert was successfull";
			System.out.println();
			System.out.println(output);
		}catch(SQLException e){
			output = "An error occured while attempting to insert -- "+e.toString();
			System.out.println(output);
		}
		int quantity, code;
		System.out.print("Enter the quantity of this book which would like to stock  : ");
		quantity = in.nextInt();
        System.out.print("Enter the code of the WareHouse which you would like to stock : ");
	    code = in.nextInt();
	    
		try{
			//Execute the insert
			String updateStock = "INSERT INTO stockedIn VALUES ( "  + quantity + "," + bookid + "," + code  +  ')';
			System.out.println(updateStock);
			statement.executeUpdate(updateStock);
			output = "Stock updated successfull";
			System.out.println(output);
		}catch(SQLException e){
			output = "An error occured while attempting to insert -- "+e.toString();
			System.out.println(output);
		}
		
		con.close();
		mainMenu();
	}
	
	public static void addCustomer() throws SQLException, ParseException {
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
	    
	    
		String email, password, address, phone, firstname, lastname;
		//Scanner keyboard = new Scanner(System.in);
		String output = "";
		
		//Get the input from the user for all the attributes in the User table
		System.out.print("Enter the email: ");
		email = in.next();
		System.out.print("Enter the password: " );
		password = in.next();
		
		System.out.print(" Enter the phone: ");
		phone = in.next();
		System.out.print("Enter the firstname: ");
		firstname = in.next();
		System.out.print("Enter the lastname: ");
		lastname = in.next();
		
		System.out.print("Enter the address: ");
		address = in.next();
		
		
		
		try{
			//Execute the insert
			String sqlInsert = "insert into CUSTOMER values('"+email+"','"+password+"','"+address+"','"+phone+"','"+firstname+"','"+lastname+"')";
			statement.executeUpdate(sqlInsert);
			output = "Insert was successfull";
			System.out.println();
			System.out.println(output);
		}catch(SQLException e){
			output = "An error occured while attempting to insert -- "+e.toString();
			System.out.println(output);
		}
		con.close();
		mainMenu();
	}
	
	public static void updatePassword() throws SQLException, ParseException {
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
	    
	    
		String email, password;
		//Scanner keyboard = new Scanner(System.in);
		String output = "";
		
		//Get the input from the user for all the attributes in the User table
		System.out.print("Enter your email address which want to change password: ");
		email = in.next();
		int count = 0;
		try{
			 String querySQL = "SELECT email, password from Customer WHERE email = '" + email + "'";
			 System.out.println (querySQL) ;
			 java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
			 
			 
			 String emailResult = null;
			 while ( rs.next ( ) ) {
				 
				 emailResult= rs.getString ("email");
				 count ++;
				 System.out.println ("email:  " + emailResult);
				 
			 }
			 if (emailResult == null) {
				 System.out.print("Can not find this email! \n ");
				 updatePassword();
				 con.close();

			 }
			 System.out.println ("DONE");
		}catch(SQLException e){
			output = "An error occured while attempting to insert -- "+e.toString();
			System.out.println(output);
		}
		
		if(count > 0) {
			System.out.print("Enter the new password: " );
			password = in.next();
			
			try{
				 String updateSQL  = "UPDATE CUSTOMER SET PASSWORD = '" + password + "' WHERE email = '" + email + "'";
				 System.out.println (updateSQL) ;
				 statement.executeUpdate ( updateSQL ) ;
				
				 System.out.println ("PASSWORD UPDATE DONE");
			}catch(SQLException e){
				output = "An error occured while attempting to insert -- "+e.toString();
				System.out.println(output);
			}
			
			con.close();
		}
		mainMenu();
		
	}
	
	
	public static void topCustomer() throws SQLException, ParseException {
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
	    
	    
		String email, password, address, phone, firstname, lastname;
		//Scanner keyboard = new Scanner(System.in);
		int num;
		
		//Get the input from the user for all the attributes in the User table
		System.out.print("Enter the number of top customer who the system will show : ");
		num = in.nextInt();
		String output = "";
		
	
		
			//Execute the insert
//			SELECT t.email, SUM(b.totalPrice) as totalMoneySpent 
//			FROM 
//			(
//			SELECT c.firstname, c.lastname, c.email, p.orderid
//			FROM customer c, places p
//			WHERE c.email  = p.email
//			)t, bookorder b
//			WHERE   t.orderid = b.orderid
//			GROUP BY t.email 
//			ORDER BY totalMoneySpent DESC;
			
			try{
				 String querySQL = "SELECT t.email, SUM(b.totalPrice) as totalMoneySpent \n" + 
				 		"FROM \n" + 
				 		"(\n" + 
				 		"SELECT c.firstname, c.lastname, c.email, p.orderid\n" + 
				 		"FROM customer c, places p\n" + 
				 		"WHERE c.email  = p.email\n" + 
				 		")t, bookorder b\n" + 
				 		"WHERE   t.orderid = b.orderid\n" + 
				 		"GROUP BY t.email \n" + 
				 		"ORDER BY totalMoneySpent DESC;";
				 System.out.println (querySQL) ;
				 java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
				 
				 
				 String emailResult = null;
				 while ( rs.next ( ) && num > 0) {
					 
					 emailResult= rs.getString ("email");
					 int totalMoney = rs.getInt ( "totalMoneySpent" ) ;
					 num--;
					 System.out.println ("email:  " + emailResult);
					 System.out.println("totalMoneySpent:  " + totalMoney);
					 
				 }
				
				 System.out.println ("DONE");
		}catch(SQLException e){
			output = "An error occured while attempting to insert -- "+e.toString();
			System.out.println(output);
		}
		con.close();
		mainMenu();
	}
	


}

