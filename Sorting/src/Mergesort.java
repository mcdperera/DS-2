
import java.util.ArrayList;
import java.util.List;

/**
 * The merge sort algorithm
 *
 * @author Charmal
 */
public class Mergesort implements Sort<Line> {


    @Override
    public void sortList(long startTime, List<Line> names, ComparatorType comparatorType) {

        if (names.size() >= 2) {
            List<Line> left = new ArrayList<>(names.size() / 2);
            List<Line> right = new ArrayList<>(names.size() - names.size() / 2);

            for (int i = 0; i < left.size(); i++) {
                left.set(i, names.get(i));
            }

            for (int i = 0; i < right.size(); i++) {
                right.set(i, names.get(i + names.size() / 2));
            }

            sortList(startTime, left, comparatorType);
            sortList(startTime, right, comparatorType);

            merge(startTime, names, left, right, comparatorType);
        }
    }

    public void merge(long startTime, List<Line> names, List<Line> left, List<Line> right, ComparatorType comparatorType) {
        int a = 0;
        int b = 0;

        for (int i = 0; i < left.size() + right.size(); i++) {
            names.add(new Line());
        }

        for (int i = 0; i < names.size(); i++) {

            if (b >= right.size() || (a < left.size() && CustomComparator.compare(comparatorType, left.get(a), right.get(b)) < 0)) {

                Line lineA = left.get(a);
                lineA.setTime((System.nanoTime() - startTime) / 1000000);
                names.set(i, lineA);
                a++;
            } else {
                Line lineB = right.get(b);
                lineB.setTime((System.nanoTime() - startTime) / 1000000);
                names.set(i, lineB);
                b++;
            }
        }
    }

}
