package FormatFa.ApkRun;
import android.content.*;
import android.graphics.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import android.graphics.drawable.*;
import android.content.res.*;
import android.app.*;
import android.content.pm.*;

public class FormatFaUtils
{	



	

	
	static void Fdialog(Context c,String title,String msg,String button1,String button2,DialogInterface.OnClickListener listener1,DialogInterface.OnClickListener listener2)
	{


		//AlertDialog.Builder ab=new Fdialog.Builder(c);
		Fdialog.Builder fb=new Fdialog.Builder(c);
		fb.setTitle(title);
		fb.  setMessage(msg);
		if(button1!=null)
			fb.setPositiveButton(button1,listener1);
		if(button2!=null)
			fb.setNegativeButton(button2,listener2);
		fb.create().show();
	}
	//获取文件管理Adapter,filter过滤文件后缀,inclidedir是否包含文件夹,
	
	
	static SimpleAdapter getAdapter(Context con,File[] filename)
	{
		
		
		Map<String, Object> map;

	
		SimpleAdapter sa;
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		SimpleDateFormat formatfa=new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
		for (int i=0;i < filename.length;i += 1)
		{
			
			
			map=new HashMap<String,Object>();
			map.put("name",filename[i].getName());

			map.put("date", formatfa.format(new Date(filename[i].lastModified())));

			map.put("size", FormatFaUtils.filesize(  filename[i].length()));




			if(filename[i].isDirectory())
			{

				map.put("icon",R.drawable.ic_folder);
			}
			else
			{
				String end=FormatFaUtils. gettype(filename[i].getName());
				int drawableid=0;
				switch(end)
				{

					case ".apk": 
					
						drawableid=R.drawable.ic_apk;
						break;
					
					default :drawableid=R.drawable.ic_unknow;
				}




				map.put("icon",drawableid);


			}
			list.add(map);
		}
		
		sa=new SimpleAdapter(con,list,R.layout.filelist_item,new String[]{"name","date","size","icon"},new int[]{R.id.fileitem_name,R.id.fileitem_date,R.id.fileitem_size,R.id.fileitem_icon});
	
		
		return sa;
		
		}

		//获取适配器
		
	
		
		
		

	
	public static View getlayout(int layout,Context con)
	{

		LayoutInflater lf=(LayoutInflater)con.getSystemService(con.LAYOUT_INFLATER_SERVICE);

		return lf.inflate(layout, null);

	}


	


	static Boolean deletedir(final File f)
	{
//System.out.println("删除文件夹"+f.getAbsolutePath());
	if(f.isFile())
	return f.delete();
	


			if(f.isDirectory())
					{


						
							//列出}当前要搜索的文件
							File[] temp=f.listFiles();
							for (int a=0;a < temp.length;a += 1)
							{
					
			
									//文件就删除
									temp[a].delete();

								
							}

							//处理完一个就增加一
								//把剩下的空文件夹删除,从里删到外
						
								
					}

					// TODO: Implement this method

return true;
}
	//文件排序
	static File[] paixu(File[] o)
	{
		List<File> file=new ArrayList<File>();
		List<File> dir=new ArrayList<File>();
		//第2个开始
		for(int a=1;a<o.length-1;a+=1)
		{
			

			
			//如果a是1就和前1个比较
			for(int b=0;b<a;b+=1)
			{
				int p=o[a].getName().compareToIgnoreCase(o[b].getName());
				
				
				if(p<0)
				{
					
					
					File temp=o[a];
					o[a]=o[b];
					o[b] =temp;
					
				}
			}
	
			
			}

			for(int c=0;c<o.length;c+=1)
			{
				if(o[c].isDirectory())
					dir.add(o[c]);
				else
				{
					if(o[c].getName().endsWith(".apk"))
					file.add(o[c]);
					
					}
				
				
			}
		
		File[] result=new File[file.size()+dir.size()];
		System.arraycopy(dir.toArray(),0,result,0,dir.toArray().length);
	
		System.arraycopy(file.toArray(),0,result,dir.toArray().length,file.toArray().length);
		
		
		return result;
		}
	static String filesize(long bytelen)
	{
		
		if(bytelen<1024)
		{
			return bytelen+"b";
		}
		
		else if(bytelen>=1024&&bytelen<1048576)
		{
			return (float)(bytelen/1024.0f)+"k";
		}
		
		else if(bytelen>=1048576)
		{
			return bytelen/1024.0f/1024.0f+"m";
		}
		
		return null;
	}
	
	
//获取文件类型,返回,如.mrp
	static String gettype(String name)
	{
		int p=name.lastIndexOf(".");
		if(p==-1)
			return "";
		return name.substring(p);
	}
	//判段文件名是否以指定的结尾
	

