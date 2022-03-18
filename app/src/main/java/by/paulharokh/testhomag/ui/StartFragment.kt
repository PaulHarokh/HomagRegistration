package by.paulharokh.testhomag.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import by.paulharokh.testhomag.R
import by.paulharokh.testhomag.databinding.FragmentStartBinding


class StartFragment : Fragment() {

    private var _startBinding: FragmentStartBinding? = null
    private val startBinding get() = _startBinding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _startBinding = FragmentStartBinding.inflate(inflater, container, false)
        return startBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startBinding.btnEntry.setOnClickListener{

            view.findNavController()
                .navigate(R.id.authFragment)
        }

        startBinding.btnReg.setOnClickListener{

            view.findNavController()
                .navigate(R.id.regFragment)
        }

    }


}