import java.util.Arrays;

class Testing {
    public static int log2(int N) {
        int result = (int)Math.ceil(Math.log(N) / Math.log(2));

        return result;
    }

    public static void main(String[] args) {
        System.out.println(log2(4));
    }
}