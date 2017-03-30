package com.kevadiyakrunalk.rxvalidation.phoneformat;

import android.content.Context;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * The type Phone format.
 */
public class PhoneFormat {
    /**
     * The Data.
     */
    public byte[] data;
    private boolean initialzed = false;
    /**
     * The Buffer.
     */
    public ByteBuffer buffer;
    /**
     * The Default country.
     */
    public String defaultCountry;
    /**
     * The Default calling code.
     */
    public String defaultCallingCode;
    /**
     * The Calling code offsets.
     */
    public HashMap<String, Integer> callingCodeOffsets;
    /**
     * The Calling code countries.
     */
    public HashMap<String, ArrayList<String>> callingCodeCountries;
    /**
     * The Calling code data.
     */
    public HashMap<String, CallingCodeInfo> callingCodeData;
    /**
     * The Country calling code.
     */
    public HashMap<String, String> countryCallingCode;

    private static volatile PhoneFormat Instance = null;

    /**
     * Gets instance.
     *
     * @param context the context
     * @return the instance
     */
    public static PhoneFormat getInstance(Context context) {
        PhoneFormat localInstance = Instance;
        if (localInstance == null) {
            synchronized (PhoneFormat.class) {
                localInstance = Instance;
                if (localInstance == null) {
                    Instance = localInstance = new PhoneFormat(context);
                }
            }
        }
        return localInstance;
    }

    /**
     * Strip string.
     *
     * @param str the str
     * @return the string
     */
    public static String strip(String str) {
        StringBuilder res = new StringBuilder(str);
        String phoneChars = "0123456789+*#";
        for (int i = res.length() - 1; i >= 0; i--) {
            if (!phoneChars.contains(res.substring(i, i + 1))) {
                res.deleteCharAt(i);
            }
        }
        return res.toString();
    }

    /**
     * Strip except numbers string.
     *
     * @param str         the str
     * @param includePlus the include plus
     * @return the string
     */
    public static String stripExceptNumbers(String str, boolean includePlus) {
        StringBuilder res = new StringBuilder(str);
        String phoneChars = "0123456789";
        if (includePlus) {
            phoneChars += "+";
        }
        for (int i = res.length() - 1; i >= 0; i--) {
            if (!phoneChars.contains(res.substring(i, i + 1))) {
                res.deleteCharAt(i);
            }
        }
        return res.toString();
    }

    /**
     * Strip except numbers string.
     *
     * @param str the str
     * @return the string
     */
    public static String stripExceptNumbers(String str) {
        return stripExceptNumbers(str, false);
    }

    /**
     * Instantiates a new Phone format.
     *
     * @param context the context
     */
    public PhoneFormat(Context context) {
        init(null, context);
    }

    /**
     * Instantiates a new Phone format.
     *
     * @param countryCode the country code
     * @param context     the context
     */
    public PhoneFormat(String countryCode, Context context) {
        init(countryCode, context);
    }

    /**
     * Init.
     *
     * @param countryCode the country code
     * @param context     the context
     */
    public void init(String countryCode, Context context) {
        InputStream stream = null;
        ByteArrayOutputStream bos = null;
        try {
            stream = context.getApplicationContext().getAssets().open("PhoneFormats.dat");
            bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int len;
            while ((len = stream.read(buf, 0, 1024)) != -1) {
                bos.write(buf, 0, len);
            }
            data = bos.toByteArray();
            buffer = ByteBuffer.wrap(data);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (Exception e) {
                Log.e("PhoneFormatt", "Exception", e);
            }
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception e) {
                Log.e("PhoneFormatt", "Exception", e);
            }
        }

        if (countryCode != null && countryCode.length() != 0) {
            defaultCountry = countryCode;
        } else {
            Locale loc = Locale.getDefault();
            defaultCountry = loc.getCountry().toLowerCase();
        }
        callingCodeOffsets = new HashMap<>(255);
        callingCodeCountries = new HashMap<>(255);
        callingCodeData = new HashMap<>(10);
        countryCallingCode = new HashMap<>(255);

