package tree;

/**
 * 伸展树
 * 
 * 伸展树是一种二叉树，它能在O(logN)内完成插入、查找和删除操作；
 * 
 * 伸展树属于二叉查找树，即它具有与二叉查找树一样的性质：假设x为树中的任意一个节点，
 * x节点包含关键字key, 节点x的key值记为key[x]；如果y是x的左子树中的一个节点，则key[y] <= key[x]；
 * 如果y是x的右子树的一个节点，则key[y] >= key[x];
 * 
 * 伸展树还具有一个特点：当某个节点被访问时，伸展树会通过旋转使该节点成为树根；
 * 这样做的好处是：下次要访问该节点时，能够迅速的访问到该节点；
 * 
 * 伸展树是一种自调整形式的二叉查找树，它会沿着从某个节点到树根之间的路径，通过一系列的旋转把
 * 这个节点搬移到树根去；
 * 
 * 
 * @author Stargazer
 * @date 2017-03-20
 */

public class SplayTree<T extends Comparable<T>> {
	
	private SplayTreeNode<T> mRoot;			//根节点
	
	public class SplayTreeNode<T extends Comparable<T>>{
		T key;								//键值
		SplayTreeNode<T> left;	//左孩子
		SplayTreeNode<T> right;	//右孩子
		
		public SplayTreeNode(){
			this.left = null;
			this.right = null;
		}
		
		public SplayTreeNode(T key, SplayTreeNode<T> left, SplayTreeNode<T> right){
			this.key = key;
			this.left = left;
			this.right = right;
		}
	}
	
	public SplayTree(){
		mRoot = null;
	}
	
	/**
	 * 前序遍历“伸展树”
	 */
	private void preOrder(SplayTreeNode<T> tree){
		if(tree != null){
			System.out.print(tree.key + " ");
			preOrder(tree.left);
			preOrder(tree.right);
		}
	}
	
	/**
	 * 前序遍历
	 */
	public void preOrder(){
		preOrder(mRoot);
	}
	
	/**
	 * 中序遍历伸展树
	 */
	private void inOrder(SplayTreeNode<T> tree){
		if(tree != null){
			inOrder(tree.left);
			System.out.print(tree.key + " ");
			inOrder(tree.right);
		}
	}
	
	/**
	 * 中序遍历
	 */
	public void inOrder(){
		inOrder(mRoot);
	}
	
	/**
	 * 后序遍历伸展树
	 */
	private void postOrder(SplayTreeNode<T> tree){
		if(tree != null){
			postOrder(tree.left);
			postOrder(tree.right);
			System.out.print(tree.key + " ");
		}
	}
	
	/**
	 * 后序遍历
	 */
	public void postOrder(){
		postOrder(mRoot);
	}
	
	/**
	 * 查找伸展树x中键值为key的节点（递归实现）
	 */
	private SplayTreeNode<T> search(SplayTreeNode<T> x, T key){
		if(x == null)
			return x;
		
		int cmp = key.compareTo(x.key);
		if(cmp < 0)
			return search(x.left, key);
		else if(cmp > 0)
			return search(x.right, key);
		else
			return x;
	}
	
	/**
	 * 查找键值为key的节点
	 */
	public SplayTreeNode<T> search(T key){
		return search(mRoot, key);
	}
	
	/**
	 * 查找伸展树x中键值为key的节点（非递归实现）
	 */
	private SplayTreeNode<T> iterativeSearch(SplayTreeNode<T> x, T key){
		while(x != null){
			int cmp = key.compareTo(x.key);
			
			if(cmp < 0)
				x = x.left;
			else if(cmp > 0)
				x = x.right;
			else
				return x;
		}
		return x;
	}
	
	/**
	 * 查找键值为key的节点
	 */
	public SplayTreeNode<T> iterativeSearch(T key){
		return iterativeSearch(mRoot, key);
	}
	
	/**
	 * 查找最小节点，返回tree为根节点的伸展树的最小节点
	 */
	private SplayTreeNode<T> minimum(SplayTreeNode<T> tree){
		if(tree == null)
			return null;
		
		while(tree.left != null)
			tree = tree.left;
		
		return tree;
	}
	
	/**
	 * 最小键值
	 */
	public T minimum(){
		SplayTreeNode<T> p = minimum(mRoot);
		if(p != null)
			return p.key;
		
		return null;
	}
	
	/**
	 * 查找最大节点，返回tree为根节点的伸展树的最大节点
	 */
	private SplayTreeNode<T> maximum(SplayTreeNode<T> tree){
		if(tree == null)
			return null;
		
		while(tree.right != null)
			tree = tree.right;
		
		return tree;
	}
	
	/**
	 * 最大键值
	 */
	public T maximum(){
		SplayTreeNode<T> p = maximum(mRoot);
		if(p != null)
			return p.key;
		
		return null;
	}
	
