import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AddressBook implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<BuddyInfo> buddies;
    private List<AddressView> views;

    public AddressBook() {
        buddies = new ArrayList<>();
        views = new ArrayList<>();
    }

    // Adds a buddy and notifies views
    public void addBuddy(BuddyInfo buddy) {
        buddies.add(buddy);
        notifyViews();
    }

    // Removes a buddy and notifies views
    public void removeBuddy(BuddyInfo buddy) {
        buddies.remove(buddy);
        notifyViews();
    }

    // Registers a new view
    public void addView(AddressView view) {
        views.add(view);
    }

    // Unregisters a view
    public void removeView(AddressView view) {
        views.remove(view);
    }

    // Notifies all registered views of changes
    public void notifyViews() {
        for (AddressView view : views) {
            view.update(buddies);  // Calls update on each view, passing the current buddy list
        }
    }

    // Returns a copy of the buddy list (for read-only purposes)
    public List<BuddyInfo> getBuddies() {
        return new ArrayList<>(buddies);
    }


    public void clearAddressBook() {

        buddies.clear();
        notifyViews();
    }

    public void saveAddressBook(String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            for (BuddyInfo buddy : buddies) {
                writer.write(buddy.toString());
                writer.write("\n");
            }
            writer.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();

        }
    }

    public void importAddressBook(String filename) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = in.readLine()) != null) {  // Read each line until the end of the file
                BuddyInfo buddy = BuddyInfo.importBuddyInfo(line);
                buddies.add(buddy);
                notifyViews();
            }
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        } catch (IOException e) {
            throw new RuntimeException("Error reading from file: " + filename, e);
        }
    }

    public void serializeAddressBook(String filename) {
        try {
            FileOutputStream out = new FileOutputStream(filename);
            ObjectOutputStream objOut = new ObjectOutputStream(out);
            objOut.writeObject(this);
            objOut.close();
            out.close();

        } catch (Exception e) {
            System.out.println("Error serializing AddressBook: " + e.getMessage());
        }
    }

    public static AddressBook deserializeAddressBook(String filename) {
        try {
            ObjectInputStream inObj = new ObjectInputStream(new FileInputStream(filename));
            AddressBook ab = (AddressBook) inObj.readObject();
            inObj.close();
            return ab;

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error deserializing AddressBook: " + e.getMessage());

        }
        return null;
    }
    public String toXML(){
        StringBuilder xml = new StringBuilder("<AddressBook>\n");
        for (BuddyInfo buddy : buddies) {
            xml.append(buddy.toXML()).append("\n");
        }
        xml.append("</AddressBook>\n");
        return xml.toString();
    }

    public void exportToXML(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(toXML());
            writer.flush(); // Ensure data is written to the file
        } catch (IOException e) {
            System.out.println("Error exporting AddressBook to XML: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static AddressBook readSAX(File f) throws Exception{
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser s = spf.newSAXParser();
        AddressBook ab = new AddressBook();

        DefaultHandler handler = new DefaultHandler() {
            BuddyInfo buddy = null;
            StringBuilder current = new StringBuilder();

            public void startElement(String uri, String localName, String qName, Attributes attributes) {
                current.setLength(0); // Clear the buffer for new text
                if (qName.equalsIgnoreCase("BuddyInfo")) {
                    buddy = new BuddyInfo("", "", ""); // Create a new BuddyInfo instance
                }
            }

            public void endElement(String uri, String localName, String qName) {
                if (buddy != null) {
                    switch (qName) {
                        case "Name":
                            buddy.setName(current.toString());
                            break;
                        case "Address": // Match XML <Address> tag
                            buddy.setStreet(current.toString());
                            break;
                        case "Phonenumber": // Match XML <Phonenumber> tag
                            buddy.setPhone(current.toString());
                            break;
                        case "BuddyInfo":
                            ab.addBuddy(buddy); // Add the completed BuddyInfo to the AddressBook
                            buddy = null; // Reset for the next BuddyInfo
                            break;
                    }
                }
            }

            public void characters(char[] ch, int start, int length) {
                current.append(new String(ch, start, length).trim()); // Collect text data
            }
        };

        s.parse(f, handler);
        return ab;

    }

}



