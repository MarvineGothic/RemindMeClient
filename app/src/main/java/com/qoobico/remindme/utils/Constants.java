package com.qoobico.remindme.utils;

import com.qoobico.remindme.R;

public class Constants {

    public static final boolean MOCK = true;
    public static final boolean DEBUG = true;
    public static final int MAIN_ACTIVITY_CODE = 1000;
    public static final int ADD_REMINDER_ACTIVITY_CODE = 1001;

    public static final int ACTIVITY_MAIN_LAYOUT = R.layout.activity_main;
    public static final int ACTIVITY_ADD_REMINDER_LAYOUT = R.layout.activity_add_reminder;
    public static final int REMINDER_TYPE_ITEM_LAYOUT = R.layout.reminder_type_item_layout;
    public static final int REMIND_ITEM_LAYOUT = R.layout.remind_item;

    public static final int DRAWER_LAYOUT = R.id.drawer_layout;
    public static final int DATE_INPUT = R.id.date_picker_actions;
    public static final int TITLE_INPUT  = R.id.title_input_edit_text;
    public static final int REMINDER_TYPE_INPUT = R.id.reminder_type_input;

    public static final int TOOL_BAR_ID = R.id.toolbar;
    public static final int NAVIGATION_ID = R.id.navigation;
    public static final int FAB_ID = R.id.fab;
    public static final int CLOSE_ID = R.id.close;
    public static final int CHECK_ID = R.id.check;
    public static final int VIEW_PAGER_ID = R.id.view_pager;
    public static final int TAB_LAYOUT_ID = R.id.tab_layout;

    public static final int RECYCLER_VIEW_ID = R.id.recycleView;

    public static final int CARD_VIEW_ID = R.id.card_view;
    public static final int REMINDER_ID = R.id.reminder_id;
    public static final int REMINDER_TITLE = R.id.reminder_title;
    public static final int REMINDER_TYPE = R.id.reminder_type;
    public static final int REMINDER_DATE = R.id.reminder_date;

    public static final int MENU = R.menu.menu;
    public static final int MENU_ADD_REMINDER = R.menu.menu_add_reminder;
    public static final int DEFAULT_THEME = R.style.AppDefault;
    public static final int REMINDER_FORM_THEME = R.style.ReminderFormTheme;

    public static final int APP_NAME = R.string.app_name;
    public static final int ADD_REMINDER_NAME = R.string.add_reminder_name;
    public static final int NAV_OPEN = R.string.view_navigation_open;
    public static final int NAV_CLOSE = R.string.view_navigation_close;

    public static final int DIALOG_CANCEL = R.string.dialog_neutral;
    public static final int DIALOG_DELETE = R.string.dialog_negative;
    public static final int DIALOG_EDIT = R.string.dialog_positive;

    public static final int[] ALL_REMINDERS = {0, R.layout.fragment_all, R.string.menu_item_all};
    public static final int[] IDEAS = {1, R.layout.fragment_ideas, R.string.menu_item_ideas};
    public static final int[] TODO = {2, R.layout.fragment_todo, R.string.menu_item_todo};
    public static final int[] BIRTHDAYS = {3, R.layout.fragment_birthdays, R.string.menu_item_birthdays};
    public static final int[] HOLIDAYS = {4, R.layout.fragment_birthdays, R.string.menu_item_holidays};
    public static final int[][] TABS = {ALL_REMINDERS, IDEAS, TODO, BIRTHDAYS, HOLIDAYS};

    public static final String REMINDERS_DIR = "reminders";

    public static class URL {
        public static final String HOST = "http://192.168.0.11:8080/";
        public static final String GET_REMIND_ITEM = HOST + "reminders";
        public static final String POST_REMIND_ITEM = HOST + "reminders";
        public static final String DELETE_REMIND_ITEM = HOST + "reminders/";
        public static final String UPDATE_REMIND_ITEM = HOST + "reminders/";

    }
}

// 169.254.67.226
// 192.168.1.178
// 192.168.56.1
// 169.254.120.171