	/**
	 * 旋转key对应的节点为根节点，并返回该根节点
	 * 
	 * 注意：
	 * 1、当伸展树中存在键值为key的节点时：
	 * 			将键值为key的节点旋转为根节点；
	 * 2、当伸展树中不存在键值为key的节点，并且key < tree.key：
	 * 			(a)键值key的节点的前驱节点存在的话，则将键值为key的节点的前驱节点旋转为根节点；
	 * 			(b)键值key的节点的前驱节点不存在的话，则说明key比树中任何键值都要小，那么就把最小节点旋转为根节点；
	 * 3、当伸展树不存在键值为key的节点，并且key > tree.key：
	 * 			(a)键值key的节点的后继节点存在的话，则将键值为key的节点的后继节点旋转为根节点；
	 * 			(b)键值key的节点的后继节点不存在的话，则说明key比树中任何键值都要大，那么就把最大节点旋转为根节点；
	 * 
	 */
	private SplayTreeNode<T> splay(SplayTreeNode<T> tree, T key){
		if(tree == null){
			return null;
		}
		
		SplayTreeNode<T> N = new SplayTreeNode<T>();
		SplayTreeNode<T> l = N;
		SplayTreeNode<T> r = N;
		SplayTreeNode<T> c;
		
		for(;;){
			
			int cmp = key.compareTo(tree.key);
			if(cmp < 0){
				if(tree.left == null)
					break;
				
				if(key.compareTo(tree.left.key) < 0){
					c = tree.left;
					tree.left = c.right;
					c.right = tree;
					tree = c;
					if(tree.left == null)
						break;
				}
				r.left = tree;
				r = tree;
				tree = tree.left;
			}else if(cmp > 0){
				if(tree.right == null)
					break;
				
				if(key.compareTo(tree.right.key) > 0){
					c = tree.right;
					tree.right = c.left;
					c.left = tree;
					tree = c;
					if(tree.right == null)
						break;
				}
				
				l.right = tree;
				l = tree;
				tree = tree.right;
			}else{
				break;
			}
		}
		
		l.right = tree.left;
		r.left = tree.right;
		tree.left = N.right;
		tree.right = N.left;
		
		return tree;
	}
	
	/**
	 * 旋转
	 */
	public void splay(T key){
		mRoot = splay(mRoot, key);
	}
	
	/**
	 * 将节点插入到伸展树中，并返回根节点
	 * 
	 * @param tree 	伸展树的根节点
	 * @param z 		插入的节点
	 */
	private SplayTreeNode<T> insert(SplayTreeNode<T> tree, SplayTreeNode<T> z){
		int cmp;
		SplayTreeNode<T> y = null;
		SplayTreeNode<T> x = tree;
		
		//查找z的插入位置
		while(x != null){
			y = x;
			cmp = z.key.compareTo(x.key);
			
			if(cmp < 0){
				x = x.left;
			}else if(cmp > 0){
				x = x.right;
			}else {
				System.out.println("不允许插入相同的节点" + z.key);
				z = null;
				return tree;
			}
		}
		
		if(y == null)
			tree = z;
		else{
			cmp = z.key.compareTo(y.key);
			if(cmp < 0)
				y.left = z;
			else
				y.right = z;
		}
		
		return tree;
	}
	
	/**
	 * 插入键值为key的节点
	 */
	public void insert(T key){
		SplayTreeNode<T> z = new SplayTreeNode<T>(key, null, null);
		
		//如果新建节点失败，则返回
		if((z = new SplayTreeNode<T>(key, null, null)) == null)
			return;
		
		//插入节点
		mRoot = insert(mRoot, z);
		
		//将节点(key)旋转为根节点
		mRoot = splay(mRoot, key);
	}
	
	/**
	 * 删除节点z，并返回被删除的节点
	 */
	private SplayTreeNode<T> remove(SplayTreeNode<T> tree, T key){
		SplayTreeNode<T> x;
		
		if(tree == null)
			return null;
		
		//查找键值为key的节点，找不到的话就直接返回
		if(search(tree, key) == null)
			return tree;
		
		//将key对应的节点旋转为根节点
		tree = splay(tree, key);
		
		if(tree.left != null){
			//将tree的前驱节点旋转为根节点
			x = splay(tree.left, key);
			
			//移除tree节点
			x.right = tree.right;
		}else
			x = tree.right;
		
		tree = null;
		
		return x;
	}
	
	/**
	 * 删除键值为key的节点
	 */
	public void remove(T key){
		mRoot = remove(mRoot, key);
	}
	
	/**
	 * 销毁伸展树
	 */
	private void  destroy(SplayTreeNode<T> tree){
		if(tree == null)
			return;
		
		if(tree.left != null)
			destroy(tree.left);
		if(tree.right != null)
			destroy(tree.right);
		
		tree = null;
	}
	
	/**
	 * 清空伸展树
	 */
	public void clear(){
		destroy(mRoot);
		mRoot = null;
	}
	
	/**
	 * 打印伸展树
	 * 
	 * @param key  节点的键值
	 * @param direction  	0，表示该节点是根节点
	 * 									-1，表示该节点是它的父节点的左孩子
	 * 									1，表示该 节点是它父节点的右孩子
	 */
	private void  print(SplayTreeNode<T> tree, T key, int direction){
		if(tree != null){
			if(direction == 0)
				System.out.println(tree.key + " is root");
			else
				System.out.println(tree.key + " is " + (direction==1?"right" : "left"));
			
			print(tree.left, tree.key, -1);
			print(tree.right, tree.key, 1);
		}
	}
	
	/**
	 * 打印伸展树
	 */
	public void print(){
		if(mRoot != null)
			print(mRoot, mRoot.key, 0);
	}

	
}
