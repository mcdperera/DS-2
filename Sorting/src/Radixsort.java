/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Charmal
 */
public class Radixsort implements Sort<Line> {

    private static int charactersCheck = 100;

    private static int extendASCIIAlphabetSize = 256;

    private long startTime = 0;

    /**
     *
     * @param lines
     * @param comparatorType
     */
    @Override
    public void sortList(long startTime, List<Line> lines, ComparatorType comparatorType) {

        this.startTime = startTime;

        int n = lines.size();

        List<Line> aux = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            aux.add(new Line());
        }

        for (int d = charactersCheck - 1; d >= 0; d--) {
            // sort by key-indexed counting on dth character

            // compute frequency counts
            int[] count = new int[extendASCIIAlphabetSize + 1];

            for (int i = 0; i < n; i++) {

                String s = (CustomComparator.getComparator(comparatorType, lines.get(i)));

                if (s.length() > d) {
                    int k = s.charAt(d);

                    count[k + 1]++;
                } else {
                    count[0]++;
                }
            }

            // compute cumulates
            for (int r = 0; r < extendASCIIAlphabetSize; r++) {
                count[r + 1] += count[r];
            }

            int j = 0;
            // move data
            for (int i = 0; i < n; i++) {

                Line line = lines.get(i);
                String s = (CustomComparator.getComparator(comparatorType, line));

                long totalTime = (System.nanoTime() - this.startTime) / 1000000;

                line.setTime(totalTime);

                if (s.length() > d) {
                    int index = count[s.charAt(d)]++;
                    aux.set(index, line);
                } else {
                    aux.set(j++, line);
                }

            }

            // copy back
            for (int i = 0; i < n; i++) {

                lines.set(i, aux.get(i));
            }
        }

    }

}
