package FileRenamerPackage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
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
  final static ExecutorService pool = Executors.newFixedThreadPool(1);
  static JFrame f;
  static JTextField t;
  static JRadioButton add, sub;

  static String keyOg;
  static Pattern key;
  static File directory;
  static boolean addToName;


  private static void start() {
    try {
      // ENSURE we only take directories
      jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

      // If the selected file is approved
      if (jfc.showDialog(new JPanel(), "Select Directory") == JFileChooser.APPROVE_OPTION) {
        directory = jfc.getSelectedFile();
        createTextEnvironment();
      }
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Unable to use directory:\n" + e.getMessage(), "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }


  private static void createTextEnvironment() {
    // Get the size of the screen
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



    f.add(j1, BorderLayout.NORTH);
    f.add(j2, BorderLayout.SOUTH);


    // visulaize the frame
    j1.setVisible(true);
    j2.setVisible(true);
    j1.revalidate();
    j2.revalidate();
    f.setVisible(true);
    f.setLocationRelativeTo(null);
    f.pack();
    f.validate();
  }

  static void storeText() {
    try {
    addToName = add.isSelected();
    keyOg = t.getText();
    key = Pattern.compile(".*" + t.getText() + ".*");
    } catch (Exception e) {
      f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
      JOptionPane.showMessageDialog(null,
          "Unable to use regex:\n" + directory.getAbsolutePath(), "Error",
          JOptionPane.ERROR_MESSAGE);
      createTextEnvironment();
    }
  }

  static void runFileRenamer() {
    if (addToName) {
      addToFileNames();
    } else {
      removeFromFileNames();
    }
    closeRanamer();
  }



  private static void addToFileNames() {
    // For all the files in this location
    for (File f : directory.listFiles()) {
      // Edit file if it contains shortcut
      if (!f.getAbsolutePath().contains(keyOg)) {
        String originalName = f.getAbsolutePath();
        int i = originalName.lastIndexOf('.');
        String[] split =
            {originalName.substring(0, i), originalName.substring(i, originalName.length())};
        f.renameTo(new File(split[0] + keyOg + split[1]));
      }
    }
  }

  private static void removeFromFileNames() {
    // For all the files in this location
    for (File f : directory.listFiles()) {
      // Edit file if it contains shortcut
      if (key.matcher(f.getAbsolutePath()).matches()) {
        f.renameTo(new File(f.getAbsolutePath().replaceAll(keyOg, "")));
      }
    }
  }

  private static void closeRanamer() {
    JOptionPane.showMessageDialog(null,
        "Finished editing names in:\n" + directory.getAbsolutePath(), "Complete",
        JOptionPane.INFORMATION_MESSAGE);
    f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
  }


  public static void main(String... strings) {
    FileRanamer.start();
  }
}
