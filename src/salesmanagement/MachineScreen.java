package salesmanagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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

public class MachineScreen extends MainPage {
	ConnectionClass connection = new ConnectionClass();

	private JPanel contentPane;
	private JTextField textField;
	private JTextField tfengname;
	int delivchallanno;

	/**
	 * Create the frame.
	 */
	public MachineScreen() {
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
				FormFactory.DEFAULT_ROWSPEC, }));

		setVisible(true);
		JLabel lblDeliveryChallanNo = new JLabel("Delivery challan no");
		contentPane.add(lblDeliveryChallanNo, "2, 2, right, default");

		JLabel lblCustomerCodeaddress = new JLabel("customer code&address");
		contentPane.add(lblCustomerCodeaddress, "2, 4");

		final JLabel lblCCode = new JLabel();
		contentPane.add(lblCCode, "4, 6");

		final JLabel lblcadd1 = new JLabel();
		contentPane.add(lblcadd1, "4, 8");

		final JLabel lblCadd2 = new JLabel();
		contentPane.add(lblCadd2, "4, 10");

		final JLabel lblcadd3 = new JLabel();
		contentPane.add(lblcadd3, "4, 12");

		JLabel lblCustomerEngineerName = new JLabel("customer engineer name");
		contentPane.add(lblCustomerEngineerName, "2, 14, right, default");

		textField = new JTextField();
		textField.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent arg) {
				try {
					String string = textField.getText().trim().toString();
					delivchallanno = Integer.parseInt(string);
					String initial = "";
					int slno = 0;
					String queryString = "SELECT * FROM order_header WHERE delivery_challan_no = "
							+ delivchallanno;
					// System.out.println(queryString);
					ResultSet rSet = connection.getDataSet(queryString);
					try {
						while (rSet.next()) {
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
							lblcadd1.setText(rSet.getString(4));
							lblCadd2.setText(rSet.getString(5));
							lblcadd3.setText(rSet.getString(6));
						}
					} catch (SQLException ex) {
						ex.printStackTrace();
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null,
							"Invalid Delivery Challan no");
				}
			}
		});
		contentPane.add(textField, "4, 2, fill, default");
		textField.setColumns(10);

		tfengname = new JTextField();
		contentPane.add(tfengname, "4, 14, fill, default");
		tfengname.setColumns(10);
		tfengname.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String engName = tfengname.getText().trim().toString();
				if (engName.equals("")) {
					JOptionPane.showMessageDialog(null, "Engineer name reqd");
				} else {
					String queryString = "SELECT order_status FROM order_header where delivery_challan_no = "
							+ delivchallanno;
					System.out.println(queryString);
					ResultSet rSet = connection.getDataSet(queryString);
					try {
						while (rSet.next()) {
							if (!rSet.getString(1).equals("INVG")) {
								JOptionPane.showMessageDialog(null,
										"Order Status not INVG");
							} else {
								String query = "UPDATE order_header SET order_status = 'MACI' WHERE delivery_challan_no = "
										+ delivchallanno;
								connection.insertData(query);
								query = "UPDATE order_header SET machine_installed_by = '"
										+ engName
										+ "' WHERE delivery_challan_no = "
										+ delivchallanno;
								connection.insertData(query);
								JOptionPane.showMessageDialog(null,
										"Order Status : MACI");
							}
						}
					} catch (Exception e) {
						// TODO: handle exception

					}

				}
			}
		});
	}
}
