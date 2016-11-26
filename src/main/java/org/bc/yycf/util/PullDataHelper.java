package org.bc.yycf.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.IOUtils;

public class PullDataHelper {

	public final static int errorReportUserId= 36;

	public static String getHttpData(String urlStr , String encode) throws IOException{
		URL url = new URL(urlStr);
		URLConnection conn = url.openConnection();
		conn.setDefaultUseCaches(false);
		conn.setUseCaches(false);
		conn.setConnectTimeout(10000);
		conn.setReadTimeout(10000);
		conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; CIBA)");
		String contentEncoding = conn.getContentEncoding();
		String result ="";
		if("gzip".equals(contentEncoding)){
			GZIPInputStream zipStream = new GZIPInputStream(conn.getInputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(zipStream,encode));
			StringBuffer sb = new StringBuffer();
            String line = "";  
            while((line = reader.readLine()) != null) {  
                sb.append(line).append("\n");
            }
            reader.close();
            zipStream.close();
            result = sb.toString();
		}else{
			result = IOUtils.toString(conn.getInputStream(),encode);
		}
		return result;
	}
}
