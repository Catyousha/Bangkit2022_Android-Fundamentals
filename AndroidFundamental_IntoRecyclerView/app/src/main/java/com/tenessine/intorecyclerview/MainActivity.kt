package com.tenessine.intorecyclerview

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tenessine.intorecyclerview.adapters.ListHeroAdapter
import com.tenessine.intorecyclerview.databinding.ActivityMainBinding
import com.tenessine.intorecyclerview.models.Hero

class MainActivity : AppCompatActivity() {
  private lateinit var rvHeroes: RecyclerView
  private lateinit var binding: ActivityMainBinding
  private val list = ArrayList<Hero>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    rvHeroes = findViewById(R.id.rv_heroes)
    rvHeroes.setHasFixedSize(true)

    list.addAll(listHeroes)
    showRecyclerList()
  }

  private val listHeroes: ArrayList<Hero>
    get() {
      val dataName = resources.getStringArray(R.array.data_name)
      val dataDescription = resources.getStringArray(R.array.data_description)
      val dataPhoto = resources.getStringArray(R.array.data_photo)
      val listHero = ArrayList<Hero>()

      for (i in dataName.indices) {
        val hero = Hero(dataName[i], dataDescription[i], dataPhoto[i])
        listHero.add(hero)
      }
      return listHero
    }

  private fun showSelectedHero(hero: Hero) {
    Toast.makeText(this, "Nama Pahlawan: ${hero.name}", Toast.LENGTH_SHORT).show()
  }

  private fun showRecyclerList() {
    val listHeroAdapter = ListHeroAdapter(list)
    if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
      rvHeroes.layoutManager = GridLayoutManager(this, 2)
    } else {
      rvHeroes.layoutManager = LinearLayoutManager(this)
    }
    rvHeroes.adapter = listHeroAdapter

    listHeroAdapter.setOnItemClickCallback(
        object : ListHeroAdapter.OnItemClickCallback {
          override fun onItemClicked(data: Hero) {
            showSelectedHero(data)
          }
        })
  }
}
