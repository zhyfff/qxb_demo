package Hisign.entity;

public class ProcessInfo {
	private String _id;//id
	private String dcnumber;//
	private String name;//发起者
	private String audit_name;//审批人
	private String manager;//管理员
	private String type;//所属分类
	private String state;//处理状态
	private String insert_time;//插入时间
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getDcnumber() {
		return dcnumber;
	}
	public void setDcnumber(String dcnumber) {
		this.dcnumber = dcnumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAudit_name() {
		return audit_name;
	}
	public void setAudit_name(String audit_name) {
		this.audit_name = audit_name;
	}
	public String getManager() {
		return manager;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getInsert_time() {
		return insert_time;
	}
	public void setInsert_time(String insert_time) {
		this.insert_time = insert_time;
	}

	

}
