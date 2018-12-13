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
import FormatFa.ApkRun.tool.*;
import com.github.dfqin.grantor.*;

public class MainActivity extends Activity
{
	
	private Button sd,start,startWithActivity,search,choosePath  ,searchtitle;
	private Button btn_package;
	private FileChoose fc;
	private TextView apkname;
	private ImageView apkicon;
	boolean isStart;
	static boolean isStop;
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
	CheckBox check;
	
	
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
		
		btn_package = (Button) findViewById(R.id.btn_package);
		btn_package.setOnClickListener(new View.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					Toast.makeText(p1.getContext(), "直接启动",0).show();
					if(fc!=null)
					AppTool.installApk(p1.getContext(), fc.getPath());
					else{
						Intent intent = getIntent();
						if(intent.getData()!=null){
							AppTool.installApk(p1.getContext(), intent.getData().getPath());
						}
					}
				}
				
			
		});
		
		searchResult=(ListView)findViewById(R.id.main_searchresult);
		searchpath=(EditText)findViewById(R.id.main_searchpath);
		searchtitle=(Button)findViewById(R.id.main_searchtitle);
		mainScro=(ScrollView)findViewById(R.id.mainScrollView1);
		topLinear=(LinearLayout)findViewById(R.id.mainLinearLayout1);
		
		bottomLinear=(LinearLayout)findViewById(R.id.mainLinearLayout2);
		startWithActivity=(Button)findViewById(R.id.main_startwithactivity);
		apkicon=(ImageView)findViewById(R.id.main_icon);
		
		 check = (CheckBox) findViewById(R.id.isStart);
		SharedPreferencesUtil prefer = new SharedPreferencesUtil(this);
		check.setChecked(prefer.getBoolean("isStart", false));
		check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton p1, boolean p2)
				{
					// TODO: Implement this method
					isStart = p2;
				}
				
			
		});
		isStart = check.isChecked();
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
		boolean loaded = false;
			Uri u=	i.getData();
		String log="";
			if(u!=null){
				//SharedPreferencesUtil prefer = new SharedPreferencesUtil(this);
			String guolv = prefer.getString("guolv","");
            String items[] = guolv.split("\\|");
            
            for(String item:items){
				Toast.makeText(this,item,0).show();
				log+=item+"\n";
                    if(u.getPath().indexOf(item)>=0){
						log+="运行\n";
                        AppTool.installApk(this,u.getPath());
                        loaded   = true;
						finish();
                    }
                        
            }
				
if(!loaded){
	log+="直接运行\n";
	loadApk(u.getPath());
	
			//Toast.makeText(this,"加载apk",0).show();
			}
			}
	apkname.setText(log);
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
					Intent i=getIntent();

					Uri u=	i.getData();

					if(u!=null && (!isStop)){
						isStop = true;
					if(isStart)
					start.performClick();
					else
						btn_package.performClick();
					//System.exit(0);
					//Toast.makeText(MainActivity.this,"启动中",0).show();
					MainActivity.this.finish();
				    }
				
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
	
	//预加载apk
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
		
		
	//加载布局成view
	public static View getView(Context context,int id)
	{
		LayoutInflater factory =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		//LayoutInflater factory = LayoutInflater.from(context);
		return  factory.inflate(id, null);
	}
	
		

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add(1,1,1,"关于");
		menu.add(2,2,2,"支持^_^");
		menu.add(3,3,3,"清理缓存");
		menu.add(4,4,4,"关键字过滤");
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
		if(item.getItemId() == 4){
			View layout = getView(this,R.layout.dlg_guolv);
			final EditText edit = (EditText) layout.findViewById(R.id.dlg_edit);
			SharedPreferencesUtil prefer = new SharedPreferencesUtil(MainActivity.this);
            edit.setText( prefer.getString("guolv",""));
			new AlertDialog.Builder(this)
				.setTitle("过滤")
				.setView(layout)
				.setPositiveButton("确定", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialogInterface, int n) {

						SharedPreferencesUtil prefer = new SharedPreferencesUtil(MainActivity.this);
						prefer.setString("guolv", edit.getText().toString());
					}
				})

				.setNegativeButton("取消", new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface p1, int p2)
					{
						// TODO: Implement this method
						
					}


				})
				.setCancelable(false)
				.show();
		}
		
		
		// TODO: Implement this method
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		SharedPreferencesUtil prefer = new SharedPreferencesUtil(this);
		prefer.setBoolean("isStart", check.isChecked());
		isStop = true;
		super.onPause();
	}

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		
		super.onResume();
		PermissionsUtil.requestPermission(getApplication(), new PermissionListener() {
				@Override
				public void permissionGranted( String[] permissions) {
					//toast("");
					//onLoad();
				}

				@Override
				public void permissionDenied( String[] permissions) {
					//Toast.makeText(MainActivity.this, "用户拒绝了访问摄像头", Toast.LENGTH_LONG).show();
				}
			}, android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
		
		if(isStop){
		Intent i=getIntent();

		Uri u=	i.getData();

		if(u!=null && isStart){
			System.exit(0);
			}
		}
	}
		
		
    
		
	
	
		
		
		}
