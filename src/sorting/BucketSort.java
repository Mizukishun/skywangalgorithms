package sorting;

/**
 * 桶排序（可能不是所谓的桶排序。。。）
 * 
 * 桶排序的原理是：假设待排序的数组a中共有N个整数，并且已知数组a中的数据
 * 范围为(0, MAX)，在桶排序时，创建容量为MAX的桶数组r，并将桶数组元素都
 * 初始化为0，将容量为MAX的桶数组中的每一个单元都看作一个“桶”；在排序
 * 时，逐个遍历待排序数组a，将数组a的值作为“桶数组r"的下标，当a中数据被
 * 读取时，就将桶的值加1；例如读取到数组a[3]=5，则将r[5]的值+1；
 * 
 * 
 * @author Stargazer
 * @date 2017-03-18
 */

public class BucketSort {

	/**
	 * 桶排序（存疑。。。
	 * 
	 * @param a  	待排序的数组
	 * @param max 		数组a中最大值的范围
	 */
	public static void bucketSort(int[] a, int max){
		int[] buckets;
		
		if(a == null || max < 1)
			return;
		
		//创建一个容量为max的数组buckets,并将buckets中的所有数据都初始化为0
		buckets = new int[max];
		
		//1、计数
		for(int i = 0; i < a.length; i++)
			buckets[a[i]]++;
		
		//2、排序
		for(int i = 0, j = 0; i < max; i++){
			while((buckets[i]--)>0){
				a[j++] = i;
			}
		}
		
		buckets = null;
	}
	
	public static void main(String[] args){
		int i;
		int a[] = {5, 3, 7, 9, 1, 4, 2, 2, 6, 3, 5, 8, 3};
		
		System.out.println("before sort:");
		for(i = 0; i < a.length; i++)
			System.out.print(a[i] + "\t");
		System.out.println("");
		
		bucketSort(a, 10);
		
		System.out.println("after sort");
		for(i = 0; i < a.length; i++)
			System.out.print(a[i] + "\t");
	}
}
