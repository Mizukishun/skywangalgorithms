package graphs;

import java.io.IOException;
import java.util.Scanner;

/**
 * 邻接矩阵有向图
 * 
 * @author Stargazer
 * @date 2017-03-25
 */

public class MatrixDG {

	private char[] mVexs;				//顶点集合
	private int[][] mMatrix;				//邻接矩阵
	
	/**
	 * 创建图（自己输入数据）
	 */
	public MatrixDG(){
		//输入顶点数和边数
		System.out.println("input vertex number : ");
		int vlen = readInt();
		System.out.println("input edge number : ");
		int elen  =readInt();
		if(vlen < 1 || elen < 1 || (elen > (vlen * ( vlen - 1)))){
			System.out.println("input error : invalid parameter!");
			return;
		}
		
		//初始化顶点
		mVexs = new char[vlen];
		for(int i = 0; i < mVexs.length; i++){
			System.out.print("vertext " + i);
			mVexs[i] = readChar();
		}
		
		//初始化边
		mMatrix = new int[vlen][vlen];
		for(int i = 0; i < elen; i++){
			//读取边的起始顶点和结束顶点
			System.out.println("edge " + i);
			char c1 = readChar();
			char c2= readChar();
			int p1 = getPosition(c1);
			int p2 = getPosition(c2);
			
			if(p1 == -1 || p2 == -1){
				System.out.println("input error : invalid edge!");
				return ;
			}
			
			mMatrix[p1][p2] = 1;
		}
	}
	
	
	/**
	 * 创建图（利用已提供的矩阵）
	 * 
	 * @param vexs  		顶点数组
	 * @param edges 	边数组
	 */
	public MatrixDG(char[] vexs, char[][] edges){
		//初始化顶点数和边数
		int vlen = vexs.length;
		int elen = vexs.length;
		
		//初始化顶点
		mVexs = new char[vlen];
		for(int i = 0; i < mVexs.length; i++)
			mVexs[i] = vexs[i];
		
		//初始化边
		mMatrix = new int[vlen][vlen];
		for(int i = 0; i < elen; i++){
			//读取边的起始顶点和结束顶点
			int p1 = getPosition(edges[i][0]);
			int p2 = getPosition(edges[i][1]);
			
			mMatrix[p1][p2] = 1;
		}
	}
	
	/**
	 * 返回ch的位置
	 */
	private int getPosition(char ch){
		for(int i = 0; i < mVexs.length; i++){
			if(mVexs[i] == ch)
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
	 * 打印矩阵队列图
	 */
	public void print(){
		System.out.println("Matrix Graph : ");
		for(int i = 0; i < mVexs.length; i++){
			for(int j = 0; j < mVexs.length; j++)
				System.out.print(mMatrix[i][j] +  " ");
			
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
        MatrixDG pG;

        // 自定义"图"(输入矩阵队列)
        //pG = new MatrixDG();
        // 采用已有的"图"
        pG = new MatrixDG(vexs, edges);

        pG.print();   // 打印图
    }
}
