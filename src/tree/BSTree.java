package tree;

/**
 * 二叉查找树
 * 
 * 树是一种数据结构，它是由n(n>=1)个有限节点组成的一个具有层次关系的集合；
 * 具有以下几个特点：
 * 1、每个节点有零个或多个子节点；
 * 2、没有父节点的节点称为根节点；
 * 3、每一个非根节点有且只有一个父节点；
 * 4、除了根节点外，每个子节点可以分为多个不相交的子树；
 * 
 * 树的基本术语：
 * 		若一个节点有子树，那么该节点称为子树根的“双亲”；
 * 		子树的根是该节点的“孩子”；
 * 		有相同双亲的节点互为“兄弟”；
 * 		一个节点的所有子树上的任何节点都是该节点的“后裔”；
 * 		从根节点到某个节点的路径上的所有节点都是该节点的“祖先”；
 * 
 * 节点的度：节点拥有的子树的数目；
 * 叶子：度为零的节点；
 * 分支节点：度不为零的节点；
 * 树的度：树中节点的最大的度；
 * 层次：根节点的层次为1，其余节点的层次等于该节点的双亲节点的层次加1；
 * 树的高度：树中节点的最大层次；
 * 无序树：如果树中节点的各子树之间的次序是不重要的，可以交换位置；
 * 有序树：如果树中节点的各子树之间的次序是重要的，不可以交换位置；
 * 森林：0个或多个不相交的树组成。如果对森林加上一个根，森林即成为树；删去根，树即成为森林；
 * 
 * 二叉树
 * 
 * 二叉树是每个节点最多有两个子树的树结构；它有五种基本形态：二叉树可以是空集；
 * 根可以有空的左子树或右子树；或者左右子树皆为空；
 * 
 * 二叉树的性质：
 * 1、二叉树第i层上的节点数目最多为(2^(i-1))；
 * 2、深度为k的二叉树之多有(2^k - 1)个结点；
 * 3、包含n个节点的二叉树的高度至少为log2(n+1);
 * 4、在任意一棵二叉树中，若终端节点的个数为N，度为2的节点个数为M，则N=M+1;
 * 
 * 满二叉树
 * 定义：高度为h,并且由(2^h - 1)个节点的二叉树；
 * 
 * 完全二叉树
 * 定义：一棵二叉树中，只有最下面两层节点的度可以小于2，并且最下一层的 叶节点
 * 			集中在靠左的若干位置上，这样的二叉树成为完全二叉树；
 * 特点：叶子节点只能出现在最下层和次下层，且最下层的叶子节点集中在树的左边；
 * 			一棵满二叉树必定是一棵完全二叉树，但完全二叉树未必是满二叉树；
 * 
 * 二叉查找树
 * 定义：又称为二叉搜索树，设x为二叉查找树中的一个节点，x节点包含关键字key,节点x的key值记为key[x];
 * 			如果y是x的左子树中的一个节点，则key[y]<= key[x];如果y是x的右子树中的一个节点，则key[y] >= key[x];
 * 特点：
 * 		1、若任意节点的左子树不空，则左子树上所有节点的值均小于它的根节点的值；
 * 		2、若任意节点的右子树不空，则右子树上所有节点的值均大于它的根节点的值；
 * 		3、任意节点的左、右子树也分别而二叉查找树；
 * 		4、没有键值相等的节点； 
 * 
 * 二叉查找树的遍历
 * （根据对根节点的访问先后位置分为前序、中序、后序遍历）
 * 
 * 前序遍历：若二叉查找树非空，则先访问根节点，再继续访问左子树，最后再访问右子树；
 * 
 * 中序遍历：若二叉查找树非空，则先访问左子树，再继续访问根节点，最后再访问右子树；
 * 
 * 后序遍历：若二叉查找树非空，则先访问左子树，再继续访问右子树，最后再访问根节点；
 * 
 * 节点的前驱：是该节点的左子树中的最大节点；
 * 节点的后继：是该节点的右子树中的最小节点；
 * 
 * 
 * 
 * 
 * @author Stargazer
 * @date 2017-03-19
 */

