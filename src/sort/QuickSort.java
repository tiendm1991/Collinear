package sort;

import java.util.Arrays;

public class QuickSort {
	
	public static void main(String[] args) {
		int[] a = {2,3,8,6,87,456,87,33,56,32,1,23,5,8,5,};
		System.out.println("Before:");
		System.out.println(Arrays.toString(a));
		quicksort(a, 0, a.length-1);
		System.out.println("After:");
		System.out.println(Arrays.toString(a));
	}
	
	public static void quicksort(int[] a, int low, int high){
		if(low >= high) return;
		int j = partition(a, low, high);
		quicksort(a, low, Math.max(j-1, low));
		quicksort(a, j, high);
	}
	
	public static int partition(int a[], int low, int high){
		int i =low, j = high;
		int pivot = a[(low + high)/2];
		while (i <= j) {
			while (a[i] < pivot) {
				i++;
			}
			while (a[j] > pivot) {
				j--;
			}
			if(i <= j){
				swap(a, i, j);
				i++;
				j--;
			}
		}
		return i;
	}
	
	public static void swap(int[] a, int i, int j){
		int tmp = a[i];
		a[i] = a[j];
		a[j] = tmp;
	}
}
