package io.github.Leonardo0013YT.UltraCTW.api;

import io.github.Leonardo0013YT.UltraCTW.UltraCTW;
import io.github.Leonardo0013YT.UltraCTW.config.Settings;

public class CTWInjectionAPI {

    public static Settings createNewFile(String name, boolean defaults, boolean comments) {
        return new Settings(UltraCTW.get(), name, defaults, comments);
    }

}