/*
 * Copyright (C) 2019 Cricin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.cricin.colorpicker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

@SuppressWarnings("WeakerAccess")
public class ThumbDrawable extends Drawable {
  private static final int DEFAULT_SIZE = 24;//dp

  private int mSize;
  private Paint mPaint;
  private WeakReference<Context> mContextRef;
  private boolean mPressed;

  public ThumbDrawable(Context context) {
    this.mContextRef = new WeakReference<>(context);
    this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mSize = dp2px(context, DEFAULT_SIZE);
  }

  @Override
  public void draw(@NonNull Canvas canvas) {
    Rect bounds = getBounds();
    canvas.save();
    canvas.translate(bounds.left, bounds.top);

    canvas.translate(bounds.width() / 2, bounds.height() / 2);

    mPaint.setColor(0x55000000);
    mPaint.setStyle(Paint.Style.FILL);
    canvas.drawCircle(0, 0, bounds.width() / 2, mPaint);

    mPaint.setColor(Color.BLACK);

    if (mPressed) {
      mPaint.setStyle(Paint.Style.STROKE);
      mPaint.setStrokeWidth(5);
      mPaint.setColor(Color.BLACK);
      canvas.drawCircle(0, 0, bounds.width() / 2 - 5, mPaint);
    }
    mPaint.setStyle(Paint.Style.FILL);
    canvas.drawCircle(0, 0, dp2px(mContextRef.get(), 3), mPaint);

    canvas.restore();
  }

  public void setPressed(boolean pressed) {
    mPressed = pressed;
  }

  @Override
  public void setAlpha(int alpha) {}

  @Override
  public int getIntrinsicHeight() {
    return mSize;
  }

  @Override
  public int getIntrinsicWidth() {
    return mSize;
  }

  @Override
  public void setColorFilter(@Nullable ColorFilter colorFilter) {}

  @Override
  public int getOpacity() {
    return PixelFormat.TRANSLUCENT;
  }

  private static int dp2px(Context ctx, int dpVal) {
    if (ctx == null) return dpVal;
    return (int) (ctx.getResources().getDisplayMetrics().density * dpVal + 0.5);
  }
}
