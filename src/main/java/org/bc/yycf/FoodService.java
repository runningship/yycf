package org.bc.yycf;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.bc.sdak.CommonDaoService;
import org.bc.sdak.GException;
import org.bc.sdak.Page;
import org.bc.sdak.TransactionalServiceHelper;
import org.bc.sdak.utils.JSONHelper;
import org.bc.web.ModelAndView;
import org.bc.web.Module;
import org.bc.web.PlatformExceptionType;
import org.bc.web.WebMethod;
import org.bc.yycf.entity.Food;
import org.bc.yycf.entity.Nutrient;
import org.bc.yycf.util.DataHelper;

@Module(name="/admin/food")
public class FoodService {
	CommonDaoService dao = TransactionalServiceHelper.getTransactionalService(CommonDaoService.class);
	
	@WebMethod
	public ModelAndView listData(Page<Map> page , String name){
		ModelAndView mv = new ModelAndView();
		StringBuilder hql = new StringBuilder("select id as id ,name as name, alias as alias,imgPath as imgPath from Food where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if(StringUtils.isNotEmpty(name)){
			name = name.trim();
			hql.append(" and name like ? ");
			params.add("%"+name+"%");
			
			String py = DataHelper.toPinyin(name);
			hql.append(" or py like ? ");
			params.add("%"+py+"%");
		}
		page.setPageSize(25);
		page = dao.findPage(page, hql.toString(), true, params.toArray());
		mv.data = JSONHelper.toJSON(page);
		return mv;
	}
	
	@WebMethod
	public ModelAndView add(){
		ModelAndView mv = new ModelAndView();
		return mv;
	}
	
	@WebMethod
	public ModelAndView cacu(String data){
		ModelAndView mv = new ModelAndView();
		JSONArray arr = JSONArray.fromObject(data);
		Map<String , JSONObject> result = new HashMap<String , JSONObject>();
		for(int i=0; i<arr.size(); i++){
			JSONObject jobj = arr.getJSONObject(i);
			String foodId = jobj.getString("foodId");
			String value = jobj.getString("value");
			List<Nutrient> list = dao.listByParams(Nutrient.class, "from Nutrient where foodId=?	order by leibie",Integer.valueOf(foodId));
			for(Nutrient nutrient : list){
				if(!result.containsKey(nutrient.name)){
					JSONObject tmp = new JSONObject();
					tmp.put("value", 0f);
					result.put(nutrient.name, tmp);
				}
				JSONObject obj = result.get(nutrient.name);
				Double xx = obj.getDouble("value")+  nutrient.value*Float.valueOf(value)/100;
				DecimalFormat df = new DecimalFormat("###.0000");
				obj.put("value", df.format(xx));
				obj.put("unit", nutrient.unit);
				result.put(nutrient.name, obj);
			}
		}
		
		//to array
		JSONArray arrResult = new JSONArray();
		int classIndex=0;
		for(String key : result.keySet()){
			JSONObject obj = new JSONObject();
			obj.put("name", key);
			JSONObject target = result.get(key);
			obj.put("value", target.getDouble("value"));
			obj.put("unit", target.getString("unit"));
			obj.put("classIndex", classIndex);
			arrResult.add(obj);
			classIndex = classIndex^1;
		}
		mv.data.put("result", arrResult);
		return mv;
	}
	
	@WebMethod
	public ModelAndView save(Food food){
		ModelAndView mv = new ModelAndView();
		Food po = dao.getUniqueByKeyValue(Food.class, "name", food.name);
		if(po!=null){
			throw new GException(PlatformExceptionType.BusinessException, "存在相同名称");
		}
		food.addtime = new Date();
		dao.saveOrUpdate(food);
		return mv;
	}
	
	@WebMethod
	public ModelAndView update(Food food){
		ModelAndView mv = new ModelAndView();
		food.updatetime = new Date();
		dao.saveOrUpdate(food);
		return mv;
	}
	
	@WebMethod
	public ModelAndView edit(String id){
		ModelAndView mv = new ModelAndView();
		Food po = dao.get(Food.class, id);
		mv.jspData.put("food", po);
		return mv;
	}
	
	@WebMethod
	public ModelAndView view(int id){
		ModelAndView mv = new ModelAndView();
		return mv;
	}
	
	@WebMethod
	public ModelAndView delete(int id){
		ModelAndView mv = new ModelAndView();
		return mv;
	}
}
