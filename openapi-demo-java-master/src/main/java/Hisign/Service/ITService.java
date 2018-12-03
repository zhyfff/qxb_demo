package Hisign.Service;

import java.util.List;

import Hisign.entity.ConsumableBorrowInfor;
import Hisign.entity.ConsumableListInfor;
import Hisign.entity.ConsumablePurInfor;
import Hisign.entity.EquipBorrowInfor;
import Hisign.entity.FixedAssetsInfor;

public interface ITService {
	public List<ConsumablePurInfor> consu_insert(ConsumablePurInfor consumablePurInfor);//保存耗材采购申请
	public List<ConsumablePurInfor> consu_audit(ConsumablePurInfor consumablePurInfor);//审批
	public List<ConsumableListInfor> addNewCon(ConsumableListInfor consumableListInfor);//新增耗材种类
	public List<ConsumableListInfor> getConsumableInfor(String type_num);//获取耗材列表
	public List<ConsumableListInfor> UpdateConsumableInfor(ConsumableListInfor consumableListInfor);//更新耗材信息
	public List<ConsumableListInfor> EndApplyConsu(String dcnumber);
	public List<ConsumablePurInfor> searchConsumApply(String searchkey);//查询耗材采购申请信息
	/*****************************************************************************************/
	public List<EquipBorrowInfor> eborrow_insert(EquipBorrowInfor equipBorrowInfor);//保存设备借用申请
	public List<EquipBorrowInfor> eborrow_audit(EquipBorrowInfor equipBorrowInfor);//审批设备借用
	public List<EquipBorrowInfor> getMyEboroowInfor(String userid);//获取我的设备借用
	public List<EquipBorrowInfor> eborrow_return(EquipBorrowInfor equipBorrowInfor);//归还设备
	public List<EquipBorrowInfor> eborrow_setEva(EquipBorrowInfor equipBorrowInfor);//确认归还评论反馈
	public List<EquipBorrowInfor> searchEBorrowApply(String searchkey);//查询设备借用申请
	/********************************************************************************************/
	public List<ConsumableBorrowInfor> Cborrow_insert(ConsumableBorrowInfor consumableBorrowInfor);//保存耗材申领信息
	public List<ConsumableBorrowInfor> ConsuBorrowAudit(ConsumableBorrowInfor consumableBorrowInfor);//保存耗材申领审批信息
	public List<ConsumableBorrowInfor> ConsuBorrowIssu(ConsumableBorrowInfor consumableBorrowInfor);//保存耗材管理员发放信息
	public List<ConsumableBorrowInfor> ConsuEva(ConsumableBorrowInfor consumableBorrowInfor);//保存评价
	public List<ConsumableListInfor> getgetConsumableNumList(String recipients_number);//获取耗材数量
	public List<ConsumableListInfor> ChangeConsumableNumAndP(String recipients_number,String l_cnum,String l_tup);//修改发放后耗材的数量总价
	/**********************************************************************************************/
	public List<FixedAssetsInfor> FixedAssets_insert(FixedAssetsInfor fixedAssetsInfor);//保存固定资产申请



}
