package de.sbg.unity.iconomy.Utils;

import net.risingworld.api.assets.PrefabAsset;

public class PrefabVorlage {

    private final String BundlePath;
    private final PrefabAsset prefabAsset;

    public PrefabVorlage(String bundlePath, PrefabAsset asset) {
        this.BundlePath = bundlePath;
        this.prefabAsset = asset;
    }

    public String getBundlePath() {
        return BundlePath;
    }

    public PrefabAsset getPrefabAsset() {
        return prefabAsset;
    }

}