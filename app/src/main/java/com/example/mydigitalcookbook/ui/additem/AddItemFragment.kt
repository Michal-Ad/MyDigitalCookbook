package com.example.mydigitalcookbook.ui.additem

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.mydigitalcookbook.data.model.Item
import com.example.mydigitalcookbook.ui.ItemViewModel
import com.example.mydigitalcookbook.R
import com.example.mydigitalcookbook.databinding.AddItemLayoutBinding

class AddItemFragment : Fragment() {

    private val viewModel : ItemViewModel by activityViewModels()

    private var _binding : AddItemLayoutBinding?  = null
    private val binding get() = _binding!!

    private var imgeUri : Uri? = null

    val pickItemLauncher : ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.OpenDocument()) {
            binding.resultImage.setImageURI(it)
            requireActivity().contentResolver.takePersistableUriPermission(it!!,Intent.FLAG_GRANT_READ_URI_PERMISSION)
            imgeUri = it
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AddItemLayoutBinding.inflate(inflater,container,false)

        binding.finishBtn.setOnClickListener {
            /* val bundle  = bundleOf("title" to binding.itemTitle.text.toString(),
                 "description" to binding.itemDescription.text.toString())*/
            if (binding.itemTitle.text.toString() == "") {
                // Alert
                val builder = this.context?.let { it1 -> AlertDialog.Builder(it1) }
                builder?.setTitle("Oops...")
                builder?.setMessage("Item title must not be empty")
                builder?.setPositiveButton("Ok") { _, _ ->
                    // alert dissappears, do nothing
                }
                val dialog = builder?.create()
                dialog?.show()
            }
            else {
                val item = Item(
                    binding.itemTitle.text.toString(),
                    binding.itemDescription.text.toString(),
                    imgeUri.toString()
                )
                viewModel.addItem(item)
                findNavController().navigate(R.id.action_addItemFragment_to_allItemsFragment)
            }
        }

        binding.imageBtn.setOnClickListener {
            pickItemLauncher.launch(arrayOf("image/*"))
        }
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}