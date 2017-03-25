package heaps;

/**
 * 斐波那契堆
 * 
 * 斐波那契堆是一种可合并堆，可用于实现合并优先队列；它比二项堆具有更好的平摊分析性能，
 * 它的合并操作的时间复杂度是O(1)；
 * 与二项堆一样，它也是由一组最小有序树组成，并且是一种可合并堆；但与二项堆不同的是，
 * 斐波那契堆中的树不一定是二项树；而且二项堆中的树是有序排列的，但是斐波那契堆中的
 * 树都是有根但无序的；
 * 
 * 斐波那契堆是由一组最小堆组成的 ，这些最小堆的根节点组成了双向链表，也即根链表；
 * 斐波那契堆中的最小节点就是根链表中的最小节点；
 * 
 * 插入操作
 * 插入一个节点到堆中，直接将该节点插入到根链表的min节点之前即可；若被插入节点
 * 比min节点小，则更新min节点为被插入节点；
 * 斐波那契堆的根链表是双向 链表，这里将min节点看作双向链表的表头，在插入节点时，
 * 每次都是将节点插入到min节点之前（即插入到双链表末尾）；
 * 此外，对于 根链表中最小堆都是只有一个节点的情况，插入操作就演化成了双向链表的插入操作；
 * 
 * 合并操作
 * 合并操作和插入操作的原理非常类似：将一个堆的根链表插入到另一个堆的根链表上即可；
 * 简单地说，就是将两个双链表拼接成一个双链表；
 * 
 * 删除最小节点
 * 1、将要删除的最小节点的子树都直接串联在根表中；
 * 2、合并所有degree相等的树，直到没有相等的degree的树；
 * 
 * 减少节点值
 * 减少斐波那契堆中的节点的键值，其中操作的难点是：如果减少节点的键值之后破坏了
 * 斐波那契堆的“最小堆”性质，如何去维护？对一般情况进行分析：
 * 1、首先，将“被减小节点”从“它所在的最小堆”中剥离出来；然后将“该节点”关联到
 * “根链表”中；倘若被减小的节点不是一个单独的 节点，而是包含子树的树根，则是将
 * “被减小节点”为根的子树从最小堆中剥离出来，然后将该树关联到根链表中；
 * 2、接着，对“被减少节点”的原父节点进行“级联剪切”；所谓的级联剪切“，就是在
 * 被减小节点破坏了最小堆性质，并被切下来之后，再从“它的父节点”进行递归的级联剪切操作；
 * 而级联操作的具体动作则是：若父节点（被减小节点的父节点）的markd标记为false，则将其设为true,
 * 然后退出；否则（即marked标记为true），将父节点从最小堆中切下来（方式与“切被减小节点的方式”一样）
 * ，然后递归对祖父节点进行级联剪切；
 * marked标记的作用就是用来标记“该节点的子节点是否有被删除过”，它的作用是来实现级联剪切的，
 * 而级联剪切的目的是为了防止“最小堆”由二叉树演化成链表；
 * 3、最后，对根链表的最小节点进行更新；
 * 
 * 增加节点值
 * 操作的难点也是如何维护其“最小堆”的性质，思路如下：
 * 1、将“被增加节点“的左孩子和左孩子的所有兄弟都链接到根链表中；
 * 2、接下来，把被增加节点添加到链表中，对其进行级联剪切；
 * 
 * 删除节点
 * 1、先将被删除节点的键值减小，减小后值要比原最小节点的值小即可；
 * 2、删除最小节点
 * 
 * 
 * 
 * @author Stargazer
 * @date 2017-03-25
 */

public class FibHeap {

	private int keyNum;				//堆中节点的总数
	private FibNode min;			//最小节点（某个最小堆的根节点）
	
	private class FibNode{
		int key;								//键值
		int degree;						//度数
		FibNode left;					//左兄弟
		FibNode right;					//右兄弟
		FibNode child;					//第一个孩子节点
		FibNode parent;				//父节点
		boolean marked;				//是否被删除第一个孩子
		
