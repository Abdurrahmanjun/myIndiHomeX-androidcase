package com.abdurrahmanjun.androidcase.presentation.storydetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdurrahmanjun.androidcase.business.datasource.network.ServiceGenerator
import com.abdurrahmanjun.androidcase.business.datasource.cache.favorite.FavoriteDao
import com.abdurrahmanjun.androidcase.business.datasource.cache.AndroidCaseDatabase
import com.abdurrahmanjun.androidcase.business.datasource.cache.favorite.Favorite
import com.abdurrahmanjun.androidcase.business.datasource.network.story.response.StoryDetailsCommentResult
import com.abdurrahmanjun.androidcase.business.datasource.network.story.response.StoryDetailsResult
import com.abdurrahmanjun.androidcase.databinding.FragmentStoryDetailsBinding
import com.abdurrahmanjun.androidcase.business.domain.models.Comment
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.lang.String
import java.text.SimpleDateFormat
import java.util.*

class StoryDetailsFragment : Fragment() {

    private lateinit var binding: FragmentStoryDetailsBinding
    private lateinit var adapter: CommentAdapter
    private lateinit var dao: FavoriteDao

    private var flagFav : Boolean = false
    private val viewModel: StoryDetailsViewModel by viewModels()
    private val disposables = CompositeDisposable()

    private var storyId: Int = 0

    companion object {
        var EXTRA_ID = "extra_id"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoryDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            storyId = arguments?.getInt(EXTRA_ID)!!
        }

        dao = context?.let { AndroidCaseDatabase.getInstance(it).favoriteDao }!!
        lifecycleScope.launch {
            if (dao.loadId(storyId) != null) {
                animateHeart(binding.productLoved, binding.productLovedIdle, true)
                flagFav = true
            } else {
                animateHeart(binding.productLoved, binding.productLovedIdle, false)
                flagFav = false
            }
        }

        initRecyclerView()
        Thread.sleep(4000)

        showProgressBar(true)
        binding.rlHeart.setOnClickListener { v: View? ->
            favClicked(binding.productLoved,binding.productLovedIdle)
        }

        getDetailsStoryObservable(storyId)
            ?.subscribeOn(Schedulers.io())
            ?.flatMap {
                    commentId -> getCommentsDetailsObservable(commentId)
            }
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<Comment?> {
                override fun onSubscribe(d: Disposable) {
                    disposables.add(d)
                }

                override fun onNext(t: Comment) {
                    updateComment(t)
                }

                override fun onError(e: Throwable) {
                    Log.e("TAG", "onError: ", e)
                }

                override fun onComplete() {}
            })
    }

    private fun updateComment(comment: Comment) {
        adapter.updateComment(comment)
    }

    private fun getDetailsStoryObservable(idStory : Int): Observable<Int>? {
        return ServiceGenerator.getRequestApi()
            .getStoryDetails(idStory)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap(object : io.reactivex.functions.Function<StoryDetailsResult, ObservableSource<Int>> {
                override fun apply(t: StoryDetailsResult): ObservableSource<Int> {
                    showProgressBar(false)
                    updateFragmentView(t)

                    adapter.notifyCommentsValueChange(viewModel.getStoryDetails.transformArrayIntegerIntoComment(t.kids))
                    return Observable.fromIterable(t.kids)
                        .subscribeOn(Schedulers.io())
                }
            })
    }

    private fun getCommentsDetailsObservable(commentId: Int): Observable<Comment> {
        return commentId.let {
            ServiceGenerator.getRequestApi()
                .getCommentsOnDetails(it)
                .map(object : io.reactivex.functions.Function<StoryDetailsCommentResult?, Comment> {
                    override fun apply(t: StoryDetailsCommentResult): Comment {
                        val delay = (Random().nextInt(5) + 1) * 1000 // sleep thread for x ms
                        Thread.sleep(delay.toLong())
                        return viewModel.getStoryDetails.transformRawResponseIntoComment(t)
                    }
                })
                .subscribeOn(Schedulers.io())
        }
    }

    private fun updateFragmentView(detailsStory: StoryDetailsResult) {
        binding.tvId.text = "ID : " + detailsStory.id
        binding.tvDate.text = getDate(detailsStory.time.toLong(), "dd/MM/yyyy hh:mm:ss.SSS")
        binding.tvTitleticket.text = detailsStory.title
        binding.tvUrl.text = detailsStory.url
        binding.tvComment.text = (String.valueOf(detailsStory.kids.size)) + " Comments"
    }

    private fun initRecyclerView() {
        adapter = CommentAdapter()
        binding.rvComment.setLayoutManager(LinearLayoutManager(context))
        binding.rvComment.setAdapter(adapter)
    }

    fun favClicked(product_loved : ImageView, product_loved_no : ImageView) {
        if (!flagFav) {
            lifecycleScope.launch {
                dao.insertFavorite(Favorite(storyId,binding.tvTitleticket.text.toString()))
            }

            animateHeart(product_loved, product_loved_no, true)
            flagFav = true
        } else {
            lifecycleScope.launch {
                dao.deleteByStoryId(storyId)
            }

            animateHeart(product_loved, product_loved_no, false)
            flagFav = false
        }
    }

    fun animateHeart(imageLovedOn: ImageView, imageLovedOff: ImageView, on: Boolean) {
        imageLovedOff.animate().scaleX(if (on) 0.toFloat() else 1.toFloat()).scaleY(if (on) 0.toFloat() else 1.toFloat()).alpha(if (on) 0.toFloat() else 1.toFloat())
        imageLovedOn.animate().scaleX(if (on) 1.toFloat() else 0.toFloat()).scaleY(if (on) 1.toFloat() else 0.toFloat()).alpha(if (on) 1.toFloat() else 0.toFloat())
    }

    private fun showProgressBar(showProgressBar: Boolean) {
        if (showProgressBar) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    fun getDate(milliSeconds: Long, dateFormat: kotlin.String?): kotlin.String? {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(milliSeconds)
        return formatter.format(calendar.getTime())
    }
}