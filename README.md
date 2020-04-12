### File Renamer
---

This Java program removes or adds characters to all file names within some directory. <br>
#### Origin and Idea
<br>
The idea originated from reseting the layout of my desktop. I enjoy having several shortcuts there as a reference, a library of available tools I currently have downloaded. This helps during times where I forget the name of a program I haven't used in months, or just generally gives me easier access. During the creation of all the shortcuts, every new icon was titled with what I considered an annoying "- Shortcut" added to the end of their respective titles. Rather than manually deleting this from the 30 odd icons, I quickly bashed together a program to rewrite a file name, then extended this to do all the files in a directory.<br>
<br>
The original program was very specific, using exact directory and key words to remove. This solved my specific issue, however, I wanted to extend my program to solve all similar types of issues. Immediately, I thought that having the user select the directory with a JFileChooser would allow this process to be more versatile. This lead to a full user interface. With a small amount of feature creep, the program also allows the user to add text to the end of all file titles, and also includes the ability to use Java regexs.<br>

#### How to use
<br>
To operate the program. Ensure that a Java JRE (The latest version should suit fine) is installed on your machine. Then run the _FileRenamerProgram.jar_.<br>
From here you will need to select the directory that contains all the files that you would like to rename.<br>
The following screen will ask that you for the text to remove or add. You can alter the way the program acts below the text box. The Add will add the text in the text box to all the file names, where Subtract will remove this text from all files with this in their name.<br>
Finally, click Submit and this action will come into effect.  
