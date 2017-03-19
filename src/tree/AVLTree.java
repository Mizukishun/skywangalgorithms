package tree;

/**
 * AVL树
 * 
 * AVL树是高度平衡的二叉树，它的特点是：AVL树中任何节点的两个子树的高度最大差别都<=1;
 * 
 * AVL树的查找、插入和删除在平均和最坏情况下都是O(logN);
 * 
 * 树的高度为最大层次，也即空的二叉树的高度为0，非空树的高度等于它的最大层次；
 * 根的层次为1，根的子节点为第2层，以此类推；
 * 
 * 旋转
 * 如果在AVL数中进行插入或删除节点后，可能导致AVL树失去平衡，因此需要通过旋转来保持平衡；
 * 
 * (1)LL：LeftLeft, 也称为"左左"；插入或删除一个节点后，根节点的左子树还有非空子节点，导致
 * “根的左子树的高度”比“根的右子树的高度”大2，从而导致AVL树失去平衡；
 * 
 * (2)LR：LeftRight，也称为"左右"；插入或删除一个节点后，根节点的左子树的右子树还有非空子节点，
 * 导致“根的左子树的高度”比“根的右子树的高度”大2，导致AVL树失去平衡；
 * 
 * (3)RL：RightLeft，也称为“右左"；插入或删除一个节点后，根节点的右子树的左子树还有非空子节点，
 * 导致“根的右子树的高度”比“根的左子树的高度”大2，导致AVL树失去平衡；
 * 
 * (4)RR：RightRight，也称为"右右"；插入或删除一个节点后，根节点的右子树的右子树还有非空子节点，
 * 导致“根的右子树的高度”比“根的左子树的高度”大2，导致AVL树失去平衡；
 * 
 * 
 * 
 * @author Stargazer
 * @date 2017-03-19
 */

public class AVLTree<T extends Comparable<T>> {
	
	private AVLTreeNode<T> mRoot;		//根节点
	
	//AVL树的节点（内部类）
	class AVLTreeNode<T extends Comparable<T>>{
		T key;									//键值
		int height;							//高度
		AVLTreeNode<T> left;		//左孩子
		AVLTreeNode<T> right;		//右孩子
		
		public AVLTreeNode(T key, AVLTreeNode<T> left, AVLTreeNode<T> right){
			this.key = key;
			this.left = left;
			this.right = right;
			this.height = 0;
		}
	}
	
	//构造函数
	public AVLTree(){
		mRoot = null;
	}
	
	/**
	 * 获取树的高度
	 */
	private int height(AVLTreeNode<T> tree){
		if(tree != null)
			return tree.height;
		
		return 0;
	}
	
	/**
	 * 获取树的高度
	 */
	public int height(){
		return height(mRoot);
	}
	
	/**
	 * 比较两个值的大小,并返回较大的值
	 */
	private int max(int a, int b){
		return a>b ? a : b;
	}
	
