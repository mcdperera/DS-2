
/**
 * The text record object.
 *
 * @author Avinash G
 */
public class Record {

    public String lastName = "";
    public String firstName = "";
    public String address = "";
    public String city = "";
    public String state = "";
    public int zipcode = 0;

    public Record() {

    }

    public Record(String texttoSplit) {

        String[] parts = texttoSplit.split("\\|");

        String name = parts[0];
        
        lastName = name.split(" ")[0].trim();
        firstName = name.split(" ")[1].trim();
        address = parts[1].trim();
        city = parts[2].trim();
        state = parts[3].trim();
        zipcode = Integer.parseInt(parts[4].trim().replaceAll("\\s+", ""));
    }

    public String getRecord() {

        String commanSeperator = "| ";

        return new StringBuilder()
                .append(this.lastName).append(" ").append(this.firstName)
                .append(commanSeperator)
                .append(this.address)
                .append(commanSeperator)
                .append(this.city)
                .append(commanSeperator)
                .append(this.state)
                .append(commanSeperator)
                .append(this.zipcode).toString();
    }

}
