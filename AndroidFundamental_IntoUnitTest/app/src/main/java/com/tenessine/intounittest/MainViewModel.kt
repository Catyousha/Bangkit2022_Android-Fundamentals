package com.tenessine.intounittest

import androidx.lifecycle.ViewModel
import com.tenessine.intounittest.models.CuboidModel

class MainViewModel(private val cuboidModel: CuboidModel): ViewModel() {
    fun getCircumference(): Double = cuboidModel.getCircumference()
    fun getSurfaceArea(): Double = cuboidModel.getSurfaceArea()
    fun getVolume(): Double = cuboidModel.getVolume()
    fun save(l: Double, w: Double, h: Double) = cuboidModel.save(l, w, h)
  
}