	/**
	 * 前序遍历AVL树
	 */
	private void preOrder(AVLTreeNode<T> tree){
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
	 * 中序遍历AVL树
	 */
	private void inOrder(AVLTreeNode<T> tree){
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
	 * 后序遍历AVL树
	 */
	private void postOrder(AVLTreeNode<T> tree){
		if(tree != null){
			postOrder(tree.left);
			postOrder(tree.right);
			System.out.print(tree.key + " ");
		}
	}
	/**
	 * 后续遍历
	 */
	public void postOrder(){
		postOrder(mRoot);
	}
	
	/**
	 * 查找AVL树x中键值为key的节点（递归实现）
	 */
	private AVLTreeNode<T> search(AVLTreeNode<T> x, T key){
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
	 * 查找键值key的节点
	 */
	public AVLTreeNode<T> search(T key){
		return search(mRoot, key);
	}
	
	/**
	 * 查找AVL树x中键值为key的节点（非递归实现）
	 */
	private AVLTreeNode<T> iterativeSearch(AVLTreeNode<T> x, T key){
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
	public AVLTreeNode<T> iterativeSearch(T key){
		return iterativeSearch(mRoot, key);
	}
	
	/**
	 * 查找最小节点，返回tree为根节点AVL树的最小节点
	 */
	private AVLTreeNode<T> minimum(AVLTreeNode<T> tree){
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
		AVLTreeNode<T> p = minimum(mRoot);
		if(p != null)
			return p.key;
		
		return null;
	}
	
	/**
	 * 查找最大节点，返回tree为根节点的额AVL树的最大节点
	 */
	private AVLTreeNode<T> maximum(AVLTreeNode<T> tree){
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
		AVLTreeNode<T> p = maximum(mRoot);
		if(p != null)
			return p.key;
		
		return null;
	}
	
	/**
	 * LL旋转，返回左单旋转后的根节点
	 */
	private AVLTreeNode<T> leftLeftRotation(AVLTreeNode<T> P){
		AVLTreeNode<T> L;
		
		L = P.left;
		P.left = L.right;
		L.right = P;
		
		P.height = max(height(P.left), height(P.right)) + 1;
		L.height = max(height(L.left), P.height) + 1;
		
		return L;
	}
	
	/**
	 * RR旋转，返回右单旋转后的根节点
	 */
	private AVLTreeNode<T> rightRightRotation(AVLTreeNode<T> P){
		AVLTreeNode<T> R;
		
		R = P.right;
		P.right = R.left;
		R.left = P;
		
		P.height = max(height(P.left), height(P.right)) + 1;
		R.height = max(height(R.right), P.height) + 1;
		
		return R;
	}
	
	/**
	 * LR旋转，（←←先进行RR旋转，再LL旋转）
	 * 
	 * 返回旋转后的根节点
	 */
	private AVLTreeNode<T> leftRightRotation(AVLTreeNode<T> P){
		P.left = rightRightRotation(P.left);
		
		return leftLeftRotation(P);
	}
	
	/**
	 * RL旋转，（←←先进行LL旋转，在RR旋转）
	 * 
	 * 返回旋转后的根节点
	 */
	private AVLTreeNode<T> rightLeftRotation(AVLTreeNode<T> P){
		P.right = leftLeftRotation(P.right);
		
		return rightRightRotation(P);
	}
	
	/**
	 * 将节点插入到AVL树中，并返回根节点
	 * 
	 * @param tree AVL树的根节点
	 * @param key 插入的节点的键值
	 */
	private AVLTreeNode<T> insert(AVLTreeNode<T> tree, T key){
		if(tree == null){
			//新建节点
			tree = new AVLTreeNode<T>(key, null, null);
			if(tree == null){
				System.out.println("ERROR : create avltree node failed!");
				return null;
			}
		}else{
			int cmp = key.compareTo(tree.key);
			
			if(cmp < 0){
				//应该将key插入到tree的左子树中
				tree.left = insert(tree.left, key);
				
				//插入节点后，若AVL树失去了平衡，则进行相应的旋转调节
				if(height(tree.left) - height(tree.right) == 2){
					if(key.compareTo(tree.left.key) < 0)
						tree = leftLeftRotation(tree);
					else
						tree = leftRightRotation(tree);
				}
			}else if(cmp > 0){
				//应该将key插入到tree的右子树中
				tree.right = insert(tree.right, key);
				
				if(height(tree.right) - height(tree.left) == 2){
					if(key.compareTo(tree.right.key) > 0)
						tree = rightRightRotation(tree);
					else
						tree = rightLeftRotation(tree);
				}
			}else{
				//cmp==0
				System.out.println("添加失败：不允许添加相同的节点！");
			}
		}
		
		tree.height = max(height(tree.left), height(tree.right)) + 1;
		
		return tree;
	}
	
	/**
	 * 删除节点z，返回删除节点后的根节点
	 * 
	 * @param tree  AVL树的根节点
	 * @param z  	 待删除的节点
	 */
	private AVLTreeNode<T> remove(AVLTreeNode<T> tree, AVLTreeNode<T> z){
		//根为空或者没有要删除的节点，则直接返回null
		if(tree == null || z == null)
			return null;
		
		int cmp = z.key.compareTo(tree.key);
		if(cmp < 0){
			//待删除的节点在tree的左子树中
			tree.left = remove(tree.left, z);
			
			//删除节点后，若AVL树失去平衡，则需通过旋转进行调节
			if(height(tree.right) - height(tree.left) == 2){
				AVLTreeNode<T> r = tree.right;
				if(height(r.left) > height(r.right))
					tree = rightLeftRotation(tree);
				else
					tree = rightRightRotation(tree);
			}
		}else if(cmp > 0){
			//待删除的节点在tree的右子树中
			tree.right = remove(tree.right, z);
			
			//删除节点后，若AVL树失去平衡，则需通过旋转进行调节
			if(height(tree.left) - height(tree.right) == 2){
				AVLTreeNode<T> l = tree.left;
				if(height(l.right) > height(l.left))
					tree = leftRightRotation(tree);
				else
					tree = leftLeftRotation(tree);
			}
		}else{
			//tree根节点即为对应的要删除的节点
			
			if((tree.left != null) && (tree.right != null)){
				//如果tree的左右子树都非空
				if(height(tree.left) > height(tree.right)){
					/*
					 * 如果tree的左子树比右子树高，则
					 * 1、找出tree左子树中的最大节点；
					 * 2、将该最大节点的值赋给tree
					 * 3、删除该最大节点
					 * 
					 * 也即是用tree左子树中的最大节点来替换掉要被删除的原tree根节点
					 */
					AVLTreeNode<T> max = maximum(tree.left);
					tree.key = max.key;
					tree.left = remove(tree.left, max);
				}else{
					/*
					 * 如果tree的左子树不比右子树高（即同样高，或者右子树比左子树高），则
					 * 1、找出tree的右子树中的最小节点；
					 * 2、将该最小节点的值赋给tree;
					 * 3、删除该最小节点
					 * 
					 * 也即是用tree右子树中的最小节点来替换要被删除的原tree根节点
					 */
					AVLTreeNode<T> min = minimum(tree.right);
					tree.key = min.key;
					tree.right = remove(tree.right, min);
				}
			}else{
				//如果tree左右子树中至少有一个为空，则可以把不为空的那个子树作为根节点
				AVLTreeNode<T> tmp = tree;
				tree = (tree.left != null) ? tree.left : tree.right;
				tmp = null;
			}
		}
		
		return tree;
	}
	
	/**
	 * 删除键值为key的节点
	 */
	public void remove(T key){
		AVLTreeNode<T> z;
		
		if((z = search(mRoot, key)) != null)
			mRoot = remove(mRoot, z);
	}
	
	/**
	 * 销毁AVL树
	 */
	private void destroy(AVLTreeNode<T> tree){
		if(tree == null)
			return ;
		
		if(tree.left != null)
			destroy(tree.left);
		
		if(tree.right != null)
			destroy(tree.right);
		
		tree = null;
	}
	
	/**
	 * 销毁AVL树
	 */
	public void destroy(){
		destroy(mRoot);
	}
	
	/**
	 * 打印AVL树
	 * 
	 * @param key 节点的键值
	 * @param direction  0，表示该节点是根节点；
	 * 								-1，表示该节点是它父节点的左孩子；
	 * 								 1，表示该节点是它父节点的右孩子；
	 */
	private void print(AVLTreeNode<T> tree, T key, int direction){
		if(tree != null){
			if(direction == 0)
				System.out.print(tree.key + " is root ");
			else
				System.out.print(tree.key + " is " + key + "'s child");
			
			print(tree.left, tree.key, -1);
			print(tree.right, tree.key, 1);
			
		}
	}
	
	/**
	 * 打印AVL树
	 */
	public void print(){
		if(mRoot != null)
			print(mRoot, mRoot.key, 0);
	}
}
