package tree;

/**
 * 红黑树
 * 
 * 红黑树的特性：
 * 1、每个节点或者是黑色，或者是红色；
 * 2、根节点是黑色；
 * 3、每个叶子 节点（NIL）是黑色；（这里的叶子节点是指为空（NIL或NULL）的叶子节点）
 * 4、如果一个节点是红色的，则它的子节点必须是黑色的；
 * 5、从一个节点到该节点 的子孙节点的所有路径上包含相同数目的黑节点；
 * 
 * 红黑树的时间复杂度为O(lgN)
 * 
 * 一棵含有N个节点的红黑树的高度至多为2log(N+1)
 * 
 * 
 * ====================================================================================
 * 红黑树的添加操作
 * 1、将红黑树当作一棵二叉查找树，将节点插入；
 * 2、将插入的节点着色为红色；
 * 3、通过一系列的旋转或着色等操作，使之重新成为一棵红黑树；
 * 		↓		  	↓			↓			↓			↓				↓			↓
 * 根据被插入节点的父节点的情况，可以将“当节点z被着色为红色节点，并插入二叉树”分为三种情况处理：
 * (1)被插入的节点是根节点：则直接把次节点着为黑色；
 * (2)被插入的节点的父节点为黑色：则什么都不需要做，节点被插入后，仍然是红黑树；
 * (3)被插入的节点的父节点是红色：那么该情况就与红黑树的特性5相冲突，在这种情况下，被插入节点是一定存在非空祖父节点的，
 * 并且被插入节点也一定存在叔叔节点（即使叔叔节点为空，我们也视之为存在，空节点本身是黑色节点），之后根据叔叔节点进一步分析：
 * 		(A)若当前节点的父节点是红色，且当前节点的祖父节点的另一子节点（叔叔节点）也是红色：
 * 			则：	(A1)将父节点设为黑色；
 * 					(A2)将叔叔节点设为黑色；
 * 					(A3)将祖父接项目设为红色；
 * 					(A4)将祖父节点设为"当前节点"（红色），即之后继续对"当前节点"进行操作；
 * 		(B)若当前节点的父节点是红色，叔叔节点是黑色，且当前节点是其父节点的右孩子：
 * 			则：	(B1)将“父节点”作为新的”当前节点“；
 * 					(B2)以新的”当前节点“为支点进行左旋；
 * 		(C)若当前节点的父节点是红色，叔叔节点是黑色，且当前节点是其父节点的左孩子：
 * 		 	则：	(C1)将父节点设为黑色；
 * 					(C2)将祖父节点设为红色；
 * 					(C3)以祖父节点为支点进行右旋；
 * 
 * 
 * ====================================================================================
 * 红黑树的删除操作：
 * 1、将红黑树当作一棵二叉查找树，将节点删除；
 * 			这与“删除常规的二叉查找树中删除节点的方法时一样的，分为3中情况：
 * 				(a)被删除节点没有儿子，即为叶节点，那么，可以直接将该节点删除就行；
 * 				(b)被删除节点只有一个儿子，那么，可以直接删除该节点，并用该节点的唯一子节点顶替它的位置即可；
 * 				(c)被删除节点有两个儿子，那么，先找出它的后继节点；然后把“它的后继节点的内容“复制给”该节点的内容“；之后，删除
 * 					“它的后继节点”；在这里，后继节点相当于替身，在将后继节点的内容复制给 “被删除节点”之后，再将后继节点删除，
 * 					从而问题就转化为删除后继节点的问题了。
 * 					在“被删除节点”有两个非空子节点的情况下，它的后继节点不可能是双子非空，也即意味着该节点的后继节点要么没有儿子，
 * 					要么只有一个儿子；若没有儿子，则按(a)情况处理，若只有一个儿子，则按(b)情况处理；
 * 	
 * 2、通过旋转和重新着色等一系列操作来修正该树，使之重新成为一棵红黑树；
 * 
 * 
 * 
 * @author Stargazer
 * @date 2017-03-21
 */

