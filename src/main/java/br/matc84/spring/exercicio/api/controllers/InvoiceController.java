package br.matc84.spring.exercicio.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.matc84.spring.exercicio.api.dtos.InvoiceDTO;
import br.matc84.spring.exercicio.domain.models.InvoiceModel;
import br.matc84.spring.exercicio.domain.ports.InvoiceServicePort;

@Validated
@RestController
@RequestMapping(value = InvoiceController.API_URL)
public class InvoiceController {
    public static final String API_URL = "/v1/invoices";
    private final InvoiceServicePort invoiceServicePort;

    public InvoiceController(InvoiceServicePort invoiceServicePort) {
        this.invoiceServicePort = invoiceServicePort;
    }

    @GetMapping
    public List<InvoiceModel> getAll() {
        return this.invoiceServicePort.getAll();
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<InvoiceModel> findById(@PathVariable String uuid) {
        InvoiceModel response = this.invoiceServicePort.findById(uuid);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Validated InvoiceDTO invoice) {

       return new ResponseEntity<>(this.invoiceServicePort.create(invoice.totalValue, invoice.dueDate),
                HttpStatus.CREATED);

    }
}
