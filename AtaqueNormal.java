package jogopokemon;

public class AtaqueNormal implements IAtaque {
    @Override
    public int calcularDano(Pokemon atacante, Pokemon alvo) {
        int base = atacante.getForca() + (int)(Math.random() * (atacante.getNivel() + 1));
        int bonusHabilidade = 0; // pode adicionar habilidades
        double fatorTipo = 1.0; // ajuste de vantagem/desvantagem por tipo
        if (atacante.getTipo().equals("floresta")) fatorTipo = 1.1;
        if (atacante.getTipo().equals("agua") && alvo.isEmAmbienteAdverso()) fatorTipo = 0.8;
        return (int)(base * fatorTipo + bonusHabilidade);
    }

    @Override
    public int atacar(Pokemon alvo, int turno) {
        return 0;
    }
}
