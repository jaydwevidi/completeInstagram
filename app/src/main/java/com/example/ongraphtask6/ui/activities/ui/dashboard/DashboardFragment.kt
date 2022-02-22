package com.example.ongraphtask6.ui.activities.ui.dashboard

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.aghajari.zoomhelper.ZoomHelper
import com.bumptech.glide.Glide
import com.example.ongraphtask6.databinding.FragmentDashboardBinding
import com.example.ongraphtask6.retrofit.models.ImageFileObject
import com.example.ongraphtask6.rv.GridRVAdapter
import com.example.ongraphtask6.ui.activities.PostPictureActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File
import java.lang.Exception

class DashboardFragment : Fragment() {

private var _binding: FragmentDashboardBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

    private lateinit var path : File
    val imageList = mutableListOf<ImageFileObject>()
    private lateinit var selectedPicture : String

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

    _binding = FragmentDashboardBinding.inflate(inflater, container, false)
    val root: View = binding.root

    dashboardViewModel.text.observe(viewLifecycleOwner) {

    }


    path = File(System.getenv("EXTERNAL_STORAGE")!!)
    askPermissionForStorage()
    postPictureButton()

    ZoomHelper.addZoomableView(_binding!!.selectedImage)
    zoomFunctionality()



    return root
  }

  private fun zoomFunctionality(){
    ZoomHelper.addZoomableView(_binding!!.selectedImage)
  }

  fun setupRV(){
    val rv =_binding?.gridImagesRV
    val mAdapter = GridRVAdapter(imageList , requireContext())


    mAdapter.msetupOnClickListner(object : GridRVAdapter.MyOnItemClickListner{
      override fun onItemClick(position: Int) {


        Glide.with(activity!!.baseContext)
          .load(imageList[position].file)
          .fitCenter()
          .into(_binding!!.selectedImage)

        selectedPicture = imageList[position].path
      }

    })


    rv?.apply {
      adapter = mAdapter
      layoutManager = GridLayoutManager(context, 3)

    }
    try {
      Glide.with(requireContext())
        .load(imageList[0].file)
        .fitCenter()
        .into(_binding!!.selectedImage)

      selectedPicture = imageList[0].path

    }catch (e : Exception){
      Toast.makeText(context, "no Images found", Toast.LENGTH_SHORT).show()
    }


  }

  fun postPictureButton(){
    _binding!!.postPictureButton.setOnClickListener {
      val intent = Intent(context , PostPictureActivity::class.java)
      intent.putExtra("1" , selectedPicture )
      intent.putExtra("uid" , requireActivity().intent.getIntExtra("uid" , 0))
      startActivity(intent)
    }
  }

  private fun askPermissionForStorage() {

    val permissionListner = object : PermissionListener {

      override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
        //Toast.makeText(this@MainActivity, "Permission Given", Toast.LENGTH_SHORT).show()
        displayFiles(path)
        Log.d("videoList", "$imageList ")
        setupRV()
      }

      override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
        Toast.makeText(context, "permission dedo please", Toast.LENGTH_SHORT).show()
      }

      override fun onPermissionRationaleShouldBeShown(
        p0: com.karumi.dexter.listener.PermissionRequest?,
        p1: PermissionToken?
      ) {
        p1?.continuePermissionRequest()
      }
    }

    Dexter.withContext(activity?.applicationContext)
      .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
      .withListener(permissionListner)
      .check()
  }

  private fun displayFiles(file : File){

    val allFiles  = file.listFiles() ?: return
    for(i in allFiles){
      if(i.isDirectory || !i.isHidden){
        //Log.d("directory found", "= ${i.name}")
        displayFiles(i)

        if(i.name.endsWith(".jpg") || i.name.endsWith(".jpeg") || i.name.endsWith(".png")) {

          val videoObject = ImageFileObject(i.name , i.path.toString() ,  i )
          imageList.add(videoObject)
          Log.d("myPath", "=  ${i.path} ")
          //Log.d("videofound = ", " ${i.name}")
        }
      }

    }
  }

  private fun getList() : MutableList<String>{
    val list = mutableListOf<String>()
    list.add("https://static.wikia.nocookie.net/disney/images/1/13/Gal_Gadot.jpg/revision/latest?cb=20180811005357")
    list.add("https://m.media-amazon.com/images/M/MV5BZWVhYzE0NzgtM2U1Yi00OWM1LWJlZTUtZmNkNWZhM2VkMDczXkEyXkFqcGdeQW1yb3NzZXI@._V1_.jpg")
    list.add("https://i.guim.co.uk/img/media/1af905f750e1dc85eb490a3ec20bf76fb3ac51f7/0_486_2518_1509/master/2518.jpg?width=465&quality=45&auto=format&fit=max&dpr=2&s=1257fe1010592f3e91cf17a80471eefa")
    list.add("https://observer.com/wp-content/uploads/sites/2/2021/05/1_ynJEWSa6ivgFpF0EUP1L_A.jpeg?quality=80&strip")
    list.add("https://static.wikia.nocookie.net/disney/images/1/13/Gal_Gadot.jpg/revision/latest?cb=20180811005357")
    list.add("https://m.media-amazon.com/images/M/MV5BZWVhYzE0NzgtM2U1Yi00OWM1LWJlZTUtZmNkNWZhM2VkMDczXkEyXkFqcGdeQW1yb3NzZXI@._V1_.jpg")
    list.add("https://i.guim.co.uk/img/media/1af905f750e1dc85eb490a3ec20bf76fb3ac51f7/0_486_2518_1509/master/2518.jpg?width=465&quality=45&auto=format&fit=max&dpr=2&s=1257fe1010592f3e91cf17a80471eefa")
    list.add("https://observer.com/wp-content/uploads/sites/2/2021/05/1_ynJEWSa6ivgFpF0EUP1L_A.jpeg?quality=80&strip")
    list.add("https://static.wikia.nocookie.net/disney/images/1/13/Gal_Gadot.jpg/revision/latest?cb=20180811005357")
    list.add("https://m.media-amazon.com/images/M/MV5BZWVhYzE0NzgtM2U1Yi00OWM1LWJlZTUtZmNkNWZhM2VkMDczXkEyXkFqcGdeQW1yb3NzZXI@._V1_.jpg")
    list.add("https://i.guim.co.uk/img/media/1af905f750e1dc85eb490a3ec20bf76fb3ac51f7/0_486_2518_1509/master/2518.jpg?width=465&quality=45&auto=format&fit=max&dpr=2&s=1257fe1010592f3e91cf17a80471eefa")
    list.add("https://observer.com/wp-content/uploads/sites/2/2021/05/1_ynJEWSa6ivgFpF0EUP1L_A.jpeg?quality=80&strip")
    list.add("https://static.wikia.nocookie.net/disney/images/1/13/Gal_Gadot.jpg/revision/latest?cb=20180811005357")
    list.add("https://m.media-amazon.com/images/M/MV5BZWVhYzE0NzgtM2U1Yi00OWM1LWJlZTUtZmNkNWZhM2VkMDczXkEyXkFqcGdeQW1yb3NzZXI@._V1_.jpg")
    list.add("https://i.guim.co.uk/img/media/1af905f750e1dc85eb490a3ec20bf76fb3ac51f7/0_486_2518_1509/master/2518.jpg?width=465&quality=45&auto=format&fit=max&dpr=2&s=1257fe1010592f3e91cf17a80471eefa")
    list.add("https://observer.com/wp-content/uploads/sites/2/2021/05/1_ynJEWSa6ivgFpF0EUP1L_A.jpeg?quality=80&strip")
    list.add("https://static.wikia.nocookie.net/disney/images/1/13/Gal_Gadot.jpg/revision/latest?cb=20180811005357")
    list.add("https://m.media-amazon.com/images/M/MV5BZWVhYzE0NzgtM2U1Yi00OWM1LWJlZTUtZmNkNWZhM2VkMDczXkEyXkFqcGdeQW1yb3NzZXI@._V1_.jpg")
    list.add("https://i.guim.co.uk/img/media/1af905f750e1dc85eb490a3ec20bf76fb3ac51f7/0_486_2518_1509/master/2518.jpg?width=465&quality=45&auto=format&fit=max&dpr=2&s=1257fe1010592f3e91cf17a80471eefa")
    list.add("https://observer.com/wp-content/uploads/sites/2/2021/05/1_ynJEWSa6ivgFpF0EUP1L_A.jpeg?quality=80&strip")
    list.add("https://static.wikia.nocookie.net/disney/images/1/13/Gal_Gadot.jpg/revision/latest?cb=20180811005357")
    list.add("https://m.media-amazon.com/images/M/MV5BZWVhYzE0NzgtM2U1Yi00OWM1LWJlZTUtZmNkNWZhM2VkMDczXkEyXkFqcGdeQW1yb3NzZXI@._V1_.jpg")
    list.add("https://i.guim.co.uk/img/media/1af905f750e1dc85eb490a3ec20bf76fb3ac51f7/0_486_2518_1509/master/2518.jpg?width=465&quality=45&auto=format&fit=max&dpr=2&s=1257fe1010592f3e91cf17a80471eefa")
    list.add("https://observer.com/wp-content/uploads/sites/2/2021/05/1_ynJEWSa6ivgFpF0EUP1L_A.jpeg?quality=80&strip")
    list.add("https://static.wikia.nocookie.net/disney/images/1/13/Gal_Gadot.jpg/revision/latest?cb=20180811005357")
    list.add("https://m.media-amazon.com/images/M/MV5BZWVhYzE0NzgtM2U1Yi00OWM1LWJlZTUtZmNkNWZhM2VkMDczXkEyXkFqcGdeQW1yb3NzZXI@._V1_.jpg")
    list.add("https://i.guim.co.uk/img/media/1af905f750e1dc85eb490a3ec20bf76fb3ac51f7/0_486_2518_1509/master/2518.jpg?width=465&quality=45&auto=format&fit=max&dpr=2&s=1257fe1010592f3e91cf17a80471eefa")
    list.add("https://observer.com/wp-content/uploads/sites/2/2021/05/1_ynJEWSa6ivgFpF0EUP1L_A.jpeg?quality=80&strip")
    list.add("https://static.wikia.nocookie.net/disney/images/1/13/Gal_Gadot.jpg/revision/latest?cb=20180811005357")
    list.add("https://m.media-amazon.com/images/M/MV5BZWVhYzE0NzgtM2U1Yi00OWM1LWJlZTUtZmNkNWZhM2VkMDczXkEyXkFqcGdeQW1yb3NzZXI@._V1_.jpg")
    list.add("https://i.guim.co.uk/img/media/1af905f750e1dc85eb490a3ec20bf76fb3ac51f7/0_486_2518_1509/master/2518.jpg?width=465&quality=45&auto=format&fit=max&dpr=2&s=1257fe1010592f3e91cf17a80471eefa")
    list.add("https://observer.com/wp-content/uploads/sites/2/2021/05/1_ynJEWSa6ivgFpF0EUP1L_A.jpeg?quality=80&strip")
    list.add("https://static.wikia.nocookie.net/disney/images/1/13/Gal_Gadot.jpg/revision/latest?cb=20180811005357")
    list.add("https://m.media-amazon.com/images/M/MV5BZWVhYzE0NzgtM2U1Yi00OWM1LWJlZTUtZmNkNWZhM2VkMDczXkEyXkFqcGdeQW1yb3NzZXI@._V1_.jpg")
    list.add("https://i.guim.co.uk/img/media/1af905f750e1dc85eb490a3ec20bf76fb3ac51f7/0_486_2518_1509/master/2518.jpg?width=465&quality=45&auto=format&fit=max&dpr=2&s=1257fe1010592f3e91cf17a80471eefa")
    list.add("https://observer.com/wp-content/uploads/sites/2/2021/05/1_ynJEWSa6ivgFpF0EUP1L_A.jpeg?quality=80&strip")
    list.add("https://static.wikia.nocookie.net/disney/images/1/13/Gal_Gadot.jpg/revision/latest?cb=20180811005357")
    list.add("https://m.media-amazon.com/images/M/MV5BZWVhYzE0NzgtM2U1Yi00OWM1LWJlZTUtZmNkNWZhM2VkMDczXkEyXkFqcGdeQW1yb3NzZXI@._V1_.jpg")
    list.add("https://i.guim.co.uk/img/media/1af905f750e1dc85eb490a3ec20bf76fb3ac51f7/0_486_2518_1509/master/2518.jpg?width=465&quality=45&auto=format&fit=max&dpr=2&s=1257fe1010592f3e91cf17a80471eefa")
    list.add("https://observer.com/wp-content/uploads/sites/2/2021/05/1_ynJEWSa6ivgFpF0EUP1L_A.jpeg?quality=80&strip")
    list.add("https://static.wikia.nocookie.net/disney/images/1/13/Gal_Gadot.jpg/revision/latest?cb=20180811005357")
    list.add("https://m.media-amazon.com/images/M/MV5BZWVhYzE0NzgtM2U1Yi00OWM1LWJlZTUtZmNkNWZhM2VkMDczXkEyXkFqcGdeQW1yb3NzZXI@._V1_.jpg")
    list.add("https://i.guim.co.uk/img/media/1af905f750e1dc85eb490a3ec20bf76fb3ac51f7/0_486_2518_1509/master/2518.jpg?width=465&quality=45&auto=format&fit=max&dpr=2&s=1257fe1010592f3e91cf17a80471eefa")
    list.add("https://observer.com/wp-content/uploads/sites/2/2021/05/1_ynJEWSa6ivgFpF0EUP1L_A.jpeg?quality=80&strip")

    return list
  }


override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}