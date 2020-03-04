import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Backwards 
{

	public static void main(String[] args) throws FileNotFoundException, InterruptedException, ExecutionException 
	{
		int numThreads = 4;
		System.out.println("Number of Processors: " + Runtime.getRuntime().availableProcessors());
		System.out.println("Number of threads: " + numThreads);
		
		long time = System.nanoTime();
		singleThread("doi.txt", "backwards.txt");
		System.out.println("\nExecution time(ms): " +(System.nanoTime() - time)/1000000);
		
		time = System.nanoTime();
		multiThread("doi.txt", "backwards.txt", numThreads);
		System.out.println("\nMultithreaded execution time(ms): " +(System.nanoTime() - time)/1000000);
	}
	
	//takes an input .txt file and writes it backwards to outputFile
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
	public static void multiThread(String inputFile, String outputFile, int numThreads) throws FileNotFoundException, InterruptedException, ExecutionException
	{
		Stack<String> wordStack = new Stack<String>();
		ExecutorService pool = Executors.newFixedThreadPool(numThreads);
		
		Scanner input = new Scanner(new File(inputFile));
		
		
		
		while(input.hasNextLine())
		{
			
			ArrayList<Future<Stack<String>>> futures = new ArrayList<Future<Stack<String>>>();
			for(int i = 0; i < numThreads && input.hasNextLine(); i++)
			{
				String str = input.nextLine();
				Callable<Stack<String>> task = () -> {
					return parseLine(str);
				};
				futures.add(pool.submit(task));
			}
			for(Future<Stack<String>> future: futures)
			{
				wordStack.addAll(future.get());
			}
		}
		input.close();
		PrintWriter output = new PrintWriter(outputFile);
		while(!wordStack.empty())
		{
			output.print(wordStack.pop() + " ");
		}
		
		pool.shutdown();
		output.close();
		
	}
	
	static Stack<String> parseLine(String line)
	{
		Stack<String> stack = new Stack<String>();
		Scanner lineScan = new Scanner(line);
		while(lineScan.hasNext())
			stack.add(lineScan.next());
		
		lineScan.close();
		return stack;
		
	}
}

