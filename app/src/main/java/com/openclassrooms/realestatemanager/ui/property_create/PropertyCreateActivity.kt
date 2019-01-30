package com.openclassrooms.realestatemanager.ui.property_create

import android.Manifest
import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.content.ComponentName
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_DCIM
import android.os.Parcelable
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.openclassrooms.realestatemanager.R
import kotlinx.android.synthetic.main.activity_property_create.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import android.widget.ArrayAdapter
import android.widget.Toast
import com.openclassrooms.realestatemanager.di.Injection
import com.openclassrooms.realestatemanager.model.Picture
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.utils.Utils
import kotlinx.android.synthetic.main.dialog_add_picture.view.*
import kotlin.collections.ArrayList


private const val RC_IMAGE_PERMS = 100
private const val RC_CHOOSE_PHOTO = 200
class PropertyCreateActivity : AppCompatActivity() {

    private lateinit var mOutputFileUri: Uri
    private lateinit var mCurrentPhotoPath: String
    private var mAdapterRecycler: PropertyGridRecyclerViewAdapter = PropertyGridRecyclerViewAdapter()
    private var mPictureList = ArrayList<Picture>()
    private lateinit var mPropertyCreateViewModel: PropertyCreateViewModel
    private lateinit var mView: View
    private var mPictureUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_create)
        this.configureSpinner()
        this.configureViewModel()
        this.configureGridView()

        add_photo_button.setOnClickListener {
            onCreateDialog()
        }
    }

    private fun onCreateDialog() {
        val builder = AlertDialog.Builder(this)
        mView = layoutInflater.inflate(R.layout.dialog_add_picture, null)
        builder.setView(mView)
        builder.setPositiveButton(getString(R.string.ok)) { dialog, which ->
            val description = mView.description_picture_dialog.text.toString()
            mPictureList.add(Picture(description, mPictureUri.toString(), Utils.getTodayDate(), 0))
            mAdapterRecycler.updateData(mPictureList)
        }
        builder.setNegativeButton(getString(R.string.cancel)) { dialog, which ->
        }
        builder.create()
        builder.show()
        mView.image_button_dialog.setOnClickListener {
            checkAccessImageFromPhone()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_activity_property_create, menu)
        return true
    }

    private fun configureViewModel() {
        val mViewModelFactory = Injection().provideViewModelFactory(this)
        this.mPropertyCreateViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyCreateViewModel::class.java)
    }

    private fun configureSpinner(){
        val typePropertyAdapter = ArrayAdapter.createFromResource(this, R.array.type_property_array, android.R.layout.simple_spinner_item)
        typePropertyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        type_of_property_spinner.adapter = typePropertyAdapter
        val estateAgentAdapter = ArrayAdapter.createFromResource(this, R.array.estate_agent_array, android.R.layout.simple_spinner_item)
        estateAgentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        estate_agent_spinner.adapter = estateAgentAdapter
    }

    private fun configureGridView(){
        val recyclerView = RecyclerView(this)
        val layoutParams = RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        recyclerView.layoutParams = layoutParams
        val gridLayoutManager = GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false)
        picture_gridview_create.adapter = mAdapterRecycler
        picture_gridview_create.layoutManager = gridLayoutManager
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.create -> {
            retrieveInformationEntered()
            true
        }else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun retrieveInformationEntered() {
        val surface = surface_edit_text.text.toString()
        val rooms = number_of_room_edit_text.text.toString()
        val price = price_edit_text.text.toString()
        val description = description_property_edit_text.text.toString()
        val location = location_edit_text.text.toString()
        val typeProperty = type_of_property_spinner.selectedItem.toString()
        val estateAgent = estate_agent_spinner.selectedItem.toString()
        val property = Property(typeProperty, price, surface, rooms, description, location, Utils.getTodayDate(), estateAgent)
        this.mPropertyCreateViewModel.createProperty(property, mPictureList)
        finish()
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun accessToImages(){
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryIntent.type = "image/*"

        val root = File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM).toString() + File.separator + "Real Estate Manager" + File.separator)
        root.mkdirs()
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fileName = "$timeStamp.jpg"
        val imageMainDirectory = File(root, fileName)
        mOutputFileUri = Uri.fromFile(imageMainDirectory)
        mCurrentPhotoPath = imageMainDirectory.absolutePath
        val cameraIntents = ArrayList<Intent>()
        val captureIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
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

    @SuppressLint("PrivateResource")
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
                    mPictureUri = mOutputFileUri
                    galleryAddPic()
                } else {
                    mView.image_button_dialog.setImageURI(data?.data)
                    mPictureUri = data?.data
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
