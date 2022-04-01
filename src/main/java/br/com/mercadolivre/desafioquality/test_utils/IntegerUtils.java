package br.com.mercadolivre.desafioquality.test_utils;

public class IntegerUtils {


    public static Integer parseToInt(String strNum, Integer defaultValue, boolean allowNegatives) {
        if (strNum == null) {
            return defaultValue;
        }
        try {
            int value = Integer.parseInt(strNum);
            if(!allowNegatives && value < 0){
                return defaultValue;
            }

            return value;
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }
}
