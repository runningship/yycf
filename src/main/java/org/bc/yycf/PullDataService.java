package org.bc.yycf;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;
import org.bc.sdak.CommonDaoService;
import org.bc.sdak.TransactionalServiceHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PullDataService {
	CommonDaoService dao = TransactionalServiceHelper.getTransactionalService(CommonDaoService.class);

	public static void main(String[] args) throws IOException{
		//获取分类下食物
		Elements types = getTypes();
		for(Element elem : types){
			System.out.println(elem.attr("href")+"-"+elem.text());
			Elements foods = getFoodOfType(elem.attr("href"));
			for(Element food : foods){
				processFood(food);
			}
		}
	}
	
	private static void processFood(Element food) {
		System.out.println(food.attr("href")+"-"+food.text());
	}

	public static Elements getTypes() throws IOException{
		String url = "http://www.yingyangzidian.com/index.php";
		String html = getHttpData(url);
		Document page = Jsoup.parse(html);
		return page.select("#yyfood_contents li a");
	}
	
	public static Elements getFoodOfType(String suffix) throws IOException{
		String url = "http://www.yingyangzidian.com/"+suffix;
		String html = getHttpData(url);
		Document page = Jsoup.parse(html);
		return page.select("[id=food_name] a");
	}
	public static String getHttpData(String urlStr) throws IOException{
		URL url = new URL(urlStr);
		URLConnection conn = url.openConnection();
		conn.setDefaultUseCaches(false);
		conn.setUseCaches(false);
		conn.setConnectTimeout(10000);
		conn.setReadTimeout(10000);
		String result = IOUtils.toString(conn.getInputStream(),"utf-8");
		return result;
	}
}
