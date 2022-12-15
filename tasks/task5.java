package tasks;

import java.lang.Math;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class task5 {
    public static void main(String[] args) throws NoSuchAlgorithmException{
        System.out.println("-----------№1-----------");
        System.out.println(encrypt("Hello"));
        System.out.println(decrypt(new int [] {72, 33, -73, 84, -12, -3, 13, -13, -68 }));
        System.out.println(encrypt("Sunshine"));
        System.out.println("-----------№2-----------");
        System.out.println(canMove("Rook", "A8", "H8"));
        System.out.println(canMove("Bishop", "A7", "G1"));
        System.out.println(canMove("Queen", "C4", "D6"));
        System.out.println("-----------№3-----------");
        System.out.println(canComplete("butl", "beautiful"));
        System.out.println(canComplete("butlz", "beautiful")); 
        System.out.println(canComplete("tulb", "beautiful"));
        System.out.println(canComplete("bbutl", "beautiful"));
        System.out.println("-----------№4-----------");
        System.out.println(sumDigProd(16, 28));
        System.out.println(sumDigProd(0));
        System.out.println(sumDigProd(1, 2, 3, 4, 5, 6));
        System.out.println("-----------№5-----------");
        System.out.println(sameVowelGroup(new String [] {"toe", "ocelot", "maniac"}));
        System.out.println(sameVowelGroup(new String [] {"many", "carriage", "emit", "apricot", "animal"}));
        System.out.println(sameVowelGroup(new String [] {"hoops", "chuff", "bot", "bottom"}));
        System.out.println("-----------№6-----------");
        System.out.println(validateCard(1234567890123456L));
        System.out.println(validateCard(1234567890123452L));  
        System.out.println("-----------№7-----------");
        System.out.println(numToEng(0));
        System.out.println(numToEng(18)); 
        System.out.println(numToEng(126)); 
        System.out.println(numToEng(909)); 
        System.out.println(numToRus(0));
        System.out.println(numToRus(18)); 
        System.out.println(numToRus(126)); 
        System.out.println(numToRus(909)); 
        System.out.println("-----------№8-----------");
        System.out.println(getSha256Hash("password123"));
        System.out.println(getSha256Hash("Fluffy@home"));
        System.out.println(getSha256Hash("Hey dude!"));
        System.out.println("-----------№9-----------");
        System.out.println(correctTitle("jOn SnoW, kINg IN thE noRth."));
        System.out.println(correctTitle("sansa stark, lady of winterfell.")); 
        System.out.println(correctTitle("TYRION LANNISTER, HAND OF THE QUEEN."));
        System.out.println("-----------№10-----------");
        System.out.println(hexLattice(1));
        System.out.println(hexLattice(7));
        System.out.println(hexLattice(19));
        System.out.println(hexLattice(21));
    }

    public static String encrypt(String s) {
        int[] arr1 = new int[s.length()];
        int[] arr2 = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            arr1[i] = Integer.valueOf(s.charAt(i));
        }
        arr2[0] = arr1[0];
        for (int i = 1; i < arr1.length; i++) {
            arr2[i] = arr1[i] - arr1[i - 1];
        }
        return Arrays.toString(arr2);
    }

    public static String decrypt(int [] arr) {
        String s = new String();
        s += (char) arr[0];
        for (int i = 1; i < arr.length; i++) {
            arr[i] = arr[i - 1] + arr[i];
            s += (char) (arr[i]);
        }
        return s;
    }

    public static boolean canMove(String figure, String startPos, String finalPos){

        
        char startLetter = startPos.charAt(0);
        char finalLetter = finalPos.charAt(0);

        int startInt = startPos.charAt(1);
        int finalInt = finalPos.charAt(1);

        String list = "ABCDEFGH";

        if (figure == "Pawn"){
            if (startInt == (finalInt - 1) || startInt == (finalInt - 2)){
                return true;
            }
        }
        else if (figure == "Horse"){
            if (Math.pow((list.indexOf(finalLetter) + 1) - (list.indexOf(startLetter) + 1), 2) + Math.pow(finalInt - startInt, 2) == 5){
                return true;
            }
        }
        else if (figure == "Bishop"){
            if (Math.abs(list.indexOf(finalLetter) - list.indexOf(startLetter)) == Math.abs(finalInt - startInt)){
                return true;
            }
        }
        else if (figure == "Rook"){
            if (startLetter == finalLetter || startInt == finalInt){
                return true;
            }
        }
        else if (figure == "Queen"){
            if (Math.abs(list.indexOf(finalLetter) - list.indexOf(startLetter)) == Math.abs(finalInt - startInt) || startLetter == finalLetter || startInt == finalInt){
                return true;
            } 
        }
        else if (figure == "King"){
            if (Math.abs(list.indexOf(startLetter) - list.indexOf(finalLetter)) == 1 && Math.abs(startInt - finalInt) == 1){
                return true;
            }
        }
        
        return false;
    }

    public static boolean canComplete(String word1, String word2) {
        int count = 0;
        boolean state = false;
        for (int i = 0; i < word1.length(); i++) {
            state = false;
            for (int j = count; j < word2.length(); j++) {
                if (word1.charAt(i) == word2.charAt(j)) {
                    count = word1.indexOf(word1.charAt(i)) + 1;
                    state = true;
                    break;
                }
            }
            if (state == false) {
                return false;
            }
        }
        return true;
    }

    public static int sumDigProd(int ...nums) {
        int s = 0;
        for (int n: nums) {
            s += n;
        }
        int x = s;
        while (x > 9) {
            x = 1;
            while (s > 0) {
                x *= s % 10;
                s = s / 10;
            }
            s = x;
        }
        return x;
    }

    public static ArrayList<String> sameVowelGroup(String arr[]) {
        String letters = "aeyuio";
        ArrayList<Character> list1 = new ArrayList<>();
        ArrayList<Character> list2 = new ArrayList<>();
        ArrayList<String> list3 = new ArrayList<>();
        for (int i = 0; i < arr[0].length(); i++) {
            if (letters.indexOf(arr[0].charAt(i)) != -1) {
                list1.add(arr[0].charAt(i));
            }
        }
        Collections.sort(list1);
        for (int i = 1; i < list1.size(); i++) {
            if ((list1.get(i)).equals(list1.get(i-1))) {
                list1.remove(i-1);
            }
        }
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length(); j++) {
                if (letters.indexOf(arr[i].charAt(j)) != -1) {
                    list2.add(arr[i].charAt(j));
                }
                Collections.sort(list2);
                for (int n = 1; n < list2.size(); n++) {
                    if ((list2.get(n)).equals(list2.get(n-1))) {
                        list2.remove(n-1);
                    }
                }
            }
            if (list1.size() == list2.size()) {
                for (int n = 0; n < list2.size(); n++) {
                    if (list1.get(n) != list2.get(n)) {
                        break;
                    }
                    else {
                        list3.add(arr[i]);
                    }
                }
            }
            else break;
            list2.clear();
        }
        for (int i = 1; i < list3.size(); i++) {
            if ((list3.get(i)).equals(list3.get(i-1))) {
                list3.remove(i-1);
            }
        }
        return list3;
    }

    public static boolean validateCard(long n) {
        int sum = 0;
        ArrayList<Character> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        String s = String.valueOf(n).substring(0, String.valueOf(n).length()-1);
        if (s.length() < 13 || s.length() >= 18) {
            return false;
        }
        for (int i = s.length() - 1; i >= 0; i--) {
            list1.add(s.charAt(i));
        }
        for (int i = 0; i < list1.size(); i++) {
            if (i % 2 == 0) {
                list2.add(Character.getNumericValue(list1.get(i)) * 2);
            }
            else list2.add(Character.getNumericValue(list1.get(i)));
        }
        for (int i = 0; i < list1.size(); i++) {
            if (list2.get(i) > 9) {
                list2.set(i, list2.get(i) / 10 + list2.get(i) % 10);
            }
            sum += list2.get(i);
        }
        int a = Integer.valueOf(String.valueOf(n).substring(String.valueOf(n).length()-1));
        int b = 10 - Integer.valueOf(String.valueOf(sum).substring(String.valueOf(sum).length()-1));
        
        return (a == b);
    }

    public static String numToEng(int n) {
        String s = new String();
        int a, b, c;
        Map<Integer, String> numbers = new HashMap<Integer, String>();
        numbers.put(0, "zero");
        numbers.put(1, "one");
        numbers.put(2, "two");
        numbers.put(3, "three");
        numbers.put(4, "four");
        numbers.put(5, "five");
        numbers.put(6, "six");
        numbers.put(7, "seven");
        numbers.put(8, "eight");
        numbers.put(9, "nine");
        numbers.put(10, "ten");
        numbers.put(11, "eleven");
        numbers.put(12, "twelve");
        numbers.put(13, "thirteen");
        numbers.put(14, "fourteen");
        numbers.put(15, "fifteen");
        numbers.put(16, "sixteen");
        numbers.put(17, "seventeen");
        numbers.put(18, "eighteen");
        numbers.put(19, "nineteen");
        numbers.put(20, "twenty");
        numbers.put(30, "thirty");
        numbers.put(40, "forty");
        numbers.put(50, "fifty");
        numbers.put(60, "sixty");
        numbers.put(70, "seventy");
        numbers.put(80, "eighty");
        numbers.put(90, "ninety");
        numbers.put(100, "hundred");
        if (n >= 0 && n <= 20) {
            s = numbers.get(n);
        }
        if (n > 20 && n < 100) {
            a = n % 10;
            b = (n / 10) * 10;
            if (a != 0) s = numbers.get(b) + " " + numbers.get(a);
            else s = numbers.get(b);
        }
        if (n >= 100 && n <= 999) {
            a = n % 10;
            b = ((n % 100) / 10) * 10;
            c = n / 100;
            if (n % 100 == 0) s = numbers.get(c) + " " + numbers.get(100);
            else if (a == 0) s = numbers.get(c) + " " + numbers.get(100) + " " + numbers.get(b);
            else if (b == 0) s = numbers.get(c) + " " + numbers.get(100) + " " + numbers.get(a);
            else s = numbers.get(c) + " " + numbers.get(100) + " " + numbers.get(b) + " " + numbers.get(a);
        }
        return s;
    }

    public static String numToRus(int n) {
        String s = new String();
        int a, b, c;
        Map<Integer, String> numbers = new HashMap<Integer, String>();
        numbers.put(0, "ноль");
        numbers.put(1, "один");
        numbers.put(2, "два");
        numbers.put(3, "три");
        numbers.put(4, "четыре");
        numbers.put(5, "пять");
        numbers.put(6, "шесть");
        numbers.put(7, "семь");
        numbers.put(8, "восемь");
        numbers.put(9, "девять");
        numbers.put(10, "десять");
        numbers.put(11, "одиннадцать");
        numbers.put(12, "двенадцать");
        numbers.put(13, "тринадцать");
        numbers.put(14, "четырнадцать");
        numbers.put(15, "пятнадцать");
        numbers.put(16, "шестнадцать");
        numbers.put(17, "семнадцать");
        numbers.put(18, "восемнадцать");
        numbers.put(19, "девятнадцать");
        numbers.put(20, "двадцать");
        numbers.put(30, "тридцать");
        numbers.put(40, "сорок");
        numbers.put(50, "пятьдесят");
        numbers.put(60, "шестьдесят");
        numbers.put(70, "семьдесят");
        numbers.put(80, "восемьдесят");
        numbers.put(90, "девяносто");
        numbers.put(100, "сто");
        numbers.put(200, "двести");
        numbers.put(300, "триста");
        numbers.put(400, "четыреста");
        numbers.put(500, "пятьсот");
        numbers.put(600, "шестьсот");
        numbers.put(700, "семьсот");
        numbers.put(800, "восемьсот");
        numbers.put(900, "девятьсот");
        if (n >= 0 && n <= 20) {
            s = numbers.get(n);
        }
        if (n > 20 && n < 100) {
            a = n % 10;
            b = (n / 10) * 10;
            if (a != 0) s = numbers.get(b) + " " + numbers.get(a);
            else s = numbers.get(b);
        }
        if (n >= 100 && n <= 999) {
            a = n % 10;
            b = ((n % 100) / 10) * 10;
            c = n - n % 100;
            if (n % 100 == 0) s = numbers.get(c);
            else if (a == 0) s = numbers.get(c) + " " + numbers.get(b);
            else if (b == 0) s = numbers.get(c) + " " + numbers.get(a);
            else s = numbers.get(c) + " " + numbers.get(b) + " " + numbers.get(a);
        }
        return s;
    }

    public static String getSha256Hash(String s) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(s.getBytes());
        byte[] hash = md.digest();
        StringBuffer sb = new StringBuffer();
        for (byte b : hash) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    public static String correctTitle(String s) {
        String str = new String();
        String arr[] = s.toLowerCase().split(" ");
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals("and") || arr[i].equals("the") || arr[i].equals("of") || arr[i].equals("in")) {
                str += arr[i] + " ";
            }
            else str += arr[i].substring(0, 1).toUpperCase() + arr[i].substring(1) + " ";
        }
        return str;
    }

    public static String hexLattice(int n) {
        int count = 1;
        int i = 0;
        int step = 6;
        // Проверяем является ли число центрированным шестиугольным
        // находим количество итераций
        while (count < n) {
            i++;
            count += step * i;
        }
        if (count == n) {
            String s = new String();
            int space = i;
            // Строки до центра включая
            for (int j = 0; j < i + 1; j++) {
                String line = new String();
                line += " ".repeat(space - j);
                String middle = new String();
                middle += "o ".repeat(i + 1 + j);
                middle = middle.strip();
                line += middle;
                line += " ".repeat(space - j) + "\n";
                s += line;
            }
            // Строки от центра
            for (int j = i - 1; j >= 0; j--) {
                String line = "";
                line += " ".repeat(space - j);
                String middle = "";
                middle += "o ".repeat(i + 1 + j);
                middle = middle.strip();
                line += middle;
                line += " ".repeat(space - j) + "\n";
                s += line;
            }
            return s;
        } 
        else return "Invalid";
    }
    
}
