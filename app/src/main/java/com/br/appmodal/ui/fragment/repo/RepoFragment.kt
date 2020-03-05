package com.br.appmodal.ui.fragment.repo


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.br.appmodal.R
import com.br.appmodal.model.UserRepo
import com.br.appmodal.ui.components.FilterView
import com.br.appmodal.ui.fragment.repo.adapter.UserRepoAdapter
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_repo.*
import kotlinx.android.synthetic.main.view_filter.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class RepoFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private val repoViewModel:RepoViewModel by viewModel()

    private val adapter by lazy {
        UserRepoAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        this.activity?.title = resources.getString(R.string.app_name)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showSearch()

        observeData()
        configRecyclerView()

        addChipe("DATA")
        addChipe("SEGUIDORES")
        addChipe("DECRESCENTE")

        swipeRefresh.setOnRefreshListener(this)

        txtFilterViewListener()

    }

    private fun txtFilterViewListener(){
        txtFilter.txtFilterView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(char?.length!! > 3) {
                    repoViewModel.getUserRepoSearch("%$char%")
                }else{
                    repoViewModel.getUserRepoRefresh()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.nmu_user_repo, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nmu_actionFilter -> navFilter()
            R.id.mnu_actionSearch -> showSearch()
        }
        return false
    }

    private fun showSearch(){
       if(searchPainel.visibility == View.VISIBLE) searchPainel.visibility = View.GONE
       else searchPainel.visibility = View.VISIBLE
    }

    private fun configRecyclerView(){
        RecyclerViewUserRepo.adapter = adapter
        adapter.onClickItem = this::navItemRepo
        setRecyclerViewScrollListener()
    }

    private fun setRecyclerViewScrollListener(){
        RecyclerViewUserRepo.addOnScrollListener(object: OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == 0) {
                    val totalItemCount = recyclerView.adapter!!.itemCount
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastItemView = linearLayoutManager.findLastVisibleItemPosition()
                    if (totalItemCount == (lastItemView + 1)) {
                        repoViewModel.getUserRepo()
                    }
                }
            }
        })
    }

    private fun observeData() {
        context?.let {
            repoViewModel.getUserRepo()
            repoViewModel.userRepo().observe(viewLifecycleOwner, Observer {
                it.data?.let { data ->
                    adapter.update(data)
                    swipeRefresh.isRefreshing = false
                }
                it.erro?.let { error -> Log.e("Erro Dados:", error) }
            })
        }
    }

    private fun navItemRepo(item: UserRepo){
        val action = RepoFragmentDirections
            .actionRepoFragmentToDetailFragment(item.id!!)
        this.findNavController().navigate(action)
    }

    private fun navFilter(){
        this.findNavController().navigate(R.id.filterFragment)
    }

    private fun addChipe(text:String){
        val chip = Chip(filterGroup.context)
        chip.text = text
        chip.isCloseIconVisible = true
        filterGroup.addView(chip)
    }

    override fun onRefresh() {
        repoViewModel.getUserRepoRefresh()
    }
}
