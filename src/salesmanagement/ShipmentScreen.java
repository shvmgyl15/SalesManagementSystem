package salesmanagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class ShipmentScreen extends MainPage {

	static ConnectionClass connection = new ConnectionClass();
	private JPanel contentPane;
	int orderno, dcno;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Create the frame.
	 */
	public ShipmentScreen() {
		setTitle("SHIPMENT SCREEN");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane
		.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
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
				FormFactory.DEFAULT_ROWSPEC, }));

		JLabel lblOrderNumber = new JLabel("Order Number");
		contentPane.add(lblOrderNumber, "2, 2, right, default");

		textField = new JTextField();
		contentPane.add(textField, "4, 2, fill, default");
		textField.setColumns(10);

		JLabel lblDeliveryChallanNo = new JLabel("Delivery Challan No");
		contentPane.add(lblDeliveryChallanNo, "2, 4");

		JLabel lblDelivChallanNo = new JLabel("");
		contentPane.add(lblDelivChallanNo, "4, 4");

		String queryString = "SELECT max(delivery_challan_no) FROM order_header ";
		System.out.println(queryString);
		ResultSet rSet = connection.getDataSet(queryString);
		try {
			while (rSet.next()) {
				dcno = rSet.getInt(1) + 1;
				lblDelivChallanNo.setText("" + dcno);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			dcno = 1;
		}

		Calendar javaCalendar = null;
		String currentDate = "";

		javaCalendar = Calendar.getInstance();

		currentDate = javaCalendar.get(Calendar.DATE) + "/"
				+ (javaCalendar.get(Calendar.MONTH) + 1) + "/"
				+ javaCalendar.get(Calendar.YEAR);

		JLabel lblShipmentDate = new JLabel("Shipment Date");
		contentPane.add(lblShipmentDate, "2, 6");

		JLabel lblShipDate = new JLabel(currentDate);
		contentPane.add(lblShipDate, "4, 6");

		JLabel lblTransporterName = new JLabel("Transporter Name");
		contentPane.add(lblTransporterName, "2, 8, right, default");

		textField_1 = new JTextField();
		contentPane.add(textField_1, "4, 8, fill, default");
		textField_1.setColumns(10);

		JButton btnButton = new JButton("SHIP");
		contentPane.add(btnButton, "2, 10");
		btnButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					orderno = Integer.parseInt(textField.getText().trim());
					String queryString = "SELECT order_status FROM order_header where order_no = "
							+ orderno;
					System.out.println(queryString);
					ResultSet rSet = connection.getDataSet(queryString);

					while (rSet.next()) {
						if (!rSet.getString(1).equals("SCHD")) {
							JOptionPane.showMessageDialog(null,
									"Order Status not SCHD");
						} else {
							String query = "UPDATE order_header SET delivery_challan_no = "
									+ dcno + " WHERE order_no = " + orderno;
							connection.insertData(query);
							query = "UPDATE order_header SET transporter_name = '"
									+ textField_1.getText().toString()
									+ "' WHERE order_no = " + orderno;
							connection.insertData(query);
							query = "UPDATE order_header SET order_status = 'SHIP' WHERE order_no = "
									+ orderno;
							connection.insertData(query);
							JOptionPane.showMessageDialog(null,
									"Order Status : SHIP");
						}
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Invalid Order no");
				}
			}
		});
		setVisible(true);
	}

}
