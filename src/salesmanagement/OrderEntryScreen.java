package salesmanagement;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class OrderEntryScreen extends MainPage {

	static ConnectionClass connection = new ConnectionClass();
	private JPanel contentPane;
	int orderno, slno;
	String initial = "", currentDate = "";
	private JTextField shipplant;
	private JTextField ordvalue;
	private JTextField crefdate;
	private JTextField ccode;
	JLabel lblCPinCode, lblCCity;

	public OrderEntryScreen() {
		setTitle("ORDER ENTRY SCREEN");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane
		.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, }));

		Calendar javaCalendar = null;

		javaCalendar = Calendar.getInstance();

		currentDate = javaCalendar.get(Calendar.DATE) + "/"
				+ (javaCalendar.get(Calendar.MONTH) + 1) + "/"
				+ javaCalendar.get(Calendar.YEAR);

		setVisible(true);
		JLabel lblOrderNumber = new JLabel("Order Number");
		contentPane.add(lblOrderNumber, "1, 2");

		JLabel lblOrderNo = new JLabel("");
		contentPane.add(lblOrderNo, "3, 2");

		String queryString = "SELECT max(order_no) FROM order_header ";
		System.out.println(queryString);
		ResultSet rSet = connection.getDataSet(queryString);
		try {
			while (rSet.next()) {
				orderno = rSet.getInt(1) + 1;
				lblOrderNo.setText("" + orderno);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			orderno = 1;
			lblOrderNo.setText("1");
		}

		JLabel lblOrderCreationDate = new JLabel("Order Creation Date");
		contentPane.add(lblOrderCreationDate, "1, 4");

		JLabel lblCreationDate = new JLabel(currentDate);
		contentPane.add(lblCreationDate, "3, 4");

		JLabel lblOrderStatus = new JLabel("Order Status");
		contentPane.add(lblOrderStatus, "1, 6");

		// JSpinner spinner = new JSpinner();
		// spinner.setModel(new SpinnerListModel(OrderStatus.values()));
		/*
		 * Throws exception : for (OrderStatus orderStatus :
		 * OrderStatus.values()) { contentPane.add(spinner,
		 * orderStatus.toString()); }
		 */

		final JSpinner spinner = new JSpinner(new SpinnerListModel(
				OrderStatus.values()));
		spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {

				if (((ccode.getText().trim().length() == 0)
						|| (ordvalue.getText().trim().length() == 0) || (shipplant
								.getText().trim().length() == 0))
								&& !((JSpinner) e.getSource()).getValue().equals(
										OrderStatus.BLOC)) {
					spinner.setValue(OrderStatus.BLOC);
					JOptionPane.showMessageDialog(null,
							"Some necessary fields have been left empty");
				} else if (((JSpinner) e.getSource()).getValue().equals(
						OrderStatus.SCHD)) {
					String query = "INSERT INTO order_header(order_no, order_creation_date, order_status, order_value, plant_code, customer_initial, customer_slno) VALUES("
							+ orderno
							+ ",'"
							+ currentDate
							+ "',"
							+ "'SCHD'"
							+ ","
							+ Integer.parseInt(ordvalue.getText())
							+ ",'"
							+ shipplant.getText()
							+ "', '"
							+ initial
							+ "',"
							+ slno + ")";
					connection.insertData(query);
					JOptionPane.showMessageDialog(null,
							"Order entered successfully");
				}

			}
		});
		contentPane.add(spinner, "3, 6");

		JLabel lblCustomerCode = new JLabel("Customer Code");
		contentPane.add(lblCustomerCode, "1, 8, right, default");

		JLabel lblCustomerRefNodate = new JLabel("Customer Ref No-date");
		contentPane.add(lblCustomerRefNodate, "1, 10, right, default");

		crefdate = new JTextField();
		contentPane.add(crefdate, "3, 10, fill, default");
		crefdate.setColumns(10);

		JLabel lblCustomerDetails = new JLabel("Customer Details :");
		contentPane.add(lblCustomerDetails, "1, 12");

		JLabel lblCustomerName = new JLabel("Name");
		contentPane.add(lblCustomerName, "1, 14");

		JLabel lblAdd = new JLabel("Add1");
		contentPane.add(lblAdd, "1, 16");

		JLabel lblCustomerAdd = new JLabel("Add2");
		contentPane.add(lblCustomerAdd, "1, 18");

		JLabel lblCustomerAdd_1 = new JLabel("Add3");
		contentPane.add(lblCustomerAdd_1, "1, 20");

		JLabel lblPincode = new JLabel("Pincode");
		contentPane.add(lblPincode, "1, 22");

		lblCPinCode = new JLabel("");
		contentPane.add(lblCPinCode, "3, 22");

		JLabel lblCity = new JLabel("City");
		contentPane.add(lblCity, "1, 24");

		lblCCity = new JLabel("");
		contentPane.add(lblCCity, "3, 24");

		JLabel lblOrderValue = new JLabel("Order Value");
		contentPane.add(lblOrderValue, "1, 26, right, default");

		ordvalue = new JTextField();
		contentPane.add(ordvalue, "3, 26, fill, default");
		ordvalue.setColumns(10);

		JLabel lblShippingPlant = new JLabel("Shipping Plant");
		contentPane.add(lblShippingPlant, "1, 28, right, default");

		shipplant = new JTextField();
		contentPane.add(shipplant, "3, 28, fill, default");
		shipplant.setColumns(10);

		ccode = new JTextField();
		ccode.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				String idString = ccode.getText();
				String[] st = idString.split("-");
				initial = st[0];
				slno = Integer.parseInt(st[1]);
				String queryString = "SELECT * FROM customer_master WHERE cust_initial = '"
						+ initial + "' AND cust_slno = " + slno;
				System.out.println(queryString);
				ResultSet rSet = connection.getDataSet(queryString);
				try {
					while (rSet.next()) {

						JLabel lblCName = new JLabel();
						contentPane.add(lblCName, "3, 14");

						JLabel lblCAdd2 = new JLabel();
						contentPane.add(lblCAdd2, "3, 18");

						JLabel lblCAdd3 = new JLabel();
						contentPane.add(lblCAdd3, "3, 20");

						JLabel lblCAdd1 = new JLabel();
						contentPane.add(lblCAdd1, "3, 16");

						lblCName.setText(rSet.getString(3));
						lblCAdd1.setText(rSet.getString(4));
						lblCAdd2.setText(rSet.getString(5));
						lblCAdd3.setText(rSet.getString(6));
						lblCPinCode.setText(rSet.getString(7));
						lblCCity.setText(rSet.getString(8));
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		contentPane.add(ccode, "3, 8, fill, default");
		ccode.setColumns(10);
	}
}
