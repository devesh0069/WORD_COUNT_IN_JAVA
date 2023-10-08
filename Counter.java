import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class Counter {
    /**
     * @param args
     */
    public static void main(String args[]) {
        // frame define
        JFrame f = new JFrame("Word Couter");
        f.setSize(600, 700);

        // frame align center
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - f.getWidth()) / 2;
        int y = (d.height - f.getHeight()) / 2;
        f.setLocation(x, y);
        f.setLayout(null);

        // add lable inside the frame
        JLabel l1 = new JLabel(
                "<html><u>Please write the text or upload a file foe count words and characters</html></u> .");
        l1.setBounds(50, 15, x + 95, y + 10);
        l1.setFont(new Font("Arial", Font.PLAIN, 16));
        f.add(l1);

        // adding the scrollable Text area in frame.
        JScrollPane Sp = new JScrollPane();
        Sp.setBounds(50, 80, x + 80, y + 160);

        // creating textarea
        JTextArea txtArea = new JTextArea();
        Sp.setViewportView(txtArea);
        f.add(Sp);

        // adding linkable lable to upload file into lable and textarea.
        JLabel l2 = new JLabel("<html><u>ADD File </html></u>  : ");
        l2.setBounds(50, 280, x + 15, y + 10);
        l2.setFont(new Font("Arial", Font.PLAIN, 16));
        l2.setForeground(Color.BLUE);
        f.add(l2);

        // add lable l3 into the frame.
        JLabel l3 = new JLabel();
        l3.setBounds(300, 280, x + 15, y + 10);
        f.add(l3);

        // adding button into the frame.
        JButton btn = new JButton("Count.");
        btn.setBounds(220, 340, x - 250, y - 10);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        f.add(btn);

        // adding another the scrollable Text area in frame.
        JScrollPane Sp1 = new JScrollPane();
        Sp1.setBounds(50, 380, x + 80, y + 160);

        // creating textarea
        JTextArea txtArea1 = new JTextArea();
        Sp1.setViewportView(txtArea1);
        f.add(Sp1);

        // adding event into the lable l2.
        MouseListener ms = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JFileChooser file = new JFileChooser();
                int val = file.showOpenDialog(null);
                if (val == JFileChooser.APPROVE_OPTION) {
                    File selfile = file.getSelectedFile();
                    String fileName = selfile.getName();
                    if (fileName.toLowerCase().endsWith(".txt")) {
                        try {
                            BufferedReader read = new BufferedReader(new FileReader(selfile));
                            StringBuilder fileContent = new StringBuilder();
                            String line;
                            while ((line = read.readLine()) != null) {
                                fileContent.append(line).append("\n");
                            }
                            read.close();
                            txtArea.setText(fileContent.toString());
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(f, ex);
                        }
                    } else {
                        l3.setText("File is not a TXT file.");
                    }
                }
            }
        };

        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (txtArea.getText() != null) {
                    int Wcount = 0, Ccount = 0;
                    String txt = txtArea.getText();
                    String[] words = txt.split("\\s+|\\p{Punct}+");
                    Map<String, Integer> wordFrequency = new HashMap<>();
                    Set<String> stopWords = new HashSet<>(Arrays.asList("your", "stop", "words", "here"));
                    for (String word : words) {
                        word = word.toLowerCase();
                        if (!stopWords.contains(word)) {
                            Wcount++;
                            wordFrequency.put(word, wordFrequency.getOrDefault(word, 0) + 1);
                        }
                    }
                    Ccount = txt.length();
                    l3.setText("Words: " + Wcount + " & " + "Characters: " + Ccount);
                    StringBuilder frequencyText = new StringBuilder("\t\t Word Frequency Statistics:\n");
                    for (Entry<String, Integer> entry : wordFrequency.entrySet()) {
                        frequencyText.append(entry.getKey()).append(": ").append(entry.getValue()).append(" times\n");
                    }
                    txtArea1.setText(frequencyText.toString());
                }
            }
        });

        l2.addMouseListener(ms);
        f.setVisible(true);
    }
}
