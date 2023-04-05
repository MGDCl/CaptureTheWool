package io.github.Leonardo0013YT.UltraCTW.interfaces;

import io.github.Leonardo0013YT.UltraCTW.calls.CallBackAPI;
import org.bukkit.Location;

public interface WorldEdit {

    void paste(Location loc, String schematic, boolean air, CallBackAPI<Boolean> done);

}
