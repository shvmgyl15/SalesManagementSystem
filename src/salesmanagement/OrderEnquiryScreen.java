package salesmanagement;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class OrderEnquiryScreen extends MainPage {

	ConnectionClass connection = new ConnectionClass();

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Create the frame.
	 */
	public OrderEnquiryScreen() {
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
						FormFactory.DEFAULT_ROWSPEC, }));
		setVisible(true);
		JLabel lblOrderNo = new JLabel("order no");
		contentPane.add(lblOrderNo, "2, 2, right, default");

		JLabel lblCustomerCode = new JLabel("customer code & address");
		contentPane.add(lblCustomerCode, "2, 4");

		final JLabel lblCcode = new JLabel();
		contentPane.add(lblCcode, "4, 6");

		final JLabel lbladd1 = new JLabel();
		contentPane.add(lbladd1, "4, 8");

		final JLabel lbladd2 = new JLabel();
		contentPane.add(lbladd2, "4, 10");

		final JLabel lbladd3 = new JLabel();
		contentPane.add(lbladd3, "4, 12");

		textField = new JTextField();
		textField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String string = textField.getText();
				int orderno = Integer.parseInt(string);
				String initial = "";
				int slno = 0;
				String queryString = "SELECT * FROM order_header WHERE order_no = "
						+ orderno;
				System.out.println(queryString);
				ResultSet rSet = connection.getDataSet(queryString);
				try {
					while (rSet.next()) {
						JLabel lblOS = new JLabel("");
						contentPane.add(lblOS, "4, 14");
						lblOS.setText("" + rSet.getString(3));
						initial = rSet.getString(8);
						slno = rSet.getInt(9);
						lblCcode.setText(initial + "-" + slno);

						JLabel lblNOS = new JLabel("Order Status");
						contentPane.add(lblNOS, "2, 14");

					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				rSet = null;
				queryString = "SELECT * FROM customer_master WHERE cust_initial = '"
						+ initial + "' AND cust_slno = " + slno;
				System.out.println(queryString);
				try {
					rSet = connection.getDataSet(queryString);
					while (rSet.next()) {
						lbladd1.setText(rSet.getString(4));
						lbladd2.setText(rSet.getString(5));
						lbladd3.setText(rSet.getString(6));
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		contentPane.add(textField, "4, 2, fill, default");
		textField.setColumns(10);

	}

}
