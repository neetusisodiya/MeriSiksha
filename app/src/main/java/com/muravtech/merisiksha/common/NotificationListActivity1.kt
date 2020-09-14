//package com.muravtech.merisiksha.common
//
//import android.os.Bundle
//import android.util.Log
//import android.view.View
//import android.widget.TextView
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
//import butterknife.BindView
//import butterknife.ButterKnife
//import com.muravtech.merisiksha.R
//import com.muravtech.merisiksha.adapter.NotficationAdapter
//import com.muravtech.merisiksha.interfaces.OnItemClickListener
//import com.muravtech.merisiksha.module.Notification
//import com.muravtech.merisiksha.server.APIClient
//import com.muravtech.merisiksha.server.DKRInterface
//import com.muravtech.merisiksha.student_ui.BaseActivity
//import com.muravtech.merisiksha.utils.AppPreferences
//import com.muravtech.merisiksha.utils.CommonUtils
//import com.muravtech.merisiksha.utils.CustomTextView
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class NotificationListActivity1 :BaseActivity(), OnItemClickListener {
//    @BindView(R.id.title_txt)
//    var titleTxt: TextView? = null
//
//    @BindView(R.id.recycler_view)
//    var mRecyclerView: RecyclerView? = null
//
//    @BindView(R.id.empty_view)
//    var emptyView: CustomTextView? = null
//
//    // private ArrayList<ClassListBean.DataBean> mList = new ArrayList<>();
//    private var mAdapter: NotficationAdapter? = null
//    var swipeRefreshLayout: SwipeRefreshLayout? = null
//    var type: String? = null
//    var from: String? = null
//    var onItemClickListener: OnItemClickListener? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.video_subject_name)
//        ButterKnife.bind(this)
//        titleTxt!!.text = "Notifications"
//        if (intent.extras != null) {
//            type = intent.getStringExtra("type")
//            from = intent.getStringExtra("from")
//        }
//        onItemClickListener = this
//    }
//
//    private fun findViews() {
//        mRecyclerView!!.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        //RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
//        if (isOnline(true)) {
//            try {
//                getClassList()
//            } catch (e: Exception) {
//            }
//        }
//    }
//
//    private fun getClassList() {
//        CommonUtils.progressDialogShow(this)
//        val call = APIClient.getClient().create(DKRInterface::class.java)
//                .getNotificationList(AppPreferences.getAccessId())
//        call.enqueue(object : Callback<Notification> {
//            override fun onResponse(call: Call<Notification>, response: Response<Notification>) {
//                CommonUtils.hideProgressDoalog()
//                if (response.body()!!.isStatus) {
//                    val childListBean1 = response.body()
//                    CommonUtils.hideProgressDoalog()
//                    //mList.addAll(childListBean1.getData());
//                    mAdapter = NotficationAdapter(this, childListBean1!!.data, onItemClickListener)
//                    mRecyclerView!!.adapter = mAdapter
//                } else {
//                    emptyView!!.visibility = View.VISIBLE
//                    emptyView!!.text = "No Data Found!!!"
//                    mRecyclerView!!.visibility = View.GONE
//                }
//            }
//
//            override fun onFailure(call: Call<Notification>, t: Throwable) {
//                call.cancel()
//                CommonUtils.hideProgressDoalog()
//                //  dismissProgressDialog();
//                Log.e("accesstokwen20 ", "" + t)
//            }
//        })
//    }
//
//    override fun onClick(position: Int) {
//        TODO("Not yet implemented")
//    }
//}