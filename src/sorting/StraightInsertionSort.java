package sorting;

/**
 * 直接插入排序
 * 
 * 直接插入排序的基本思想是：把n个待排序的元素看成为一个有序表和一个无序表，
 * 开始时，有序表中只包含1个元素，无序表中包含有n-1个元素，排序过程中每次从
 * 无序表中取出第一个元素，将它插入到有序表中的适当位置，使之成为新的有序表，
 * 重复n-1次这样的插入过程便可完成整个排序。
 * 
 * 步骤：
 * 1、取出无序区中的第1个数，并找出它在有序区对应的位置；
 * 2、将该数据插入到有序区；若有必要，还需要对有序区中的相关数据进行移位；
 * 
 * 直接插入排序的时间复杂度：O(N^2)
 * 
 * 对上面复杂度的解释：假设被排序的数列中有N个数，遍历一趟的时间复杂度是
 * O(N)，需要遍历多少次呢？----(N-1)次！
 * 
 * 直接插入排序时稳定的算法
 * 
 * 算法稳定性：假设在数列中存在a[i]=a[j]，若在排序之前，a[i]在a[j]的前面；并且
 * 在排序之后，a[i]仍在a[j]的前面，则称这个算法是稳定的。
 * 
 * @author Stargazer
 * @date 2017-03-16
 */

public class StraightInsertionSort {

	/**
	 * 直接插入排序
	 * 
	 * @param a 	待排序的数组
	 * @param n	数组的长度
	 */
	public static void insertSort(int[] a, int n){
		int i, j, k;
		
		for(i = 1; i < n; i++){
			//为无序区的第1个元素a[i]在前面的有序区a[0...i-1]中找一个合适的位置
			for(j = i-1; j >= 0; j--){
				if(a[j] < a[i])
					break;
			}
			
			//跳出上面的for循环后，则说明a[j]<a[i]<=a[j+1]
			//如果找到了一个合适的位置(j+1)，则将a[i]插入到有序区的该位置中
			if(j != i - 1){
				int temp = a[i];
				//将有序区中的(j, i-1]位置上的元素依次往后移动一位
				for(k = i-1; k > j; k--)
					a[k+1] = a[k];
				
				//把a[i]放到原先的a[j+1]位置上
				a[k+1] = temp;
			}
		}
	}
	
	public static void main(String[] args){
		int i;
		int[] a = {5, 3, 7, 9, 1, 4, 2, 6, 8};
		
		System.out.println("before sort");
		for(i = 0; i < a.length; i++)
			System.out.print(a[i] + "\t");
		System.out.println("");
		
		insertSort(a, a.length);
		
		System.out.println("after sort:");
		for(i = 0; i < a.length; i++)
			System.out.print(a[i] + "\t");
	}
}
