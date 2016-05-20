package com.Meetok.adapter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;



import com.Meetok.Entity.ShouyeEntity;

import com.imooc.tab03.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adapter_home1 extends BaseAdapter {
	private Context context;
	private List<ShouyeEntity> mlist;
	
	
	public Adapter_home1(Context context,List<ShouyeEntity> list){
		
		this.context=context;
		this.mlist=list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View currentView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		 final HolderView holderView;
			if (currentView==null) {
				holderView=new HolderView();
				currentView=LayoutInflater.from(context).inflate(R.layout.adapter_shouye_more, null);
				holderView.iv_pic=(ImageView) currentView.findViewById(R.id.iv_adapter_grid_pic);
				holderView.iv_name = (TextView) currentView.findViewById(R.id.iv_adapter_grid_txt);
				holderView.iv_pic.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
				currentView.setTag(holderView);
			}else {
				holderView=(HolderView) currentView.getTag();
			}
			ShouyeEntity se=mlist.get(position);
			String str = se.name;
			String name = str.subSequence(1, str.length()).toString();
			//
			holderView.iv_name.setText(name);
			//显示图片的配置  
	        DisplayImageOptions options = new DisplayImageOptions.Builder()  
	                //.showImageOnLoading(R.drawable.app_logo)  
	                //.showImageOnFail(R.drawable.icon_error)  
	                .cacheInMemory(true)  
	                .cacheOnDisk(true)  
	                .bitmapConfig(Bitmap.Config.RGB_565)  
	                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//
	                .displayer(new FadeInBitmapDisplayer(100))
	                .build();  
	        ImageLoader.getInstance().displayImage(se.typepic, holderView.iv_pic, options); 
			holderView.iv_name.setText(name);
		return currentView;
	}
	
	public void displayImage(String imageURL, ImageView imageView) {
		ImageLoader.getInstance().displayImage(imageURL, imageView);
	}
	public class HolderView {
		
		private ImageView iv_pic;
		private TextView  iv_name;
		
	}
	/**
	 * 图片按比例大小压缩方法
	 * @param srcPath
	 * @return
	 */
	private Bitmap getimage(String srcPath) {  
        BitmapFactory.Options newOpts = new BitmapFactory.Options();  
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
        newOpts.inJustDecodeBounds = true;  
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空  
          
        newOpts.inJustDecodeBounds = false;  
        int w = newOpts.outWidth;  
        int h = newOpts.outHeight;  
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
        float hh = 800f;//这里设置高度为800f  
        float ww = 480f;//这里设置宽度为480f  
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
        int be = 1;//be=1表示不缩放  
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
            be = (int) (newOpts.outWidth / ww);  
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
            be = (int) (newOpts.outHeight / hh);  
        }  
        if (be <= 0)  
            be = 1;  
        newOpts.inSampleSize = be;//设置缩放比例  
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩  
    }  
	/**
	 * 质量压缩方法
	 * @param image
	 * @return
	 */
	private Bitmap compressImage(Bitmap image) {  
		  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
        int options = 100;  
        while ( baos.toByteArray().length / 1024>200) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
            baos.reset();//重置baos即清空baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
            options -= 10;//每次都减少10  
        }  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
        return bitmap;  
    }  
}
