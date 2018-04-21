/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package second_app;
import java.awt.CardLayout;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import net.proteanit.sql.DbUtils;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Enumeration;

import javax.swing.JFrame;


import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.BufferedOutputStream;

/**
 *
 * @author NEXT
 */
public class arduino_serial_data extends javax.swing.JFrame implements SerialPortEventListener {

    /**
     * Creates new form arduino_serial_data
     */
    SerialPort serialPort;
	/** The port we're normally going to use. */
	Connection con;
	static String[] price = new String[5];
	static String[] quantity = new String[5];
	static String[] Discount = new String[5];
	static String[] product=new String[10];
	static int total=0;
	int j=0;
	

	private static final String PORT_NAMES[] = {
	/*
	 * "/dev/tty.usbserial-A9007UX1", // Mac OS X "/dev/ttyACM0", // Raspberry
	 * Pi "/dev/ttyUSB0", // Linux
	 */
	"COM18" // Windows
	};
	/**
	 * A BufferedReader which will be fed by a InputStreamReader converting the
	 * bytes into characters making the displayed results codepage independent
	 */
	private BufferedReader input;
	/** The output stream to the port */
	private OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;

    public arduino_serial_data() {
        // the next line is for Raspberry Pi and
		// gets us into the while loop and was suggested here was suggested
		// http://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186
		System.setProperty("gnu.io.rxtx.SerialPorts", "COM18");

		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		// First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum
					.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(
					serialPort.getInputStream()));
			/*
			 * String s; while((s = input.readLine())!=null) {
			 * System.out.println(s); }
			 */

			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port. This will prevent
	 * port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		int i = 0;
                 int j= 0;
                 String stg="drashti";
                  byte[] bytes = stg.getBytes();
                                String st2 = "Hello";
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine = input.readLine();
				System.out.println(inputLine);
				String CardName = inputLine;
				con = connection.getConnect();
				String str = "Select * from productdetail where `Card_ID`='"
						+ CardName + "'";
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(str);
                  
				while (rs.next()) {
					price[i] = rs.getString("Price");
					total=total+Integer.parseInt(price[i]);
					quantity[i] = rs.getString("Quantity");
					product[i]=rs.getString("product_name");
					//Discount[i] = rs.getString("Discount");
			
					
					/*if(Discount[i]==null)
					{
						Discount[i]="No discount";
						System.out.println("discnt is:" + Discount[i]);
					}
					else
					{
			
						total = Integer.parseInt(price[i])*(Integer.parseInt(Discount[i])/100);
						System.out.println("discnt" + Integer.parseInt(Discount[i])/100);
						System.out.println("total" +total);
					}*/
					j++;
					System.out.println(price[i]);
				
					
				
					i++;
				}
                                jTextField1.setText(/*gui.textField.getText()+"\n\n\n"+j+ ") \n" +*/product[i]+" "+price[i]+"  "+quantity[i]);

			} catch (Exception e) {
				// System.err.println(e.toString());
			}
                        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try
                        {
				String inputLine = input.readLine();
				System.out.println(inputLine);                               
			
			}
                        

			
                catch (Exception e) {
				// System.err.println(e.toString());
			}
		
		}
                        try(OutputStream out = new BufferedOutputStream(new BufferedOutputStream(output)))
                                {
                                    out.write(bytes);
                                }
                  catch(Exception e)
                  {
                      e.getMessage();
                  }
                         
		}
		// Ignore all the other eventTypes, but you should consider the other
		// ones.
		System.out.println("total is"+total);
	

        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(125, 125, 125)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(99, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(137, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(arduino_serial_data.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(arduino_serial_data.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(arduino_serial_data.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(arduino_serial_data.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new arduino_serial_data().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

/*    @Override
    public void serialEvent(SerialPortEvent spe) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
}