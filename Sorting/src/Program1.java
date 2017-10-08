
import java.io.File;

/**
 *
 * @author Charmal
 */
public class Program1 {

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String... args) throws Exception {

        boolean isMixedAlgorithm = true;
        int sortType = 3;

//        if (args.length != 3) {
//            System.out.println("Invalid Usage -- please supply a filename as an argument");
//            System.out.println("`java -jar pgm1-...jar 100.txt 2 1");
//        } else {
        //System.out.println("Please enter a file name (with absolute path)");
        // Scanner filepath = new Scanner(System.in);
        String filename = "100.txt";//filepath.nextLine();
        //String filename = args[0];
        //we have a filename, make sure file exists
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("The file you supplied doesn't seem to exist!");
        } else {
            //start processing
            System.out.println("Found file " + filename);

            String inputFile = filename.split("\\.")[0];

            TextLineSorter textLineSort = new TextLineSorter();

            String lastNameFileName = "last_name_" + filename;
            String fullNameFileName = "name_" + filename;
            String addressFileName = "address_" + filename;

            String firstNameSortTimeFile = "name_" + inputFile + "_time.txt";
            String addressSortTimeFile = "address_" + inputFile + "_time.txt";

            String cityFileName = "city_" + filename;
            String stateFileName = "state_" + filename;
            String zipFileName = "zip_" + filename;

            Sort<Line> sorter = new Insertionsort();

            String algo = "Insertion";

            if (sortType == 2) {
                sorter = new Quicksort();
                algo = "Quick";
            } else if (sortType == 3) {
                sorter = new Radixsort();
                algo = "Radix";
            }

            System.out.println("Your sortinng algo is : " + algo + " combination : " + (isMixedAlgorithm ? " true" : " false"));

            if (isMixedAlgorithm) {
                textLineSort.secondarySorting(filename, ComparatorType.LastName, sorter,
                        lastNameFileName, false, "");

                textLineSort.secondarySorting(lastNameFileName, ComparatorType.FirstName, sorter,
                        fullNameFileName, true, firstNameSortTimeFile);

                new File(lastNameFileName).delete();

                textLineSort.secondarySorting(fullNameFileName, ComparatorType.Address, sorter,
                        addressFileName, true, addressSortTimeFile);

                textLineSort.secondarySorting(addressFileName, ComparatorType.City, sorter,
                        cityFileName, false, "");

                textLineSort.secondarySorting(cityFileName, ComparatorType.State, sorter,
                        stateFileName, false, "");

                textLineSort.secondarySorting(stateFileName, ComparatorType.ZipCode, sorter,
                        zipFileName, false, "");
            } else {
                textLineSort.primarySorting(filename, ComparatorType.LastName, sorter,
                        lastNameFileName, false, "");

                textLineSort.primarySorting(lastNameFileName, ComparatorType.FirstName, sorter,
                        fullNameFileName, true, firstNameSortTimeFile);

                new File(lastNameFileName).delete();

                textLineSort.primarySorting(fullNameFileName, ComparatorType.Address, sorter,
                        addressFileName, true, addressSortTimeFile);

                textLineSort.primarySorting(addressFileName, ComparatorType.City, sorter,
                        cityFileName, false, "");

                textLineSort.primarySorting(cityFileName, ComparatorType.State, sorter,
                        stateFileName, false, "");

                textLineSort.primarySorting(stateFileName, ComparatorType.ZipCode, sorter,
                        zipFileName, false, "");
            }

        }
        //}
    }

}
