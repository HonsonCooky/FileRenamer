package FileRenamerPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

class SubmitActionListener extends JFrame implements ActionListener {
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  
  
  /**
   * Initiates storing variables to rename.
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    FileRanamer.storeText();
    FileRanamer.runFileRenamer();
  }

}