	//isdir,是否包含文件夹
	static List<String> searchFile(File path,String name)
	{



		List<String> result=new ArrayList<String>();

		
		if(path.isDirectory())
		{


			//用来存放文件夹的
			List<File> lf=new ArrayList<File>();

			//先增加要删除的
			lf.add(path);

			//用来循环的条件
			int i=0;


			//当i不等于所有文件夹的长度时，就搜索,不知道共有几个，用while
			while (i != lf.size())
			{

				//列出}当前要搜索的文件
				File[] temp=lf.get(i).listFiles();
				for (int a=0;a < temp.length;a += 1)
				{
					if (temp[a].isDirectory())
					{

						//是文件夹就增加进去
						lf.add(temp[a]);
						//包含文件夹
						
						
						
					}
					else
					{

					
						//文件就判断
						if(temp[a].getName().lastIndexOf(name)!=-1)
						{
							
						
								result.add(temp[a].getAbsolutePath());
							
						}


					}
				}

				//处理完一个就增加一
				i+=1;

			}


		}

		//是文件
		else
		{

		}
		return result;

	}
	

	static  void setListViewHeightBasedOnChildren(ListView listView) {   
		ListAdapter listAdapter = listView.getAdapter();         
		if (listAdapter == null) {     
			// pre-condition         
			return;              }    
		int totalHeight = 0;      
		for (int i = 0; i < listAdapter.getCount(); i++) 
		{        
			View listItem = listAdapter.getView(i, null, listView);    

			listItem.measure(0, 0); 
			totalHeight += listItem.getMeasuredHeight();    
		}                

		ViewGroup.LayoutParams params = listView.getLayoutParams();         
		params.height = totalHeight   + (listView.getDividerHeight() * (listAdapter.getCount() - 1));           
		listView.setLayoutParams(params);      }
	public static Boolean stringtosd(String str,String path,Boolean app)
	{


		
		File f=new File(path);

		if(!f.getParentFile().exists())
			f.getParentFile().mkdirs();

			if(!f.exists())
		try
		{
			f.createNewFile();
		}
		catch (IOException e)
		{
			return false;
		}
		OutputStream os=null;
		try
		{
			os = new FileOutputStream(f,app);
		}
		catch (FileNotFoundException e)
		{
			return false;
		}

		try
		{
			os.write(str.getBytes());
		}
		catch (IOException e)
		{
			return false;
		}

		try
		{
			os.close();
		}
		catch (IOException e)
		{

			return false;
		}
		return true;
	}
	
	
	static String getAssetsString(Context c,String  name)
	{
		
		AssetManager am=c.getAssets();
		
		InputStream is=null;
		try
		{
			is = am.open(name);
		}
		catch (IOException e)
		{
			return ("获取:"+name+"失败!");
		}

		byte[] buff=null;
		
		try
		{
			buff = new byte[is.available()];
		}
		catch (IOException e)
		{
			return ("获取:"+name+"失败!");
			
		}

		try
		{
			is.read(buff);
		}
		catch (IOException e)
		{
			return ("获取:"+name+"失败!");
		}
		return new String(buff);
		
		
	}
	
}
