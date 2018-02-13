// Austin Holtz Program 2 austin.a.holtz@und.edu

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.*;

public class Prog2AAH
{

    protected static LinkedHashMap<String,String> regexes = genRegexHash();

	public static void main(String[] args)
	{
        // this.regexes = genRegexHash();

		String filename = args[0];
		FileByToken fbl = new FileByToken(filename);
		String in = fbl.nextToken();
		do
		{
			checkForRegex(in);
			in = fbl.nextToken();

			// String s = checkForRegex(in);
			// if (!s.equals(""))
			// {
			// 	in = s;
			// }

   //          else
			// in = fbl.nextToken();
		}
		while (!in.equals(""));
	}

	private static String checkForRegex(String in)
	{

		// LinkedHashMap<String,String> regexes = this.regexes;
		for (String key : regexes.keySet())
		{
			// Pattern p = Pattern.compile(key);
			// Matcher m = p.matcher(in);
            // System.out.println();

            if(in.matches(key))
            {

				// String matchedStr = m.group();

                if (true)
                {
					String output = regexes.get(key)+", "+in;
					System.out.println(output);   
                }
				// int offset = m.end();
				// String remaining = in.substring(offset);
				return "";
			}

		}

		
        // System.out.print("Key: "+key);
        // System.out.println(" In->|"+in.replace(" ","_"));
		System.out.println("<error>, "+in);
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
        d.put("\\s+","");
        d.put("[a-zA-z]([a-zA-Z]|\\d)*","<id>");
        d.put("(\\d+\\.\\d+)|(\\d+)","<number>");
		return d;
	}

	private static class FileByToken extends Prog2AAH
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
			// else
			// 	System.exit(0);
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
		
		out = out.replaceAll("//"," @integerdivide@ ");
		out = out.replaceAll("<="," @lessthanequal@ ");
		out = out.replaceAll(">="," @greaterthanequal@ ");
		out = out.replaceAll("!="," @notequal@ ");
		out = out.replaceAll("=="," @equalequal@ ");
		
                
	

        	for (int i = 0;i<needles.length;i++)
        	{
        		out = out.replaceAll(needles[i]," "+needles[i]+" ");
        	}
        	
        	out = out.replaceAll("@integerdivide@","//");
		out = out.replaceAll("@lessthanequal@","<=");
		out = out.replaceAll("@greaterthanequal@",">=");
		out = out.replaceAll("@equalequal@","==");
		out = out.replaceAll("@notequal@","!=");


        	return new Scanner(out);
        }


	}
}
