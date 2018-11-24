package FormatFa.ApkRun.Adapter;

import FormatFa.ApkRun.*;
import android.content.*;
import android.content.pm.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import android.graphics.drawable.*;

public class PluginAdapter extends BaseAdapter
{

	List<String> paths;
	Context c;
	public PluginAdapter (Context c,List<String> paths)
	{
		
		
		this.paths=paths;
		this.c=c;
		
		
	}
	@Override
	public int getCount()
	{
		// TODO: Implement this method
		return paths.size();
	}

	@Override
	public Object getItem(int p1)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public long getItemId(int p1)
	{
		// TODO: Implement this method
		return 0;
	}

	@Override
	public View getView(int p1, View p2, ViewGroup p3)
	{
		
		
		LinearLayout v;
		v=(LinearLayout)FormatFaUtils.getlayout(R.layout.plugin_item,c);
		
		
		ImageView icon=(ImageView)v.findViewById(R.id.pluginitemImageView1);
		TextView name=(TextView)v.findViewById(R.id.apkname);
		TextView path=(TextView)v.findViewById(R.id.apkpath);
		
		
		PackageManager pm = c.getPackageManager();

		PackageInfo info = pm.getPackageArchiveInfo(paths.get(p1).toString(),

													PackageManager.GET_ACTIVITIES);

		if(info != null) {

			ApplicationInfo appInfo = info.applicationInfo;

			appInfo.sourceDir = paths.get(p1).toString();

			appInfo.publicSourceDir = paths.get(p1).toString();

			
			
			Drawable Dicon;
			
			Dicon= appInfo.loadIcon(pm);

			if(icon!=null)
				icon.setImageDrawable(Dicon);
				
				
				name.setText( appInfo.loadLabel(pm));
				path.setText(paths.get(p1).toString());
				
				}
		
		// TODO: Implement this method
		return v;
	}

}
