/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Charmal
 */
public class CustomComparator {

    /**
     * compare two record depending on the comparator type.
     *
     * @param comparatorType
     * @param a
     * @param b
     * @return
     */
    public static int compare(ComparatorType comparatorType, Line a, Line b) {
        if (a == null && b == null) {
            return 0;
        } else if ((a == null && b != null)
                || (a != null && b == null)) {
            return a == null ? 1 : -1;
        } else {
            int recordCompare = 0;

            switch (comparatorType) {
                case NONE:
                    break;
                case LastName:
                    recordCompare = a.lastName.compareToIgnoreCase(b.lastName);
                    break;
                case FirstName:

                    String aName = a.lastName + a.firstName;
                    String bName = b.lastName + b.firstName;

                    recordCompare = aName.compareToIgnoreCase(bName);
                    break;
                case Address:

                    String aAddress = a.lastName + a.firstName + a.address;
                    String bAddress = b.lastName + b.firstName + b.address;
                    recordCompare = aAddress.compareToIgnoreCase(bAddress);
                    break;
                case City:

                    String aCity = a.lastName + a.firstName + a.address + a.city;
                    String bCity = b.lastName + b.firstName + b.address + b.city;

                    recordCompare = aCity.compareToIgnoreCase(bCity);
                    break;
                case State:

                    String aState = a.lastName + a.firstName + a.address + a.city + a.state;
                    String bState = b.lastName + b.firstName + b.address + b.city + b.state;

                    recordCompare = aState.compareToIgnoreCase(bState);
                    break;
                case ZipCode:

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
    }

    /**
     *
     * @param comparatorType
     * @param a
     * @return
     */
    public static String getComparator(ComparatorType comparatorType, Line a) {

        String comparator = "";

        switch (comparatorType) {
            case NONE:
                break;
            case LastName:
                comparator = a.lastName;
                break;
            case FirstName:
                comparator = a.lastName + a.firstName;
                break;
            case Address:
                comparator = a.lastName + a.firstName + a.address;
                break;
            case City:
                comparator = a.lastName + a.firstName + a.address + a.city;
                break;
            case State:
                comparator = a.lastName + a.firstName + a.address + a.city + a.state;
                break;
            case ZipCode:
                comparator = a.lastName + a.firstName + a.address + a.city + a.state + Integer.toString(a.zipcode);
                break;

        }

        return comparator;
    }
}
