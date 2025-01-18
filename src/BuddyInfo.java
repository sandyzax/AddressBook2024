import java.io.Serializable;
import java.util.Scanner;

public class BuddyInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String phone;
    private String street;

    public BuddyInfo(String name, String phoneNumber, String streetNumber) {
        this.name = name;
        this.phone = phoneNumber;
        this.street = streetNumber;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPhone(String phoneNumber) {
        this.phone = phoneNumber;
    }
    public void setStreet(String streetNumber) {
        this.street = streetNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phone;
    }

    public String getStreet() {
        return street;
    }


    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Mr. ").append(name).append("#").append(street).append("#").append(phone);
        return sb.toString(); // Display the buddy's name in the JList
    }

    public static BuddyInfo importBuddyInfo(String line) {

        if(line.startsWith("Mr. ")) {
            line = line.replace("Mr. ", "");
        }

        Scanner scanner = new Scanner(line);
        scanner.useDelimiter("#");
        String name = scanner.hasNext() ? scanner.next() : "";
        String street = scanner.hasNext() ? scanner.next() : "";
        String phone = scanner.hasNext() ? scanner.next() : "";
        scanner.close();

        return new BuddyInfo(name,phone,street);
    }

    public String toXML(){
        return String.format(
                "  <BuddyInfo>\n"+
                "       <Name>%s</Name>\n"  +
                "       <Address>%s</Address>\n" +
                "       <Phonenumber>%s</Phonenumber>\n"  +
                "  </BuddyInfo>",
                name, street, phone);



    }

}
