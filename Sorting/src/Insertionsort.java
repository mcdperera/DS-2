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
    public void sortList(List<Line> records, ComparatorType comparatorType) {
        int j;
        Line key;
        int i;

       for (j = 1; j < records.size(); j++) {

            key = records.get(j);

            for (i = j - 1; (i >= 0) && CustomComparator.compare(comparatorType, key, records.get(i)) < 0; i--) {

                  records.set(i + 1,  records.get(i));
            }
            
            records.set(i + 1, key);
        }
    }

}
