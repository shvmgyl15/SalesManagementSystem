package salesmanagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.ResultSet;

import javax.swing.JButton;
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

public class CommercialGroupScreen extends MainPage {
	ConnectionClass connection = new ConnectionClass();
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	String orderStatus = "";
	int orderno;

	/**
	 * Create the frame.
	 */
	public CommercialGroupScreen() {
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC, }));
		setVisible(true);
		JLabel lblOrderNo = new JLabel("order no");
		contentPane.add(lblOrderNo, "2, 2, right, default");

		textField_3 = new JTextField();
		contentPane.add(textField_3, "4, 14, fill, default");
		textField_3.setColumns(10);

		JLabel lblCustomerCodeAddress = new JLabel("Customer code& address");
		contentPane.add(lblCustomerCodeAddress, "2, 4");

		final JLabel lblCCode = new JLabel();
		contentPane.add(lblCCode, "4, 6");

		final JLabel lblCadd1 = new JLabel();
		contentPane.add(lblCadd1, "4, 8");

		final JLabel lblCadd2 = new JLabel();
		contentPane.add(lblCadd2, "4, 10");

		final JLabel lblCadd3 = new JLabel();
		contentPane.add(lblCadd3, "4, 12");

		JLabel lblPaymentReceived = new JLabel("payment received");
		contentPane.add(lblPaymentReceived, "2, 14, right, default");

		JLabel lblChequeNo = new JLabel("cheque no");
		contentPane.add(lblChequeNo, "2, 16, right, default");

		textField_1 = new JTextField();
		contentPane.add(textField_1, "4, 16, fill, default");
		textField_1.setColumns(10);

		JLabel lblBankName = new JLabel("bank name");
		contentPane.add(lblBankName, "2, 18, right, default");

		textField_2 = new JTextField();
		contentPane.add(textField_2, "4, 18, fill, default");
		textField_2.setColumns(10);

		textField = new JTextField();
		textField.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent arg0) {
				try {
					String string = textField.getText();
					orderno = Integer.parseInt(string);
					String initial = "";
					int slno = 0;
					String queryString = "SELECT * FROM order_header WHERE order_no = "
							+ orderno;
					// System.out.println(queryString);
					ResultSet rSet = connection.getDataSet(queryString);
					while (rSet.next()) {
						orderStatus = rSet.getString(3);
						initial = rSet.getString(8);
						slno = rSet.getInt(9);
						lblCCode.setText(initial + "-" + slno);
					}
					rSet = null;
					queryString = "SELECT * FROM customer_master WHERE cust_initial = '"
							+ initial + "' AND cust_slno = " + slno;
					// System.out.println(queryString);
					rSet = connection.getDataSet(queryString);
					while (rSet.next()) {
						lblCadd1.setText(rSet.getString(4));
						lblCadd2.setText(rSet.getString(5));
						lblCadd3.setText(rSet.getString(6));
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Invalid Order no");
				}
				if (!orderStatus.equals("MACI")) {
					JOptionPane
							.showMessageDialog(null, "Order status not MACI");
				}

			}
		});

		contentPane.add(textField, "4, 2, fill, default");
		textField.setColumns(10);

		JButton btnButton = new JButton("Save Details");
		contentPane.add(btnButton, "2, 20");
		btnButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!orderStatus.equals("MACI")) {
					JOptionPane
							.showMessageDialog(null, "Order status not MACI");
				} else if ((textField.getText().trim().length() == 0)
						|| (textField_3.getText().trim().length() == 0)) {
					JOptionPane.showMessageDialog(null,
							"Some necessary fields have been left empty");
				} else {
					String query = "UPDATE order_header SET cheque_no = '"
							+ textField_1.getText() + "' where order_no = "
							+ orderno;
					connection.insertData(query);
					query = "UPDATE order_header SET bank_name = '"
							+ textField_2.getText() + "' where order_no = "
							+ orderno;
					connection.insertData(query);
					query = "UPDATE order_header SET order_status = 'PYMR' WHERE order_no = "
							+ orderno;
					connection.insertData(query);
					JOptionPane.showMessageDialog(null, "Order Status : PYMR");
				}
			}
		});

	}

}
