
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Avinash G
 */
public class RecordSorter {

    private static int NumOfRecords;

    public LinkedHashMap<Integer, Long> Sorting(
            String inputFileName,
            FieldCompareType comparatorType,
            String outputFileName) throws FileNotFoundException, IOException {

        LinkedHashMap<Integer, Long> performanceData = new LinkedHashMap<Integer, Long>() {
        };

        List<Record> unsortedRecords = readFile(inputFileName);

        List<Record> sortedRecords = null;

        List<Record> unsortedSublist = new ArrayList<>();

        for (int i = 0; i < NumOfRecords; ++i) {

            long startTime = System.nanoTime();

            unsortedSublist.add(unsortedRecords.get(i));

            sort(unsortedSublist, comparatorType);

            long totalTime = (System.nanoTime() - startTime) / 1000;

            performanceData.put((i + 1), totalTime);

            if ((i + 1) == NumOfRecords) {
                sortedRecords = new ArrayList<>(unsortedSublist);
            }
        }

        writeFile(outputFileName, sortedRecords);

        return performanceData;

    }

    public LinkedHashMap<Integer, Long> combinationSorting(
            String inputFileName,
            FieldCompareType comparatorType,
            String outputFileName) throws FileNotFoundException, IOException {

        List<Record> unsortedRecords = readFile(inputFileName);

        List<Record> unsortedSublist = new ArrayList<>();

        int partitionSize = NumOfRecords / 4;

        List<List<Record>> partitions = new LinkedList<>();

        for (int i = 0; i < unsortedRecords.size(); i += partitionSize) {
            partitions.add(unsortedRecords.subList(i,
                    Math.min(i + partitionSize, unsortedRecords.size())));
        }

        List<List<Record>> sortedPartitions = new LinkedList<>();

        List<Record> emptylist = new ArrayList<>();

        for (int i = 0; i < partitionSize; i++) {
            emptylist.add(new Record());
        }

        for (int i = 0; i < 4; i++) {
            sortedPartitions.add(emptylist);
        }

        LinkedHashMap<Integer, Long> timeData = new LinkedHashMap<Integer, Long>() {
        };

        for (int p = 0; p < partitions.size(); p++) {

            for (int i = 0; i < partitionSize; ++i) {

                long startTime = System.nanoTime();

                unsortedSublist.add(partitions.get(p).get(i));

                sort(unsortedSublist, comparatorType);

                long totalTime = (System.nanoTime() - startTime) / 1000;

                int row = (p * partitionSize) + i + 1;

                timeData.put(row, totalTime);

                if ((i + 1) == partitionSize) {

                    sortedPartitions.set(p, unsortedSublist);

                    unsortedSublist = new ArrayList<>();

                }
            }

        }

        List<Record> partSortedList1 = new LinkedList<>();
        List<Record> partSortedList2 = new LinkedList<>();

        merge(partSortedList1, sortedPartitions.get(0),
                sortedPartitions.get(1), comparatorType);

        merge(partSortedList2, sortedPartitions.get(2),
                sortedPartitions.get(3), comparatorType);

        List<Record> finalSortedList = new ArrayList<>();

        merge(finalSortedList, partSortedList1,
                partSortedList2, comparatorType);

        writeFile(outputFileName, finalSortedList);

        return timeData;

    }

    private static List<Record> readFile(String fileName) throws IOException {

        List<Record> unsortedRecords = new ArrayList<>();

        File inFile = new File(fileName);

        BufferedReader br = new BufferedReader(new FileReader(inFile));

        NumOfRecords = Integer.parseInt(br.readLine());

        String inputLine;

        while ((inputLine = br.readLine()) != null) {

            try {
                Record r = new Record(inputLine);
                unsortedRecords.add(r);
            } catch (Exception e) {
                System.out.println(inputLine);
                System.out.println(e.getMessage());
            }
        }

        try {
            br.close();
        } catch (Exception e) {
            System.out.println("Failed to close buffered reader");
        }

        return unsortedRecords;
    }

