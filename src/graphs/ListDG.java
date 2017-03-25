package graphs;

import java.io.IOException;
import java.util.Scanner;

/**
 * 邻接表有向图
 * 
 * @author Stargazer
 * @date 2017-03-25
 */

public class ListDG {

	/**
	 * 邻接表中对应的链表的顶点
	 */
	private class ENode{
		int ivex;							//该边所指向的顶点的位置
		ENode nextEdge;				//指向下一条边的指针
	}
	
	/**
	 * 邻接表中表的顶点
	 */
	private class VNode{
		char data;							//顶点信息
		ENode firstEdge;				//指向第一条依附该顶点的边
	}
	
	private VNode[] mVexs;		//顶点数组
	
	/**
	 * 创建图（自己输入数据)
	 */
	public ListDG(){
		//输入顶点数和边数
		System.out.println("input vertex number : ");
		int vlen = readInt();
		System.out.println("input edge number : ");
		int elen = readInt();
		if(vlen < 1 || elen < 1 || (elen > (vlen*(vlen-1)))){
			System.out.println("input error : invalid parameter!");
			return;
		}
		
		//初始化顶点
		mVexs = new VNode[vlen];
		for(int i = 0; i < mVexs.length; i++){
			System.out.println("vertext " + i);
			mVexs[i] = new VNode();
			mVexs[i].data = readChar();
			mVexs[i].firstEdge = null;
		}
		
		//初始化边
		for(int i = 0; i < elen; i++){
			//读取边的起始顶点和结束顶点
			System.out.println("edge " + i);
			char c1 = readChar();
			char c2 = readChar();
			int p1 = getPosition(c1);
			int p2 = getPosition(c2);
			
			//初始化node1
			ENode node1 = new ENode();
			node1.ivex = p2;
			//将node2链接到p1所在的链表的末尾
			if(mVexs[p1].firstEdge == null)
				mVexs[p1].firstEdge = node1;
			else
				linkLast(mVexs[p1].firstEdge, node1);
		}
	}
	
	/**
	 * 创建图（利用已提供的矩阵）
	 * 
	 * @param vexs			顶点数组
	 * @param edges 		边数组
	 */
	public ListDG(char[] vexs, char[][] edges){
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
			int p1 = getPosition(edges[i][0]);
			int p2 = getPosition(edges[i][1]);
			
			//初始化node1
			ENode node1 = new ENode();
			node1.ivex = p2;
			//将node1链接到p1所在的链表的末尾
			if(mVexs[p1].firstEdge == null)
				mVexs[p1].firstEdge = node1;
			else
				linkLast(mVexs[p1].firstEdge, node1);
			
		}
	}
	
	/**
	 * 将node节点链接到list的末尾
	 */
	private void linkLast(ENode list, ENode node){
		ENode p = list;
		
		while(p.nextEdge != null)
			p = p.nextEdge;
		
		p.nextEdge = node;
	}
	
	/**
	 * 返回ch位置
	 */
	private int getPosition(char ch){
		for(int i = 0; i < mVexs.length; i++){
			if(mVexs[i].data == ch)
				return i;
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
			}catch(IOException e){
				e.printStackTrace();
			}
		}while(!((ch>='a'&&ch<='z') || (ch>='A'&&ch<='Z')));
		
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
	 * 打印图
	 */
	public void print(){
		System.out.println("List Graph : ");
		for(int i = 0; i < mVexs.length; i++){
			System.out.printf("%d(%c): ", i, mVexs[i].data);
			ENode node = mVexs[i].firstEdge;
			while(node != null){
				System.out.printf("%d(%c) ", node.ivex, mVexs[node.ivex].data);
				node = node.nextEdge;
			}
			
			System.out.println("");
		}
	}
	
	public static void main(String[] args) {
        char[] vexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        char[][] edges = new char[][]{
            {'A', 'B'}, 
            {'B', 'C'}, 
            {'B', 'E'}, 
            {'B', 'F'}, 
            {'C', 'E'}, 
            {'D', 'C'}, 
            {'E', 'B'}, 
            {'E', 'D'}, 
            {'F', 'G'}}; 
        ListDG pG;

        // 自定义"图"(输入矩阵队列)
        //pG = new ListDG();
        // 采用已有的"图"
        pG = new ListDG(vexs, edges);

        pG.print();   // 打印图
    }
}
