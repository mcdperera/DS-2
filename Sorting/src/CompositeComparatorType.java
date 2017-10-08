/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Charmal
 */
public enum CompositeComparatorType {

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
    Name(2),
    /**
     *
     */
    Address(23),
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

    private CompositeComparatorType(int value) {
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
    public static CompositeComparatorType getEnum(int value) {
        CompositeComparatorType messageType = CompositeComparatorType.NONE;
        switch (value) {

            case 1:
                messageType = CompositeComparatorType.LastName;
                break;
            case 2:
                messageType = CompositeComparatorType.Name;
                break;
            case 3:
                messageType = CompositeComparatorType.Address;
                break;
            case 4:
                messageType = CompositeComparatorType.City;
                break;
            case 5:
                messageType = CompositeComparatorType.State;
                break;
            case 6:
                messageType = CompositeComparatorType.ZipCode;
                break;

            default:
                break;
        }

        return messageType;
    }
}
