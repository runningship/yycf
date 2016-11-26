package org.bc.reason.core.job;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class MakeWordsLibido extends Thread{

	//http://zuci.51240.com/大__zuci/
	
	@Override
	public void run() {
		InputStream is = MakeWordsLibido.class.getResourceAsStream("words.txt");
		try {
			String str = IOUtils.toString(is);
			for(int i = 0;i<str.length();i++){
				if(isChinese(str.charAt(i))){
					System.out.println(str.charAt(i));
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	 // 根据Unicode编码完美的判断中文汉字和符号
    private static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
            return true;
        }
        return false;
    }
    
	public static void main(String[] args){
		MakeWordsLibido mwLibido = new MakeWordsLibido();
		mwLibido.start();
	}
}
