package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import io.SocketServer;

public class MainMonitor {

	private JFrame frmCamera;
	private String ip = null;
	private JTextField textUser;
	private JPasswordField textPass;
	private JTextField ipField;
	private JTextField portField;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMonitor window = new MainMonitor();
					//window.frmCamera.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainMonitor() {
		//findIP();
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCamera = new JFrame();
		frmCamera.setTitle("Camera");
		frmCamera.setBounds(100, 100, 450, 300);
		frmCamera.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCamera.getContentPane().setLayout(null);
		frmCamera.setVisible(true);
		
		JLabel lblNewLabel = new JLabel("\u0110\u0103ng nh\u1EADp \u0111\u1EC3 k\u1EBFt n\u1ED1i");
		lblNewLabel.setBounds(124, 28, 155, 29);
		frmCamera.getContentPane().add(lblNewLabel);
		
		JLabel Username = new JLabel("T\u00E0i kho\u1EA3n:");
		Username.setBounds(33, 108, 58, 21);
		frmCamera.getContentPane().add(Username);
		
		JLabel Password = new JLabel("M\u1EADt kh\u1EA9u:");
		Password.setBounds(33, 139, 58, 21);
		frmCamera.getContentPane().add(Password);
		
		JButton btnSignin = new JButton("K\u1EBFt n\u1ED1i");
		btnSignin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmCamera.dispose();
				JFrame f = new JFrame("Camera");
				f.addWindowListener(new WindowAdapter(){
	                @Override
	                public void windowClosing(WindowEvent e) {
	                    System.exit(0);
	                }
	            });
				//System.out.print(textPass.getPassword().toString());	
				if(!textUser.getText().isEmpty()&&!(textPass.getPassword().length == 0)) {
					f.getContentPane().add(new ServerUIMain(textUser.getText().toString(),textPass.getText().toString(),ipField.getText().toString(),
							Integer.parseInt(portField.getText().toString())));
				}
				if(textUser.getText().isEmpty()&&textPass.getPassword().length == 0) {
					f.getContentPane().add(new ServerUIMain("null","null","null",12321));
				}
				f.pack();
				f.setVisible(true);         
			}
		});
		btnSignin.setBounds(138, 192, 97, 21);
		frmCamera.getContentPane().add(btnSignin);
		
		textUser = new JTextField();
		textUser.setText("");
		textUser.setBounds(113, 111, 178, 19);
		frmCamera.getContentPane().add(textUser);
		textUser.setColumns(10);

		textPass = new JPasswordField();
		textPass.setText("");
		textPass.setColumns(10);
		textPass.setBounds(113, 140, 178, 19);
		frmCamera.getContentPane().add(textPass);
		
		JLabel IP = new JLabel("\u0110\u1ECBa ch\u1EC9 IPv4");
		IP.setBounds(33, 77, 81, 21);
		frmCamera.getContentPane().add(IP);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(249, 80, 30, 21);
		frmCamera.getContentPane().add(lblPort);
		
		ipField = new JTextField();
		ipField.setText("");
		ipField.setColumns(10);
		ipField.setBounds(113, 82, 122, 19);
		frmCamera.getContentPane().add(ipField);
		
		portField = new JTextField();
		portField.setText("");
		portField.setColumns(10);
		portField.setBounds(279, 82, 81, 19);
		frmCamera.getContentPane().add(portField);
	}
//	private void findIP() {
//		try {
//		    Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
//		    NetworkInterface iface = null;
//		    while (interfaces.hasMoreElements()) {
//		    	iface = interfaces.nextElement();
//		        // filters out 127.0.0.1 and inactive interfaces
//		        if (iface.isLoopback() || !iface.isUp())
//		            continue;
//
//		        Enumeration<InetAddress> addresses = iface.getInetAddresses();
//		        while(addresses.hasMoreElements()) {
//		            InetAddress addr = addresses.nextElement();
//
//		            // *EDIT*
//		            if (addr instanceof Inet6Address) continue;
//
//		            ip = addr.getHostAddress();
//		        }
//		    }
//		    System.out.println("IPv4 Address: " + ip);
//		} catch (SocketException e) {
//		    throw new RuntimeException(e);
//		}
//	}			
}
