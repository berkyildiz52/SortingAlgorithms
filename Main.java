package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    static int operationInsertion = 0;
    static int operationBinary = 0;
    static int operationMerge = 0;
    static int countQuickSelect1 =0;
    static int countQuickSelect2=0;
    static int countHeap=0;
    static int operationCounting = 0;
    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<Integer>array=new ArrayList<>();
        String fileName="250Random";
        readArray(fileName,array);

        long startTime = System.nanoTime();
        insertionSort(array);
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        System.out.println("Insertion sort execution time is: " + timeElapsed);
        System.out.println("Insertion sort number of operations are : " + operationInsertion);
        printArray(array,array.size());
        array.clear();

        readArray(fileName,array);
        startTime = System.nanoTime();
        binaryInsertionSort(array);
        endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println("Binary insertion sort execution time is: " + timeElapsed);
        System.out.println("Binary insertion sort number of operations are: " + operationBinary);
        printArray(array,array.size());
        array.clear();

        readArray(fileName,array);
        startTime = System.nanoTime();
        array=mergeSort(array);
        endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println("Merge sort execution time is: " + timeElapsed);
        System.out.println("Merge sort number of operations are: " + operationMerge);
        printArray(array,array.size());
        array.clear();

        readArray(fileName,array);
        startTime = System.nanoTime();
        quickSort1(array,0,array.size()-1);
        endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println("Quick-sort (pivot is always selected as the first element) execution time is: " + timeElapsed);
        System.out.println("Quick-sort (pivot is always selected as the first element) number of operations are: " + countQuickSelect1);
        printArray(array,array.size());
        array.clear();

        readArray(fileName,array);
        startTime = System.nanoTime();
        quickSort2(array,0,array.size()-1);
        endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println("Quick-sort with median-of-three pivot selection execution time is: " + timeElapsed);
        System.out.println("Quick-sort with median-of-three pivot selection number of operations are: " + countQuickSelect2);
        printArray(array,array.size());
        array.clear();

        readArray(fileName,array);
        startTime = System.nanoTime();
        heapSort(array);
        endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println("Heap-sort execution time is: " + timeElapsed);
        System.out.println("Heap-sort number of operations are: " + countHeap);
        printArray(array,array.size());
        array.clear();

        readArray(fileName,array);
        startTime = System.nanoTime();
        countingSort(array);
        endTime = System.nanoTime();
        timeElapsed = endTime - startTime;
        System.out.println("Counting sort execution time is: " + timeElapsed);
        System.out.println("Counting sort number of operations are: " + operationCounting);
        printArray(array,array.size());
        array.clear();

    }




    static void readArray(String fileName,ArrayList<Integer>arr) throws FileNotFoundException
    {
        File file = new File(fileName+".txt");
        Scanner scan = new Scanner(file);
        while (scan.hasNextLine()) {
            String data = scan.nextLine();
            arr.add(Integer.parseInt(data));
        }
        scan.close();
    }
    static void printArray(ArrayList<Integer> arr, int size)
    {
        for(int i = 0; i < size; i++)
            System.out.print(arr.get(i) + " ");

        System.out.println();
    }

    static void insertionSort(ArrayList<Integer> arr) {

        int n = arr.size();
        int temp;

        for (int i = 1; i <= n - 1; i++) {
            temp = arr.get(i);
            int j = i - 1;
            operationInsertion++;

            //Move elements that are greater than key, to one position ahead of their current position
            while (j >= 0 && arr.get(j) > temp) {
                arr.set(j+1,arr.get(j));
                j = j - 1;
                operationInsertion++;
            }
            arr.set(j+1,temp);
        }
        operationInsertion++;
    }

    static int binarySearch(ArrayList<Integer> arr, int item, int low, int high) {
        //Return index of element
        if (high <= low) {
            if (item > arr.get(low)) {
                operationBinary++;
                return low + 1;
            } else {
                operationBinary++;
                return low;
            }
        }
        int mid = (low + high) / 2;
        //If the element is at the middle
        if (item == arr.get(mid)) {
            operationBinary++;
            return mid + 1;
        }
        //If the element is bigger than mid
        if (item > arr.get(mid)) {
            operationBinary++;
            return binarySearch(arr, item, mid + 1, high);
        }
        //If the element is not in the array
        return binarySearch(arr, item, low, mid - 1);
    }

    static void binaryInsertionSort(ArrayList<Integer> arr) {
        int i, location = 0, j, temp, n = arr.size();
        for (i = 1; i < n; ++i) {
            j = i - 1;
            temp = arr.get(i);
            location = binarySearch(arr, temp, 0, j);
            //Move elements that are greater than key, to one position ahead of their current position
            while (j >= location) {
                arr.set(j+1,arr.get(j));
                --j;

            }
            arr.set(j+1,temp) ;

        }
    }

    static ArrayList<Integer> mergeSort(ArrayList<Integer> arr) {

        int n = arr.size();

        if (n > 1) {

            // Merge sort the first half
            ArrayList<Integer> first=new ArrayList<>();
            for(int j=0;j<arr.size() / 2;j++){
                first.add(arr.get(j));
            }
            mergeSort(first);

            // Merge sort the second half
            int lastLength = arr.size() - arr.size() / 2;
            ArrayList<Integer>last=new ArrayList<>();
            for(int j=arr.size()/2;j<lastLength;j++){
                last.add(arr.get(j));
            }
            mergeSort(last);

            // Merge firstHalf with secondHalf
            ArrayList<Integer>temp=new ArrayList<>();
            temp=merge(first, last);
            //int[] temp = merge(first, last);
            //System.arraycopy(temp, 0, arr, 0, temp.length);
            return temp;
        } else
            return arr;
    }

    static ArrayList<Integer> merge(ArrayList<Integer> b, ArrayList<Integer> c) {

        //index in first array
        int i = 0;
        //index in second array
        int j = 0;
        //index in third array
        int k = 0;
        ArrayList<Integer>temp=new ArrayList<>();
        int p = b.size();
        int q = c.size();

        while (i < p && j < q) {
            if (b.get(i) < c.get(j)) {
                temp.add(k,b.get(i));
                i++;
            } else {
                temp.add(k , c.get(j));
                j++;
            }
            k++;
            operationMerge++;
        }
        // Copy remaining elements
        while (i < p) {
            temp.add(k, b.get(i));
            k++;
            i++;
            operationMerge++;

        }
        // Copy remaining elements
        while (j < q) {
            temp.add(k,c.get(j));
            k++;
            j++;
            operationMerge++;

        }
        return temp;
    }
    static void quickSort1(ArrayList<Integer>arr, int low, int high)
    {
        if (low < high)
        {
            int pivot = partition_pivot_first(arr, low, high);

            // sort elements before pivot and after pivot
            quickSort1(arr, low, pivot - 1);
            quickSort1(arr, pivot + 1, high);
        }
    }

    static int partition_pivot_first(ArrayList<Integer> arr, int low, int high)
    {

        // choose the pivot as the first element in the array
        int pivot = arr.get(low);
        //put the pivot at the end of the array
        Collections.swap(arr,low,high);

        int store = low ;

        for(int j = low; j <= high - 1; j++)
        {   //if the array[j] is less then the pivot we swap it with the store
            if (arr.get(j) < pivot)
            {
                Collections.swap(arr,store,j);
                //increment the store index
                store++;
            }
            countQuickSelect1++;
        }
        //replace the pivot with the store
        Collections.swap(arr,store,high);
        return (store);
    }


    static void quickSort2(ArrayList<Integer>arr, int low, int high)
    {
        if (low < high)
        {
            int pi = partition_pivot_median(arr, low, high);
            // sort elements before pi and after pi
            quickSort1(arr, low, pi - 1);
            quickSort1(arr, pi + 1, high);
        }
    }
    static int partition_pivot_median(ArrayList<Integer> arr, int low, int high)
    {
        int pivot=getMedian (arr,low,high);
        int store=low;
        for (int i=low;i<high;i++){
            if (arr.get(i)<pivot){
                Collections.swap(arr,store,i);
                store++;
            }
            countQuickSelect2++;
        }
        Collections.swap(arr,store,high);
        return (store);
    }

    public static int getMedian(ArrayList<Integer>a,int low,int high){
        int center = (low+high)/2;

        if(a.get(low) > a.get(center))
            Collections.swap(a,low,center);


        if(a.get(low) > a.get(high))
            Collections.swap(a,low,high);

        if(a.get(center) > a.get(high))
            Collections.swap(a,center,high);

        Collections.swap(a,center,high);
        return a.get(high);
    }

    static void heapSort(ArrayList<Integer> arr)
    {
        int n=arr.size();
        for (int i =n/2-1;i>=0;i--)
        {
            maxHeap(arr,n,i);
        }
        //put the larger elements at the end of the array
        for (int i=n-1;i>=0;i--){
            Collections.swap(arr,0,i);
            maxHeap(arr,i,0);
        }
    }
    static void maxHeap(ArrayList<Integer> arr,int length,int i)
    {
        int largest=i;
        int left=2*i+1;
        int right=2*i+2;
        //checking if the child is larger than the root
        if (left<length && arr.get(left)>arr.get(largest))
        {
         largest=left;
         countHeap++;
        }
        if (right<length && arr.get(right)>arr.get(largest))
        {
            largest=right;
            countHeap++;
        }
        //swap the root with the largest child
        if (largest!=i){
            Collections.swap(arr,i,largest);
            maxHeap(arr,length,largest);
            countHeap++;
        }
    }
    static void countingSort(ArrayList<Integer> arr) {

        int max = findMax(arr);
        int[] count = new int[max + 1];

        // Counting
        for (int i = 0; i < arr.size(); i++) {
            count[arr.get(i)]++;

        }

        int position = 0;
        // Writing results
        for (int i = 0; i < count.length; i++) {

            for (int j = 0; j < count[i]; j++) {
                arr.set(position++ , i);

            }
        }
    }
    //Finding max
    static int findMax(ArrayList<Integer> arr) {
        int max = 0;
        for (int i = 0; i < arr.size(); i++) {
            int element = arr.get(i);

            if (element > max) {
                max = element;
                operationCounting++;
            }

        }
        return max;
    }
}