package graphs;

import java.io.IOException;
import java.util.Scanner;

/**
 * 深度优先搜索（Depth First Search, DFS）
 * 
 * 思想是：假设初始状态是图中所有顶点都未被访问，则从某个顶点V出发，首先访问该
 * 顶点，然后依次从它的各个未被访问的邻接点出发，深度优先搜素遍历图，直至图中
 * 所有和V有路径想通的顶点都被访问；若此时尚有其他顶点未被访问，则另选一个未被
 * 访问的顶点做起始点，重复上述过程，直至图中所有顶点都被访问为止；深度优先搜索
 * 是一个递归的过程；
 * 
 * 
 * 广度优先搜索（Breadth First Search, BFS）
 * 
 * 思想是：从图中某顶点V出发，在访问了V之后一次访问V的各个未曾访问过的邻接点，
 * 然后分别从这些邻接点出发再依次访问它们的邻接点，并使得“先被访问的顶点的
 * 邻接点“先于后被访问的顶点的邻接点被访问，直至图中所有已被访问的顶点的邻接点
 * 都被访问到；如果此时图中尚有顶点未被访问到，则需要另选一个未曾被访问过的顶点
 * 作为新的起始顶点，重复上述过程，直至图中所有顶点都被访问到为止；
 * 
 * 
 * 
 * @author Stargazer
 * @date 2017-04-05
 */
public class DBFSMatrixUDG {
	//邻接矩阵表示的无向图
	//这里主要关注DFS和BFS算法的实现
	
	private char[] mVexs;				//顶点集合
	private int[][] mMatrix;				//邻接矩阵
	
	/**
	 * 创建图（自己输入数据）
	 */
	public DBFSMatrixUDG(){
		//输入顶点数和边数
		System.out.println("Input vertex number: ");
		int vlen = readInt();
		System.out.println("Input edge number: ");
		int elen = readInt();
		if(vlen < 1 || elen < 1 || (elen > (vlen*(vlen-1)))){
			System.out.println("Input error : invalid parameter!");
			return;
		}
		
		//初始化顶点
		mVexs = new char[vlen];
		for(int i = 0; i < mVexs.length; i++){
			System.out.println("vertext(" + i + ")");
			mVexs[i] = readChar();
		}
		
		//初始化边
		mMatrix = new int[vlen][vlen];
		for(int i = 0; i < elen; i++){
			//读取边的起始顶点和结束顶点
			System.out.println("edge(" + i + ")");
			char c1 = readChar();
			char c2 = readChar();
			int p1 = getPosition(c1);
			int p2 = getPosition(c2);
			
			if(p1 == -1 || p2 == -1){
				System.out.println("Input error: invalid edge!");
				return;
			}
			
			mMatrix[p1][p2] = 1;
			mMatrix[p2][p1] = 1;
		}
	}
	
	/**
	 * 创建图（用已提供的矩阵）
	 * 
	 * @param vexs   		顶点数组
	 * @param edges  	边数组
	 */
	public DBFSMatrixUDG(char[] vexs, char[][]edges){
		//初始化顶点数和边数
		int vlen = vexs.length;
		int elen = edges.length;
		
		//初始化顶点
		mVexs = new char[vlen];
		for(int i = 0; i < mVexs.length; i++){
			mVexs[i] = vexs[i];
		}
		
		//初始化边
		mMatrix = new int[vlen][vlen];
		for(int i = 0; i < elen; i++){
			//读取边的起始顶点和结束顶点
			int p1 = getPosition(edges[i][0]);
			int p2 = getPosition(edges[i][1]);
			
			mMatrix[p1][p2] = 1;
			mMatrix[p2][p1] = 1;
		}
	}
	
