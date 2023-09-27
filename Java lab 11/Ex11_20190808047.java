import java.util.*;

/**
 * Burak Aydın
 * 20190808047
 * 2023-06-25
 */

public class Ex11_20190808047 {

    public static int numOfTriplets(int[] arr, int sum) {
        int count = 0;
        int n = arr.length;

        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (arr[i] + arr[j] + arr[k] < sum) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public static int kthSmallest(int[] arr, int k) {
        return quickSelect(arr, 0, arr.length - 1, k - 1);
    }

    private static int quickSelect(int[] arr, int low, int high, int k) {
        if (low == high) {
            return arr[low];
        }

        int pivotIndex = partition(arr, low, high);

        if (k == pivotIndex) {
            return arr[k];
        } else if (k < pivotIndex) {
            return quickSelect(arr, low, pivotIndex - 1, k);
        } else {
            return quickSelect(arr, pivotIndex + 1, high, k);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static String subSequence(String str) {
        int n = str.length();
        int[] dp = new int[n];
        int[] prev = new int[n];

        int maxLength = 1;
        int endIndex = 0;

        for (int i = 0; i < n; i++) {
            dp[i] = 1;
            prev[i] = -1;

            for (int j = 0; j < i; j++) {
                if (str.charAt(i) > str.charAt(j) && dp[j] + 1 > dp[i]) {
                    dp[i] = dp[j] + 1;
                    prev[i] = j;
                }
            }

            if (dp[i] > maxLength) {
                maxLength = dp[i];
                endIndex = i;
            }
        }

        StringBuilder sb = new StringBuilder();
        int currentIndex = endIndex;
        while (currentIndex != -1) {
            sb.insert(0, str.charAt(currentIndex));
            currentIndex = prev[currentIndex];
        }

        String timeComplexity = "O(n^2)";
        System.out.println("Time complexity: " + timeComplexity);

        return sb.toString();
    }

        public static int isSubString(String str1, String str2) {
        int n = str1.length();
        int m = str2.length();

        for (int i = 0; i <= n - m; i++) {
            int j;

            for (j = 0; j < m; j++) {
                if (str1.charAt(i + j) != str2.charAt(j)) {
                    break;
                }
            }

            if (j == m) {
                return i;
            }
        }

        return -1;
    }

    public static void findRepeats(int[] arr, int n) {
        Map<Integer, Integer> countMap = new HashMap<>();

        for (int num : arr) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() > n) {
                System.out.println("Element: " + entry.getKey() + ", Count: " + entry.getValue());
            }
        }
    }

}