        parseDataHeader();
        initialzed = true;
    }

    /**
     * Default calling code string.
     *
     * @return the string
     */
    public String defaultCallingCode() {
        return callingCodeForCountryCode(defaultCountry);
    }

    /**
     * Calling code for country code string.
     *
     * @param countryCode the country code
     * @return the string
     */
    public String callingCodeForCountryCode(String countryCode) {
        return countryCallingCode.get(countryCode.toLowerCase());
    }

    /**
     * Countries for calling code array list.
     *
     * @param callingCode the calling code
     * @return the array list
     */
    public ArrayList countriesForCallingCode(String callingCode) {
        if (callingCode.startsWith("+")) {
            callingCode = callingCode.substring(1);
        }

        return callingCodeCountries.get(callingCode);
    }

    /**
     * Find calling code info calling code info.
     *
     * @param str the str
     * @return the calling code info
     */
    public CallingCodeInfo findCallingCodeInfo(String str) {
        CallingCodeInfo res = null;
        for (int i = 0; i < 3; i++) {
            if (i < str.length()) {
                res = callingCodeInfo(str.substring(0, i + 1));
                if (res != null) {
                    break;
                }
            } else {
                break;
            }
        }

        return res;
    }

    /**
     * Format string.
     *
     * @param orig the orig
     * @return the string
     */
    public String format(String orig) {
        if (!initialzed) {
            return orig;
        }
        String str = strip(orig);

        if (str.startsWith("+")) {
            String rest = str.substring(1);
            CallingCodeInfo info = findCallingCodeInfo(rest);
            if (info != null) {
                String phone = info.format(rest);
                return "+" + phone;
            } else {
                return orig;
            }
        } else {
            CallingCodeInfo info = callingCodeInfo(defaultCallingCode);
            if (info == null) {
                return orig;
            }

            String accessCode = info.matchingAccessCode(str);
            if (accessCode != null) {
                String rest = str.substring(accessCode.length());
                String phone = rest;
                CallingCodeInfo info2 = findCallingCodeInfo(rest);
                if (info2 != null) {
                    phone = info2.format(rest);
                }

                if (phone.length() == 0) {
                    return accessCode;
                } else {
                    return String.format("%s %s", accessCode, phone);
                }
            } else {
                return info.format(str);
            }
        }
    }

    /**
     * Is phone number valid boolean.
     *
     * @param phoneNumber the phone number
     * @return the boolean
     */
    public boolean isPhoneNumberValid(String phoneNumber) {
        if (!initialzed) {
            return true;
        }
        String str = strip(phoneNumber);

        if (str.startsWith("+")) {
            String rest = str.substring(1);
            CallingCodeInfo info = findCallingCodeInfo(rest);
            return info != null && info.isValidPhoneNumber(rest);
        } else {
            CallingCodeInfo info = callingCodeInfo(defaultCallingCode);
            if (info == null) {
                return false;
            }

            String accessCode = info.matchingAccessCode(str);
            if (accessCode != null) {
                String rest = str.substring(accessCode.length());
                if (rest.length() != 0) {
                    CallingCodeInfo info2 = findCallingCodeInfo(rest);
                    return info2 != null && info2.isValidPhoneNumber(rest);
                } else {
                    return false;
                }
            } else {
                return info.isValidPhoneNumber(str);
            }
        }
    }

    /**
     * Value 32 int.
     *
     * @param offset the offset
     * @return the int
     */
    int value32(int offset) {
        if (offset + 4 <= data.length) {
            buffer.position(offset);
            return buffer.getInt();
        } else {
            return 0;
        }
    }

    /**
     * Value 16 short.
     *
     * @param offset the offset
     * @return the short
     */
    short value16(int offset) {
        if (offset + 2 <= data.length) {
            buffer.position(offset);
            return buffer.getShort();
        } else {
            return 0;
        }
    }

    /**
     * Value string string.
     *
     * @param offset the offset
     * @return the string
     */
    public String valueString(int offset) {
        try {
            for (int a = offset; a < data.length; a++) {
                if (data[a] == '\0') {
                    if (offset == a - offset) {
                        return "";
                    }
                    return new String(data, offset, a - offset);
                }
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Calling code info calling code info.
     *
     * @param callingCode the calling code
     * @return the calling code info
     */
    public CallingCodeInfo callingCodeInfo(String callingCode) {
        CallingCodeInfo res = callingCodeData.get(callingCode);
        if (res == null) {
            Integer num = callingCodeOffsets.get(callingCode);
            if (num != null) {
                final byte[] bytes = data;
                int start = num;
                int offset = start;
                res = new CallingCodeInfo();
                res.callingCode = callingCode;
                res.countries = callingCodeCountries.get(callingCode);
                callingCodeData.put(callingCode, res);

                int block1Len = value16(offset);
                offset += 2;

                offset += 2;
                int block2Len = value16(offset);
                offset += 2;

                offset += 2;
                int setCnt = value16(offset);
                offset += 2;

                offset += 2;

                ArrayList<String> strs = new ArrayList<>(5);
                String str;
                while ((str = valueString(offset)).length() != 0) {
                    strs.add(str);
                    offset += str.length() + 1;
                }
                res.trunkPrefixes = strs;
                offset++;

                strs = new ArrayList<>(5);
                while ((str = valueString(offset)).length() != 0) {
                    strs.add(str);
                    offset += str.length() + 1;
                }
                res.intlPrefixes = strs;

                ArrayList<RuleSet> ruleSets = new ArrayList<>(setCnt);
                offset = start + block1Len;
                for (int s = 0; s < setCnt; s++) {
                    RuleSet ruleSet = new RuleSet();
                    ruleSet.matchLen = value16(offset);
                    offset += 2;
                    int ruleCnt = value16(offset);
                    offset += 2;
                    ArrayList<PhoneRule> rules = new ArrayList<>(ruleCnt);
                    for (int r = 0; r < ruleCnt; r++) {
                        PhoneRule rule = new PhoneRule();
                        rule.minVal = value32(offset);
                        offset += 4;
                        rule.maxVal = value32(offset);
                        offset += 4;
                        rule.byte8 = (int) bytes[offset++];
                        rule.maxLen = (int) bytes[offset++];
                        rule.otherFlag = (int) bytes[offset++];
                        rule.prefixLen = (int) bytes[offset++];
                        rule.flag12 = (int) bytes[offset++];
                        rule.flag13 = (int) bytes[offset++];
                        int strOffset = value16(offset);
                        offset += 2;
                        rule.format = valueString(start + block1Len + block2Len + strOffset);

                        int openPos = rule.format.indexOf("[[");
                        if (openPos != -1) {
                            int closePos = rule.format.indexOf("]]");
                            rule.format = String.format("%s%s", rule.format.substring(0, openPos), rule.format.substring(closePos + 2));
                        }

                        rules.add(rule);

                        if (rule.hasIntlPrefix) {
                            ruleSet.hasRuleWithIntlPrefix = true;
                        }
                        if (rule.hasTrunkPrefix) {
                            ruleSet.hasRuleWithTrunkPrefix = true;
                        }
                    }
                    ruleSet.rules = rules;
                    ruleSets.add(ruleSet);
                }
                res.ruleSets = ruleSets;
            }
        }

        return res;
    }

    /**
     * Parse data header.
     */
    public void parseDataHeader() {
        int count = value32(0);
        int base = count * 12 + 4;
        int spot = 4;
        for (int i = 0; i < count; i++) {
            String callingCode = valueString(spot);
            spot += 4;
            String country = valueString(spot);
            spot += 4;
            int offset = value32(spot) + base;
            spot += 4;

            if (country.equals(defaultCountry)) {
                defaultCallingCode = callingCode;
            }

            countryCallingCode.put(country, callingCode);

            callingCodeOffsets.put(callingCode, offset);
            ArrayList<String> countries = callingCodeCountries.get(callingCode);
            if (countries == null) {
                countries = new ArrayList<>();
                callingCodeCountries.put(callingCode, countries);
            }
            countries.add(country);
        }

        if (defaultCallingCode != null) {
            callingCodeInfo(defaultCallingCode);
        }
    }
}
