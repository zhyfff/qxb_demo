package Hisign.Service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import Hisign.Service.SaveUserInforService;
import Hisign.dao.SaveUserIDao;
import Hisign.entity.UserInfor;

public class SaveUserInforImpl implements SaveUserInforService{

	@Autowired
	SaveUserIDao saveUserIDao;
	
	@Override
	public List<UserInfor> saveinfor(UserInfor userinfor) {
		// TODO Auto-generated method stub
		return saveUserIDao.saveinfor(userinfor);
	}

	@Override
	public List<UserInfor> updatepwd(String borrowid, String borrowname, String pwd) {
		// TODO Auto-generated method stub
		return saveUserIDao.updatepwd(borrowid, borrowname, pwd);
	}

	@Override
	public String searchpwd(String gh, String name) {
		// TODO Auto-generated method stub
		return saveUserIDao.searchpwd(gh, name);
	}

	@Override
	public List<UserInfor> searchpwdLogin(String gh) {
		return saveUserIDao.searchpwdLogin(gh);
	}

}