		public FibNode(int key){
			this.key = key;
			this.degree = 0;
			this.marked = false;
			this.right = this;
			this.left = this;
			this.parent = null;
			this.child = null;
		}
	}
	
	public FibHeap(){
		this.keyNum = 0;
		this.min = null;
	}
	
	/**
	 *将node从双链表中移除
	 */
	private void removeNode(FibNode node){
		node.left.right = node.right;
		node.right.left = node.left;
	}
	
	/**
	 * 将node堆节点就如root节点的前面（循环链表中）
	 */
	private void addNode(FibNode node, FibNode root){
		node.left = root.left;
		root.left.right = node;
		node.right = root;
		root.left = node;
	}
	
	/**
	 * 将节点node插入到斐波那契堆中
	 */
	private void insert(FibNode node){
		if(keyNum == 0)
			min = node;
		else{
			addNode(node, min);
			if(node.key < min.key)
					min = node;
		}
		
		keyNum++;
	}
	
	/**
	 * 新建键值为key的节点，并将其插入到斐波那契堆中
	 */
	public void insert(int key){
		FibNode node;
		node = new FibNode(key);
		
		if(node == null)
			return ;
		
		insert(node);
	}
	
	/**
	 * 将双链表b链接到双链表a的后面
	 */
	private void catList(FibNode a, FibNode b){
		FibNode tmp;
		
		tmp = a.right;
		a.right = b.right;
		b.right.left = a;
		b.right = tmp;
		tmp.left = b;
	}
	
	/**
	 * 将other合并到当前堆中
	 */
	public void union(FibHeap other){
		if(other == null)
			return ;
		
		if((this.min) == null){
			//this无最小节点
			this.min = other.min;
			this.keyNum = other.keyNum;
			other = null;
		}else if((other.min) == null){
			//this有最小节点，other无最小节点
			other = null;
		}else{
			//this有最小节点，other有最小节点
			
			//将other中根链表添加到this中
			catList(this.min, other.min);
			
			if(this.min.key > other.min.key)
				this.min = other.min;
			
			this.keyNum = other.keyNum;
			other = null;
		}
	}
	
	/**
	 * 将堆的最小节点从根链表中移除
	 * 这意味着将最小节点所属的树从堆中移除
	 */
	private FibNode extractMin(){
		FibNode p = min;
		
		if(p == p.right)
			min = null;
		else{
			removeNode(p);
			min = p.right;
		}
		p.left = p.right = p;
		
		return p;
	}
	
	/**
	 * 将node链接到root根节点
	 */
	private void link(FibNode node, FibNode root){
		//将node从双链表中移除
		removeNode(node);
		//将node设为root的孩子
		if(root.child == null)
			root.child = node;
		else
			addNode(node, root.child);
		
		node.parent = root;
		root.degree++;
		node.marked = false;
	}
	
	/**
	 * 合并斐波那契堆的根链表中左右相同度数的树
	 */
	private void consolidate(){
		//计算log2(keyNum)，floor意味着向上取整
		int maxDegree = (int)Math.floor(Math.log(keyNum)/Math.log(2.0));
		
		int D = maxDegree + 1;
		FibNode[] cons = new FibNode[D+1];
		
		for(int i = 0; i < D; i++){
			cons[i] = null;
		}
		
		//合并相同度数的根节点，使每个度数的树唯一
		while(min != null){
			//取出堆中的最小数（最小节点所在的树）
			FibNode x = extractMin();
			//获取最小树的度数
			int d = x.degree;
			//cons[d] != null		//意味着有两棵树（x和D）的度数相同
			while(cons[d] != null){
				//y是与x的度数相同的树
				FibNode y = cons[d];
				if(x.key > y.key){
					//保证x的键值比y小
					FibNode tmp = x;
					x = y;
					y = tmp;
				}
				
				link(y, x);			//将y链接到x中
				cons[d] = null;
				d++;
			}
			cons[d] = x;
		}
		
		min = null;
		
		//将cons中的节点重新加到根链表中
		for(int i = 0; i < D; i++){
			if(cons[i] != null){
				if(min == null){
					min = cons[i];
				}else{
					addNode(cons[i], min);
					if((cons[i]).key < min.key)
						min = cons[i];
				}
			}
		}
			
	}
	
