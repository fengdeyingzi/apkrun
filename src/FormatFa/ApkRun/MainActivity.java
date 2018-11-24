package FormatFa.ApkRun;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import android.graphics.drawable.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import androidx.pluginmgr.*;
import com.jfpush.*;
import java.io.*;
import java.util.*;
import android.widget.AdapterView.*;
import android.text.*;
import FormatFa.ApkRun.Adapter.*;

public class MainActivity extends Activity
{
	
	private Button sd,start,startWithActivity,search,choosePath  ,searchtitle;
	private FileChoose fc;
	private TextView apkname;
	private ImageView apkicon;
	Context c;
	
	PluginManager plugMgr;
	ProgressDialog pd;
	
	//加载的apk,
	PlugInfo apk;
	JManager jf;
	
	
	//滚动到下面
	ScrollView mainScro;
	LinearLayout topLinear,bottomLinear;
	
	List<String> searchresult;
	PluginAdapter adapter;
	ListView searchResult;
	EditText searchpath;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		sd=(Button)findViewById(R.id.main_sd);
		
		apkname=(TextView)findViewById(R.id.main_name);
		start=(Button)findViewById(R.id.main_start);
	
		search=(Button)findViewById(R.id.main_search);
		choosePath=(Button)findViewById(R.id.main_choosepath);
		
		
		
		searchResult=(ListView)findViewById(R.id.main_searchresult);
		searchpath=(EditText)findViewById(R.id.main_searchpath);
		searchtitle=(Button)findViewById(R.id.main_searchtitle);
		mainScro=(ScrollView)findViewById(R.id.mainScrollView1);
		topLinear=(LinearLayout)findViewById(R.id.mainLinearLayout1);
		
		bottomLinear=(LinearLayout)findViewById(R.id.mainLinearLayout2);
		startWithActivity=(Button)findViewById(R.id.main_startwithactivity);
		apkicon=(ImageView)findViewById(R.id.main_icon);
		c=this;
//		
//	
	
