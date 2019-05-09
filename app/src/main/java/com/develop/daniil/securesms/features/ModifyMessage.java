package com.develop.daniil.securesms.features;

import java.util.ArrayList;

public class ModifyMessage {
    /*
      Init symbols to use
     */

    private static ArrayList<Character> Alphabet;
    private static ArrayList<Character> Key_array;

    private static void init(){
        Alphabet = new ArrayList<Character>();
        Key_array = new ArrayList<Character>();
        char[] char_array = new char[]{'.',',',';','-','!','?',' ','\n'}; //Доп символы для сообщений

        for(char i = 'a';i<='z';i++){
            Alphabet.add(i);
        }
        for(char i = 'а';i<='я';i++){
            Alphabet.add(i);
        }
        for(char i = '0';i<='9';i++){
            Alphabet.add(i);
        }
        for (char i: char_array) {
            Alphabet.add(i);
        }
    }

    private static void randomKey(int count){
        int a, b, random_number = 0;
        Key_array.clear();

        for(int i = 0; i < count; i++){
            a = 0; // Начальное значение диапазона - "от"
            b = Alphabet.size() - 1; // Конечное значение диапазона - "до"

            random_number = a + (int) (Math.random() * b);

            Key_array.add(Alphabet.get(random_number));
        }
    }

    public static String secure(String msg){
        String secureMsg = ""; int temp_int;
        int count = msg.length(); // Длина смс + 5 лишних символов
        init();

        randomKey(count); //generate random key
        int i = 0;
        for (char ch: msg.toCharArray()) { //apply to msg
            char tmp = Key_array.get(i);
            temp_int = (short) ch ^ (short) tmp; //XOR
            secureMsg += (char) temp_int;

            i++;
        }

        char[] charAr = secureMsg.toCharArray();
        secureMsg = "";
        for(int j = 0; j < charAr.length; j++){ //Paste Key in msg
            secureMsg = secureMsg + charAr[j] + Key_array.get(j); //char Key char Key ...
        }

        return secureMsg;
    }

    public static String unSecure(String msg){
        String unSecureMsg = "";
        Key_array.clear();

        char[] msgChars = msg.toCharArray();
        for (int i = 0; i < msgChars.length; i++) { //Get Key from Msg
            if(i % 2 != 0){
                Key_array.add(msgChars[i]);
                msg = msg.substring(0, i) + msg.substring(i + 1); //Cut Key[i] from msg
            }
        }

        int temp_int;
        msgChars = msg.toCharArray(); //Msg without key in chars
        for (int i = 0; i < msgChars.length; i++) { // Decryption!!!
            char tmp = Key_array.get(i);
            temp_int = (short) msgChars[i] ^ (short) tmp; //XOR
            unSecureMsg += (char) temp_int;
        }

        return unSecureMsg;
    }
}
