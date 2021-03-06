package org.bc.yycf.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class PCSUploadUtil {
	//post请求地址
    private final static String API = "https://pcs.baidu.com/rest/2.0/pcs/file";

    //本地文件路径, 例如:"/Users/macuser/Documents/workspace/test.jpg"
    private static String mLocalPath;

    //上传文件路径（含上传的文件名称), 例如:"/apps/yuantest/test.jpg"
    private static String mDestPath;

    //开发者准入标识 access_token, 通过OAuth获得
    private static String mAccessToken = "10.206eb7b8bc94316e4495d9d2c30e54a9.1442280408.1183630";

    public static void main(String[] args) throws IOException {
//        if (args.length != 3) {
//            System.out.println("Usage: PCSUploadDemo file_to_upload destination your_access_token");
//            return;
//        }

//        mLocalPath = args[0];
//        mDestPath = args[1];
//        mAccessToken = args[2];

//        File fileToUpload = new File(mLocalPath);
//        if (!(fileToUpload).isFile()) {
//            System.out.println("Input file_to_upload is invalid!");
//            return;
//        }
//
//        System.out.println("Uploading...");
//
//        Thread workerThread = new Thread(new Runnable() {
//            public void run() {						    		
//                try {
//                    doUpload();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        workerThread.start();
//        listFile();
    	getAccessToken();
        return ;
    }

    public static void getAccessToken() throws ClientProtocolException, IOException{
//    	String url = "http://openapi.baidu.com/oauth/2.0/authorize?client_id=axGu84VK3zxj8l7yPGCRBwIu&response_type=code&redirect_uri=http://mobile.zjb.tunnel.mobi/c/yycf/token/notifyToken";
    	String url = "https://openapi.baidu.com/oauth/2.0/token/grant_type=client_credentials&client_id=axGu84VK3zxj8l7yPGCRBwIu&client_secret=ACuouRfsNux9MuAI3GwvHzPUsNOibu21";
    	HttpGet httpGet= new HttpGet(url);
    	HttpClient client = new DefaultHttpClient();
    	HttpResponse response = client.execute(httpGet);
    	System.out.println(response.getStatusLine().toString());
        System.out.println(EntityUtils.toString(response.getEntity()));
    }
    public static void listFile() throws  IOException{
    	String url = API+"?method=list&access_token="+mAccessToken+"&path=/";
    	HttpGet httpGet= new HttpGet(url);
    	HttpClient client = new DefaultHttpClient();
    	HttpResponse response = client.execute(httpGet);
    	System.out.println(response.getStatusLine().toString());
        System.out.println(EntityUtils.toString(response.getEntity()));
    }
    public static void doUpload() throws IOException{
        File fileToUpload = new File(mLocalPath);

        if(null != fileToUpload && fileToUpload.length() > 0){

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("method", "upload"));
            params.add(new BasicNameValuePair("access_token", mAccessToken));
            params.add(new BasicNameValuePair("path", mDestPath));

            //添加请求参数，通过POST表单进行传递，除上传文件内容之外的其它参数通过query_string进行传递。
            String postUrl = API + "?" + buildParams(params); 

            HttpPost post = new HttpPost(postUrl);

            //添加文件内容，必须使用multipart/form-data编码的HTTP entity
            MultipartEntity entity = new MultipartEntity();
            FileBody fo = new FileBody(fileToUpload);
            entity.addPart("uploaded",fo);
            post.setEntity(entity);

            //创建client
            HttpClient client = new DefaultHttpClient();

            //发送请求
            HttpResponse response = client.execute(post);

            System.out.println(response.getStatusLine().toString());
            System.out.println(EntityUtils.toString(response.getEntity()));

        }		    
    }

    // url encoded query string
    protected static String buildParams(List<NameValuePair> urlParams){		
        String ret = null;		
        if(null != urlParams && urlParams.size() > 0){			
            try {
                // 指定HTTP.UTF_8为charset参数以保证中文文件路径的正确
                HttpEntity paramsEntity = new UrlEncodedFormEntity(urlParams, HTTP.UTF_8);
                ret = EntityUtils.toString(paramsEntity);
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }		
        return ret;
    }
}
