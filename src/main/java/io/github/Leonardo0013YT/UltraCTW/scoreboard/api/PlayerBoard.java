package io.github.Leonardo0013YT.UltraCTW.scoreboard.api;

import java.util.Map;

public interface PlayerBoard<V, N, S> {

    V get(N score);

    void set(V name, N score);

    @SuppressWarnings("unchecked")
    void setAll(V... lines);

    void clear();

    void remove(N score);

    void delete();

    S getName();

    void setName(S name);

    Map<N, V> getLines();

}
