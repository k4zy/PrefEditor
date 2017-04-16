package com.github.kazy1991.prefeditor.sample


import com.github.kazy1991.prefkit.annotation.PrefKey
import com.github.kazy1991.prefkit.annotation.PrefSchema


@PrefSchema("SampleOneSchema")
interface SampleOneSchema {

    @PrefKey(featureFlag)
    fun setFeatureFlag1(flag: Boolean)

    companion object {
        const val featureFlag = "featureFlag"
    }
}
