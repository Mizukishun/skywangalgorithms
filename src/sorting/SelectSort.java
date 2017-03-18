package sorting;

/**
 * 选择排序
 * 
 * 基本思想是：首先在未排序的数列中找到最小（or最大）的元素，然后将其存放
 * 到数列的起始位置；接着 ，再从剩余未排序的元素中继续寻找最小（or最大）的
 * 元素，然后放到已排序序列的末尾；以此类推，直到所有元素均排序完毕。
 * 
 * 选择排序的时间复杂度：O(N^2)
 * 
 * 对上面的解释：假设待排序的数列中有N个数，遍历一趟的时间复杂度是O(N)，
 * 需要遍历多少次呢？N-1次！因此，选择排序的时间复杂度是O(N^2)
 * 
 * 选择排序是稳定的算法
 * 
 * 算法稳定性：假设在数列中存在a[i]=a[j]，若在排序之前，a[i]在a[j]的前面，并且
 * 在排序之后，a[i]仍在a[j]的前面，则说明这个排序算法是稳定的；
 * 
 * @author Stargazer
 * @date 2017-03-18
 */

public class SelectSort {

	/**
	 * 选择排序
	 * 
	 * @param a			待排序的数组
	 * @param n 		数组的长度
	 */
	public static void selectSort(int[] a, int n){
		int i;			//有序区的末尾位置
		int j;			//无序区的起始位置
		int min;	//无序区中的最小元素位置
		
		for(i = 0; i < n; i++){
			min = i;
			
			//找出a[i+1]~a[n]之间的最小元素，并赋值给min
			for(j = i + 1; j < n; j++){
				if(a[j] < a[min])
					min = j;
			}
			
			//若min != i，则交换a[i]和a[min]
			//交换之后，保证了a[0]~a[i]之间的元素是有序的
			if(min != i){
				int tmp = a[i];
				a[i] = a[min];
				a[min] = tmp;
			}
		}
	}
	
	public static void main(String[] args){
		int i;
		int[] a = {5, 3, 7, 9, 1, 4, 2, 6, 8};
		
		System.out.println("before sort: ");
		for(i = 0; i < a.length; i++)
			System.out.print(a[i] + "\t");
		System.out.println("");
		
		selectSort(a, a.length);
		
		System.out.println("after sort:");
		for(i = 0; i < a.length; i++)
			System.out.print(a[i] + "\t");
	}
}
