package com.abdurrahmanjun.androidcase.presentation.topstory

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.abdurrahmanjun.androidcase.databinding.FragmentStoryListBinding
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.abdurrahmanjun.androidcase.R
import com.abdurrahmanjun.androidcase.business.datasource.network.ServiceGenerator
import com.abdurrahmanjun.androidcase.business.datasource.cache.favorite.FavoriteDao
import com.abdurrahmanjun.androidcase.business.datasource.cache.AndroidCaseDatabase
import com.abdurrahmanjun.androidcase.business.datasource.cache.favorite.Favorite
import com.abdurrahmanjun.androidcase.business.datasource.network.story.response.StoryDetailsResult
import com.abdurrahmanjun.androidcase.business.domain.models.Story
import com.abdurrahmanjun.androidcase.presentation.storydetails.StoryDetailsViewModel
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TopStoryFragment : Fragment() {

    private lateinit var binding: FragmentStoryListBinding
    private lateinit var adapter: StoryAdapter
    private lateinit var viewModel: TopStoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this.requireActivity().application)
        ).get(TopStoryViewModel::class.java)
    }

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
        viewModel.populateFavoriteStory()
        viewModel.listFavoriteDataState.observe(viewLifecycleOwner, { favoriteStories ->
            Log.d("FavoriteStoriesView", favoriteStories.toString())
            Log.d("FavoriteStoriesView", favoriteStories.size.toString())
            when (favoriteStories.size) {
                0 -> hideOthersCard("You haven't chosen any favorite stories yet",true)
                1 -> hideOthersCard(favoriteStories.get(0).storyTitle.toString(),false)
                else -> { // Note the block
                    binding.tvFavorite.text = favoriteStories.get(0).storyTitle.toString()
                    binding.cvOthersFav.visibility = VISIBLE
                    binding.tvMoreFavorite.text = "${favoriteStories?.size?.minus(1)} more"
                }
            }
        })

        // top story stuff
        binding.progressBar.visibility = View.VISIBLE
        Thread.sleep(4000)
        viewModel.getTopStory.
            getTopStoryObservable()
            ?.subscribeOn(Schedulers.io())
            ?.flatMap(object : Function<List<Int>, ObservableSource<Int>> {
                override fun apply(t: List<Int>): ObservableSource<Int> {
                    binding.progressBar.visibility = View.GONE
                    adapter.notifyStoriesValueChange(viewModel.getTopStory.transformArrayIntegerIntoPost(t))
                    return Observable.fromIterable(t)
                        .subscribeOn(Schedulers.io())
                }
            })
            ?.flatMap({viewModel.getTopStory.getCommentsObservable(it)})
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<Story?> {
                override fun onSubscribe(d: Disposable) {
                    viewModel.disposables.add(d)
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

    private fun hideOthersCard(s: String,applySecondary : Boolean) {
        binding.cvOthersFav.visibility = GONE
        binding.tvFavorite.text = s
        if (applySecondary) {
            binding.tvFavorite.setTextColor(ContextCompat.getColor(this.requireContext(),R.color.md_grey_light))
        }
    }

    private fun initRecyclerView() {
        adapter = StoryAdapter()
        binding.rvStory.setLayoutManager(LinearLayoutManager(context))
        binding.rvStory.setAdapter(adapter)
    }

    private fun updateStory(story: Story?) {
        adapter.updateStory(story)
    }

}