	/**
	 * 返回ch的位置
	 */
	private int getPosition(char ch){
		for(int i = 0; i < mVexs.length; i++){
			if(mVexs[i] == ch){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 读取一个输入字符
	 */
	private char readChar(){
		char ch = '0';
		
		do{
			try{
				ch = (char) System.in.read();
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}while(!((ch >= 'a' && ch <= 'z') || (ch>='A' && ch<='Z')));
		
		return ch;
	}
	
	/**
	 * 读取一个输入字符
	 */
	private int readInt(){
		Scanner scanner = new Scanner(System.in);
		return scanner.nextInt();
	}
	
	/**
	 * 返回顶点V的第一个邻接顶点的索引，失败则返回-1
	 */
	private int firstVertex(int v){
		if(v < 0 || v > (mVexs.length-1)){
			return -1;
		}
		
		for(int i = 0; i < mVexs.length; i++){
			if(mMatrix[v][i] == 1){
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * 返回顶点v相对于w的下一个邻接顶点的索引，失败则返回-1
	 */
	private int nextVertex(int v, int w){
		if(v < 0 || v > (mVexs.length-1) || w < 0 || w > (mVexs.length-1)){
			return -1;
		}
		
		for(int i = w + 1; i < mVexs.length; i++){
			if(mMatrix[v][i] == 1){
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * 深度优先搜索遍历图的递归实现
	 */
	private void DFS(int i, boolean[] visited){
		visited[i] = true;
		System.out.println(mVexs[i]);
		//遍历该顶点的所有邻接点，若是没有访问过，则继续往下走
		for(int w = firstVertex(i); w >= 0; w = nextVertex(i, w)){
			if(!visited[w]){
				DFS(w, visited);
			}
		}
	}
	
	/**
	 * 深度优先搜索遍历图
	 */
	public void DFS(){
		//顶点访问标记
		boolean[] visited = new boolean[mVexs.length];
		
		//初始化所有顶点为都没有访问过的
		for(int i = 0; i < mVexs.length; i++){
			visited[i] = false;
		}
		
		System.out.println("DFS: ");
		for(int i = 0; i < mVexs.length; i++){
			if(!visited[i]){
				DFS(i, visited);
			}
		}
		System.out.println("");
	}
	
	/**
	 * 广度优先搜索（类似于树的层次遍历）
	 */
	public void BFS(){
		int head = 0;
		int rear = 0;
		//辅助队列
		int[] queue = new int[mVexs.length];
		//顶点访问标记
		boolean[] visited = new boolean[mVexs.length];
		
		//初始化所有顶点都为未被访问过的
		for(int i = 0; i < mVexs.length; i++){
			visited[i] = false;
		}
		
		System.out.println("BFS: ");
		for(int i = 0; i < mVexs.length; i++){
			if(!visited[i]){
				visited[i] = true;
				System.out.println(mVexs[i]);
				//入队列
				queue[rear++] = i;
			}
			
			while(head != rear){
				//出队列
				int j = queue[head++];
				//k为访问的邻接顶点
				for(int k = firstVertex(j); k >= 0; k = nextVertex(j, k)){
					if(!visited[k]){
						visited[k] = true;
						System.out.println(mVexs[k]);
						queue[rear++] = k;
					}
				}
			}
		}
		System.out.println("");
	}
	
	/**
	 * 打印矩阵队列图
	 */
	public void print(){
		System.out.println("Matrix Graph : ");
		for(int i = 0; i < mVexs.length; i++){
			for(int j = 0; j < mVexs.length; j++){
				System.out.print(mMatrix[i][j]);
			}
			System.out.println("");
		}
	}
	
	public static void main(String[] args){
		char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
		char[][] edges = new char[][]{
			{'A', 'C'},
			{'A', 'D'},
			{'A', 'F'},
			{'B', 'C'},
			{'C', 'D'},
			{'E', 'G'},
			{'F', 'G'}
		};
		
		DBFSMatrixUDG pG;
		
		//自定义图（也即自己输入矩阵队列）
		//pG = new DBFSMatrix();
		//采用已有的图
		pG = new DBFSMatrixUDG(vexs, edges);
		
		pG.print();
		pG.DFS();
		pG.BFS();
	}

}
