package org.bc.yycf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
