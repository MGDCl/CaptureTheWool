package io.github.Leonardo0013YT.UltraCTW.objects;

import io.github.Leonardo0013YT.UltraCTW.xseries.XPotion;
import lombok.Getter;

@Getter
public class ObjectPotion {

    private XPotion potion;
    private int level;
    private int duration;

    public ObjectPotion(XPotion potion, int level, int duration) {
        this.potion = potion;
        this.level = level - 1;
        this.duration = duration * 20;
    }

}