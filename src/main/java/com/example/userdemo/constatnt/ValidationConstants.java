package com.example.userdemo.constatnt;

public class ValidationConstants {
    public static final int USER_NAME_MIN_LENGTH = 3;
    public static final int USER_NAME_MAX_LENGTH = 30;

    public static final int EMAIL_MIN_LENGTH = 5;
    public static final int EMAIL_MAX_LENGTH = 50;
    public static final int ADDRESS_MIN_LENGTH = 5;
    public static final int ADDRESS_MAX_LENGTH = 120;
    public static final String PHONE_NUMBER_PATTERN = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";

    public static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$";
}
