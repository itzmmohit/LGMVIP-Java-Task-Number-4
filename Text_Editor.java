import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

public class Text_Editor extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JTextArea textArea;
    private JMenuBar menuBar;
    private JMenu fileMenu, editMenu, viewMenu;
    private JMenuItem newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, exitMenuItem;
    private JMenuItem cutMenuItem, copyMenuItem, pasteMenuItem, selectAllMenuItem;
    private JMenuItem aboutMenuItem, textColorMenuItem, textHighlightMenuItem;
    private File currentFile;
    private JSeparator separator;
    private JMenuItem findMenuItem;
    private JMenuItem replaceMenuItem;
    private JSeparator separator_1;
    private JScrollPane scrollPane;
    private JMenuItem printMenuItem;
    private JSeparator separator_2;


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Text_Editor frame = new Text_Editor();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Text_Editor() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 650);
        setTitle("Text Editor | Mohit Mehta");

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout()); // Set BorderLayout

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        newMenuItem = new JMenuItem("New");
        fileMenu.add(newMenuItem);

        openMenuItem = new JMenuItem("Open");
        fileMenu.add(openMenuItem);

        saveMenuItem = new JMenuItem("Save");
        fileMenu.add(saveMenuItem);

        saveAsMenuItem = new JMenuItem("Save As");
        fileMenu.add(saveAsMenuItem);
        
        separator_2 = new JSeparator();
        fileMenu.add(separator_2);
        
        printMenuItem = new JMenuItem("Print");
        fileMenu.add(printMenuItem);

        fileMenu.addSeparator();

        exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(exitMenuItem);

        editMenu = new JMenu("Edit");
        menuBar.add(editMenu);

        cutMenuItem = new JMenuItem("Cut");
        editMenu.add(cutMenuItem);

        copyMenuItem = new JMenuItem("Copy");
        editMenu.add(copyMenuItem);

        pasteMenuItem = new JMenuItem("Paste");
        editMenu.add(pasteMenuItem);

        editMenu.addSeparator();
        
        findMenuItem = new JMenuItem("Find");
        editMenu.add(findMenuItem);
        
        replaceMenuItem = new JMenuItem("Replace");
        editMenu.add(replaceMenuItem);
        
        separator_1 = new JSeparator();
        editMenu.add(separator_1);

        selectAllMenuItem = new JMenuItem("Select All");
        editMenu.add(selectAllMenuItem);

        viewMenu = new JMenu("View");
        menuBar.add(viewMenu);
        
        textColorMenuItem = new JMenuItem("Text Color");
        textColorMenuItem.addActionListener(this);
        viewMenu.add(textColorMenuItem);
        
        textHighlightMenuItem = new JMenuItem("Text Highlight");
        textHighlightMenuItem.addActionListener(this);
        viewMenu.add(textHighlightMenuItem);
                
        separator = new JSeparator();
        viewMenu.add(separator);
        
        aboutMenuItem = new JMenuItem("About");
        viewMenu.add(aboutMenuItem);
        aboutMenuItem.addActionListener(this);

        textArea = new JTextArea();
        textArea.setLineWrap(true); // Set line wrap to true
        textArea.setWrapStyleWord(true); // Set wrap style word to true
        scrollPane = new JScrollPane(textArea);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        newMenuItem.addActionListener(this);
        openMenuItem.addActionListener(this);
        saveMenuItem.addActionListener(this);
        saveAsMenuItem.addActionListener(this);
        exitMenuItem.addActionListener(this);
        cutMenuItem.addActionListener(this);
        copyMenuItem.addActionListener(this);
        pasteMenuItem.addActionListener(this);
        selectAllMenuItem.addActionListener(this);
        textColorMenuItem.addActionListener(this);
        textHighlightMenuItem.addActionListener(this);
        findMenuItem.addActionListener(this);
        replaceMenuItem.addActionListener(this);
        printMenuItem.addActionListener(this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newMenuItem) {
            newFile();
        } else if (e.getSource() == openMenuItem) {
            openFile();
        } else if (e.getSource() == saveMenuItem) {
            saveFile();
        } else if (e.getSource() == saveAsMenuItem) {
            saveFileAs();
        } else if (e.getSource() == printMenuItem) {
            printText();
        } else if (e.getSource() == exitMenuItem) {
            System.exit(0);
        } else if (e.getSource() == cutMenuItem) {
            textArea.cut();
        } else if (e.getSource() == copyMenuItem) {
            textArea.copy();
        } else if (e.getSource() == pasteMenuItem) {
            textArea.paste();
        } else if (e.getSource() == selectAllMenuItem) {
            textArea.selectAll();
        } else if (e.getSource() == findMenuItem) {
        	showFindDialog();
        } else if (e.getSource() == replaceMenuItem) {
        	showReplaceDialog();
        } else if (e.getSource() == textColorMenuItem) {
            showTextColorDialog();
        } else if (e.getSource() == textHighlightMenuItem) {
            showTextHighlightDialog();
        } else if (e.getSource() == aboutMenuItem) {
        	JOptionPane.showMessageDialog(this,"**Text Editor Application**\nMade By: Mohit Mehta\nEmail: mmehta692@gmail.com\nPhone: 8210925897","About",JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void newFile() {
        currentFile = null;
        textArea.setText("");
        setTitle("Text Editor | Mohit Mehta");
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(currentFile));
                textArea.read(reader, null);
                reader.close();
                setTitle("Text Editor | " + currentFile.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error opening file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFile() {
        if (currentFile != null) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile));
                textArea.write(writer);
                writer.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            saveFileAs();
        }
    }

    private void saveFileAs() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            saveFile();
            setTitle("Text Editor | " + currentFile.getName());
        }
    }
    
    private void showTextColorDialog() {
        Color selectedColor = JColorChooser.showDialog(this, "Select Text Color", textArea.getForeground());
        if (selectedColor != null) {
            textArea.setForeground(selectedColor);
        }
    }
    
    private void showTextHighlightDialog() {
        int start = textArea.getSelectionStart();
        int end = textArea.getSelectionEnd();

        Highlighter highlighter = textArea.getHighlighter();
        DefaultHighlighter.DefaultHighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);

        try {
            // Remove existing highlights first
            Highlighter.Highlight[] existingHighlights = highlighter.getHighlights();
            for (Highlighter.Highlight existingHighlight : existingHighlights) {
                if (existingHighlight.getStartOffset() >= start && existingHighlight.getEndOffset() <= end) {
                    highlighter.removeHighlight(existingHighlight);
                }
            }

            // Add the new highlight
            highlighter.addHighlight(start, end, painter);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
 
    private void showFindDialog() {
        String searchText = JOptionPane.showInputDialog(this, "Find:");
        if (searchText != null && !searchText.isEmpty()) {
            String content = textArea.getText();
            int index = content.indexOf(searchText);
            if (index != -1) {
                textArea.setCaretPosition(index);
                textArea.moveCaretPosition(index + searchText.length());
            } else {
                JOptionPane.showMessageDialog(this, "Text not found.");
            }
        }
    }
    
    private void showReplaceDialog() {
        String searchText = JOptionPane.showInputDialog(this, "Find:");
        if (searchText != null && !searchText.isEmpty()) {
            String replaceText = JOptionPane.showInputDialog(this, "Replace with:");
            if (replaceText != null) {
                String content = textArea.getText();
                content = content.replace(searchText, replaceText);
                textArea.setText(content);
            }
        }
    }
    
    private void printText() {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        if (printerJob.printDialog()) {
            printerJob.setPrintable(textArea.getPrintable(null, null));
            try {
                printerJob.print();
            } catch (PrinterException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error while printing", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
