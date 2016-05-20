package com.Meetok.Application;

import java.util.LinkedList;   
import java.util.List;   










import com.imooc.tab03.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.app.Activity;   
import android.app.ActivityManager;
import android.app.Application;   
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
/** 
 * 一个类 用来结束所有后台activity 
 * @author Administrator 
 * 
 */  
public class SysApplication extends Application { 
	public static Context mContext;
    private List<Activity> mList = new LinkedList<Activity>();  
    public static SysApplication instance;   
    public SysApplication(){}  
    //实例化一次  
    public synchronized static SysApplication getInstance(){   
        if (null == instance) {   
            instance = new SysApplication();   
        }   
        return instance;   
    }   
    @SuppressWarnings("deprecation")
	@Override
	public void onCreate() {
		super.onCreate();
		instance=this;

		DisplayImageOptions options = new DisplayImageOptions.Builder()
		        .showStubImage(R.drawable.blank) // ����ͼƬ�����ڼ���ʾ��ͼƬ
				.showImageForEmptyUri(R.drawable.icon_empty) // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
				.showImageOnFail(R.drawable.icon_error) // ����ͼƬ���ػ��������з���������ʾ��ͼƬ
				.cacheInMemory(true) // �������ص�ͼƬ�Ƿ񻺴����ڴ���
				.cacheOnDisk(true) // �������ص�ͼƬ�Ƿ񻺴���SD����
			    //.displayer(new RoundedBitmapDisplayer(20)) // ���ó�Բ��ͼƬ
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.delayBeforeLoading(100)
				.displayer(new FadeInBitmapDisplayer(100))
				.build(); // �������ù���DisplayImageOption����

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
		        .defaultDisplayImageOptions(options)
				.threadPoolSize(3)
				.memoryCacheSize(2 * 1024 * 1024)//
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheSize(50 * 1024 * 1024)
				//.memoryCacheExtraOptions(480, 800)即保存的每个缓存文件的最大长宽 
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))// 你可以通过自己的内存缓存实现
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.build();
		ImageLoader.getInstance().init(config);
    }
    public void addActivity(Activity activity) {   
        mList.add(activity);   
    }   
  //关闭每一个list内的activity  
    public void exit() {   
        try {   
            for (Activity activity:mList) {   
                if (activity != null)   
                    activity.finish();   
            }   
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            System.exit(0);   
        }   
    }   
    //杀进程  
    public void onLowMemory() {   
        super.onLowMemory();       
        System.gc();   
    }    
}  
   
