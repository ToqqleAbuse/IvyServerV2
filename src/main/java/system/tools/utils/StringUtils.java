package system.tools.utils;

import java.util.Random;

public class StringUtils{

    public static final String SPACE = " ";

    public static final String EMPTY = "";

    public static final String ALPHABET_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String ALPHABET_LOWERCASE = ALPHABET_UPPERCASE.toLowerCase();

    public static final String NUMBERS = "1234567890";

    public static final String LF = "\n";

    public static String cutOff(String input, final int length){
        if(input.length() < length){
            return null;
        }

        if(null != input && !input.isEmpty()){
            input = input.substring(0, input.length() - length);
        }

        return input;
    }

    public static String randomString(final int length){

        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < length; i++){
            String chars = ALPHABET_LOWERCASE + ALPHABET_UPPERCASE + NUMBERS;
            builder.append(chars.charAt(random.nextInt(chars.length())));
        }

        return (builder.toString());
    }

    public static String removeChar(final String input, char character){
        String forReturn = EMPTY;
        for(int i = 0; i < input.length(); i++){
            forReturn += (input.charAt(i) == character ? "" : input.charAt(i));
        }
        return forReturn;
    }

}
