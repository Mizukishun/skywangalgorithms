package graphs;

import java.io.IOException;
import java.util.Scanner;

/**
 * 邻接表实现的无向图
 * 主要关注DFS和BFS算法的实现
 * 
 * @author Stargazer
 * @date 2017-04-05
 */

public class DBFSListUDG {

	/**
	 * 邻接表中表对应的链表的顶点
	 */
	private class ENode{
		int ivex;						//该边所指向的顶点的位置
		ENode nextEdge;			//指向下一条弧的指针
	}
	
	/**
	 * 邻接表中表的顶点
	 */
	private class VNode {
		char data;						//顶点信息
		ENode firstEdge;			//指向第一条依附该顶点的弧
	}
	
	private VNode[] mVexs;	//顶点数组
	
	/**
	 * 创建图（自己输入数据）
	 */
	public DBFSListUDG(){
		//输入顶点数和边数
		System.out.println("Input vertex number : ");
		int vlen = readInt();
		System.out.println("Input edge number : ");
		int elen = readInt();
		
		if(vlen < 1 || elen < 1 || (elen > (vlen*(vlen-1)))){
			System.out.println("Input error : invalid parameters!");
			return ;
		}
		
		//初始化顶点
		mVexs = new VNode[vlen];
		for(int i = 0; i < mVexs.length; i++){
			System.out.println("vertex(" + i + ")");
			mVexs[i] = new VNode();
			mVexs[i].data = readChar();
			mVexs[i].firstEdge = null;
		}
		
		//初始化边
		//mMatrix = new int[vlen][vlen];
		for(int i = 0; i < elen; i++){
			//读取边的起始顶点和结束顶点
			System.out.println("edge(" + i + "):");
			char c1 = readChar();
			char c2 = readChar();
			int p1 = getPosition(c1);
			int p2 = getPosition(c2);
			
			//初始化node1
			ENode node1 = new ENode();
			node1.ivex = p2;
			//将node1链接到p1所在链表的末尾
			if(mVexs[p1].firstEdge == null){
				mVexs[p1].firstEdge = node1;
			}else{
				linkLast(mVexs[p1].firstEdge, node1);
			}
			
			//初始化node2
			ENode node2 = new ENode();
			node2.ivex = p1;
			//将node2链接到p2所在的链表的末尾
			if(mVexs[p2].firstEdge == null){
				mVexs[p2].firstEdge = node2;
			}else{
				linkLast(mVexs[p2].firstEdge, node2);
			}
		}
	}
	
	/**
	 * 创建图（用已提供的数据）
	 * 
	 * @param vexs		顶点数组
	 * @param edges		边数组
	 */
	public DBFSListUDG(char[] vexs, char[][] edges){
		//初始化顶点数和边数
		int vlen = vexs.length;
		int elen = edges.length;
		
		//初始化顶点
		mVexs = new VNode[vlen];
		for(int i = 0; i < mVexs.length; i++){
			mVexs[i] = new VNode();
			mVexs[i].data = vexs[i];
			mVexs[i].firstEdge = null;
		}
		
		//初始化边
		for(int i = 0; i < elen; i++){
			//读取边的起始顶点和结束顶点
			char c1 = edges[i][0];
			char c2 = edges[i][1];
			
			//读取边的起始顶点和结束顶点的位置
			int p1 = getPosition(edges[i][0]);
			int p2 = getPosition(edges[i][1]);
			
			//初始化node1
			ENode node1 = new ENode();
			node1.ivex = p2;
			//将node1链接到p1所在的链表的末尾
			if(mVexs[p1].firstEdge == null){
				mVexs[p1].firstEdge = node1;
			}else{
				linkLast(mVexs[p1].firstEdge, node1);
			}
			
			//初始化node2
			ENode node2 = new ENode();
			node2.ivex = p1;
			//将node2链接到p2所在的链表的末尾
			if(mVexs[p2].firstEdge == null){
				mVexs[p2].firstEdge = node2;
			}else{
				linkLast(mVexs[p2].firstEdge, node2);
			}
			
			
		}
	}
	
	/**
	 * 将node节点链接到list的最后
	 */
	private void linkLast(ENode list, ENode node){
		ENode p = list;
		
		while(p.nextEdge != null){
			p = p.nextEdge;
		}
		p.nextEdge = node;
	}
	
	/**
	 * 返回ch的位置
	 */
	private int getPosition(char ch){
		for(int i = 0; i < mVexs.length; i++){
			if(mVexs[i].data == ch){
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
				ch = (char)System.in.read();
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}while(!((ch>='a' && ch<='z') || (ch>='A' && ch<='Z')));
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
	 * 深度优先搜索遍历图的递归实现
	 */
	private void DFS(int i, boolean[] visited){
		ENode node;
		
		visited[i] = true;
		System.out.print(mVexs[i].data + " ");
		node = mVexs[i].firstEdge;
		while(node != null){
			if(!visited[node.ivex]){
				DFS(node.ivex, visited);
			}
			node = node.nextEdge;
		}
	}
	
	/**
	 * 深度优先搜索遍历图
	 */
	public void DFS(){
		//顶点访问标记
		boolean[] visited = new boolean[mVexs.length];
		
		//初始化所有顶点为都没有被访问过
		for(int i = 0; i < mVexs.length; i++){
			visited[i] = false;
		}
		
		System.out.println("DFS : ");
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
	public void  BFS(){
		int head = 0;
		int rear = 0;
		//辅助队列
		int[] queue = new int[mVexs.length];
		//顶点访问标记
		boolean[] visited = new boolean[mVexs.length];
		
		//初始化所有顶点的标记为未被访问过的
		for(int i = 0; i < mVexs.length; i++){
			visited[i] = false;
		}
		
		System.out.println("BFS : ");
		for(int i = 0; i < mVexs.length; i++){
			if(!visited[i]){
				visited[i] = true;
				System.out.print(mVexs[i].data + " ");
				//入队列
				queue[rear++] = i;
			}
			
			while(head != rear){
				//出队列
				int j = queue[head++];
				ENode node = mVexs[j].firstEdge;
				while(node != null){
					int k = node.ivex;
					if(!visited[k]){
						visited[k] = true;
						System.out.print(mVexs[k].data + " ");
						queue[rear++] = k;
					}
					node = node.nextEdge;
				}
			}
		}
		System.out.println("");
	}
	
	/**
	 * 打印矩阵链表图
	 */
	public void print(){
		System.out.println("List Graph : ");
		for(int i = 0; i < mVexs.length; i++){
			System.out.print(i + "(" + mVexs[i].data + ") : ");
			ENode node = mVexs[i].firstEdge;
			while(node != null){
				System.out.print(node.ivex + "(" + mVexs[node.ivex].data + ")");
				node = node.nextEdge;
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
		
		DBFSListUDG pG;
		
		//自定义图（也即自己输入数据）
		//pG = new DBFSListUDG();
		//采用已有的图
		pG = new DBFSListUDG(vexs, edges);
		
		pG.print();
		pG.DFS();
		pG.BFS();
	}
}
