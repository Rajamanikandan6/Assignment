package TaskJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	Connection con =null;
	public Connection getConnection() throws SQLException,ClassNotFoundException {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(  
					"jdbc:mysql://localhost:3306/employee_info","root","root"); 
			con.setAutoCommit(false);
			return con;
		
	}

}
