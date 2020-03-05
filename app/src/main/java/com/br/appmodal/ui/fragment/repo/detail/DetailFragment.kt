package com.br.appmodal.ui.fragment.repo.detail


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.br.appmodal.R
import kotlinx.android.synthetic.main.fragment_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailFragment : Fragment() {

    private val detailViewModel:DetailViewModel by viewModel()
    private var id:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getLong("id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        this.activity?.title = resources.getString(R.string.detail_fragment_title)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }
    private fun observeData() {
        context?.let {
            detailViewModel.getUserRepoDetail(id).observe(viewLifecycleOwner, Observer {
                it.data?.let { data ->
                    txtName.text = data.name
                    txtStargazers.text = data.stargazersCount.toString()
                    txtCommits.text = data.commitsCount.toString()
                    txtReleases.text =  data.releasesCount.toString()
                    txtCollaborators.text =  data.collaboratorsCount.toString()
                    txtBranches.text = data.branchesCount.toString()
                }
                it.erro?.let { erro -> Log.d("Erro Dados:",erro) }
            })
            detailViewModel.getReadme(id).observe(viewLifecycleOwner, Observer {
                it.data?.let { data ->
                    txtReadme.loadMarkdown(data.contentString())
                }
            })
        }
    }


}
