package com.wwe.helper

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat

object SkinLoader {
    private lateinit var skinPkgName: String
    private var resource: Resources? = null

    fun loadSkinResource(context: Context, skinPath: String) {
        try {
            (context.packageManager.getPackageArchiveInfo(
                skinPath,
                PackageManager.GET_ACTIVITIES
            ))?.let { packageInfo ->
                skinPkgName = packageInfo.packageName
                val assetManager = AssetManager::class.java.newInstance()
                val method = AssetManager::class.java.getMethod("addAssetPath", String::class.java)
                method.invoke(assetManager, skinPath)
                resource = Resources(
                    assetManager,
                    context.resources.displayMetrics,
                    context.resources.configuration
                )
            } ?: run {
                Log.w("WWE", "SkinLoader -> loadResource: app load fail")
            }
        } catch (e: Exception) {
            Log.e("WWE", "SkinLoader -> loadResource catch ", e)
        }
    }

    fun getText(context: Context, redId: Int): String {
        val skinIdentifierId = getIdentifier(context, redId)
        if (resource == null || skinIdentifierId <= 0) {
            return context.getString(redId)
        }
        return resource?.getString(skinIdentifierId) ?: "Skin text not found from L41"
    }

    fun getText(context: Context, attrName: String, attrType: String): String {
        val skinResourceId = resource?.getIdentifier(attrName, attrType, skinPkgName) ?: 0
        if (skinResourceId <= 0) {
            return context.getString(
                context.resources.getIdentifier(
                    attrName,
                    attrType,
                    context.packageName
                )
            )
        }
        return resource?.getString(skinResourceId) ?: "Skin text not found from L49"
    }

    fun getTextColor(context: Context, redId: Int): Int {
        val identifierId = getIdentifier(context, redId)
        if (resource == null || identifierId <= 0) {
            return ContextCompat.getColor(context, redId)
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            resource?.getColor(identifierId, null) ?: -1
        } else {
            return ContextCompat.getColor(context, redId)
        }
    }

    fun getTextColor(context: Context, attrName: String, attrType: String): Int {
        val skinResourceId = resource?.getIdentifier(attrName, attrType, skinPkgName) ?: 0
        if (skinResourceId <= 0) {
            return ContextCompat.getColor(
                context,
                context.resources.getIdentifier(attrName, attrType, context.packageName)
            )
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            resource?.getColor(skinResourceId, null) ?: -1
        } else {
            return ContextCompat.getColor(
                context,
                context.resources.getIdentifier(attrName, attrType, context.packageName)
            )
        }
    }

    private fun getIdentifier(context: Context, redId: Int): Int {
        // R.color.black
        // black
        val resourceEntryName = context.resources.getResourceEntryName(redId)
        Log.i("WWE", "SkinLoader -> (redId: $redId, resourceEntryName: $resourceEntryName)")
        // color
        val resourceTypeName = context.resources.getResourceTypeName(redId)
        return resource?.getIdentifier(resourceEntryName, resourceTypeName, skinPkgName) ?: -1
    }
}