#### Android 动态换肤原理解析及实践

指定资源路径地址，在打包时将对应资源打包进去

```
sourceSets {
// 测试版本和线上版本用同一套资源
YymTest {
    res.srcDirs = ["src/Yym/res", "src/YymTest/res"]
    assets.srcDirs = ["src/Yym/assets"]
 }
}
```

**动态换肤的一般步骤为：**

1，下载并加载皮肤包

2，拿到皮肤包的 Resource 对象

3，标记需要换肤的 View

- 通过 xml 标记的 View

>这种方式主要要通过实现 LayoutInflate.Factory2 这个接口(为支持 AppCompatActivity 用 LayoutInflaterFactory API 是一样的)。
>`LayoutInflater` 提供了 `setFactory(LayoutInflater.Factory factory)` 和 `setFactory2(LayoutInflater.Factory2 factory)`
>两个方法可以让你去自定义布局的填充，`Factory2` 是在 API 11 才添加的
通过实现这两个接口可以实现 `View` 的重写。`Activity` 本身就默认实现了 `Factory` 接口，所以我们复写了 `Factory` 的 `onCreateView` 之后，
>就可以不通过系统层而是自己截获从 xml 映射的 View 进行相关 View 创建的操作，包括对 View 的属性进行设置（比如背景色，字体大小，颜色等）以实现换肤的效果。
>如果 `onCreateView` 返回 null 的话，会将创建 View 的操作交给 Activity 默认实现的 Factory 的 onCreateView 处理。

4，切换时即时刷新页面

5，制作皮肤包

>1). 新建工程project
2). 将换肤的资源文件添加到res文件下，无java文件
3). 直接运行build.gradle，生成apk文件（注意，运行时Run/Redebug configurations 中Launch Options选择launch nothing），否则build 会报 no default Activity 的错误。
4). 将apk文件重命名如black.apk，重命名为black.skin防止用户点击安装

6，换肤整体框架的搭建

**在线换肤：**

1，将皮肤包上传到服务器后台

2，客户端根据接口数据下载皮肤包，进行加载及客户端换肤操作

---

**如何更加安全的换肤，如何对代码的侵入性做到最小？**

比如通过在配置文件中配置需要换肤的 View 的 id name 而不是通过在 xml 文件中进行标记等等，都是可以继续研究的方向，以后有时间会继续在这方面进行探索。

同时对换肤感兴趣的童鞋可以参考以下链接：

1、Android-Skin-Loader

2、Android-skin-support

3、Android主题换肤 无缝切换

```
public abstract class BaseActivity extends
    code.solution.base.BaseActivity implements ISkinUpdate, IDynamicNewView {

    private SkinInflaterFactory mSkinInflaterFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSkinInflaterFactory = new SkinInflaterFactory();
        LayoutInflaterCompat.setFactory(getLayoutInflater(), mSkinInflaterFactory); // 这是重点
        super.onCreate(savedInstanceState);
        changeStatusColor();
    }
}
```