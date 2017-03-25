package graphs;

import java.io.IOException;
import java.util.Scanner;

/**
 * 图
 * 定义：图是由一些点(vertex)和这些点之间的连线(edge)所组成的；其中，点通常被称为顶点vertex；
 * 			 而点与点之间的连线则被称为边或弧edge；通常记为，G=(V, E)；
 * 
 * 图的种类：根据边是否有方向，将图划分为无向图和有向图；
 * 
 * 邻接点：一条边上的两个顶点叫做邻接点；
 * 在有向图中，除了邻接点之外，还有“入边”和“出边”的概念；
 * 顶点的入边：是指以该顶点为终点的边；
 * 顶点的出边：则是值以该顶点为起点的边；
 * 
 * 度：
 * 在无向图中，某个顶点的度是邻接到该顶点的边的数目；
 * 在有向图中，度有“入度”和“出度”之分；
 * 有向图中，某个顶点的入度：是指以该顶点为终点的边的数目；
 * 有向图中，某个顶点的出度：则是指以该顶点为起点的边的数目；
 * 顶点的度 = 入度 + 出度；
 * 
 * 路径：如果顶点Vm到顶点Vn之间存在一个顶点序列，则表示Vm到Vn是一条路径；
 * 路径长度：路径中“边的数量”；
 * 简单路径：若一条路径上顶点不重复出现，则这条路径是简单路径；
 * 回路：若路径的第一个顶点和最后一个顶点相同，则这条路径是回路；
 * 简单回路：第一个顶点和最后一个顶点相同，除此之外，其它各顶点都不重复的回路是简单回路；
 * 
 * 连通图：对无向图而言，任意两个顶点之间都存在一条无向路径，则该无向图为连通图；
 *				对有向图而言，若图中任意两个顶点之间都存在一条有向路径，则称该有向图为强连通图；
 * 联通分量：非连通图中的各个连通子图称为该图的连通分量；
 * 
 * 图的存储结构，常用的是“邻接表”和“邻接矩阵”；
 * 
 * 邻接矩阵：是指用矩阵来表示图；它是采用矩阵来描述图中顶点之间的关系（以及边的权）；
 * 假设图中的顶点数位n，则邻接矩阵的定义为：
 * A[i][j]
 * 若A[i][j]=1，则说明Vi和Vj之间有边存在；若A[i][j]=0，则说明Vi和Vj之间没有边的存在
 * 通常采用两个数组来实现邻接矩阵：一个一维数组用来保存顶点信息，一个二维数组用来保存边的信息；
 * 邻接矩阵的缺点是比较耗费空间；
 * 
 * 邻接表：是图的一种链式存储表示方法，它是改进后的邻接矩阵；
 * 邻接表的缺点是不方便判断两个顶点之间是否有边，但是相对邻接矩阵来说更节省空间；
 * 
 * 
 * 邻接矩阵无向图
 * 是指通过邻接矩阵来表示的无向图
 * 
 * @author Stargazer
 * @date 2017-03-25
 */

public class MatrixUDG {
	//邻接矩阵无向图
	
	private char[] mVexs;			//顶点集合
	private int[][] mMatrix;			//邻接矩阵，二维数组，保存具体的无向图的顶点与边的信息
	
	/**
	 * 创建图（自己输入数据）
	 */
	public MatrixUDG(){
		//输入顶点数和边数
		System.out.println("input vertex number : ");
		int vlen = readInt();
		System.out.println("input edge number : ");
		int elen = readInt();
		if(vlen < 1 || elen < 1 || (elen > (vlen*(vlen-1)))){
			System.out.println("input error : invalid parameters!");
			return;
		}
		
		//初始化顶点
		mVexs = new char[vlen];
		for(int i = 0; i < mVexs.length; i++){
			System.out.print("vertex : " + i);
			mVexs[i] = readChar();
		}
		
		//初始化边
		mMatrix = new int[vlen][vlen];
		for(int i = 0; i < elen; i++){
			//读取边的起始顶点和结束顶点
			System.out.print("edge : " + i);
			char c1 = readChar();
			char c2 = readChar();
			int p1 = getPosition(c1);
			int p2 = getPosition(c2);
			
			if(p1 == -1 || p2 == -1){
				System.out.println("input error : invalid edge!");
				return;
			}
			
			mMatrix[p1][p2] = 1;
			mMatrix[p2][p1] = 1;
		}
	}
	
	/**
	 * 创建图（自己提供的矩阵）
	 * 
	 * @param vexs  		顶点数组
	 * @param edges 	边数组
	 */
	public MatrixUDG(char[] vexs, char[][] edges){
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
	 * 返回ch位置
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
		char ch='0';
		
		do{
			try{
				ch = (char)System.in.read();
			}catch(IOException e){
				e.printStackTrace();
			}
		}while(!((ch>='a' && ch<='z') || (ch>='A'&&ch<='Z')));
		
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
	 * 打印矩阵队列图
	 */
	public void print(){
		System.out.println("Matrix Graph : ");
		for(int i = 0; i < mVexs.length; i++){
			for(int j = 0; j < mVexs.length; j++){
				System.out.print(mMatrix[i][j] + " ");
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
		
		MatrixUDG pg;
		
		//自定义图（通过输入矩阵队列）
		//pg = new MatrixUDG();
		
		//采用已有的图
		pg = new MatrixUDG(vexs, edges);
		
		pg.print();
		}

}
