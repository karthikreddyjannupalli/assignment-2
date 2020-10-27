package fetch;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	public Connection connect() {
		String DRIVER = "com.mysql.cj.jdbc.Driver";
		String URL = "jdbc:mysql://localhost:3306/data";
		String USER = "admin";
		String PSWD = "admin";
			Connection con=null;
			try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL,USER,PSWD);
			System.out.println(con);
			}catch(Exception e) {
				e.printStackTrace();
			}
			return con;
	}
}
