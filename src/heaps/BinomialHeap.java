package heaps;


/**
 * 二项堆
 * 
 * 二项堆是二项树的集合；
 * 
 * 二项树
 * 二项树是一种递归定义的有序树，它的递归定义为：
 * 1、二项树B0只有一个节点；
 * 2、二项树Bk由两棵二项树B(k-1)组成的，其中一棵树是另一棵树根的最左孩子；
 * 
 * （B0只有一个节点；B1由两个B0组成；B2由两个B1组成；B3由两个B2组成；B4由两个B3组成；
 * 当两棵相同的二项树组成另一棵树时，其中一棵树是另一棵树的最左孩子）
 * 
 * 二项树的性质：
 * 1、Bk共有（2^k)个节点；
 * 2、Bk的高度为k;
 * 3、Bk在深度i处恰好有C(k, i)个节点，其中C(k, i)是阶乘函数；
 * 4、根的度数为k，它大于任何其它节点的度数；
 * 
 * 节点的度数就是该节点所拥有的子树的数目；
 * 
 * 二项堆是指满足以下性质的二项树的集合：
 * 1、每棵二项树都满足最小堆性质。即父节点的键值 <= 它的孩子的键值；
 * 2、不能有两棵或以上的二项树具有相同的度数（包括度数为0的）；也即具有度数k的二项树只有0个或1个；
 * 
 * 
 * 合并两个二项堆：
 * 1、将两个二项堆的根链表合并成一个链表，合并后的新链表按照节点的度数单调递增排列；
 * 2、将新链表中根节点度数相同的二项树连接起来，直到所有根节点的度数都不相同；
 * 
 * 在combine(h1,h2)中对h1和h2进行合并时：首先通过merge(h1, h2)将h1和h2的根链表合并成一个
 * “按节点的度数单调递增”的链表；然后进行while循环，对合并得到的新链表进行遍历，将新链表
 * 中的“根节点度数相同的二项树”连接起来，直到所有根节点度数都不相同为止。
 * 
 * 在将新链表中“根节点度数相同的二项树”连接起来时，可以将被 连接的情况概括为4种：
 * x是根链表的当前节点，next_x是x的下一个（兄弟）节点：
 * Case 1：x->degree == next_x->degree
 * 				即：“当前节点的度数”与“下一个节点的度数”相等时，此时，不需要执行任何操作，继续查看后面的节点；
 * Case 2：x->degree == next_x->degree == next_x->next->degree
 * 				即：“当前节点的度数”、“下一节点的度数”和“下下一个节点的度数”都相等时，此时，暂时不执行任何
 * 				操作，继续查看后面的节点；实际上这里是将“下一个节点”和“下下一个节点”等到后面再来进行整合连接；
 * Case 3：x->degree == next_x->degree != next_x->next->degree && x->key <= next_x->key
 * 				即：“当前节点的度数”与“下一个节点的度数 ”相等，并且“当前节点的键值key" <= "下一个节点的度数”；
 * 				此时，将“下一个节点（对应的二项树）"作为”当前节点（对应的二项树）"的左孩子；
 * Case 4：x->degree == next_x->degree != next_x->next->degree && x->key > next_x->key
 * 				即：“当前节点的度数”与“下一个节点的度数”相等，并且“当前节点的键值key" > "下一个节点的度数”，
 * 				此时，将“当前节点（对应的二项树）”作为“下一个节点（对应的二项树）”的左孩子；
 * 
 * 
 * 插入操作：可以将插入操作看作是将“要插入的节点”和当前已有的堆进行合并；
 * 
 * 删除操作：
 * 删除二项堆中的某个节点，需要的步骤：
 * 1、将“该节点”交换到“它所在的二项树”的根节点位置；方法是，从“该节点”开始不断向上
 * 		（即向树根方向）遍历，不断交换父节点和子节点的数据，直到要被删除的键值到达树根的位置；
 * 2、将“该节点所在的二项树“从二项堆中移除；将该二项堆记为heap;
 * 3、将“该节点所在的二项树”进行反转；这里反转的意思是：将根的所有孩子独立处理，并将这些
 * 		孩子整合成二项堆，将该二项堆记为child；
 * 4、将child和heap进行合并操作
 * 
 * 删除的总的思想：就是将被删节点从它所在的二项树中孤立出来，然后再对二项树进行相应的处理；
 * 
 * 更新操作：更新二项堆中的某个节点，就是修改该节点的值，因修改之后可能破坏二项堆中二项树要是最小堆的性质，
 * 所以需要进行相应的调整：
 * 1、若是减少节点的值：该节点一定位于某一棵二项树中，减少 二项树中某个节点的值后，就需要保证
 * 		该二项树仍然是一个最小堆；因此，就需要我们不断的将该节点上调；
 * 2、若是增加节点的值：则是将增加值的节点不断的下调，从而保证被增加值的节点所在的二项树的最小堆性质；
 * 
 * 更新的总的思想：就是保持被更新节点所在二项树的最小堆性质
 * 
 * 
 * 
 * @author Stargazer
 * @date 2017-03-24
 */
