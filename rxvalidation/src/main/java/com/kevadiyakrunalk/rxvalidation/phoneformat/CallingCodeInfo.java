package com.kevadiyakrunalk.rxvalidation.phoneformat;

import java.util.ArrayList;

/**
 * The type Calling code info.
 */
public class CallingCodeInfo {
    /**
     * The Countries.
     */
    public ArrayList<String> countries = new ArrayList<String>();
    /**
     * The Calling code.
     */
    public String callingCode = "";
    /**
     * The Trunk prefixes.
     */
    public ArrayList<String> trunkPrefixes = new ArrayList<String>();
    /**
     * The Intl prefixes.
     */
    public ArrayList<String> intlPrefixes = new ArrayList<String>();
    /**
     * The Rule sets.
     */
    public ArrayList<RuleSet> ruleSets = new ArrayList<RuleSet>();
    //public ArrayList formatStrings;

    /**
     * Matching access code string.
     *
     * @param str the str
     * @return the string
     */
    String matchingAccessCode(String str) {
        for (String code : intlPrefixes) {
            if (str.startsWith(code)) {
                return code;
            }
        }
        return null;
    }

    /**
     * Matching trunk code string.
     *
     * @param str the str
     * @return the string
     */
    String matchingTrunkCode(String str) {
        for (String code : trunkPrefixes) {
            if (str.startsWith(code)) {
                return code;
            }
        }

        return null;
    }

    /**
     * Format string.
     *
     * @param orig the orig
     * @return the string
     */
    String format(String orig) {
        String str = orig;
        String trunkPrefix = null;
        String intlPrefix = null;
        if (str.startsWith(callingCode)) {
            intlPrefix = callingCode;
            str = str.substring(intlPrefix.length());
        } else {
            String trunk = matchingTrunkCode(str);
            if (trunk != null) {
                trunkPrefix = trunk;
                str = str.substring(trunkPrefix.length());
            }
        }

        for (RuleSet set : ruleSets) {
            String phone = set.format(str, intlPrefix, trunkPrefix, true);
            if (phone != null) {
                return phone;
            }
        }

        for (RuleSet set : ruleSets) {
            String phone = set.format(str, intlPrefix, trunkPrefix, false);
            if (phone != null) {
                return phone;
            }
        }

        if (intlPrefix != null && str.length() != 0) {
            return String.format("%s %s", intlPrefix, str);
        }

        return orig;
    }

    /**
     * Is valid phone number boolean.
     *
     * @param orig the orig
     * @return the boolean
     */
    boolean isValidPhoneNumber(String orig) {
        String str = orig;
        String trunkPrefix = null;
        String intlPrefix = null;
        if (str.startsWith(callingCode)) {
            intlPrefix = callingCode;
            str = str.substring(intlPrefix.length());
        } else {
            String trunk = matchingTrunkCode(str);
            if (trunk != null) {
                trunkPrefix = trunk;
                str = str.substring(trunkPrefix.length());
            }
        }

        for (RuleSet set : ruleSets) {
            boolean valid = set.isValid(str, intlPrefix, trunkPrefix, true);
            if (valid) {
                return valid;
            }
        }

        for (RuleSet set : ruleSets) {
            boolean valid = set.isValid(str, intlPrefix, trunkPrefix, false);
            if (valid) {
                return valid;
            }
        }

        return false;
    }
}