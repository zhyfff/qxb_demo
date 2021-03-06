package Hisign.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import Hisign.entity.ApplyCarInfor;
import Hisign.entity.ApplyStampInfor;
import Hisign.entity.BusinessCardInfor;
import Hisign.entity.CourierInfor;
import Hisign.entity.StampCertificateInfor;
import Hisign.entity.Car;

/**
 * 行政办公
 * @author Administrator
 *
 */
@Repository
public interface OfficeDao {
	public List<CourierInfor> SaveCourierNotice(CourierInfor courier);//提交快提醒信息入库
	public List<CourierInfor> SelectCourierNotice(@Param("searchkey")String searchkey);//查询快递提醒信息
	public List<CourierInfor> SearchRelq();//登记领取查询库中信息
	public List<CourierInfor> SaverRelq(CourierInfor courier);//保存领取人登记入库
	public List<CourierInfor> MyCourier(@Param("searchkey")String searchkey);//查询我的快递提醒
	public List<CourierInfor> SaveMyCourier(CourierInfor courier);//保存我的快递入库
	
	/********************************************************************/
	public List<Car> getcarlist();//获取车辆列表
	public List<Car> updateCar(@Param("state") String state,@Param("license_plate") String license_plate);//修改车辆状态
	public List<ApplyCarInfor> SaveApplyCar(ApplyCarInfor applyCarInfor);//提交车辆申请信息入库
	public List<ApplyCarInfor> selectApplyCar(@Param("searchkey")String searchkey);//查询提交的车辆申请信息
	public List<ApplyCarInfor> SaveApplyCarAudit(ApplyCarInfor applyCarInfor);//审核完毕入库
	public List<ApplyCarInfor> selectMyApplyCar(@Param("searchkey")String searchkey);//查询我提交的申请情况
	public List<ApplyCarInfor> SaveApplyCarAfterEv(ApplyCarInfor applyCarInfor);//车辆审核通过评价之后保存
	public List<ApplyCarInfor> selectMyCar(@Param("searchkey")String searchkey);//查询我借用的车辆；
	public List<ApplyCarInfor> sureBackApplyCar(@Param("searchkey")String searchkey);//确认车辆借用归还
	public List<ApplyCarInfor> SaveApplyCarBack(ApplyCarInfor applyCarInfor);//确认车辆归还入库
	public List<ApplyCarInfor> SelectApplyCarList(@Param("searchkey")String searchkey);//查询车辆借用信息列表
	public List<ApplyCarInfor> selectApplyCardetail(@Param("searchkey")String searchkey);//查询车辆借用详细信息
	
	/************************************************************************/
	public List<BusinessCardInfor> SaveApplyBusinessCard(BusinessCardInfor businessCardInfor);	//提交的名片制作申请入库
	public List<BusinessCardInfor> selectMyAuditBusinessCard(@Param("audit_name") String audit_name);//查询需要我审核的名片制作信息
	public List<BusinessCardInfor> send2DeskBusinessCard(BusinessCardInfor businessCardInfor);//审批后保存
	public List<BusinessCardInfor> selectNeedCreateBusinessCard(@Param("desk") String desk);//需要前台制作的名片
	public List<BusinessCardInfor> sendCreateMessage(BusinessCardInfor businessCardInfor);//发送名片制作完成消息，保存数据
	public List<BusinessCardInfor> selectMyBusinessCard(@Param("myname") String myname);//我申请的已制作好的名片
	public List<BusinessCardInfor> saveBEvBcard(BusinessCardInfor businessCardInfor);//保存评论完的信息
	public List<BusinessCardInfor> selectBusinessCard(@Param("searchkey") String searchkey);//查询名片制作申请信息
	
	/***********************************************************************/
	public List<StampCertificateInfor> selectAllstamp();//维护印章证照，查询所有证照信息
	public List<StampCertificateInfor> UpdateStamp(StampCertificateInfor stampCertificateInfor);//更新印章证照信息
	public List<ApplyStampInfor> saveApplyStamp(ApplyStampInfor applyStampInfor);//提交印章证照申请，保存数据
	public List<ApplyStampInfor> selectNeedAdutistamp(@Param("audit_name") String audit_name);//需要审核的印章证照申请
	public List<ApplyStampInfor> toManger(ApplyStampInfor applyStampInfor);//审核完成传给印章管理员
	public List<ApplyStampInfor> selectAuditedApplyStamp(@Param("manager") String manager);//获取审核完成的印章借用信息
	public List<ApplyStampInfor> toIssue(ApplyStampInfor applyStampInfor);//发放印章证照
	public List<StampCertificateInfor> changeStampState(StampCertificateInfor stampCertificateInfor);//改变证照状态
	public List<ApplyStampInfor> selectMyStamp(@Param("userName") String userName);//获取我的印章证照信息
	public List<ApplyStampInfor> toReturnStamp(ApplyStampInfor applyStampInfor);//归还印章证照
	public List<ApplyStampInfor> SureReturnStampApply(ApplyStampInfor applyStampInfor);//确认证照归还保存信息
	public List<ApplyStampInfor> selectAllStampI();//查询所有印章证照申请
	public List<ApplyStampInfor> searchStampApplyI(@Param("searchkey") String searchkey);//搜索印证申请
}
