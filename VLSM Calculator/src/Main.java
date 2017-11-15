import javax.swing.JFormattedTextField.*;
import javax.swing.text.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.*;
import java.util.*;
import java.awt.*;
  
public class Main extends JFrame
{
   public Main() throws Exception
   {
      addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent we) {
            System.exit(1);
         }
      });
  
      MaskFormatter formatter = new MaskFormatter("255.255.###.###");
      formatter.setPlaceholderCharacter('0');
  
      final JFormattedTextField formattedTf = new JFormattedTextField(formatter);
      formattedTf.setInputVerifier(new IPTextFieldVerifier());
  
      final JTextField normalTf = new JTextField(25);
      JButton button = new JButton("Get value");
      button.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ae) {
            normalTf.setText(""+formattedTf.getValue());
         }
      });
  
      getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT));
      getContentPane().add(formattedTf);
      getContentPane().add(button);
      getContentPane().add(normalTf);
  
      formattedTf.setPreferredSize(normalTf.getPreferredSize());
   }
   
   public static void main(String args[]) throws Exception
   {
      Main main = new Main();
      main.setSize(300, 150);
      main.setVisible(true);
   }
}
  
class IPTextFieldVerifier extends InputVerifier {
   public boolean verify(JComponent input) {
      if (input instanceof JFormattedTextField) {
         JFormattedTextField ftf = (JFormattedTextField)input;
         AbstractFormatter formatter = ftf.getFormatter();
         if (formatter != null) {
            String text = ftf.getText();
            StringTokenizer st = new StringTokenizer(text, ".");
            while (st.hasMoreTokens()) {
               int value = Integer.parseInt((String) st.nextToken());
               if (value < 0 || value > 255) {
                  // to prevent recursive calling of the
                  // InputVerifier, set it to null and
                  // restore it after the JOptionPane has
                  // been clicked.
                  input.setInputVerifier(null);
                  JOptionPane.showMessageDialog(new Frame(), "Malformed IP Address!", "Error",
                                                JOptionPane.ERROR_MESSAGE);
                  input.setInputVerifier(this); 
                  return false;
               }
            }
            return true;
         }
      }
      return true;
   }
  
   public boolean shouldYieldFocus(JComponent input) {
      return verify(input);
   }
}