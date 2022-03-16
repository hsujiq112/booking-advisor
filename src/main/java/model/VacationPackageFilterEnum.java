package model;

import java.util.ArrayList;

public class VacationPackageFilterEnum {

    public static final String PRICE = "Price";
    public static final String DESTINATION = "Destination";
    public static final String START_DATE = "Start Date";
    public static final String END_DATE = "End Date";

    public static ArrayList<String> getEnumsDisplayNames() {
        var enums = new ArrayList<String>();
        enums.add("-");
        enums.add(PRICE);
        enums.add(DESTINATION);
        enums.add(START_DATE);
        enums.add(END_DATE);
        return enums;
    }
}
