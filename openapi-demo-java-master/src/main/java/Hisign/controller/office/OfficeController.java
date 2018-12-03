package Hisign.controller.office;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import Hisign.controller.process.AesEncryptUtil;
import Hisign.dao.OfficeDao;
import Hisign.dao.ProcessDao;
import Hisign.entity.ApplyCarInfor;
import Hisign.entity.ApplyStampInfor;
import Hisign.entity.BusinessCardInfor;
import Hisign.entity.Car;
import Hisign.entity.ContractInfor;
import Hisign.entity.CourierInfor;
import Hisign.entity.StampCertificateInfor;
import Hisign.entity.UserInfor;

/**
 * 行政办公controller
 * @author Administrator
 *
 */
@Controller("OfficeController")
public class OfficeController {
	final  AesEncryptUtil aesEncryptUtil = new AesEncryptUtil();

	/**
	 * 跳转到行政办公
	 * @param request
	 * @return
	 */
	@RequestMapping("/Office/Office")
	public ModelAndView toOffice(HttpServletRequest request,ModelAndView mav){	
		String userjson=null;
		try {
			userjson = aesEncryptUtil.desEncrypt(request.getParameter("info"));
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(userjson);
		if (userjson.equals("undefined")) {	
			mav.setViewName("/dingdingweb/HUI/err");
		}else {
			JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
//			System.out.println("tonewselect"+userjson);
			UserInfor usi = new UserInfor();
			usi.setAvatar(js.getString("avatar"));
			usi.setName(js.getString("name"));
			usi.setUserid(js.getString("userid"));
			usi.setDepartment(js.getString("department"));
			List<UserInfor> userinfoList = new ArrayList<>();
			userinfoList.add(usi);
			mav.addObject("userinfoList", userinfoList);
			mav.addObject("userjson", userjson.replaceAll("\"", "'"));
			mav.setViewName("/dingdingweb/HUI/office/office");
		}
		return mav;
	}
	
	
	/**
	 * 跳转到快递发放主页
	 * @param request
	 * @return
	 */
	@RequestMapping("/Office/Courier/courier")
	public ModelAndView toCourier(HttpServletRequest request,ModelAndView mav){
		String userjson=null;
		try {
			userjson = aesEncryptUtil.desEncrypt(request.getParameter("info"));
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(userjson);
		if (userjson.equals("undefined")) {	
			mav.setViewName("/dingdingweb/HUI/err");
		}else {
			JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
//			System.out.println("tonewselect"+userjson);
			UserInfor usi = new UserInfor();
			usi.setAvatar(js.getString("avatar"));
			usi.setName(js.getString("name"));
			usi.setUserid(js.getString("userid"));
			usi.setDepartment(js.getString("department"));
			List<UserInfor> userinfoList = new ArrayList<>();
			userinfoList.add(usi);
			mav.addObject("userinfoList", userinfoList);
			mav.addObject("userjson", userjson.replaceAll("\"", "'"));
			mav.setViewName("/dingdingweb/HUI/office/courier/courier");
		}
		return mav;
	}
	
	
	/**
	 * 跳转到发送快递提醒
	 * @param request
	 * @return
	 */
	@RequestMapping("/Office/Courier/SendNotice")
	public ModelAndView toSendNotice(HttpServletRequest request){		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/dingdingweb/HUI/office/courier/sendnotice.jsp");
		return mav;
	}
	
	/**
	 * 保存提交的快递提醒信息入库
	 * @param request
	 * @param reponse
	 */
	@RequestMapping("/SaveNotice/toBase")
	public void saveNotice2Base(HttpServletRequest request,HttpServletResponse response){
		String fsname=request.getParameter("fsname");//发送人姓名
		String dcnumber="1"+String.valueOf(System.currentTimeMillis());
		String recipients=request.getParameter("recipients");
		if (recipients.contains("(")) {
			recipients=recipients.substring(0, recipients.indexOf("("));
		}
		String express_no=request.getParameter("express-no");
		String notice_time=request.getParameter("notice-time");
		String pay_type=request.getParameter("radio");
		String notice_word=request.getParameter("notice-word");
		String lq=request.getParameter("lq");
		String express=request.getParameter("express");
		CourierInfor courier = new CourierInfor();
		courier.setDcnumber(dcnumber);
		courier.setRecipients(recipients);
		courier.setExpress_no(express_no);
		courier.setNotice_time(notice_time);
		courier.setPay_type(pay_type);
		courier.setNotice_word(notice_word);
		courier.setLq(lq);
		courier.setExpress(express);
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-mybatis.xml");
		// 获得bean
		boolean ret =false;
		OfficeDao officeDao = ctx.getBean(OfficeDao.class);
		ProcessDao processDao = ctx.getBean(ProcessDao.class);
		try {
			officeDao.SaveCourierNotice(courier);
		} catch (Exception e1) {
			try {			
				PrintWriter out = response.getWriter();
				out = response.getWriter();
				out.print("失败");
				out.flush();
				out.close();
//				response.getWriter().append("成功");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ret=true;
			e1.printStackTrace();
		}
		if (ret==false) {
			processDao.insert(dcnumber, fsname, recipients, "快递发放");
			try {			
				PrintWriter out = response.getWriter();
				out = response.getWriter();
				out.print("已提交");
				out.flush();
				out.close();
//				response.getWriter().append("成功");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}
	
	/**
	 * 跳转到快递提醒查询
	 * @param request
	 * @return
	 */
	@RequestMapping("/Office/toSearchCourierNotice")
	public ModelAndView toselectCourierNotice(HttpServletRequest request,ModelAndView mav){
		String userjson=null;
		try {
			userjson = aesEncryptUtil.desEncrypt(request.getParameter("info"));
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(userjson);
		if (userjson.equals("undefined")) {	
			mav.setViewName("/dingdingweb/HUI/err");
		}else {
			JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
//			System.out.println("tonewselect"+userjson);
			UserInfor usi = new UserInfor();
			usi.setAvatar(js.getString("avatar"));
			usi.setName(js.getString("name"));
			usi.setUserid(js.getString("userid"));
			usi.setDepartment(js.getString("department"));
			List<UserInfor> userinfoList = new ArrayList<>();
			userinfoList.add(usi);
			mav.addObject("userinfoList", userinfoList);
			mav.addObject("userjson", userjson.replaceAll("\"", "'"));
			mav.setViewName("/dingdingweb/HUI/office/courier/selectnotice");
		}
		return mav;
		
	}
	
	
	/**
	 * 查询快递提醒信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/Office/SearchCourierNotice")
	public  void selectCourierNotice(HttpServletRequest request,HttpServletResponse response){
		String searchkey = request.getParameter("searchkey");
		String pageNum = request.getParameter("pageNum");
		PageHelper.startPage(Integer.parseInt(pageNum), 12);
		List<CourierInfor> bList = selectCourier(searchkey);
		PageInfo<CourierInfor> pageInfo = new PageInfo<CourierInfor>(bList);
		JSONObject re = new JSONObject();
		re.put("blist", pageInfo.getList());
		re.put("pageInfo", pageInfo);
		try {			
			PrintWriter out = response.getWriter();
			out = response.getWriter();
			out.print(re);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<CourierInfor>  selectCourier(String searchkey){
//      System.out.println("start-------------------------");
      //初始化容器
      ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
      //获得bean
      OfficeDao officeDao = ctx.getBean(OfficeDao.class);
      List<CourierInfor> courierlist = officeDao.SelectCourierNotice(searchkey);
//      for(CourierInfor i: courierlist){
//      }
		return courierlist; 
	}
	
	
	/**
	 * 跳转到领取人登记
	 * @param request
	 * @return
	 */
	@RequestMapping("/Office/toReLq")
	public ModelAndView toRelq(HttpServletRequest request,ModelAndView mav){
		String userjson=null;
		try {
			userjson = aesEncryptUtil.desEncrypt(request.getParameter("info"));
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(userjson);
		if (userjson.equals("undefined")) {	
			mav.setViewName("/dingdingweb/HUI/err");
		}else {
			JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
//			System.out.println("tonewselect"+userjson);
			UserInfor usi = new UserInfor();
			usi.setAvatar(js.getString("avatar"));
			usi.setName(js.getString("name"));
			usi.setUserid(js.getString("userid"));
			usi.setDepartment(js.getString("department"));
			List<UserInfor> userinfoList = new ArrayList<>();
			userinfoList.add(usi);
//			List<CourierInfor> courierlist = selectRelq();
			mav.addObject("userinfoList", userinfoList);
			mav.addObject("userjson", userjson.replaceAll("\"", "'"));
//			mav.addObject("courierlist",courierlist);
			mav.setViewName("/dingdingweb/HUI/office/courier/relq");
		}
		return mav;	
		
	}
	
	/**
	 * 获取需要登记的快递消息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/Office/Courier/getRelq")
	public void getRelqInfor(HttpServletRequest request,HttpServletResponse response){
		String pageNum = request.getParameter("pageNum");
		PageHelper.startPage(Integer.parseInt(pageNum), 12);
		List<CourierInfor> bList = selectRelq();
		PageInfo<CourierInfor> pageInfo = new PageInfo<CourierInfor>(bList);
		JSONObject re = new JSONObject();
		re.put("blist", pageInfo.getList());
		re.put("pageInfo", pageInfo);
		try {			
			PrintWriter out = response.getWriter();
			out = response.getWriter();
			out.print(re);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<CourierInfor>  selectRelq(){
//      System.out.println("start-------------------------");
      //初始化容器
      ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
      //获得bean
      OfficeDao officeDao = ctx.getBean(OfficeDao.class);
      List<CourierInfor> courierlist = officeDao.SearchRelq();
//      for(CourierInfor i: courierlist){
//    	  System.out.println(i.getRecipients());
//      }
		return courierlist; 
	}
	
	
	/**
	 * 保存登记领取人信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/Office/SaveRelq")
	public ModelAndView saveRelq(HttpServletRequest request){
		String dcnumber=request.getParameter("dcnumber");
		String recipients=request.getParameter("recipients");
		String express_no=request.getParameter("express-no");
		String notice_time=request.getParameter("notice-time");
		String pay_type=request.getParameter("radio");
		String notice_word=request.getParameter("notice-word");
		String lq=request.getParameter("lq");
		String express=request.getParameter("express");
		CourierInfor courier = new CourierInfor();
		courier.setDcnumber(dcnumber);
		courier.setRecipients(recipients);
		courier.setExpress_no(express_no);
		courier.setNotice_time(notice_time);
		courier.setPay_type(pay_type);
		courier.setNotice_word(notice_word);
		courier.setLq(lq);
		courier.setExpress(express);
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-mybatis.xml");
		// 获得bean
		OfficeDao officeDao = ctx.getBean(OfficeDao.class);
		officeDao.SaverRelq(courier);
		return new ModelAndView("/dingdingweb/HUI/contract/AddSuccess");
		
	}
	
	
	/**
	 * 跳转到我的快递
	 * @param request
	 * @return
	 */
	@RequestMapping("/MyCourier")
	public ModelAndView tomyCourier(HttpServletRequest request,ModelAndView mav){
		String userjson=null;
		try {
			userjson = aesEncryptUtil.desEncrypt(request.getParameter("info"));
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(userjson);
		if (userjson.equals("undefined")) {	
			mav.setViewName("/dingdingweb/HUI/err");
		}else {
			JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
//			System.out.println("tonewselect"+userjson);
			UserInfor usi = new UserInfor();
			usi.setAvatar(js.getString("avatar"));
			usi.setName(js.getString("name"));
			usi.setUserid(js.getString("userid"));
			usi.setDepartment(js.getString("department"));
			List<UserInfor> userinfoList = new ArrayList<>();
			userinfoList.add(usi);
			String searchname= js.getString("name");
//			List<CourierInfor> courierlist = selectmyCourier(searchname);
			mav.addObject("userinfoList", userinfoList);
			mav.addObject("userjson", userjson.replaceAll("\"", "'"));
//			mav.addObject("courierlist",courierlist);
			mav.setViewName("/dingdingweb/HUI/office/courier/mycourier");
		}
		return mav;	
	}
	
	
	/**
	 * 获取我的快递信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/Office/Courier/getMyCourier")
	public void getMyCourierInfor(HttpServletRequest request,HttpServletResponse response){
		
		String searchkey = request.getParameter("searchkey").replace("欢迎您 : ", "");
		String pageNum=request.getParameter("pageNum");
		PageHelper.startPage(Integer.parseInt(pageNum), 12);
		List<CourierInfor> bList = selectmyCourier(searchkey);
		PageInfo<CourierInfor> pageInfo = new PageInfo<CourierInfor>(bList);
		JSONObject re = new JSONObject();
		re.put("blist", pageInfo.getList());
		re.put("pageInfo", pageInfo);
		try {			
			PrintWriter out = response.getWriter();
			out = response.getWriter();
			out.print(re);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<CourierInfor>  selectmyCourier(String searchkey){
//      System.out.println("start-------------------------");
      //初始化容器
      ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
      //获得bean
      OfficeDao officeDao = ctx.getBean(OfficeDao.class);
      List<CourierInfor> courierlist = officeDao.MyCourier(searchkey);
//      for(CourierInfor i: courierlist){
//      }
		return courierlist; 
	}
	
	/**
	 * 保存已领取的我的快递
	 * @param request
	 * @param response
	 */
	@RequestMapping("/Office/SaveMyCourier")
	public ModelAndView saveMyCourier(HttpServletRequest request){
		String dcnumber=request.getParameter("dcnumber");
		String recipients=request.getParameter("recipients");
		String express_no=request.getParameter("express-no");
		String notice_time=request.getParameter("notice-time");
		String pay_type=request.getParameter("radio");
		String notice_word=request.getParameter("notice-word");
		String lq=request.getParameter("lq");
		String express=request.getParameter("express");
		String note = request.getParameter("note");
		String evaluation = request.getParameter("evaluation");
		CourierInfor courier = new CourierInfor();
		courier.setDcnumber(dcnumber);
		courier.setRecipients(recipients);
		courier.setExpress_no(express_no);
		courier.setNotice_time(notice_time);
		courier.setPay_type(pay_type);
		courier.setNotice_word(notice_word);
		courier.setLq(lq);
		courier.setExpress(express);
		courier.setNote(note);
		courier.setEvaluation(evaluation);
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-mybatis.xml");
		// 获得bean
		OfficeDao officeDao = ctx.getBean(OfficeDao.class);
		officeDao.SaveMyCourier(courier);
		
		ProcessDao processDao = ctx.getBean(ProcessDao.class);
		processDao.updateState(dcnumber);
		return new ModelAndView("/dingdingweb/HUI/contract/AddSuccess");		
	}
	
	/***************************************************************************************************/
	
	
	/**
	 * 跳转到车辆列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/Office/ApplyCar/carList")
	public ModelAndView toCarList(HttpServletRequest request,ModelAndView mav){
		String userjson=null;
		try {
			userjson = aesEncryptUtil.desEncrypt(request.getParameter("info"));
		} catch (Exception e) {
			e.printStackTrace();
		}
//		System.out.println(userjson);
		if (userjson.equals("undefined")) {	
			mav.setViewName("/dingdingweb/HUI/err");
		}else {
			JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
//			System.out.println("tonewselect"+userjson);
			UserInfor usi = new UserInfor();
			usi.setAvatar(js.getString("avatar"));
			usi.setName(js.getString("name"));
			usi.setUserid(js.getString("userid"));
			usi.setDepartment(js.getString("department"));
			List<UserInfor> userinfoList = new ArrayList<>();
			userinfoList.add(usi);
			mav.addObject("userinfoList", userinfoList);
			mav.addObject("userjson", userjson.replaceAll("\"", "'"));
			mav.setViewName("/dingdingweb/HUI/office/apply_car/carlist");
		}
		return mav;	
	}
	
	
	/**
	 * 获取车辆列表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/Office/ApplyCar/getCarList")
	public void getCarList(HttpServletRequest request,HttpServletResponse response){
		ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
        //获得bean
		OfficeDao officeDao=ctx.getBean(OfficeDao.class);
	    List<Car> car = officeDao.getcarlist();
	    JSONObject re = new JSONObject();
	    re.put("blist", car);
		try {			
			PrintWriter out = response.getWriter();
			out = response.getWriter();
			out.print(re);
			out.flush();
			out.close();
//			response.getWriter().append("成功");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 跳转到车辆申请
	 * @param request
	 * @return
	 */
	@RequestMapping("/Office/ApplyCar/applycar")
	public ModelAndView toApplycar(HttpServletRequest request,ModelAndView mav ){
		String userjson=null;
		try {
			userjson = aesEncryptUtil.desEncrypt(request.getParameter("info"));
		} catch (Exception e) {
			e.printStackTrace();
		}//		System.out.println(userjson);
		if (userjson.equals("undefined")) {	
			mav.setViewName("/dingdingweb/HUI/err");
		}else {
			JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
//			System.out.println("tonewselect"+userjson);
			UserInfor usi = new UserInfor();
			usi.setAvatar(js.getString("avatar"));
			usi.setName(js.getString("name"));
			usi.setUserid(js.getString("userid"));
			usi.setDepartment(js.getString("department"));
			List<UserInfor> userinfoList = new ArrayList<>();
			userinfoList.add(usi);
			mav.addObject("userinfoList", userinfoList);
			mav.addObject("userjson", userjson.replaceAll("\"", "'"));
			mav.setViewName("/dingdingweb/HUI/office/apply_car/applycar");
		}
		return mav;		
	}
	
	
	/**
	 * 
	 * 提交车辆申请
	 * @param request
	 * @return
	 */
	@RequestMapping("/Office/ApplyCar/sendapply")
	public ModelAndView toSendApplyCar(HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/dingdingweb/HUI/office/apply_car/sendapply.jsp");
		return mav;		
	}
	
	/**
	 * 车辆申请入库
	 * @param request
	 * @param response
	 */
	@RequestMapping("/SaveCarApply/toBase")
	public void saveCarApply(HttpServletRequest request,HttpServletResponse response){
		String dcnumber="2"+String.valueOf(System.currentTimeMillis());
		String jobnumber = request.getParameter("jobnumber");
		String name =request.getParameter("name");
		String borrowde =request.getParameter("borrowde");
		String car_id=request.getParameter("car_id");
		String car_name=request.getParameter("car_name");
		String license_plate=request.getParameter("license_plate");
		String apply_time =request.getParameter("apply-time");
		String use_time=request.getParameter("use-time");
		String apply_reason=request.getParameter("apply-reason");
		String pnum=request.getParameter("pnum");
		String driver=request.getParameter("driver");
		String audit_name=request.getParameter("audit-name");
		if (audit_name.contains("(")) {
			audit_name = audit_name.substring(0, audit_name.indexOf("(")).trim();
		}
		
		ApplyCarInfor ac = new ApplyCarInfor();
		ac.setDcnumber(dcnumber);
		ac.setJobnumber(jobnumber);
		ac.setName(name);
		ac.setBorrowde(borrowde);
		ac.setCar_id(car_id);
		ac.setCar_name(car_name);
		ac.setLicense_plate(license_plate);
		ac.setApply_time(apply_time);
		ac.setUse_time(use_time);
		ac.setApply_reason(apply_reason);
		ac.setPnum(pnum);
		ac.setDriver(driver);
		ac.setAudit_name(audit_name);
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-mybatis.xml");
		boolean ret =false;
		OfficeDao officeDao=ctx.getBean(OfficeDao.class);
		ProcessDao processDao = ctx.getBean(ProcessDao.class);
		
		try {
			officeDao.SaveApplyCar(ac);
		} catch (Exception e1) {
			try {			
				PrintWriter out = response.getWriter();
				out = response.getWriter();
				out.print("失败");
				out.flush();
				out.close();
//				response.getWriter().append("成功");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ret=true;
			e1.printStackTrace();
		}
		if (ret==false) {
			processDao.insert(dcnumber, name, audit_name, "车量申请");
			try {
				
				PrintWriter out = response.getWriter();
				out = response.getWriter();
				out.print("成功");
				out.flush();
				out.close();
//				response.getWriter().append("成功");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	
	/**
	 * 跳转到车辆申请消息
	 * @param request
	 * @return
	 */
	@RequestMapping("/Office/ApplyCar/apply")
	public ModelAndView toapplycar(HttpServletRequest request){
		String userjson = request.getParameter("info");
//		System.out.println(userjson);
		ModelAndView mav = new ModelAndView();
		if (userjson.equals("undefined")) {	
			mav.setViewName("/dingdingweb/HUI/err");
		}else {
			JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
//			System.out.println("tonewselect"+userjson);
			UserInfor usi = new UserInfor();
			usi.setAvatar(js.getString("avatar"));
			usi.setName(js.getString("name"));
			usi.setUserid(js.getString("userid"));
			usi.setDepartment(js.getString("department"));
			List<UserInfor> userinfoList = new ArrayList<>();
			userinfoList.add(usi);
			mav.addObject("userinfoList", userinfoList);
			mav.addObject("userjson", userjson.replaceAll("\"", "'"));
			String searchkey = js.getString("name");
			List<ApplyCarInfor> applyCarInfor = selectApplyCar(searchkey);
			mav.addObject("applyCarInfor", applyCarInfor);
			mav.setViewName("/dingdingweb/HUI/office/apply_car/apply");
		}
		return mav;	
		
	}
	
	public List<ApplyCarInfor>  selectApplyCar(String searchkey){
//      System.out.println("start-------------------------");
      //初始化容器
      ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
      //获得bean
      OfficeDao officeDao = ctx.getBean(OfficeDao.class);
      List<ApplyCarInfor> applyCarInfor = officeDao.selectMyApplyCar(searchkey); 
//      Iterator<ApplyCarInfor> it = applyCarInfor.iterator();
//      while (it.hasNext()) {
//		ApplyCarInfor ap = (ApplyCarInfor) it.next();
//		System.out.println(ap.getAudit_type());
//		
//	}
		return applyCarInfor; 
	}
	
	
	
	
	/**
	 * 需要我审核的车辆借用
	 * @param request
	 * @return
	 */
	@RequestMapping("/Office/ApplyCar/audit")
	public ModelAndView toAuditApplyCar(HttpServletRequest request,ModelAndView mav){
		String userjson=null;
		try {
			userjson = aesEncryptUtil.desEncrypt(request.getParameter("info"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//		System.out.println(userjson);
		if (userjson.equals("undefined")) {	
			mav.setViewName("/dingdingweb/HUI/err");
		}else {
			JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
//			System.out.println("tonewselect"+userjson);
			UserInfor usi = new UserInfor();
			usi.setAvatar(js.getString("avatar"));
			usi.setName(js.getString("name"));
			usi.setUserid(js.getString("userid"));
			usi.setDepartment(js.getString("department"));
			List<UserInfor> userinfoList = new ArrayList<>();
			userinfoList.add(usi);
			mav.addObject("userinfoList", userinfoList);
			mav.addObject("userjson", userjson.replaceAll("\"", "'"));
			String searchkey = js.getString("name");
//			List<ApplyCarInfor> applyCarInfor = selectauditApplyCar(searchkey);
//			mav.addObject("applyCarInfor", applyCarInfor);
			mav.setViewName("/dingdingweb/HUI/office/apply_car/audit");
		}
		return mav;		
	}
	
	/**
	 * 获取需要我审核的车辆申请信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/Office/ApplyCar/getMyAuditApplyCar")
	public void getMyAuditApplyCar(HttpServletRequest request,HttpServletResponse response){		
		String audit_name = request.getParameter("audit_name").replace("欢迎您 : ", "");
		String pageNum = request.getParameter("pageNum");
		PageHelper.startPage(Integer.parseInt(pageNum), 12);
		List<ApplyCarInfor> bList = selectauditApplyCar(audit_name);
		PageInfo<ApplyCarInfor> pageInfo = new PageInfo<ApplyCarInfor>(bList);
		JSONObject re = new JSONObject();
		re.put("blist", pageInfo.getList());
		re.put("pageInfo", pageInfo);
		try {			
			PrintWriter out = response.getWriter();
			out = response.getWriter();
			out.print(re);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public List<ApplyCarInfor>  selectauditApplyCar(String searchkey){
//      System.out.println("start-------------------------");
      //初始化容器
      ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
      //获得bean
      OfficeDao officeDao = ctx.getBean(OfficeDao.class);
      List<ApplyCarInfor> applyCarInfor = officeDao.selectApplyCar(searchkey);      
		return applyCarInfor; 
	}
	
	
	/**
	 * 
	 * 车辆申请审核完成入库
	 * @param request
	 * @return
	 */
	@RequestMapping("/Office/ApplyCar/saveAudit")
	public ModelAndView saveApplyCarAudit(HttpServletRequest request){
		String dcnumber=request.getParameter("dcnumber");
		String jobnumber = request.getParameter("jobnumber");
		String name =request.getParameter("name");
		String borrowde =request.getParameter("borrowde");
		
		String car_id=request.getParameter("car_id");
		String car_name=request.getParameter("car_name");
		String license_plate=request.getParameter("license_plate");
		String state=request.getParameter("state");
		String apply_time =request.getParameter("apply-time");
		String use_time=request.getParameter("use-time");
		String apply_reason=request.getParameter("apply-reason");
		String pnum=request.getParameter("pnum");
		String driver=request.getParameter("driver");
		String audit_name=request.getParameter("audit-name");
		String audit_opinion=request.getParameter("audit-opinion");
		String audit_time=request.getParameter("audit-time");
		String note=request.getParameter("note");
		String radio = request.getParameter("radio");
		
		ApplyCarInfor ac = new ApplyCarInfor();
		ac.setDcnumber(dcnumber);
		ac.setJobnumber(jobnumber);
		ac.setName(name);
		ac.setBorrowde(borrowde);
		ac.setCar_id(car_id);
		ac.setCar_name(car_name);
		ac.setLicense_plate(license_plate);
		ac.setApply_time(apply_time);
		ac.setUse_time(use_time);
		ac.setApply_reason(apply_reason);
		ac.setPnum(pnum);
		ac.setDriver(driver);
		ac.setAudit_name(audit_name);
		ac.setAudit_opinion(audit_opinion);
		ac.setAudit_time(audit_time);
		ac.setNote(note);
		ac.setAudit_type(radio);
		ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-mybatis.xml");
		OfficeDao officeDao=ctx.getBean(OfficeDao.class);
		officeDao.SaveApplyCarAudit(ac);
		officeDao.updateCar(state,license_plate);
		return new ModelAndView("/dingdingweb/HUI/contract/AddSuccess");
	}
	
	
	/**
	 * 跳转到我的车辆借用
	 * @param request
	 * @return
	 */
	@RequestMapping("/Office/ApplyCar/mycar")
	public ModelAndView toMycar(HttpServletRequest request,ModelAndView mav){
		String userjson=null;
		try {
			userjson = aesEncryptUtil.desEncrypt(request.getParameter("info"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		//		System.out.println(userjson);
		if (userjson.equals("undefined")) {	
			mav.setViewName("/dingdingweb/HUI/err");
		}else {
			JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
//			System.out.println("tonewselect"+userjson);
			UserInfor usi = new UserInfor();
			usi.setAvatar(js.getString("avatar"));
			usi.setName(js.getString("name"));
			usi.setUserid(js.getString("userid"));
			usi.setDepartment(js.getString("department"));
			List<UserInfor> userinfoList = new ArrayList<>();
			userinfoList.add(usi);
			mav.addObject("userinfoList", userinfoList);
			mav.addObject("userjson", userjson.replaceAll("\"", "'"));
//			String searchkey = js.getString("name");
//			List<ApplyCarInfor> applyCarInfor = selectMycar(searchkey);
//			mav.addObject("applyCarInfor", applyCarInfor);
			mav.setViewName("/dingdingweb/HUI/office/apply_car/mycar");
		}
		return mav;	
	}
	
	
	/**
	 * 获取我租用的车辆信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/Office/ApplyCar/getMyCarInfor")
	public void getMycarInfor(HttpServletRequest request,HttpServletResponse response){
		String name = request.getParameter("name").replace("欢迎您 : ", "");
		String pageNum = request.getParameter("pageNum");
		PageHelper.startPage(Integer.parseInt(pageNum), 12);
		List<ApplyCarInfor> bList = selectMycar(name);
		PageInfo<ApplyCarInfor> pageInfo = new PageInfo<ApplyCarInfor>(bList);
		JSONObject re = new JSONObject();
		re.put("blist", pageInfo.getList());
		re.put("pageInfo", pageInfo);
		try {			
			PrintWriter out = response.getWriter();
			out = response.getWriter();
			out.print(re);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
		public List<ApplyCarInfor>  selectMycar(String searchkey){
//	      System.out.println("start-------------------------");
	      //初始化容器
	      ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
	      //获得bean
	      OfficeDao officeDao = ctx.getBean(OfficeDao.class);
	      List<ApplyCarInfor> applyCarInfor = officeDao.selectMyCar(searchkey);      
			return applyCarInfor; 
		}
	
		/**
		 * 车辆申请审核通过评价保存
		 * @param request
		 * @return
		 */
		@RequestMapping("/Office/ApplyCar/saveAfterEV")
		public ModelAndView saveApplayCarAfterEV(HttpServletRequest request){
			String dcnumber=request.getParameter("dcnumber");
			String jobnumber = request.getParameter("jobnumber");
			String name =request.getParameter("name");
			String borrowde =request.getParameter("borrowde");
			String apply_time =request.getParameter("apply-time");
			String use_time=request.getParameter("use-time");
			String apply_reason=request.getParameter("apply-reason");
			String pnum=request.getParameter("pnum");
			String driver=request.getParameter("driver");
			String audit_name=request.getParameter("audit-name");
			String audit_opinion=request.getParameter("audit-opinion");
			String audit_time=request.getParameter("audit-time");
			String note=request.getParameter("note");
			String radio = request.getParameter("radio");
			String evaluation =request.getParameter("evaluation");
			
			ApplyCarInfor ac = new ApplyCarInfor();
			ac.setDcnumber(dcnumber);
			ac.setJobnumber(jobnumber);
			ac.setName(name);
			ac.setBorrowde(borrowde);
			ac.setApply_time(apply_time);
			ac.setUse_time(use_time);
			ac.setApply_reason(apply_reason);
			ac.setPnum(pnum);
			ac.setDriver(driver);
			ac.setAudit_name(audit_name);
			ac.setAudit_opinion(audit_opinion);
			ac.setAudit_time(audit_time);
			ac.setNote(note);
			ac.setAudit_type(radio);
			ac.setEvaluation(evaluation);
			ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-mybatis.xml");
			OfficeDao officeDao=ctx.getBean(OfficeDao.class);
			officeDao.SaveApplyCarAfterEv(ac);
			return new ModelAndView("/dingdingweb/HUI/contract/AddSuccess");
			
		}
		
		
		/**
		 * 跳转到确认车辆归还
		 * @param request
		 * @return
		 */
		@RequestMapping("/Office/ApplyCar/surebackcar")
		public ModelAndView sureBackCar(HttpServletRequest request,ModelAndView mav){
			String userjson=null;
			try {
				userjson = aesEncryptUtil.desEncrypt(request.getParameter("info"));
			} catch (Exception e) {
				e.printStackTrace();
			}
//			System.out.println(userjson);
			if (userjson.equals("undefined")) {	
				mav.setViewName("/dingdingweb/HUI/err");
			}else {
				JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
//				System.out.println("tonewselect"+userjson);
				UserInfor usi = new UserInfor();
				usi.setAvatar(js.getString("avatar"));
				usi.setName(js.getString("name"));
				usi.setUserid(js.getString("userid"));
				usi.setDepartment(js.getString("department"));
				List<UserInfor> userinfoList = new ArrayList<>();
				userinfoList.add(usi);
				mav.addObject("userinfoList", userinfoList);
				mav.addObject("userjson", userjson.replaceAll("\"", "'"));
//				String searchkey = js.getString("name");
//				List<ApplyCarInfor> applyCarInfor = selectSureBackcar(searchkey);
//				mav.addObject("applyCarInfor", applyCarInfor);
				mav.setViewName("/dingdingweb/HUI/office/apply_car/surebackcar");
			}
			return mav;	
		}
		
		/**
		 * 获取归还的车辆数据
		 * @param request
		 * @param response
		 */
		@RequestMapping("/Office/ApplyCar/getSureBackCarInfor")
		public void  getSureBackCarInfor(HttpServletRequest request,HttpServletResponse response){
			String name = request.getParameter("name").replace("欢迎您 : ", "");
			String pageNum = request.getParameter("pageNum");
			PageHelper.startPage(Integer.parseInt(pageNum), 12);
			List<ApplyCarInfor> bList = selectSureBackcar(name);
			PageInfo<ApplyCarInfor> pageInfo = new PageInfo<ApplyCarInfor>(bList);
			JSONObject re = new JSONObject();
			re.put("blist", pageInfo.getList());
			re.put("pageInfo", pageInfo);
			try {			
				PrintWriter out = response.getWriter();
				out = response.getWriter();
				out.print(re);
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public List<ApplyCarInfor>  selectSureBackcar(String searchkey){
//		      System.out.println("start-------------------------");
		      //初始化容器
		      ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
		      //获得bean
		      OfficeDao officeDao = ctx.getBean(OfficeDao.class);
		      List<ApplyCarInfor> applyCarInfor = officeDao.sureBackApplyCar(searchkey);      
		      return applyCarInfor; 
		}
		
		/**
		 * 确认归还入库
		 * @param request
		 * @return
		 */
		@RequestMapping("/Office/ApplyCar/savesure")
		public ModelAndView savesurebackcar(HttpServletRequest request){
			String dcnumber=request.getParameter("dcnumber");
			String jobnumber = request.getParameter("jobnumber");
			String name =request.getParameter("name");
			String borrowde =request.getParameter("borrowde");
			String apply_time =request.getParameter("apply-time");
			String use_time=request.getParameter("use-time");
			String apply_reason=request.getParameter("apply-reason");
			String pnum=request.getParameter("pnum");
			String driver=request.getParameter("driver");
			String audit_name=request.getParameter("audit-name");
			String audit_opinion=request.getParameter("audit-opinion");
			String audit_time=request.getParameter("audit-time");
			String note=request.getParameter("note");
			String radio = request.getParameter("radio");
			String evaluation =request.getParameter("evaluation");
			
			String car_id=request.getParameter("car_id");
			String car_name=request.getParameter("car_name");
			String license_plate=request.getParameter("license_plate");
			String state=request.getParameter("state");
			
			ApplyCarInfor ac = new ApplyCarInfor();
			ac.setDcnumber(dcnumber);
			ac.setJobnumber(jobnumber);
			ac.setName(name);
			ac.setBorrowde(borrowde);
			ac.setApply_time(apply_time);
			ac.setUse_time(use_time);
			ac.setApply_reason(apply_reason);
			ac.setPnum(pnum);
			ac.setDriver(driver);
			ac.setAudit_name(audit_name);
			ac.setAudit_opinion(audit_opinion);
			ac.setAudit_time(audit_time);
			ac.setNote(note);
			ac.setAudit_type(radio);
			ac.setEvaluation(evaluation);
			ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-mybatis.xml");
			OfficeDao officeDao=ctx.getBean(OfficeDao.class);
			officeDao.SaveApplyCarBack(ac);
			officeDao.updateCar(state, license_plate);
			ProcessDao processDao = ctx.getBean(ProcessDao.class);
			processDao.updateState(dcnumber);
			return new ModelAndView("/dingdingweb/HUI/contract/AddSuccess");
			
		}
		
		
		/**
		 * 跳转到查询
		 * @param request
		 * @return
		 */
		@RequestMapping("/Office/ApplyCar/searchapply")
		public ModelAndView toSelectApplyCarList(HttpServletRequest request,ModelAndView mav){
			String userjson=null;
			try {
				userjson = aesEncryptUtil.desEncrypt(request.getParameter("info"));
			} catch (Exception e) {
				e.printStackTrace();
			}
//			System.out.println(userjson);
			if (userjson.equals("undefined")) {	
				mav.setViewName("/dingdingweb/HUI/err");
			}else {
				JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
				UserInfor usi = new UserInfor();
				usi.setAvatar(js.getString("avatar"));
				usi.setName(js.getString("name"));
				usi.setUserid(js.getString("userid"));
				usi.setDepartment(js.getString("department"));
				List<UserInfor> userinfoList = new ArrayList<>();
				userinfoList.add(usi);
				mav.addObject("userinfoList", userinfoList);
				mav.addObject("userjson", userjson.replaceAll("\"", "'"));
				mav.setViewName("/dingdingweb/HUI/office/apply_car/select_applycar");
			}
			return mav;	
			
		}
		
		
		/**
		 * 查询车辆借用信息（按申请期间和借用人、借用人部门）
		 * @param request
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/Office/Applycar/SelectList")
		public void selectContract(HttpServletRequest request,HttpServletResponse response) throws Exception {
//			System.out.println("run");
			String searchkey = request.getParameter("searchkey");
			String userjson = request.getParameter("info");
			String pageNum =request.getParameter("pageNum");
			PageHelper.startPage(Integer.parseInt(pageNum), 15);
			if (userjson.equals("undefined")){
//				mad.setViewName("/dingdingweb/HUI/err");
			}else {
//				JSONObject js = JSONObject.parseObject(userjson);
//				UserInfor usi = new UserInfor();
//				usi.setAvatar(js.getString("avatar"));
//				usi.setName(js.getString("name"));
//				usi.setUserid(js.getString("userid"));
//				usi.setDepartment(js.getString("department"));
//				List<UserInfor> userinfoList = new ArrayList<>();
//				userinfoList.add(usi);
		//		System.out.println("serachkey:"+searchkey);
				List<ApplyCarInfor> applycarList = selectApplyCarList(searchkey);
				PageInfo<ApplyCarInfor> pageInfo = new PageInfo<ApplyCarInfor>(applycarList);
				JSONObject re = new JSONObject();
				re.put("applycarList", pageInfo.getList());
				re.put("pageInfo", pageInfo);
//				re.put("userinfoList", userinfoList);
				re.put("userjson", userjson.replaceAll("\"", "'"));
				
				PrintWriter out = response.getWriter();
				out = response.getWriter();
				out.print(re);
				out.flush();
				out.close();
			}
		}
		
		public List<ApplyCarInfor>  selectApplyCarList(String searchkey){
//	        System.out.println("start-------------------------");
	        //初始化容器
	        ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
	        //获得bean
	        OfficeDao officeDao = ctx.getBean(OfficeDao.class);
	        List<ApplyCarInfor> applycarList = officeDao.SelectApplyCarList(searchkey);     
	       
			return applycarList;
	        
	    }
		
		/**
		 * 跳转到车辆借用信息详情
		 * @param request
		 * @param response
		 * @throws IOException
		 */
		@RequestMapping("/Office/Applycar/Detailed")
		public ModelAndView toDetail(HttpServletRequest request,ModelAndView mav) throws IOException{
			String userjson=null;
			try {
				userjson = aesEncryptUtil.desEncrypt(request.getParameter("info"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			String dcnumber=request.getParameter("dcnumber");
			
			if (userjson.equals("undefined")) {	
				mav.setViewName("/dingdingweb/HUI/err");
			}else {
			
				List<ApplyCarInfor> applycarlist = selectApplyCardetail(dcnumber);
//				for (ApplyCarInfor applyCarInfor : applycarlist) {
//					System.out.println(applyCarInfor.getDcnumber());
//					System.out.println(applyCarInfor.getApply_time());
//					System.out.println(applyCarInfor.getName());
//				}
				mav.addObject("userjson", userjson.replaceAll("\"", "'"));
				mav.addObject("applycarlist", applycarlist);
				mav.setViewName("/dingdingweb/HUI/office/apply_car/applycardetail");
			}
			return mav;	
		}
		
		public List<ApplyCarInfor>  selectApplyCardetail(String dcnumber){
//	        System.out.println("start-------------------------");
	        //初始化容器
	        ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
	        //获得bean
	        OfficeDao officeDao = ctx.getBean(OfficeDao.class);
	        List<ApplyCarInfor> applycarList = officeDao.selectApplyCardetail(dcnumber);     
	       
			return applycarList;
	    }
		
		
		/**********************************************************************************************/
		
		/**
		 * 
		 * 跳转到名片制作模块
		 * @param request
		 * @return
		 */
		@RequestMapping("/Office/BusinessCard/applycard")
		public ModelAndView toBuinessCard(HttpServletRequest request,ModelAndView mav){
			String userjson=null;
			try {
				userjson = aesEncryptUtil.desEncrypt(request.getParameter("info"));
			} catch (Exception e) {
				e.printStackTrace();
			}
//			System.out.println(userjson);
			if (userjson.equals("undefined")) {	
				mav.setViewName("/dingdingweb/HUI/err");
			}else {
				JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
//				System.out.println("tonewselect"+userjson);
				UserInfor usi = new UserInfor();
				usi.setAvatar(js.getString("avatar"));
				usi.setName(js.getString("name"));
				usi.setUserid(js.getString("userid"));
				usi.setDepartment(js.getString("department"));
				List<UserInfor> userinfoList = new ArrayList<>();
				userinfoList.add(usi);
				mav.addObject("userinfoList", userinfoList);
				mav.addObject("userjson", userjson.replaceAll("\"", "'"));
				mav.setViewName("/dingdingweb/HUI/office/business_card/businesscard");
			}
			return mav;	
		}
		
		
		/**
		 * 跳转到申请名片制作页面
		 * @param request
		 * @param response
		 * @return
		 */
		@RequestMapping("/Office/BusinessCard/sendapply")
		public ModelAndView toSendApplyCard(HttpServletRequest request){
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/dingdingweb/HUI/office/business_card/sendapply.jsp");
			return mav;	
		}
		
		/**
		 * 保存名片制作申请信息入库
		 * @param request
		 * @param response
		 */
		@RequestMapping("/SaveBusinessCard/toBase")
		public void saveBusinessCard(HttpServletRequest request,HttpServletResponse response){
			
			String dcnumber="3"+String.valueOf(System.currentTimeMillis());
			String uimg = request.getParameter("uimg");
			String jobnumber = request.getParameter("jobnumber");
			String name =request.getParameter("name");
			String borrowde =request.getParameter("borrowde");
			String email = request.getParameter("email");
			String company =request.getParameter("company");
			String web =request.getParameter("web");
			String customertel=request.getParameter("customertel");
			String num=request.getParameter("num");
			String audit_name =request.getParameter("audit-name");
			if (audit_name.contains("(")) {
				audit_name=audit_name.substring(0, audit_name.indexOf("("));
			}
			
			BusinessCardInfor bus = new BusinessCardInfor();
			bus.setUimg(uimg);
			bus.setDcnumber(dcnumber);
			bus.setJobnumber(jobnumber);
			bus.setName(name);
			bus.setBorrowde(borrowde);
			bus.setEmail(email);
			bus.setCompany(company);
			bus.setWeb(web);
			bus.setCustomertel(customertel);
			bus.setNum(num);
			bus.setAudit_name(audit_name);
			
			ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-mybatis.xml");
			
			boolean ret =false;
			OfficeDao officeDao=ctx.getBean(OfficeDao.class);
			ProcessDao processDao = ctx.getBean(ProcessDao.class);		
			try {
				officeDao.SaveApplyBusinessCard(bus);
			} catch (Exception e1) {
				try {			
					PrintWriter out = response.getWriter();
					out = response.getWriter();
					out.print("失败");
					out.flush();
					out.close();
//					response.getWriter().append("成功");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ret=true;
				e1.printStackTrace();
			}
			if (ret==false) {
				processDao.insert(dcnumber, name, audit_name, "名片制作");
				try {			
					PrintWriter out = response.getWriter();
					out = response.getWriter();
					out.print("成功");
					out.flush();
					out.close();
//					response.getWriter().append("成功");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		/**
		 * 跳转到审核名片制作申请
		 * @param request
		 * @return
		 */
		@RequestMapping("/Office/BusinessCard/audit")
		public ModelAndView toauditBusinessCard(HttpServletRequest request,ModelAndView mav){
			String userjson=null;
			try {
				userjson = aesEncryptUtil.desEncrypt(request.getParameter("info"));
			} catch (Exception e) {
				e.printStackTrace();
			}
//			System.out.println(userjson);
			if (userjson.equals("undefined")) {	
				mav.setViewName("/dingdingweb/HUI/err");
			}else {
				JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
				UserInfor usi = new UserInfor();
				usi.setAvatar(js.getString("avatar"));
				usi.setName(js.getString("name"));
				usi.setUserid(js.getString("userid"));
				usi.setDepartment(js.getString("department"));
				List<UserInfor> userinfoList = new ArrayList<>();
				userinfoList.add(usi);
				mav.addObject("userinfoList", userinfoList);
				mav.addObject("userjson", userjson.replaceAll("\"", "'"));
				mav.setViewName("/dingdingweb/HUI/office/business_card/audit");
			}
			return mav;	
		}
		
		/**
		 * 查询需要我审核的名片制作信息
		 * @param request
		 * @param response
		 */
		@RequestMapping("/Office/BusinessCard/getaudit")
		public void getAuditBusinessCard(HttpServletRequest request,HttpServletResponse response){
			String audit_name = request.getParameter("audit_name").replace("欢迎您 : ", "");
			String pageNum = request.getParameter("pageNum");
			PageHelper.startPage(Integer.parseInt(pageNum), 12);
			List<BusinessCardInfor> bList = selectMyAuditBusinessCard(audit_name);
			PageInfo<BusinessCardInfor> pageInfo = new PageInfo<BusinessCardInfor>(bList);
			JSONObject re = new JSONObject();
			re.put("blist", pageInfo.getList());
			re.put("pageInfo", pageInfo);
			try {			
				PrintWriter out = response.getWriter();
				out = response.getWriter();
				out.print(re);
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public List<BusinessCardInfor>  selectMyAuditBusinessCard(String audit_name){
	        //初始化容器
	        ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
	        //获得bean
	        OfficeDao officeDao = ctx.getBean(OfficeDao.class);
	        List<BusinessCardInfor> bList = officeDao.selectMyAuditBusinessCard(audit_name);     
			return bList;
	    }
		
		
		/**
		 * 审批名片制作申请保存入库
		 * @param request
		 * @return
		 */
		@RequestMapping("/Office/BusinessCard/save2Desk")
		public ModelAndView send2DeskBusinessCard(HttpServletRequest request){
			String dcnumber= request.getParameter("dcnumber");
			String jobnumber = request.getParameter("jobnumber");
			String name =request.getParameter("name");
			String borrowde =request.getParameter("borrowde");
			String email = request.getParameter("email");
			String company =request.getParameter("company");
			String web =request.getParameter("web");
			String customertel=request.getParameter("customertel");
			String num=request.getParameter("num");
			String audit_name =request.getParameter("audit-name");
			String audit_opinion =request.getParameter("audit_opinion");
			String audit_time =request.getParameter("audit_time");
			String audit_type = request.getParameter("audit_type");
			String desk = request.getParameter("desk");
			BusinessCardInfor bus = new BusinessCardInfor();
			bus.setDcnumber(dcnumber);
			bus.setJobnumber(jobnumber);
			bus.setName(name);
			bus.setBorrowde(borrowde);
			bus.setEmail(email);
			bus.setCompany(company);
			bus.setWeb(web);
			bus.setCustomertel(customertel);
			bus.setNum(num);
			bus.setAudit_name(audit_name);
			bus.setAudit_opinion(audit_opinion);
			bus.setAudit_time(audit_time);
			bus.setAudit_type(audit_type);
			bus.setDesk(desk);
			
			ModelAndView mad = new ModelAndView();
	        ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
	        OfficeDao officeDao = ctx.getBean(OfficeDao.class);
	        officeDao.send2DeskBusinessCard(bus);
	        mad.setViewName("/dingdingweb/HUI/contract/AddSuccess");
			return mad;
			
		}
		
		/**
		 * 跳转到前台名片制作
		 * @param request
		 * @return
		 */
		@RequestMapping("/Office/BusinessCard/createcard")
		public ModelAndView toCreateCard(HttpServletRequest request){
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/dingdingweb/HUI/office/business_card/createcard.jsp");
			return mav;	
		}
		
		/**
		 * 查询需要前台制作的名片
		 * @param request
		 * @param response
		 */
		@RequestMapping("/Office/BusinessCard/needcreatecard")
		public void CreateCard(HttpServletRequest request,HttpServletResponse response){
			String desk = request.getParameter("desk").replace("欢迎您 :", "");
			String pageNum = request.getParameter("pageNum");
			PageHelper.startPage(Integer.parseInt(pageNum), 12);
			List<BusinessCardInfor> bList = selectNeedCreateBusinessCard(desk);
			PageInfo<BusinessCardInfor> pageInfo = new PageInfo<BusinessCardInfor>(bList);
			JSONObject re = new JSONObject();
			re.put("blist", pageInfo.getList());
			re.put("pageInfo", pageInfo);
			try {			
				PrintWriter out = response.getWriter();
				out = response.getWriter();
				out.print(re);
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public List<BusinessCardInfor>  selectNeedCreateBusinessCard(String desk){
	        //初始化容器
	        ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
	        //获得bean
	        OfficeDao officeDao = ctx.getBean(OfficeDao.class);
	        List<BusinessCardInfor> bList = officeDao.selectNeedCreateBusinessCard(desk);     
			return bList;
	    }
		
		
		/**
		 * 发送名片制作完成消息，保存信息
		 * @param request
		 * @return
		 */
		@RequestMapping("/Office/BusinessCard/sendCreateMessage")
		public 	ModelAndView sendCreateMessage(HttpServletRequest request){
			String dcnumber= request.getParameter("dcnumber");
			String jobnumber = request.getParameter("jobnumber");
			String name =request.getParameter("name");
			String borrowde =request.getParameter("borrowde");
			String email = request.getParameter("email");
			String company =request.getParameter("company");
			String web =request.getParameter("web");
			String customertel=request.getParameter("customertel");
			String num=request.getParameter("num");
			String audit_name =request.getParameter("audit-name");
			String audit_opinion =request.getParameter("audit_opinion");
			String audit_time =request.getParameter("audit_time");
			String audit_type = request.getParameter("audit_type");
			String desk = request.getParameter("desk");
			String lq =request.getParameter("lq");
			if (lq.contains("(")) {
				lq= lq.substring(0, lq.indexOf("(")).trim();
			}
			String lq_time = request.getParameter("lq_time");
			BusinessCardInfor bus = new BusinessCardInfor();
			bus.setDcnumber(dcnumber);
			bus.setJobnumber(jobnumber);
			bus.setName(name);
			bus.setBorrowde(borrowde);
			bus.setEmail(email);
			bus.setCompany(company);
			bus.setWeb(web);
			bus.setCustomertel(customertel);
			bus.setNum(num);
			bus.setAudit_name(audit_name);
			bus.setAudit_opinion(audit_opinion);
			bus.setAudit_time(audit_time);
			bus.setAudit_type(audit_type);
			bus.setDesk(desk);
			bus.setLq(lq);
			bus.setLq_time(lq_time);
			
			ModelAndView mad = new ModelAndView();
	        ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
	        OfficeDao officeDao = ctx.getBean(OfficeDao.class);
	        officeDao.sendCreateMessage(bus);
	        mad.setViewName("/dingdingweb/HUI/contract/AddSuccess");
			return mad;
			
		}
		
		/**
		 * 跳转到我的名片
		 * @param request
		 * @return
		 */
		@RequestMapping("/Office/BusinessCard/mybcard")
		public ModelAndView toMybCard(HttpServletRequest request,ModelAndView mav){
			String userjson=null;
			try {
				userjson = aesEncryptUtil.desEncrypt(request.getParameter("info"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (userjson.equals("undefined")) {	
				mav.setViewName("/dingdingweb/HUI/err");
			}else {
				JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
				UserInfor usi = new UserInfor();
				usi.setAvatar(js.getString("avatar"));
				usi.setName(js.getString("name"));
				usi.setUserid(js.getString("userid"));
				usi.setDepartment(js.getString("department"));
				List<UserInfor> userinfoList = new ArrayList<>();
				userinfoList.add(usi);
				mav.addObject("userinfoList", userinfoList);
				mav.addObject("userjson", userjson.replaceAll("\"", "'"));
				mav.setViewName("/dingdingweb/HUI/office/business_card/mybusinesscard");
			}
			return mav;	
		}
		
		/**
		 * 查询我申请的已完成名片信息
		 * @param request
		 * @param response
		 */
		@RequestMapping("/Office/BusinessCard/selectMybCard")
		public void selectMybCard(HttpServletRequest request,HttpServletResponse response){
			String myname = request.getParameter("myname").replace("欢迎您 : ", "");
			String pageNum = request.getParameter("pageNum");
			PageHelper.startPage(Integer.parseInt(pageNum), 12);
			List<BusinessCardInfor> bList = selectMyBusinessCard(myname);
			PageInfo<BusinessCardInfor> pageInfo = new PageInfo<BusinessCardInfor>(bList);
			JSONObject re = new JSONObject();
			re.put("blist", pageInfo.getList());
			re.put("pageInfo", pageInfo);
			try {			
				PrintWriter out = response.getWriter();
				out = response.getWriter();
				out.print(re);
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public List<BusinessCardInfor>  selectMyBusinessCard(String myname){
	        //初始化容器
	        ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
	        //获得bean
	        OfficeDao officeDao = ctx.getBean(OfficeDao.class);
	        List<BusinessCardInfor> bList = officeDao.selectMyBusinessCard(myname);     
			return bList;
	    }
		
		
		/**
		 * 查看我已制作完成名片信息，保存
		 * @param request
		 * @return
		 */
		@RequestMapping("/Office/BusinessCard/saveBehandEvBcard")
		public ModelAndView saveBehandEvBcard(HttpServletRequest request){
			String dcnumber= request.getParameter("dcnumber");
			String jobnumber = request.getParameter("jobnumber");
			String name =request.getParameter("name");
			String borrowde =request.getParameter("borrowde");
			String email = request.getParameter("email");
			String company =request.getParameter("company");
			String web =request.getParameter("web");
			String customertel=request.getParameter("customertel");
			String num=request.getParameter("num");
			String audit_name =request.getParameter("audit-name");
			String audit_opinion =request.getParameter("audit_opinion");
			String audit_time =request.getParameter("audit_time");
			String audit_type = request.getParameter("audit_type");
			String desk = request.getParameter("desk");
			String lq =request.getParameter("lq");
			String lq_time = request.getParameter("lq_time");
			String evaluation =request.getParameter("evaluation");
			BusinessCardInfor bus = new BusinessCardInfor();
			bus.setDcnumber(dcnumber);
			bus.setJobnumber(jobnumber);
			bus.setName(name);
			bus.setBorrowde(borrowde);
			bus.setEmail(email);
			bus.setCompany(company);
			bus.setWeb(web);
			bus.setCustomertel(customertel);
			bus.setNum(num);
			bus.setAudit_name(audit_name);
			bus.setAudit_opinion(audit_opinion);
			bus.setAudit_time(audit_time);
			bus.setAudit_type(audit_type);
			bus.setDesk(desk);
			bus.setLq(lq);
			bus.setLq_time(lq_time);
			bus.setEvaluation(evaluation);
			
			ModelAndView mad = new ModelAndView();
	        ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
	        OfficeDao officeDao = ctx.getBean(OfficeDao.class);
	        officeDao.saveBEvBcard(bus);
	        ProcessDao processDao = ctx.getBean(ProcessDao.class);
	    	processDao.updateState(dcnumber);
	        mad.setViewName("/dingdingweb/HUI/contract/AddSuccess");
			return mad;
		}
		
		
		/**
		 * 跳转到查询名片申请
		 * @param request
		 * @return
		 */
		@RequestMapping("/Office/BusinessCard/searchapply")
		public ModelAndView tosearchCardApply(HttpServletRequest request,ModelAndView mav){
			String userjson=null;
			try {
				userjson = aesEncryptUtil.desEncrypt(request.getParameter("info"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (userjson.equals("undefined")) {	
				mav.setViewName("/dingdingweb/HUI/err");
			}else {
				JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
				UserInfor usi = new UserInfor();
				usi.setAvatar(js.getString("avatar"));
				usi.setName(js.getString("name"));
				usi.setUserid(js.getString("userid"));
				usi.setDepartment(js.getString("department"));
				List<UserInfor> userinfoList = new ArrayList<>();
				userinfoList.add(usi);
				mav.addObject("userinfoList", userinfoList);
				mav.addObject("userjson", userjson.replaceAll("\"", "'"));
				mav.setViewName("/dingdingweb/HUI/office/business_card/search_applycard");
			}
			return mav;	
			
		}
		
		
		/**
		 * 查询名片制作申请信息
		 * @param request
		 * @param response
		 */
		@RequestMapping("/Office/BusinessCard/SearchBCardApply")
		public void SearchBusinessCardApply(HttpServletRequest request,HttpServletResponse response){
			String searchkey = request.getParameter("searchkey");
			String pageNum = request.getParameter("pageNum");
			PageHelper.startPage(Integer.parseInt(pageNum), 12);
			List<BusinessCardInfor> bList = selectBusinessCard(searchkey);
			PageInfo<BusinessCardInfor> pageInfo = new PageInfo<BusinessCardInfor>(bList);
			
			JSONObject re = new JSONObject();
			re.put("blist", pageInfo.getList());
			re.put("pageInfo", pageInfo);
			try {			
				PrintWriter out = response.getWriter();
				out = response.getWriter();
				out.print(re);
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public List<BusinessCardInfor>  selectBusinessCard(String searchkey){
	        //初始化容器
	        ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
	        //获得bean
	        OfficeDao officeDao = ctx.getBean(OfficeDao.class);
	        List<BusinessCardInfor> bList = officeDao.selectBusinessCard(searchkey);     
			return bList;
	    }
		
		/**
		 * 显示名片
		 * @param request
		 * @return
		 */
		@RequestMapping("/BusinessCard/getCardStyle")
		public ModelAndView getCardStyle(HttpServletRequest request){
			String jobnumber= request.getParameter("jobnumber");
			String uimg=request.getParameter("uimg");
			String name=request.getParameter("name");
			String borrowde=request.getParameter("borrowde");
			String email=request.getParameter("email");
			String company=request.getParameter("company");
			String web=request.getParameter("web");
			String customertel=request.getParameter("customertel");
			ModelMap modelMap = new ModelMap();
			modelMap.addAttribute("jobnumber", jobnumber);
			modelMap.addAttribute("uimg", uimg);
			modelMap.addAttribute("name",name);
			modelMap.addAttribute("borrowde",borrowde);
			modelMap.addAttribute("email",email);
			modelMap.addAttribute("company",company);
			modelMap.addAttribute("web",web);
			modelMap.addAttribute("customertel",customertel);
			ModelAndView mod = new ModelAndView();
			mod.addAllObjects(modelMap);
			mod.setViewName("/dingdingweb/HUI/cardstyle");
			return mod;
			
		}
		
		/**********************************************************************/
		
		
		/**
		 * 跳转到印章及证照申请
		 * @param request
		 * @return
		 */
		@RequestMapping("/Office/StampCertificate/applystamp")
		public ModelAndView toApplystamp(HttpServletRequest request,ModelAndView mav){
			String userjson=null;
			try {
				userjson = aesEncryptUtil.desEncrypt(request.getParameter("info"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (userjson.equals("undefined")) {	
				mav.setViewName("/dingdingweb/HUI/err");
			}else {
				JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
				UserInfor usi = new UserInfor();
				usi.setAvatar(js.getString("avatar"));
				usi.setName(js.getString("name"));
				usi.setUserid(js.getString("userid"));
				usi.setDepartment(js.getString("department"));
				List<UserInfor> userinfoList = new ArrayList<>();
				userinfoList.add(usi);
				mav.addObject("userinfoList", userinfoList);
				mav.addObject("userjson", userjson.replaceAll("\"", "'"));
				mav.setViewName("/dingdingweb/HUI/office/Stamp_Certificate/stampcertificate");
			}
			return mav;	
		}
		
		/**
		 * 跳转到印章及证照维护
		 * @param request
		 * @return
		 */
		@RequestMapping("/Office/StampCertificate/maintain")
		public ModelAndView toMaintainStamp(HttpServletRequest request,ModelAndView mav){
			String userjson=null;
			try {
				userjson = aesEncryptUtil.desEncrypt(request.getParameter("info"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (userjson.equals("undefined")) {	
				mav.setViewName("/dingdingweb/HUI/err");
			}else {
				JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
				UserInfor usi = new UserInfor();
				usi.setAvatar(js.getString("avatar"));
				usi.setName(js.getString("name"));
				usi.setUserid(js.getString("userid"));
				usi.setDepartment(js.getString("department"));
				List<UserInfor> userinfoList = new ArrayList<>();
				userinfoList.add(usi);
				mav.addObject("userinfoList", userinfoList);
				mav.addObject("userjson", userjson.replaceAll("\"", "'"));
				mav.setViewName("/dingdingweb/HUI/office/Stamp_Certificate/maintain");
			}
			return mav;	
		}
		
		
		/**
		 * 维护印章证照显示所有印章证照信息
		 * @param request
		 * @param response
		 */
		@RequestMapping("/Office/StampCertificate/getstampandcertificate")
		public void maintainstampAndcertificate(HttpServletRequest request,HttpServletResponse response){
			
			String pageNum = request.getParameter("pageNum");
			PageHelper.startPage(Integer.parseInt(pageNum), 12);
			List<StampCertificateInfor> bList = selectAllstamp();
			PageInfo<StampCertificateInfor> pageInfo = new PageInfo<StampCertificateInfor>(bList);
			
			JSONObject re = new JSONObject();
			re.put("blist", pageInfo.getList());
			re.put("pageInfo", pageInfo);
			try {			
				PrintWriter out = response.getWriter();
				out = response.getWriter();
				out.print(re);
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		public List<StampCertificateInfor>  selectAllstamp(){
	        //初始化容器
	        ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
	        //获得bean
	        OfficeDao officeDao = ctx.getBean(OfficeDao.class);
	        List<StampCertificateInfor> bList = officeDao.selectAllstamp();     
			return bList;
	    }
		
		
		/**
		 * 更新印章证照
		 * @param request
		 * @return
		 */
		@RequestMapping("/Office/StampCertificate/UpdateStamp")
		public ModelAndView updateStamp(HttpServletRequest request){
			 String stamp_id=request.getParameter("stamp_id");
			 String stamp_name=request.getParameter("stamp_name");
			 String manager=request.getParameter("manager");
			 String state=request.getParameter("select_state");
			 String stamp_company = request.getParameter("stamp_company");
			 String stamp_type=request.getParameter("stamp_type");
			 StampCertificateInfor stacer = new StampCertificateInfor();
			 stacer.setStamp_id(stamp_id);
			 stacer.setStamp_name(stamp_name);
			 stacer.setManager(manager);
			 stacer.setState(state);
			 stacer.setStamp_company(stamp_company);
			 stacer.setStamp_type(stamp_type);
		     ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
		     OfficeDao officeDao = ctx.getBean(OfficeDao.class);
		     try {
				officeDao.UpdateStamp(stacer);
			} catch (Exception e) {
				return new ModelAndView("/dingdingweb/HUI/err");
			}	  
		     return new ModelAndView("/dingdingweb/HUI/contract/AddSuccess");
			 
		}
		
		
		/**
		 * 跳转到印章证照申请页面
		 * @param request
		 * @return
		 */
		@RequestMapping("/Office/StampCertificate/sendapply")
		public ModelAndView toSendApplyStamp(HttpServletRequest request){
			ModelAndView mav = new ModelAndView();
			mav.setViewName("redirect:/dingdingweb/HUI/office/Stamp_Certificate/sendapply.jsp");	
			return mav;
		}
		
		
		/**
		 * 提交印章证照申请，保存
		 * @param request
		 * @param response
		 */
		@RequestMapping("/Office/StampCertificate/saveApplyStamp")
		public void saveApplyStamp(HttpServletRequest request,HttpServletResponse response){
			
			String dcnumber="4"+String.valueOf(System.currentTimeMillis());
			String jobnumber=request.getParameter("jobnumber");
			String name=request.getParameter("name");
			String borrowde=request.getParameter("borrowde");
			String stamp_name =request.getParameter("stamp_name");
			String stamp_company=request.getParameter("stamp_company");
			String stamp_type=request.getParameter("stamp_type");
			String manager=request.getParameter("manager");
			String apply_time=request.getParameter("apply-time");
			String use_time=request.getParameter("use-time");
			String reason=request.getParameter("reason");
			String back_time=request.getParameter("back-time");
			String audit_name=request.getParameter("audit-name");
			if (audit_name.contains("(")) {
				audit_name=audit_name.substring(0, audit_name.indexOf("(")).trim();
			}
			
			ApplyStampInfor asp = new ApplyStampInfor();
			asp.setDcnumber(dcnumber);
			asp.setJobnumber(jobnumber);
			asp.setName(name);
			asp.setBorrowde(borrowde);
			asp.setStamp_name(stamp_name);
			asp.setStamp_company(stamp_company);
			asp.setStamp_type(stamp_type);
			asp.setManager(manager);
			asp.setApply_time(apply_time);
			asp.setUse_time(use_time);
			asp.setReason(reason);
			asp.setBack_time(back_time);
			asp.setAudit_name(audit_name);
			ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-mybatis.xml");
			boolean ret =false;
			OfficeDao officeDao=ctx.getBean(OfficeDao.class);			
			ProcessDao processDao = ctx.getBean(ProcessDao.class);
			try {
				officeDao.saveApplyStamp(asp);
			} catch (Exception e1) {
				try {			
					PrintWriter out = response.getWriter();
					out = response.getWriter();
					out.print("失败");
					out.flush();
					out.close();
//					response.getWriter().append("成功");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ret=true;
				e1.printStackTrace();
			}
			if (ret==false) {
				processDao.insert(dcnumber, name, audit_name, "印章证照");
				try {			
					PrintWriter out = response.getWriter();
					out = response.getWriter();
					out.print("成功");
					out.flush();
					out.close();
//					response.getWriter().append("成功");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		
		/**
		 * 跳转到证章审核
		 * @param request
		 * @return
		 */
		@RequestMapping("/Office/StampCertificate/audit")
		public ModelAndView toAuditApplyStamp(HttpServletRequest request,ModelAndView mav){
			String userjson=null;
			try {
				userjson = aesEncryptUtil.desEncrypt(request.getParameter("info"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (userjson.equals("undefined")) {	
				mav.setViewName("/dingdingweb/HUI/err");
			}else {
				JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
				UserInfor usi = new UserInfor();
				usi.setAvatar(js.getString("avatar"));
				usi.setName(js.getString("name"));
				usi.setUserid(js.getString("userid"));
				usi.setDepartment(js.getString("department"));
				List<UserInfor> userinfoList = new ArrayList<>();
				userinfoList.add(usi);
				mav.addObject("userinfoList", userinfoList);
				mav.addObject("userjson", userjson.replaceAll("\"", "'"));
				mav.setViewName("/dingdingweb/HUI/office/Stamp_Certificate/audit");
			}
			return mav;	
		}
		
		
		/**
		 * 获得需要审核的印章借用申请信息
		 * @param request
		 * @param response
		 */
		@RequestMapping("/Office/StampCertificate/getNeedAuditApplyStamp")
		public void getNeedAuditApplyStamp(HttpServletRequest request,HttpServletResponse response){
			String audit_name=request.getParameter("audit_name").replace("欢迎您 : ", "");
			String pageNum = request.getParameter("pageNum");
			PageHelper.startPage(Integer.parseInt(pageNum), 12);
			List<ApplyStampInfor> bList = selectNeedAdutistamp(audit_name);
			PageInfo<ApplyStampInfor> pageInfo = new PageInfo<ApplyStampInfor>(bList);
			
			JSONObject re = new JSONObject();
			re.put("blist", pageInfo.getList());
			re.put("pageInfo", pageInfo);
			try {			
				PrintWriter out = response.getWriter();
				out = response.getWriter();
				out.print(re);
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		public List<ApplyStampInfor>  selectNeedAdutistamp(String audit_name){
	        //初始化容器
	        ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
	        //获得bean
	        OfficeDao officeDao = ctx.getBean(OfficeDao.class);
	        List<ApplyStampInfor> bList = officeDao.selectNeedAdutistamp(audit_name);     
			return bList;
	    }
		
		
		/**
		 * 印章审核完成传给管理员查看
		 * @param request
		 * @return
		 */
		@RequestMapping("/Office/StampCertificate/toManger")
		public ModelAndView toManger(HttpServletRequest request){
			String dcnumber=request.getParameter("dcnumber");
			String jobnumber=request.getParameter("jobnumber");
			String name=request.getParameter("name");
			String borrowde=request.getParameter("borrowde");
			String stamp_name =request.getParameter("stamp_name");
			String stamp_company=request.getParameter("stamp_company");
			String stamp_type=request.getParameter("stamp_type");
			String manager=request.getParameter("manager");
			String apply_time=request.getParameter("apply-time");
			String use_time=request.getParameter("use-time");
			String reason=request.getParameter("reason");
			String back_time=request.getParameter("back-time");
			String audit_name=request.getParameter("audit-name");
			String audit_opinion=request.getParameter("audit_opinion");
			String audit_time=request.getParameter("audit_time");
			String audit_type=request.getParameter("audit_type");
			
			ApplyStampInfor asp = new ApplyStampInfor();
			asp.setDcnumber(dcnumber);
			asp.setJobnumber(jobnumber);
			asp.setName(name);
			asp.setBorrowde(borrowde);
			asp.setStamp_name(stamp_name);
			asp.setStamp_company(stamp_company);
			asp.setStamp_type(stamp_type);
			asp.setManager(manager);
			asp.setApply_time(apply_time);
			asp.setUse_time(use_time);
			asp.setReason(reason);
			asp.setBack_time(back_time);
			asp.setAudit_name(audit_name);
			asp.setAudit_opinion(audit_opinion);
			asp.setAudit_time(audit_time);
			asp.setAudit_type(audit_type);
			
			ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
			OfficeDao officeDao = ctx.getBean(OfficeDao.class);
		    ProcessDao processDao = ctx.getBean(ProcessDao.class);
		   
		    try {
		    	officeDao.toManger(asp);
		    	 processDao.updateManager(dcnumber,"",manager);
			} catch (Exception e) {
				e.printStackTrace();
				return new ModelAndView("/dingdingweb/HUI/err");
			}	  
		     return new ModelAndView("/dingdingweb/HUI/contract/AddSuccess");     
		}
		
		
		/**
		 * 跳转到印章管理员查看审核完毕请求
		 * @param request
		 * @return
		 */
		@RequestMapping("/Office/StampCertificate/issue")
		public ModelAndView issue(HttpServletRequest request,ModelAndView mav){
			String userjson=null;
			try {
				userjson = aesEncryptUtil.desEncrypt(request.getParameter("info"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (userjson.equals("undefined")) {	
				mav.setViewName("/dingdingweb/HUI/err");
			}else {
				JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
				UserInfor usi = new UserInfor();
				usi.setAvatar(js.getString("avatar"));
				usi.setName(js.getString("name"));
				usi.setUserid(js.getString("userid"));
				usi.setDepartment(js.getString("department"));
				List<UserInfor> userinfoList = new ArrayList<>();
				userinfoList.add(usi);
				mav.addObject("userinfoList", userinfoList);
				mav.addObject("userjson", userjson.replaceAll("\"", "'"));
				mav.setViewName("/dingdingweb/HUI/office/Stamp_Certificate/issue");
			}
			return mav;	
			
		}
		
		
		/**
		 * 获取已审核的印章借用信息
		 * @param request
		 * @param response
		 */
		@RequestMapping("/Office/StampCertificate/getAuditedApplyStamp")
		public void getAuditedApplyStamp(HttpServletRequest request, HttpServletResponse response){
			String manager = request.getParameter("manager").replace("欢迎您 : ", "");
			String pageNum= request.getParameter("pageNum");
			PageHelper.startPage(Integer.parseInt(pageNum), 12);
			List<ApplyStampInfor> bList = selectAuditedApplyStamp(manager);
			PageInfo<ApplyStampInfor> pageInfo = new PageInfo<ApplyStampInfor>(bList);
			
			JSONObject re = new JSONObject();
			re.put("blist", pageInfo.getList());
			re.put("pageInfo", pageInfo);
			try {			
				PrintWriter out = response.getWriter();
				out = response.getWriter();
				out.print(re);
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public List<ApplyStampInfor>  selectAuditedApplyStamp(String manager){
	        //初始化容器
	        ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
	        //获得bean
	        OfficeDao officeDao = ctx.getBean(OfficeDao.class);
	        List<ApplyStampInfor> bList = officeDao.selectAuditedApplyStamp(manager);     
			return bList;
	    }
		
		
		/**
		 * 借出印章证照保存信息
		 * @param request
		 * @return
		 */
		@RequestMapping("/Office/StampCertificate/toIssue")
		public ModelAndView toIssue(HttpServletRequest request){
			String dcnumber=request.getParameter("dcnumber");
			String jobnumber=request.getParameter("jobnumber");
			String name=request.getParameter("name");
			String borrowde=request.getParameter("borrowde");
			String stamp_name =request.getParameter("stamp_name");
			String stamp_company=request.getParameter("stamp_company");
			String stamp_type=request.getParameter("stamp_type");
			String manager=request.getParameter("manager");
			String apply_time=request.getParameter("apply-time");
			String use_time=request.getParameter("use-time");
			String reason=request.getParameter("reason");
			String back_time=request.getParameter("back-time");
			String audit_name=request.getParameter("audit-name");
			String audit_opinion=request.getParameter("audit_opinion");
			String audit_time=request.getParameter("audit_time");
			String audit_type=request.getParameter("audit_type");
			String issue_type=request.getParameter("issue_type");
			
			
			ApplyStampInfor asp = new ApplyStampInfor();
			asp.setDcnumber(dcnumber);
			asp.setJobnumber(jobnumber);
			asp.setName(name);
			asp.setBorrowde(borrowde);
			asp.setStamp_name(stamp_name);
			asp.setStamp_company(stamp_company);
			asp.setStamp_type(stamp_type);
			asp.setManager(manager);
			asp.setApply_time(apply_time);
			asp.setUse_time(use_time);
			asp.setReason(reason);
			asp.setBack_time(back_time);
			asp.setAudit_name(audit_name);
			asp.setAudit_opinion(audit_opinion);
			asp.setAudit_time(audit_time);
			asp.setAudit_type(audit_type);
			asp.setIssue_type(issue_type);
			
			StampCertificateInfor stacer = new StampCertificateInfor();
			stacer.setStamp_name(stamp_name);
			stacer.setState("借出");
			ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
			OfficeDao officeDao = ctx.getBean(OfficeDao.class);
		    try {
		    	officeDao.toIssue(asp);
		    	officeDao.changeStampState(stacer);
			} catch (Exception e) {
				return new ModelAndView("/dingdingweb/HUI/err");
			}	  
		     return new ModelAndView("/dingdingweb/HUI/contract/AddSuccess"); 
		}
		
		/**
		 * 跳转到我的印章证照
		 * @param request
		 * @return
		 */
		@RequestMapping("/Office/StampCertificate/toMyStamp")
		public ModelAndView toMyStamp(HttpServletRequest request,ModelAndView mav){
			String userjson=null;
			try {
				userjson = aesEncryptUtil.desEncrypt(request.getParameter("info"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (userjson.equals("undefined")) {	
				mav.setViewName("/dingdingweb/HUI/err");
			}else {
				JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
				UserInfor usi = new UserInfor();
				usi.setAvatar(js.getString("avatar"));
				usi.setName(js.getString("name"));
				usi.setUserid(js.getString("userid"));
				usi.setDepartment(js.getString("department"));
				List<UserInfor> userinfoList = new ArrayList<>();
				userinfoList.add(usi);
				mav.addObject("userinfoList", userinfoList);
				mav.addObject("userjson", userjson.replaceAll("\"", "'"));
				mav.setViewName("/dingdingweb/HUI/office/Stamp_Certificate/mystamp");
			}
			return mav;	
			
		}
		
		/**
		 * 获取我的印章证照借用信息
		 * @param request
		 * @param response
		 */
		@RequestMapping("/Office/StampCertificate/getMyStamp")
		public void getMyStamp(HttpServletRequest request,HttpServletResponse response){
			String userName=request.getParameter("userName").replace("欢迎您 : ", "");
			String pageNum=request.getParameter("pageNum");
			PageHelper.startPage(Integer.parseInt(pageNum), 12);
			List<ApplyStampInfor> bList = selectMyStamp(userName);
			PageInfo<ApplyStampInfor> pageInfo = new PageInfo<ApplyStampInfor>(bList);
			
			JSONObject re = new JSONObject();
			re.put("blist", pageInfo.getList());
			re.put("pageInfo", pageInfo);
			try {			
				PrintWriter out = response.getWriter();
				out = response.getWriter();
				out.print(re);
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public List<ApplyStampInfor>  selectMyStamp(String userName){
	        //初始化容器
	        ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
	        //获得bean
	        OfficeDao officeDao = ctx.getBean(OfficeDao.class);
	        List<ApplyStampInfor> bList = officeDao.selectMyStamp(userName);     
			return bList;
	    }
		
		
		/**
		 * 归还印章证照
		 * @param request
		 * @return
		 */
		@RequestMapping("/Office/StampCertificate/returnStamp")
		public ModelAndView toReturnStamp(HttpServletRequest request){
			String dcnumber=request.getParameter("dcnumber");
			String evaluation=request.getParameter("evaluation");
			ApplyStampInfor asp = new ApplyStampInfor();
			asp.setDcnumber(dcnumber);
			asp.setEvaluation(evaluation);
			ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-mybatis.xml");
			OfficeDao officeDao = ctx.getBean(OfficeDao.class);
			
			try {
				 officeDao.toReturnStamp(asp);
			} catch (Exception e) {
				return new ModelAndView("/dingdingweb/HUI/err");
			}	  
			  return new ModelAndView("/dingdingweb/HUI/contract/AddSuccess"); 
		}
		
		
		/**
		 * 跳转到印章证照归还确认
		 * @param request
		 * @return
		 */
		@RequestMapping("/Office/StampCertificate/returned")
		public ModelAndView toReturned(HttpServletRequest request,ModelAndView mav){
			String userjson=null;
			try {
				userjson = aesEncryptUtil.desEncrypt(request.getParameter("info"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (userjson.equals("undefined")) {	
				mav.setViewName("/dingdingweb/HUI/err");
			}else {
				JSONObject js = JSONObject.parseObject(userjson.replaceAll("'", "\""));
				UserInfor usi = new UserInfor();
				usi.setAvatar(js.getString("avatar"));
				usi.setName(js.getString("name"));
				usi.setUserid(js.getString("userid"));
				usi.setDepartment(js.getString("department"));
				List<UserInfor> userinfoList = new ArrayList<>();
				userinfoList.add(usi);
				mav.addObject("userinfoList", userinfoList);
				mav.addObject("userjson", userjson.replaceAll("\"", "'"));
				mav.setViewName("/dingdingweb/HUI/office/Stamp_Certificate/returned");
			}
			return mav;	
		}
		
		
		/**
		 * 确认证照已归还数据保存
		 */
		@RequestMapping("/Office/StampCertificate/SureReturnStampApply")
		public ModelAndView SureReturnStampApply(HttpServletRequest request){
			String dcnumber =request.getParameter("dcnumber");
			String sureback_time=request.getParameter("sureback_time");
			ApplyStampInfor asp = new ApplyStampInfor();
			asp.setDcnumber(dcnumber);
			asp.setSureback_time(sureback_time);
			ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-mybatis.xml");
			OfficeDao officeDao = ctx.getBean(OfficeDao.class);
			ProcessDao processDao = ctx.getBean(ProcessDao.class);
			processDao.updateState(dcnumber);
			try {
				 officeDao.SureReturnStampApply(asp);
			} catch (Exception e) {
				return new ModelAndView("/dingdingweb/HUI/err");
			}	  
			  return new ModelAndView("/dingdingweb/HUI/contract/AddSuccess"); 
			
		}
		
		
		/**
		 * 获取所有印章证照申请信息
		 * @param request
		 * @param response
		 */
		@RequestMapping("/Office/StampCertificate/getAllStampApply")
		public void selectAllStampApplyInf(HttpServletRequest request,HttpServletResponse response){
			String pageNum=request.getParameter("pageNum");
			PageHelper.startPage(Integer.parseInt(pageNum), 12);
			List<ApplyStampInfor> bList = selectAllStampI();
			PageInfo<ApplyStampInfor> pageInfo = new PageInfo<ApplyStampInfor>(bList);
			
			JSONObject re = new JSONObject();
			re.put("blist", pageInfo.getList());
			re.put("pageInfo", pageInfo);
			try {			
				PrintWriter out = response.getWriter();
				out = response.getWriter();
				out.print(re);
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		public List<ApplyStampInfor>  selectAllStampI(){
	        //初始化容器
	        ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
	        //获得bean
	        OfficeDao officeDao = ctx.getBean(OfficeDao.class);
	        List<ApplyStampInfor> bList = officeDao.selectAllStampI();     
			return bList;
	    }
		
		
		
		/**
		 * 跳转到印证借用申请搜索
		 * @param request
		 * @return
		 */
		@RequestMapping("/Office/StampCertificate/searchapply")
		public ModelAndView toSerchStampApply(HttpServletRequest request){
			return new ModelAndView("/dingdingweb/HUI/office/Stamp_Certificate/search_applystamp");
		}
		
		/**
		 * 搜索印证申请
		 * @param request
		 * @param response
		 */
		@RequestMapping("/Office/StampCertificate/searchStampApplyInfor")
		public void searchStampApplyInfor(HttpServletRequest request,HttpServletResponse response){
			String searchkey = request.getParameter("searchkey");
			String pageNum=request.getParameter("pageNum");
			PageHelper.startPage(Integer.parseInt(pageNum), 12);
			List<ApplyStampInfor> bList = searchStampApplyI(searchkey);
			PageInfo<ApplyStampInfor> pageInfo = new PageInfo<ApplyStampInfor>(bList);
			JSONObject re = new JSONObject();
			re.put("blist", pageInfo.getList());
			re.put("pageInfo", pageInfo);
			try {			
				PrintWriter out = response.getWriter();
				out = response.getWriter();
				out.print(re);
				out.flush();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		public List<ApplyStampInfor>  searchStampApplyI(String searchkey){
	        //初始化容器
	        ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-mybatis.xml");
	        //获得bean
	        OfficeDao officeDao = ctx.getBean(OfficeDao.class);
	        List<ApplyStampInfor> bList = officeDao.searchStampApplyI(searchkey);     
			return bList;
	    }
		
		
		
}