public class BinomialHeap<T extends Comparable<T>> {
	
	private BinomialNode<T> mRoot;			//根节点
	
	private class BinomialNode<T extends Comparable<T>>{
		T key;									//键值
		int degree;							//度数
		BinomialNode<T> child;		//左孩子
		BinomialNode<T> parent;	//父节点
		BinomialNode<T> next;		//兄弟节点，（根链表中的下一节点？）
		
		public BinomialNode(T key){
			this.key = key;
			this.degree = 0;
			this.child = null;
			this.parent = parent;
			this.next = next;
		}
		
		public String toString(){
			return "key : " + key;
		}
	}
	
	public BinomialHeap(){
		mRoot = null;
	}
	
	/**
	 * 获取二项堆中的最小节点的键值
	 */
	public T minimum(){
		if(mRoot == null)
			return null;
		
		BinomialNode<T> x, prev_x;			//x是用来遍历的当前节点
		BinomialNode<T> y, prev_y;				//y是最小节点
		
		prev_x = mRoot;
		x = mRoot.next;
		prev_y = null;
		y = mRoot;
		
		//找到最小节点
		while(x != null){
			if(x.key.compareTo(y.key)< 0){
				y = x;
				prev_y = prev_x;
			}
			prev_x = x;
			x = x.next;
		}
		
		return y.key;
	}
	
	/**
	 * 合并两个二项堆，将child合并到root中
	 */
	private void link(BinomialNode<T> child, BinomialNode<T> root){
		child.parent = root;
		child.next = root.child;
		root.child = child;
		root.degree++;
	}
	
	/**
	 * 将h1,h2中的根链表合并成一个按度数递增的链表，返回合并后的根节点
	 */
	private BinomialNode<T> merge(BinomialNode<T> h1, BinomialNode<T> h2){
		if(h1 == null)
			return h2;
		if(h2 == null)
			return h1;
		
		//root是新堆的根，h3用来遍历h1和h2的
		BinomialNode<T> pre_h3, h3, root=null;
		
		pre_h3 = null;
		
		//整个while循环中,h1,h2,pre_h3,h3都在往后顺移
		while((h1 != null) && (h2 != null)){
			
			if(h1.degree <= h2.degree){
				h3 = h1;
				h1 = h1.next;
			}else{
				h3 = h2;
				h2 = h2.next;
			}
			
			if(pre_h3 == null){
				pre_h3 = h3;
				root = h3;
			}else{
				pre_h3.next = h3;
				pre_h3 = h3;
			}
			
			if(h1 != null){
				h3.next = h1;
			}else{
				h3.next = h2;
			}
		}
		return root;
	}
	
	/**
	 * 合并二项堆，将h1，h2合并成一个堆，并返回合并后的堆
	 */
	private BinomialNode<T> union(BinomialNode<T> h1, BinomialNode<T> h2){
		BinomialNode<T> root;
		
		//将h1,h2中的根链表合并成一个按度数递增的链表root
		root = merge(h1, h2);
		if(root == null)
			return null;
		
		BinomialNode<T> prev_x = null;
		BinomialNode<T> x = root;
		BinomialNode<T> next_x = x.next;
		
		while(next_x != null){
			if((x.degree != next_x.degree) || (next_x.next != null) && (next_x.degree == next_x.next.degree)){
				//Case 1 : x.degree != next_x.degree
				//Case 2 : x.degree == next_x.degree == next_x.next.degree
				prev_x = x;
				x = next_x;
			}else if(x.key.compareTo(next_x.key) <= 0){
				//Case 3 : x.degree == next_x.degree != next_x.next_degree && x.key <= next_x.key
				x.next = next_x.next;
				link(next_x, x);
			}else{
				//Case 4 : x.degree == next_x.degree != next_x.next.degree && x.key > next_x.key
				if(prev_x == null){
					root = next_x;
				}else{
					prev_x.next = next_x;
				}
				link(x, next_x);
				x = next_x;
			}
			
			next_x = x.next;
		}
		
		return root;
		
	}
	
