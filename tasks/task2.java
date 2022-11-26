package tasks;

import java.util.Arrays;

public class task2 {
    public static void main(String[] args){
        System.out.println(repeat("hello", 3));
        System.out.println(differenceMaxMin(new int[] { 44, 32, 86, 19}));
        System.out.println(isAvgWhole(new int[] {1, 2, 3, 4}));
        System.out.println(Arrays.toString(cumulativeSum(new int[] {1, 2, 3})));
        System.out.println(getDecimalPlaces("43.20"));
        System.out.println(Fibonacci(12));
        System.out.println(isValid("59001"));
        System.out.println(isStrangePair("aba", "aba"));
        System.out.println(isPrefix("automation", "auto-"));
        System.out.println(isSuffix("arachnophobia", "-phobia"));
        System.out.println(boxSeq(4));
    }

    private static String repeat(String str, int numOfRepeat){
        String newStr = "";

        for (int i = 0; i < str.length(); i++){
            String oneChar = String.valueOf(str.charAt(i));
            String temp = "";

            for (int j = 0; j < numOfRepeat; j++){
                temp += oneChar;
            }
            newStr += temp;
        }
        return newStr;
    }

    private static Integer differenceMaxMin(int[] numbers){
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < numbers.length; i++){
            if (numbers[i] > max)
                max = numbers[i];
            if (numbers[i] < min)
                min = numbers[i];
        }

        return (max - min);
    }

    private static boolean isAvgWhole(int[] numbers) {
        int sumOfArray = 0;

        for (int i = 0; i < numbers.length; i++) {
            sumOfArray += numbers[i];
        }

        return sumOfArray % numbers.length == 0;

    }
    
    private static int[] cumulativeSum(int[] arr){
        int[] new_arr = arr.clone();
        for (int i = 0; i < arr.length; i++)
            for (int k = 0; k < i; k++)
                new_arr[i] += arr[k];      
        
        return new_arr;
    }


    private static Integer getDecimalPlaces(String number) {
        if (number.contains(".")) {
        String fractionalPart = number.split("\\.")[1];
        return fractionalPart.length();
        }
        else {
            return 0;
        }
        
    }

    private static Integer Fibonacci(int number) {

        if (number == 0)
            return 0;
        else if (number == 1)
            return 1;
        else if (number == 2)
            return 2;

        return Fibonacci(number - 1) + Fibonacci(number - 2);
    }

    private static boolean isValid(String str) {
        try {
            int test = Integer.parseInt(str);
        } 
        catch (NumberFormatException nfe) {
            return false;
        }
        if (str.length()==5){
        return true;
    }
        else {
            return false;
        }
    }

    private static boolean isStrangePair(String firstStr, String secondStr) {
        if (firstStr == "" & secondStr == "") {
            return true;
        }
        if (firstStr == "" || secondStr == ""){
            return false;
        } 
        return ((firstStr.charAt(0) == secondStr.charAt(secondStr.length() - 1))
                && (firstStr.charAt(firstStr.length() - 1) == secondStr.charAt(0)));

    }

    private static boolean isPrefix(String word, String prefix) {
        String a = prefix.substring(0, prefix.length()-1);
        String b = word.substring(0, a.length());
        return b.equals(a);
    }

    private static boolean isSuffix(String word, String suffix) {
        String a = suffix.substring(1, suffix.length());
        String b = word.substring(word.length() - a.length(), word.length());
        return b.equals(a);
    }

    private static int boxSeq(int step) {
        int start = 0;
        if (step == 0)
            return 0;
        for (int i = 1; i <= step; i++) {
            if (i % 2 == 1)
                start += 3;
            else
                start -= 1;
        }

        return start;
    }

}
