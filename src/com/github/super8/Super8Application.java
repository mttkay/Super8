package com.github.super8;

import android.app.Application;

import com.github.ignition.core.widgets.RemoteImageView;
import com.github.ignition.support.cache.ImageCache;
import com.github.ignition.support.images.remote.RemoteImageLoader;

public class Super8Application extends Application {

  private RemoteImageLoader imageLoader;

  @Override
  public void onCreate() {
    super.onCreate();

    imageLoader = new RemoteImageLoader(this);
    imageLoader.getImageCache().enableDiskCache(this, ImageCache.DISK_CACHE_SDCARD);
    RemoteImageView.setSharedImageLoader(imageLoader);
  }

  public RemoteImageLoader getImageLoader() {
    return imageLoader;
  }

}
