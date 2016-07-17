package banking;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import essentials.Essentials;

public class GUI {

	public GUI() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Error while setting LookAndFeel! Default Java LookAndFeel will be used...");
		}

		GridBagLayout layout = new GridBagLayout();

		JFrame frame = new JFrame("Bankschalter");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(1, 3));
		frame.setSize(new Dimension(1366, 768));
		frame.setLocationRelativeTo(null);

		JPanel pnlWest = new JPanel();
		pnlWest.setLayout(layout);
		frame.add(pnlWest);

		JPanel pnlCenter = new JPanel();
		pnlCenter.setLayout(layout);
		frame.add(pnlCenter);

		JPanel pnlEast = new JPanel();
		pnlEast.setLayout(layout);
		frame.add(pnlEast);

		// Citizen overview panel
		TitledBorder brdCitizenOverview = new TitledBorder(BorderFactory.createLineBorder(Color.black),
				"Bürgerübersicht");
		brdCitizenOverview.setTitleFont(new Font("Helvetica", 1, 14));

		JPanel pnlCitizenOverview = new JPanel();
		pnlCitizenOverview.setLayout(layout);
		pnlCitizenOverview.setBorder(brdCitizenOverview);
		Essentials.addComponent(pnlWest, layout, pnlCitizenOverview, 0, 0, 1, 1, 1, 0, new Insets(10, 10, 10, 10));

		// ID input
		JPanel pnlCitizenInformation = new JPanel();
		pnlCitizenInformation.setLayout(layout);
		Essentials.addComponent(pnlCitizenOverview, layout, pnlCitizenInformation, 0, 0, 1, 1, 1, 0,
				new Insets(10, 10, 10, 10));

		JLabel lblID = new JLabel("Bürger-ID:");
		lblID.setFont(new Font("Helvetica", 0, 14));
		Essentials.addComponent(pnlCitizenInformation, layout, lblID, 0, 0, 1, 1, 0, 0, new Insets(0, 0, 10, 10));

		JTextField txfCitizenID = new JTextField();
		txfCitizenID.setPreferredSize(new Dimension(80, 20));
		txfCitizenID.setFont(new Font("Helvetica", 0, 12));
		Essentials.addComponent(pnlCitizenInformation, layout, txfCitizenID, 1, 0, 1, 1, 0, 0,
				new Insets(0, 0, 10, 10));

		JButton btnCitizenID = new JButton("bestätigen");
		btnCitizenID.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		Essentials.addComponent(pnlCitizenInformation, layout, btnCitizenID, 2, 0, 1, 1, 0, 0, new Insets(0, 0, 10, 0));
		Essentials.addComponent(pnlCitizenInformation, layout, new JPanel(), 3, 0, 1, 1, 1, 0, new Insets(0, 0, 0, 0));

		// Citizen name and class
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Helvetica", 0, 14));
		Essentials.addComponent(pnlCitizenInformation, layout, lblName, 0, 1, 1, 1, 0, 0, new Insets(10, 0, 10, 10));

		JTextField txfName = new JTextField();
		txfName.setPreferredSize(new Dimension(0, 20));
		txfName.setFont(new Font("Helvetica", 0, 12));
		txfName.setEditable(false);
		Essentials.addComponent(pnlCitizenInformation, layout, txfName, 1, 1, 3, 1, 1, 0, new Insets(10, 0, 10, 0));

		JLabel lblClass = new JLabel("Klasse:");
		lblClass.setFont(new Font("Helvetica", 0, 14));
		Essentials.addComponent(pnlCitizenInformation, layout, lblClass, 0, 2, 1, 1, 0, 0, new Insets(0, 0, 0, 10));

		JTextField txfClass = new JTextField();
		txfClass.setPreferredSize(new Dimension(80, 20));
		txfClass.setFont(new Font("Helvetica", 0, 12));
		txfClass.setEditable(false);
		Essentials.addComponent(pnlCitizenInformation, layout, txfClass, 1, 2, 1, 1, 0, 0, new Insets(0, 0, 0, 10));
		Essentials.addComponent(pnlCitizenInformation, layout, new JPanel(), 2, 2, 2, 1, 1, 0, new Insets(0, 0, 0, 0));

		JPanel pnlAlreadyChanged = new JPanel();
		pnlAlreadyChanged.setLayout(layout);
		Essentials.addComponent(pnlCitizenInformation, layout, pnlAlreadyChanged, 0, 3, 3, 1, 1, 0,
				new Insets(10, 10, 10, 10));
		
		JLabel lblAlreadyChanged = new JLabel("Bereits umgetauscht:");
		lblAlreadyChanged.setFont(new Font("Helvetica", 0, 14));
		Essentials.addComponent(pnlAlreadyChanged, layout, lblAlreadyChanged, 0, 0, 1, 1, 0, 0, new Insets(0, 0, 0, 10));

		JTextField txfAlreadyChanged = new JTextField();
		txfAlreadyChanged.setFont(new Font("Helvetica", 0, 12));
		Essentials.addComponent(pnlAlreadyChanged, layout, txfAlreadyChanged, 1, 0, 1, 1, 1, 0, new Insets(0, 0, 0, 10));
		
		JLabel lblAlreadyChangedMartini = new JLabel("Martini");
		lblAlreadyChangedMartini.setFont(new Font("Helvetica", 0, 14));
		Essentials.addComponent(pnlAlreadyChanged, layout, lblAlreadyChangedMartini, 2, 0, 1, 1, 0, 0, new Insets(0, 0, 0, 0));
		
		// Comment area
		JLabel lblComment = new JLabel("Kommentar:");
		lblComment.setFont(new Font("Helvetica", 0, 14));
		Essentials.addComponent(pnlCitizenOverview, layout, lblComment, 0, 4, 1, 1, 1, 0, new Insets(10, 10, 10, 10));

		JTextArea txaComment = new JTextArea(8, 0);
		txaComment.setFont(new Font("Helvetica", 0, 12));
		txaComment.setEditable(false);

		JScrollPane scpComment = new JScrollPane(txaComment, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		Essentials.addComponent(pnlCitizenOverview, layout, scpComment, 0, 5, 1, 1, 1, 0, new Insets(0, 10, 10, 10));

		// Basic Security
		TitledBorder brdBasicSecurity = new TitledBorder(BorderFactory.createLineBorder(Color.black), "Grundsicherung");
		brdBasicSecurity.setTitleFont(new Font("Helvetica", 1, 14));

		JPanel pnlBasicSecurity = new JPanel();
		pnlBasicSecurity.setLayout(layout);
		pnlBasicSecurity.setBorder(brdBasicSecurity);
		Essentials.addComponent(pnlWest, layout, pnlBasicSecurity, 0, 1, 1, 1, 1, 0, new Insets(0, 10, 10, 10));

		JLabel lblAlreadyPaid = new JLabel("Bereits ausgezahlt:");
		lblAlreadyPaid.setFont(new Font("Helvetica", 0, 14));
		Essentials.addComponent(pnlBasicSecurity, layout, lblAlreadyPaid, 0, 0, 1, 1, 0, 0, new Insets(10, 10, 10, 0));

		JCheckBox cbxAlreadyPaid = new JCheckBox();
		cbxAlreadyPaid.setEnabled(false);
		Essentials.addComponent(pnlBasicSecurity, layout, cbxAlreadyPaid, 1, 0, 1, 1, 0, 0, new Insets(10, 0, 10, 10));

		JButton btnPayOff = new JButton("auszahlen");
		btnPayOff.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		Essentials.addComponent(pnlBasicSecurity, layout, btnPayOff, 2, 0, 1, 1, 0, 0, new Insets(10, 0, 10, 10));
		Essentials.addComponent(pnlBasicSecurity, layout, new JPanel(), 3, 0, 1, 1, 1, 0, new Insets(0, 0, 0, 0));

		// Bank accounts with access authorization
		TitledBorder brdBankAccounts = new TitledBorder(BorderFactory.createLineBorder(Color.black),
				"Zugriffsberechtigte Konten");
		brdBankAccounts.setTitleFont(new Font("Helvetica", 1, 14));

		JPanel pnlBankAccounts = new JPanel();
		pnlBankAccounts.setLayout(layout);
		pnlBankAccounts.setBorder(brdBankAccounts);
		Essentials.addComponent(pnlWest, layout, pnlBankAccounts, 0, 2, 1, 1, 1, 1, new Insets(0, 10, 10, 10));

		JList<String> lstBankAccounts = new JList<String>();
		lstBankAccounts.setFont(new Font("Helvetica", 0, 12));
		lstBankAccounts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scpBankAccounts = new JScrollPane(lstBankAccounts, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		Essentials.addComponent(pnlBankAccounts, layout, scpBankAccounts, 0, 0, 1, 1, 1, 1, new Insets(10, 10, 10, 10));

		JPanel pnlManageBankAccounts = new JPanel();
		pnlManageBankAccounts.setLayout(layout);
		Essentials.addComponent(pnlBankAccounts, layout, pnlManageBankAccounts, 0, 1, 1, 1, 1, 0,
				new Insets(0, 10, 10, 10));
		Essentials.addComponent(pnlManageBankAccounts, layout, new JPanel(), 0, 0, 1, 1, 1, 0, new Insets(0, 0, 0, 0));

		JButton btnAddBankAccounts = new JButton("hinzuf.");
		btnAddBankAccounts.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		Essentials.addComponent(pnlManageBankAccounts, layout, btnAddBankAccounts, 1, 0, 1, 1, 0, 0,
				new Insets(0, 0, 0, 10));

		JButton btnRemoveBankAccounts = new JButton("entf.");
		btnRemoveBankAccounts.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		Essentials.addComponent(pnlManageBankAccounts, layout, btnRemoveBankAccounts, 2, 0, 1, 1, 0, 0,
				new Insets(0, 0, 0, 0));

		// Bank account overview
		TitledBorder brdBankAccountOverview = new TitledBorder(BorderFactory.createLineBorder(Color.black),
				"Konto-Übersicht");
		brdBankAccountOverview.setTitleFont(new Font("Helvetica", 1, 14));

		JPanel pnlBankAccountOverview = new JPanel();
		pnlBankAccountOverview.setLayout(layout);
		pnlBankAccountOverview.setBorder(brdBankAccountOverview);
		Essentials.addComponent(pnlCenter, layout, pnlBankAccountOverview, 0, 0, 1, 1, 1, 1,
				new Insets(10, 10, 10, 10));

		JLabel lblAccountNumber = new JLabel("Kontonummer:");
		lblAccountNumber.setFont(new Font("Helvetica", 0, 14));
		Essentials.addComponent(pnlBankAccountOverview, layout, lblAccountNumber, 0, 0, 1, 1, 0, 0,
				new Insets(10, 10, 10, 10));

		JTextField txfAccountNumber = new JTextField();
		txfAccountNumber.setPreferredSize(new Dimension(80, 20));
		Essentials.addComponent(pnlBankAccountOverview, layout, txfAccountNumber, 1, 0, 1, 1, 1, 0,
				new Insets(10, 0, 10, 10));

		JButton btnAccountNumber = new JButton("bestätigen");
		btnAccountNumber.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		Essentials.addComponent(pnlBankAccountOverview, layout, btnAccountNumber, 2, 0, 1, 1, 0, 0,
				new Insets(10, 0, 10, 10));

		JLabel lblBalance = new JLabel("Kontostand:");
		lblBalance.setFont(new Font("Helvetica", 0, 14));
		Essentials.addComponent(pnlBankAccountOverview, layout, lblBalance, 0, 1, 1, 1, 0, 0,
				new Insets(10, 10, 10, 10));

		JTextField txfBalance = new JTextField();
		txfBalance.setPreferredSize(new Dimension(120, 20));
		txfBalance.setFont(new Font("Helvetica", 0, 12));
		txfBalance.setEditable(false);
		Essentials.addComponent(pnlBankAccountOverview, layout, txfBalance, 1, 1, 2, 1, 0, 0,
				new Insets(10, 0, 10, 10));

		JLabel lblType = new JLabel("Kontotyp:");
		lblType.setFont(new Font("Helvetica", 0, 14));
		Essentials.addComponent(pnlBankAccountOverview, layout, lblType, 0, 2, 1, 1, 0, 0, new Insets(0, 10, 10, 10));

		JComboBox<String> cbxType = new JComboBox<String>();
		cbxType.setPreferredSize(new Dimension(120, 20));
		cbxType.setFont(new Font("Helvetica", 0, 12));
		Essentials.addComponent(pnlBankAccountOverview, layout, cbxType, 1, 2, 2, 1, 0, 0, new Insets(0, 0, 10, 10));

		JLabel lblAuthorizedCitizen = new JLabel("Zugriffsberechtigte Bürger:");
		lblAuthorizedCitizen.setFont(new Font("Helvetica", 0, 14));
		Essentials.addComponent(pnlBankAccountOverview, layout, lblAuthorizedCitizen, 0, 3, 3, 1, 1, 0,
				new Insets(10, 10, 10, 10));

		JList<String> lstAuthorizedCitizen = new JList<String>();
		lstAuthorizedCitizen.setFont(new Font("Helvetica", 0, 12));
		lstAuthorizedCitizen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scpAuthorizedCitizen = new JScrollPane(lstAuthorizedCitizen, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		Essentials.addComponent(pnlBankAccountOverview, layout, scpAuthorizedCitizen, 0, 4, 3, 1, 1, 1,
				new Insets(0, 10, 10, 10));

		JPanel pnlManageAuthorizedCitizen = new JPanel();
		pnlManageAuthorizedCitizen.setLayout(layout);
		Essentials.addComponent(pnlBankAccountOverview, layout, pnlManageAuthorizedCitizen, 0, 5, 3, 1, 1, 0,
				new Insets(0, 10, 10, 10));

		JButton btnDeleteAccount = new JButton("Konto löschen");
		btnDeleteAccount.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		Essentials.addComponent(pnlManageAuthorizedCitizen, layout, btnDeleteAccount, 0, 0, 1, 1, 0, 0,
				new Insets(0, 0, 0, 10));

		Essentials.addComponent(pnlManageAuthorizedCitizen, layout, new JPanel(), 1, 0, 1, 1, 1, 0,
				new Insets(0, 0, 0, 0));

		JButton btnAddAuthorizedCitizen = new JButton("hinzuf.");
		btnAddAuthorizedCitizen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		Essentials.addComponent(pnlManageAuthorizedCitizen, layout, btnAddAuthorizedCitizen, 2, 0, 1, 1, 0, 0,
				new Insets(0, 0, 0, 10));

		JButton btnRemoveAuthorizedCitizen = new JButton("entf.");
		btnRemoveAuthorizedCitizen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		Essentials.addComponent(pnlManageAuthorizedCitizen, layout, btnRemoveAuthorizedCitizen, 3, 0, 1, 1, 0, 0,
				new Insets(0, 0, 0, 0));

		// Money Transfer
		TitledBorder brdTransfer = new TitledBorder(BorderFactory.createLineBorder(Color.black), "Überweisung");
		brdTransfer.setTitleFont(new Font("Helvetica", 1, 14));

		JPanel pnlTransfer = new JPanel();
		pnlTransfer.setLayout(layout);
		pnlTransfer.setBorder(brdTransfer);
		Essentials.addComponent(pnlEast, layout, pnlTransfer, 0, 0, 1, 1, 1, 0, new Insets(10, 10, 10, 10));

		JLabel lblTransferAccountNumber = new JLabel("Empfänger-Kontonummer:");
		lblTransferAccountNumber.setFont(new Font("Helvetica", 0, 14));
		Essentials.addComponent(pnlTransfer, layout, lblTransferAccountNumber, 0, 0, 1, 1, 0, 0,
				new Insets(10, 10, 10, 10));

		JTextField txfTransferAccountNumber = new JTextField();
		txfTransferAccountNumber.setPreferredSize(new Dimension(0, 20));
		txfTransferAccountNumber.setFont(new Font("Helvetica", 0, 12));
		Essentials.addComponent(pnlTransfer, layout, txfTransferAccountNumber, 1, 0, 1, 1, 1, 0,
				new Insets(10, 0, 10, 10));

		JLabel lblAmount = new JLabel("Betrag (Martini):");
		lblAmount.setFont(new Font("Helvetica", 0, 14));
		Essentials.addComponent(pnlTransfer, layout, lblAmount, 0, 1, 1, 1, 0, 0, new Insets(0, 10, 10, 10));

		JTextField txfAmount = new JTextField();
		txfAmount.setPreferredSize(new Dimension(0, 20));
		txfAmount.setFont(new Font("Helvetica", 0, 12));
		Essentials.addComponent(pnlTransfer, layout, txfAmount, 1, 1, 1, 1, 1, 0, new Insets(0, 0, 10, 10));

		JPanel pnlConfirmTransfer = new JPanel();
		pnlConfirmTransfer.setLayout(layout);
		Essentials.addComponent(pnlTransfer, layout, pnlConfirmTransfer, 0, 2, 2, 1, 1, 0, new Insets(0, 10, 10, 10));
		Essentials.addComponent(pnlConfirmTransfer, layout, new JPanel(), 0, 0, 1, 1, 1, 0, new Insets(0, 0, 0, 0));

		JButton btnTransfer = new JButton("bestätigen");
		btnTransfer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		Essentials.addComponent(pnlConfirmTransfer, layout, btnTransfer, 1, 0, 1, 1, 0, 0, new Insets(0, 0, 0, 0));

		// Deposit
		TitledBorder brdDeposit = new TitledBorder(BorderFactory.createLineBorder(Color.black), "Einzahlung");
		brdDeposit.setTitleFont(new Font("Helvetica", 1, 14));

		JPanel pnlDeposit = new JPanel();
		pnlDeposit.setLayout(layout);
		pnlDeposit.setBorder(brdDeposit);
		Essentials.addComponent(pnlCenter, layout, pnlDeposit, 0, 1, 2, 1, 1, 0, new Insets(0, 10, 10, 10));

		JTabbedPane tbpDeposit = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		tbpDeposit.setFont(new Font("Helvetica", 0, 14));
		Essentials.addComponent(pnlDeposit, layout, tbpDeposit, 0, 0, 1, 1, 1, 0, new Insets(10, 10, 10, 10));

		JPanel pnlDepositEuro = new JPanel();
		pnlDepositEuro.setLayout(layout);
		tbpDeposit.add("Euro", pnlDepositEuro);

		JLabel lblDepositEuroAmount = new JLabel("Betrag:");
		lblDepositEuroAmount.setFont(new Font("Helvetica", 0, 14));
		Essentials.addComponent(pnlDepositEuro, layout, lblDepositEuroAmount, 0, 0, 1, 1, 0, 0,
				new Insets(10, 10, 10, 10));

		JTextField txfDepositEuroAmount = new JTextField();
		txfDepositEuroAmount.setPreferredSize(new Dimension(0, 20));
		txfDepositEuroAmount.setFont(new Font("Helvetica", 0, 12));
		Essentials.addComponent(pnlDepositEuro, layout, txfDepositEuroAmount, 1, 0, 1, 1, 1, 0,
				new Insets(10, 0, 10, 10));

		JButton btnDepositEuroAmount = new JButton("bestätigen");
		btnDepositEuroAmount.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		Essentials.addComponent(pnlDepositEuro, layout, btnDepositEuroAmount, 2, 0, 1, 1, 0, 0,
				new Insets(10, 0, 10, 10));

		JPanel pnlDepositMartini = new JPanel();
		pnlDepositMartini.setLayout(layout);
		tbpDeposit.add("Martini", pnlDepositMartini);

		JLabel lblDepositMartiniAmount = new JLabel("Betrag:");
		lblDepositMartiniAmount.setFont(new Font("Helvetica", 0, 14));
		Essentials.addComponent(pnlDepositMartini, layout, lblDepositMartiniAmount, 0, 0, 1, 1, 0, 0,
				new Insets(10, 10, 10, 10));

		JTextField txfDepositMartiniAmount = new JTextField();
		txfDepositMartiniAmount.setPreferredSize(new Dimension(0, 20));
		txfDepositMartiniAmount.setFont(new Font("Helvetica", 0, 12));
		Essentials.addComponent(pnlDepositMartini, layout, txfDepositMartiniAmount, 1, 0, 1, 1, 1, 0,
				new Insets(10, 0, 10, 10));

		JButton btnDepositMartiniAmount = new JButton("bestätigen");
		btnDepositMartiniAmount.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		Essentials.addComponent(pnlDepositMartini, layout, btnDepositMartiniAmount, 2, 0, 1, 1, 0, 0,
				new Insets(10, 0, 10, 10));

		// Paying out
		TitledBorder brdPayingOut = new TitledBorder(BorderFactory.createLineBorder(Color.black), "Auszahlung");
		brdPayingOut.setTitleFont(new Font("Helvetica", 1, 14));

		JPanel pnlPayingOut = new JPanel();
		pnlPayingOut.setLayout(layout);
		pnlPayingOut.setBorder(brdPayingOut);
		Essentials.addComponent(pnlCenter, layout, pnlPayingOut, 0, 2, 2, 1, 1, 0, new Insets(0, 10, 10, 10));

		JTabbedPane tbpPayingOut = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		tbpPayingOut.setFont(new Font("Helvetica", 0, 14));
		Essentials.addComponent(pnlPayingOut, layout, tbpPayingOut, 0, 0, 1, 1, 1, 0, new Insets(10, 10, 10, 10));

		JPanel pnlPayingOutEuro = new JPanel();
		pnlPayingOutEuro.setLayout(layout);
		tbpPayingOut.add("Euro", pnlPayingOutEuro);

		JLabel lblPayingOutEuroAmount = new JLabel("Betrag:");
		lblPayingOutEuroAmount.setFont(new Font("Helvetica", 0, 14));
		Essentials.addComponent(pnlPayingOutEuro, layout, lblPayingOutEuroAmount, 0, 0, 1, 1, 0, 0,
				new Insets(10, 10, 10, 10));

		JTextField txfPayingOutEuroAmount = new JTextField();
		txfPayingOutEuroAmount.setPreferredSize(new Dimension(0, 20));
		txfPayingOutEuroAmount.setFont(new Font("Helvetica", 0, 12));
		Essentials.addComponent(pnlPayingOutEuro, layout, txfPayingOutEuroAmount, 1, 0, 1, 1, 1, 0,
				new Insets(10, 0, 10, 10));

		JButton btnPayingOutEuroAmount = new JButton("bestätigen");
		btnPayingOutEuroAmount.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		Essentials.addComponent(pnlPayingOutEuro, layout, btnPayingOutEuroAmount, 2, 0, 1, 1, 0, 0,
				new Insets(10, 0, 10, 10));

		JPanel pnlTaxation = new JPanel();
		pnlTaxation.setLayout(layout);
		Essentials.addComponent(pnlPayingOutEuro, layout, pnlTaxation, 0, 1, 3, 1, 1, 0, new Insets(0, 10, 10, 10));

		JLabel lblTaxation = new JLabel("Nach Besteuerung (20%):");
		lblTaxation.setFont(new Font("Helvetica", 0, 14));
		Essentials.addComponent(pnlTaxation, layout, lblTaxation, 0, 0, 1, 1, 0, 0, new Insets(0, 0, 0, 10));

		JTextField txfTaxation = new JTextField();
		txfTaxation.setPreferredSize(new Dimension(0, 20));
		txfTaxation.setEditable(false);
		Essentials.addComponent(pnlTaxation, layout, txfTaxation, 1, 0, 1, 1, 1, 0, new Insets(0, 0, 0, 10));

		JLabel lblMartini = new JLabel("Martini");
		lblMartini.setFont(new Font("Helvetica", 0, 14));
		Essentials.addComponent(pnlTaxation, layout, lblMartini, 2, 0, 1, 1, 0, 0, new Insets(0, 0, 0, 0));

		JPanel pnlPayingOutMartini = new JPanel();
		pnlPayingOutMartini.setLayout(layout);
		tbpPayingOut.add("Martini", pnlPayingOutMartini);

		JLabel lblPayingOutMartiniAmount = new JLabel("Betrag:");
		lblPayingOutMartiniAmount.setFont(new Font("Helvetica", 0, 14));
		Essentials.addComponent(pnlPayingOutMartini, layout, lblPayingOutMartiniAmount, 0, 0, 1, 1, 0, 0,
				new Insets(10, 10, 10, 10));

		JTextField txfPayingOutMartiniAmount = new JTextField();
		txfPayingOutMartiniAmount.setPreferredSize(new Dimension(0, 20));
		txfPayingOutMartiniAmount.setFont(new Font("Helvetica", 0, 12));
		Essentials.addComponent(pnlPayingOutMartini, layout, txfPayingOutMartiniAmount, 1, 0, 1, 1, 1, 0,
				new Insets(10, 0, 10, 10));

		JButton btnPayingOutMartiniAmount = new JButton("bestätigen");
		btnPayingOutMartiniAmount.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		Essentials.addComponent(pnlPayingOutMartini, layout, btnPayingOutMartiniAmount, 2, 0, 1, 1, 0, 0,
				new Insets(10, 0, 10, 10));

		// Account log
		TitledBorder brdAccountLog = new TitledBorder(BorderFactory.createLineBorder(Color.black), "Konto-Log");
		brdAccountLog.setTitleFont(new Font("Helvetica", 1, 14));

		JPanel pnlAccountLog = new JPanel();
		pnlAccountLog.setLayout(layout);
		pnlAccountLog.setBorder(brdAccountLog);
		Essentials.addComponent(pnlEast, layout, pnlAccountLog, 0, 1, 1, 1, 1, 1, new Insets(0, 10, 10, 10));

		JTextArea txaAccountLog = new JTextArea();
		txaAccountLog.setFont(new Font("Helvetica", 0, 12));
		txaAccountLog.setLineWrap(true);
		txaAccountLog.setEditable(false);

		JScrollPane scpAccountLog = new JScrollPane(txaAccountLog, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		Essentials.addComponent(pnlAccountLog, layout, scpAccountLog, 0, 0, 1, 1, 1, 1, new Insets(10, 10, 10, 10));

		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new GUI();
	}
}
