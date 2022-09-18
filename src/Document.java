import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Document {
    public String name;
    public TwoWayCycledOrderedListWithSentinel<Link> link;

    public Document(String name, Scanner scan) {
        this.name = name.toLowerCase();
        link = new TwoWayCycledOrderedListWithSentinel<Link>();
        load(scan);
    }

    public void load(Scanner scan) {
        while (scan.hasNext()) {
            String line = scan.nextLine();
            if (line.equals("eod")) return;

            String[] oneLine = line.split(" ");
            String regex = "link=(.+)";
            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
            Matcher matcher;

            for (String string : oneLine) {
                matcher = pattern.matcher(string);

                if (matcher.matches())
                    if (isCorrectLink(matcher.group(1))) {
                        link.add(new Link(matcher.group(1).toLowerCase()));
                    } else if (checkWithWeight(matcher.group(1))) {
                        link.add(createLink(matcher.group(1).toLowerCase()));
                    }
            }
        }
    }

    public static boolean isCorrectLink(String id) {
        // (([0-9]*)\)
        String regex = "[a-zA-Z][a-zA-Z_0-9]*";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(id);

        return matcher.matches();
    }

    public static boolean checkWithWeight(String id) {
        // (([0-9]*)\)
        boolean result = false;

        String regex = "[a-zA-Z][a-zA-Z_0-9]*(.*)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(id);

        if (matcher.matches()) {
            String weightReg = "(\\({1}([1-9]{1}[0-9]*)\\){1})";
            Pattern weightPat = Pattern.compile(weightReg);
            Matcher weightMatch = weightPat.matcher(matcher.group(1));

            return weightMatch.matches();
        }

        return false;
    }

    // accepted only small letters, capitalic letter, digits nad '_' (but not on the begin)
    static Link createLink(String link) {
        String regex = "([a-zA-Z][a-zA-Z_0-9]*)\\((.*)\\)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(link);

        if (matcher.matches()) {

            return new Link(matcher.group(1), Integer.parseInt(matcher.group(2)));
        }
        return null;
    }

    public static boolean isCorrectId(String id) {
        String regex = "[a-zA-Z][a-zA-Z_0-9]*";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(id);

        return matcher.matches();
    }

    @Override
    public String toString() {
        return "Document: " + name + link.toString().toLowerCase();
    }

    public String toStringReverse() {
        return "Document: " + name + link.toStringReverse().toLowerCase();
    }

    public int[] getWeights() {
        int[] weights = new int[link.size];

        for (int i = 0; i < link.size; i++) {
            weights[i] = link.get(i).getWeight();
        }

        return weights;
    }

    public static void showArray(int[] arr) {//Sysout w sortach
        for (int i = 0; i < arr.length; i++) {
            if (i != arr.length - 1) {
                System.out.print(arr[i] + " ");
            } else {
                System.out.print(arr[i]);
            }
        }
    }

    void bubbleSort(int[] arr) {
//        int elemCount = 0;
//        int temp;
//        boolean isSorted = false;
//
//        while (!isSorted){
//            isSorted = true;
//            showArray(arr);
//            System.out.println();
//
//            for (int j = arr.length - 1; j > elemCount; j--) {
//                if (arr[j] < arr[j - 1]) {
//                    temp = arr[j];
//                    arr[j] = arr[j - 1];
//                    arr[j - 1] = temp;
//
//                    isSorted = false;
//                }
//            }
//
//            elemCount++;
//        }
        int elemCount = 0;
        int temp;

        for (int i = 0 ; i < arr.length; i++){
            showArray(arr);
            System.out.println();

            for (int j = arr.length - 1; j > elemCount;j--){
                if ( arr[j] < arr[j - 1]){
                    temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                }
            }

            elemCount++;
        }
    }

    public void insertSort(int[] arr) {
        int currInt;

        for (int i = 0; i < arr.length; i++) {
            currInt = arr[arr.length - i - 1];

            for (int j = arr.length - i; j < arr.length; j++) {
                if (currInt < arr[j]) {
                    break;

                } else {
                    arr[j - 1] = arr[j];
                    arr[j] = currInt;
                }
            }

            showArray(arr);
            System.out.println();
        }
    }

    public void selectSort(int[] arr) {
        int elemCount = 0;
        int currMax;
        int indexMax = -1;
        int temp;

        for (int i = 0; i < arr.length; i++) {
            showArray(arr);
            System.out.println();

            currMax = Integer.MIN_VALUE;
            for (int j = 0; j < arr.length - elemCount; j++) {
                if (arr[j] > currMax) {
                    currMax = arr[j];
                    indexMax = j;
                }
            }

            temp = arr[arr.length - elemCount - 1];
            arr[arr.length - elemCount - 1] = currMax;
            arr[indexMax] = temp;

            elemCount++;
        }
    }

    public void show2dArray(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (i == array.length - 1 && j == array[i].length - 1) {
                    System.out.print(array[i][j]);
                } else {
                    System.out.print(array[i][j] + " ");
                }
            }
        }
    }

    public void iterativeMergeSort(int[] arr) {
        showArray(arr);
        System.out.println();
        double arrSize = 2;
        int[][] arrOfArr;
        arrOfArr = new int[arr.length][1];

        for (int i = 0; i < arr.length; i++) {
            arrOfArr[i][0] = arr[i];
        }

        int[][] arrOfArrNext = new int[(int) Math.ceil(arr.length / arrSize)][(int) arrSize];
        int firstArrIndex;
        int secondArrIndex;
        int nextArrIndex;

        while (arrOfArr[0].length < arr.length) {

            for (int i = 0; i <= arrOfArr.length - 1; i += 2) {
                if (arrOfArr.length % 2 != 0 && i == arrOfArr.length - 1) {
                    arrOfArrNext[arrOfArrNext.length - 1] = arrOfArr[i];
                    continue;
                }

                firstArrIndex = 0;
                secondArrIndex = 0;
                nextArrIndex = 0;

                while (nextArrIndex != arrSize && nextArrIndex != arr.length) {
                    if (nextArrIndex == arrOfArrNext[i / 2].length) {
                        break;
                    }

                    if (arrOfArr[i][firstArrIndex] <= arrOfArr[i + 1][secondArrIndex]) {
                        arrOfArrNext[i / 2][nextArrIndex] = arrOfArr[i][firstArrIndex];
                        nextArrIndex++;

                        if (firstArrIndex != arrOfArr[i].length - 1) {
                            firstArrIndex++;
                        } else {
                            arrOfArr[i][firstArrIndex] = Integer.MAX_VALUE;
                        }

                    } else {
                        arrOfArrNext[i / 2][nextArrIndex] = arrOfArr[i + 1][secondArrIndex];
                        nextArrIndex++;

                        if (secondArrIndex != arrOfArr[i + 1].length - 1) {
                            secondArrIndex++;
                        } else {
                            arrOfArr[i + 1][secondArrIndex] = Integer.MAX_VALUE;
                        }
                    }
                }
            }

            show2dArray(arrOfArrNext);
            System.out.println();
            arrSize = arrSize * 2;
            arrOfArr = arrOfArrNext;
            arrOfArrNext = new int[(int) Math.ceil(arr.length / arrSize)][(int) arrSize];

            double modulo = arr.length % arrSize;

            if (modulo != 0) {
                arrOfArrNext[arrOfArrNext.length - 1] = new int[(int) modulo];
            }

            if ((int) Math.ceil(arr.length / arrSize) == 1) {
                arrOfArrNext[0] = new int[arr.length];
            }
        }
    }

    private void countSort(int[] arr, int n, int exp) {
        int[] output = new int[n]; // output array
        int[] count = new int[10];
        int i;
        Arrays.fill(count, 0);

        for (i = 0; i < n; i++)
            count[(arr[i] / exp) % 10]++;

        for (i = 1; i < 10; i++)
            count[i] += count[i - 1];

        for (i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }

        for (i = 0; i < n; i++) {
            arr[i] = output[i];
        }

        showArray(arr);
        System.out.println();
    }

    public void radixSort(int[] arr) {
        if (arr.length != 0) {
            showArray(arr);
            System.out.println();

            for (int exp = 1; exp <= 100; exp *= 10)
                countSort(arr, arr.length, exp);

        } else {
            return;
        }
    }
}