package com.example.musicapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapp.MusicApp
import com.example.musicapp.adapter.MusicAdapter
import com.example.musicapp.databinding.FragmentRockBinding
import com.example.musicapp.domain.DomainSongs
import com.example.musicapp.presenter.RockPresenterContract
import com.example.musicapp.presenter.RockViewContract
import javax.inject.Inject


class Rock : Fragment(), RockViewContract {

    @Inject
    lateinit var rockPresenter: RockPresenterContract

    private val binding by lazy {
        FragmentRockBinding.inflate(layoutInflater)
    }

    private val songAdapter by lazy {
        MusicAdapter(
            object: songClickListener{
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

        binding.rockRV.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = songAdapter
        }

        rockPresenter.init(this)

//        binding.refreshData.setOnRefreshListener {
//            rockPresenter.getRockSongs()
//        }
        rockPresenter.getRockSongs()

        return binding.root
    }

    override fun loadingSongs(isLoading: Boolean) {

    }

    override fun successSongsResponse(songs: MutableList<DomainSongs>, isOffline: Boolean) {

        songAdapter.updateSongs(songs)
    }

    override fun error(error: Throwable) {

        Toast.makeText(context, "Error -> $error", Toast.LENGTH_LONG).show()
        Log.d("Error", "error: $error")
    }




    override fun onDestroy() {
        super.onDestroy()
        rockPresenter.destroyPresenter()
    }
}