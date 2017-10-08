/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Charmal
 */
public enum ComparatorType {

    /**
     *
     */
    NONE(0),
    /**
     *
     */
    LastName(1),
    /**
     *
     */
    FirstName(2),
    /**
     *
     */
    Address(3),
    /**
     *
     */
    City(4),
    /**
     *
     */
    State(5),
    /**
     *
     */
    ZipCode(6);

    private final int value;

    private ComparatorType(int value) {
        this.value = value;
    }

    /**
     *
     * @return
     */
    public int getValue() {
        return value;
    }

    /**
     *
     * @param value
     * @return
     */
    public static ComparatorType getEnum(int value) {
        ComparatorType messageType = ComparatorType.NONE;
        switch (value) {
            case 1:
                messageType = ComparatorType.LastName;
                break;
            case 2:
                messageType = ComparatorType.FirstName;
                break;
            case 3:
                messageType = ComparatorType.Address;
                break;
            case 4:
                messageType = ComparatorType.City;
                break;
            case 5:
                messageType = ComparatorType.State;
                break;
            case 6:
                messageType = ComparatorType.ZipCode;
                break;
            default:
                break;
        }

        return messageType;
    }
}
