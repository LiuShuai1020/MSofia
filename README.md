# MSofia
##### 沉浸式状态栏及导航栏适配
##### 在开发项目时，需要沉浸式效果进行开发的时候，一直使用Sofia的第三方库，但是在近期发现Sofia没有在适配虚拟导航栏，在部分机型上面，虚拟导航栏与TabHost中间会有黑色或者白色的空白区域(图1)，为了解决问题，从GitHub下载源代码，进行了修复，目前只在华为荣耀play（9.0）和小米MI5（8.0）进行了测试，如果有问题请及时联系我，期待Sofia作者继续维护该项目，如果解决了虚拟按键适配的问题，MSofia依赖库便不再维护，支持Sofia作者！

    dependencies {
	    implementation 'com.github.LiuShuai1020:MSofia:1.0.0.2'
	}

##### 如果你之前使用Sofia，用法与Sofia完全一样，只不过需要修改下包名
        implementation 'com.yanzhenjie:sofia:1.0.5'
 ##### 修改成:
        implementation 'com.github.LiuShuai1020:MSofia:1.0.0.2'

##### 布局文件中：
         <com.yanzhenjie.sofia.StatusView  ... />
##### 修改成:
          <com.liushiyu.sofia.msofia.StatusView ... />

##### 其他正常调用，没有任何区别

    // 状态栏深色字体。
    Bar statusBarDarkFont();
    
    // 状态栏浅色字体。
    Bar statusBarLightFont();
    
    // 状态栏背景色。
    Bar statusBarBackground(int statusBarColor);
    
    // 状态栏背景 Drawable。
    Bar statusBarBackground(Drawable drawable);
    
    // 状态栏背景透明度。
    Bar statusBarBackgroundAlpha(int alpha);
    
    // 导航栏背景色。
    Bar navigationBarBackground(int navigationBarColor);
    
    // 导航栏背景 Drawable。
    Bar navigationBarBackground(Drawable drawable);
    
    // 导航栏背景透明度。
    Bar navigationBarBackgroundAlpha(int alpha);
    
    // 内容入侵状态栏。
    Bar invasionStatusBar();
    
    // 内容入侵导航栏。
    Bar invasionNavigationBar();
    
    // 让某一个 View 考虑状态栏的高度，显示在适当的位置，接受 ViewId。
    Bar fitsStatusBarView(int viewId);
    
    // 让某一个 View 考虑状态栏的高度，显示在适当的位置，接受 View。
    Bar fitsStatusBarView(View view);
    
    // 让某一个 View 考虑导航栏的高度，显示在适当的位置，接受 ViewId。
    Bar fitsNavigationBarView(View view);
    
    // 让某一个 View 考虑导航栏的高度，显示在适当的位置，接受 View。
    Bar fitsNavigationBarView(View view);

#### 相关图片:

![图1.png](https://upload-images.jianshu.io/upload_images/13761067-1128d7bbd1cdecf6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![屏幕内单键导航.png](https://upload-images.jianshu.io/upload_images/13761067-820434c2f5ff6313.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![屏幕内三键导航.png](https://upload-images.jianshu.io/upload_images/13761067-5f9b915a59e3ce1f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![隐藏虚拟导航栏.png](https://upload-images.jianshu.io/upload_images/13761067-afecfb8a8943fccf.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

