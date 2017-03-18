package sorting;

/**
 * 归并排序
 * 
 * 将两个有序数列合并成一个有序数列，我们称之为“归并”；
 * 
 * 归并排序就是利用归并思想对数列进行排序，根据具体实现，归并排序包括两种
 * 方式：“从下往上”和“从上往下”；
 * 
 * 从下往上的归并排序：将待排序的数列分成若干个长度为1的子数列，然后将这些
 * 数列两两合并；得到若干个长度为2的有序数列，再将这些数列两两合并，得到
 * 若干个长度为4的有序数列，再将它们两两合并，以此类推，直到合并成为一个
 * 数列为止，这样就得到了我们想要的有序的排序结果；
 * 
 * 从上往下的归并排序：它与“从下往上”在排序上是反方向的，它基本包括3步：
 * 1、分解——将当前区间一分为二，即求分裂点mid=(low+high)/2；
 * 2、求解——递归地对两个子区间a[low,mid]和a[mid+1, high]进行归并排序，递归的终结条件时子区间长度为1；
 * 3、合并——将已排序的两个子区间a[low,mid]和a[mid+1,high]归并为一个有序的区间；
 * 
 * 归并排序的时间复杂度：O(N*lgN)
 * 
 * 对上面的解释：假设被排序的数列中有N个数，遍历一趟的时间复杂度是O(N)，
 * 那需要遍历多少次呢？归并排序的形式就是一棵二叉树，它需要遍历的次数就是
 * 二叉树的深度，而根据完全二叉树的性质可以得出它的时间复杂度是O(N*lgN);
 * 
 * 归并排序是稳定的算法
 * 
 * 算法稳定性：假设在数列中存在 a[i]=a[j]，若在排序之前，a[i]在a[j]的前面，
 * 并且在排序之后，a[i]仍在a[j]的前面，则称该算法是稳定的；
 * 
 * 
 * @author Stargazer
 * @date 2017-03-18
 */

public class MergeSort {

	/**
	 * 将一个数组中的两个相邻有序区合并成一个
	 * 
	 * @param a			包含两个有序区的数组
	 * @param start 	第1个有序区的起始地址
	 * @param mid 	第1个有序区的结束地址，mid+1是第2个有序区的起始地址
	 * @param end 	第2个有序区的结束地址
	 */
	public static void merge(int[] a, int start, int mid, int end){
		int[] temp = new int[end-start+1];		//temp是汇总2个有序区的临时区域
		int i = start;					//第1个有序区的索引
		int j = mid+1;				//第2个有序区的索引
		int k = 0;						//临时区域的索引
		
		/**
		 * 分别将两个有序区中元素逐个按大小顺序添加到临时区域中，
		 * 这样添加到在临时区域中的元素就是有序的了
		 */
		while(i <= mid && j <= end){
			if(a[i] <= a[j])
				temp[k++] = a[i++];
			else
				temp[k++] = a[j++];
		}
		
		//通过上面的循环比较，则两个有序区中有一个有序区的元素都被放进临时区域了
		//i<mid则说明a[start,mid]有序区还有元素没放进临时区域，因为是有序，所以可以直接放进去
		while(i <= mid)
			temp[k++] = a[i++];
		
		//j<=end则说明a[mid+1,end]有序区还有元素多出来，有序的，所以可以直接放进临时区域
		while(j <= end)
			temp[k++] = a[j++];
		
		//将排序后的元素全部都整合到原数组a[]中
		for(int m = 0; m < k; m++)
			a[start+m] = temp[m];
		
		//销毁临时数组变量
		temp = null;
	}
	
	/**
	 * 归并排序（从上往下）
	 * 
	 * @param a  		待排序的数组
	 * @param start	数组的起始地址
	 * @param end	 	数组的结束地址
	 */
	public static void mergeSortUp2Down(int[] a, int start, int end){
		//下面这个是递归的终止条件
		if(a == null || start >= end)
			return;
		
		int mid = (end + start) /2;
		
		mergeSortUp2Down(a, start, mid);		//递归排序a[start, mid]区间
		mergeSortUp2Down(a, mid+1, end);		//递归排序a[mid+1, end]区间
		
		//递归到最后只剩单个元素，把它们看成是有序的，在逐步将两个相邻的有序区间合并成一个有序区间
		//也即a[start,mid]和a[mid+1, end]是两个有序区间，再将它们合并成一个有序区间a[start, end]
		merge(a, start, mid, end);
	}
	
	/**
	 * 对数组a做若干次合并：
	 * 数组a的总长度为len，将它分为若干个长度为gap的子数组，将“每两个相邻
	 * 的子数组“进行合并；
	 * 
	 * @param a 		待排序的数组
	 * @param len		数组的长度
	 * @param gap		子数组的长度
	 */
	public static void mergeGroups(int[]  a, int len, int gap){
		int i;
		int twolen = 2 * gap;		//两个相邻子数组合并后的长度
		
		//每次都是将“相邻的两个子数组”进行合并排序
		for(i = 0; i+2*gap-1 < len; i += twolen)
			merge(a, i, i+gap-1, i+2*gap-1);
		
		//若i+gap-1 < len -1 ，则说明剩余一个子数组没有 配对
		//将该子数组合并到已排序的数组中
		if(i+gap-1 < len-1)
			merge(a, i, i+gap-1, len-1);
	}
	
	/**
	 * 归并排序（从下往上）
	 * 
	 * @param a		待排序的数组
	 */
	public static void mergeSortDown2Up(int[] a){
		if(a == null)
			return;
		
		/*
		 * 首先将整个数组中的所有元素都看成只有一个元素的有序数组，然后就是将
		 * 相邻的两个有序数组合并，每次合并之后，相邻有序数组的长度就都变为原来
		 * 的2倍；
		 */
		for(int n = 1; n < a.length; n *= 2)
				mergeGroups(a, a.length, n);
	}
	
	public static void main(String[] args){
		int i;
		int a[] = {5, 3, 7, 9, 1, 4, 2, 6, 8};
		
		System.out.println("before sort:");
		for(i = 0; i < a.length; i++)
			System.out.print(a[i] + "\t");
		System.out.println("");
		
		mergeSortUp2Down(a, 0, a.length-1);
		mergeSortDown2Up(a);
		

		System.out.println("after sort:");
		for(i = 0; i < a.length; i++)
			System.out.print(a[i] + "\t");
	}	
}
