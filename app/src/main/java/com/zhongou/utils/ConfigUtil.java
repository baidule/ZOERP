package com.zhongou.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.zhongou.db.entity.UserEntity;

/**
 * 中断保存设置
 * gson包
 * @author JackSong
 *
 */
public class ConfigUtil {
	protected SharedPreferences sp;
	protected SharedPreferences.Editor editor;


	private static final String STOREID = "storeId";//公司编号
	private static final String WORKID = "workId";//工号
	private static final String PSD = "password";//密码
	
	private static final String AUTO_LOGIN = "auto_login";
	private static final String MEMBER_ENTITY = "user_entity";
	private static final String PUSH_CLIENTID = "push_clientId";//个推clientID

	private static final String WIFINAME = "WIFI_NAME";//wifi名称
	private static final String MACADRESS = "WIFI_MAXADRESS";//wifi的mac地址


	//登录内容保存
	@SuppressLint("CommitPrefEdits")
	public ConfigUtil(Context context) {
		try {
			//获取SharePreferences实例，保存位置是config.xml文件，模式是私有模式
			//文件存放在/data/data/<package name>/shared_prefs/config.xml
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
			//获得编辑器 ,方便将内容添加到Sharepreferences中
			editor = sp.edit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//
	public void resetConfig() {
		setWorkId(null);
		setStoreId("");
		setAutoLogin(true);
	}

	//调用该方法，将值保存
	public void put(String key, String value) {
		editor.putString(key, value);
		editor.commit();
	}

	//获取值，没有对应值返回""
	public String get(String key) {
		return sp.getString(key, "");
	}

	//保存boolean值
	public void putBoolean(String key, Boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
	}

	//返回boolean值，没有对应值，返回false
	public Boolean getBoolean(String key) {
		return sp.getBoolean(key, false);
	}

	/**
	 * 将js转换成对象，需要调用外部jar包，
	 * 获取所有当前用户的信息
	 */
	public UserEntity getUserEntity() {
		//返回member_entity的信息，没有就返回null
		String string = sp.getString(MEMBER_ENTITY, null);
		if (string != null && string.length() > 0) {
			try {
				return new Gson().fromJson(string, UserEntity.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	//保存对象，转换成js格式保存，需要调用外部jar包
	//CreateUserActivity--UserHelper--ConfigUtil该方法
	public void setUserEntity(UserEntity userManagers) {
		editor.putString(MEMBER_ENTITY, new Gson().toJson(userManagers));//"member_entity"
		editor.commit();
	}

	//密码
	public String getPassword() {
		return sp.getString(PSD, null);
	}

	public void setPassword(String psd) {
		editor.putString(PSD, psd);
		editor.commit();
	}

	//工号
	public String getWorkId() {
		return sp.getString(WORKID, null);//userid
	}

	//工号
	public void setWorkId(String id) {
		editor.putString(WORKID, id);
		editor.commit();
	}

	//公司编号
	public String getStoreId() {
		return sp.getString(STOREID, "");
	}

	//公司编号
	public void setStoreId(String account) {
		editor.putString(STOREID, account);
		editor.commit();
	}

	//返回自动登录状态
	public boolean getAutoLogin() {
		return sp.getBoolean(AUTO_LOGIN, true);
	}

	//保存登录状态
	//MainVisitorActivity--UserHelper--ConfigUtil该方法：若是点击退出，就不再自动自动登录
	public void setAutoLogin(boolean play) {
		editor.putBoolean(AUTO_LOGIN, play);
		editor.commit();
	}

	//个推ClientID

	public String getPushClientid() {
		return sp.getString(PUSH_CLIENTID, "");
	}

	public void setPushClientid(String pushClientid) {
		editor.putString(PUSH_CLIENTID, pushClientid);
		editor.commit();
	}

	//wifi名称
	public String getWifiName(){
		return sp.getString(WIFINAME,"");
	}
	public void setWifiName(String SSID){//wifi名称
		editor.putString(WIFINAME,SSID);
		editor.commit();
	}

	//wifi的mac
	public String getMacAddress(){
		return sp.getString(MACADRESS,"");
	}
	public void setMacAddress(String macAdress){
		editor.putString(MACADRESS,macAdress);
		editor.commit();
	}
}
