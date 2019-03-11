package com.openclassrooms.realestatemanager.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.openclassrooms.realestatemanager.model.Property
import android.content.ContentUris
import com.openclassrooms.realestatemanager.persistance.RealEstateManagerDatabase

class PropertyContentProvider: ContentProvider() {

    // FOR DATA
    companion object {
        const val AUTHORITY = "com.openclassrooms.realestatemanager.provider"
        val TABLE_NAME: String = Property::class.java.simpleName
        val URI_PROPERTY: Uri = Uri.parse("content://$AUTHORITY/$TABLE_NAME")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (context != null){
            val id: Long? = values?.let { Property.fromContentValues(it) }?.let { RealEstateManagerDatabase.getInstance(context)?.propertyDao()?.insertProperty(it) }
            if (id != null) {
                if (!id.equals(0)){
                    context.contentResolver.notifyChange(uri, null)
                    return ContentUris.withAppendedId(uri, id)
                }
            }
        }
        throw IllegalArgumentException("Failed to insert row into $uri")
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        if (context != null) {
            val propertyId = ContentUris.parseId(uri)
            val cursor: Cursor? = RealEstateManagerDatabase.getInstance(context)?.propertyDao()?.getPropertyWithCursor(propertyId)
            cursor?.setNotificationUri(context!!.contentResolver, uri)
            return cursor
        }
        throw IllegalArgumentException("Failed to query row for uri $uri")
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        if (context != null){
            val count = values?.let { Property.fromContentValues(it) }?.let { RealEstateManagerDatabase.getInstance(context)?.propertyDao()?.updateProperty(it) }
            context.contentResolver.notifyChange(uri, null)
            return count!!
        }
        throw IllegalArgumentException("Failed to update row into $uri")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(uri: Uri): String? {
        return "vnd.android.cursor.item/$AUTHORITY.$TABLE_NAME"
    }
}