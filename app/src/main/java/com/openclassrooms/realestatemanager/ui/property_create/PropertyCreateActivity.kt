package com.openclassrooms.realestatemanager.ui.property_create

import android.Manifest
import android.arch.lifecycle.ViewModelProviders
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Environment.DIRECTORY_DCIM
import android.os.Parcelable
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.AdapterView
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
import com.openclassrooms.realestatemanager.ui.property_list.PropertyListViewModel
import com.openclassrooms.realestatemanager.utils.Utils
import kotlin.collections.ArrayList


private const val RC_IMAGE_PERMS = 100
private const val RC_CHOOSE_PHOTO = 200
class PropertyCreateActivity : AppCompatActivity() {

    private lateinit var mOutputFileUri: Uri
    private lateinit var mCurrentPhotoPath: String
    private lateinit var mAdapter: PropertyGridViewAdapter
    private var mPictureList = ArrayList<Picture>()
    private lateinit var mPropertyListViewModel: PropertyListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_create)
        this.configureSpinner()
        this.configureViewModel()
        this.configureGridView()

        add_photo_button.setOnClickListener {
            checkAccessImageFromPhone()
        }
    }

    private fun configureViewModel() {
        val mViewModelFactory = Injection().provideViewModelFactory(this)
        this.mPropertyListViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyListViewModel::class.java)
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
        this.mAdapter = PropertyGridViewAdapter(this)
        picture_gridview_create.adapter = this.mAdapter

        picture_gridview_create.onItemClickListener =
                AdapterView.OnItemClickListener { parent, v, position, id ->
                    Toast.makeText(this, "$position", Toast.LENGTH_SHORT).show()
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun accessToImages(){
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryIntent.type = "image/*"

        val root = File(Environment.getExternalStoragePublicDirectory(DIRECTORY_DCIM).toString() + File.separator + "Collage" + File.separator)
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

                val selectedImageUri: Uri?

                if (isCamera) {
                    selectedImageUri = mOutputFileUri
                    imageView_create.setImageURI(selectedImageUri)
                    galleryAddPic()
                    mPictureList.add(Picture("essai", mOutputFileUri.toString(), Utils.getTodayDate(), 1))
                    mAdapter.updateData(mPictureList)
                } else {
                    selectedImageUri = data?.data
                    imageView_create.setImageURI(selectedImageUri)
                    mPictureList.add(Picture("essai gallery", data?.data.toString(), Utils.getTodayDate(), 1))
                    mAdapter.updateData(mPictureList)
                }
            }
        }
    }

    private fun createPicture(picture: Picture) {
        this.mPropertyListViewModel.createPicture(picture)
    }

    private fun galleryAddPic() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(mCurrentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(f)
            sendBroadcast(mediaScanIntent)
        }
    }
}
