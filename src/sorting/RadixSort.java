package sorting;

/**
 * 基数排序
 * 
 * 基数排序时桶排序的扩展，它的基本思想是：将整数按照位数切割成不同的数字，
 * 然后按每个位数分别比较；
 * 具体做法是：将所有待比较数值统一为同样的数位长度，数位较短的数就在前面补零；
 * 然后，从最低位开始，依次进行依次排序，这样从最低位排序一直到最高位，排序
 * 完成之后，则个数列就是有序序列了；
 * 
 * 
 * 
 * @author Stargazer
 * @date 2017-03-18
 */

public class RadixSort {

	/**
	 * 获取数组a中的最大值
	 * 
	 * @param a  数组
	 */
	private static int getMax(int[] a){
		int max;
		
		max = a[0];
		for(int i = 1; i < a.length; i++){
			if(a[i] > max)
				max = a[i];
		}
		
		return max;
	}
	/**
	 * 对数组按照“某个位数”进行排序（桶排序）
	 * 
	 * @param a		待排序数组
	 * @param exp		指数，对数组a按照该指数进行排序
	 * 
	 * 例如，对于数组a={53, 4, 9, 27, 156, 231, 95, 786, 10}
	 * 			(1)当exp=1时表示按照"个位"对数组a进行排序
	 * 			(2)当exp=10时表示按照"十位"对数组a进行排序
	 * 			(3)当exp=100时表示按照"百位"对数组a进行排序
	 * 			...
	 */
	private static void countSort(int[] a, int exp){
		int[] output = new int[a.length];		//存储被排序数据的临时数组
		int[] buckets = new int[10];				//对于单独的某一个“位”来说，其上的数字都肯定是小于10的
		
		//将数据出现的次数存储为buckets[]中的元素值,也即buckets[i]的值
		//表示数组a中值i出现的次数
		for(int i = 0; i < a.length; i++)
			buckets[(a[i]/exp)%10]++;
		
		//更改buckets[i]。目的是让更改后的buckets[i]的值，是该数据在output[]中的位置
		//就是让值i前面的元素出现次数相加，便可得到元素i在有序数组output中的位置
		for(int i = 1; i < 10; i++)
			buckets[i] += buckets[i-1];	
		
		//经过上面的for循环，则buckets[i]的值就表示a[i]的值在有序数组中的位置
		//将数据按序保存到output数组中
		for(int i = a.length-1; i >= 0; i--){
			output[buckets[(a[i]/exp)%10]-1] = a[i];	//将a[i]的值放到该值应有的顺序位置上
			buckets[(a[i]/exp)%10]--;
		}
		
		//将排序好的数据赋值给a[]
		for(int i = 0; i < a.length; i++)
			a[i] = output[i];
		
		output = null;
		buckets = null;		
	}
	
	/**
	 * 基数排序
	 * 
	 * @param a 待排序数组
	 */
	public static void radixSort(int[] a){
		int exp;			//指数，也即当对数组的按"个位"进行排序时，exp=1；按"十位"排序时，exp=10；按"百位"排序时，exp=100;...
		int max = getMax(a);		//数组a中的最大值，以方便后面求出需要总共对多少"位"分别进行排序
		
		//从"个位"开始，对数组a按指数exp进行排序
		for(exp = 1; max/exp > 0; exp *= 10)
			countSort(a, exp);
	}
	
	public static void main(String[] args){
		int i;
		int[] a = {53, 4, 9, 27, 156, 231, 96, 768, 10};
		
		System.out.println("before sort:");
		for(i = 0; i < a.length; i++)
			System.out.print(a[i] + "\t");
		System.out.println("");
		
		radixSort(a);
		
		System.out.println("after sort:");
		for(i = 0; i < a.length; i++)
			System.out.print(a[i] + "\t");
	}
}
