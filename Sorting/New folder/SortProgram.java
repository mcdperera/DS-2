
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Avinash G
 */
public class SortProgram {

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String... args) throws Exception {

//        if (args.length != 3) {
//            System.out.println("Invalid Usage -- please supply a filename as an argument");
//            System.out.println("`java -jar pgm1-...jar 100.txt 2 1");
        //} else {
        //System.out.println("Please enter a file name (with absolute path)");
        // Scanner filepath = new Scanner(System.in);
        String filename = (args[0].isEmpty() ? "100.txt" : args[0]);//"1000.txt";//filepath.nextLine();
        boolean isCombinationOfAlgorithm = (!args[1].isEmpty()
                && "1".equals(args[1]));

        //String filename = args[0];
        //we have a filename, make sure file exists
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("The file not found");
        } else {

            String inputFile = filename.split("\\.")[0];

            RecordSorter recordSort = new RecordSorter();

            String lastNameFileName = "lastname_" + filename;
            String fullNameFileName = "name_" + filename;
            String addressFileName = "address_" + filename;

            String firstNameSortTimeFile = "name_" + inputFile + "_time.txt";
            String addressSortTimeFile = "address_" + inputFile + "_time.txt";

            String cityFileName = "city_" + filename;
            String stateFileName = "state_" + filename;
            String zipFileName = "zip_" + filename;

            if (isCombinationOfAlgorithm) {
                recordSort.combinationSorting(filename, FieldCompareType.lastname,
                        lastNameFileName);

                writePerformance(firstNameSortTimeFile,
                        recordSort.combinationSorting(lastNameFileName,
                                FieldCompareType.firstname, fullNameFileName));

                writePerformance(addressSortTimeFile,
                        recordSort.combinationSorting(fullNameFileName,
                                FieldCompareType.address, addressFileName));

                recordSort.combinationSorting(addressFileName, FieldCompareType.city,
                        cityFileName);

                recordSort.combinationSorting(cityFileName, FieldCompareType.state,
                        stateFileName);

                recordSort.combinationSorting(stateFileName, FieldCompareType.zipcode,
                        zipFileName);
            } else {
                recordSort.Sorting(filename, FieldCompareType.lastname,
                        lastNameFileName);

                writePerformance(firstNameSortTimeFile,
                        recordSort.Sorting(lastNameFileName,
                                FieldCompareType.firstname, fullNameFileName));

                writePerformance(addressSortTimeFile,
                        recordSort.Sorting(fullNameFileName,
                                FieldCompareType.address, addressFileName));

                recordSort.Sorting(addressFileName, FieldCompareType.city,
                        cityFileName);

                recordSort.Sorting(cityFileName, FieldCompareType.state,
                        stateFileName);

                recordSort.Sorting(stateFileName, FieldCompareType.zipcode,
                        zipFileName);
            }

            // }
        }
    }

    private static void writePerformance(String fileName, LinkedHashMap<Integer, Long> list) throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        long value = 0;

        for (Map.Entry<Integer, Long> entry : list.entrySet()) {
            value += entry.getValue();

            bw.write(String.format("%6s\t%s\n", entry.getKey(), value));
        }
        try {
            bw.close();
        } catch (IOException e) {
            System.out.println("Failed to close buffered writer");
        }
    }
}
