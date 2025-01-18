import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Frame extends JFrame implements AddressView {

    private AddressBook addressBook;
    private JList<BuddyInfo> buddyList;
    private DefaultListModel<BuddyInfo> listModel;

    public Frame() {
        super("AddressBook");

        // Initialize address book and controller
        addressBook = new AddressBook();
        AddressController controller = new AddressController(addressBook);
        addressBook.addView(this); // Register this frame as a view

        // Set up frame properties
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 300);
        this.setLayout(new BorderLayout());

        // Menu bar setup
        JMenuBar menuBar = new JMenuBar();

        // AddressBook menu
        JMenu addressMenu = new JMenu("AddressBook");
        JMenuItem newAddressItem = new JMenuItem("New AddressBook");
        newAddressItem.setActionCommand("New AddressBook");
        newAddressItem.addActionListener(controller);

        JMenuItem saveAddressItem = new JMenuItem("Save AddressBook");
        saveAddressItem.setActionCommand("Save AddressBook");
        saveAddressItem.addActionListener(controller);

        addressMenu.add(newAddressItem);
        addressMenu.add(saveAddressItem);
        //menuBar.add(addressMenu);

        JMenuItem exportAddressItem = new JMenuItem("Export");
        exportAddressItem.setActionCommand("Export");
        exportAddressItem.addActionListener(controller);

        addressMenu.add(exportAddressItem);
        menuBar.add(addressMenu);

        JMenuItem importAddressItem = new JMenuItem("Import");
        importAddressItem.setActionCommand("Import");
        importAddressItem.addActionListener(controller);
        addressMenu.add(importAddressItem);

        JMenuItem serializeAddressitem = new JMenuItem("Serialize");
        serializeAddressitem.setActionCommand("Serialize");
        serializeAddressitem.addActionListener(controller);
        addressMenu.add(serializeAddressitem);

        JMenuItem deserializeAddressItem = new JMenuItem("Deserialize");
        deserializeAddressItem.setActionCommand("Deserialize");
        deserializeAddressItem.addActionListener(controller);
        addressMenu.add(deserializeAddressItem);

        JMenuItem exportXMLItem = new JMenuItem("Export XML");
        exportXMLItem.setActionCommand("Export XML");
        exportXMLItem.addActionListener(controller);
        addressMenu.add(exportXMLItem);

        JMenuItem importXMLItem = new JMenuItem("Import XML");
        importXMLItem.setActionCommand("Import XML");
        importXMLItem.addActionListener(controller);
        addressMenu.add(importXMLItem);

        JMenuItem clearAddressItem = new JMenuItem("Clear");
        clearAddressItem.setActionCommand("Clear");
        clearAddressItem.addActionListener(controller);
        addressMenu.add(clearAddressItem);

        // Buddy menu
        JMenu buddyMenu = new JMenu("Buddy");
        JMenuItem addBuddyItem = new JMenuItem("Add Buddy");
        addBuddyItem.setActionCommand("Add Buddy");
        addBuddyItem.addActionListener(controller);

        JMenuItem removeBuddyItem = new JMenuItem("Remove Buddy");
        removeBuddyItem.setActionCommand("Remove Buddy");
        removeBuddyItem.addActionListener(controller);

        buddyMenu.add(addBuddyItem);
        buddyMenu.add(removeBuddyItem);
        menuBar.add(buddyMenu);

        this.setJMenuBar(menuBar);

        // Buddy list setup
        listModel = new DefaultListModel<>();
        buddyList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(buddyList);
        this.add(scrollPane, BorderLayout.CENTER);

        this.setVisible(true);
    }

    @Override
    public void update(List<BuddyInfo> buddies) {
        listModel.clear(); // Clear current contents
        for (BuddyInfo buddy : buddies) {
            listModel.addElement(buddy); // Add each BuddyInfo to the model
        }
    }

    @Override
    public void updateAddressBookImport(List<BuddyInfo> buddies) {

    }


    public static void main(String[] args) {
        // Directly create an instance of Frame
        Frame frame = new Frame();
    }
}
