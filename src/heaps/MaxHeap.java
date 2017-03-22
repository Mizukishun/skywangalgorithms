package heaps;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉堆（最大堆）
 * 
 * 堆（heap）是一种数据结构，具有以下性质：
 * 1、堆中任意节点的值总是不大于（不小于）其子节点的值；
 * 2、堆总是一个完全树；
 * 
 * 将任意节点不大于其子节点的堆叫做小根堆或最小堆；
 * 将任意节点不小于其子节点的堆叫做大根堆或最大堆；
 * 常见的堆有二叉堆、左倾堆、斜堆、二项堆、斐波那契堆等等；
 * 
 * 二叉堆根据数据的排列方式可以分为两种：最大堆和最小堆；
 * 
 * 最大堆：父节点的键值总是大于或等于任何一个子节点的键值；
 * 最小堆：父节点的键值总是小于或等于任何一个子节点的键值；
 * 
 * 二叉堆一般通过数组来实现；
 * 根据二叉堆的第一个元素放在数组中索引的位置有不同的情况：
 * 最大堆（第一个元素的索引为0）：
 * 1、索引为i的左孩子的索引是(2*i+1)；
 * 2、索引为i的右孩子的索引是(2*i+2)；
 * 3、索引为i的父节点的索引是floor((i-1)/2)；
 * 
 * 最大堆（第一个元素的索引为1）：
 * 1、索引为i的左孩子的索引是(2*i)；
 * 2、索引为i的右孩子的索引是(2*i+1)；
 * 3、索引为i的父节点的索引是floor(i/2)；
 * 
 * 这里采用的是二叉堆第一个元素在数组索引为0的方式
 * 
 * 
 * @author Stargazer
 * @date 2017-03-22
 */

public class MaxHeap<T extends Comparable<T>> {
	
	private List<T> mHeap;			//队列（实际上是动态数组ArrayList实例）
	
	public MaxHeap(){
		this.mHeap = new ArrayList<T>();
	}
	
	/**
	 * 最大堆的向下调整算法
	 * 
	 * 注：数组实现中，第N个节点的左孩子的索引值为2*N+1，右孩子的索引值为2*N+2
	 * 
	 * @param start  		被下调节点的起始位置（一般为0，表示从第1个开始）
	 * @param end 		截至范围（一般在数组中最后一个元素的索引）
	 */
	protected void filterdown(int start, int end){
		int current = start;					//当前节点的位置
		int left = 2*current + 1;			//左孩子的位置
		T tmp = mHeap.get(current);	//当前节点的键值
		
		while(left <= end){
			int cmp = mHeap.get(left).compareTo(mHeap.get(left+1));		//左孩子与右孩子的键值比较
			
			//left是左孩子，left+1是右孩子
			if(left < end && cmp < 0)
				left++;								//左右孩子中选择较大者，及mHeap[left+1]
			
			cmp = tmp.compareTo(mHeap.get(left));	//当前节点与孩子中较大的节点的键值比较
			if(cmp >= 0)
				break;													//调整结束
			else{
				mHeap.set(current, mHeap.get(left));				//将较大的孩子向上调整到当前节点的原位置
				current = left;												//将原先的父节点向下设为较大的那个孩子的位置上，作为当前节点继续调整
				left = 2*left + 1;
			}
		}
		
		mHeap.set(current, tmp);						//将向下调整的那个节点的值放到其正确的位置上，也即最后的当前节点的位置
	}
	
	/**
	 * 删除最大堆中键值为data的节点
	 * 
	 * @return 0 成功
	 * 				 -1 失败
	 */
	public int remove(T data){
		//如果堆已空，则返回-1
		if(mHeap.isEmpty() == true)
			return -1;
		
		//获取键值为data的节点在数组中的索引
		int index = mHeap.indexOf(data);
		
		if(index == -1)
			return -1;
		
		int size = mHeap.size();
		mHeap.set(index, mHeap.get(size-1));		//用最后的元素填补被删除的节点位置
		mHeap.remove(size-1);								//最后的元素已经替换到被删的位置上了，所以最后位置上的原元素可以删除了
		
		if(mHeap.size() > 1)
			filterdown(index, mHeap.size()-1);			//从index位置开始自上向下调整回最大堆
		
		return 0;
	}
	
	/**
	 * 最大堆的向上调整算法(从start开始向上直到0，调整堆）
	 * 
	 * 注：数组实现中，第N个节点的左孩子的索引值是2N+1，右孩子的索引值为2N+2
	 * 
	 * @param start  	被向上调节的节点的起始位置（一般为数组中最后一个元素的索引）
	 */
	protected void filterup(int start){
		int current = start;						//被向上调节的节点的当前位置
		int parent = (current-1)/2;			//被向上调节的节点的父节点的位置
		T tmp = mHeap.get(current);		//被向上调节的节点的键值，当最后找到了其位置时再把该键值放到该位置上
		
		while(current > 0){
			int cmp = mHeap.get(parent).compareTo(tmp);
			if(cmp >= 0)
				break;									//被调节节点的键值小于父节点的键值，向上调整结束
			else{
				mHeap.set(current, mHeap.get(parent));	//被调节节点的键值大于父节点的键值，交换它们的位置，也即被调节节点向上走了
				current = parent;
				parent = (parent-1)/2;
			}
		}
		
		mHeap.set(current, tmp);				//最后将被调节节点的键值放到被调节的位置上
	}
	
	/**
	 * 将键值data插入最大堆中
	 */
	public void insert(T data){
		int size = mHeap.size();
		
		mHeap.add(data);				//将新的键值插入数组的末尾
		filterup(size);						//从被插入键值的位置开始向上调整
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < mHeap.size(); i++)
			sb.append(mHeap.get(i) + " ");
		
		return sb.toString();
	}

}
