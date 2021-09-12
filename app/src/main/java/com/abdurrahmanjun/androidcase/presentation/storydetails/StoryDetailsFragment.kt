package com.abdurrahmanjun.androidcase.presentation.storydetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdurrahmanjun.androidcase.business.datasource.network.story.response.StoryDetailsResult
import com.abdurrahmanjun.androidcase.databinding.FragmentStoryDetailsBinding
import com.abdurrahmanjun.androidcase.business.domain.models.Comment
import com.abdurrahmanjun.androidcase.presentation.utils.ViewUtils.Companion.getDate
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class StoryDetailsFragment : Fragment() {

    private lateinit var binding: FragmentStoryDetailsBinding
    private lateinit var adapter: CommentAdapter
    private lateinit var viewModel: StoryDetailsViewModel

    companion object {
        var EXTRA_ID = "extra_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.requireActivity().application)
        ).get(StoryDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoryDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        if (arguments != null) {
            viewModel.storyId = arguments?.getInt(EXTRA_ID)!!
            viewModel.isFavorite()
        }

        viewModel.booleanFavoriteDataState.observe(viewLifecycleOwner, { bool ->
            animateHeart(binding.productLoved, binding.productLovedIdle, bool)
        })

        Thread.sleep(4000)
        binding.progressBar.visibility = View.VISIBLE
        binding.rlHeart.setOnClickListener {
            viewModel.favoriteAction(binding.tvTitleticket.text.toString())
        }

        viewModel.getDetailsStoryObservable()
            ?.subscribeOn(Schedulers.io())
            ?.flatMap(object : io.reactivex.functions.Function<StoryDetailsResult, ObservableSource<Int>> {
                override fun apply(t: StoryDetailsResult): ObservableSource<Int> {
                    binding.progressBar.visibility = View.GONE
                    updateFragmentView(t)
                    adapter.notifyCommentsValueChange(viewModel.getStoryDetails.transformArrayIntegerIntoComment(t.kids))

                    return Observable.fromIterable(t.kids)
                        .subscribeOn(Schedulers.io())
                }
            })
            ?.flatMap { commentId -> viewModel.getCommentsDetailsObservable(commentId) }
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<Comment?> {
                override fun onSubscribe(d: Disposable) {
                    viewModel.disposables.add(d)
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

    private fun updateFragmentView(detailsStory: StoryDetailsResult) {
        binding.tvId.text = "ID : " + detailsStory.id
        binding.tvDate.text = getDate(detailsStory.time.toLong(), "dd/MM/yyyy hh:mm:ss.SSS")
        binding.tvTitleticket.text = detailsStory.title
        binding.tvUrl.text = detailsStory.url
        binding.tvComment.text = "${detailsStory.kids.size} Comments"
    }

    private fun initRecyclerView() {
        adapter = CommentAdapter()
        binding.rvComment.setLayoutManager(LinearLayoutManager(context))
        binding.rvComment.setAdapter(adapter)
    }

    fun animateHeart(imageLovedOn: ImageView, imageLovedOff: ImageView, on: Boolean) {
        imageLovedOff.animate().scaleX(if (on) 0.toFloat() else 1.toFloat()).scaleY(if (on) 0.toFloat() else 1.toFloat()).alpha(if (on) 0.toFloat() else 1.toFloat())
        imageLovedOn.animate().scaleX(if (on) 1.toFloat() else 0.toFloat()).scaleY(if (on) 1.toFloat() else 0.toFloat()).alpha(if (on) 1.toFloat() else 0.toFloat())
    }
}