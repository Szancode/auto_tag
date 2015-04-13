package auto_tagging;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import jeasy.analysis.*;
public class Test {

	public static void main(String[] args) throws IOException {
/*		String text= "据路透社报道，是印度尼西亚社会事务部一官员星期二(29日)表示，"
	             + "日惹市附近当地时间27日晨5时53分发生的里氏6.2级地震已经造成至少5427人死亡，"
	             + "20000余人受伤，近20万人无家可归。";
		
		Segment cs2= new Segment();
		String temp= cs2.split(text, "|");
		String []toarray =temp.split("\\|");
//		for(String a:toarray)
//			System.out.print(a);
//		System.out.println();
		String []result= Segment.dropStopWords(toarray);
		for(String a:result)
			System.out.println(a);*/

		
		String test_file = "C:\\Users\\Ivar\\Desktop\\Sample\\C000007\\19.txt";
		ArrayList<String> cutwords = TF_IDF.cutWords(test_file);		
		Main t = new Main();
		Factor_1 fac_1 = new Factor_1();
		Factor_2 fac_2 = new Factor_2();
		HashMap<String, Float> tw = new HashMap<String, Float>();
		HashMap<String, Float> tf =TF_IDF.tf(cutwords);
		HashMap<String, Float> idf = TF_IDF.resIDF;
		HashMap<String , Float> f1 = fac_1.resFactor();
		HashMap<String, HashMap<String, Float>> all_f2 = fac_2.resFacForAllClass();
		String class_name = new File(test_file).getParent();
		HashMap<String , Float> f2 = all_f2.get(class_name);
		for(String word:cutwords){
			if(idf.get(word)==null || f1.get(word)==null){
				float defaultIDF = (float)Math.log(TF_IDF.fileNum);
				if(f2.get(word) == null)
					tw.put(word, tf.get(word)*defaultIDF*1/3*0.5f);
				else
					tw.put(word, tf.get(word)*defaultIDF*1/3*(1.0f - f2.get(word)));
			}
			else
			{
				if(f2.get(word) == null)
					tw.put(word, tf.get(word)*idf.get(word)*f1.get(word)*0.5f);
				else 
					tw.put(word, tf.get(word)*idf.get(word)*f1.get(word)*(1.0f - f2.get(word)));
			}
		}
		t.sort(tw);
	}

}
