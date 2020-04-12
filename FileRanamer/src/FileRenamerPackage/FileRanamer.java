package FileRenamerPackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * The idea of this class is to solve an issue. I like to have shortcuts on my desktop, sorted into
 * folders. This acts like a quick reference place such that I can see what tools I have downloaded
 * and available. My issue, is that all my shortcuts will be tagged with " - Shortcut" by Windows.
 * This program will aim to mass edit file names in some directory.
 * 
 * @author Harri
 *
 */
public class FileRanamer {

  final static JFileChooser jfc = new JFileChooser();
  static JFrame f;
  static JTextField t;
  static JRadioButton add, sub;

  static String keyOg;
  static File directory;
  static boolean addToName;
  static int filesAltered;

  /**
   * Starts the program by asking for a directory to edit.
   */
  private static void start() {
    try {
      // ENSURE we only take directories
      jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

      // If the selected file is approved
      if (jfc.showDialog(new JPanel(), "Select Directory") == JFileChooser.APPROVE_OPTION) {
        // Save the directory for later
        directory = jfc.getSelectedFile();
        // Create the environment to ask for editing parameters.
        createTextEnvironment();
      }
    } catch (Exception e) {
      // Tell the user there has been some issue with their file
      JOptionPane.showMessageDialog(null, "Unable to use directory:\n" + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
      // Allow the user to try again executing elsewhere to end this process.
      start();
    }
  }


  /**
   * Creates a JFrame where the user can edit their directory files by adding to all file names, or
   * taking away something in all file names.
   */
  private static void createTextEnvironment() {
    // Get the size of the screen, and relevant sizes for panels
    Dimension fullScreenDim = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension subScreenDim = new Dimension(fullScreenDim.width / 6, fullScreenDim.height / 6);
    Dimension subScreenDim1 = new Dimension(subScreenDim.width, subScreenDim.height / 3);
    Dimension subScreenDim2 = new Dimension(subScreenDim.width, subScreenDim.height * 2 / 3);

    // Create the frame
    f = new JFrame();
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setSize(subScreenDim);

    // Create the top panel
    JPanel j1 = new JPanel();
    j1.setPreferredSize(subScreenDim1);
    j1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
        "Text to edit file names"));


    // Create TextField
    t = new JTextField();
    t.setText("");

    // Create button with functionality
    JButton b = new JButton("Submit");
    b.addActionListener(new SubmitActionListener());


    // Set layout for top panel
    j1.setLayout(new GridBagLayout());
    GridBagConstraints gbc1 = new GridBagConstraints();
    gbc1.weighty = 1;
    gbc1.gridy = 0;
    gbc1.fill = GridBagConstraints.HORIZONTAL;

    gbc1.weightx = 4;
    gbc1.gridx = 0;
    j1.add(t, gbc1);
    gbc1.weightx = 1;
    gbc1.gridx = GridBagConstraints.RELATIVE;
    j1.add(b, gbc1);


    // Create the bottom panel
    JPanel j2 = new JPanel();
    j2.setPreferredSize(subScreenDim2);
    j2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
        "Add or Subtract from titles"));


    // Create Radio Buttons
    add = new JRadioButton("Add");
    sub = new JRadioButton("Subtract");

    // Create button group
    ButtonGroup buttonGroup = new ButtonGroup();
    buttonGroup.add(add);
    buttonGroup.add(sub);
    sub.setSelected(true);


    // Set layout for bottomPanel panel
    j2.setLayout(new GridBagLayout());
    GridBagConstraints gbc2 = new GridBagConstraints();
    gbc2.weightx = 1;
    gbc2.gridx = 0;
    gbc2.fill = GridBagConstraints.BOTH;
    gbc2.weighty = 1;

    gbc2.gridy = 0;
    j2.add(add, gbc2);

    gbc2.gridy = 1;
    j2.add(sub, gbc2);



    // Add the panels into the frame
    f.add(j1, BorderLayout.NORTH);
    f.add(j2, BorderLayout.SOUTH);
    j1.setVisible(true);
    j2.setVisible(true);
    j1.revalidate();
    j2.revalidate();


    // visulaize the frame
    f.setVisible(true);
    f.setLocationRelativeTo(null);
    f.pack();
    f.validate();
  }

  /**
   * Stores the text, and weather it is being added or subtracted from file names.
   */
  static void storeText() {
    addToName = add.isSelected();
    keyOg = t.getText();
  }

  /**
   * Adds or subtracts from file names, then closes the program.
   */
  static void runFileRenamer() {
    if (addToName) {
      addToFileNames();
    } else {
      removeFromFileNames();
    }
    closeRanamer();
  }



  /**
   * Adds to the end of file names;
   */
  private static void addToFileNames() {
    filesAltered = 0;
    // For all the files in this location
    for (File f : directory.listFiles()) {
      // Edit file if it contains shortcut
      if (!f.getAbsolutePath().contains(keyOg)) {
        String originalName = f.getAbsolutePath();
        int i = originalName.lastIndexOf('.');

        // Split the name by name - extension
        String[] split =
            {originalName.substring(0, i), originalName.substring(i, originalName.length())};
        // Add the strings together, name + key + extension
        f.renameTo(new File(split[0] + keyOg + split[1]));
        filesAltered++;
      }
    }
  }

  /**
   * Subtracts all instances of key in file names
   */
  private static void removeFromFileNames() {
    filesAltered = 0;
    // For all the files in this location
    for (File f : directory.listFiles()) {
      // Edit file if it contains shortcut
      if (f.getAbsolutePath().contains(keyOg)) {
        f.renameTo(new File(f.getAbsolutePath().replaceAll(keyOg, "")));
        filesAltered++;
      }
    }
  }

  /**
   * Ask the user if they would like to go again, if so, go agian, else close the program.
   */
  private static void closeRanamer() {
    f.setVisible(false);
    JOptionPane.showMessageDialog(null,
        "Finished editing " + filesAltered + " file names in:\n" + directory.getAbsolutePath(), "Complete",
        JOptionPane.INFORMATION_MESSAGE);
    if (JOptionPane.showOptionDialog(null, "Would you like to do another?", "Again?",
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] {"YES", "NO"},
        1) == JOptionPane.YES_OPTION) {
      start();
    } else {
      f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
    }
  }


  public static void main(String... strings) {
    FileRanamer.start();
  }
}
