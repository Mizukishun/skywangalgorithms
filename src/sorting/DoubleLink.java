package sorting;

/**
 * 线性表
 * (数组、单链表、双链表）
 * 
 * 线性表是一种线性结构，它是具有相同类型的n(n>=0)个数据元素组成的有限序列。
 * 
 * 数组：是有上界和下界的，数组的元素在内存中是连续的；随机访问速度快；
 * 动态数组是指数组的容量能够动态增长的数组；
 * 
 * 单向链表是链表的一种，它由节点组成，每个节点都包含指向下一个节点的指针；
 * 单链表的特点是：节点的链接方向是单向的；相对于数组来说，单链表的随机访问速度较慢，
 * 但是单链表删除、添加数据的效率较高；
 * 
 * 双向链表是链表的一种，它由节点组成，每个节点都有两个指针，分别指向其直接后继和
 * 直接前驱；所以，从双向链表中的任意一个节点开始，都可以很方便地访问它的前驱和后继
 * 节点；一般都可构造成双向循环链表；
 * 
 * 
 * @author Stargazer
 * @date 2017-03-18
 */

public class DoubleLink<T> {
	//这里只实现了双向链表
	
	//表头
	private DNode<T> mHead;
	//节点个数
	private int mCount;
	
	//双向链表“节点”对应的类
	private class DNode<T>{
		public DNode prev;			//该节点的前驱节点
		public DNode next;			//该节点的后继节点
		public T value;					//该节点的数据值
		
		public DNode(T value, DNode prev, DNode next){
			this.value = value;
			this.prev = prev;
			this.next = next;
		}
	}
	
	//双向链表的构造函数
	public DoubleLink(){
		//创建表头，注意：表头没有存储数据
		mHead = new DNode<T>(null, null, null);
		
		//空的双向链表，使表头的的前驱和后继指针皆指向表头自己
		mHead.prev = mHead.next = mHead;
		
		//初始化节点个数为0
		mCount = 0;
	}
	
	//返回节点数目
	public int size(){
		return mCount;
	}
	
	//返回 链表是否为空
	public boolean isEmpty(){
		return mCount == 0;
	}
	
	//获取第index位置的节点（位置从0开始计数）
	private DNode<T> getNode(int index){
		if(index < 0 || index >= mCount)
			throw new IndexOutOfBoundsException();
		
		//正向查找
		if(index <= mCount/2){
			DNode<T> node = mHead.next;
			for(int i = 0; i < index; i++)
				node = node.next;
			
			return node;
		}
		
		//反向查找
		DNode<T> rnode = mHead.prev;
		int rindex = mCount - index - 1;
		for(int j = 0; j < rindex; j++)
			rnode = rnode.prev;
		
		return rnode;
	}
	
	//获取第index位置的节点的数据值
	public T get(int index){
		return getNode(index).value;
	}
	
	//获取第一个节点的数据值
	public T getFirst(){
		return getNode(0).value;
	}
	
	//获取最后一个节点的数据值
	public T getLast(){
		return getNode(mCount-1).value;
	}
	
	//将节点插入到第index位置之前
	public void insert(int index, T t){
		if(index == 0){
			DNode<T> node = new DNode<T>(t, mHead, mHead.next);
			mHead.next.prev = node;
			mHead.next = node;
			mCount++;
			return;
		}
		
		DNode<T> inode = getNode(index);
		DNode<T> tnode = new DNode<T>(t, inode.prev, inode);
		inode.prev.next = tnode;
		inode.prev = tnode;
		
		mCount++;
		return;
	}
	
	//将节点插入第一个节点处
	public void insertFirst(T t){
		insert(0, t);
	}
	
	//将节点追加到链表的末尾
	public void appendLast(T t){
		DNode<T> node = new DNode<T>(t, mHead.prev, mHead);
		mHead.prev.next = node;
		mHead.prev = node;
		mCount++;
		return;
	}
	
	//删除index位置的节点
	public void delete(int index){
		DNode<T> inode = getNode(index);
		inode.prev.next = inode.next;
		inode.next.prev = inode.prev;
		inode = null;
		mCount--;
		return;
	}
	
	//删除第一个节点
	public void deleteFirst(){
		delete(0);
	}
	
	//删除最后一个节点
	public void deleteLast(){
		delete(mCount--);
	}
	
	
	
	
}