    private static void writeFile(String fileName, List<Record> list) throws IOException {

        File outFile = new File(fileName);

        if (!outFile.exists()) {
            outFile.createNewFile();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));

        bw.write(String.format("%d%n", NumOfRecords));

        for (Record r : list) {
            bw.write(String.format("%s%n", r.getRecord()));
        }

        try {
            bw.close();
        } catch (IOException e) {
            System.out.println("Failed to close buffered writer");
        }

    }

    public void merge(List<Record> names, List<Record> left, List<Record> right, FieldCompareType comparatorType) {
        int a = 0;
        int b = 0;

        for (int i = 0; i < left.size() + right.size(); i++) {
            names.add(new Record());
        }

        for (int i = 0; i < names.size(); i++) {

            if (b >= right.size() || (a < left.size()
                    && compare(comparatorType, left.get(a), right.get(b)) < 0)) {

                names.set(i, left.get(a));
                a++;
            } else {
                names.set(i, right.get(b));
                b++;
            }
        }
    }

    public static int compare(FieldCompareType comparatorType, Record a, Record b) {

        int recordCompare = 0;

        switch (comparatorType) {
            case lastname:
                recordCompare = a.lastName.compareToIgnoreCase(b.lastName);
                break;
            case firstname:

                String aName = a.lastName + a.firstName;
                String bName = b.lastName + b.firstName;

                recordCompare = aName.compareToIgnoreCase(bName);
                break;
            case address:

                String aAddress = a.lastName + a.firstName + a.address;
                String bAddress = b.lastName + b.firstName + b.address;
                recordCompare = aAddress.compareToIgnoreCase(bAddress);
                break;
            case city:

                String aCity = a.lastName + a.firstName + a.address + a.city;
                String bCity = b.lastName + b.firstName + b.address + b.city;

                recordCompare = aCity.compareToIgnoreCase(bCity);
                break;
            case state:

                String aState = a.lastName + a.firstName + a.address + a.city + a.state;
                String bState = b.lastName + b.firstName + b.address + b.city + b.state;

                recordCompare = aState.compareToIgnoreCase(bState);
                break;
            case zipcode:

                String aZipcode = a.lastName + a.firstName + a.address + a.city + a.state;
                String bZipcode = b.lastName + b.firstName + b.address + b.city + b.state;

                recordCompare = aZipcode.compareToIgnoreCase(bZipcode);

                if (recordCompare == 0 && a.zipcode > b.zipcode) {
                    recordCompare = 1;
                } else if (recordCompare == 0 && a.zipcode < b.zipcode) {
                    recordCompare = -1;
                }

                break;
        }

        return recordCompare;

    }

    public void sort(List<Record> records, FieldCompareType compareType) {
        Sort(records, compareType, 0, records.size() - 1);
    }

    private void Sort(List<Record> records, FieldCompareType compareType, int left, int right) {
        if (left < right) {
            int pivotIndex = getRandomPartition(records, compareType, left, right);
            Sort(records, compareType, left, pivotIndex - 1);
            Sort(records, compareType, pivotIndex + 1, right);
        }
    }

    private int getRandomPartition(List<Record> records, FieldCompareType compareType, int left, int right) {
        int randPivot = left + (int) (Math.random() * ((right - left) + 1));
        swaping(records, right, randPivot);
        return getPartition(records, compareType, left, right);
    }

    private int getPartition(List<Record> records, FieldCompareType compareType, int left, int right) {

        Record pivot = records.get(right);

        int i = left - 1;
        for (int j = left; j < right; ++j) {

            int comparison = compare(compareType, records.get(j), pivot);

            if (comparison < 0) {
                i++;
                swaping(records, i, j);
            }
        }

        swaping(records, i + 1, right);

        return i + 1;
    }

    private void swaping(List<Record> records, int a, int b) {
        Record tmp = records.get(a);
        records.set(a, records.get(b));
        records.set(b, tmp);
    }

}
