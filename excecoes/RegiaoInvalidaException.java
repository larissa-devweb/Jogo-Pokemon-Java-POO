/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jogopokemon.excecoes;

// Define uma exceção personalizada para tratar posicionamento em região inválida
public class RegiaoInvalidaException extends RuntimeException {

    // Construtor da exceção que recebe uma mensagem e repassa para a superclasse
    public RegiaoInvalidaException(String mensagem) {
        super(mensagem); // passa a mensagem para o construtor da Exception padrão
    }
}
