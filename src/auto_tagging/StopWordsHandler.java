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

	private static String stopWordsList[] ={"��","��", "�й�","����","��","����","�ɽ�", "һ��","һ��","����","Ҫ","�Լ�","֮","��","��","��","��","��","��","��","Ӧ","��","ĳ","��",""
			+ "��","��" ,"λ","��","һ","��","��","��","��","��","��","��","����","��","��","��","��Ϊ"," "};//����ͣ�ô�

	
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


