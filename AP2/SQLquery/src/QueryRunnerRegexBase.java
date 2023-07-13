import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

public class QueryRunnerRegexBase {
	static String query = "";
	static Pattern Q11 = Pattern.compile("^create\\s+table\\s+([^^*|\\/<> \\\"'\\-\\\\]+)\\s+(.*);$",Pattern.CASE_INSENSITIVE)
	, Q12 = Pattern.compile("^drop\\s+table\\s+([^^*|\\/<> \\\"'\\-\\\\]+);$",Pattern.CASE_INSENSITIVE)
	, Q31 = Pattern.compile("^insert\\s+into\\s+([^^*|\\/<> \\\"'\\-\\\\]+)\\s+(.*)\\s+values\\s+(.*);$",Pattern.CASE_INSENSITIVE)
	, Q311 = Pattern.compile("^insert\\s+into\\s+([^^*|\\/<> \\\"'\\-\\\\]+)\\s+values\\s+(.*);$",Pattern.CASE_INSENSITIVE)
	//-----------------------
	, Q21 = Pattern.compile("^select\\s+(.*)\\s+from\\s+([^^*|\\/<> \\\"'\\-\\\\]+);$",Pattern.CASE_INSENSITIVE)
	, Q23 = Pattern.compile("^select\\s+(.*)\\s+from\\s+([^^*|\\/<> \\\"'\\-\\\\]+)\\s+where\\s+(.*);$",Pattern.CASE_INSENSITIVE)
	, Q81 = Pattern.compile("select\\s+(.*)\\s+from\\s+([^^*|\\/<> \\\"'\\-\\\\]+)\\s+order\\s+by\\s+(\\S+)\\s*(asc|desc)?;",Pattern.CASE_INSENSITIVE)
	, Q82 = Pattern.compile("select\\s+(.*)\\s+from\\s+([^^*|\\/<> \\\"'\\-\\\\]+)\\s+where\\s+(.*)\\s+order\\s+by\\s+(\\S+)\\s*(asc|desc)?;",Pattern.CASE_INSENSITIVE)
	, Q61 = Pattern.compile("^select\\s+count\\s*\\(\\s*(\\S+)\\s*\\)\\s+from\\s+([^^*|\\/<> \\\"'\\-\\\\]+);$",Pattern.CASE_INSENSITIVE)
	, Q611 = Pattern.compile("^select\\s+count\\s*\\(\\s*(\\S+)\\s*\\)\\s+from\\s+([^^*|\\/<> \\\"'\\-\\\\]+)\\s+where\\s+(.*);$",Pattern.CASE_INSENSITIVE)
	, Q62 = Pattern.compile("^select\\s+avg\\s*\\(\\s*(\\S+)\\s*\\)\\s+from\\s+([^^*|\\/<> \\\"'\\-\\\\]+);$",Pattern.CASE_INSENSITIVE)
	, Q621 = Pattern.compile("^select\\s+avg\\s*\\(\\s*(\\S+)\\s*\\)\\s+from\\s+([^^*|\\/<> \\\"'\\-\\\\]+)\\s+where\\s+(.*);$",Pattern.CASE_INSENSITIVE)
	, Q63 = Pattern.compile("^select\\s+sum\\s*\\(\\s*(\\S+)\\s*\\)\\s+from\\s+([^^*|\\/<> \\\"'\\-\\\\]+);$",Pattern.CASE_INSENSITIVE)
	, Q631 = Pattern.compile("^select\\s+sum\\s*\\(\\s*(\\S+)\\s*\\)\\s+from\\s+([^^*|\\/<> \\\"'\\-\\\\]+)\\s+where\\s+(.*);$",Pattern.CASE_INSENSITIVE)
			//-----------------------------------
	, Q51 = Pattern.compile("^delete\\s+from\\s+([^^*|\\/<> \\\"'\\\\-\\\\]+);$",Pattern.CASE_INSENSITIVE)
	, Q511 = Pattern.compile("^delete\\s+from\\s+([^^*|\\/<> \\\"'\\\\-\\\\]+)\\s+where\\s+(.*);$",Pattern.CASE_INSENSITIVE)
	, Q41 = Pattern.compile("^update\\s+([^^*|\\/<> \\\"'\\-\\\\]+)\\s+set\\s+(.*);$",Pattern.CASE_INSENSITIVE)
	, Q411 = Pattern.compile("^update\\s+([^^*|\\/<> \\\"'\\-\\\\]+)\\s+set\\s+(.*)\\s+where\\s+(.*);$",Pattern.CASE_INSENSITIVE);
	static Matcher matcher;
	static ArrayList<ArrayList<String>> table = new ArrayList<>();
	public static String removeX(String s,char[] x) {
		// TODO Auto-generated method stub
		try {
			String ans="";
			for(int i = 0;i<s.length();i++) {
				boolean t = false;
				for(int j = 0;j<x.length;j++) {
					if(s.charAt(i) == x[j]) {
						t = true;
					}
				}
				if(!t) {
					ans+=s.charAt(i);
				}
			}
			return ans;
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
			return "";
		}
		
	}
	public static String removeSpaceSmart(String s) {
		// TODO Auto-generated method stub
		try {
			String ans="";
			boolean isin = false;
			for(int i = 0;i<s.length();i++) {
				if(s.charAt(i) != ' ' || isin) {
					ans+=s.charAt(i);
				}
				if(s.charAt(i) == '\'') {
					isin = !isin;
				}
			}
			return ans;
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
			return "";
		}
	}
	public static void setQuery(String input) {
		// TODO Auto-generated method stub
		try {
			query = input;
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
		}
	}
	public static void run() {
		try {
			boolean runed = true;
			matcher = Q11.matcher(query);
			if(matcher.find() && runed) {
				char[] c = {' ','(',')'};
				q11(removeX(matcher.group(2), c));
				writeInFIle(table, matcher.group(1)+".txt");
				table = readFile(matcher.group(1)+".txt");
				//print();
				runed = false;
			}
			matcher = Q12.matcher(query);
			if(matcher.find()&& runed) {
				q12(matcher.group(1)+".txt");
				runed = false;
			}
			matcher = Q311.matcher(query);
			if(matcher.find()&& runed) {
				table= readFile(matcher.group(1)+".txt");
				q311(matcher.group(2));
				writeInFIle(table, matcher.group(1)+".txt");
				table = readFile(matcher.group(1)+".txt");
				//print();
				runed = false;
			}
			matcher = Q31.matcher(query);
			if(matcher.find()&& runed) {
				table= readFile(matcher.group(1)+".txt");
				q31(matcher.group(2),matcher.group(3));
				writeInFIle(table, matcher.group(1)+".txt");
				table = readFile(matcher.group(1)+".txt");
				//print();
				runed = false;
			}
			
			matcher = Q61.matcher(query);
			if(matcher.find()&& runed) {
				table= readFile(matcher.group(2)+".txt");
				int count = q61(matcher.group(1));
				//System.out.println(count);
				printcnt(count,matcher.group(1));
				table= readFile(matcher.group(2)+".txt");
				//print();
				runed = false;
			}
			matcher = Q611.matcher(query);
			if(matcher.find()&& runed) {
				table= readFile(matcher.group(2)+".txt");
				int count = q611(matcher.group(1),matcher.group(3));
				//System.out.println(count);
				printcnt(count,matcher.group(1));
				table= readFile(matcher.group(2)+".txt");
				//print();
				runed = false;
			}
			matcher = Q62.matcher(query);
			if(matcher.find()&& runed) {
				table= readFile(matcher.group(2)+".txt");
				double avg = q62(matcher.group(1));
				//System.out.println(avg);
				printavg(avg,matcher.group(1));
				table= readFile(matcher.group(2)+".txt");
				//print();
				runed = false;
			}
			matcher = Q621.matcher(query);
			if(matcher.find()&& runed) {
				table= readFile(matcher.group(2)+".txt");
				double avg = q621(matcher.group(1),matcher.group(3));
				//System.out.println(avg);
				printavg(avg,matcher.group(1));
				table= readFile(matcher.group(2)+".txt");
				//print();
				runed = false;
			}
			matcher = Q63.matcher(query);
			if(matcher.find()&& runed) {
			
				table= readFile(matcher.group(2)+".txt");
				int sum = q63(matcher.group(1));
				//System.out.println(sum);
				printsum(sum,matcher.group(1));
				table= readFile(matcher.group(2)+".txt");
				//print();
				runed = false;
				
			}
			matcher = Q631.matcher(query);
			if(matcher.find()&& runed) {
				table= readFile(matcher.group(2)+".txt");
				int sum = q631(matcher.group(1),matcher.group(3));
				//System.out.println(sum);
				printsum(sum,matcher.group(1));
				table= readFile(matcher.group(2)+".txt");
				//print();
				runed = false;
			}
			
			matcher = Q21.matcher(query);
			if(matcher.find()&& runed) {
				table= readFile(matcher.group(2)+".txt");
				table = q21(matcher.group(1));
				print();
				table = readFile(matcher.group(2)+".txt");
				//print();
				runed = false;
			}
			matcher = Q81.matcher(query);
			if(matcher.find()&& runed) {
				HashMap<String, Integer> hash = new HashMap<>();
				table= readFile(matcher.group(2)+".txt"); 
				for(int i = 0; i<table.get(0).size();i++) {
					hash.put(table.get(0).get(i), i);
				}
				int asc = 1;
				if( matcher.group(4) != null &&  matcher.group(4).equalsIgnoreCase("desc")) {
					asc = -1;
				}
				sort(table, hash, matcher.group(3),asc);
				table = q21(matcher.group(1));
				print();
				table = readFile(matcher.group(2)+".txt");
				//print();
				runed = false;
			}
			matcher = Q82.matcher(query);
			if(matcher.find()&& runed) {
				HashMap<String, Integer> hash = new HashMap<>();
				table= readFile(matcher.group(2)+".txt"); 
				for(int i = 0; i<table.get(0).size();i++) {
					hash.put(table.get(0).get(i), i);
				}
				int asc = 1;
				if( matcher.group(5) != null &&  matcher.group(5).equalsIgnoreCase("desc")) {
					asc = -1;
				}
				sort(table, hash, matcher.group(4),asc);
				table = q23(matcher.group(3));
				table = q21(matcher.group(1));
				print();
				table = readFile(matcher.group(2)+".txt");
				//print();
				runed = false;
			}
			matcher = Q23.matcher(query);
			if(matcher.find()&& runed) {
				table= readFile(matcher.group(2)+".txt");
				table = q23(matcher.group(3));
				table = q21(matcher.group(1));
				print();
				table = readFile(matcher.group(2)+".txt");
				//print();
				runed = false;
			}
			
			matcher = Q51.matcher(query);
			if(matcher.find()&& runed) {
				table= readFile(matcher.group(1)+".txt");
				for(int i = 1;table.size() > 1;) {
					table.remove(i);
				}
				writeInFIle(table, matcher.group(1)+".txt");
				table = readFile(matcher.group(1)+".txt");
				//print();
				runed = false;
			}
			matcher = Q511.matcher(query);
			if(matcher.find()&& runed) {
				table= readFile(matcher.group(1)+".txt");
				table = q23("not ("+matcher.group(2)+")");
				writeInFIle(table, matcher.group(1)+".txt");
				table = readFile(matcher.group(1)+".txt");
				//print();
				runed = false;
			}
			matcher = Q411.matcher(query);
			if(matcher.find()&& runed) {
				HashMap<String, Integer> hash = new HashMap<>();
				table= readFile(matcher.group(1)+".txt"); 
				for(int i = 0; i<table.get(0).size();i++) {
					hash.put(table.get(0).get(i), i);
				}
				table = q411(matcher.group(2),hash,matcher.group(3));
				writeInFIle(table, matcher.group(1)+".txt");
				table = readFile(matcher.group(1)+".txt");
				//print();
				runed = false;
			}
			matcher = Q41.matcher(query);
			if(matcher.find()&& runed) {
				HashMap<String, Integer> hash = new HashMap<>();
				table= readFile(matcher.group(1)+".txt"); 
				for(int i = 0; i<table.get(0).size();i++) {
					hash.put(table.get(0).get(i), i);
				}
				table = q41(matcher.group(2),hash);
				writeInFIle(table, matcher.group(1)+".txt");
				table = readFile(matcher.group(1)+".txt");
				//print();
				runed = false;
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
		}
		
	}
	public static ArrayList<ArrayList<String>> q411(String what,HashMap<String, Integer> hash , String cond) {
		try {
			ArrayList<ArrayList<String>> anstable = new ArrayList<>();
			what = removeSpaceSmart(what);
			String[] whats = what.split(",");
			anstable.add(table.get(0));
			for(int i = 1; i< table.size();i++) {
				if(solveWhere(table.get(0),table.get(i), cond)) {
					for(int j = 0;j<whats.length;j++) {
						String[] wt = whats[j].split("=");
						char[] c = {'\''};
						table.get(i).set(hash.get(wt[0]),removeX(wt[1], c));
					}
				}
				anstable.add(table.get(i));
			}
			return anstable;
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
			return new ArrayList<>();
		}
	}
	public static ArrayList<ArrayList<String>> q41(String what,HashMap<String, Integer> hash) {
		try {
			ArrayList<ArrayList<String>> anstable = new ArrayList<>();
			what = removeSpaceSmart(what);
			String[] whats = what.split(",");
			anstable.add(table.get(0));
			for(int i = 1; i< table.size();i++) {
				for(int j = 0;j<whats.length;j++) {
					String[] wt = whats[j].split("=");
					char[] c = {'\''};
					table.get(i).set(hash.get(wt[0]),removeX(wt[1], c));
				}
				anstable.add(table.get(i));
			}
			return anstable;
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
			return new ArrayList<>();
		}
	}
	public static int q61(String row) {
		try {
			table = q21(row);
			return table.get(0).size() - 1;
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
			return 0;
		}
	}
	public static int q611(String row,String cond) {
		try {
			table = q23(cond);
			table = q21(row);
			return table.get(0).size() - 1;
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
			return 0;
		}
	}
	public static int q63(String row) {
		try {
			table = q21(row);
			int sum = 0;
			for(int i = 1;i<table.get(0).size();i++) {
				if(isNumber(table.get(0).get(i))) {
					sum+= Integer.parseInt(table.get(0).get(i));
				}
			}
			return sum;
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
			return 0;
		}
	}
	public static int q631(String row,String cond) {
		try {
			table = q23(cond);
			table = q21(row);
			int sum = 0;
			for(int i = 1;i<table.get(0).size();i++) {
				if(isNumber(table.get(0).get(i))) {
					sum+= Integer.parseInt(table.get(0).get(i));
				}
			}
			return sum;
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
			return 0;
		}
	}
	public static double q62(String row) {
		try {
			table = q21(row);
			int sum = 0;
			for(int i = 1;i<table.get(0).size();i++) {
				if(isNumber(table.get(0).get(i))) {
					sum+= Integer.parseInt(table.get(0).get(i));
				}
			}
			return (double)sum/(table.get(0).size() - 1);
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
			return 0;
		}
	}
	public static double q621(String row,String cond) {
		try {
			table = q23(cond);
			table = q21(row);
			int sum = 0;
			for(int i = 1;i<table.get(0).size();i++) {
				if(isNumber(table.get(0).get(i))) {
					sum+= Integer.parseInt(table.get(0).get(i));
				}
			}
			return (double)sum/(table.get(0).size() - 1);
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
			return 0;
		}
	}
	public static void q11(String row) {
		try {
			ArrayList<String> temp = new ArrayList<>(Arrays.asList(row.split(",")));
			table.clear();
			table.add(temp);
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
			
		}
	}
	public static ArrayList<ArrayList<String>> q21(String row) {
		try {
			ArrayList<String> temp = new ArrayList<>(Arrays.asList(row.split(",")));
			ArrayList<String> Atemp = new ArrayList<>();
			if (row.contains("*")) {
				temp = new ArrayList<>(table.get(0));
			}
			for(int i = 0;i<temp.size();i++) {
				Pattern alias = Pattern.compile("^\\s*(.*)\\s+as\\s+(.*)\\s*$",Pattern.CASE_INSENSITIVE);
				Matcher mat = alias.matcher(temp.get(i));
				if(mat.find()) {
					temp.set(i, mat.group(1));
					Atemp.add(removeSpaceSmart(mat.group(2)));
				}
				else {
					temp.set(i, removeSpaceSmart(temp.get(i)));
					Atemp.add(removeSpaceSmart(temp.get(i)));
				}
			}
			ArrayList<ArrayList<String>> rtable = new ArrayList<>();
			for(int i = 0 ; i < table.get(0).size() ; i++) {
				ArrayList<String> column = new ArrayList<String>();
				column.add(table.get(0).get(i));
				rtable.add(column);
			}
			for(int i = 1 ; i < table.size() ; i++) {
				for(int j = 0;j<table.get(0).size();j++) {
					rtable.get(j).add(table.get(i).get(j));
				}
			}
			ArrayList<ArrayList<String>> anstable = new ArrayList<>();
			for(int i = 0 ; i < temp.size() ; i++) {
				for(int j = 0;j<rtable.size();j++) {
					if(temp.get(i).equals(rtable.get(j).get(0))) {
						anstable.add(new ArrayList<String>(rtable.get(j)));
					}
				}
			}
			for(int i = 0 ; i < anstable.size() ; i++) {
				anstable.get(i).set(0, Atemp.get(i));
			}
			return anstable;
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
			return new ArrayList<>();
		}
	}
	public static ArrayList<ArrayList<String>> q23(String cond) {
		try {
			ArrayList<ArrayList<String>> anstable = new ArrayList<>();
			anstable.add(table.get(0));
			for(int i = 1; i< table.size();i++) {
				if(solveWhere(table.get(0),table.get(i), cond)) {
					anstable.add(table.get(i));
				}
			}
			return anstable;
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
			return new ArrayList<>();
		}
	}
	public static void q12(String fname) {
		try {
			File myFile = new File(fname);
			if(myFile.delete()) {
				
			}
		} 
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
		}
	}
	public static void q31(String row,String value) {
		try {
			ArrayList<String> temp = new ArrayList<>(table.get(0));
			char[] c = {'(',')'};
			String[] rows = removeSpaceSmart(removeX(row, c)).split(",");
			String[] values = removeSpaceSmart(removeX(value, c)).split(",");
			for(int i = 0;i<temp.size();i++) {
				boolean isnull = true;
				for(int j = 0;j<rows.length;j++) {
					if(temp.get(i).equals(rows[j])) {
						char[] cc = {'\''};
						temp.set(i, removeX(values[j], cc)); 
						isnull = false;
					}
				}
				if(isnull) {
					temp.set(i, "null");
				}
			}
			table.add(temp);
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
		}
	}
	public static void q311(String row) {
		try {
			String[] rows = removeSpaceSmart(row).split("\\),\\(");
			for(int i =0;i<rows.length;i++) {
				char[] c = {'(',')'};
				char[] cc = {'\''};
				ArrayList<String> temp = new ArrayList<>(Arrays.asList(removeX(removeSpaceSmart(removeX(rows[i], c)),cc).split(",")));
				table.add(temp);
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
			
		}
	}
	public static void print() {
		try {
	//		for(int i = 0;i<table.size();i++) {
	//			for(int j = 0;j<table.get(i).size();j++) {
	//				 System.out.print(table.get(i).get(j));
	//				 if(j!=table.get(i).size()-1) {
	//					 System.out.print("|");
	//				 }
	//			}
	//			System.out.println();
	//		}
			ShowTable show = new ShowTable(table);
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
		}
	}
	public static void printsum(int num,String row) {
		try {
			ArrayList<ArrayList<String>> temp = new ArrayList<>();
			ArrayList<String> temp2 = new ArrayList<>();
			temp2.add("SumOf(" + row + ")");
			temp2.add(""+num);
			temp.add(temp2);
			ShowTable show = new ShowTable(temp);
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
		}
	}
	public static void printcnt(int num,String row) {
		try {
			ArrayList<ArrayList<String>> temp = new ArrayList<>();
			ArrayList<String> temp2 = new ArrayList<>();
			temp2.add("CountOf(" + row + ")");
			temp2.add(""+num);
			temp.add(temp2);
			ShowTable show = new ShowTable(temp);
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
		}
	}
	public static void printavg(double num,String row) {
		try {
			ArrayList<ArrayList<String>> temp = new ArrayList<>();
			ArrayList<String> temp2 = new ArrayList<>();
			temp2.add("AvgOf(" + row + ")");
			temp2.add(""+num);
			temp.add(temp2);
			ShowTable show = new ShowTable(temp);
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
		}
	}
	public static void writeInFIle(ArrayList<ArrayList<String>> text, String fname) {
		try {
			File myFile = new File(fname);
			if(myFile.createNewFile()) {
				
			}
			FileWriter myWriter = new FileWriter(myFile,false);
			for(int i = 0;i<text.size();i++) {
				for(int j = 0;j<text.get(i).size();j++) {
					 myWriter.write(text.get(i).get(j));
					 if(j!=text.get(i).size()-1) {
						 myWriter.write("~");
					 }
				}
				myWriter.write("\n");
			}
			myWriter.close();
		} 
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
		}
	}
	public static ArrayList<ArrayList<String>> readFile(String fname) {
		try {
			File myFile = new File(fname);
			Scanner fReader = new Scanner(myFile);
			String ans = "";
			while(fReader.hasNextLine()) {
				ans+=fReader.nextLine()+"\n";
			}
			ArrayList<String> ans2 = new ArrayList(Arrays.asList(ans.split("\n")));
			ArrayList<ArrayList<String>> ret;
			ret = new ArrayList<>();
			for(int i = 0;i<ans2.size();i++) {
				ret.add(new ArrayList<>(Arrays.asList(ans2.get(i).split("~")))); 
			}
			fReader.close();
			return ret;
		} 
		catch (Exception ex) {
			exception(ex);
			return new ArrayList<>();
		}
	}
	public static String notsolver(String tosolve) {
		try {
			if(!tosolve.contains("!1") && !tosolve.contains("!0")) {
				return tosolve;
			}
			for(int i = 0; i< tosolve.length();i++) {
				if(tosolve.charAt(i) == '!') {
					if(tosolve.charAt(i+1) == '1') {
						tosolve = tosolve.substring(0,i) + "0" +tosolve.substring(i+2);
					}
					if(tosolve.charAt(i+1) == '0') {
						tosolve = tosolve.substring(0,i) + "1" +tosolve.substring(i+2);
					}
				}
			}
			return notsolver(tosolve);
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
			return "";
		}
	}
	public static String boolsolver(String tosolve) {
		try {
			tosolve = notsolver(tosolve);
			if(tosolve.equals("1") || tosolve.equals("0")) {
				return tosolve;
			}
			for( int i = tosolve.length() -1 ;i>=0;i--) {
				if(tosolve.charAt(i) == '(') {
					int j = i;
					while(tosolve.charAt(j) != ')') {
						if(tosolve.charAt(j) == '&') {
							tosolve = tosolve.substring(0,j-1) + (Integer.parseInt(tosolve.charAt(j-1)+"") & Integer.parseInt(tosolve.charAt(j+1)+"")) + tosolve.substring(j+2);
							j--;
						}
						if(tosolve.charAt(j) == '|') {
							tosolve = tosolve.substring(0,j-1) + (Integer.parseInt(tosolve.charAt(j-1)+"") | Integer.parseInt(tosolve.charAt(j+1)+"")) + tosolve.substring(j+2);
							j--;
						}
						j++;
					}
					tosolve = tosolve.substring(0,i)+tosolve.substring(i+1,i+2)+tosolve.substring(i+3);
					break;
				}
			}
			return boolsolver(tosolve);
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
			return "";
		}
	}
	public static String solve(String tosolve , HashMap<String, String> hash) {
		try {
			Pattern eq1 = Pattern.compile("([^=><!]+)\\s*=\\s*([^=><!]+)",Pattern.CASE_INSENSITIVE)
			,eq2 = Pattern.compile("([^=><!]+)\\s*>=\\s*([^=><!]+)",Pattern.CASE_INSENSITIVE)
			,eq3 = Pattern.compile("([^=><!]+)\\s*<=\\s*([^=><!]+)",Pattern.CASE_INSENSITIVE)
			,eq4 = Pattern.compile("([^=><!]+)\\s*<>\\s*([^=><!]+)",Pattern.CASE_INSENSITIVE)
			,eq5 = Pattern.compile("([^=><!]+)\\s*>\\s*([^=><!]+)",Pattern.CASE_INSENSITIVE)
			,eq6 = Pattern.compile("([^=><!]+)\\s*<\\s*([^=><!]+)",Pattern.CASE_INSENSITIVE)
			,eq7 = Pattern.compile("([^=><!]+)\\s*like\\s*([^=><!]+)",Pattern.CASE_INSENSITIVE)
			,eq8 = Pattern.compile("([^=><!]+)\\s*regexp\\s*([^=><!]+)",Pattern.CASE_INSENSITIVE);
			Matcher match;
			match = eq1.matcher(tosolve);
			if(match.find()) {
				char[] ccc = { '\''};
				if(hash.get(match.group(1)).equals(removeX(match.group(2),ccc))) {
					return "1";
				}
				else {
					return "0";
				}
			}
			match = eq2.matcher(tosolve);
			if(match.find()) {
				char[] ccc = { '\''};
				if(compare(hash.get(match.group(1)),removeX(match.group(2),ccc)) == 1 || hash.get(match.group(1)).equals(removeX(match.group(2),ccc))) {
					return "1";
				}
				else {
					return "0";
				}
			}
			match = eq3.matcher(tosolve);
			if(match.find()) {
				char[] ccc = { '\''};
				if(compare(hash.get(match.group(1)),removeX(match.group(2),ccc)) == -1 || hash.get(match.group(1)).equals(removeX(match.group(2),ccc))) {
					return "1";
				}
				else {
					return "0";
				}
			}
			match = eq4.matcher(tosolve);
			if(match.find()) {
				char[] ccc = { '\''};
				if(!hash.get(match.group(1)).equals(removeX(match.group(2),ccc))) {
					return "1";
				}
				else {
					return "0";
				}
			}
			match = eq5.matcher(tosolve);
			if(match.find()) {
				char[] ccc = { '\''};
				if(compare(hash.get(match.group(1)),removeX(match.group(2),ccc)) == 1) {
					return "1";
				}
				else {
					return "0";
				}
			}
			match = eq6.matcher(tosolve);
			if(match.find()) {
				char[] ccc = { '\''};
				if(compare(hash.get(match.group(1)),removeX(match.group(2),ccc)) == -1) {
					return "1";
				}
				else {
					return "0";
				}
			}
			match = eq7.matcher(tosolve);
			if(match.find()) {
				//char[] ccc = { '\''};
				String like = match.group(2).substring(1,match.group(2).length()-1) , like2 = "^";
				for(int i = 0; i< like.length() ; i++) {
					if(like.charAt(i) == '_') {
						like2 += ".";
					}
					else if(like.charAt(i) == '%') {
						like2 += ".*";
					}
					else {
						like2 += like.charAt(i);
					}
					
				}
				like2 +="$";
				Pattern pat = Pattern.compile(like2,Pattern.CASE_INSENSITIVE);
				Matcher m;
				m = pat.matcher(hash.get(match.group(1)));
				if(m.find()) {
					return "1";
				}
				else {
					return "0";
				}
			}
			match = eq8.matcher(tosolve);
			if(match.find()) {
				//char[] ccc = { '\''};
				Pattern pat = Pattern.compile(match.group(2).substring(1,match.group(2).length()-1),Pattern.CASE_INSENSITIVE);
				Matcher m;
				m = pat.matcher(hash.get(match.group(1)));
				if(m.find()) {
					return "1";
				}
				else {
					return "0";
				}
			}
			//System.out.println(tosolve);
			return "0";
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
			return "";
		}
	}
	public static boolean solveWhere(ArrayList<String> names,ArrayList<String> values,String conditions) {
		try {
			HashMap<String, String> hash = new HashMap<>();
			for(int i = 0;i < names.size();i++) {
				hash.put(names.get(i), values.get(i));
			}
			conditions = "("+conditions+")";
			for(int i = 0;i < conditions.length();i++) {
				if(conditions.charAt(i) == '(') {
					conditions = conditions.substring(0,i)+" ( "+conditions.substring(i+1);
					i+=2;
				}
				if(conditions.charAt(i) == ')') {
					conditions = conditions.substring(0,i)+" ) "+conditions.substring(i+1);
					i+=2;
				}
			}
			String[] t = conditions.split(" ");
			
			for(int i = 0;i< t.length;i++) {
				if(t[i].equals("and")) {
					t[i] = "&";
				}
				if(t[i].equals("or")) {
					t[i] = "|";
				}
				if(t[i].equals("not")) {
					t[i] = "!";
				}
			}
			String ans ="";
			for(int i = 0;i< t.length;i++) {
				ans += t[i]+" ";
			}
			
			ans = QueryRunnerRegexBase.removeSpaceSmart(ans);
			String put = "";
			int j = -1;
			for(int i = 0;i< ans.length();i++) {
				if(ans.charAt(i) == '!' || ans.charAt(i) == '|' || ans.charAt(i) == '&' || ans.charAt(i) == '(' || ans.charAt(i) == ')') {
					if(i - j != 1) {
						put += solve(ans.substring(j+1, i) , hash) ;
						//System.out.println(ans.substring(j+1, i));
						j=i;
					}
					else {
						j=i;
					}
					put+= ans.charAt(i) ;
				}
			}
			//put = "("+put+")";
			//System.out.println(conditions);
			//System.out.println(ans);
			//System.out.println(put);
			 put = boolsolver(put);
			 //System.out.println(put);
			 if(put.equals("1")) {
				 return true;
			 }
			 else {
				 return false;
			 }
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
			return false;
		}
	}
	public static int compare(String first , String second) {
		try {
			if(isNumber(first) && isNumber(second)) {
				//System.out.println("isnum");
				if(Integer.parseInt(first) > Integer.parseInt(second)) {
					return 1;
				}
				else if (Integer.parseInt(first) < Integer.parseInt(second)) {
					return -1;
				}
				else {
					return 0;
				}
			}
			else {
				if(first.length() > second.length()) {
					return 1;
				}
				else if(first.length() < second.length()) {
					return -1;
				}
				else {
					return (first.compareTo(second) > 0 ? 1 : (first.compareTo(second) < 0 ? -1 : 0));
				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
			return 0;
		}
	}
	public static void sort(ArrayList<ArrayList<String>> tosort , HashMap<String, Integer > hash , String column,int ascordesc) {// 1 for asc -1 for desc
		try {
			int n = tosort.size();  
	        
	        for(int i = 0; i < n; i++){  
	        	for(int j = 2; j < (n-i); j++){
	        		
	        		if( compare(tosort.get(j-1).get(hash.get(column)),tosort.get(j).get(hash.get(column))) == ascordesc) {
	        			Collections.swap(tosort ,j-1 ,j );
	                }        
	            }  
	        }  
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
		}
	}
	public static boolean isNumber(String num) {
		try {
			if(num.length() == 0) {
				return false;
			}
			for(int i = 0; i < num.length(); i++) {
				if(num.charAt(0) == '-') {
					continue;
				}
				if(num.charAt(i) == '1') {
					
				}
				else if (num.charAt(i) == '2') {
					
				}
				else if (num.charAt(i) == '3') {
					
				}
				else if (num.charAt(i) == '4') {
					
				}
				else if (num.charAt(i) == '5') {
		
				}
				else if (num.charAt(i) == '6') {
		
				}
				else if (num.charAt(i) == '7') {
		
				}
				else if (num.charAt(i) == '8') {
		
				}
				else if (num.charAt(i) == '9') {
					
				}
				else if (num.charAt(i) == '0') {
					
				}
				else {
					return false;
				}
			}
			//System.out.println(num);
			return true;
		}
		catch (Exception e) {
			// TODO: handle exception
			exception(e);
			return false;
		}
	}
	public static void exception(Exception ex) {
		try {
			JOptionPane.showMessageDialog(null,"there must be a problem with your Query syntax or Order\n for code debugging : "+ex.getMessage(),"An Exceptin Has Occurred",JOptionPane.ERROR_MESSAGE);
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("there must be a problem with your Query syntax or Order\n for code debugging : " + e.getMessage());
		}
	}
}
