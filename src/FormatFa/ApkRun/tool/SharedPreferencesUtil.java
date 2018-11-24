package FormatFa.ApkRun.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

/*
数据保存与读取

风的影子
*/

public class SharedPreferencesUtil{
	public  SharedPreferences sha;
	public  SharedPreferences.Editor editor;
	
	public SharedPreferencesUtil(Context context)
	{
		sha = PreferenceManager.getDefaultSharedPreferences(context);
		this.editor = sha.edit();
	}
	
	public  void setSharedPreferencesName(String name,Context context){
		sha = context.getSharedPreferences(name,context.MODE_PRIVATE);
		editor = sha.edit();
	}
	/*
	 *获取Boolean数据
	 */
	public  void setBoolean(String Name,boolean Data){
		editor.putBoolean(Name, Data);
		editor.commit();
	}
	/*
	 *获取String数据
	 */
	public  void setString(String Name,String Data){
		editor.putString(Name, Data);
		editor.commit();
	}
	/*
	 *获取Int数据 
	 */
	public  void setInt(String Name,int Data){
		editor.putInt(Name, Data);
		editor.commit();
	}
	/*
	 *获取Float数据
	 */
	public  void setFloat(String Name,float Data){
		editor.putFloat(Name,Data);
		editor.commit();
	}
	/*
	 *得到Boolean数据
	 */
	public  boolean getBoolean(String Name,boolean Data){
		return sha.getBoolean(Name,Data);
	}
	/*
	 *得到String数据
	 */
	public  String getString(String Name,String Data){
		return sha.getString(Name,Data);
	}
	/*
	 *得到Int数据
	 */
	public  int getInt(String Name,int Data){
		return sha.getInt(Name,Data);
	}
	/*
	 *得到Float数据
	 */
	public  float getFloat(String Name,float Data){
		return sha.getFloat(Name,Data);
	}
	
	public void commit()
	{
		if(Build.VERSION.SDK_INT>=9)
			editor.apply();
		else
			editor.commit();
	}
	
}
