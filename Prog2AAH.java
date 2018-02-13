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
		FileByLine fbl = new FileByLine(filename);
		String in = fbl.nextLine();
		do
		{
			String s = checkForRegex(in);
			if (!s.equals(""))
			{
				in = s;
			}

            else
			in = fbl.nextLine();	
		}
		while (!in.equals("NO MORE TOKENS"));
	}

	private static String checkForRegex(String in)
	{

		// LinkedHashMap<String,String> regexes = this.regexes;
		for (String key : regexes.keySet())
		{
			Pattern p = Pattern.compile(key);
			Matcher m = p.matcher(in);
            // System.out.println();

            if(m.lookingAt())
            {

                if (regexes.get(key).equals("<error>"))
                {
                    break;
                }

				String matchedStr = m.group();

                if (!matchedStr.equals(" "))
                {
					String output = regexes.get(key)+", "+matchedStr;
					System.    out.println(output);   
                }
				int offset = m.end();
				String remaining = in.substring(offset);
				return remaining;
			}

		}

		
        // System.out.print("Key: "+key);
        // System.out.println(" In->|"+in.replace(" ","_"));
		System.out.println("<error> ,"+in);
		System.exit(0);
        return "";
	}
	
	private static LinkedHashMap<String,String> genRegexHash()
	{
		LinkedHashMap<String,String> d = new LinkedHashMap<String,String>();
		d.put("print\\s+","<print>");
		d.put("input\\s+","<input>");
        d.put("\\(","<lparen>");
        d.put("\\)","<rparen>");
        d.put("[\\+\\-]","<add_op>");
        d.put("(\\/\\/)|[\\*\\/\\%]","<mult_op>");
        d.put("(<=)|(>=)|[<>]|={2}|(!=)","<rel_op>");
        d.put("=","<assign>");
        d.put("\\s+","");
        d.put("\\d+[a-zA-Z]+","<error>");
        d.put("[a-zA-z]([a-zA-Z]|\\d)*","<id>");
        d.put("(\\d+\\.\\d+)|(\\d+)","<number>");
		return d;
	}

	private static class FileByLine extends Prog2AAH
	{
		private Scanner sc;
		private static ArrayList<String> tokenBuffer;

		public FileByLine(String filename)
		{
			try
			{
				File f = new File(filename);
				this.sc = new Scanner(f);
			}catch (FileNotFoundException e)
			{
				System.out.println("File not found!");
				System.exit(0);
			}

			this.tokenBuffer = new ArrayList<String>();
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

        private class tokenizer extends FileByLine
        {

        }
	}
}