package Hisign.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import Hisign.dao.FeedbackDao;

import Hisign.entity.DepartmentDe;
import Hisign.entity.FeedbackInfor;

import Hisign.entity.UserInfor;

@Controller("FeedbackController")
public class FeedbackController {
	   final Logger logger = LoggerFactory.getLogger(this.getClass());	   
	   @RequestMapping(value = "/Feedback/newfeed",method = RequestMethod.POST)
	   public ModelAndView applyFeedback(@ModelAttribute("FeedbackInfor") FeedbackInfor FeedbackInfor,HttpServletRequest request) throws Exception {
    	   
    	   
	       String dctid = String.valueOf(System.currentTimeMillis()) ;  
	       String anonymous=request.getParameter("anonymous");
		   String complaint_jobnum = request.getParameter("job_number");
		   String complaint_people = request.getParameter("complaint_people");
		   String complaint_depart= request.getParameter("borrowde2");
		   String complaint_content = request.getParameter("complaint_content");
		   String recive_people= request.getParameter("link_spname"); 
		   	   
		   FeedbackInfor fbl = new FeedbackInfor();
		   if(anonymous.equals("非匿名")){
			   fbl.setComplaint_depart(complaint_depart);
			   fbl.setComplaint_jobnum(complaint_jobnum);
			   fbl.setComplaint_people(complaint_people);
		   }
		   fbl.setDctid(dctid);
		   fbl.setAnonymous(anonymous);
		   fbl.setComplaint_content(complaint_content);
		   
		   long time = System.currentTimeMillis();
  		   Date date = new Date(time);
  		   SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  		   String complaint_time=dateformat.format(date);
  		  // Date date2 = new Date(time);
  		   SimpleDateFormat dateF = new SimpleDateFormat("yyyy-MM-dd");
  		   String test_time=dateF.format(date);
  		   fbl.setTest_time(test_time);
		   fbl.setComplaint_time(complaint_time);
		   fbl.setRecive_people(recive_people);
		    
		   ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");  
	       //获得bean
	       FeedbackDao fdb = ctx.getBean(FeedbackDao.class);
	       fdb.Insert(fbl);
	       ModelAndView mav = new ModelAndView();
	       mav.setViewName("/dingdingweb/HUI/contract/AddSuccess");
	       mav.addObject("opmsg", "提交成功!");
	       return mav;
		
	       }
	   /**
	    * 收到投诉
	    * @param request
	    * @return
	    */
	   @RequestMapping(value = "/Feedback/toMydeal",method = RequestMethod.GET)
     	public ModelAndView toReciveRepair(HttpServletRequest request){
    		String userjson = request.getParameter("info");
     		JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
     		UserInfor usi = new UserInfor();
     		usi.setAvatar(js.getString("avatar"));
     		usi.setName(js.getString("name"));
     		usi.setUserid(js.getString("userid"));
     		usi.setJobnumber(js.getString("jobnumber"));
     	    ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
   		  //获得bean
   		    FeedbackDao feedbackDao = ctx.getBean(FeedbackDao.class);
   		    String searchname=js.getString("name");
   		    List<FeedbackInfor> feedlist = feedbackDao.Recive(searchname);
   		    List<UserInfor> userinfoList = new ArrayList<>();
     		userinfoList.add(usi);
     		ModelAndView mav = new ModelAndView();
     		mav.addObject("feedlist", feedlist);
     		mav.addObject("userinfoList", userinfoList);
     		mav.addObject("userjson", userjson.replaceAll("\"", "'"));
     		mav.setViewName("/dingdingweb/HUI/feedback/mydeal");
     		return mav;
     	}
	   /**
	    * 投诉动态
	    * @param request
	    * @return
	    */
	   @RequestMapping(value = "/Feedback/tofeedbacking",method = RequestMethod.GET)
    	public ModelAndView feedBacking(HttpServletRequest request){
   		String userjson = request.getParameter("info");
    		JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
    		UserInfor usi = new UserInfor();
    		usi.setAvatar(js.getString("avatar"));
    		usi.setName(js.getString("name"));
    		usi.setUserid(js.getString("userid"));
    		usi.setJobnumber(js.getString("jobnumber"));
    	    ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
  		  //获得bean
  		    FeedbackDao feedbackDao = ctx.getBean(FeedbackDao.class);
  		    String searchname=js.getString("name");
  		    List<FeedbackInfor> feedlist = feedbackDao.Backing(searchname);
    		List<UserInfor> userinfoList = new ArrayList<>();
    		userinfoList.add(usi);
    		ModelAndView mav = new ModelAndView();
    		mav.addObject("feedlist", feedlist);
    		mav.addObject("userinfoList", userinfoList);
    		mav.addObject("userjson", userjson.replaceAll("\"", "'"));
    		mav.setViewName("/dingdingweb/HUI/feedback/feedbacking");
    		return mav;
    	}
	   