		//插件类
		plugMgr=PluginManager.getInstance(c);
		
		
		apkicon.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					android.os.Process.killProcess(android.os.Process.myPid());
					// TODO: Implement this method
				}
			});
		sd.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					
					fc=new FileChoose(c,"选择Apk文件",Capkh,1);
					fc.show();
					// TODO: Implement this method
				}
			});
		search.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					
					new Thread(new Runnable(){

							@Override
							public void run()
							{
								searchh.sendEmptyMessage(0);

								searchresult=FormatFaUtils.searchFile(new File(searchpath.getText().toString()),".apk");
								//System.out.print(searchresult.size());
								
								searchh.sendEmptyMessage(1);
								// TODO: Implement this method
							}
						}).start();
					
					//searchStart();
					// TODO: Implement this method
				}
			});
			
		choosePath.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					fc=new FileChoose(c,"选择目录",Capkh,2);
					fc.setDir();
					fc.show();
					// TODO: Implement this method
				}
			});
		
		start.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{

					if(apk!=null)
					{
						if(!plugMgr.startMainActivity(c,apk.getPackageName()))
						{
							t("加载失败*_*");
						}

						}
						else
							t("请选择Apk文件");
					// TODO: Implement this method
				}
			});
		startWithActivity. setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{

					if(apk==null)
						t("请选择apk文件");
					else
					startMyActivity();

					// TODO: Implement this method
				}
			});
		
			
			Intent i=getIntent();
		
			Uri u=	i.getData();
		
			if(u!=null)
			loadApk(u.getPath());
			
			
		searchResult.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
					//t("点击;"+p3);
					loadApk(searchresult.get(p3).toString());
					// TODO: Implement this method
				}
			});
		searchtitle.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					if(bottomLinear.getVisibility()==View.INVISIBLE)
					{
						searchtitle.setText("隐藏");
					
					bottomLinear.setVisibility(View.VISIBLE);
					}
					else
					{
						searchtitle.setText("搜索");
					bottomLinear.setVisibility(View.INVISIBLE);
					}
					
					
					// TODO: Implement this method
				}
			});
			
    }
	
	
	
	
	
	Handler searchh=new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			
			if(msg.what==0)
			{
				
				pd=ProgressDialog.show(c,"0.0","搜索中..");
				bottomLinear.setVisibility(View.INVISIBLE);
			}
			if(msg.what==1)
			{
				//
		
				pd.dismiss();
				//t(searchresult.size()+"");
				adapter=new PluginAdapter(c,searchresult);

				
				searchResult.setAdapter(adapter);
				FormatFaUtils.setListViewHeightBasedOnChildren(searchResult);
				mainScro.scrollTo(0,topLinear.getHeight()+bottomLinear.getHeight());
			
			}
			// TODO: Implement this method
			super.handleMessage(msg);
		}
		
	}
	;
	
	
	
	//文件选择
	Handler Capkh=new Handler(){

		@Override
		public void handleMessage(Message msg)
		{
			if(msg.what==1)
				{
					loadApk(fc.getPath());
				}
				if(msg.what==2)
					searchpath.setText(fc.getPath());
			// TODO: Implement this method
			super.handleMessage(msg);
		}};
		
		
		
		
		
		
	
	Handler loadh=new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			int w=msg.what;
			if(w==0)
				pd=ProgressDialog.show(c,"^_^","加载Apk信息ing");
				if(w==1)
				{
					//apkname.setText(apk.);
					
					int labelRes =apk.getPackageInfo().applicationInfo.labelRes;
					if (labelRes != 0) {
						String label = apk.getResources().getString(labelRes);
						apkname.setText(label);
					} else{
						CharSequence label = apk.getPackageInfo().applicationInfo
							.loadLabel(c.getPackageManager());
						if (label != null) {
							apkname.setText(label);
						}
					}
		
				Drawable drawable = apk.getResources().getDrawable(
					apk.getPackageInfo().applicationInfo.icon);
				apkicon.setImageDrawable(drawable);
				mainScro.scrollTo(0,0);
					pd.dismiss();
				}
			//文件不存在
			if(w==2)
			{
				pd.dismiss();
				t("文件加载失败");
			}
			// TODO: Implement this method
			super.handleMessage(msg);
		}
		
	};
	void loadApk(final String path)
	{
		
		new Thread(new Runnable(){

				@Override
				public void run()
				{
					loadh.sendEmptyMessage(0);
					try
					{
						apk=plugMgr.loadPlugin((new File(path)));
					}
					catch (Exception e)
					{
						loadh.sendEmptyMessage(2);
						return;
					}
					if(apk==null)loadh.sendEmptyMessage(2);
						
					loadh.sendEmptyMessage(1);
					// TODO: Implement this method
				}
			}).start();
		
	}
	
	
	//启动指定的
	void startMyActivity()
	{

		final List<ResolveInfo> activity;
		activity=new ArrayList<ResolveInfo>(apk.getActivities());
		
		
		final String[] tempname=new String[activity.size()];
		for(int i=0;i<activity.size();i+=1)
			tempname[i]=activity.get(i).activityInfo.name.toString();
		AlertDialog.Builder ab=new AlertDialog.Builder(MainActivity.this);
		ab.setItems(tempname, new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					plugMgr.starTheActivity2(MainActivity.this,tempname[p2]);
				}
			});
		ab.show();
	}
	
	
	void t(String msg)
	{
		Toast.makeText(c,msg,2000).show();
	}
	
	
	
	Handler Cleanh=new Handler(){

		@Override
		public void handleMessage(Message msg)
		{
			if(msg.what==0)
			{
				pd=ProgressDialog.show(c,"^_^","清理缓存.....");
			}
			if(msg.what==1)
			{
				pd.dismiss();
				t("清理完成!");
			}
			// TODO: Implement this method
			super.handleMessage(msg);
		}};

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add(1,1,1,"关于");
		menu.add(2,2,2,"支持^_^");
		menu.add(3,3,3,"清理缓存");
		//menu.add(4,4,4,"设置");
		// TODO: Implement this method
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		
		if(item.getItemId()==1)
		{
			FormatFaUtils.Fdialog(c,"关于",FormatFaUtils.getAssetsString(c,"info.txt"),"确定",null,null,null);
			
		}
		if(item.getItemId()==2)
		{
			
	jf=JManager.getInstance(MainActivity.this,"6bf4cd22cc8c25c1420f6ef67582b876","official");
	jf.setResId(R.layout.f_custom_noti, R.id.noti_icon, R.id.noti_title, R.id.noti_time,
	R.id.noti_content);
	jf.getMessage(c,true);
	
		}
		if(item.getItemId()==3)
		{
			new Thread(new Runnable(){

					@Override
					public void run()
					{
						Cleanh.sendEmptyMessage(0);

						FormatFaUtils.deletedir(c.getDir("plugsout",MODE_PRIVATE));
						FormatFaUtils.deletedir(c.getDir("plugins",MODE_PRIVATE));
						Cleanh.sendEmptyMessage(1);
						// TODO: Implement this method
					}
				}).start();
			
		}
		
		
		// TODO: Implement this method
		return super.onOptionsItemSelected(item);
	}
		
		
		
	
	
		
		
		}
