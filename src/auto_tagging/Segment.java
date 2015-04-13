package auto_tagging;


import java.io.IOException;
import java.util.Vector;
import jeasy.analysis.MMAnalyzer;

public class Segment {
	private MMAnalyzer analyzer;
	
    public Segment() {
    	/*
		����Ϊ�ִ����ȣ����������ڻ򳬹��ò��������ܳɴʣ��ôʾͱ��зֳ���
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
     * ȥ��ͣ�ô�.
     * 
     * @param oldText
     *            �������ı�
     * @return ȥͣ�ôʺ���
     * @throws IOException 
     */
    public static String[] dropStopWords(final String[] oldText) throws IOException {
        Vector<String> v1 = new Vector<String>();
        StopWordsHandler SWH= new StopWordsHandler();
        for (int i = 0; i < oldText.length; ++i) {
            if (!SWH.isStopWord(oldText[i])) { // ����ͣ�ô�
                v1.add(oldText[i]);
            }
        }
        String[] newText = new String[v1.size()];
        v1.toArray(newText);
        return newText;
    }
}