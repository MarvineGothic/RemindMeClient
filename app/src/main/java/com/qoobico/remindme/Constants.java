package com.qoobico.remindme;

public class Constants {

    public static final int[] HISTORY = {0, R.layout.fragment_history, R.string.menu_item_history};
    public static final int[] IDEAS = {1, R.layout.fragment_ideas, R.string.menu_item_ideas};
    public static final int[] TODO = {2, R.layout.fragment_todo, R.string.menu_item_todo};
    public static final int[] BIRTHDAYS = {3, R.layout.fragment_birthdays, R.string.menu_item_birthdays};

    public static class URL {
        public static final String HOST = "http://192.168.1.178:8080/";
        public static final String GET_REMIND_ITEM = HOST + "reminders";
        public static final String POST_REMIND_ITEM = HOST + "reminders";
        public static final String DELETE_REMIND_ITEM = HOST + "reminders/";

    }
}

// 169.254.67.226
// 192.168.1.178
// 192.168.56.1
// 169.254.120.171