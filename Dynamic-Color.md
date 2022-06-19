#### 动态颜色的原理是什么？

- 从用户的壁纸上提取一种源颜色，并推算出5种关键颜色，比如 primary、Secondary、Tertiary 等

- 然后，将每个关键颜色转化为由13种色调组成的调色板，且每种色调会生成对应的浅、深色方案

- 最后，通过系统 Token 将从壁纸提取的颜色方案和 App Theme 关联起来，在 `DynamicColorsActivityLifecycleCallbacks` 中判断是否需要覆盖

**使用动态颜色**

我们过去在使用颜色时可能是硬编码，比如这样：

```
android:background="@color/colorPrimary"
```

现在颜色的色值不确定了，应改为动态访问的方式：

```
android:background="?attr/colorPrimary"
```

至此已经可以在Android 12 上试试动态颜色的效果了。

