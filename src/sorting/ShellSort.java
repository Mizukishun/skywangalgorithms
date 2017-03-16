package sorting;

/**
 * 希尔排序/缩小增量排序
 * 
 * 希尔排序是插入排序的一种，是针对直接插入排序算法的改进；
 * 希尔排序是一种 分组插入方法，它的基本思想是：对于n个待排序的数列，取一
 * 个小于n的整数gap(步长)，将待排序的元素分成若干个组子序列，所有距离为
 * gap的倍数的记录放在同一个组中；然后，对各组内的元素进行直接插入排序；
 * 这一趟排序完成之后，每一个组中的元素都是有序的；然后减小gap的值，并重
 * 复上面的操作，当gap=1时，整个数列就是有序的了。
 * 
 * 希尔排序的时间复杂度：
 * 其时间复杂度与增量（即步长gap）的选取有关；例如，当增量为1时，希尔排序
 * 退化成了直接插入排序，此时的时间复杂度为O(N^2)；而Hibbard增量的希尔
 * 排序的时间复杂度为O(N^(3/2))；
 * 
 * 希尔排序时不稳定的算法
 * 
 * 算法稳定性：假设在数列中存在a[i]和a[j]，若在排序之前，a[i]在a[j]的前面，并且
 * 在排序之后，a[i]仍在a[j]的前面，则称此算法是稳定的。
 * 
 * @author Stargazer
 * @date 2017-03-16
 */
public class ShellSort {

	/**
	 * 希尔排序
	 * 
	 * @param a 	待排序的数组
	 * @param n 	数组的长度
	 */
	public static void shellSort(int[] a, int n){
		
		//gap为步长，每次减为原来的一半
		for(int gap = n/2; gap > 0; gap /= 2){
			
			//会把整个数列分为gap个组，对每组都执行直接插入排序
			for(int i = 0; i < gap; i++){
				
				/**
				 * 注意此时单独一个组里的各个元素的位置不是连续的，而是间隔gap,
				 * 即：i, i+gap, i+2gap, i+3gap, i+4gap, i+5gap, ... 
				 * 如果下面的for循环较难理解，则可以把gap全部看成1来理解
				 * 【i,...,j-gap】是有序区，【j,..,n】是无序区，每次都把无序区的第1个元素a[j]
				 * 插入到有序区中
				 */
				for(int j = i+gap; j < n; j += gap){
					//如果a[j] < a[j-gap]，则寻找a[j]的位置，并将后面数据的位置都后移一位
					if(a[j] < a[j-gap]){
						int temp = a[j];
						int k = j - gap;
						//在有序区中找到temp>a[k]的位置，并把有序区中a[k]之后的所有元素都后移一位，以便让出可以放temp的位置
						while(k >= 0 && a[k] > temp){
							a[k+gap] = a[k];
							k -= gap;
						}
						//while循环出来后，a[k]就刚好小于temp,并已经把原先a[k]之后的元素都
						//后移了一位，因此就把temp插入到a[k]之后的一位位置上
						a[k+gap] = temp;
					}
				}
		
			}
		}
	}
	
	/**
	 * 对希尔排序中的单个组进行排序
	 *
	 * 注意：组是“从i开始，将相隔gap长度的元素取出“所组成的！
	 * 
	 * @param a 	待排序的数组
	 * @param n	数组的总长度
	 * @param i 		组的起始位置
	 * @param gap 组的步长
	 */
	public static void groupSort(int[] a, int n, int i, int gap){
		
		for(int j = i + gap; j < n; j += gap){
			//如果a[j] < a[j-gap],则在【i,...,j-gap】这个有序区中寻找a[j]的位置，并就后面数据的位置都后移一位
			if(a[j] < a[j-gap]){
				int temp = a[j];
				int k = j-gap;
				while(k >= 0 && a[k] > temp){
					a[k+gap] = a[k];
					k -= gap;
				}
				a[k+gap] = temp;
			}
		}
	}
	
	/**
	 * 希尔排序
	 * 
	 * @param a		待排序的数组
	 * @param n	数组的长度
	 */
	public static void shellSort2(int[] a, int n){
		//gap为步长，每次减为原来的一半
		for(int gap = n/2; gap > 0; gap /= 2){
			//将整个数列分为个gap个组，对每一组都执行直接插入排序
			for(int i = 0; i < gap; i++)
				groupSort(a, n, i, gap);
		}
	}
	
	public static void main(String[] args){
		int i; 
		int[] a = {5, 3, 7, 9, 1, 4, 2, 6, 8};
		
		System.out.println("before sort:");
		for(i = 0; i < a.length; i++)
			System.out.print(a[i] + "\t");
		System.out.println("");
		
		shellSort(a, a.length);
		//shellSort2(a, a.length);
		
		System.out.println("after sort:");
		for(i = 0; i < a.length; i++)
			System.out.print(a[i] + "\t");
	}
	
}
