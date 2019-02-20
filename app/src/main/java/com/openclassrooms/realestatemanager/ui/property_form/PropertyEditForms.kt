package com.openclassrooms.realestatemanager.ui.property_form

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Parcelable
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Address
import com.openclassrooms.realestatemanager.model.InterestPoint
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.ui.property_form.recyclerView.PropertyGridRecyclerViewAdapter
import com.openclassrooms.realestatemanager.utils.ItemClickSupport
import com.openclassrooms.realestatemanager.utils.Utils
import kotlinx.android.synthetic.main.activity_property_create.*
import kotlinx.android.synthetic.main.dialog_add_picture.view.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

private const val RC_IMAGE_PERMS = 100
private const val RC_CHOOSE_PHOTO = 200

abstract class PropertyEditForms: AppCompatActivity() {

    private lateinit var mView: View
    protected var mPictureList = ArrayList<Picture>()
    protected var mAdapterRecycler: PropertyGridRecyclerViewAdapter = PropertyGridRecyclerViewAdapter()
    private lateinit var mOutputFileUri: Uri
    private lateinit var mCurrentPhotoPath: String
    private var mPictureUri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_create)

    }

    protected fun onCreateDialog() {
        val builder = AlertDialog.Builder(this)
        mView = layoutInflater.inflate(R.layout.dialog_add_picture, null)
        builder.setView(mView)
        builder.setPositiveButton(getString(R.string.ok)) { _, _ ->
            val description = mView.description_picture_dialog.text.toString()
            mPictureList.add(Picture(0, description, mPictureUri.toString(), Utils.getTodayDate()))
            mAdapterRecycler.updateData(mPictureList)
        }
        builder.setNegativeButton(getString(R.string.cancel)) { _, _ ->
        }
        builder.create()
        builder.show()
        mView.image_button_dialog.setOnClickListener {
            checkAccessImageFromPhone()
        }
    }

    @AfterPermissionGranted(RC_IMAGE_PERMS)
    private fun checkAccessImageFromPhone() {
        val perms = Manifest.permission.WRITE_EXTERNAL_STORAGE
        if (EasyPermissions.hasPermissions(this, perms)) {
            accessToImages()
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.popup_title_permission_files_access), RC_IMAGE_PERMS, perms)
            return
        }
    }

    protected fun configureGridRecyclerView(){
        val recyclerView = RecyclerView(this)
        val layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        recyclerView.layoutParams = layoutParams
        val gridLayoutManager = GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false)
        picture_gridview_create.adapter = mAdapterRecycler
        picture_gridview_create.layoutManager = gridLayoutManager
    }

    protected fun configureClickGridRecyclerView(){
        ItemClickSupport.addTo(picture_gridview_create, R.layout.item_grid_picture_property)
                .setOnItemClickListener { _, position, _ ->
                    val builder = AlertDialog.Builder(this@PropertyEditForms)
                    builder.setMessage(getString(R.string.are_you_sure_you_want_to_delete_the_image))
                            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                                mPictureList.removeAt(position)
                                mAdapterRecycler.updateData(mPictureList)
                            }
                            .setNegativeButton(R.string.cancel) { _, _ ->
                            }
                    builder.create()
                    builder.show()
                }
    }

    protected fun retrieveInformationEntered(): Property {
        val surface = if (surface_edit_text.text.toString().isNotEmpty()) Integer.parseInt(surface_edit_text.text.toString()) else null
        val price = if (price_edit_text.text.toString().isNotEmpty()) Integer.parseInt(price_edit_text.text.toString()) else null
        val rooms = number_of_room_edit_text.text.toString()
        val description = description_property_edit_text.text.toString()
        val location = retrieveAddress()
        val typeProperty = type_of_property_spinner.selectedItem.toString()
        val estateAgent = estate_agent_spinner.selectedItem.toString()
        val interestPoint = retrieveInterestPoint()
        val numberOfPhotos = mPictureList.size
        return Property(typeProperty, price, surface, rooms, description, Date(), interestPoint, estateAgent, location, numberOfPhotos)
    }

    private fun retrieveInterestPoint(): InterestPoint {
        return InterestPoint(
                radioButton_doctor.isChecked,
                radioButton_hobbies.isChecked,
                radioButton_public_transport.isChecked,
                radioButton_school.isChecked,
                radioButton_stores.isChecked,
                radioButton_parc.isChecked)
    }

    protected fun retrieveAddress(): Address {
        val number = number_edit_text.text.toString()
        val street = street_edit_text.text.toString()
        val postCode = postal_code_edit_text.text.toString()
        val city = city_edit_text.text.toString()
        return Address(number, street, postCode, city)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    protected fun accessToImages(){
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryIntent.type = "image/*"

        val root = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + File.separator + "Real Estate Manager" + File.separator)
        root.mkdirs()
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.FRANCE).format(Date())
        val fileName = "$timeStamp.jpg"
        val imageMainDirectory = File(root, fileName)
        mOutputFileUri = Uri.fromFile(imageMainDirectory)
        mCurrentPhotoPath = imageMainDirectory.absolutePath
        val cameraIntents = ArrayList<Intent>()
        val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val packageManager = packageManager
        val listCam = packageManager.queryIntentActivities(captureIntent, 0)
        for (res in listCam) {
            val packageName = res.activityInfo.packageName
            val intent = Intent(captureIntent)
            intent.component = ComponentName(packageName, res.activityInfo.name)
            intent.setPackage(packageName)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mOutputFileUri)
            cameraIntents.add(intent)
        }

        val chooser = Intent(Intent.ACTION_CHOOSER)
        chooser.putExtra(Intent.EXTRA_INTENT, galleryIntent)
        chooser.putExtra(Intent.EXTRA_TITLE, getString(R.string.select_from))

        val chooserIntent = Intent.createChooser(galleryIntent, getString(R.string.select_from))
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(arrayOfNulls<Parcelable>(cameraIntents.size)))
        startActivityForResult(chooserIntent, RC_CHOOSE_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RC_CHOOSE_PHOTO) {
                val isCamera: Boolean
                if (data?.data == null) {
                    isCamera = true
                } else {
                    val action = data.action
                    if (action == null) {
                        isCamera = false
                    } else {
                        isCamera = action == android.provider.MediaStore.ACTION_IMAGE_CAPTURE
                    }
                }

                if (isCamera) {
                    mView.image_button_dialog.setImageURI(mOutputFileUri)
                    galleryAddPic()
                    mPictureUri = Utils.getRealPathFromURI(this, mOutputFileUri)
                } else {
                    mView.image_button_dialog.setImageURI(data?.data)
                    mPictureUri = Utils.getRealPathFromURI(this, data?.data)
                }
                mView.image_button_dialog.setBackgroundResource(R.color.colorWhite)
            }
        }
    }

    private fun galleryAddPic() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(mCurrentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(f)
            sendBroadcast(mediaScanIntent)
        }
    }
}