
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class CSVLoader {

    /*
	 * function getList() returns an unordered list of Donor objects
         * from file
     */
    public static LinkedList<Donor> getList(String path) {
        // create linked list to hold the results
        LinkedList<Donor> records = new LinkedList<>();
        // create string to temporarily hold each row from the file
        String lineContents = null;
        String[] tokens = new String[5];

        try {
            // create the input stream
            BufferedReader buffRead = new BufferedReader(new FileReader(path));

            while ((lineContents = buffRead.readLine()) != null) {
                // while there are still lines in file
                // add line to linked list
                tokens = lineContents.split(",");
                Donor currentDonor = new Donor(
                        Integer.parseInt(tokens[5]),
                        tokens[0],
                        tokens[1],
                        tokens[2],
                        Long.parseLong(tokens[3]),
                        Double.parseDouble(tokens[4]));
                records.add(currentDonor);
            }

            buffRead.close(); // close the file stream

        } catch (IOException ioe) { // if the file was not found or was unreadable
            System.err.println("There was an error: " + ioe);
        }

        return records; // return the linked list containing all records
    } // END getList()

    public static void saveList(String path, LinkedList<Donor> donors) {
        try (BufferedWriter buffWrite = new BufferedWriter(new FileWriter(path))) {
            donors.forEach(donor -> {
                try {
                    buffWrite.write(donor.toString() + "\n");
                } catch (IOException ioe) {
                    System.err.println("Error: " + ioe);
                }
            });

        } catch (IOException ioe) {
            System.err.println("Error: " + ioe);
        }
    } // END saveList

} // END CSVLoader CLASS
