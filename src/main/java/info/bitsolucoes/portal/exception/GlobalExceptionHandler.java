package info.bitsolucoes.portal.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SolicitacaoNaoEncontradaException.class)
    public String handleSolicitacaoNaoEncontrada(SolicitacaoNaoEncontradaException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/404"; // Aponta para uma página de erro amigável ou reutiliza uma view
    }
}