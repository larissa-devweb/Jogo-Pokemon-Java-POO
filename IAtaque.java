package jogopokemon;

public interface IAtaque {
    int atacar(Pokemon alvo, int turno);

    int calcularDano(Pokemon atacante, Pokemon alvo);
}
