package com.abdurrahmanjun.androidcase.presentation.feature.topstory

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.abdurrahmanjun.androidcase.databinding.FragmentStoryListBinding
import android.view.ViewGroup
import android.view.LayoutInflater
import io.reactivex.Observer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import androidx.recyclerview.widget.LinearLayoutManager

import com.abdurrahmanjun.androidcase.domain.model.Story
import com.abdurrahmanjun.androidcase.presentation.adapter.StoryAdapter
import io.reactivex.disposables.CompositeDisposable


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
        subscribeObservers()
    }

    private fun initRecyclerView() {
        adapter = StoryAdapter()
        binding.rvStory.setLayoutManager(LinearLayoutManager(context))
        binding.rvStory.setAdapter(adapter)
        binding.rvStory.setHasFixedSize(true)
    }

    private fun subscribeObservers(){
        viewModel.getTopStory.execute()
            ?.flatMap { it ->
                adapter.notifyStoriesValueChange(it)
                Observable.fromIterable(it).subscribeOn(Schedulers.io())
            }
            ?.subscribeOn(Schedulers.io())
            ?.flatMap{ it ->
                viewModel.getTopStory.execute(it)
                    ?.map { story : Story ->
                        val delay = (Random().nextInt(5) + 1) * 1000 // sleep thread for x ms
                        Thread.sleep(delay.toLong())
                        story
                    }
            }
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : Observer<Story> {
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

    private fun updateStory(story: Story?) {
        adapter.updateStory(story)
    }

}