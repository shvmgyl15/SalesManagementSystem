package salesmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionClass {

	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sales","root","");
		} catch (ClassNotFoundException | SQLException ex) {
			ex.printStackTrace();
		}
		return con;
	}

	private static Connection con;

	public ConnectionClass() {
		// TODO Auto-generated constructor stub
		con = getConnection();
	}

	ResultSet getDataSet(String quesry) {
		ResultSet rs = null;
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(quesry);
			rs = ps.executeQuery();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return rs;
	}

	void insertData(String query) {
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
