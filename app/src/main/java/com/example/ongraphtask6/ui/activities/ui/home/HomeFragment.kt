package com.example.ongraphtask6.ui.activities.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ongraphtask6.databinding.FragmentHomeBinding
import com.example.ongraphtask6.retrofit.functionality.UserIDToSend
import com.example.ongraphtask6.retrofit.functionality.ViewFeedBuilderInstance
import com.example.ongraphtask6.retrofit.models.view_feed.SingleFeedPost
import com.example.ongraphtask6.retrofit.models.view_feed.ViewFeedResponse
import com.example.ongraphtask6.rv.FeedAdapter
import java.lang.Exception

class HomeFragment : Fragment() {

private var _binding: FragmentHomeBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!
    private val TAG = "Jay Home Fragment"

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

    _binding = FragmentHomeBinding.inflate(inflater, container, false)
    val root: View = binding.root

    homeViewModel.text.observe(viewLifecycleOwner) {

    }

    getDList()
    return root
  }
    private fun setupRecyclerView(context : Context, list : MutableList<SingleFeedPost> ){
        val rv = _binding?.recyclerViewFeeds

        rv?.apply {
            adapter = FeedAdapter(list ,  context)
            layoutManager = LinearLayoutManager(context , LinearLayoutManager.VERTICAL , false)
        }
    }

    private fun getDList() {
        lifecycleScope.launchWhenCreated {
            val response = try {
                val id = activity?.intent?.getIntExtra("uid" , 0)!!
                ViewFeedBuilderInstance.builderAPI.addToFeed(
                    UserIDToSend(id)
                )
            }

            catch (e: Exception) {
                Log.d(TAG, "onCreate: login Exception $e  , ${e.message}")
                return@launchWhenCreated
            }

            if(response.isSuccessful){
                val body = response.body()
                Log.d(TAG, "onCreate: response successful ${response.body()}")
                bodyToList(body!!)
            }
            else{
                Log.d(TAG, "onCreate: response unsuccessful")

            }
        }
    }

    fun bodyToList(body : ViewFeedResponse){
        val dList = mutableListOf<SingleFeedPost>()
        dList.addAll(getStaticList())
        for(i in body.data){
            var image = "https://m.media-amazon.com/images/M/MV5BZWVhYzE0NzgtM2U1Yi00OWM1LWJlZTUtZmNkNWZhM2VkMDczXkEyXkFqcGdeQW1yb3NzZXI@._V1_.jpg"

            try{

                image = i.images[0].image
            }
            catch (e : Exception){}

            val t = SingleFeedPost(
                i.user_data.full_name ,
                "https://static.wikia.nocookie.net/disney/images/1/13/Gal_Gadot.jpg/revision/latest?cb=20180811005357",
                image ,
                content = i.description
            )
            dList.add(t)
        }
        setupRecyclerView(requireContext() , dList)
    }

    fun getStaticList() : MutableList<SingleFeedPost>{
        val list = mutableListOf<SingleFeedPost>()
        list.add(
            SingleFeedPost(
                "Wonder Diana" ,
                "https://static.wikia.nocookie.net/disney/images/1/13/Gal_Gadot.jpg/revision/latest?cb=20180811005357",
                "https://m.media-amazon.com/images/M/MV5BZWVhYzE0NzgtM2U1Yi00OWM1LWJlZTUtZmNkNWZhM2VkMDczXkEyXkFqcGdeQW1yb3NzZXI@._V1_.jpg",
                "I will Ride for those who cannot Ride for themselves."
            ))

        list.add(
            SingleFeedPost(
                "Clark Joseph Kent" ,
                "https://i.guim.co.uk/img/media/1af905f750e1dc85eb490a3ec20bf76fb3ac51f7/0_486_2518_1509/master/2518.jpg?width=465&quality=45&auto=format&fit=max&dpr=2&s=1257fe1010592f3e91cf17a80471eefa",
                "https://observer.com/wp-content/uploads/sites/2/2021/05/1_ynJEWSa6ivgFpF0EUP1L_A.jpeg?quality=80&strip",
                "Wishing all of you a very happy Independence Day.\nStay Safe\nStay Super !!"
            ))

        list.add(
            SingleFeedPost(
                "Bruce Wayne" ,
                "https://pyxis.nymag.com/v1/imgs/abd/266/96205bfceb90512ef4e197a6ceb095a349-robert-pattinson.rvertical.w1200.jpg",
                "https://wegotthiscovered.com/wp-content/uploads/2021/05/the-batman-statue-1.jpg",
                "I'm Batman"
            ))

        list.add(
            SingleFeedPost(
                "Barry Allen" ,
                "https://media1.popsugar-assets.com/files/thumbor/kRHOD_VRocEvhfhkC5j8WNTQgAY/569x195:2942x2568/fit-in/2048xorig/filters:format_auto-!!-:strip_icc-!!-/2019/12/09/863/n/1922398/fce2eeaf5deea3c3b39189.70140056_/i/Grant-Gustin.jpg",
                "https://wallpapersmug.com/large/da5647/minimal-flash-barry-allen.jpg",
                "Run Barry , Run !!"
            ))


        return list
    }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}