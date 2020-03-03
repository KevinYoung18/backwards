import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Stack;

public class Backwards 
{

	public static void main(String[] args) throws FileNotFoundException 
	{
		long time = System.nanoTime();
		
		singleThread("doi.txt", "backwards.txt");
		
		System.out.println("\nExecution time(ms): " +(System.nanoTime() - time)/1000000);
	}
	
	public static void singleThread(String inputFile, String outputFile) throws FileNotFoundException
	{
		
		Scanner input = new Scanner(new File(inputFile));
		PrintWriter output = new PrintWriter(outputFile);
		Stack<String> wordStack = new Stack<String>();
		
		while(input.hasNext())
		{
			wordStack.push(input.next());
		}
		while(!wordStack.empty())
		{
			output.print(wordStack.pop() + " ");
		}
		
		
		output.close();
		input.close();
	}
	public static void multiThread()
	{
		
	}
}
