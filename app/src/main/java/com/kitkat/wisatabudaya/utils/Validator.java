package com.kitkat.wisatabudaya.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by irwan on 4/20/2016.
 */
public class Validator {

    public Boolean isEmailValid(String email){
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{1,})$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }
}