public class RBTree<T extends Comparable<T>> {
	private RBTNode<T> mRoot;			//根节点
	
	private static final boolean RED = false;
	private static final boolean BLACK = true;
	
	public class RBTNode<T extends Comparable<T>>{
		boolean color;					//颜色
		T key;								//键值
		RBTNode<T> left;			//左孩子
		RBTNode<T> right;			//右孩子
		RBTNode<T> parent;		//父节点
		
		public RBTNode(T key, boolean color, RBTNode<T> parent, RBTNode<T> left, RBTNode<T> right){
			this.key = key;
			this.parent = parent;
			this.left = left;
			this.right = right;
		}
		
		public T getKey(){
			return key;
		}
		
		public String toString(){
			return "" + key + (this.color==RED?"R":"B");
		}
	}
	
	public RBTree(){
		mRoot = null;
	}
	
	private RBTNode<T> parentOf(RBTNode<T> node){
		return node != null ? node.parent : null;
	}
	
	private boolean colorOf(RBTNode<T> node){
		return node != null ? node.color : BLACK;
	}
	
	private boolean isRed(RBTNode<T> node){
		return ((node != null)&&(node.color == RED)) ? true : false;
	}
	
	private boolean isBlack(RBTNode<T> node){
		return !isRed(node);
	}
	
	private void setBlack(RBTNode<T> node){
		if(node != null)
			node.color = BLACK;
	}
	
	private void setRed(RBTNode<T> node){
		if(node != null)
			node.color = RED;
	}
	
	private void setParent(RBTNode<T> node, RBTNode<T> parent){
		if(node != null)
			node.parent = parent;
	}
	
	private void setColor(RBTNode<T> node, boolean color){
		if(node != null)
			node.color = color;
	}
	
