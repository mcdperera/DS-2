
/**
 * The text line object.
 *
 * @author Charmal
 */
public class Line {

    /**
     * Id property
     */
    public int id = 0;

    /**
     * Last name property
     */
    public String lastName = "";

    /**
     * First name property
     */
    public String firstName = "";

    /**
     * Address property
     */
    public String address = "";

    /**
     * City property
     */
    public String city = "";

    /**
     * State property
     */
    public String state = "";

    /**
     * Zip code property
     */
    public int zipcode = 0;

    /**
     * time property
     */
    public long time = 0;

    /**
     * Construct of the line class.
     */
    public Line() {

    }

    /**
     * Read the text line add create the Line object
     *
     * @param textLine
     */
    public Line(int id, String textLine) {

        this.id = id;
        String[] parts = textLine.split("\\|");

        String name = parts[0];
        String[] nameParts = name.split(" ");

        lastName = nameParts.length > 1 ? nameParts[0].trim() : "";
        firstName = nameParts.length == 2 ? nameParts[1].trim() : "";
        address = parts.length > 2 ? parts[1].trim() : "";
        city = parts.length > 3 ? parts[2].trim() : "";
        state = parts.length > 4 ? parts[3].trim() : "";

        String zip = parts.length == 5 ? parts[4].trim() : "";
        zipcode = !zip.isEmpty() ? Integer.parseInt(zip.replaceAll("\\s+", "")) : 0;

        time = 0;
    }

    /**
     * Returns the printed line object.
     *
     * @return
     */
    public String getLine() {

        String commanSeperator = "|";

        return new StringBuilder()
                //                .append(this.id)
                //                .append(commanSeperator)
                .append(this.lastName).append(" ").append(this.firstName)
                .append(commanSeperator)
                .append(this.address)
                .append(commanSeperator)
                .append(this.city)
                .append(commanSeperator)
                .append(this.state)
                .append(commanSeperator)
                .append(this.zipcode).toString();
//                .append(commanSeperator)
//                .append(this.time).toString();

    }

    public void setTime(long time) {
        this.time += time;
    }

    public long getTime() {
        return this.time;
    }

    public int getId() {
        return this.id;
    }
}
