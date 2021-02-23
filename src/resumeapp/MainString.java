package resumeapp;

public class MainString {
    public static void main(String[] args) {
        String[] str = {"a ", "b ", "c ", "d "};
        StringBuilder result = new StringBuilder();
        for (String s : str) {
            result.append(s);
        }
        String s1 = "abc";
        String s3 = "ab";
        String s4 = "c";
        String s5 = s3 + s4;
        System.out.println(s1 == s5);
    }
}
