
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Charmal
 */
public class TextLineSorter {

    /**
     * Number of records property.
     */
    private static int NumberOfRecords;

    private Mergesort Mergesort;

    /**
     *
     * @param inputFileName
     * @param comparatorType
     * @param sorter
     * @param outputFileName
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void primarySorting(
            String inputFileName,
            ComparatorType comparatorType,
            Sort sorter,
            String outputFileName,
            boolean isNeedPerformanceData,
            String performanceOutputFile) throws FileNotFoundException, IOException {

        LinkedHashMap<Integer, Long> timeData = new LinkedHashMap<Integer, Long>() {
        };

        List<Line> unsortedRecords = readUnSortedFile(inputFileName);

        List<Line> sortedRecords = null;

        List<Line> unsortedSublist = new ArrayList<>();

        for (int i = 0; i < NumberOfRecords; ++i) {

            long startTime = System.nanoTime();

            unsortedSublist.add(unsortedRecords.get(i));

            sorter.sortList(unsortedSublist, comparatorType);

            long endTime = System.nanoTime();
            long totalTime = (endTime - startTime) / 1000;

            timeData.put((i + 1), totalTime);

            if ((i + 1) == NumberOfRecords) {
                sortedRecords = new ArrayList<>(unsortedSublist);
            }
        }

        writeSortedFile(outputFileName, sortedRecords);

        if (isNeedPerformanceData) {
            writePerformanceData(performanceOutputFile, timeData);
        }

    }

    public void secondarySorting(
            String inputFileName,
            ComparatorType comparatorType,
            Sort sorter,
            String outputFileName,
            boolean isNeedPerformanceData,
            String performanceOutputFile) throws FileNotFoundException, IOException {

        List<Line> unsortedRecords = readUnSortedFile(inputFileName);

        List<Line> unsortedSublist = new ArrayList<>();

        int partitionSize = NumberOfRecords / 4;

        List<List<Line>> partitions = new LinkedList<>();

        for (int i = 0; i < unsortedRecords.size(); i += partitionSize) {
            partitions.add(unsortedRecords.subList(i,
                    Math.min(i + partitionSize, unsortedRecords.size())));
        }

        List<List<Line>> sortedPartitions = new LinkedList<>();

        List<Line> emptylist = new ArrayList<>();

        for (int i = 0; i < partitionSize; i++) {
            emptylist.add(new Line());
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

                sorter.sortList(unsortedSublist, comparatorType);

                long endTime = System.nanoTime();
                long totalTime = (endTime - startTime) / 1000;
                
                int row = (p * partitionSize) + i + 1;
                
                timeData.put(row, totalTime);

                if ((i + 1) == partitionSize) {

                    sortedPartitions.set(p, unsortedSublist);

                    unsortedSublist = new ArrayList<>();

                }
            }

        }

        List<Line> partallySortedPartitions1 = new LinkedList<>();
        List<Line> partallySortedPartitions2 = new LinkedList<>();

        Mergesort = new Mergesort();

        Mergesort.merge(partallySortedPartitions1, sortedPartitions.get(0),
                sortedPartitions.get(1), comparatorType);

        Mergesort.merge(partallySortedPartitions2, sortedPartitions.get(2),
                sortedPartitions.get(3), comparatorType);

        List<Line> fullySortedPartitions = new ArrayList<>();

        Mergesort.merge(fullySortedPartitions, partallySortedPartitions1,
                partallySortedPartitions2, comparatorType);

        writeSortedFile(outputFileName, fullySortedPartitions);

        if (isNeedPerformanceData) {
            writePerformanceData(performanceOutputFile, timeData);
        }

    }

    private static List<Line> readUnSortedFile(String fileName) throws IOException {

        List<Line> unsortedRecords = new ArrayList<>();

        File inFile = new File(fileName);

        BufferedReader br = new BufferedReader(new FileReader(inFile));

        NumberOfRecords = Integer.parseInt(br.readLine());

        String line;

        int id = 1;

        while ((line = br.readLine()) != null) {

            try {
                Line r = new Line(id, line);
                unsortedRecords.add(r);
                id++;
            } catch (Exception e) {
                System.out.println(line);
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

    private static void writeSortedFile(String fileName, List<Line> list) throws IOException {

        // create the file.
        File outFile = new File(fileName);

        // check the existence of the file.
        if (!outFile.exists()) {
            outFile.createNewFile();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));

        bw.write(String.format("%d%n", NumberOfRecords));

        for (Line r : list) {
            bw.write(String.format("%s%n", r.getLine()));
        }

        try {
            bw.close();
        } catch (IOException e) {
            System.out.println("Failed to close buffered writer");
        }

        System.out.println(fileName + " created");

    }

    private static void writePerformanceData(String fileName, LinkedHashMap<Integer, Long> list) throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        long value = 0;
        for (Map.Entry<Integer, Long> entry : list.entrySet()) {
            value += entry.getValue();

            bw.write(String.format("%6s\t%s\n", entry.getKey(), value));
        }
        try {
            bw.close();
        } catch (Exception e) {
            System.out.println("Failed to close buffered writer");
        }

        System.out.println(fileName + " created");

    }
}
