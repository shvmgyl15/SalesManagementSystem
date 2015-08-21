package salesmanagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class InvoiceScreen extends MainPage {
	ConnectionClass connection = new ConnectionClass();

	int invno = 1;
	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Create the frame.
	 */
	public InvoiceScreen() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane
				.setLayout(new FormLayout(new ColumnSpec[] {
						FormFactory.RELATED_GAP_COLSPEC,
						FormFactory.DEFAULT_COLSPEC,
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
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC, }));

		setVisible(true);
		final JLabel lblDeliveryChallanNo = new JLabel("delivery challan no");
		contentPane.add(lblDeliveryChallanNo, "2, 2");

		JLabel lblCustomerCode = new JLabel("customer code & address");
		contentPane.add(lblCustomerCode, "2, 4");

		final JLabel lblCCode = new JLabel("");
		contentPane.add(lblCCode, "6, 6");

		final JLabel lblCAdd1 = new JLabel();
		contentPane.add(lblCAdd1, "6, 8");

		final JLabel lblCAdd2 = new JLabel();
		contentPane.add(lblCAdd2, "6, 10");

		final JLabel lblCAdd3 = new JLabel();
		contentPane.add(lblCAdd3, "6, 12");

		JLabel lblInvoiceNumber = new JLabel("invoice number");
		contentPane.add(lblInvoiceNumber, "2, 14");

		final JLabel lblinvoiceNo = new JLabel();
		contentPane.add(lblinvoiceNo, "6, 14");

		JLabel lblInvoiceValue = new JLabel("invoice value");
		contentPane.add(lblInvoiceValue, "2, 16");

		final JLabel lblorderValue = new JLabel();
		contentPane.add(lblorderValue, "6, 16");

		textField = new JTextField();
		textField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String string = textField.getText().trim().toString();
					int delivchallanno = Integer.parseInt(string);
					String initial = "";
					int slno = 0;
					String queryString = "SELECT * FROM order_header WHERE delivery_challan_no = "
							+ delivchallanno;
					System.out.println(queryString);
					ResultSet rSet = connection.getDataSet(queryString);
					try {
						while (rSet.next()) {
							lblorderValue.setText("" + rSet.getLong(6));
							initial = rSet.getString(8);
							slno = rSet.getInt(9);
							lblCCode.setText(initial + "-" + slno);
						}
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
					rSet = null;
					queryString = "SELECT * FROM customer_master WHERE cust_initial = '"
							+ initial + "' AND cust_slno = " + slno;
					// System.out.println(queryString);
					try {
						rSet = connection.getDataSet(queryString);
						while (rSet.next()) {
							lblCAdd1.setText(rSet.getString(4));
							lblCAdd2.setText(rSet.getString(5));
							lblCAdd3.setText(rSet.getString(6));
						}
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
					queryString = "SELECT order_status FROM order_header where delivery_challan_no = "
							+ delivchallanno;
					System.out.println(queryString);
					rSet = connection.getDataSet(queryString);

					while (rSet.next()) {
						if (!rSet.getString(1).equals("SHIP")) {
							JOptionPane.showMessageDialog(null,
									"Order Status not SHIP");
						} else {
							String query = "UPDATE order_header SET invoice_number = "
									+ invno
									+ " WHERE delivery_challan_no = "
									+ delivchallanno;
							connection.insertData(query);
							query = "UPDATE order_header SET order_status = 'INVG' WHERE delivery_challan_no = "
									+ delivchallanno;
							connection.insertData(query);
							JOptionPane.showMessageDialog(null,
									"Order Status : INVG");
						}
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null,
							"Invalid Delivery Challan no");
				}
			}
		});
		contentPane.add(textField, "6, 2, fill, default");
		textField.setColumns(10);
	}
}
