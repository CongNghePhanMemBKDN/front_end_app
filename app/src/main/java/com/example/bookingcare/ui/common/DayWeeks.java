package com.example.bookingcare.ui.common;

public enum DayWeeks {
    SUNDAY(0),
    MONDAY (1),
    TUESDAY (2),
    WEDNESDAY (3),
    THURSDAY (4),
    FRIDAY (5),
    SATURDAY (6);

    byte id;

    DayWeeks(int id) {
        this.id = (byte) id;
    }

    public static DayWeeks from(int id){
        return values()[id];
    }

    public int getId(){
        return this.id;
    }

    public String getShortString(){
        return this.toString().substring(0, 3);
    }

    public static int size(){
        return values().length;
    }
}
