package auto_tagging;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Factor_2 {	
	
	private String rootDir = "C:\\Users\\Ivar\\Desktop\\Sample";
	private HashMap<String, HashMap<String, Float>> tfDic =
			new HashMap<String, HashMap<String,Float>>();
	private String []class_list = null;
	
	
	public Factor_2(){
		try {
			this.tfDic = TF_IDF.tfAllFiles(rootDir);
			this.class_list = new File(rootDir).list();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public HashMap<String, Float> averageTFforPerClass(String class_name){
		HashMap<String, Float> res = new HashMap<String, Float>();
		String []file_list = new File(class_name).list();
		int file_num = file_list.length;
		for(String file:file_list){
			HashMap<String, Float> tf = tfDic.get(class_name + "\\" + file);
			for(String word:tf.keySet()){
				if(res.get(word) == null){
					res.put(word, tf.get(word)/file_num);
				}
				else
				{
					res.put(word, res.get(word) + tf.get(word)/file_num);
				}
			}
		}
		System.out.println(res);
		return res;
	}
	
	
	public HashMap<String, Float> normalFac(String class_name){
		HashMap<String, Float> res = new HashMap<String, Float>();
		String []file_list = new File(class_name).list();
		int file_num = file_list.length;
		for(String file:file_list){
			HashMap<String, Float> tf = tfDic.get(class_name + "\\" + file);
			for(String word:tf.keySet()){
				if(res.get(word) == null){
					res.put(word, (float)Math.pow(tf.get(word), 2)/file_num);
				}
				else
				{
					res.put(word, res.get(word) + (float)Math.pow(tf.get(word), 2)/file_num);
				}
			}
		}
		System.out.println(res);
		return res;
	}

	public HashMap<String, Float> resFacForPerClass(String class_name){
		HashMap<String, Float> res = new HashMap<String, Float>();
		HashMap<String, Float> average = averageTFforPerClass(class_name);
		HashMap<String, Float> normals = normalFac(class_name);
		String []file_list = new File(class_name).list();
		int file_num = file_list.length;
		for(String file:file_list){
			HashMap<String, Float> tf = tfDic.get(class_name + "\\" + file);
			for(String word:average.keySet()){
				float normal = normals.get(word);
				if(tf.get(word) == null)
					res.put(word, (float)Math.pow(average.get(word), 2)/file_num/normal);
				else if(res.get(word) == null)
				{
					res.put(word, (float)Math.pow(average.get(word)-tf.get(word), 2)/file_num/normal);
				}
				else
					res.put(word, res.get(word) + (float)Math.pow(average.get(word)-tf.get(word), 2)/file_num/normal);
			}
		}
		
		return res;
	}
	
	public HashMap<String, HashMap<String, Float>> resFacForAllClass(){
		HashMap<String, HashMap<String, Float>> res = new HashMap<String, HashMap<String, Float>>();
		for(String class_name:class_list){
			HashMap<String, Float> dic = resFacForPerClass(rootDir + "\\" + class_name);
			res.put(rootDir+"\\"+class_name, dic);
		}
		System.out.println(res);
		return res;
	}
	
	public static void main(String []args){
		Factor_2 test  = new Factor_2();
//		test.averageTFforPerClass("C:\\Users\\Ivar\\Desktop\\Sample\\C000007");
		test.resFacForAllClass();
	}
	
	
	
}
