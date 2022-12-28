package tasks;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
 

public class task6 {
    public static void main(String[] args) throws Exception {
        System.out.println(bell(1));
        System.out.println(translateWord("flag"));
        System.out.println(translateWord(""));
        System.out.println(translateSentence("I like to eat honey waffles."));
        System.out.println(validColor("rgb(0,0,0)"));
        System.out.println(validColor("rgba(0,0,0,0.123456789)"));
        System.out.println(stripUrlParams("https://edabit.com?a=1&b=2&a=2"));
        System.out.println(stripUrlParams("https://edabit.com?a=1&b=2&a=2", new String[] {"b"}));
        System.out.println(getHashTags("How the Avocado Became the Fruit of the Global Trade"));
        System.out.println(getHashTags("Science Visualizing"));
        System.out.println(ulam(4));
        System.out.println(ulam(9));  
        System.out.println(longestNonrepeatingSubstring("abcabcbb"));
        System.out.println(longestNonrepeatingSubstring("aaaaaa")); 
        System.out.println(convertToRoman(2));
        System.out.println(convertToRoman(12));
        System.out.println(convertToRoman(16));;
        System.out.println(formula("6 * 4 = 24"));
        System.out.println(palindromeDescendant(11211230));
        System.out.println(palindromeDescendant(13001120));
    }

    //число белла
    public static int bell(int n) {
        int[] arr1 = new int[n];
        int[] arr2 = new int[n];
        int b = 1;
        arr1[0] = 1;
        for (int i = 1; i < n; i++) {
            arr2[0] = b;
            for (int j = 1; j < i + 1; j++) {
                arr2[j] = b + arr1[j-1];
                b = arr2[j];
            }
            b = arr2[i];
            for (int j = 0; j < n; j++) {
                arr1[j] = arr2[j];
            }
        }
        return b;
    }

    //поросячий латинский
    public static String translateWord(String s) {
        String letters = "aeyuioAEYUIO";
        int n = 0;
        if (s.isEmpty()) {
            return "";
        }
        if (letters.indexOf(s.charAt(0)) != -1) {
            s += "yay";
        }
        else {
            while (letters.indexOf(s.charAt(n)) == -1) {
                n++;
            }
            if (Character.isUpperCase(s.charAt(0))) {
                s = s.substring(n, n + 1).toUpperCase() + s.substring(n + 1) + s.substring(0, n).toLowerCase() + "ay";
            } 
            else s = s.substring(n) + s.substring(0, n) + "ay";
        }
        return s;
    }

    public static String translateSentence(String s) {
        String sign = s.substring(s.length()-1);
        String str = new String();
        String[] arr = s.substring(0,s.length()-1).split(" ");
        str = translateWord(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            str += " " + translateWord(arr[i]);
        }
        return str + sign;
    }

