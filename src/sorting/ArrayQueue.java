package sorting;

/**
 * 队列
 * 
 * 队列是一种线性结构，它有以下几个特点：
 * 1、队列中数据是按照“先进先出(FIFO, First In First Out)”方式进出队列的；
 * 2、队列只允许在“队首”进行删除操作，而在“队尾”进行插入操作；
 * 
 * 
 * @author Stargazer
 * @date 2017-03-19
 */

public class ArrayQueue {
	//数组实现的队列，但只能存储int类型数据
	
	private int[] mArray;
	private int mCount;
	
	public ArrayQueue(int sz){
		mArray = new int[sz];
		mCount = 0;
	}
	
	/**
	 * 将val添加到队列的末尾
	 * @param val
	 */
	public void add(int val){
		mArray[mCount++] = val;
	}
	
	/**
	 * 返回队列开头元素
	 * @return
	 */
	public int front(){
		return mArray[0];
	}
	
	/**
	 * 返回队首元素值，并删除队首元素
	 * @return
	 */
	public int pop(){
		int ret = mArray[0];
		mCount--;
		for(int i = 1; i <= mCount; i++)
			mArray[i-1] = mArray[i];
		
		return ret;
	}
	
	/**
	 * 返回队列的大小
	 */
	public int size(){
		return mCount;
	}
	
	/**
	 * 返回队列是否为空
	 */
	public boolean isEmpty(){
		return size() == 0;
	}
	
	public static void main(String[] args){
		int tmp = 0;
		ArrayQueue aqueue = new ArrayQueue(12);
		
		//将10/20/30依次放入队列中
		aqueue.add(10);
		aqueue.add(20);
		aqueue.add(30);
		
		//将队首元素赋值给tmp，并删除队首元素
		tmp = aqueue.pop();
		System.out.println("被删除的队首元素是tmp=" + tmp);
		
		aqueue.add(40);
		
		System.out.println("isEmpty()="  + aqueue.isEmpty());
		System.out.println("size()=" + aqueue.size());
		while(!aqueue.isEmpty()){
			System.out.println("元素为:" + aqueue.pop());
		}
	}

}
