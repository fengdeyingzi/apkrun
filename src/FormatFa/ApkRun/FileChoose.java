package FormatFa.ApkRun;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.io.*;
import java.util.*;

public class FileChoose
{
	
	Context c;

	
	
	File nowpath;
	File[] files;
	
	
	
	
	//
	String splastpath="lastpath";
	AlertDialog.Builder dialog;
	
	ProgressDialog pd;
	SharedPreferences sp;
	
	
	
	RelativeLayout layout;
	ListView lv;
	Button bup;
	TextView ttitle,tmsg;
	
	SimpleAdapter sa;
	
	//是否选择文件夹,是否以选择
	Boolean dir=false,ischoose=false;
	
	
	//传抵消息
	Handler h;
	//选择时,发送到handle
	int selectmsg;
	//选择文件类
	FileChoose(Context con,String title,Handler handler,final int selectmsg)
	{
		this.c=con;
		h=handler;
		this.selectmsg=selectmsg;
		dialog=new AlertDialog.Builder(c,R.style.f);
	
		sp=c.getSharedPreferences("filechoose",c.MODE_PRIVATE);
		
		nowpath=new File(sp.getString(splastpath,"/sdcard"));
		
		
		layout=(RelativeLayout)FormatFaUtils.getlayout(R.layout.filechoose,c);
		
		lv=(ListView)layout.findViewById(R.id.fc_list);
		

		lv.setOnItemClickListener(new lvonclick());
		
		ttitle=(TextView)layout.findViewById(R.id.fc_title);
		tmsg=(TextView)layout.findViewById(R.id.fc_msg);
		bup=(Button)layout.findViewById(R.id.fc_up);
		
		
		ttitle.setText(title);
		
		dialog.setView(layout);
		
		dialog.setPositiveButton("选择", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					
					if(ischoose)
					
					h.sendEmptyMessage(selectmsg);
					// TODO: Implement this method
				}
			});
		dialog.setNegativeButton("取消", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{

					h.sendEmptyMessage(0);
					// TODO: Implement this method
				}
			});
		
		bup.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					
					if(nowpath.getAbsolutePath().equals("/"))
						return;
					nowpath=nowpath.getParentFile();
					uplist();
					// TODO: Implement this method
				}
			});
	}
	
	void show()
	{
		
		uplist();
		dialog.show();
		//选择文件夹
		

	}
	String getPath()
	{
		
		
		if(dir)
		return nowpath.getAbsolutePath();
		//不是选择文件夹
		if(tmsg.getText().toString()==null)
		{
			
		
			return null;
			
			}
			
		return nowpath.getAbsolutePath()+"/"+tmsg.getText().toString();
	}
	private class lvonclick implements  ListView. OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
		{
			
			
			File f=files[p3];
			
			if(!f.canRead())
			{
				Toast.makeText(c,"读取失败*_<",2000).show();
				return;
				
			}
			if(f.isFile())
			{
				
			
		
				if(!dir)
				{
				tmsg.setText(f.getName());
				ischoose=true;
				}
			
			}
			if(f.isDirectory())
			{
				nowpath=f;
				uplist();
				
			}
			
			// TODO: Implement this method
		}

		
		
	}
	
	
	Handler uplisth=new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			if(msg.what==0)
			{
				pd=ProgressDialog.show(c,"^_<","加载列表中.....");
				
			}
			if(msg.what==1)
			{
				pd.dismiss();
				tmsg.setText("");
				lv.setAdapter(sa);
				
			}
			
			
			// TODO: Implement this method
			super.handleMessage(msg);
		}
		
		
		
	};
	
	//更新列表
	void uplist()
	{
		Runnable uplistR=new Runnable()
		{

			@Override
			public void run()
			{

				
				uplisth.sendEmptyMessage(0);

				files=nowpath.listFiles();

				
			
				
				
			
				files=FormatFaUtils.paixu(files);
				
				sa=FormatFaUtils.getAdapter(c,files);


				sp.edit().putString(splastpath,nowpath.getAbsolutePath()).commit();
				uplisth.sendEmptyMessage(1);


				// TODO: Implement this method
			}


		};
		
		
		if(!nowpath.canRead())
		{
			return;
		}
		
		new Thread(uplistR).start();
		
		
		
	}
	
	//设置启动目录
	void setFirstPath(String path)
	{
		nowpath=new File(path);
		
	}
	void setDir()
	{
		
		dialog.setPositiveButton("选择当前文件夹", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{

					h.sendEmptyMessage(selectmsg);
					// TODO: Implement this method
				}
			});
		
		
		
	}
	
	
	
	
}
