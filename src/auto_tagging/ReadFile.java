package auto_tagging;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class ReadFile {

	public static ArrayList<String> filelist = new ArrayList<String>();
	
    public static ArrayList<String> readDirs(String filepath) throws FileNotFoundException, IOException
    {
//    	ArrayList<String> filelist = new ArrayList<String>();
        try
        {
            File file = new File(filepath);
            if(!file.isDirectory())
            {
                System.out.print("输入的");
                System.out.println("filepath:\n" + file.getAbsolutePath());
            }
            else
            {
                String[] flist = file.list();
                for(int i = 0; i < flist.length; i++)
                {
                    File newfile = new File(filepath + "\\" + flist[i]);
                    if(!newfile.isDirectory())
                    {
                        filelist.add(newfile.getAbsolutePath());
                    }
                    else if(newfile.isDirectory()) //如果是路径，继续调用
                    {
                        readDirs(filepath + "\\" + flist[i]);
                    }                    
                }
            }
        }catch(FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
        return filelist;
    }
    
    //读入文件
    public static String readFile(String file) throws FileNotFoundException, IOException
    {
        StringBuffer strSb = new StringBuffer(); //String is constant， StringBuffer can be changed.
        InputStreamReader inStrR = new InputStreamReader(new FileInputStream(file), "gbk"); //byte streams to character streams
        BufferedReader br = new BufferedReader(inStrR); 
        String line = br.readLine();
        while(line != null){
            strSb.append(line).append("\r\n");
            line = br.readLine();    
        }
        
        return strSb.toString();
    }
    
    

}
