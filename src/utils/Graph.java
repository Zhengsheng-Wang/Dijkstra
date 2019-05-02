package utils;

import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.io.File;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.Set;
import java.util.Stack;
import java.util.HashSet;
import java.util.Stack;

public class Graph {
	//edges
	class Edge {
		int ie;  //end port ie
		int iw;  //weight of this edge
		//ie = i; iw = j
		public Edge(int i, int j){
			ie = i;
			iw = j;
		}
		//toString
		public String toString(){
			return String.format("(%d,%d)", ie, iw);
		}
	}
	HashMap<Integer, Edge[]> mapGraph = new HashMap<>();
	
	public Graph(List<String> listr) {
		for (String s: listr) {
			List<Integer> l = ParseStr(s);
			Edge[] arrE = new Edge[(l.size() - 1) / 2];
			for(int i = 0; i != arrE.length; ++i){
				int j = 2 * i + 1;
				arrE[i] = new Edge(l.get(j), l.get(j + 1));
			}
			mapGraph.put(l.get(0), arrE);  
		}
	}
	//calculate the shortest route from iStartVer to iEndVer
	public List<Integer> CalculateRoute(int iStartVer, int iEndVer){
		Set<Integer> setAccessed = new HashSet<>();  //accessed vertexes set

		int iSizeOfGr = mapGraph.size();     //graph vertexes number
		int[] iarrLen= new int[iSizeOfGr];    //iarrLen: array of vertexes' distances from start vertex
		Arrays.fill(iarrLen, Integer.MAX_VALUE);  //initial the array by Max_value
		iarrLen[iStartVer] = 0;                //set the distance of start point from itself to 0

		int[] iarrPreVer = new int[iSizeOfGr];    //iarrPreVer: previous vertex of every vertex. No need to initialize

		ArrayDeque<Integer> queVer = new ArrayDeque<>();  //queue of vertexes!!!!!!!!!!!!!!!!!!
		queVer.add(iStartVer);         //add the start vertex to queue
		
		while(!queVer.isEmpty()){
			int iVer = queVer.pollFirst();       //get and remove the first vertex of the queue as current vertex
			setAccessed.add(iVer);        //add current vertex to the accessed set
			
			//iterate on every current's edges
			for(Edge ed : mapGraph.get(iVer)){
				//if edge ed's ending vertex is in the queue or has been accessed,
				//don't append it to the tail of this queue
				//Because the vertexes in the queue are going to be gotten as current vertex and iterated by their edges,
				//if they are already in queue(which will be gotten as current vertex in future while loop) or 
				//in accessed set(which have been dealt with), there is no need to deal with them again
				if(!queVer.contains(ed.ie) && !setAccessed.contains(ed.ie)){
					queVer.addLast(ed.ie);
				}
				
				int w = iarrLen[iVer] + ed.iw;   //temporary length of this route
				//compare temporary length of this route to current length of this, if templength is shorter, update this route
				if(w < iarrLen[ed.ie]){
					iarrLen[ed.ie] = w;
					iarrPreVer[ed.ie] = iVer;
				}
			}
			
		}

		LinkedList<Integer> liRes = new LinkedList<>();
		int it = iEndVer;
		while(it != iStartVer){
			liRes.addFirst(it);
			it = iarrPreVer[it];
		}
		liRes.addFirst(iStartVer);
		
		return liRes;
	}

	//string parser. Extract the useful info from the string s(each line of the input text)
	private List<Integer> ParseStr(String s){
		String[] sp = s.split("[,():]+");
		List<Integer> l = new LinkedList<>();

		for(int i = 0; i != sp.length; ++i){
			l.add(Integer.parseInt(sp[i]));
		}
		return l;
 	}

	//toString
	public String toString(){
		String s = new String();
		for(int i : mapGraph.keySet()){
			s += i + ":";
			for(Edge e : mapGraph.get(i)){
				s += e;
			}
			s += "\n";
		}
		return s;
	}
}
