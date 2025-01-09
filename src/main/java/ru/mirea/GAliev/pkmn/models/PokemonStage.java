package ru.mirea.GAliev.pkmn.models;

public enum PokemonStage {
    BASIC("BASIC"),
    STAGE1("STAGE1"),
    STAGE2("STAGE2"),
    VSTAR("VSTAR"),
    VMAX("VMAX");

    private final String stage;

    PokemonStage(String stage) {
        this.stage = stage;
    }

    @Override
    public String toString() {
        return stage;
    }
}

