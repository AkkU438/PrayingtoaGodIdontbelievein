import java.util.*;
import java.io.*;

public class Generics{
    public static void main(String[] args) {
        System.out.println("1: create a random array store it into a file, 2: take array from a file and sort");
        System.out.println("3: for a merge sort from an array from a file");
        @SuppressWarnings("resource")
        Scanner number = new Scanner(System.in);
        int lmao = number.nextInt();
        switch (lmao) {
            case 1:
                System.out.println("Enter the length of the array");

                Scanner myObj = new Scanner(System.in);
                int arrayLength = myObj.nextInt();

                createRandomArray(arrayLength);

                break;
            case 2:
                System.out.println("Please write out the name of the file");
                Scanner myName1 = new Scanner(System.in);
                String name1 = myName1.nextLine();

                Integer[] numbers = readFileToArray(name1);
                long startTime = System.nanoTime();
                bubbleSort(numbers);
                long endTime = System.nanoTime();
                long duration = (endTime - startTime);
                System.out.println("The sort took" + duration);
                System.out.println("Please write out the name of the file");
                Scanner myName2 = new Scanner(System.in);
                String name2 = myName2.nextLine();
                writeArrayToFile(numbers, name2);

                break;

            case 3:
                System.out.println("Please write out the name of the file");
                Scanner myName3 = new Scanner(System.in);
                String name3 = myName3.nextLine();

                Integer[] numbers2 = readFileToArray(name3);
                long startTime2 = System.nanoTime();
                mergeSort(numbers2, 0, numbers2.length - 1);
                long endTime2 = System.nanoTime();
                long duration2 = (endTime2 - startTime2);
                System.out.println("The sort took" + duration2);
                System.out.println("Please write out the name of the file");
                Scanner myName4 = new Scanner(System.in);
                String name4 = myName4.nextLine();
                writeArrayToFile(numbers2, name4);

                break;
            default:
                
                break;
        }
    }

    public static void createRandomArray(int arrayLength) {
        Random random = new Random();
        Integer[] randomArray = new Integer[arrayLength];

        for (int i = 0; i < arrayLength; i++) {
            randomArray[i] = random.nextInt(100);
            System.out.print(randomArray[i] + " ");
        }
        @SuppressWarnings("resource")
        Scanner myObj1 = new Scanner(System.in);
        System.out.println("");
        System.out.println("What is the name of the file");
        String nameString = myObj1.nextLine();
        writeArrayToFile(randomArray, nameString);
    }

    public static <T extends Comparable<T>> void writeArrayToFile(T[] array, String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (T num : array) {
                bufferedWriter.write(num.toString());
                bufferedWriter.newLine(); // Add a newline character after each integer
            }

            bufferedWriter.close();
            System.out.println("Array written to file: " + filename);
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static Integer[] readFileToArray(String filename) {
        List<Integer> numbers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    int number = Integer.parseInt(line);
                    numbers.add(number);
                    System.out.print(number + " ");
                    System.out.println("");
                } catch (NumberFormatException e) {
                    System.err.println("Invalid integer format in line: " + line); // Log or handle invalid lines
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename);
            return new Integer[0]; // Return an empty array on error
        }

        return numbers.toArray(new Integer[0]); // Convert list to array
    }

    public static <T extends Comparable<T>> void bubbleSort(T[] array) {
        int n = array.length;
        boolean swapped;
        do {
            swapped = false;
            for (int i = 0; i < n - 1; i++) {
                if (array[i].compareTo(array[i + 1]) > 0) {
                    // Swap elements
                    T temp = array[i];
                    array[i] = array[i + 1];
                    array[i + 1] = temp;
                    swapped = true;
                }
            }
    
            n--;
        } while (swapped);
    }

    public static <T extends Comparable<T>> void merge(T[] arr, int l, int m, int r) {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temporary arrays */
        T[] L = Arrays.copyOfRange(arr, l, m + 1);
        T[] R = Arrays.copyOfRange(arr, m + 1, r + 1);

        /* Merge the temporary arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarray array
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i].compareTo(R[j]) <= 0) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    // Main function that sorts arr[l..r] using merge()
    public static <T extends Comparable<T>> void mergeSort(T[] arr, int l, int r) {
        if (l < r) {
            // Find the middle point
            int m = (l + r) / 2;

            // Sort first and second halves
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);

            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }

    // Utility function to print array of size n
    public static <T extends Comparable<T>> void printArray(T[] arr) {
        for (T element : arr) {
            System.out.print(element + " ");
        }
        System.out.println();
    }
}