	/**
	 * 移除最小节点
	 */
	public void removeMin(){
		if(min == null)
			return;
		
		FibNode m = min;
		//将min每一个儿子（儿子和儿子的兄弟）都添加到斐波那契堆的根链表中
		while(m.child != null){
			FibNode child = m.child;
			
			removeNode(child);
			if(child.right == child)
				m.child = null;
			else
				m.child = child.right;
			
			addNode(child, min);
			child.parent = null;
		}
		
		//将m从根链表中移除
		removeNode(m);
		//若m是堆中唯一节点，则设置对的最小节点为null
		//否则，设置堆的最小节点为一个非空节点（m.right)，然后再进行调节
		if(m.right == m)
			min = null;
		else{
			min = m.right;
			consolidate();
		}
		keyNum--;
		
		m = null;
	}
	
	/**
	 * 获取斐波那契堆中的最小键值，失败返回-1
	 */
	public int minimum(){
		if(min == null)
			return -1;
		
		return min.key;
	}
	
	/**
	 * 修改度数
	 */
	private void renewDegree(FibNode parent, int degree){
		parent.degree -= degree;
		if(parent.parent != null)
			renewDegree(parent.parent, degree);
	}
	
	/**
	 * 将node从父节点parent的子链表中剥离出来，并使node成为堆的根链表中的一员
	 */
	private void cut(FibNode node, FibNode parent){
		removeNode(node);
		renewDegree(parent, node.degree);
		//node没有兄弟
		if(node == node.right)
			parent.child = null;
		else
			parent.child = node.right;
		
		node.parent = null;
		node.left = node.right = node;
		node.marked = false;
		
		//将node所在树添加到根链表中
		addNode(node, min);
	}
	
	/**
	 * 对节点node进行级联剪切
	 * 
	 * 级联剪切：如果减小后的节点破坏了最小堆性质，则把它切下来（即从其所在的双链表中删除，
	 * 					并将其插入到由最小树根节点形成的双链表中），然后在从被切节点的父节点到
	 * 					其所在树根节点递归执行级联剪切；
	 */
	private void cascadingCut(FibNode node){
		FibNode parent = node.parent;
		
		if(parent != null){
			if(node.marked == false)
				node.marked = true;
			else{
				cut(node, parent);
				cascadingCut(parent);
			}
		}
	}
	
	/**
	 * 将斐波那契堆中节点node的值减小为key
	 */
	private void decrease(FibNode node ,int key){
		if(min == null || node == null)
			return;
		
		if(key > node.key){
			System.out.println("decrease failed : the new key " + key + " is no smaller than current key " + node.key );
			return;
		}
		
		FibNode parent = node.parent;
		node.key = key;
		
		if(parent != null && (node.key < parent.key)){
			//将node从父节点parent中剥离出来，并将node添加到根链表中
			cut(node, parent);
			cascadingCut(parent);
		}
		
		//更新最小节点
		if(node.key < min.key)
			min = node;
	}
	
