package com.github.kazy1991.prefeditor.sample;


import com.github.kazy1991.prefkit.annotation.PrefKey;
import com.github.kazy1991.prefkit.annotation.PrefSchema;


@PrefSchema("SampleOneSchema")
public interface SampleOneSchema {

    String featureFlag1 = "featureFlag1";

    @PrefKey(featureFlag1)
    void setFeatureFlag1(boolean flag);


}
