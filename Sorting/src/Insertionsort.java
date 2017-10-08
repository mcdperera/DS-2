/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.List;

/**
 *
 * @author Charmal
 */
public class Insertionsort implements Sort<Line> {

    /**
     *
     * @param records
     * @param comparatorType
     */
    @Override
    public void sortList(long startTime, List<Line> records, ComparatorType comparatorType) {
        int j;
        Line key;
        int i;

        for (j = 1; j < records.size(); j++) {

            key = records.get(j);

            for (i = j - 1; (i >= 0) && CustomComparator.compare(comparatorType, key, records.get(i)) < 0; i--) {

                Line line = records.get(i);
                long totalTime = (System.nanoTime() - startTime) ;
                line.setTime(totalTime);

                records.set(i + 1, line);
            }

            long totalTime = (System.nanoTime() - startTime);
            key.setTime(totalTime);
            
            records.set(i + 1, key);
        }
    }

}