    //формат цвета
    public static boolean validColor(String s) {
        if (s.substring(0,4).contains("rgb(")) {
            s = s.substring(4, s.length() - 1);
        }
        else if (s.substring(0, 5).contains("rgba(")) {
            s = s.substring(5, s.length() - 1);
        }
        String[] arr1 = s.split(",");
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i].isEmpty()) return false;
        } 
        double[] arr2 = new double[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            arr2[i] = Double.parseDouble(arr1[i]);
        }
        if (arr2.length == 3) {
            for (int i = 0; i < arr2.length; i++) {
                int b = (int) arr2[i];
                if (b >= 0 && b <= 255) continue;
                else return false;
            }
        }
        if (arr2.length == 4) {
            for (int i = 0; i < arr2.length - 1; i++) {
                int b = (int) arr2[i];
                if (b >= 0 && b <= 255) continue;
                else return false;
            }
            if (arr2[arr2.length - 1] >= 0 && arr2[arr2.length - 1] <= 1) return true;
        }
        return true;
    }

    //ссылка
    public static String stripUrlParams(String url) {
        return stripUrlParams(url, new String[] { "" });
    }

    public static String stripUrlParams(String url, String[] arr) {
        String s = new String();
        ArrayList<String> list = new ArrayList<>();
        if (url.contains("?")) {
            s = url.substring(url.indexOf("?") + 1);
            url = url.substring(0, url.indexOf("?") + 1);
        }
        else {
            return url;
        }
        String[] arr1 = s.split("&");
        Arrays.sort(arr1);
        for (int i = 0; i < arr1.length; i++) {
            list.add(arr1[i]);
        }
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i).substring(0, 1).equals(list.get(i-1).substring(0, 1))) {
                list.remove(i-1);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).substring(0, 1).equals(arr[0])) {
                list.remove(i);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            url += list.get(i) + "&";
        }
        return url.substring(0,url.length()-1);
    }

    //длинное слово + хэштэг
    public static ArrayList<String> getHashTags(String s) {
        ArrayList<String> list = new ArrayList<>();
        String str;
        s = s.toLowerCase().replace(',', ' ');
        String[] arr = s.split(" ");
        if (arr.length < 3) {
            if (arr[0].length() < arr[1].length()) {
                list.add("#" + arr[1]);
                list.add("#" + arr[0]);
            }
            else if (arr[0].length() > arr[1].length()) {
                list.add("#" + arr[0]);
                list.add("#" + arr[1]);
            }
        }
        else {
            for(int i = 1; i < arr.length; i++) {
                for(int j = arr.length-1; j >= i; j--) {
                    if(arr[j-1].length() < arr[j].length()){
                        str = arr[j-1];
                        arr[j-1] = arr[j];
                        arr[j] = str;
                    }
                }
            }
            for (int i = 0; i < 3; i++) {
                list.add("#" + arr[i]);
            }
        }
        return list;
    }

    //последовательность Улама
    public static int ulam(int n) {
        ArrayList<Integer> list = new ArrayList<>(n);
        list.add(1);
        list.add(2);
        list.add(3);
        int max = 3;
        int x = 4; //число по счету
        int num = 4; //просто число
        //поочередно вычитаем все числа из последовательности
        //если число есть, то счетчик увеличивается
        //запускается 2 цикл по тому же списку и ищем в нем
        while (x <= n) {
            int count = 0;
            int find = 0;
            for (int i = 0; i < list.size() - 1; i++) {
                if (num < max) {
                    num++;
                    continue;
                }
                find = num - list.get(i);
                for (int j = list.size() - 1; j > i; j--) {
                    if (list.get(j) == list.get(i)) {
                        num++;
                        break;
                    }
                    if (find == list.get(j)) {
                        count++;
                        break;
                    }
                }
                
            }
            if (count == 1) {
                list.add(num);
                max = num;
                x++;
            }
            num++;
        }
        return list.get(n-1);
    }
    
    //самая длинная подстрока
    public static String longestNonrepeatingSubstring(String s) {
        ArrayList<String> list = new ArrayList<>();
        String a = new String();
        String b = new String();
        for (int i = 0; i < s.length(); i++) {
            if (list.contains(s.charAt(i) + "")) {
                if (b.length() > a.length()) {
                    a = b;
                }
                b = "";
            }
            else {
                list.add(s.charAt(i) + "");
                b += s.charAt(i) + "";
                if (b.length() > a.length()) {
                    a = b;
                }
            }
        }
        return a;
    }

    //римские числа
    public static String convertToRoman(int n) {
        String s = new String();
        int a, b, c, d;
        Map<Integer, String> numbers = new HashMap<Integer, String>();
        numbers.put(1, "I");
        numbers.put(2, "II");
        numbers.put(3, "III");
        numbers.put(4, "IV");
        numbers.put(5, "V");
        numbers.put(6, "VI");
        numbers.put(7, "VII");
        numbers.put(8, "VIII");
        numbers.put(9, "IX");
        numbers.put(10, "X");
        numbers.put(50, "L");
        numbers.put(100, "C");
        numbers.put(500, "D");
        numbers.put(1000, "M");
        if (n >= 0 && n <= 10 || n == 50 || n == 100 || n == 500 || n == 1000) {
            s = numbers.get(n);
        }
        if (n % 10 == 0 && n / 10 > 0 && n / 10 < 10) {
            a = n / 10;
            if (a < 4) {
                for (int i = 1; i <= a; i++) {
                    s += numbers.get(10);
                }
            }
            if (a == 4) {
                s += numbers.get(10) + numbers.get(50);;
            }
            if (a > 5 && a < 9) {
                s += numbers.get(50);
                for (int i = 1; i <= a; i++) {
                    s += numbers.get(10);
                }
            }
            if (a == 9) {
                s += numbers.get(10) + numbers.get(100);;
            }
        }
        if (n % 100 == 0 && n / 100 > 0 && n / 100 < 10) {
            a = n / 100;
            if (a < 4) {
                for (int i = 1; i <= a; i++) {
                    s += numbers.get(100);
                }
            }
            if (a == 4) {
                s += numbers.get(100) + numbers.get(500);;
            }
            if (a > 5 && a < 9) {
                s += numbers.get(500);
                for (int i = 1; i <= a; i++) {
                    s += numbers.get(100);
                }
            }
            if (a == 9) {
                s += numbers.get(100) + numbers.get(1000);;
            }
        }
        if (n % 1000 == 0 && n / 1000 > 0 && n / 1000 < 3) {
            a = n / 1000;
            for (int i = 1; i <= a; i++) {
                s += numbers.get(1000);
            }
        }
        if (n >= 11 && n < 40) {
            a = n / 10;
            b = n % 10;
            for (int i = 1; i <= a; i++) {
                s += numbers.get(10);
            }
            s += numbers.get(b);
        }
        if (n > 40 && n < 50) {
            b = n % 10;
            s += numbers.get(10) + numbers.get(50) + numbers.get(b);
        }
        if (n > 50 && n < 90) {
            a = (n - 50) / 10;
            b = n % 10;
            s = numbers.get(50);
            for (int i = 1; i <= a; i++) {
                s += numbers.get(10);
            }
            s += numbers.get(b);
        }
        if (n > 90 && n < 100) {
            b = n % 10;
            s += numbers.get(10) + numbers.get(100) + numbers.get(b);
        }
        if (n > 100 && n < 400) {
            a = n / 100;
            for (int i = 1; i <= a; i++) {
                s += numbers.get(100);
            }
            b = (n / 10) % 10;
            if (b < 4) {
                for (int i = 1; i <= b; i++) {
                    s += numbers.get(10);
                }
            }
            else if (b == 4) {
                s += numbers.get(10) + numbers.get(50);
            }
            else if (b == 5) {
                s += numbers.get(50);
            }
            else if (b == 9) {
                s += numbers.get(10) + numbers.get(100);
            }
            else {
                s += numbers.get(50);;
                for (int i = 6; i <= b; i++) {
                    s += numbers.get(10);
                }
            }
            c = n % 10;
            s += numbers.get(c);
        }
        if (n > 400 && n < 500) {
            s += numbers.get(100) + numbers.get(500);
            b = (n / 10) % 10;
            if (b < 4) {
                for (int i = 1; i <= b; i++) {
                    s += numbers.get(10);
                }
            }
            else if (b == 4) {
                s += numbers.get(10) + numbers.get(50);
            }
            else if (b == 5) {
                s += numbers.get(50);
            }
            else if (b == 9) {
                s += numbers.get(10) + numbers.get(100);
            }
            else {
                s += numbers.get(50);;
                for (int i = 6; i <= b; i++) {
                    s += numbers.get(10);
                }
            }
            c = n % 10;
            s += numbers.get(c);
        }
        if (n > 500 && n < 900) {
            a = (n - 500) / 100;
            s += numbers.get(500);
            for (int i = 1; i <= a; i++) {
                s += numbers.get(100);
            }
            b = (n / 10) % 10;
            if (b < 4) {
                for (int i = 1; i <= b; i++) {
                    s += numbers.get(10);
                }
            }
            else if (b == 4) {
                s += numbers.get(10) + numbers.get(50);
            }
            else if (b == 5) {
                s += numbers.get(50);
            }
            else if (b == 9) {
                s += numbers.get(10) + numbers.get(100);
            }
            else {
                s += numbers.get(50);;
                for (int i = 6; i <= b; i++) {
                    s += numbers.get(10);
                }
            }
            c = n % 10;
            s += numbers.get(c);
        }
        if (n > 900 && n < 1000) {
            s += numbers.get(100) + numbers.get(1000);
            b = (n / 10) % 10;
            if (b < 4) {
                for (int i = 1; i <= b; i++) {
                    s += numbers.get(10);
                }
            }
            else if (b == 4) {
                s += numbers.get(10) + numbers.get(50);
            }
            else if (b == 5) {
                s += numbers.get(50);
            }
            else if (b == 9) {
                s += numbers.get(10) + numbers.get(100);
            }
            else {
                s += numbers.get(50);;
                for (int i = 6; i <= b; i++) {
                    s += numbers.get(10);
                }
            }
            c = n % 10;
            s += numbers.get(c);
        }
        if (n > 1000 && n < 4000) {
            a = n / 1000;
            for (int i = 1; i <= a; i++) {
                s += numbers.get(1000);
            }
            b = (n / 100) % 10;
            if (b < 4) {
                for (int i = 1; i <= b; i++) {
                    s += numbers.get(100);
                }
            }
            else if (b == 4) {
                s += numbers.get(100) + numbers.get(500);
            }
            else if (b == 5) {
                s += numbers.get(500);
            }
            else if (b == 9) {
                s += numbers.get(100) + numbers.get(1000);
            }
            else {
                s += numbers.get(50);;
                for (int i = 6; i <= b; i++) {
                    s += numbers.get(10);
                }
            }
            c = (n / 10) % 10;
            if (c < 4) {
                for (int i = 1; i <= b; i++) {
                    s += numbers.get(10);
                }
            }
            else if (c == 4) {
                s += numbers.get(10) + numbers.get(50);
            }
            else if (c == 5) {
                s += numbers.get(50);
            }
            else if (c == 9) {
                s += numbers.get(10) + numbers.get(100);
            }
            else {
                s += numbers.get(50);;
                for (int i = 6; i <= c; i++) {
                    s += numbers.get(10);
                }
            }
            d = n % 10;
            s += numbers.get(d);
        }
        return s;
    }

    //выражение
    public static boolean formula(String str) {
        if (str.substring(str.indexOf("=") + 1).contains("=")) {
            return false;
        }
        String[] arr = str.split(" [+-/=\\*] ");
        int a = Integer.parseInt(arr[0]);
        int b = Integer.parseInt(arr[1]);
        int c = Integer.parseInt(arr[2]);
        if (str.contains("+") && a + b == c) {
            return true;
        }
        else if (str.contains("-") && a - b == c) {
            return true;
        }
        else if (str.contains("*") && a * b == c) {
            return true;
        }
        else if (str.contains("/") && a / b == c) {
            return true;
        }
        return false;
    }


    //палиндром
    public static boolean palindrome(int n) {
        String a = Integer.toString(n);
        String b = new String();
        for (int i = a.length() - 1 ; i >= 0; i--) {
            b += a.charAt(i);
        }
        if (Integer.parseInt(a) != Integer.parseInt(b)) {
            return false;
        }
        return true;
    }

    public static int sumNum(int n) {
        String a = Integer.toString(n);
        String b = new String();
        for (int i = 1; i < a.length(); i += 2) {
            int x = Character.getNumericValue(a.charAt(i)) + Character.getNumericValue(a.charAt(i-1));
            b += Integer.toString(x);
        }
        return Integer.parseInt(b);
    }

    public static boolean palindromeDescendant(int n) {
        int x = n;
        if (palindrome(n)) {
            return true;
        }
        while (Integer.toString(x).length() % 2 == 0) {
            x = sumNum(x);
            if (palindrome(x)) {
                return true;
            }
        }
        return false;
    }

}