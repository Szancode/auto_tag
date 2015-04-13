package auto_tagging;

import java.io.*;
import java.util.*;

//import jeasy.analysis.MMAnalyzer;
//import org.wltea.analyzer.lucene.IKAnalyzer;

public class TF_IDF {

 
    private static ArrayList<String> FileList = new ArrayList<String>(); 
    public static HashMap<String, Float> resIDF = new HashMap<String, Float>();
    public static  int fileNum;

    static{
    	try {
			FileList=ReadFile.readDirs("C:\\Users\\Ivar\\Desktop\\Sample");
			fileNum = FileList.size();
			resIDF = idf(tfAllFiles("C:\\Users\\Ivar\\Dektop\\Sample"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    //获得分好的词
    public static ArrayList<String> cutWords(String file) throws IOException{
        
        ArrayList<String> words = new ArrayList<String>();
        String text = ReadFile.readFile(file);
        
        Segment cs= new Segment();
        String temp= cs.split(text, "|");
        String []toArray= temp.split("\\|");
        words.addAll(Arrays.asList(Segment.dropStopWords(toArray)));
        return words;
    }
    
    //计算特征词在每片文档中出现的次数
    public static HashMap<String, Integer> normalTF(ArrayList<String> cutwords){
        HashMap<String, Integer> resTF = new HashMap<String, Integer>();
        
        for(String word : cutwords){
            if(resTF.get(word) == null){
                resTF.put(word, 1);
            }
            else{
                resTF.put(word, resTF.get(word) + 1);
            }
            System.out.println(word+resTF.get(word));
        }
        return resTF;
    }
    
    //计算文档中每个词的频率
    public static HashMap<String, Float> tf(ArrayList<String> cutwords){
        HashMap<String, Float> resTF = new HashMap<String, Float>();
        
        int wordLen = cutwords.size();
        HashMap<String, Integer> intTF = TF_IDF.normalTF(cutwords); 
        
        Iterator<Map.Entry<String, Integer>> iter = intTF.entrySet().iterator(); //iterator for that get from TF
        while(iter.hasNext()){
            Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>)iter.next();
            resTF.put(entry.getKey().toString(), Float.parseFloat(entry.getValue().toString()) / wordLen);
            System.out.println(entry.getKey().toString() + " = "+  Float.parseFloat(entry.getValue().toString()) / wordLen);
        }
        return resTF;
    } 
    
    //建立每个词的词频值字典
    public static HashMap<String, HashMap<String, Integer>> normalTFAllFiles(String dirc) throws IOException{
        HashMap<String, HashMap<String, Integer>> allNormalTF = new HashMap<String, HashMap<String,Integer>>();
        
        List<String> filelist =ReadFile.readDirs(dirc);
        for(String file : filelist){
            HashMap<String, Integer> dict = new HashMap<String, Integer>();
            ArrayList<String> cutwords = TF_IDF.cutWords(file); //get cut word for one file
            
            dict = TF_IDF.normalTF(cutwords);
            allNormalTF.put(file, dict);
        }    
        return allNormalTF;
    }
    
    //每个词的TF值的字典
    public static HashMap<String,HashMap<String, Float>> tfAllFiles(String dirc) throws IOException{
        HashMap<String, HashMap<String, Float>> allTF = new HashMap<String, HashMap<String, Float>>();
//        List<String> filelist = ReadFile.readDirs(dirc);
        
        for(String file : FileList){
            HashMap<String, Float> dict = new HashMap<String, Float>();
            ArrayList<String> cutwords = TF_IDF.cutWords(file); 
            
            dict = TF_IDF.tf(cutwords);
            allTF.put(file, dict);
        }
        return allTF;
    }
    //计算IDF的值
    public static HashMap<String, Float> idf(HashMap<String,HashMap<String, Float>> all_tf){
        HashMap<String, Float> resIdf = new HashMap<String, Float>();
        HashMap<String, Integer> dict = new HashMap<String, Integer>();
        int docNum = FileList.size();
        
        System.out.println(docNum);
        
        for(int i = 0; i < docNum; i++){
            HashMap<String, Float> temp = all_tf.get(FileList.get(i));
            Iterator<Map.Entry<String, Float>> iter = temp.entrySet().iterator();
            while(iter.hasNext()){
                Map.Entry<String, Float> entry = (Map.Entry<String, Float>)iter.next();
                String word = entry.getKey().toString();
                if(dict.get(word) == null){
                    dict.put(word, 1);
                }else {
                    dict.put(word, dict.get(word) + 1);
                }
            }
        }
        System.out.println("IDF for every word is:");
        Iterator<Map.Entry<String, Integer>> iter_dict = dict.entrySet().iterator();
        while(iter_dict.hasNext()){
            Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>)iter_dict.next();
            float value = (float)Math.log(docNum / Float.parseFloat(entry.getValue().toString()));
            resIdf.put(entry.getKey().toString(), value);
//            System.out.println(entry.getKey().toString() + " = " + value);
        }
        return resIdf;
    }
    
    
    
    
    public static HashMap<String, HashMap<String, Float>> tf_idf(HashMap<String,HashMap<String, Float>> all_tf,HashMap<String, Float> idfs){
        HashMap<String, HashMap<String, Float>> resTfIdf = new HashMap<String, HashMap<String, Float>>();
            
        int docNum = FileList.size();
        for(int i = 0; i < docNum; i++){
            String filepath = FileList.get(i);
            HashMap<String, Float> tfidf = new HashMap<String, Float>();
            HashMap<String, Float> temp = all_tf.get(filepath);
            Iterator<Map.Entry<String, Float>> iter = temp.entrySet().iterator();
            while(iter.hasNext()){
                Map.Entry<String, Float> entry = (Map.Entry<String, Float>)iter.next();
                String word = entry.getKey().toString();
                Float value = (float)Float.parseFloat(entry.getValue().toString()) * idfs.get(word); 
                tfidf.put(word, value);
            }
            resTfIdf.put(filepath, tfidf);
        }
        
//        System.out.println("TF-IDF for Every file is :");
//        DisTfIdf(resTfIdf);
        return resTfIdf;
    }
    public static void DisTfIdf(HashMap<String, HashMap<String, Float>> tfidf){
        Iterator<Map.Entry<String, HashMap<String, Float>>> iter1 = tfidf.entrySet().iterator();
        while(iter1.hasNext()){
            Map.Entry<String, HashMap<String, Float>> entrys = (Map.Entry<String, HashMap<String, Float>>)iter1.next();
            System.out.println("FileName: " + entrys.getKey().toString());
            System.out.print("{");
            HashMap<String, Float> temp = (HashMap<String, Float>) entrys.getValue();
            Iterator<Map.Entry<String, Float>> iter2 = temp.entrySet().iterator();
            while(iter2.hasNext()){
                Map.Entry<String, Float> entry = (Map.Entry<String, Float>)iter2.next(); 
                System.out.print(entry.getKey().toString() + " = " + entry.getValue().toString() + ", ");
            }
            System.out.println("}");
        }
        
    }
    public static void main(String[] args) throws IOException {
//        String file = "C:\\Users\\Ivar\\Desktop\\sample\\C000007";
        String file = "C:\\Users\\Ivar\\Desktop\\test";
//        String file= "C:\\Users\\Ivar\\Desktop\\SogouC.reduced";

        HashMap<String,HashMap<String, Float>> all_tf = tfAllFiles(file);
        System.out.println();
        HashMap<String, Float> idfs = idf(all_tf);
        System.out.println();
        tf_idf(all_tf, idfs);
        
    }

}