/*
 * 
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.util.Collections;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.border.EmptyBorder;

public class Contributors extends JFrame {

    // Create the GUI Objects
    // panels
    private JPanel pnlMain = new JPanel();
    private JPanel pnlLoadFile = new JPanel();
    private JPanel pnlButtons = new JPanel();
    private JPanel pnlStatus = new JPanel();

    // labels
    private JLabel lblStatus = new JLabel();

    // text boxes
    private JTextField tfPathToCSV = new JTextField();
    private JTextArea taRecords = new JTextArea();
    private JScrollPane spRecords = new JScrollPane(taRecords);

    // buttons
    private JButton btnSelectCSV = new JButton("Load CSV File");
    private JButton btnSave = new JButton("Save Changes");
    private JButton btnLookUpName = new JButton("Search By Name");
    private JButton btnLookUpID = new JButton("Search By ID");
    private JButton btnPrint = new JButton("Print All");
    private JButton btnAdd = new JButton("Add (Push) Donor");
    private JButton btnPopByID = new JButton("Pop By ID");
    private JButton btnPopByName = new JButton("Pop By Name");
    private JButton btnFibonacci = new JButton("Fibonacci Demo");
    private JButton btnQuit = new JButton("Quit");

    // menus
    private JMenuBar mbMain = new JMenuBar();
    private JMenu muFile = new JMenu("File");
    private JMenuItem miQuit = new JMenuItem("Quit");
    private JMenuItem miLoadCSV = new JMenuItem("Load CSV File...");
    private JMenuItem miSave = new JMenuItem("Save Changes");
    private JMenu muEdit = new JMenu("Edit");
    private JMenuItem miPrintAll = new JMenuItem("Print All");
    private JMenuItem miPush = new JMenuItem("Add/Push Entry...");
    private JMenuItem miPopByID = new JMenuItem("Pop By ID...");
    private JMenuItem miPopByName = new JMenuItem("Pop By Name...");
    private JMenu muSearch = new JMenu("Search");
    private JMenuItem miLookUpName = new JMenuItem("Find By Name");
    private JMenuItem miLookUpID = new JMenuItem("Find By ID");
    private JMenu muHelp = new JMenu("Help");
    private JMenuItem miAbout = new JMenuItem("About");

    // Create the linked list of Donor objects
    static LinkedList<Donor> contributions = new LinkedList<>();

    // remember original filepath
    private String path;

    public Contributors() { // constructor
        // Set the window title
        super("Unit 5 Individual Project");

        lblStatus.setText("Application loaded.");

        // action events for menu items
        miPrintAll.addActionListener(e -> printList());

        miLoadCSV.addActionListener(e -> {
            // get path to CSV file from user
            lblStatus.setText("Loading list...");
            try {
                this.path = selectFile().getAbsolutePath();
                // instantiate a CSVLoader object and feed the path to the constructor
                taRecords.append("\nLOADING LIST...\n");
                lblStatus.setText("List loaded.");
                // load the CSV file records into the contributions linked list
                contributions = CSVLoader.getList(path);
                // set the ftPathToCSV text to the path of the CSV file
                tfPathToCSV.setText(path);
                // sort the list
                sort(contributions);
                // display in taRecords (text area)
                printList();
            } catch (NullPointerException npe) {
                System.err.println("Error: Invalid file path.");
                taRecords.append("\nError: Invalid file path.\n");
                lblStatus.setText("Load failed.");
            }
        });

        miSave.addActionListener(e -> {
            lblStatus.setText("Saving list...");
            try {
                CSVLoader.saveList(path, contributions);
                taRecords.append("\nLIST SAVED.\n");
                lblStatus.setText("List saved.");
            } catch (NullPointerException npe) {
                System.err.println("Error: Invalid file path.");
                taRecords.append("\nError: Invalid file path.\n");
                lblStatus.setText("Save failed.");
            }
        });

        miQuit.addActionListener(e -> System.exit(0)); // quit application

        miPush.addActionListener(e -> {
            // get input values from user
            String firstName = JOptionPane.showInputDialog("Enter a first name:");
            String lastName = JOptionPane.showInputDialog("Enter a last name:");
            String country = JOptionPane.showInputDialog("Enter country:");
            long phone = Long.parseLong(JOptionPane.showInputDialog("Enter 10 digit "
                    + "phone number (numbers only no spaces):"));
            double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter amount "
                    + "as integer (ex: 150):"));
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter ID: "));

            // push entry to top list
            addEntry(firstName, lastName, country, phone, amount, id);
        });

        miPopByID.addActionListener(e -> {
            popByID(Integer.parseInt(JOptionPane.showInputDialog("Enter the ID of donor.")));
        });

        miPopByName.addActionListener(e -> {
            popByName(JOptionPane.showInputDialog("Enter the last name of donor."));
        });

        miLookUpName.addActionListener(e -> {
            lookUpByName(JOptionPane.showInputDialog("Enter the last name of donor."));
        });

        miLookUpID.addActionListener(e -> {
            try {
                lookUpByID(Integer.parseInt(JOptionPane.showInputDialog("Enter record ID")));
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid number.");
                taRecords.append("\nError: Input must be a valid number.\n");
            }
        });

        miAbout.addActionListener(e -> new AboutWindow());

        // construct menus
        muFile.add(miLoadCSV);
        muFile.add(miSave);
        muFile.addSeparator();
        muFile.add(miQuit);

        muEdit.add(miPrintAll);
        muEdit.addSeparator();
        muEdit.add(miPush);
        muEdit.addSeparator();
        muEdit.add(miPopByID);
        muEdit.add(miPopByName);

        muSearch.add(miLookUpID);
        muSearch.add(miLookUpName);

        muHelp.add(miAbout);

        mbMain.add(muFile);
        mbMain.add(muEdit);
        mbMain.add(muSearch);
        mbMain.add(muHelp);

        // add action listeners for buttons
        btnSelectCSV.addActionListener(e -> {
            // get path to CSV file from user
            lblStatus.setText("Loading list...");
            try {
                this.path = selectFile().getAbsolutePath();
                // instantiate a CSVLoader object and feed the path to the constructor
                taRecords.append("\nLOADING LIST...\n");
                lblStatus.setText("List loaded.");
                // load the CSV file records into the contributions linked list
                contributions = CSVLoader.getList(path);
                // set the ftPathToCSV text to the path of the CSV file
                tfPathToCSV.setText(path);
                // sort the list
                sort(contributions);
                // display in taRecords (text area)
                printList();
            } catch (NullPointerException npe) {
                System.err.println("Error: Invalid file path.");
                taRecords.append("\nError: Invalid file path.\n");
                lblStatus.setText("Load failed.");
            }
        });

        btnSave.addActionListener(e -> {
            lblStatus.setText("Saving list...");
            try {
                CSVLoader.saveList(path, contributions);
                taRecords.append("\nLIST SAVED.\n");
                lblStatus.setText("List saved.");
            } catch (NullPointerException npe) {
                System.err.println("Error: Invalid file path.");
                taRecords.append("\nError: Invalid file path.\n");
                lblStatus.setText("Save failed.");
            }
        });

        btnLookUpName.addActionListener(e -> {
            lookUpByName(JOptionPane.showInputDialog("Enter the last name of donor."));
        });

        btnLookUpID.addActionListener(e -> {
            try {
                lookUpByID(Integer.parseInt(JOptionPane.showInputDialog("Enter record ID")));
            } catch (NumberFormatException nfe) {
                System.err.println("Invalid number.");
                taRecords.append("\nError: Input must be a valid number.\n");
            }
        });

        btnAdd.addActionListener(e -> {
            // get input values from user
            String firstName = JOptionPane.showInputDialog("Enter a first name:");
            String lastName = JOptionPane.showInputDialog("Enter a last name:");
            String country = JOptionPane.showInputDialog("Enter country:");
            long phone = Long.parseLong(JOptionPane.showInputDialog("Enter 10 digit "
                    + "phone number (numbers only no spaces):"));
            double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter amount "
                    + "as integer (ex: 150):"));
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter ID: "));

            // push entry to top list
            addEntry(firstName, lastName, country, phone, amount, id);
        });

        btnPopByName.addActionListener(e -> {
            popByName(JOptionPane.showInputDialog("Enter the last name of donor."));
        });

        btnPopByID.addActionListener(e -> {
            try {
                popByID(Integer.parseInt(JOptionPane.showInputDialog("Enter the ID of donor.")));
            } catch (NumberFormatException nfe) {
                System.err.println("Error: Invalid number input.");
                taRecords.append("\nError: Input must be a valid number.\n");
            }
        });

        btnQuit.addActionListener(e -> System.exit(0));

        btnPrint.addActionListener(e -> printList());
        
        btnFibonacci.addActionListener(e -> {
            taRecords.append("\nFIBONACCI SEQUENCE DEMONSTRATION\n");
            taRecords.append("\t" + fibonacciDemo() + "\n");
        });

        // Arrange GUI objects and add to panel/frame
        pnlLoadFile.setLayout(new BorderLayout());
        pnlLoadFile.add(btnSelectCSV, BorderLayout.WEST);
        pnlLoadFile.add(tfPathToCSV, BorderLayout.CENTER);

        taRecords.setEditable(false);
        taRecords.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        spRecords.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // pnlButtons will be 2 rows and 4 columns
        pnlButtons.setLayout(new GridLayout(9, 1));

        pnlButtons.add(btnPrint);
        pnlButtons.add(btnLookUpName);
        pnlButtons.add(btnLookUpID);
        pnlButtons.add(btnAdd);
        pnlButtons.add(btnPopByName);
        pnlButtons.add(btnPopByID);
        pnlButtons.add(btnSave);
        pnlButtons.add(btnFibonacci);
        pnlButtons.add(btnQuit);

        pnlMain.setLayout(new BorderLayout());
        pnlMain.setBorder(new EmptyBorder(4, 4, 4, 4));
        pnlMain.add(pnlLoadFile, BorderLayout.NORTH);
        pnlMain.add(spRecords, BorderLayout.CENTER);
        pnlMain.add(pnlButtons, BorderLayout.EAST);

        pnlStatus.setLayout(new BorderLayout());
        pnlStatus.setBorder(new EmptyBorder(4, 4, 4, 4));
        pnlStatus.add(lblStatus, BorderLayout.CENTER);

        // Settings for JFrame
        this.setLayout(new BorderLayout());
        this.add(mbMain, BorderLayout.NORTH);
        this.add(pnlMain, BorderLayout.CENTER);
        this.add(pnlStatus, BorderLayout.SOUTH);

        this.setPreferredSize(new Dimension(800, 600));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

    } // END constructor

    public static void main(String[] args) {
        new Contributors(); // run program by instantiating a Contributors object
    }

    /*
	 * function selectFile() get and return file object from user selection.
     */
    private File selectFile() {

        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(this);
        File selectedFile = null;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            selectedFile = fc.getSelectedFile();
        }
        return selectedFile;
    }

    private void sort(LinkedList<Donor> listToSort) {
        // Add Comparator Implemetation using the Lambda method:
        taRecords.append("\nSORTING LIST...\n");
        lblStatus.setText("Sorting list...");
        Collections.sort(contributions, (Donor d1, Donor d2) -> {
            return d1.getLastName().compareTo(d2.getLastName());
        });
        lblStatus.setText("Sort completed.");
    }

    /*
	 * function formatRow() takes a record(row) as an argument and splits it into 
	 * an array (columns) based on the delimiter "," Then formats the string using 
	 * printf and returns the formatted line.
	 * 
     */
    private String formatRow(String row) {
        // create local variables
        String[] columns; // array to hold columns
        String firstName;
        String lastName;
        String country;
        long phone;
        double amount;
        int id;
        // create the format string for printf
        String format = " | %3d | %10s | %10s | %10s | %10d | %8.2f |%n";
        // create the outputLine string
        String outputLine;

        columns = row.split(","); // split row into columns
        // assign the appropriate values and cast types
        firstName = columns[0];
        lastName = columns[1];
        country = columns[2];
        phone = Long.parseLong(columns[3]);
        amount = Double.parseDouble(columns[4]);
        id = Integer.parseInt(columns[5]);
        // format the outputLine string
        outputLine = String.format(format, id, firstName, lastName, country, phone, amount);

        return outputLine; // output the formatted record
    }

    /*
     * function addEntry() adds another Donor object to the list and displays 
     * the record details in taRecords text area.
     */
    private void addEntry(String firstName,
            String lastName,
            String country,
            long phone,
            double amount,
            int id) {

        String recordToAdd = firstName + ','
                + lastName + ','
                + country + ','
                + phone + ','
                + amount + ','
                + id;
        Donor newDonor = new Donor(
                id,
                firstName,
                lastName,
                country,
                phone,
                amount);

        // add new entry to the list
        contributions.add(newDonor);
        // make sure the new entry is in the correct order
        sort(contributions);
        // format and display the record
        taRecords.append("\nADDED TO THE LIST\n");
        taRecords.append(" +-----+------------+------------+------------+------------+----------+\n");
        taRecords.append(String.format(" | %3s | %10s | %10s | %10s | %10s | %8s |%n",
                "ID", "First Name", "Last Name", "Country", "Phone #", "Amount"));
        taRecords.append(" +-----+------------+------------+------------+------------+----------+\n");

        taRecords.append(formatRow(recordToAdd));
        taRecords.append(" +-----+------------+------------+------------+------------+----------+\n");
        lblStatus.setText("New entry added.");
    }

    /*
    popByID() Finds an entry by the ID field and removes it from the list
     */
    private void popByID(int ID) {

        Donor poppedRecord = null;
        lblStatus.setText("Finding record to pop.");
        for (int i = 0; i < contributions.size(); i++) {
            if (contributions.get(i).getID() == ID) {
                poppedRecord = contributions.get(i);
                contributions.remove(i);
            }
        }

        if (poppedRecord != null) {
            taRecords.append("\nPOPPED FROM LIST (BY ID):\n");
            taRecords.append(" +-----+------------+------------+------------+------------+----------+\n");
            taRecords.append(String.format(" | %3s | %10s | %10s | %10s | %10s | %8s |%n",
                    "ID", "First Name", "Last Name", "Country", "Phone #", "Amount"));
            taRecords.append(" +-----+------------+------------+------------+------------+----------+\n");
            taRecords.append(formatRow(poppedRecord.toString()));
            taRecords.append(" +-----+------------+------------+------------+------------+----------+\n");
            lblStatus.setText("Record popped.");
        } else {
            taRecords.append("\nPOP FAILED: NO RECORDS WERE FOUND\n");
            lblStatus.setText("Pop failed.");
        }
    }

    /*
    popByName() Finds an entry by the ID field and removes it from the list
     */
    private void popByName(String lastName) {

        Donor poppedRecord = null;
        lblStatus.setText("Finding record to pop...");
        for (int i = 0; i < contributions.size(); i++) {
            if (contributions.get(i).getLastName().equals(lastName)) {
                poppedRecord = contributions.get(i);
                contributions.remove(i);
            }
        }

        if (poppedRecord != null) {
            taRecords.append("\nPOPPED FROM LIST (BY NAME):\n");
            taRecords.append(" +-----+------------+------------+------------+------------+----------+\n");
            taRecords.append(String.format(" | %3s | %10s | %10s | %10s | %10s | %8s |%n",
                    "ID", "First Name", "Last Name", "Country", "Phone #", "Amount"));
            taRecords.append(" +-----+------------+------------+------------+------------+----------+\n");
            taRecords.append(formatRow(poppedRecord.toString()));
            taRecords.append(" +-----+------------+------------+------------+------------+----------+\n");
            lblStatus.setText("Record popped.");
        } else {
            taRecords.append("\nPOP FAILED: NO RECORDS WERE FOUND\n");
            lblStatus.setText("Pop failed.");
        }
    }

    /*
     * lookUpByName() finds and displays a record in the list using the last name
     */
    private void lookUpByName(String lastName) {
        Donor recordFound = null;

        lblStatus.setText("Begining search...");
        for (int i = 0; i < contributions.size(); i++) {
            if (contributions.get(i).getLastName().equals(lastName)) {
                recordFound = contributions.get(i);
            }
        }

        if (recordFound != null) {
            taRecords.append("\nRECORD LOOKUP BY NAME:\n");
            taRecords.append(" +-----+------------+------------+------------+------------+----------+\n");
            taRecords.append(String.format(" | %3s | %10s | %10s | %10s | %10s | %8s |%n",
                    "ID", "First Name", "Last Name", "Country", "Phone #", "Amount"));
            taRecords.append(" +-----+------------+------------+------------+------------+----------+\n");
            taRecords.append(formatRow(recordFound.toString()));
            taRecords.append(" +-----+------------+------------+------------+------------+----------+\n");
            lblStatus.setText("Search completed.");
        } else {
            taRecords.append("\nSEARCH FAILED: NO RECORDS WERE FOUND\n");
            lblStatus.setText("Search failed.");
        }

    }

    /*
     * lookUpByID() finds and displays a record in the list using the record ID
     */
    private void lookUpByID(int ID) {
        Donor recordFound = null;

        lblStatus.setText("Beginning search...");
        for (int i = 0; i < contributions.size(); i++) {
            if (contributions.get(i).getID() == ID) {
                recordFound = contributions.get(i);
            }
        }

        if (recordFound != null) {
            taRecords.append("\nRECORD LOOKUP BY ID:\n");
            taRecords.append(" +-----+------------+------------+------------+------------+----------+\n");
            taRecords.append(String.format(" | %3s | %10s | %10s | %10s | %10s | %8s |%n",
                    "ID", "First Name", "Last Name", "Country", "Phone #", "Amount"));
            taRecords.append(" +-----+------------+------------+------------+------------+----------+\n");
            taRecords.append(formatRow(recordFound.toString()));
            taRecords.append(" +-----+------------+------------+------------+------------+----------+\n");
        } else {
            taRecords.append("\nSEARCH FAILED: NO RECORDS WERE FOUND\n");
        }
        lblStatus.setText("Search completed.");
    }

    /*
     * function printTable() display formatted records, ordered by most recent 
     * records first (i.e. stacked top to bottom)
     */
    private void printList() {
        lblStatus.setText("Printing list...");
        taRecords.append("\nPRINT THE LIST:\n");
        taRecords.append(" +-----+------------+------------+------------+------------+----------+\n");
        taRecords.append(String.format(" | %3s | %10s | %10s | %10s | %10s | %8s |%n",
                "ID", "First Name", "Last Name", "Country", "Phone #", "Amount"));
        taRecords.append(" +-----+------------+------------+------------+------------+----------+\n");

        // NEW LAMBDA forEach LOOP IN ASCENDING ORDER
        contributions.forEach(record -> {
            taRecords.append(formatRow(record.toString()));
        });
        taRecords.append(" +-----+------------+------------+------------+------------+----------+\n");
        lblStatus.setText("List printed.");
    }
    
    /*
        Fibonacci Demonstration
    */
    private String fibonacciDemo() {
        int n = Integer.parseInt(JOptionPane.showInputDialog("Enter an integer to apply the Fibonacci sequence to."));
        int f = 0, g = 1;
        String output = "";
        
        for (int i = 1; i <= n; i++) {
            f = f + g;
            g = f - g;
            output += f;
            if(i < n) {
                output += "-";
            }
        }
        lblStatus.setText("Fibonacci sequence completed.");
        return output;
    }
} // END Contributors CLASS
