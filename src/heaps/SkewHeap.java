package heaps;

/**
 * 斜堆
 * 
 * 斜堆也称为自适应堆，是左倾堆的一个变种；
 * 
 * 斜堆的合并操作的时间复杂度是O(lgN)；
 * 
 * 与左倾堆的差别：
 * 1、斜堆的节点没有NPL这个属性，而左倾堆有；
 * 2、斜堆的合并操作和左倾堆的合并算法不同；
 * 
 * 斜堆的合并操作：
 * 1、如果一个空斜堆与一个非空斜堆合并，则返回非空斜堆；
 * 2、如果两个斜堆都非空，那么比较两个根节点，去较小堆的根节点为新的根节点，
 * 		将较小堆的根节点的右子树和较大堆进行合并；
 * 3、合并后，交换新堆根节点的左孩子和右孩子；
 * 
 * 
 * 
 * 
 * @author Stargazer
 * @date 2017-03-24
 */

public class SkewHeap<T extends Comparable<T>> {

	private SkewNode<T> mRoot;			//根节点
	
	private class SkewNode<T extends Comparable<T>>{
		T key;								//键值
		SkewNode<T> left;			//左孩子
		SkewNode<T> right;		//右孩子
		
		public SkewNode(T key, SkewNode<T> left, SkewNode<T> right){
			this.key = key;
			this.left = left;
			this.right = right;
		}
		
		public String toString(){
			return " key : " + key;
		}
	}
	
	public SkewHeap(){
		mRoot = null;
	}
	
	/**
	 * 前序遍历斜堆
	 * @param heap
	 */
	private void preOrder(SkewNode<T> heap){
		if(heap != null){
			System.out.print(heap.key + " ");
			preOrder(heap.left);
			preOrder(heap.right);
		}
	}
	
	/**
	 * 前序遍历
	 */
	public void preOrder(){
		preOrder(mRoot);
	}
	
	/**
	 * 中序遍历
	 */
	private void inOrder(SkewNode<T> heap){
		if(heap != null){
			inOrder(heap.left);
			System.out.print(heap.key + " ");
			inOrder(heap.right);
		}
	}
	
	/**
	 * 中序遍历
	 */
	public void inOrder(){
		inOrder(mRoot);
	}
	
	/**
	 * 后续遍历斜堆
	 */
	private void postOrder(SkewNode<T> heap){
		if(heap != null){
			postOrder(heap.left);
			postOrder(heap.right);
			System.out.print(heap.key + " ");
		}
	}
	
	/**
	 * 后续遍历
	 */
	public void postOrder(){
		postOrder(mRoot);
	}
	
	/**
	 * 合并斜堆x和斜堆y
	 */
	private SkewNode<T> merge(SkewNode<T> x, SkewNode<T> y){
		if(x == null)
			return y;
		if(y == null)
			return x;
		
		//合并x和y时，将x作为合并后的树的根；
		//这里的操作是保证x的key<y的key
		if(x.key.compareTo(y.key) > 0){
			SkewNode<T> tmp = x;
			x = y;
			y = tmp;
		}
		
		/*
		 * 将x的右孩子和y合并，合并后直接交换x的左右孩子，而不需要像左倾堆那样考虑NPL
		 */
		SkewNode<T> tmp = merge(x.right, y);
		x.right = x.left;
		x.left = tmp;
		
		return x;
	}
	
	public void merge(SkewHeap<T> other){
		this.mRoot = merge(this.mRoot, other.mRoot);
	}
	
	/**
	 * 将键值key插入到斜堆中
	 */
	public void insert(T key){
		SkewNode<T> node = new SkewNode<T>(key, null, null);
		
		//如果新建节点失败，则返回
		if(node != null){
			this.mRoot = merge(this.mRoot, node);
		}
	}
	
	/**
	 * 删除根节点
	 * 
	 * @return  返回被删除节点的键值
	 */
	public T remove(){
		if(this.mRoot == null)
			return null;
		
		T key = this.mRoot.key;
		SkewNode<T> l = this.mRoot.left;
		SkewNode<T> r = this.mRoot.right;
		
		this.mRoot = null;			//删除根节点
		this.mRoot = merge(l, r);	//合并左右子节点
		
		return key;
	}
	
	/**
	 * 销毁斜堆
	 */
	private void destroy(SkewNode<T> heap){
		if(heap == null)
			return ;
		
		if(heap.left != null)
			destroy(heap.left);
		if(heap.right != null)
			destroy(heap.right);
		
		heap = null;
	}
	
	public void clear(){
		destroy(mRoot);
		mRoot = null;
	}
	
	/**
	 * 打印斜堆
	 * 
	 * key 					节点的键值
	 * direction 			0，表示该节点是它的父节点的左孩子
	 * 							-1，表示该节点是它的父节点的右孩子
	 */
	private void print(SkewNode<T> heap,  T key, int direction){
		if(heap != null){
			if(direction == 0)
				System.out.println(heap.key + " is root.");
			else
				System.out.println(heap.key + " is " + key + "'s " + (direction==1?"right":"left"));
			
			
			print(heap.left, heap.key, -1);
			print(heap.right, heap.key, 1);
		}
	}
	
	public void print(){
		if(mRoot != null){
			print(mRoot, mRoot.key, 0);
		}
	}
	
}
