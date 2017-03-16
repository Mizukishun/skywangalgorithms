package sorting;

/**
 * 冒泡排序
 * 
 * 冒泡排序，它会遍历若干次要排序的数列，每次遍历时，它都会从前往后依次
 * 的比较相邻两个数的大小；如果前者比后者大，则交换它们的位置；这样，在
 * 一次便利了之后，最大的元素就在数列的末尾！采用相同的方法再次遍历时，
 * 则第二大的元素就被排列再最大元素之前；重复这样的遍历操作，直到整个数列
 * 都有序为止！
 * 
 * 冒泡排序的的时间复杂度为O(N^2)
 * 冒泡排序是稳定的算法
 * 
 * 算法稳定性：假设在数列中存在a[i]=a[j]，若在排序之前，a[i]在a[j]的前面；并且
 * 在排序之后，a[i]仍在a[j]的前面，则这个排序算法是稳定的。
 * 
 * @author Stargazer
 * @date 2017-03-16
 */
public class BubbleSort {

	/**
	 * 冒泡排序
	 * @param a 待排序的数组
	 * @param n 数组的长度
	 */
	public static void bubbleSort1(int[] a, int n){
		int i, j;
		
		for(i = n-1; i > 0; i--){
			//将a[0...i]中的最大数据放在末尾
			//每完成一次内循环，就把【0~i】中的最大一个数放到了最后面；
			for(j = 0; j < i; j++){
				
				if(a[j] > a[j+1]){
					//交换a[j]和a[j+1]
					int tmp = a[j];
					a[j] = a[j+1];
					a[j+1] = tmp;
				}
			}
		}
	}
	
	
	/**
	 * 冒泡排序（改进版）
	 * @param a
	 * @param n
	 */
	public static void bubbleSort2(int[] a, int n){
		int i, j;
		int flag;		//标记
		
		for(i = n-1; i > 0; i--){
			//初始化标记为0
			flag = 0;		
			//将a[0...i]中的最大的数据放在末尾
			for(j = 0; j < i; j++){
				if(a[j] > a[j+1]){
					//交换a[j]和a[j+1]
					int tmp = a[j];
					a[j] = a[j+1];
					a[j+1] = a[j];
					
					//若发生交换，则标记记为1
					flag = 1;			
				}
			}
			
			/*
			 * 如果一次完整的内循环都没有发生交换，则说明整个数列已经有序了，
			 * 可以不用再去循环遍历比较a[0...i]之间的数据了
			 */
			if(flag == 0){
				break;					//若没发生交换，则说明整个数列已经有序了
			}
		}
	}
	
	//冒泡排序测试
	public static void main(String[] args){
		int i;
		int[] a = {5, 3, 7, 9, 1, 4, 2, 6};
		
		System.out.println("before sort: ");
		for(i = 0; i < a.length; i++){
			System.out.print(a[i] + "\t");
		}
		System.out.println("");
		
		bubbleSort1(a, a.length);
		//bubbleSort2(a, a.length);
		
		System.out.println("after sort: ");
		for(i = 0; i < a.length; i++){
			System.out.print(a[i] + "\t");
		}
		System.out.println("");
	}
}
