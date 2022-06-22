package com.example.musicapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapp.MusicApp
import com.example.musicapp.adapter.MusicAdapter
import com.example.musicapp.adapter.SongClickListener
import com.example.musicapp.databinding.FragmentClassicBinding
import com.example.musicapp.domain.DomainSongs
import com.example.musicapp.presenter.ClassicPresenterContract
import com.example.musicapp.presenter.ClassicViewContract
import javax.inject.Inject


class Classic : Fragment(), ClassicViewContract {

    @Inject
    lateinit var classicPresenter: ClassicPresenterContract

    private val binding by lazy {
        FragmentClassicBinding.inflate(layoutInflater)
    }

    private val songAdapter by lazy {
        MusicAdapter(object : SongClickListener {
                override fun onSongRClicked(song: DomainSongs) {
                }
            }

        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        MusicApp.component.inject(this)
        classicPresenter.init(this)

        binding.classicRV.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = songAdapter
        }
        classicPresenter.getClassicSongs()
        return binding.root
    }
    override fun loadingSongs(isLoading: Boolean) {
        TODO("Not yet implemented")
    }
    override fun successSongsResponse(songs: MutableList<DomainSongs>, isOffline: Boolean) {
        songAdapter.updateSongs(songs)
    }
    override fun error(error: Throwable) {

        Toast.makeText(context, "Error -> $error", Toast.LENGTH_SHORT).show()
    }
    override fun onDestroy() {
        super.onDestroy()
        classicPresenter.destroyPresenter()
    }
}