/**
 * 
  *
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

public class AboutWindow {
    
    private static JFrame aboutFrame = new JFrame("About");
    private JPanel pnlMain = new JPanel();
    private JLabel lblAbout = new JLabel();
    
    public AboutWindow() {
        String labelText = new String();
        labelText = "<html><center>"
                + "<font color='#ff0000'>Colorado Technical University</font><br />"
                + "<font color='#0000ff'>Unit 5 Individual Project</font><br /><br />"
                + "Author: <font color='#0000ff'>James Cathcart</font><br />"
                + "CS230: <font color='#0000ff'>Professor Griepp</font><br /><br />"
                + "</center></html>";
        lblAbout.setText(labelText);
        pnlMain.setLayout(new BorderLayout());
        pnlMain.add(lblAbout, BorderLayout.CENTER);
        pnlMain.setBorder(new EmptyBorder(10,10,10,10));
        aboutFrame.setTitle("About");
        aboutFrame.setLayout(new BorderLayout());
        aboutFrame.add(pnlMain, BorderLayout.CENTER);
        aboutFrame.setLocationRelativeTo(null);
        aboutFrame.setResizable(false);
        aboutFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        aboutFrame.pack();
        aboutFrame.setVisible(true);
    }
}
