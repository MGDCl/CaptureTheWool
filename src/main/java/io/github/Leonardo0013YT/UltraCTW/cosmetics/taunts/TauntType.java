package io.github.Leonardo0013YT.UltraCTW.cosmetics.taunts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class TauntType {

    private String damage;
    private Collection<String> messages;

    public TauntType(String damage, Collection<String> messages) {
        this.damage = damage;
        this.messages = messages;
    }

    public String getDamage() {
        return damage;
    }

    public Collection<String> getMessages() {
        return messages;
    }

    public String getRandomMessage() {
        return new ArrayList<>(messages).get(ThreadLocalRandom.current().nextInt(0, messages.size()));
    }

}