	/**
	 * 前序遍历红黑树
	 */
	private void preOrder(RBTNode<T> tree){
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
	 * 中序遍历红黑树
	 */
	private void inOrder(RBTNode<T> tree){
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
	 * 后序遍历
	 */
	private void postOrder(RBTNode<T> tree){
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
	 * 查找红黑树x中键值为key的节点（递归实现）
	 */
	private RBTNode<T> search(RBTNode<T> x, T key){
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
	public RBTNode<T> search(T key){
		return search(mRoot, key);
	}
	
	/**
	 * 查找红黑树中x中键值为key的节点（非递归实现）
	 */
	private RBTNode<T> iterativeSearch(RBTNode<T> x, T key){
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
	public RBTNode<T> iterativeSearch(T key){
		return iterativeSearch(mRoot, key);
	}
	
	/**
	 * 查找最小节点，返回tree为根节点的红黑树中的最小节点
	 */
	private RBTNode<T> minimum(RBTNode<T> tree){
		if(tree == null)
			return null;
		
		while(tree.left != null)
			tree = tree.left;
		
		return tree;
	}
	
	/**
	 * 查找最小键值
	 */
	public T minimum(){
		RBTNode<T> p = minimum(mRoot);
		if(p != null)
			return p.key;
		
		return null;
	}
	
	/**
	 * 查找最大节点，返回tree为根节点的红黑树中的最大节点
	 */
	private RBTNode<T> maximum(RBTNode<T> tree){
		if(tree == null)
			return null;
		
		while(tree.right != null)
			tree = tree.right;
		
		return tree;
	}
	
	/**
	 * 查找最大键值
	 */
	public T maximum(){
		RBTNode<T> p = maximum(mRoot);
		
		if(p != null)
			return p.key;
		
		return null;
	}
	
	/**
	 * 查找节点x的后继节点，即查找红黑树中键值大于x键值的最小节点
	 */
	public RBTNode<T> successor(RBTNode<T> x){
		//如果x存在右孩子，则x的后继节点为“以其右孩子为根的子树的最小节点”
		if(x.right != null)
			return minimum(x.right);
		
		//如果x不存在右孩子，则有以下两种情况
		//1、x是一个左孩子，在x的后继节点为其父节点；
		//2、x是一个右孩子，则查找x的最低父节点，并且该父节点要具有左孩子，找到这个最低的父节点就是x的后继节点；
		RBTNode<T> y = x.parent;
		while((y != null) && (x == y.right)){
			x = y;
			y = y.parent;
		}
		
		return y;
	}
	
	/**
	 * 查找节点x的前驱节点，及查找红黑树中键值小于x键值的最大节点
	 */
	public RBTNode<T> predecessor(RBTNode<T> x){
		//如果x存在左孩子，则x的前驱节点为“以其左孩子为根的子树的最大节点”
		if(x.left != null)
			return maximum(x.left);
		
		//如果x不存在左孩子，则有以下两种情况
		//1、x是一个右孩子，则x的前驱节点为其父节点；
		//2、x是一个左孩子，则查找x的最低父节点，并且该父节点要具有右孩子，找到这个最低的父节点即为x的前驱节点；
		RBTNode<T> y = x.parent;
		while((y != null) && (x == y.left)){
			x = y;
			y = y.parent;
		}
		
		return y;
	}
	
	/**
	 * 对红黑树p进行左旋转
	 */
	private void leftRotate(RBTNode<T> p){
		//设置p的右孩子为r
		RBTNode<T> r = p.right;
		
		//将r的左孩子设为p的右孩子；
		//如果r的左孩子非空，将p设为x的左孩子的父节点
		p.right = r.left;
		if(r.left != null)
			r.left.parent = p;
		
		//将p的父节点设为r的父节点
		r.parent = p.parent;
		
		if(p.parent == null){
			this.mRoot = r;
		}else{
			if(p.parent.left == p)
				p.parent.left = r;
			else
				p.parent.right = r;
		}
		
		//将p设为r的左孩子
		r.left = p;
		p.parent = r;
	}
	
	/**
	 * 对红黑树p进行右旋
	 */
	private void rightRotate(RBTNode<T> p){
		//设置L为p节点的左孩子
		RBTNode<T> L = p.left;
		
		//将L的右孩子设置p的左孩子
		//如果L的右孩子不为空的话，则将p设为L的右孩子的父节点
		p.left = L.right;
		if(L.right != null)
			L.right.parent = p;
		
		//将p的父节点设为L的父节点
		L.parent = p.parent;
		
		if(p.parent == null){
			this.mRoot = L;
		}else{
			if(p == p.parent.right){
				p.parent.right = L;
			}else{
				p.parent.left = L;
			}
		}
		
		L.right = p;
		
		p.parent = L;
		
	}
	
	/**
	 * 红黑树插入的修正函数
	 * 
	 * 在向红黑树插入节点之后（失去平衡），再调用次函数进行调整，
	 * 目的是将它重新调整成一颗红黑树；
	 * 
	 * @param node  	插入的节点
	 */
	private void insertFixUp(RBTNode<T> node){
		RBTNode<T> parent, gparent;
		
		//若父节点存在，并且父节点的颜色是红色
		while(((parent=parentOf(node)) != null) && isRed(parent)){
			gparent = parentOf(parent);
			
			//若父节点是祖父节点的左孩子
			if(parent == gparent.left){
				//Case 1条件：叔叔节点是红色
				RBTNode<T> uncle = gparent.right;
				if((uncle != null) && isRed(uncle)){
					setBlack(uncle);
					setBlack(parent);
					setRed(gparent);
					node = gparent;
					continue;
				}
				
				//Case 2条件：叔叔是黑色，且当前节点是右孩子
				if(parent.right == node){
					RBTNode<T> tmp;
					leftRotate(parent);
					tmp = parent;
					parent = node;
					node = tmp;
				}
				
				//Case 3条件：叔叔是黑色，且当前节点是左孩子
				setBlack(parent);
				setRed(gparent);
				rightRotate(gparent);
			}else{
				//若父节点是祖父节点的右孩子
				
				//Case 1条件：叔叔节点是红色
				RBTNode<T> uncle = gparent.left;
				
				if((uncle != null) && isRed(uncle)){
					setBlack(uncle);
					setBlack(parent);
					setRed(gparent);
					node = gparent;
					continue;
				}
				
				//Case 2条件：叔叔是黑色，且当前节点是左孩子
				if(parent.left == node){
					RBTNode<T> tmp;
					rightRotate(parent);
					tmp = parent;
					parent = node;
					node = tmp;
				}
				
				//Case 3条件：叔叔是黑色，且当前节点是右孩子
				setBlack(parent);
				setRed(gparent);
				leftRotate(gparent);
			}
		}
		
		//将根节点设为黑色
		setBlack(this.mRoot);
	}
	
	/**
	 * 将节点插入到红黑树中
	 * 
	 * @param node 		要插入的节点
	 */
	private void insert(RBTNode<T> node){
		int cmp;
		RBTNode<T> y = null;
		RBTNode<T> x = this.mRoot;
		
		//1、将红黑树当作一棵二叉查找树，将节点插入到二叉查找树中
		while(x != null){
			y = x;
			cmp = node.key.compareTo(x.key);
			if(cmp < 0)
				x = x.left;
			else
				x = x.right;
		}
		
		node.parent = y;
		if(y != null){
			cmp = node.key.compareTo(y.key);
			if(cmp < 0)
				y.left = node;
			else
				y.right = node;
		}else{
			this.mRoot = node;
		}
		
		//2、设置节点的颜色为红色
		node.color = RED;
		
		//3、将它重新修正为一棵红黑树
		insertFixUp(node);
	}
	
	/**
	 * 将键值为key的节点插入到红黑树中
	 */
	public void insert(T key){
		RBTNode<T> node = new RBTNode<T>(key, BLACK, null, null, null);
		
		//如果新建节点失败，则返回
		if(node != null)
			insert(node);
	}
	
	/**
	 * 红黑树删除的修正函数
	 * 
	 * 在红黑树中删除节点之后（失去平衡），再调用此函数进行调整，
	 * 目的是将它重新修正为一棵红黑树
	 * 
	 * @param node 	待修正的节点
	 */
	private void removeFixUp(RBTNode<T> node, RBTNode<T> parent){
		RBTNode<T> other;
		
		while((node == null || isBlack(node)) && (node != this.mRoot)){
			
			if(parent.left == node){
				other = parent.right;
				if(isRed(other)){
					//Case 1条件：x的兄弟w是红色的
					setBlack(other);
					setRed(parent);
					leftRotate(parent);
					other = parent.right;
				}
				
				if((other.left == null || isBlack(other.left)) && (other.right == null || isBlack(other.right))){
					//Case 2条件：x的兄弟w是黑色，且w的俩个孩子也都是黑色的
					setRed(other);
					node = parent;
					parent = parentOf(node);
				}else{
					if(other.right == null || isBlack(other.right)){
						//Case 3条件：x的兄弟w是黑色的，且w的左孩子是红色，右孩子为黑色
						setBlack(other.left);
						setRed(other);
						rightRotate(other);
						other = parent.right;
					}
					
					//Case 4条件：x的兄弟w是黑色的，且w的右孩子是红色的，左孩子任意颜色
					setColor(other, colorOf(parent));
					setBlack(parent);
					setBlack(other.right);
					leftRotate(parent);
					node = this.mRoot;
					break;
				}
				
			}else{
				
				other = parent.left;
				if(isRed(other)){
					//Case 1条件：x的兄弟w是红色的
					setBlack(other);
					setRed(parent);
					rightRotate(parent);
					other = parent.left;
				}
				
				if((other.left == null || isBlack(other.left)) && (other.right == null || isBlack(other.right))){
					//Case 2条件：x的兄弟w是黑色，且w的俩个孩子也都是黑色的
					setRed(other);
					node = parent;
					parent = parentOf(node);
				}else{
					
					if(other.left == null || isBlack(other.left)){
						//Case 3条件：x的兄弟w是黑色，且w的左孩子是红色的，右孩子为黑色的
						setBlack(other.right);
						setRed(other);
						leftRotate(other);
						other = parent.left;
					}
					
					//Case 4条件：x的兄弟w是黑色，且w的右孩子是红色的，左孩子任意颜色
					setColor(other, colorOf(parent));
					setBlack(parent);
					setBlack(other.left);
					rightRotate(parent);
					node = this.mRoot;
					break;
				}
			}
		}
		
		if(node != null)
			setBlack(node);
	}
	
	/**
	 * 删除节点node,并返回被删除的节点
	 * 
	 * @param node 	待删除的节点
	 */
	private void remove(RBTNode<T> node){
		RBTNode<T> child, parent;
		boolean color;
		
		//被删除节点的左右孩子都不为空的情况
		if((node.left != null) && (node.right != null)){
			//被删节点的后继节点（取代节点），用来替代被删除节点的位置，然后再将被删节点删除掉
			RBTNode<T> replace = node;
			
			//获取后继节点
			replace = replace.right;
			while(replace.left != null)
				replace = replace.left;
			
			//node节点不是根节点（只有根节点不存在父节点
			if(parentOf(node) != null){
				if(parentOf(node).left == node)
					parentOf(node).left = replace;
				else
					parentOf(node).right = replace;
				
			}else{
				//node节点是根节点，更新节点
				this.mRoot = replace;
			}
			
			//child是取代节点的右孩子，也是需要调整的节点
			//取代节点肯定不存在左孩子，因为它本身是一个后继节点
			child = replace.right;
			parent = parentOf(replace);
			
			//保存取代节点的颜色
			color = colorOf(replace);
			
			//被删除节点是它的后继节点的父节点
			if(parent == node){
				parent = replace;
			}else{
				//child不为空
				if(child != null)
					setParent(child, parent);
				
				parent.left = child;
				
				replace.right = node.right;
				setParent(node.right, replace);
			}
			
			replace.parent = node.parent;
			replace.color = node.color;
			replace.left = node.left;
			node.left.parent = replace;
			
			if(color == BLACK)
				removeFixUp(child, parent);
			
			node = null;
			return;
		}
		
		if(node.left != null){
			child = node.left;
		}
		else{
			child = node.right;
		}
		
		parent = node.parent;
		
		//保存取代节点的颜色
		color = node.color;
		
		if(child != null)
			child.parent = parent;
		
		//node节点不是根节点
		if(parent != null){
			if(parent.left == node)
				parent.left = child;
			else
				parent.right = child;
		}else{
			this.mRoot = child;
		}
		
		if(color == BLACK)
			removeFixUp(child, parent);
		
		node = null;
	}
	
	/**
	 * 删除键值为key的节点
	 */
	public void remove(T key){
		RBTNode<T> node;
		
		if((node = search(mRoot, key)) != null)
			remove(node);
	}
	
	/**
	 * 销毁红黑树
	 */
	private void destroy(RBTNode<T> tree){
		if(tree == null)
			return;
		
		if(tree.left != null)
			destroy(tree.left);
		if(tree.right != null)
			destroy(tree.right);
		
		tree = null;
	}
	
	/**
	 * 清空红黑树
	 */
	public void clear(){
		destroy(mRoot);
		mRoot = null;
	}
	
	/**
	 * 打印红黑树
	 * 
	 * @param key 	节点的键值
	 * @param direction 		0，表示该节点是根节点；
	 * 										-1，表示该节点是其父节点的左孩子；
	 * 										1，表示该节点是其父节点的右孩子；
	 */
	private void print(RBTNode<T> tree, T key, int direction){
		if(tree != null){
			
			if(direction == 0)
				System.out.print(tree.key + " is root.");
			else
				System.out.print(tree.key + " is " + (isRed(tree)?"R" : "B") + "'s " + (direction==1?"right" : "left"));
			
			print(tree.left, tree.key, -1);
			print(tree.right, tree.key, 1);
		}
	}
	
	/**
	 * 打印
	 */
	public void print(){
		if(mRoot != null)
			print(mRoot, mRoot.key, 0);
	}
	

}
