package auto_tagging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
	private final String file = "C:\\Users\\Ivar\\Desktop\\sample";

	
	
	public HashMap<String, HashMap<String, Float>> result() throws IOException{
		HashMap<String, HashMap<String, Float>> res = new HashMap<String, HashMap<String, Float>>();

		HashMap<String,HashMap<String, Float>> all_tf = TF_IDF.tfAllFiles(file);
        System.out.println();
        HashMap<String, Float> idfs = TF_IDF.idf(all_tf);
        System.out.println();
        
        Factor_1 test = new Factor_1();
        HashMap<String, Float> resFac = test.resFactor();
        HashMap<String, HashMap<String, Float>> alltfidf = TF_IDF.tf_idf(all_tf, idfs);
        Set<String> keyset = alltfidf.keySet();
        for(String file:keyset){
        	HashMap<String, Float> tfidf = alltfidf.get(file);
    		HashMap<String, Float> dict = new HashMap<String, Float>();
        	for(String word:tfidf.keySet()){
        		dict.put(word, tfidf.get(word)*resFac.get(word));
        		res.put(file, dict);
        	}
        }
//        Set<String> ks = res.keySet();
//        for(String file:ks){
//        	System.out.println(file);
//        	HashMap<String,Float> temp = res.get(file);
//        	System.out.println(temp);
//        }
		return res;
	}
	
/*	public HashMap<String, HashMap<String, Float>> sort() throws IOException {
		HashMap<String, HashMap<String, Float>> tag = new HashMap<String, HashMap<String, Float>>();
		HashMap<String, HashMap<String, Float>> forsort = result();
		for (String file : forsort.keySet()) {
			HashMap<String, Float> map_Data = forsort.get(file);
			List<Map.Entry<String, Float>> list_Data = new ArrayList<Map.Entry<String, Float>>(map_Data.entrySet());
			Collections.sort(list_Data, new Comparator<Map.Entry<String, Float>>() {
						public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
							if (o2.getValue() != null && o1.getValue() != null && o2.getValue().compareTo(o1.getValue()) > 0) {
								return 1;
							} else {
								return -1;
							}
						}
					});
			System.out.println(file+" 的5个推荐词为：");
			for(int i=0;i<5;i++)
				System.out.println(list_Data.get(i));
		}
		
		return tag;
	}*/
	
	public ArrayList<Map.Entry<String, Float>> sort(HashMap<String, Float> forsort){
		ArrayList<Map.Entry<String, Float>> tag = new ArrayList<Map.Entry<String, Float>>(forsort.entrySet());
		Collections.sort(tag, new Comparator<Map.Entry<String, Float>>() {
			public int compare(Map.Entry<String, Float> o1, Map.Entry<String, Float> o2) {
				if (o2.getValue() != null && o1.getValue() != null && o2.getValue().compareTo(o1.getValue()) > 0) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		
		System.out.println(" 5个推荐词为：");
		for(int i=0;i<10;i++)
			System.out.println(tag.get(i));
		
		return tag;
	}
	
	public static void main(String[] args) throws IOException {
		
//		new Main().sort();

	}

}
