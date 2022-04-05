package com.tenessine.intoreadwrite

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.tenessine.intoreadwrite.databinding.ActivityMainBinding
import com.tenessine.intoreadwrite.helpers.FileHelper
import com.tenessine.intoreadwrite.models.FileModel

class MainActivity : AppCompatActivity(), View.OnClickListener {
  private var _binding: ActivityMainBinding? = null
  private val binding get() = _binding!!

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.buttonNew.setOnClickListener(this)
    binding.buttonOpen.setOnClickListener(this)
    binding.buttonSave.setOnClickListener(this)
  }

  override fun onClick(v: View?) {
    when(v?.id){
      R.id.button_new -> newFile()
      R.id.button_open -> showList()
      R.id.button_save -> saveFile()
    }
  }

  private fun saveFile() {
    when {
      binding.editTitle.text.toString().isEmpty() -> Toast.makeText(this, "Title is empty", Toast.LENGTH_SHORT).show()
      binding.editFile.text.toString().isEmpty() -> Toast.makeText(this, "File is empty", Toast.LENGTH_SHORT).show()
      else -> {
        val title = binding.editTitle.text.toString()
        val file = binding.editFile.text.toString()
        val fileModel = FileModel()
        fileModel.filename = title
        fileModel.data = file
        FileHelper.writeToFile(fileModel, this)
        Toast.makeText(this, "Saving ${fileModel.filename} file", Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun showList() {
    val items = fileList()
    val builder = AlertDialog.Builder(this)
    builder.setTitle("Choose a file")
    builder.setItems(items) {dialog, item -> loadData(items[item].toString())}
    val alert = builder.create()
    alert.show()
  }

  private fun loadData(title: String) {
    val fileModel = FileHelper.readFromFile(this, title)
    binding.editTitle.setText(fileModel.filename)
    binding.editFile.setText(fileModel.data)
    Toast.makeText(this, "File loaded", Toast.LENGTH_SHORT).show()
  }

  private fun newFile() {
    binding.editFile.setText("")
    binding.editTitle.setText("")
    Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show()
  }


  override fun onDestroy() {
    super.onDestroy()
    _binding = null
  }
}