	/**
	 * 将二项堆other合并到当前堆中
	 */
	public void union(BinomialHeap<T> other){
		if(other != null && other.mRoot != null){
			mRoot = union(mRoot, other.mRoot);
		}
	}
	
	/**
	 * 新建键值为key的节点，并将其插入到二项堆中
	 */
	public void insert(T key){
		BinomialNode<T> node;
		
		//禁止插入相同的键值
		if(contains(key) == true){
			System.out.println("insert failed: the key is existed already!");
			
			return;
		}
		
		node = new BinomialNode<T>(key);
		if(node == null)
			return ;
		
		mRoot = union(mRoot, node);
	}
	
	/**
	 * 反转二项堆root,并返回反转后的根节点
	 */
	private BinomialNode<T> reverse(BinomialNode<T> root){
		BinomialNode<T> next;
		BinomialNode<T> tail = null;
		
		if(root == null){
			return root;
		}
		
		root.parent = null;
		
		while(root.next != null){
			next = root.next;
			root.next = tail;
			tail = root;
			root = next;
			root.parent = null;
		}
		
		root.next = tail;
		
		return root;
	}
	
	/**
	 * 移除二项堆root中的最小节点，并返回删除节点后的二项树
	 */
	private BinomialNode<T> extractMinimum(BinomialNode<T> root){
		if(root == null)
			return root;
		
		BinomialNode<T> x, prev_x;			//x是用来遍历的当前节点
		BinomialNode<T> y, prev_y;			//y是最小节点
		
		prev_x = root;
		x = root.next;
		prev_y = null;
		y = root;
		
		//找到最小节点
		while(x != null){
			if(x.key.compareTo(y.key) < 0){
				y = x;
				prev_y = prev_x;
			}
			prev_x = x;
			x = x.next;
		}
		
		if(prev_y == null)
			root = root.next;			//root的根节点就是最小根节点
		else
			prev_y.next = y.next;
		
		//反转最小节点的左孩子，得到最小堆child
		//这样，就使得最小节点所在二项树的孩子们都脱离出来成为一棵独立的二项树（不包括最小节点）
		BinomialNode<T> child = reverse(y.child);
		
		//将删除最小节点的二项堆child和root进行合并
		root = union(root, child);
		
		y = null;
		
		return root;
	}
	
	public void exxtractMinimum(){
		mRoot = extractMinimum(mRoot);
	}
	
	/**
	 * 减少节点的键值，将二项堆中的节点node的键值减小为key
	 */
	private void decreaseKey(BinomialNode<T> node, T key){
		if(key.compareTo(node.key) > 0 || contains(key) == true){
			System.out.println("decrease failed : the new key(" + key + ") is existed already, or is no smaller than current key(" + node.key + ")");
			return;
		}
		
		node.key = key;
		
		BinomialNode<T> child, parent;
		child = node;
		parent = node.parent;
		
		while(parent != null && child.key.compareTo(parent.key) < 0){
			//交换parent和child的数据
			T tmp = parent.key;
			parent.key = child.key;
			child.key = tmp;
			
			child = parent;
			parent = child.parent;
		}
	}
	
