package com.fabrizio.sendyou.ui.fragment

import androidx.fragment.app.Fragment
import com.fabrizio.sendyou.architecture.AttractionsViewModel
import com.fabrizio.sendyou.data.Attraction
import com.fabrizio.sendyou.ui.MainActivity

abstract class BaseFragment: Fragment() {
    protected val navController by lazy {
        (activity as MainActivity).navController
    }

    protected val activityViewModel: AttractionsViewModel
        get() = (activity as MainActivity).viewModel
}