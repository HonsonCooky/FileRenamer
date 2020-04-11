package FileRenamerPackage;
import java.io.File;
import javax.swing.JOptionPane;

/**
 * The idea of this class is to solve an issue. I like to have shortcuts on my desktop, sorted into
 * folders. This acts like a quick reference place such that I can see what tools I have downloaded
 * and available. My issue, is that all my shortcuts will be tagged with " - Shortcut" by Windows.
 * This program should be short and sweet, renaming the file name to remove this.
 * 
 * @author Harri
 *
 */
public class FileRanamer {

  private static final String dirFromUser = "/Desktop";
  private static final String absStringRemoval = " - Shortcut";

  public static void main(String... strings) {
    try {
      File desktop = new File(System.getProperty("user.home") + dirFromUser);
      editFileNames(desktop);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Unable to rename files:\n" + e.getMessage(), "Error",
          JOptionPane.INFORMATION_MESSAGE);
    }
  }

  private static void editFileNames(File inFile) {
    // For all the files in this location
    for (File f : inFile.listFiles()) {
      // Edit file if it contains shortcut
      if (f.getAbsolutePath().contains(absStringRemoval)) {
        f.renameTo(new File(f.getAbsolutePath().replace(absStringRemoval, "")));
      }
    }
  }

}
