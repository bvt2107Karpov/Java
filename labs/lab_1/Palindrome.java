public class Palindrome {
    public static void main(String[] args) {
        boolean isAllPalindrome = true;
        for(int i = 0; i < args.length; i++){
            if (!isPalindrome(args[i])) {
                isAllPalindrome = false;
            }
            System.out.println(args[i]+" "+isAllPalindrome);
        }
    }
    public static String reverseString(String s) {
        return new StringBuilder(s).reverse().toString();
    }
    public static boolean isPalindrome(String s) {
        return s.equals(reverseString(s));
    }
}