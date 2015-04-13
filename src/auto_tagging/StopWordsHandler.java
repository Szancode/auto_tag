package auto_tagging;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;




public class StopWordsHandler 
{

/*	private static ArrayList<String> stopWordsList=new ArrayList<String>();

	
	public StopWordsHandler() throws IOException{
		String dic_path="C:\\Users\\Ivar\\Desktop\\stopWords.txt";
		
		
		InputStreamReader inStrR= new InputStreamReader(new FileInputStream(dic_path));
		BufferedReader br=new BufferedReader(inStrR);
		String line=br.readLine();

		while(line!=null){
			stopWordsList.add(line);
			line=br.readLine();
		}
		
	}
	
	public  boolean isStopWord(String word)
	{
		for(int i=0;i<stopWordsList.size();i++)
		{
			if(word.equalsIgnoreCase(stopWordsList.get(i)))
				return true;
		}
		return false;
	}*/

	private static String stopWordsList[] ={"的","据", "中国","但是","近","可以","可将", "一个","一种","我们","要","自己","之","将","“","”","，","（","）","后","应","到","某","后",""
			+ "个","是" ,"位","新","一","两","在","中","或","有","更","好","建设","就","者","从","认为"," "};//常用停用词

	
	public  boolean isStopWord(String word)
	{
		for(int i=0;i<stopWordsList.length;i++)
		{
			if(word.equalsIgnoreCase(stopWordsList[i]))
				return true;
		}
		return false;
	}

	
/*	public static void main(String args[]) throws IOException{
//		StopWordsHandler s=new StopWordsHandler();
		StopWordsHandler.stopWordsList.get(0);
		StopWordsHandler.stopWordsList.get(1);
	}
	*/
	
	
}


