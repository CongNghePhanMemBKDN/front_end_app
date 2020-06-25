package com.example.bookingcare.ui.common;

public enum DayWeeks {
    SUNDAY(0),
    MONDAY (1),
    TUESDAY (1),
    WEDNESDAY (1),
    THURSDAY (1),
    FRIDAY (1),
    SATURDAY (1);

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
