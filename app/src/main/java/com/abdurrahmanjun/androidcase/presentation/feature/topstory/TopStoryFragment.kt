package com.abdurrahmanjun.androidcase.presentation.feature.topstory

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.abdurrahmanjun.androidcase.databinding.FragmentStoryListBinding
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View.GONE
import androidx.lifecycle.lifecycleScope
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdurrahmanjun.androidcase.data.ServiceGenerator
import com.abdurrahmanjun.androidcase.data.database.AndroidCaseDao
import com.abdurrahmanjun.androidcase.data.database.AndroidCaseDatabase
import com.abdurrahmanjun.androidcase.data.story.repository.source.network.result.StoryDetailsResult
import com.abdurrahmanjun.androidcase.domain.model.Story
import com.abdurrahmanjun.androidcase.presentation.adapter.StoryAdapter
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import kotlinx.coroutines.launch

class TopStoryFragment : Fragment() {

    private lateinit var binding: FragmentStoryListBinding
    private lateinit var adapter: StoryAdapter
    private val viewModel: TopStoryViewModel by viewModels()
    private val disposables = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoryListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        Thread.sleep(4000)

        // favorite story stuff
        populateFavoriteStory()

        // top story stuff
        showProgressBar(true)
        getTopStoryObservable()
            ?.subscribeOn(Schedulers.io())
            ?.flatMap(Function<Int?, ObservableSource<Story?>?> {
                    post -> getCommentsObservable(post)
            })
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<Story?> {
                override fun onSubscribe(d: Disposable) {
                    disposables.add(d)
                }

                override fun onNext(t: Story) {
                    updateStory(t)
                }

                override fun onError(e: Throwable) {
                    Log.e("TAG", "onError: ", e)
                }

                override fun onComplete() {}
            })
    }

    private fun populateFavoriteStory() {
        val dao : AndroidCaseDao? = context?.let { AndroidCaseDatabase.getInstance(it).androidCaseDao }
        lifecycleScope.launch {
            Log.e("TAG-launch", "onViewCreated: "+dao?.getAll() )
            val favoriteStory = dao?.getAll()

            when (favoriteStory?.size) {
                0 -> hideOthersCard("You havent choose any favorite story")
                1 -> hideOthersCard(favoriteStory.get(0).storyTitle.toString())
                else -> { // Note the block
                    binding.tvMoreFavorite.text = "${favoriteStory?.size?.minus(1)} more"
                }
            }
        }
    }

    private fun hideOthersCard(s: String) {
        binding.cvOthersFav.visibility = GONE
        binding.tvFavorite.text = s
    }

    private fun initRecyclerView() {
        adapter = StoryAdapter()
        binding.rvStory.setLayoutManager(LinearLayoutManager(context))
        binding.rvStory.setAdapter(adapter)
    }

    private fun getTopStoryObservable(): Observable<Int>? {
        return ServiceGenerator.getRequestApi()
            .getStory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap(object : Function<List<Int>, ObservableSource<Int>> {
                override fun apply(t: List<Int>): ObservableSource<Int> {
                    showProgressBar(false)
                    adapter.notifyStoriesValueChange(viewModel.getTopStory.transformArrayIntegerIntoPost(t))
                    return Observable.fromIterable(t)
                        .subscribeOn(Schedulers.io())
                }
            })
    }

    private fun getCommentsObservable(post: Int): Observable<Story> {
        return post.let {
            ServiceGenerator.getRequestApi()
                .getStoryDetails(it)
                .map(object : Function<StoryDetailsResult?, Story> {
                    override fun apply(t: StoryDetailsResult): Story {
                        val delay = (Random().nextInt(5) + 1) * 1000 // sleep thread for x ms
                        Thread.sleep(delay.toLong())
                        return viewModel.getTopStory.transformRawResponseIntoStory(t)
                    }
                })
                .subscribeOn(Schedulers.io())
        }
    }

//    private fun subscribeObservers(){
//        viewModel.getTopStory.execute()
//            ?.flatMap { it ->
//                adapter.notifyStoriesValueChange(it)
//                Observable.fromIterable(it).subscribeOn(Schedulers.io())
//            }
//            ?.subscribeOn(Schedulers.io())
//            ?.flatMap{ it ->
//                viewModel.getTopStory.execute(it)
//            }
//            ?.observeOn(AndroidSchedulers.mainThread())
//            ?.subscribe(object : Observer<Story> {
//                override fun onSubscribe(d: Disposable) {
//                    disposables.add(d)
//                }
//
//                override fun onNext(t: Story) {
//                    updateStory(t)
//                }
//
//                override fun onError(e: Throwable) {
//                    Log.e("TAG", "onError: ", e)
//                }
//
//                override fun onComplete() {}
//            })
//    }

    private fun updateStory(story: Story?) {
        adapter.updateStory(story)
    }

    private fun showProgressBar(showProgressBar: Boolean) {
        if (showProgressBar) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}