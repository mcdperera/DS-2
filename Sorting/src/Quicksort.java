/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.List;

/**
 *
 * @author Charmal
 */
public class Quicksort implements Sort<Line> {

    private long startTime = 0;

    /**
     *
     * @param records
     * @param comparatorType
     */
    @Override
    public void sortList(long startTime, List<Line> records, ComparatorType comparatorType) {
        this.startTime = startTime;

        sortList(records, comparatorType, 0, records.size() - 1);
    }

    private void sortList(List<Line> records, ComparatorType comparatorType, int left, int right) {
        if (left < right) {
            int pivotIndex = randPartition(records, comparatorType, left, right);
            sortList(records, comparatorType, left, pivotIndex - 1);
            sortList(records, comparatorType, pivotIndex + 1, right);
        }
    }

    private int randPartition(List<Line> records, ComparatorType comparatorType, int left, int right) {
        int randPivot = left + (int) (Math.random() * ((right - left) + 1));
        swap(records, right, randPivot);
        return partition(records, comparatorType, left, right);
    }

    private int partition(List<Line> records, ComparatorType comparatorType, int left, int right) {

        // Select pivot element
        Line pivot = records.get(right);

        int i = left - 1;
        for (int j = left; j < right; ++j) {

            int comparison = CustomComparator.compare(comparatorType, records.get(j), pivot);

            if (comparison < 0) {
                i++;
                swap(records, i, j);
            }
        }

        // Move the pivot element in the middle of the array
        swap(records, i + 1, right);

        // Return the pivot element index
        return i + 1;
    }

    private void swap(List<Line> records, int a, int b) {
        Line lineA = records.get(a);
        long totalTime = (System.nanoTime() - this.startTime) / 1000000;

        lineA.setTime(totalTime);

        Line temp = lineA;

        Line lineB = records.get(b);
        lineB.setTime((System.nanoTime() - this.startTime) / 1000000);
        records.set(a, lineB);

        records.set(b, temp);

    }

}
