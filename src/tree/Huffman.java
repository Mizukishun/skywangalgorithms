package tree;

/**
 * 哈弗曼树
 * 
 * 定义：给定n个权值作为n个叶子节点，构造一棵二叉树，若树的带权路径长度
 * 达到最小，则这棵树被称为哈弗曼树。
 * 
 * 路径&路径长度：
 * 在一棵树，从一个节点往下可以达到的孩子或孙子节点之间的通路，称为路径；
 * 通路中分治的数目称为路径长度；若规定根节点的层数为1，则从根节点到第L层节点的
 * 路径长度为L-1；
 * 
 * 节点的权&带权路径长度：
 * 若将树中节点赋给一个有着某种含义的数值，则这个数值称为该节点的权；
 * 节点的带权路径长度为：从根节点到该节点之间的路径长度与该节点的权的乘积；
 * 
 * 树的带权路径长度：
 * 树的带权路径长度规定为所有 叶子节点的带权路径长度之和，记为WPL;
 * 
 * 哈弗曼树的构造规则：
 * 假设有n个权值，则构造出的哈夫曼树有n个叶子节点，n个权值分别为W1,W2,...Wn；
 * 1、将W1,W2,...,Wn看成是有n棵树的森林（每棵树仅有一个节点）；
 * 2、在森林中选出根节点的权值最小的两个棵树进行合并，作为一棵新树的左、右子树，
 * 		且新树的根节点权值为其左右子树根节点权值之和；
 * 3、从森林中删除选取的两棵树，并将新树加入森林；
 * 4、重复2、3步骤，知道森林中只剩一棵树为止，该树即为所求的哈弗曼树；
 * 
 * 
 * @author Stargazer
 * @date 2017-03-22
 */

public class Huffman {

	private HuffmanNode mRoot;			//根节点
	
	/**
	 * 创建Huffman树
	 * 
	 * @param a  权值数组
	 */
	public Huffman(int[] a){
		HuffmanNode parent = null;
		MinHeap heap;
		
		//建立数组a对应的最小堆
		heap = new MinHeap(a);
		
		for(int i = 0; i < a.length - 1; i++){
			//最小节点是左孩子
			HuffmanNode left = heap.dumpFromMinimum();		
			//其次才是右孩子
			HuffmanNode right = heap.dumpFromMinimum();
			
			//新建parent节点，左右孩子分别是left/right；
			//parent的大小是左右孩子之和
			parent = new HuffmanNode(left.key+right.key, left, right, null);
			left.parent = parent;
			right.parent = parent;
			
			//将parent节点数据拷贝到最小堆中
			heap.insert(parent);
		}
		mRoot = parent;
		
		//销毁最小堆
		heap.destroy();
	}
	
	/**
	 * 前序遍历哈弗曼树
	 */
	private void preOrder(HuffmanNode tree){
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
	 * 中序遍历哈弗曼树
	 */
	private void inOrder(HuffmanNode tree){
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
	 * 后序遍历哈弗曼树
	 */
	private void postOrder(HuffmanNode tree){
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
	 * 销毁哈弗曼树
	 */
	private void destroy(HuffmanNode tree){
		if(tree == null)
			return;
		
		if(tree.left != null)
			destroy(tree.left);
		if(tree.right != null)
			destroy(tree.right);
		
		tree = null;
	}
	
	public void destroy(){
		destroy(mRoot);
		mRoot = null;
	}
	
	/**
	 * 打印哈弗曼树
	 * 
	 * @param key 		节点的键值
	 * @param direction  	0，表示该节点是根节点
	 * 									-1，表示该节点是它的父节点的左孩子
	 * 									1，表示该节点是它的父节点的右孩子 
	 */
	private void print(HuffmanNode tree, int key, int direction){
		if(tree != null){
			if(direction == 0)
				System.out.println(tree.key + " is root.");
			else
				System.out.println(tree.key + "'s child " + (direction==1?"right":"left"));
			
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
