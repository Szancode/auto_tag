package auto_tagging;


import java.io.IOException;
import java.util.Vector;
import jeasy.analysis.MMAnalyzer;

public class Segment {
	private MMAnalyzer analyzer;
	
    public Segment() {
    	/*
		参数为分词粒度：当字数等于或超过该参数，且能成词，该词就被切分出来
		*/
    	analyzer = new MMAnalyzer(2);
    }

    public String split(final String text, final String splitToken)
    {
        String result = null;
        
        try {
            result = analyzer.segment(text, splitToken);
        	} 
        catch (IOException e)
        		{
        		e.printStackTrace();
        		}
        return result;
    }

    /**
     * 去掉停用词.
     * 
     * @param oldText
     *            给定的文本
     * @return 去停用词后结果
     * @throws IOException 
     */
    public static String[] dropStopWords(final String[] oldText) throws IOException {
        Vector<String> v1 = new Vector<String>();
        StopWordsHandler SWH= new StopWordsHandler();
        for (int i = 0; i < oldText.length; ++i) {
            if (!SWH.isStopWord(oldText[i])) { // 不是停用词
                v1.add(oldText[i]);
            }
        }
        String[] newText = new String[v1.size()];
        v1.toArray(newText);
        return newText;
    }
}