	/**
	 * 增加节点的键值，将二项堆中的节点node的键值增加为key
	 */
	private void increaseKey(BinomialNode<T> node, T key){
		if(key.compareTo(node.key) <= 0 || contains(key) == true){
			System.out.println("increase failed : the new key(" + key + ") is existed already, or is no greater than current key (" +  node.key + ")");
			
			return ;
		}
		
		node.key = key;
		
		BinomialNode<T> cur = node;
		BinomialNode<T> child = cur.child;
		
		while(child != null){
			
			if(cur.key.compareTo(child.key) >0){
				/*
				 * 如果当前节点<它的左孩子，则在它的孩子（左孩子和左孩子的兄弟），
				 * 找出最小节点，然后将最小节点的键值和当前节点的键值进行互换
				 */
				BinomialNode<T> least = child;		//least是child和它的兄弟中的最小节点
				while(child.next != null){
					if(least.key.compareTo(child.next.key) > 0){
						least = child.next;
					}
					child = child.next;
				}
				
				//交换最小节点和当前节点的键值
				T tmp = least.key;
				least.key = cur.key;
				cur.key = tmp;
				
				//交换键值之后，再对原最小节点进行调整，使它满足最小堆的性质
				//父节点<=子节点
				cur = least;
				child = cur.child;
			}else{
				child = child.next;
			}
		}
	}
	
	/**
	 * 更新二项堆的节点node的键值为key
	 */
	private void updateKey(BinomialNode<T> node, T key){
		if(node == null)
			return ;
		
		int cmp = key.compareTo(node.key);
		if(cmp < 0)
			decreaseKey(node, key);
		else if(cmp > 0)
			increaseKey(node, key);
		else
			System.out.println("No need to upate!");
	}
	
	/**
	 * 将二项堆中键值oldkey更新为newkey
	 */
	public void update(T oldkey, T newkey){
		BinomialNode<T> node;
		
		node = search(mRoot, oldkey);
		if(node != null)
			updateKey(node, newkey);
	}
	
	/**
	 * 查找：在二项堆中查找键值为key的节点
	 */
	private BinomialNode<T> search(BinomialNode<T> root, T key){
		BinomialNode<T> child;
		BinomialNode<T> parent = root;
		
		parent = root;
		
		while(parent != null){
			if(parent.key.compareTo(key) == 0)
				return parent;
			else{
				if((child = search(parent.child, key)) != null)
					return child;
				
				parent = parent.next;
			}
		}
		
		return null;
	}
	
	/**
	 * 二项堆中是否包含键值key
	 */
	public boolean contains(T key){
		return search(mRoot, key) != null ? true : false;
	}
	
	/**
	 * 删除节点，删除键值为key的节点
	 */
	private BinomialNode<T> remove(BinomialNode<T> root, T key){
		if(root == null)
			return root;
		
		BinomialNode<T> node;
		
		//查找键值为key的节点
		if((node = search(root, key)) == null)
			return root;
		
		//将被删除的节点的数据移到它所在的二项树的根节点
		BinomialNode<T> parent = node.parent;
		
		while(parent != null){
			//交换数据
			T tmp = node.key;
			node.key = parent.key;
			parent.key = tmp;
			
			//下一个父节点
			node = parent;
			parent = node.parent;
		}
		
		//找到node的前一个根节点prev
		BinomialNode<T> prev = null;
		BinomialNode<T> pos = root;
		
		while(pos != node){
			prev = pos;
			pos = pos.next;
		}
		
		//移除node节点
		if(prev != null)
			prev.next = node.next;
		else
			root = node.next;
		
		root = union(root, reverse(node.child));
		
		node = null;
		
		return root;
	}
	
	public void remove(T key){
		mRoot = remove(mRoot, key);
	}
	
	/**
	 * 打印二项堆
	 */
	private void print(BinomialNode<T> node, BinomialNode<T> prev, int direction){
		while(node != null){
			if(direction == 1)
				System.out.println(node.key + " is " + prev.key + "'s chld.");
			else
				System.out.println(node.key + " is " + prev.key + "'s next.");
			
			if(node.child != null)
				print(node.child, node, 1);
			
			//兄弟节点
			prev = node;
			node = node.next;
			direction = 2;
		}
	}
	
	public void print(){
		if(mRoot == null)
			return ;
		
		BinomialNode<T> p = mRoot;
		System.out.println("====二项堆");
		while(p != null){
			System.out.println(p.degree + " ");
			p  = p.next;
		}
		System.out.println("的详细信息：");
		
		int i = 0;
		p = mRoot;
		while(p != null){
			i++;
			System.out.println("二项树：" + p.degree);
			
			
			print(p.child, p, 1);
			p = p.next;
		}
		
		System.out.println("");
	}
	
	

}
