import static org.junit.Assert.*;

public class AddressBookTest {
    AddressBook ab = new AddressBook();
    BuddyInfo buddy1 = new BuddyInfo("sandy", "12345","123");
    AddressBook ab2 = new AddressBook();



    @org.junit.Test
    public void saveAddressBook() {
        ab.addBuddy(buddy1);
        ab.saveAddressBook("test1.txt");
        ab2.importAddressBook("test1.txt");


        // Step 4: Assert that both AddressBooks contain the same data
        assertEquals("AddressBooks should have the same number of buddies", ab.getBuddies().size(), ab2.getBuddies().size());

        // Check that each BuddyInfo entry is the same by comparing the toString output
        for (int i = 0; i < ab.getBuddies().size(); i++) {
           assertEquals("BuddyInfo entries should match", ab.getBuddies().get(i).toString(), ab2.getBuddies().get(i).toString());
        }

    }
    @org.junit.Test
    public void serializeAddressBook() {
        ab.addBuddy(buddy1);
        ab.saveAddressBook("test1.txt");
        ab2.importAddressBook("test1.txt");

        ab.serializeAddressBook("test1.txt");
        ab.deserializeAddressBook("test1.txt");


        assertEquals("AddressBooks should have the same number of buddies", ab.getBuddies().size(), ab2.getBuddies().size());

        // Check that each BuddyInfo entry is the same by comparing the toString output
        for (int i = 0; i < ab.getBuddies().size(); i++) {
            assertEquals("BuddyInfo entries should match", ab.getBuddies().get(i).toString(), ab2.getBuddies().get(i).toString());
        }
    }

}