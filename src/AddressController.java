import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class AddressController implements ActionListener {

    private AddressBook addressBook;

    public AddressController(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "Add Buddy":
                addBuddy();
                break;
            case "Remove Buddy":
                removeBuddy();
                break;
            case "New AddressBook":
                createNewAddressBook();
                break;
            case "Export":
                saveAddressBook();
                break;
            case "Import":
                importAddressBook();
                break;
            case "Serialize":
                serializeAddressBook();
                break;
            case "Deserialize":
                deserializeAddressBook();
                break;
            case "Export XML":
                exportXML();
                break;
            case "Import XML":
                importXML();
                break;
            case "Clear":
                clear();
            default:
                System.out.println("Unknown command: " + command);
        }
    }

    private void addBuddy() {
        String name = JOptionPane.showInputDialog(null, "Enter Buddy Name:");
        String phone = JOptionPane.showInputDialog(null, "Enter Buddy Phone:");
        String address = JOptionPane.showInputDialog(null, "Enter Buddy Address:");

        if (name != null && phone != null && !name.isEmpty() && !phone.isEmpty()) {
            BuddyInfo buddy = new BuddyInfo(name, phone, address);
            addressBook.addBuddy(buddy);
        } else {
            JOptionPane.showMessageDialog(null, "Buddy information cannot be empty!");
        }
    }

    private void removeBuddy() {
        if (addressBook.getBuddies().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No buddies to remove.");
            return;
        }

        String name = JOptionPane.showInputDialog(null, "Enter Buddy Name to Remove:");
        if (name != null && !name.isEmpty()) {
            BuddyInfo buddyToRemove = null;
            for (BuddyInfo buddy : addressBook.getBuddies()) {
                if (buddy.getName().equals(name)) {
                    buddyToRemove = buddy;
                    break;
                }
            }

            if (buddyToRemove != null) {
                addressBook.removeBuddy(buddyToRemove);
            } else {
                JOptionPane.showMessageDialog(null, "Buddy not found.");
            }
        }
    }

    private void createNewAddressBook() {
        addressBook.clearAddressBook();
        JOptionPane.showMessageDialog(null, "New AddressBook created!");
    }

    private void saveAddressBook() {
        // Placeholder for saving logic, e.g., save to a file
        String name = JOptionPane.showInputDialog(null, "Enter FileName to Save to:");
        if (name != null && !name.isEmpty()) {
            addressBook.saveAddressBook(name);
        }
    }

    private void importAddressBook() {
        String name = JOptionPane.showInputDialog(null, "Enter FileName to Import:");
        if (name != null && !name.isEmpty()) {
            addressBook.importAddressBook(name);
        }
    }

    private void serializeAddressBook() {
        String name = JOptionPane.showInputDialog(null, "Enter FileName to Serialize:");
        if (name != null && !name.isEmpty()) {
            addressBook.serializeAddressBook(name);
        }
    }

    private void deserializeAddressBook() {
        String name = JOptionPane.showInputDialog(null, "Enter FileName to Deserialize:");
        if (name != null && !name.isEmpty()) {
            AddressBook deserializedAddressBook = AddressBook.deserializeAddressBook(name);
            if (deserializedAddressBook != null) {
                this.addressBook = deserializedAddressBook;  // Update to use the deserialized addressBook
                JOptionPane.showMessageDialog(null, "AddressBook deserialized successfully from " + name);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to deserialize AddressBook.");
            }
        }

    }

    private void exportXML() {
        String name = JOptionPane.showInputDialog(null, "Enter FileName to Export:");
        if (name != null && !name.isEmpty()) {
            addressBook.exportToXML(name);
        }
    }

    private void importXML() {
        String filename = JOptionPane.showInputDialog(null, "Enter FileName to Import:");
        if (filename != null && !filename.isEmpty()) {
            try {
                File file = new File(filename);
                AddressBook importedAddressBook = AddressBook.readSAX(file);

                if (importedAddressBook != null) {
                    addressBook.clearAddressBook(); // Clear the current AddressBook
                    for (BuddyInfo buddy : importedAddressBook.getBuddies()) {
                        addressBook.addBuddy(buddy); // Add buddies to the current AddressBook
                    }
                    addressBook.notifyViews(); // Notify views after updating the AddressBook
                    JOptionPane.showMessageDialog(null, "AddressBook imported successfully from " + filename);
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to import AddressBook.");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error importing AddressBook: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void clear() {
        addressBook.clearAddressBook();
    }
}

