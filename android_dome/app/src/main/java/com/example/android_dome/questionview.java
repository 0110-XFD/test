package com.example.android_dome;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class questionview extends ViewPager {
    public questionview(Context context){
        super(context);
    }
    public questionview(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        setfy();
    }

    public void setfy(){
        setPageTransformer(true, new PageTransformer() {

            private static final float MIN_SCALE = 0.75f;
            @Override
            public void transformPage(@NonNull View view, float v) {

                int pagewidth = view.getWidth();
                int pageheight = view.getHeight();

                if(v < -1){
                    view.setAlpha(0);
                }else if(v <=0){
                    view.setAlpha(1);
                    view.setTranslationX(0);
                    view.setScaleX(1);
                    view.setScaleY(1);
                }else if (v <= 1){
                    view.setAlpha(1);
                    view.setTranslationX( pagewidth * -v);
                }else{
                    view.setAlpha(0);
                }

            }
        });
    }
}
