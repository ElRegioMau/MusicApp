package com.example.musicapp.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapp.MusicApp
import com.example.musicapp.adapter.MusicAdapter
import com.example.musicapp.adapter.SongClickListener
import com.example.musicapp.adapter.songClickListener
import com.example.musicapp.databinding.FragmentPopBinding
import com.example.musicapp.domain.DomainSongs
import com.example.musicapp.presenters.PopPresenter
import com.example.musicapp.presenters.PopViewContract
import com.example.musicapp.databinding.FragmentPopBinding
import com.example.musicapp.presenter.PopPresenter
import com.example.musicapp.presenter.PopViewContract
import javax.inject.Inject


class Pop : Fragment(), PopViewContract {

    @Inject
    lateinit var popPresenter: PopPresenter

    private val binding by lazy {
        FragmentPopBinding.inflate(layoutInflater)
    }

    private val songAdapter by lazy {
        MusicAdapter(
            object: SongClickListener {
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

        popPresenter.init(this)

        binding.popRV.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = songAdapter
        }

        popPresenter.getPopSongs()

        return binding.root
    }

    override fun loadingSongs(isLoading: Boolean) {


    }

    override fun successSongsResponse(songs: MutableList<DomainSongs>, isOffline: Boolean) {

        songAdapter.updateSongs(songs)
    }

    override fun error(error: Throwable) {

        Toast.makeText(context, "Error -> $error", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        popPresenter.destroyPresenter()
    }
}