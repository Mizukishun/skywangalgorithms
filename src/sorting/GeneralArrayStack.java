package sorting;

import java.lang.reflect.Array;

/**
 * 栈
 * 
 * 栈是一种线性结构，它有以下几个特点：
 * 1、栈中数据是按照“后进先出(LIFO, Last In First Out)”方式进出栈的；
 * 2、向栈中添加/删除数据时，只能从栈顶进行操作；
 * 
 * 栈通常包括的三种操作：
 * push -- 向栈中添加元素
 * peek -- 返回栈顶元素，并不删除元素
 * pop --  返回并删除栈顶元素
 * 
 * 
 * @author Stargazer
 * @date 2017-03-19
 */

public class GeneralArrayStack<T> {

	private static final int DEFAULT_SIZE = 12;
	private T[] mArray;
	private int count;
	
	public GeneralArrayStack(Class<T> type){
		this(type, DEFAULT_SIZE);
	}
	
	public GeneralArrayStack(Class<T> type, int size){
		//不能直接使用mArray=new T[DEFAULT_SIZE];
		mArray = (T[]) Array.newInstance(type, size);
		count = 0;
	}
	
	/**
	 * 将val添加到栈中
	 * @param val
	 */
	public void push(T val){
		mArray[count++] = val;
	}
	
	/**
	 * 返回栈顶元素值，但并不删除
	 */
	public T peek(){
		return mArray[count-1];
	}
	/**
	 * 返回栈顶元素值，并删除栈顶元素
	 */
	public T pop(){
		T ret = mArray[count-1];
		count--;
		return ret;
	}
	
	/**
	 * 返回栈的大小
	 */
	public int size(){
		return count;
	}
	
	/**
	 * 返回栈是否为空
	 */
	public boolean isEmpty(){
		return size() == 0;
	}
	
	/**
	 * 打印栈
	 */
	public void printArrayStack(){
		if(isEmpty()){
			System.out.println("stack is Empty!");
			return;
		}
		
		System.out.println("stack size() = " + size());
		
		int i = size() - 1;
		while(i >= 0){
			System.out.println(mArray[i]);
			i--;
		}
	}
	
	public static void main(String[] args){
		String tmp;
		GeneralArrayStack<String> astack = new GeneralArrayStack<String>(String.class);
		
		//将10、20、30依次推入栈中
		astack.push("10");
		astack.push("20");
		astack.push("30");
		
		//将栈顶元素赋值给tmp,并删除栈顶元素
		tmp = astack.pop();
		System.out.println("栈顶元素tmp=" + tmp);
		
		//只将栈顶元素赋值给tmp，并不删除元素
		tmp = astack.peek();
		System.out.println("此时的栈顶元素为tmp="+ tmp);
		
		astack.push("40");
		astack.printArrayStack();
	}
}
