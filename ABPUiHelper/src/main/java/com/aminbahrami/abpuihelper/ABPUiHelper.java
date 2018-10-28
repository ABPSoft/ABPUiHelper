package com.aminbahrami.abpuihelper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ABPUiHelper
{
	private Typeface typeface;
	
	protected void parseUi(Context context,View view,Resources resources)
	{
		parseUi(view,resources,context.getPackageName());
	}
	
	public void parseUi(Context context,View view)
	{
		parseUi(view,context.getResources(),context.getPackageName());
	}
	
	public void parseUi(View view,Resources resources,String packageName)
	{
		Field[] fields=getClass().getDeclaredFields();
		
		for(Field field : fields)
		{
			//Important
			field.setAccessible(true);
			
			int id=resources.getIdentifier(field.getName(),"id",packageName);
			
			try
			{
				View view1=view.findViewById(id);
				
				if(getTypeface()!=null)
				{
					try
					{
						Method setTypeface=field.getType().getMethod("setTypeface",Typeface.class);
						setTypeface.invoke(view1,getTypeface());
					}
					catch(NoSuchMethodException ignored)
					{
					
					}
				}
				
				field.set(this,field.getType().cast(view1));
			}
			catch(IllegalAccessException|IllegalArgumentException e)
			{
				//e.printStackTrace();
			}
			catch(Exception e)
			{
				Log.e("ABPUiHelper","Error on Field: "+field.getName()+" with type "+field.getType().getCanonicalName());
			}
		}
	}
	
	protected Typeface getTypeface()
	{
		return typeface;
	}
	
	protected void setTypeface(Typeface typeface)
	{
		this.typeface=typeface;
	}
}
