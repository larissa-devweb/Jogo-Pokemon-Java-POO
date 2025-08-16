package jogopokemon;

// Ataque normal básico
public class AtaqueNormal implements IAtaque {

    @Override
    public int atacar(Pokemon alvo, int turno) {
        return 0;
    }

    @Override
    public int calcularDano(Pokemon atacante, Pokemon alvo) {
        // Dano base: força + valor aleatório até o nível do atacante
        int dano = atacante.getForca() + (int)(Math.random() * (atacante.getNivel() + 1));
        alvo.receberDano(dano); // aplica dano
        return dano;
    }
}
