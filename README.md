#### 换肤的大致步骤如下：

1、收集需要换肤的控件以及属性

>设置自定义的 LayoutInflater.Factory2，这里使用 `LayoutInflaterCompat` 来 `createView()` 保证兼容性。

```
// 如何创建 View？
使用 delegate.createView(parent, name, context, attrs)，委托给系统的实现，保证兼容性。

// 如何读取 ?attr/xxx 形式？
使用 `TypedValue` 读取即可。
```

2、制作皮肤包

>皮肤包只需要资源文件，各种代码依赖都需要删除掉，打包以后观察下app包的大小以及里面的dex文件内容即可。哪里多余删除哪里。
>直接运行 `build.gradle`，生成 apk 文件（注意，运行 `Run/Redebug configurations` 中 `Launch Options` 选择 `launch nothing`）
>否则 build 会报 `no default Activity` 的错误
> 也可以通过执行 `assembleRelease` 命令打包 apk
> 将 apk文件重命名，如将 black.apk，重命名为 black.skin 防止用户点击安装

3、读取皮肤包

>将外部的 apk 的路径添加到 `AssetManager`，然后创建 `Resources` 对象，
> 当我们换肤的时候，就是在这个 `Resources` 对象中寻找资源文件并替换。

4、刷新控件

>第一步中已经找到了需要换肤的所有控件，以及每一个控件的属性、属性id，
> 当换肤的时候，直接遍历控件列表，然后去皮肤包中寻找同名的资源设置给控件即可完成换肤。

```
fun applySkin(context: Context) {
    // 在Factory2 中找到的所有支持换肤的控件
    attrViews.forEach {
        applyAttrView(context, it)
    }
}

fun applyAttrView(context: Context, attrView: AttrView) {
    //将每一个换肤控件的属性进行应用
    attrView.attrs.forEach {
        if (attrView.view is TextView) {
            // 去皮肤包中寻找对应的资源
            if (it.attrName == "textColor") {
                attrView.view.setTextColor(SkinLoader.instance.getTextColor(context, it.resId))
            } else if (it.attrName == "text") {
                attrView.view.text = SkinLoader.instance.getText(context, it.resId)
            }
        }
    }
}
```

#### 获取插件工程的资源只需要三步：

1、通过主工程的资源 id 获取资源名，资源类型。

2、通过资源名、资源类型去插件包中寻找对应的资源 id。

3、通过插件资源 id，用插件 `Resources` 对象去获取插件资源。

```
fun getText(context: Context, redId: Int): String {
    // 获取插件中对应的资源 id
    val identifier = getIdentifier(context, redId)

    if (resource == null || identifier <= 0) {
        return context.getString(redId)
    }
    // 获取插件工程对应的资源
    return resource!!.getString(identifier)
}

private fun getIdentifier(context: Context, redId: Int): Int {
    // R.color.black
    // black
    val resourceEntryName = context.resources.getResourceEntryName(redId)
    
    // color
    val resourceTypeName = context.resources.getResourceTypeName(redId)
    return resource?.getIdentifier(resourceEntryName, resourceTypeName, skinPkgName) ?: 0
}
```

其他：支持手动设置属性，手动添加控件

#### 如何更加安全的换肤，如何对代码的侵入性做到最小？

比如通过在配置文件中配置需要换肤的 View 的 id name 而不是通过在 xml 文件中进行标记等等，
都是可以继续研究的方向，以后有时间会继续在这方面进行探索。

Ref:

https://juejin.cn/post/6844903780060758029

https://mp.weixin.qq.com/s/lZpza9RXMUHKhz3D9veB7w