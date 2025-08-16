package jogopokemon;

public interface IAtaque {
    // Método para calcular dano de um ataque
    int calcularDano(Pokemon atacante, Pokemon alvo);

    int atacar(Pokemon alvo, int turno);
}