	/**
	 * 将斐波那契堆中节点node的值增加为key
	 */
	private void increase(FibNode node, int key){
		if(min == null || node == null)
			return;
		
		if(key <= node.key){
			System.out.println("increase failed : the new key " + key + " is no greater than current key " + node.key);
			
			return;
		}
		
		//将node每一个儿子（不包括孙子、重孙。。。）都添加到斐波那契堆的根链表中
		while(node.child != null){
			FibNode child = node.child;
			removeNode(child);			//将child从node的子链表中删除
			
			if(child.right == child)
				node.child = null;
			else
				node.child = child.right;
			
			addNode(child, min);			//将child添加到根链表中
			child.parent = null;
		}
		
		node.degree = 0;
		node.key = key;
		
		/*
		 * 如果node不在根链表中，则将node从父节点parent的子链表中剥离出来，
		 * 并使node成为堆的根链表中的一员，然后级联剪切；否则，则判断是否 需要
		 * 更新堆的最小节点
		 */
		FibNode parent = node.parent;
		if(parent != null){
			cut(node, parent);
			cascadingCut(parent);
		}else if(min == node){
			FibNode right = node.right;
			while(right != node){
				if(node.key > right.key)
					min = right;
				
				right = right.right;
			}
		}
	}
	
	
	/**
	 * 更新斐波那契堆的节点node的键值key
	 */
	private void update(FibNode node, int key){
		if(key < node.key)
			decrease(node, key);
		else if(key > node.key)
			increase(node, key);
		else
			System.out.println("No need to update!");
	}
	
	/**
	 * 跟新节点的键值
	 */
	public void update(int oldKey, int newKey){
		FibNode node;
		
		node = search(oldKey);
		if(node != null){
			update(node, newKey);
		}
	}
	
	/**
	 * 
	 * 在最小堆root中查找键值为key节点
	 */
	private FibNode search(FibNode root, int key){
		FibNode t = root;			//临时节点
		FibNode p = null;				//要查找的节点
		
		if(root == null)
			return root;
		
		do{
			if(t.key == key){
				p = t;
				break;
			}else{
				if((p = search(t.child, key)) != null){
					break;
				}
				
			}
			t = t.right;
		}while(t != root);
			
		return p;
		
	}
	
	/**
	 * 在斐波那契堆中查找键值为key的节点
	 */
	private FibNode search(int key){
		if(min == null)
			return null;
		
		return search(min, key);
	}
	
	/**
	 * 在斐波那契堆中是否存在键值为key的节点：存在返回true,否则返回false
	 */
	public boolean contains(int key){
		return search(key) != null ? true : false;
	}
	
	/**
	 * 删除节点node
	 */
	private void remove(FibNode node){
		int m = min.key;
		decrease(node, m-1);
		removeMin();
	}
	
	public void remove(int key){
		if(min == null)
			return;
		
		FibNode node = search(key);
		if(node == null)
			return ;
		
		remove(node);
	}
	
	/**
	 * 销毁斐波那契堆
	 */
	private void destroyNode(FibNode node){
		if(node == null)
			return ;
		
		FibNode start = node;
		do{
			destroyNode(node.child);
			//销毁node,并将node指向下一个
			node = node.right;
			node.left = null;
		}while(node != start);
	}
	
	/**
	 * 销毁斐波那契堆
	 */
	public void destroy(){
		destroyNode(min);
	}
	
	/**
	 * 打印斐波那契堆
	 */
	private void print(FibNode node, FibNode prev, int direction){
		FibNode start = node;
		
		if(node == null)
			return ;
		
		do{
			if(direction == 1)
				System.out.println(node.key + " is " + prev.key + "'s chlld.");
			else
				System.out.println(node.key + " is " + prev.key + "'s next.");
			
			if(node.child != null)
				print(node.child, node, 1);
			
			//兄弟节点
			prev = node;
			node = node.right;
			direction = 2;
		}while(node != start);
	}
	
	/**
	 * 打印
	 */
	public void print(){
		if(min == null)
			return;
		
		int i = 0;
		FibNode p = min;
		System.out.println("===========斐波那契堆的详细信息为：===========");
		
		do{
			i++;
			System.out.println(p.key + " is root.");
			print(p.child, p, 1);
			p = p.right;
		}while(p != min);
		
		System.out.println("=======================================");
	}
}
