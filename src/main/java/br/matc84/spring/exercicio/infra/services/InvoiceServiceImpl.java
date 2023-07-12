package br.matc84.spring.exercicio.infra.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.matc84.spring.exercicio.domain.exceptions.InvoiceNotFoundException;
import br.matc84.spring.exercicio.domain.models.InvoiceModel;
import br.matc84.spring.exercicio.domain.ports.InvoiceRepositoryPort;
import br.matc84.spring.exercicio.domain.ports.InvoiceServicePort;

@Service
public class InvoiceServiceImpl implements InvoiceServicePort {
    private final InvoiceRepositoryPort invoiceRepositoryPort;
    private static final Logger LOGGER = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    public InvoiceServiceImpl(InvoiceRepositoryPort invoiceRepositoryPort) {
        this.invoiceRepositoryPort = invoiceRepositoryPort;
    }

    public List<InvoiceModel> getAll() {
        return this.invoiceRepositoryPort.getAll();
    }

    @Override
    public InvoiceModel findById(String uuid) {
        Optional<InvoiceModel> invoice = this.invoiceRepositoryPort.findById(uuid);

        if(invoice.isPresent()) {
            LOGGER.info("Invoice found -> {}", uuid);
            return invoice.get();
        }

        throw new InvoiceNotFoundException(uuid);
    }

    @Override
    public InvoiceModel create(BigDecimal totalValue, LocalDate dueDate) {
        return this.invoiceRepositoryPort.create(totalValue, dueDate);
    }
}