public class BSTree<T extends Comparable<T>> {
	//二叉查找树
	
	//根节点
	private BSTNode<T> mRoot;
	
	public class BSTNode<T extends Comparable<T>>{
		T key;								//键值
		BSTNode<T> left;			//左孩子
		BSTNode<T> right;			//右孩子
		BSTNode<T> parent;		//父节点
		
		public BSTNode(T key, BSTNode<T> parent, BSTNode<T> left, BSTNode<T> right){
			this.key = key;
			this.parent = parent;
			this.left = left;
			this.right = right;
		}
		
		public T getKey(){
			return key;
		}
		
		public String toString(){
			return "key:"  + key;
		}
	}
	
	public BSTree(){
		mRoot = null;
	}
	
	/**
	 * 前序遍历二叉查找树
	 */
	private void preOrder(BSTNode<T> tree){
		if(tree != null){
			System.out.println(tree.key + "");
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
	 * 中序遍历二叉查找树
	 */
	private void inOrder(BSTNode<T> tree){
		if(tree != null){
			inOrder(tree.left);
			System.out.println(tree.key + "");
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
	 * 后序遍历二叉查找树
	 */
	private void postOrder(BSTNode<T> tree){
		if(tree != null){
			postOrder(tree.left);
			postOrder(tree.right);
			System.out.println(tree.key + "");
		}
	}
	
	/**
	 * 后序遍历
	 */
	public void postOrder(){
		postOrder(mRoot);
	}
	
	/**
	 * 查找二叉查找树x中键值为key的节点（递归实现）
	 */
	private BSTNode<T> search(BSTNode<T> x, T key){
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
	 * 
	 * @param key
	 * @return
	 */
	public BSTNode<T> search(T key){
		return search(mRoot, key);
	}
	
	/**
	 * 查找二叉查找树中键值为key的节点（非递归实现）
	 */
	private BSTNode<T> iterativeSearch(BSTNode<T> x, T key){
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
	public BSTNode<T> iterativeSearch(T key){
		return iterativeSearch(mRoot, key);
	}
	
	/**
	 * 查找最小节点，返回tree为根节点的二叉树中的最小节点
	 */
	private BSTNode<T> minimum(BSTNode<T> tree){
		if(tree == null)
			return null;
		
		while(tree.left != null)
			tree = tree.left;
		
		return tree;
	}
	
	
	/**
	 * 查找最小节点的键值
	 */
	public T minimum(){
		BSTNode<T> p = minimum(mRoot);
		if(p != null)
			return p.key;
		
		return null;
	}
	
	/**
	 * 查找最大节点，返回tree为根节点的二叉树的最大节点
	 */
	private BSTNode<T> maximum(BSTNode<T> tree){
		if(tree == null)
			return null;
		
		while(tree.right != null)
			tree = tree.right;
		
		return tree;
	}
	
	/**
	 * 查找最大节点的键值
	 */
	public T maximum(){
		BSTNode<T> p = maximum(mRoot);
		if(p != null)
			return p.key;
		
		return null;
	}
	
	/**
	 * 查找节点x的后继节点，也即查找二叉树中键值大于该节点的最小节点
	 */
	public BSTNode<T> successor(BSTNode<T> x){
		//如果x存在右孩子，则x的后继节点即为以其右孩子为根的子树中的最小节点
		if(x.right != null)
			return minimum(x.right);
		
		//如果x没有右孩子，则x有以下两种可能：
		//1、x是一个左孩子，则x的后继节点是它的父节点
		//2、x是一个右孩子，则查找x的最低的父节点，并且该父节点要有左孩子，找到的这个最低父节点就是x的后继节点
		BSTNode<T> y = x.parent;
		while((y != null) && (x == y.right)){
			x = y;
			y = y.parent;
		}
		
		return y;
	}
	
	/**
	 * 查找节点x的前驱节点，也即查找二叉树中键值小于该节点的最大节点
	 */
	public BSTNode<T> predecessor(BSTNode<T> x){
		//如果x存在左孩子，则x的前驱节点即为以其左孩子为根的子树中的最大节点
		if(x.left != null)
			return maximum(x.left);
		
		//如果x没有左孩子，则x有以下两种可能：
		//1、x是一个右孩子，则x的前驱节点即为它的父节点；
		//2、x是一个左孩子，则查找x的最低的父节点，并且该父节点要具有右孩子，找到的这个父节点就是x的前驱节点
		BSTNode<T> y = x.parent;
		while((y != null ) && (x == y.left)){
			x = y;
			y = y.parent;
		}
		
		return y;
	}
	
	/**
	 * 将节点插入到二叉树中
	 * 
	 * @param tree 	二叉树
	 * @param z 		插入的节点
	 */
	private void insert(BSTree<T> tree, BSTNode<T> z){
		int cmp;
		BSTNode<T> y = null;
		BSTNode<T> x = tree.mRoot;
		
		//查找z的插入位置
		while(x != null){
			y = x;
			cmp = z.key.compareTo(x.key);
			if(cmp < 0)
				x = x.left;
			else
				x = x.right;
		}
		
		z.parent = y;
		if(y == null)
			tree.mRoot = z;
		else{
			cmp = z.key.compareTo(y.key);
			if(cmp < 0)
				y.left = z;
			else
				y.right = x;
		}
	}
	
	/**
	 * 插入一个键值为key的新节点
	 */
	public void insert(T key){
		BSTNode<T> z = new BSTNode<T>(key, null, null, null);
		
		//如果新建失败，则返回
		if(z != null)
			insert(this, z);
		
	}
	
	/**
	 * 删除节点z，并返回被删除的节点
	 */
	private BSTNode<T> remove(BSTree<T> tree, BSTNode<T> z){
		BSTNode<T> x = null;
		BSTNode<T> y = null;
		
		if((z.left == null) || (z.right == null)){
			y = z;
		}else{
			y = successor(z);
		}
		
		if(y.left != null)
			x = y.left;
		else
			x = y.right;
		
		if(x != null)
			x.parent = y.parent;
		else if(y == y.parent.left)
			y.parent.left = x;
		else
			y.parent.right = x;
		
		if(y != z)
			z.key = y.key;
		
		return y;
	}
	
	/**
	 * 删除键值为key的节点，并返回被删除的节点
	 */
	public void remove(T key){
		BSTNode<T> z, node;
		
		if((z = search(mRoot, key)) != null){
			if((node = remove(this, z)) != null){
				node = null;
			}
		}
	}
	
	/**
	 * 销毁二叉树
	 */
	private void destory(BSTNode<T> tree){
		if(tree == null)
			return;
		
		if(tree.left != null)
			destory(tree.left);
		
		if(tree.right != null)
			destory(tree.right);
		
		tree = null;
	}
	
	/**
	 * 清空二叉树
	 */
	public void clear(){
		destory(mRoot);
		mRoot = null;
	}
	
	/**
	 * 打印二叉树
	 * 
	 * @param key 		节点的键值
	 * @param direction		0，表示该节点是根节点
	 * 								 	-1，表示该节点是它的父节点的左孩子
	 * 									1，表示该节点是它的父节点的右孩子
	 */
	private void print(BSTNode<T> tree, T key, int direction){
		
		if(tree != null){
			
			if(direction == 0){
				System.out.println(tree.key + " is root!");
			}else{
				System.out.println(tree.key + " is " + key + "'s " + (direction==1?"right":"lefft") + " child");			
			}
			
			print(tree.left, tree.key, -1);
			print(tree.right, tree.key, 1);
		}
	}
	
	/**
	 * 打印二叉树
	 */
	public void print(){
		if(mRoot != null)
			print(mRoot, mRoot.key, 0);
	}

}
