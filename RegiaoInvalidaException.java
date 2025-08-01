/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jogopokemon;

// Define uma exceção personalizada para tratar posicionamento inválido
public class RegiaoInvalidaException extends Exception {

    // Construtor da exceção que recebe uma mensagem e repassa para a superclasse
    public RegiaoInvalidaException(String mensagem) {
        super(mensagem); // passa a mensagem para o construtor da Exception padrão
    }
}
