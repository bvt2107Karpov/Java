package tasks;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class task3 {
    public static void main(String[] args){
        System.out.println(solutions(new int[] {1, 0, -1}));
        System.out.println(findZip("all zip files are zipped"));
        System.out.println(checkPerfect(6));
        System.out.println(flipEndChars("Cat, dog, and mouse."));
        System.out.println(isValidHexCode("#CD5C5C"));
        System.out.println(same(new Integer[] {2}, new Integer[] {3, 3, 3, 3}));
        System.out.println(isKaprekar(3));
        System.out.println(longestZero("01100001011000"));
        System.out.println(nextPrime(12));
        System.out.println(rightTriangle(3, 4, 5));
    }


    private static int solutions(int[] numbers){
        if (numbers[1] * numbers[1] - 4 * numbers[0] * numbers[2] > 0) return 2;
        else if (numbers[1] * numbers[1] - 4 * numbers[0] * numbers[2] == 0) return 1;
        else return 0;
    }


    private static int findZip(String text){
        boolean start_func = false;
        for (int i = 0; i < text.length(); i++){
            if (text.charAt(i) == 'z' && text.charAt(i+1) == 'i' && text.charAt(i+2) == 'p'){
                if (start_func)
                    return i;
                start_func = true;
            }
        }
        return -1;
    }


    private static boolean checkPerfect(int numbers){
        int sum = 0;
        for (int i = 1; i < numbers; i++){
            if (numbers % i == 0){
                sum += i;
            }
        }
        return (sum == numbers) ? true : false;
    }


    private static String flipEndChars(String s){
        if (s.length() < 2){
            return "Incompatible";
        }

        if (s.charAt(0) == s.charAt(s.length() - 1)){
            return "Two's a pair";
        }

        return s.charAt(s.length() - 1) + "" + s.subSequence(1, s.length() - 1) + "" + s.charAt(0);
    }


    private static boolean isValidHexCode(String str){
        String s = "abcdef1234567890";
        String substr = (str.toLowerCase()).substring(1);
        if (str.charAt(0) == '#' && substr.length() == 6){
            for (int i = 0; i < substr.length(); i++){
                if (s.indexOf(substr.charAt(i)) != -1) continue;
                else return false;
            }
        }
        else return false;
        return true;
    }


    public static boolean same(Integer[] arr1, Integer[] arr2){
        int difInt1 = 0;
        int difInt2 = 0;

        for (int i = 0; i < arr1.length; i++){
            int thisInt = arr1[i];
            boolean sameInt = false;

            for (int j = 0; j < i; j++){
                if (thisInt == arr1[j]){
                    sameInt = true;
                }
            }
            if (!sameInt){
                difInt1++;
            }
        }

        for (int i = 0; i < arr2.length; i++){
            int thisInt = arr2[i];
            boolean sameInt = false;

            for (int j = 0; j < i; j++){
                if (thisInt == arr2[j]){
                    sameInt = true;
                }
            }
            if (!sameInt){
                difInt2++;
            }
        }
        return (difInt1 == difInt2) ? true : false;
    }


    private static boolean isKaprekar(int x){
        if (x == 0 || x == 1)
            return true;
        if (x == 3) {
            return false;
        }
        Integer xx = x * x;
        String s = xx.toString();
        String left = "";
        String right = "";
        if (s.length() % 2 == 0) {
            left = String.copyValueOf(s.toCharArray(), 0, (s.length()) / 2);
            right = String.copyValueOf(s.toCharArray(), (s.length()) / 2, s.length() / 2);
        } else {
            left += String.copyValueOf(s.toCharArray(), 0, s.length() / 2);
            right += String.copyValueOf(s.toCharArray(), (s.length()) / 2, s.length() / 2 + 1);
        }
        int ans = Integer.parseInt(left) + Integer.parseInt(right);
        return ans == x ? true : false;
    }


    private static String longestZero(String s){
        int max_count = 0;
        int x = 0;
        for(int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '0') {
                x++;
                if (x > max_count) max_count = x;
            }
            else {
                x = 0;
            }
        }
        String answer = "0";
        String sRepeated = IntStream.range(0, max_count).mapToObj(i -> answer).collect(Collectors.joining(""));
        return sRepeated;
    }


    private static int nextPrime(int x){
        boolean isPrime = false;
        while (isPrime != true) {
            for(int i = 2; i <= Math.sqrt(x); i++) {
                if ((x % i) == 0) {
                    isPrime = false;
                    x++;
                }
                else {
                    isPrime = true;
                }
            }   
        }
        return x;
    }
    
    
    private static boolean rightTriangle(int a, int b, int c){
        int gip = 0;
        int kat1 = 0;
        int kat2 = 0;
        if (a >= b && a >= c) {
            gip = a;
            kat1 = b;
            kat2 = c;
        }
        if (b >= a && b >= c) {
            gip = b;
            kat1 = a;
            kat2 = c;
        }
        if (c >= b && c >= a) {
            gip = c;
            kat1 = b;
            kat2 = a;
        }
        return gip * gip == kat1 * kat1 + kat2 * kat2 ? true : false;
    }
    
}   
