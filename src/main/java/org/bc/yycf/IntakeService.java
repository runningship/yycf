package org.bc.yycf;

import org.bc.sdak.CommonDaoService;
import org.bc.sdak.TransactionalServiceHelper;
import org.bc.web.ModelAndView;
import org.bc.web.Module;
import org.bc.web.WebMethod;
import org.bc.yycf.entity.InTakeItem;

@Module(name="/intake/")
public class IntakeService {
	CommonDaoService dao = TransactionalServiceHelper.getTransactionalService(CommonDaoService.class);
	
	@WebMethod
	public ModelAndView addItem(InTakeItem item){
		ModelAndView mv = new ModelAndView();
		dao.saveOrUpdate(item);
		return mv;
	}
	
	@WebMethod
	public ModelAndView updateItem(InTakeItem item){
		ModelAndView mv = new ModelAndView();
		InTakeItem po = dao.get(InTakeItem.class, item.id);
		if(po!=null){
			po.value = item.value;
			dao.saveOrUpdate(po);
		}
		return mv;
	}
	
	@WebMethod
	public ModelAndView removeItem(InTakeItem item){
		ModelAndView mv = new ModelAndView();
		InTakeItem po = dao.get(InTakeItem.class, item.id);
		if(po!=null){
			dao.delete(po);
		}
		return mv;
	}
}
