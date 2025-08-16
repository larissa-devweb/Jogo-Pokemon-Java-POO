package jogopokemon;

// Ataque específico baseado no tipo do Pokémon
public class AtaqueTipo implements IAtaque {

    @Override
    public int atacar(Pokemon alvo, int turno) {
        return 0;
    }

    @Override
    public int calcularDano(Pokemon atacante, Pokemon alvo) {
        int dano = atacante.getForca() + (int)(Math.random() * (atacante.getNivel() + 1));

        switch (atacante.getTipo().toLowerCase()) {
            case "floresta":
                // Regeneração parcial: recupera 20% do dano causado
                int regen = (int)(dano * 0.2);
                atacante.curar(regen);
                break;
            case "terra":
                // Dano dobrado em turno ímpar
                // Para simplificar, vamos assumir turno = 1
                int turno = 1; // você pode passar como parâmetro se quiser
                if (turno % 2 == 1) dano *= 2;
                break;
            case "eletrico":
                // 30% chance de paralisar
                if (Math.random() < 0.3) alvo.paralisar();
                break;
            case "agua":
                // Se estiver em ambiente adverso, reduz dano recebido em 50%
                if (alvo.isEmAmbienteAdverso()) dano /= 2;
                break;
        }

        alvo.receberDano(dano);
        return dano;
    }
}