	   /**
	    * 根据时间查找
	    * @param request
	    * @return
	    */
	   @RequestMapping(value = "/Feedback/toFind",method = RequestMethod.GET)
   	public ModelAndView toFind(HttpServletRequest request){
  		String userjson = request.getParameter("info");
   		JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
   		UserInfor usi = new UserInfor();
   		usi.setAvatar(js.getString("avatar"));
   		usi.setName(js.getString("name"));
   		usi.setUserid(js.getString("userid"));
   		usi.setJobnumber(js.getString("jobnumber"));
   		List<UserInfor> userinfoList = new ArrayList<>();
   		userinfoList.add(usi);
   		ModelAndView mav = new ModelAndView();
   		mav.addObject("userinfoList", userinfoList);
   		mav.addObject("userjson", userjson.replaceAll("\"", "'"));
   		mav.setViewName("/dingdingweb/HUI/feedback/select");
   		return mav;
   	}
	   /**
	    * 提交反馈
	    * @param request
	    * @return
	    */
	   @RequestMapping(value = "/feedback/tobacked",method = RequestMethod.POST)
     	public ModelAndView toBack(HttpServletRequest request){
   		    String dctid = request.getParameter("dctid");
     		String back_content=request.getParameter("back_content");
     		FeedbackInfor fdb = new FeedbackInfor();
     		fdb.setBack_content(back_content);
     		fdb.setDctid(dctid);
     		long time = System.currentTimeMillis();
     		Date date = new Date(time);
     		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     		String back_time=dateformat.format(date);
     		fdb.setBack_time(back_time);
     		ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");  
             //获得bean
             FeedbackDao feedback = ctx.getBean(FeedbackDao.class);
             feedback.toBakcontent(fdb);
             ModelAndView mav = new ModelAndView();
             mav.setViewName("/dingdingweb/HUI/contract/AddSuccess");
             mav.addObject("opmsg", "提交成功!");
     		return mav;
     	}
	   /**
	    * 提交评价
	    * @param request
	    * @return
	    */
	   @RequestMapping(value = "/feedback/torating",method = RequestMethod.POST)
    	public ModelAndView toRating(HttpServletRequest request){
  		    String dctid = request.getParameter("dctid");
    		String service_rating=request.getParameter("service_rating");
    		long time = System.currentTimeMillis();
    		Date date = new Date(time);
    		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		String done_time=dateformat.format(date);
    		FeedbackInfor fdb = new FeedbackInfor();
    		fdb.setService_rating(service_rating);
    		fdb.setDctid(dctid);
    		fdb.setDone_time(done_time);
    		
    		ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");  
            //获得bean
            FeedbackDao feedback = ctx.getBean(FeedbackDao.class);
            feedback.toRating(fdb);
            ModelAndView mav = new ModelAndView();
            mav.setViewName("/dingdingweb/HUI/contract/AddSuccess");
            mav.addObject("opmsg", "提交成功!");
    		return mav;
    	}
	   
     	/**
     	 * 跳转到新增反馈
     	 */
     	@RequestMapping(value = "/Feedback/tonewSugestion",method = RequestMethod.GET)
      	public ModelAndView tonewadd(HttpServletRequest request){
      		String userjson = request.getParameter("info");
      		JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
      		UserInfor usi = new UserInfor();
      		usi.setAvatar(js.getString("avatar"));
      		usi.setName(js.getString("name"));
      		usi.setUserid(js.getString("userid"));
      		usi.setJobnumber(js.getString("jobnumber"));
      		JSONArray depart = js.getJSONArray("department");
      		List<DepartmentDe> departList = new ArrayList<>();
      		String fid = depart.getString(0);
      		String zid = depart.getString(0);
      		 	DepartmentDe dpd = new DepartmentDe();
      			dpd.setFid(fid);
      			dpd.setZid(zid);
      			departList.add(dpd);
      		List<UserInfor> userinfoList = new ArrayList<>();
      		userinfoList.add(usi);
      		ModelAndView mav = new ModelAndView();
      		mav.addObject("userinfoList", userinfoList);
      		mav.addObject("userjson", userjson.replaceAll("\"", "'"));
      		mav.addObject("departList", departList);
      		mav.setViewName("/dingdingweb/HUI/feedback/newSuggestion");
      		return mav;
      	}
     	
     	
	   
	   
	   
}
