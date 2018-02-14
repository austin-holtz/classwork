
import java.util.*;
import java.io.*;

public class Prog2AAH
{
    private static LinkedHashMap<String,String> regexes = genRegexHash();

	public static void main(String[] args)
	{
		try
		{
			String filename = args[0];
	        FileByToken fbt = new FileByToken(filename);
			String in = fbt.nextToken();
			do
			{
				checkForRegex(in);
				in = fbt.nextToken();
			}
			while (!in.equals(""));
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Please provide a filename!");
			System.exit(0);
		}	
	}

	private static String checkForRegex(String in)
	{
		for (String key : regexes.keySet())
		{
            if(in.matches(key))
            {
                
            	String output = String.format("%10s %s",regexes.get(key)+",",in);
				// String output = s+" "+in;
				System.out.println(output);   
				return "";
			}

		}
		
		System.out.format("%10s %s%n","<error>,",in);
		System.exit(0);
        return "";
	}
	
	private static LinkedHashMap<String,String> genRegexHash()
	{
		LinkedHashMap<String,String> d = new LinkedHashMap<String,String>();
		d.put("print","<print>");
		d.put("input","<input>");
        d.put("\\(","<lparen>");
        d.put("\\)","<rparen>");
        d.put("[\\+\\-]","<add_op>");
        d.put("(\\/\\/)|[\\*\\/\\%]","<mult_op>");
        d.put("(<=)|(>=)|[<>]|={2}|(!=)","<rel_op>");
        d.put("=","<assign>");
        d.put("[a-zA-z]([a-zA-Z]|\\d)*","<id>");
        d.put("(\\d+\\.\\d+)|(\\d+)","<number>");
		return d;
	}

	private static class FileByToken
	{
		private Scanner sc;
		private Scanner tokens;

		public FileByToken(String filename)
		{
			try
			{
				File f = new File(filename);
				this.sc = new Scanner(f);
				this.tokens = tokenize(fileToString());
			}catch (FileNotFoundException e)
			{
				System.out.println("File not found!");
				System.exit(0);
			}

		}

		public String nextToken()
		{
			if (this.tokens.hasNext())
				return tokens.next();
			return "";
		}

		public String fileToString(){
			String out = "";
			String temp = "";
			do
			{
				out += temp+"\n";
				temp = nextLine();
			}
			while (!temp.equals("NO MORE TOKENS"));
			return out;
		}

		public String nextLine()
		{
			try
			{
				String out = this.sc.nextLine();

				while (out.matches("#.*")||out.matches(""))
				{
					out = this.sc.nextLine();
				}

				return out;

			}catch (NoSuchElementException e)
			{
				return "NO MORE TOKENS";
			}		
		}

        private Scanner tokenize(String line)
        {
        	String[] needles = {"\\(","\\)","\\+","\\-","\\*","\\/\\/","\\/","%","<=",">=","==","!=","<",">","="};
        
        	String out = line;
		
			out = out.replaceAll("<="," @lessthanequal@ ");
			out = out.replaceAll(">="," @greaterthanequal@ ");
			out = out.replaceAll("!="," @notequal@ ");
			out = out.replaceAll("=="," @equalequal@ ");
			out = out.replaceAll("\\/\\/"," @integerdivide@ ");
		
        	for (int i = 0;i<needles.length;i++)
        	{
        		out = out.replaceAll(needles[i]," "+needles[i]+" ");
        	}
        	
			out = out.replaceAll("@lessthanequal@","<=");
			out = out.replaceAll("@greaterthanequal@",">=");
			out = out.replaceAll("@equalequal@","==");
			out = out.replaceAll("@notequal@","!=");
			out = out.replaceAll("@integerdivide@","//");

        	return new Scanner(out);
        }
	}
}
