package info.bitsolucoes.portal.exception;

import info.bitsolucoes.portal.controller.TicketApiController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;

// O assignableTypes garante que este tratamento só afeta a API, não quebrando as telas Thymeleaf
@RestControllerAdvice(assignableTypes = TicketApiController.class)
public class ApiExceptionHandler {

    @ExceptionHandler(SolicitacaoNaoEncontradaException.class)
    public ProblemDetail handleSolicitacaoNaoEncontrada(SolicitacaoNaoEncontradaException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Recurso Não Encontrado");
        problemDetail.setType(URI.create("https://api.bitsolucoes.info/erros/nao-encontrado"));
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ProblemDetail handleIllegalArgument(IllegalArgumentException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Requisição Inválida");
        problemDetail.setType(URI.create("https://api.bitsolucoes.info/erros/requisicao-invalida"));
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }
}