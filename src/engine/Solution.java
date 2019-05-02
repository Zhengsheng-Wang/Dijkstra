package engine;
import utils.Graph;
import java.io.File;
import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;

//engine of calculation
public class Solution {
	public static void main(String[] args) throws Exception{
		//parse the command arguments
		//if args number is not 3
		//exit this program
		if(args.length != 3){
			System.out.println("Input illegal!");
			return;
		}
		String sfileName = args[0];
		int iStartVer = 0;
		try {
			iStartVer = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
			return;
		}
		int iEndVer = 0;
		try {
			iEndVer = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}

		Scanner sc = new Scanner(new File(sfileName));
		List<String> li = new LinkedList<>();
		while (sc.hasNextLine()) {
			li.add(sc.nextLine());
		}
		Graph gr = new Graph(li);
		
		List<Integer> liRes = gr.CalculateRoute(iStartVer, iEndVer);
		
		//print out the result
		System.out.println("The route is:");
		for(Integer index : liRes){
			System.out.print(index + "->");
		}
		System.out.print("End");
	}
}
