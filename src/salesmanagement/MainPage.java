package salesmanagement;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

public class MainPage extends JFrame implements ActionListener {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainPage frame = new MainPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JButton btnOrderEntry, btnShip, btnInvoice, btnPay, btnEnq, btnMachine;

	public JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public MainPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setExtendedState(MAXIMIZED_BOTH);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		btnOrderEntry = new JButton("ORDER ENTRY");
		menuBar.add(btnOrderEntry);

		btnShip = new JButton("SHIPMENT");
		menuBar.add(btnShip);

		btnInvoice = new JButton("INVOICE");
		menuBar.add(btnInvoice);

		btnMachine = new JButton("MACHINE");
		menuBar.add(btnMachine);

		btnPay = new JButton("PAYMENT RECEIVED");
		menuBar.add(btnPay);

		btnEnq = new JButton("ORDER ENQUIRY");
		menuBar.add(btnEnq);
		btnEnq.addActionListener(this);
		btnPay.addActionListener(this);

		btnInvoice.addActionListener(this);
		btnShip.addActionListener(this);
		btnOrderEntry.addActionListener(this);
		btnMachine.addActionListener(this);

		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {},
				new RowSpec[] {}));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btnOrderEntry) {
			dispose();
			new OrderEntryScreen();
		} else if (e.getSource() == btnShip) {
			dispose();
			new ShipmentScreen();
		} else if (e.getSource() == btnInvoice) {
			dispose();
			new InvoiceScreen();
		} else if (e.getSource() == btnMachine) {
			dispose();
			new MachineScreen();
		} else if (e.getSource() == btnPay) {
			dispose();
			new CommercialGroupScreen();
		} else if (e.getSource() == btnEnq) {
			dispose();
			new OrderEnquiryScreen();
		}

	}
}
