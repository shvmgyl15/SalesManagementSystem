package salesmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class CreateTables {

	public static void main(String[] args) {
		CreateTables createTables = new CreateTables();
	}

	PreparedStatement ps;
	Connection con = ConnectionClass.getConnection();

	public CreateTables() {
		// createOrder_DetailTable();//prob
		// createOrder_TrackingMasterTable();
		// createStock_MasterTable();
		// createOrderHeader();//probn
		// createCustomerMasterTable(); // syntax error
		// createMaterialMasterTable(); //prob
		// createPlantMasterTable(); // arrayoutofbounds
		// createStateMasterTable(); //success
		// createStatusMasterTable(); //success
	}

	public boolean createCustomerMasterTable() {

		String queryString = "CREATE TABLE customer_master(cust_initial varchar(1) , cust_slno int"
				+ " , cust_name varchar(40), cust_add1 varchar(40), cust_add2 varchar(40) , "
				+ "cust_add3 varchar(40) , cust_pincode varchar(10), cust_city varchar(15), "
				+ "contact_person_name varchar(30), contact_person_number varchar(20) "
				+ ", state_code varchar(2) )";
		try {
			ps = con.prepareStatement(queryString);
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null,
					"Customer Master Table created successfully");

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean createMaterialMasterTable() {
		try {
			ps = con.prepareStatement("CREATE TABLE material_master(material_code varchar(10), material_description varchar(100), shipping_plant varchar(4), material_price int)");
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null,
					"Material Master Table created successfully");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean createOrder_DetailTable() {
		try {
			ps = con.prepareStatement("CREATE TABLE order_detail(order_no int, material_code varchar(10), item_qty int,item_value int)");
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null,
					"Order_Detail Table created successfully");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean createOrder_TrackingMasterTable() {
		try {
			ps = con.prepareStatement("CREATE TABLE order_tracking(order_no int, order_status varchar(4), creation_date int)");
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null,
					"order_tracking Table created successfully");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean createOrderHeader() {
		try {
			ps = con.prepareStatement("CREATE TABLE order_header(order_no int, order_creation_date varchar(10),order_status varchar(4),customer_ref_no varchar(40),customer_ref_date varchar(10),order_value int,material_required_date varchar(10),customer_initial varchar(1),customer_slno int,delivery_challan_no int,shipment_date int,invoice_number int,invoice_date varchar(10),transporter_name varchar(40),plant_code varchar(4),machine_installed_by varchar(40),cheque_no varchar(15),bank_name varchar(15) )");
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null,
					"Order Header Table created successfully");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean createPlantMasterTable() {
		try {
			ps = con.prepareStatement("CREATE TABLE plant_master(plant_code varchar(4) , plant_name varchar(40) ,  plant_add1 varchar(40), plant_add2 varchar(40), plant_add3 varchar(40) , plant_city  varchar(15), plant_pincode varchar(15))");
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null,
					"Plant Master Table created successfully");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean createStateMasterTable() {
		try {
			ps = con.prepareStatement("CREATE TABLE state_master(state_code varchar(2), state_description varchar(40))");
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null,
					"State Master Table created successfully");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean createStatusMasterTable() {
		try {
			ps = con.prepareStatement("CREATE TABLE status_master(order_status varchar(4), description varchar(40))");
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null,
					"Order Status Master Table created successfully");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean createStock_MasterTable() {
		try {
			ps = con.prepareStatement("CREATE TABLE stock_master(material_code varchar(10), plant_code varchar(4), stock_qty int)");
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null,
					"Stock Master Table created successfully");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
