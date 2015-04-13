package auto_tagging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;




public class Factor_1 {
	private final String rootDir = "C:\\Users\\Ivar\\Desktop\\Sample";
//	private List<String> filelist = new ArrayList<String>();
	private List<HashMap<String, Integer>> maplist = new ArrayList<HashMap<String, Integer>>();
	
	public Factor_1(){
		try {
			File file = new File(rootDir);
			String[] filelist = file.list();
			for(String list:filelist)
				this.maplist.add(timesForTerms(rootDir+"\\"+list));
//			this.maplist.add(timesForTerms("C:\\Users\\Ivar\\Desktop\\Sample\\C000007"));
//			this.maplist.add(timesForTerms("C:\\Users\\Ivar\\Desktop\\Sample\\C000008"));
//			this.maplist.add(timesForTerms("C:\\Users\\Ivar\\Desktop\\Sample\\C000010"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	



	
	public ArrayList<String> allCutWords(String subDir) throws FileNotFoundException, IOException{
		ArrayList<String> allcutwords = new ArrayList<String>();
		File newfile = new File(subDir);
		for(String file:newfile.list()){
			allcutwords.addAll(TF_IDF.cutWords(subDir+"\\"+file));
		}
		return allcutwords;
				
	}
	
	public  HashMap<String, Integer>timesForTerms(String subDir) throws FileNotFoundException, IOException{
        HashMap<String, Integer> resTF = new HashMap<String, Integer>();
        ArrayList<String> allcutwords = allCutWords(subDir);
        for(String word : allcutwords){
            if(resTF.get(word) == null){
                resTF.put(word, 1);
            }
            else{
                resTF.put(word, resTF.get(word) + 1);
            }
        }
        return resTF;
		
	}
	
/*	public  HashMap<String, HashMap<String, Integer>>timesForPerClass(String dir) throws IOException{
		HashMap<String, HashMap<String, Integer>> result=new HashMap<String, HashMap<String, Integer>>();
		String classname = new File(filelist.get(0)).getParent();
		System.out.println("classname is: " + classname);
		HashMap<String, Integer> dic = new HashMap<String, Integer>();	
		
		dic = timesForTerms(dir);
		result.put(classname, dic);
		return result;
	}*/
	
	
	
	public  HashMap<String, Float> averageTermTimes() throws FileNotFoundException, IOException{
		HashMap<String, Float> averageRes = new HashMap<String, Float>();
		int listNum=maplist.size();
		System.out.println(listNum);
		for(int i=0;i<listNum;i++){
			for(String word:maplist.get(i).keySet()){
				if(averageRes.get(word)==null)
					averageRes.put(word, (float)maplist.get(i).get(word)/listNum);
				else
					averageRes.put(word, (float)(averageRes.get(word)+maplist.get(i).get(word)/listNum));
					
			}
		}
		
		System.out.println(averageRes);
		return averageRes;
	}
	
	public  HashMap<String, Float> resFactor() throws FileNotFoundException, IOException{
		HashMap<String, Float> result = new HashMap<String, Float>();
		HashMap<String, Float> average = averageTermTimes();
		int listNum=maplist.size();
		for(int i=0;i<listNum;i++){
			for(String word:average.keySet()){
				if(maplist.get(i).get(word)==null)
					result.put(word, (float) Math.pow(average.get(word), 2)/listNum);
				else
				{	
					if(result.get(word)==null)					
						result.put(word, (float) Math.pow(average.get(word)-maplist.get(i).get(word), 2)/listNum);
					else
					{
						float temp = result.get(word);
						result.put(word, temp+(float) Math.pow(average.get(word)-maplist.get(i).get(word), 2)/listNum);
					}
				}
			}
		}
		
		System.out.println(result);
		return result;
		 
	}
	
	
	public static void main(String args[]) throws IOException{
		Factor_1 test = new Factor_1();
		
//		test.timesForPerClass("C:\\Users\\Ivar\\Desktop\\Sample");
//		test.averageTermTimes();
		test.resFactor();
//		timesAllFile("C:\\Users\\Ivar\\Desktop\\Sample");
	}
	
	
}
