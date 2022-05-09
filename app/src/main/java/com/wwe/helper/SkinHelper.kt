package com.wwe.helper

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources

class SkinHelper {

    companion object {

        fun getSkinResource(context: Context, skinPath: String): Resources {
            val skinPackageName = (context.packageManager as PackageManager).getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES)?.packageName

            val assetManager = AssetManager::class.java.newInstance()
            val addAssetPath = assetManager.javaClass.getMethod("addAssetPath", String::class.java)
            addAssetPath.invoke(assetManager, skinPath)

            val appRes = context.resources
            return Resources(assetManager, appRes.displayMetrics, appRes.configuration)
        }

        fun getSkinColor(skinResource: Resources, colorName: String, skinPackageName: String): Int {
            return skinResource.getIdentifier(colorName, "color", skinPackageName)
        }
    }
}