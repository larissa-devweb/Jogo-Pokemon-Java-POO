package jogopokemon;

import java.util.Random;

public interface IAtaque {
    int calcularDano(Pokemon atacante, Pokemon alvo);
}

class AtaqueNormal implements IAtaque {
    private final Random random = new Random();

    @Override
    public int calcularDano(Pokemon atacante, Pokemon alvo) {
        return atacante.getForca() + random.nextInt(atacante.getNivel() + 1);
    }
}

class AtaqueTipo implements IAtaque {
    private final Random random = new Random();

    @Override
    public int calcularDano(Pokemon atacante, Pokemon alvo) {
        double fatorTipo = 1.0;
        if (atacante.getTipo().equals("Agua") && alvo.getTipo().equals("Fogo")) fatorTipo = 1.5;
        else if (atacante.getTipo().equals("Fogo") && alvo.getTipo().equals("Agua")) fatorTipo = 0.5;

        int dano = atacante.getForca() + random.nextInt(atacante.getNivel() + 1);
        return (int)(dano * fatorTipo);
    }
}
