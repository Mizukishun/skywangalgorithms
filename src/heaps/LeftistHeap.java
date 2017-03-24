package heaps;

/**
 * 左倾堆
 * 
 * 又称为左偏树、左偏堆、最左堆等；
 * 
 * 节点的NPL：Null Path Length，是从一个节点到一个“最近的不满节点”的路径长度；
 * 不满节点是指该节点的左右孩子中至少有一个节点是为NULL的；
 * 叶子节点的NPL为0；NULL节点的NPL为-1；
 * 
 * 左倾堆的性质：
 * 1、节点的键值 小于等于它的左右节点的键值；
 * 2、节点的左孩子的NPL >= 右孩子的NPL；
 * 3、节点的NPL = 它的右孩子的NPL + 1；
 * 
 * 
 * 两个左倾堆的合并：
 * 1、如果一个空左倾堆与一个非空左倾堆合并，返回非空左倾堆；
 * 2、如果两个左倾堆都非空，那么比较两个根节点，取键值较小那个堆的根节点作为新的根节点，
 *		将较小堆的根节点的右子树整体 与 较大堆整体 进行交换；重复这个交换，直到只剩一个堆；
 * 3、如果新堆的右孩子的NPL > 左孩子的NPL，则交换左右孩子；
 * 4、设置新堆的根节点的NPL = 右子堆NPL + 1；
 * 
 * 
 * @author Stargazer
 * @date 2017-03-22
 */

public class LeftistHeap<T extends Comparable<T>>{
	
	private LeftistNode<T> mRoot;				//根节点
	
	private class LeftistNode<T extends Comparable<T>>{
		T key;								//键值
		int npl;								//节点的NPL
		LeftistNode<T> left;		//左孩子
		LeftistNode<T> right;		//右孩子
		
		public LeftistNode(T key, LeftistNode<T> left, LeftistNode<T> right){
			this.key = key;
			this.npl = 0;
			this.left = left;
			this.right = right;
		}
		
		
		public String toString(){
			return "key : " + key;
		}
	}
	
	public LeftistHeap(){
		mRoot = null;
	}
	
	/**
	 * 前序遍历左倾堆
	 */
	private void preOrder(LeftistNode<T> heap){
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
	 * 中序遍历左倾堆
	 */
	private void inOrder(LeftistNode<T> heap){
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
	 * 后序遍历左倾堆
	 */
	private void postOrder(LeftistNode<T> heap){
		if(heap != null){
			postOrder(heap.left);
			postOrder(heap.right);
			System.out.print(heap.key + " ");
		}
	}
	
	/**
	 * 后序遍历
	 */
	public void postOrder(){
		postOrder(mRoot);
	}
	
	/**
	 * 合并左倾堆x和左倾堆y
	 * 
	 * @param x  	待合并的左倾堆
	 * @param y 	待合并的左倾堆
	 * @return 		返回合并后的左倾堆
	 */
	private LeftistNode<T> merge(LeftistNode<T> x, LeftistNode<T> y){
		if(x == null)
			return y;
		
		if(y == null)
			return x;
		
		//合并x和y时，将x作为合并后的树的根；
		//这里的操作是保证让x的key < y的key
		if(x.key.compareTo(y.key) > 0){
			LeftistNode<T> tmp = x;
			x = y;
			y = tmp;
		}
		
		//将x的右子树和y合并，合并后的树的根是x的右孩子
		x.right = merge(x.right, y);
		
		//如果x的左孩子为空，或者x的左孩子的NPL < 右孩子的NPL，则交换左、右两子树
		if(x.left == null || x.left.npl < x.right.npl){
			LeftistNode<T> tmp = x.left;
			x.left = x.right;
			x.right = tmp;
		}
		
		if(x.right == null || x.left == null)
			x.npl = 0;
		else
			x.npl = (x.left.npl > x.right.npl) ? (x.right.npl + 1) : (x.left.npl + 1);
			
		return x;
	}
	
	/**
	 * 将键值key插入左倾堆中
	 */
	public void  insert(T key){
		LeftistNode<T> node = new LeftistNode<T>(key, null, null);
		
		//如果新建节点失败，则返回
		if(node != null)
			this.mRoot = merge(this.mRoot, node);
	}
	
	/**
	 * 删除根节点
	 * 
	 * @return 返回被删除的节点的键值
	 */
	public T remove(){
		if(this.mRoot == null){
			return null;
		}
		
		T key = this.mRoot.key;
		LeftistNode<T> left = this.mRoot.left;
		LeftistNode<T> right = this.mRoot.right;
		
		this.mRoot = null;		//删除根节点
		this.mRoot = merge(left, right);		//调整删除了根节点后的两个子树重新为一个左倾堆
		
		return key;
	}
	
	/**
	 * 销毁左倾堆
	 */
	private void destroy(LeftistNode<T> heap){
		if(heap == null)
			return;
		
		if(heap.left != null)
			destroy(heap.left);
		if(heap.right == null)
			destroy(heap.right);
		
		heap = null;
	}
	
	/**
	 * 清空
	 */
	public void clear(){
		destroy(mRoot);
		mRoot = null;
	}
	
	/**
	 * 打印左倾堆
	 * 
	 * @param key 			节点的键值
	 * @param direction 	0，表示该节点是根节点
	 * 									-1，表示该节点是父节点的左孩子
	 * 									1，表示该节点是父节点的右孩子
	 */
	private void print(LeftistNode<T> heap, T key, int direction){
		if(heap != null){
			if(direction == 0)
				System.out.println(heap.key + " is root.");
			else
				System.out.println(heap.key + " is " + key + (direction==1?"right":"left") + "'s child.");
			
			
			print(heap.left, heap.key, -1);
			print(heap.right, heap.key, 1);
		}
	}
	
	/**
	 * 打印
	 */
	public void print(){
		if(mRoot != null){
			print(mRoot, mRoot.key, 0);
		}
	}

}
