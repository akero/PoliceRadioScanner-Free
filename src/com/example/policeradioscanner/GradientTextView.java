package com.example.policeradioscanner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.widget.TextView;

import com.akero.fuzzradiopro.R;


public class GradientTextView extends TextView {
 
 private int colorStartGradient, colorEndGradient;
  
 public GradientTextView(Context context, AttributeSet attrs) {
  super(context, attrs);
 
  TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.GradientTextView);
  colorStartGradient = a.getColor(R.styleable.GradientTextView_colorStartGradient, -2);
  colorEndGradient = a.getColor(R.styleable.GradientTextView_colorEndGradient, -2);
 }
 
 @Override
 protected void onDraw(Canvas canvas) {
  if (colorStartGradient != -2 && colorEndGradient != -2)
  { 
   getPaint().setShader(
     new LinearGradient(0, 0, 0, getHeight(), colorStartGradient,
       colorEndGradient, TileMode.MIRROR)); 
  }
  super.onDraw(canvas);
 }
 
}