/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;

/**
 *
 * @author Charmal
 * @param <T>
 */
public interface Sort<T> {

    /**
     * Sort the given list
     *
     * @param lines1
     * @param comparatorType
     */
    void sortList(List<T> lines1, ComparatorType comparatorType);

}
