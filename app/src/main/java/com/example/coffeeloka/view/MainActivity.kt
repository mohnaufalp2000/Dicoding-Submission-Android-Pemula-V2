package com.example.coffeeloka.view

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeeloka.R
import com.example.coffeeloka.adapter.AdapterCoffee
import com.example.coffeeloka.data.CoffeeData
import com.example.coffeeloka.databinding.ActivityMainBinding
import com.example.coffeeloka.model.Coffee


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val listAdapter = AdapterCoffee(CoffeeData.listData, this)

    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showRecyclerView()
        findList()
        profileListener()

    }


    private fun profileListener() {
        binding.tbMain.setOnMenuItemClickListener{menuItem ->
            when(menuItem.itemId){
                R.id.account -> {
                    val profileIntent = Intent(this@MainActivity, ProfileActivity::class.java)
                    startActivity(profileIntent)
                    true
                }
                else -> false
            }
        }
    }

    private fun showRecyclerView() {

        binding.rvCoffee.setHasFixedSize(true)
        binding.rvCoffee.layoutManager = LinearLayoutManager(this)
        binding.rvCoffee.adapter = listAdapter

        listAdapter.setOnItemClickCallback(object : AdapterCoffee.OnItemClickCallback{
            override fun onItemClicked(coffee: Coffee) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.apply {
                    putExtra(DetailActivity.TITLE, coffee.titleCoffee)
                    putExtra(DetailActivity.ADDRESS, coffee.addressCoffee)
                    putExtra(DetailActivity.DETAIL, coffee.detailCoffee)
                    putExtra(DetailActivity.HOURS, coffee.hoursCoffee)
                    putExtra(DetailActivity.PRICE, coffee.priceCoffee)
                    putExtra(DetailActivity.RATE, coffee.rateCoffee)
                    putExtra(DetailActivity.CALL, coffee.callCoffe)
                    putExtra(DetailActivity.IMAGE, coffee.imageCoffe)
                }
                startActivity(intent)
            }

        })

    }




    private fun findList() {
        binding.edtSearch.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                filterText(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })
    }

    private fun filterText(text : String) {
        val filteredList = ArrayList<Coffee>()

        for (item in CoffeeData.listData) {
            if (item.titleCoffee.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }
            listAdapter.filterList(filteredList)
    }



    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle(R.string.app_name)
            .setMessage("Anda yakin ingin keluar?")
            .setPositiveButton("OK",
                DialogInterface.OnClickListener { dialog, which -> finish()  }
            )
            .setNegativeButton("Cancel",
                DialogInterface.OnClickListener{dialog, which -> dialog.cancel()  })
            .show()
    }


}