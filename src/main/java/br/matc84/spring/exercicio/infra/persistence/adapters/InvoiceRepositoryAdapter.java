package br.matc84.spring.exercicio.infra.persistence.adapters;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import br.matc84.spring.exercicio.domain.models.InvoiceModel;
import br.matc84.spring.exercicio.domain.ports.InvoiceRepositoryPort;
import br.matc84.spring.exercicio.infra.persistence.entities.InvoiceEntity;
import br.matc84.spring.exercicio.infra.persistence.mappers.InvoiceMapper;
import br.matc84.spring.exercicio.infra.persistence.repositories.InvoiceRepository;

public class InvoiceRepositoryAdapter implements InvoiceRepositoryPort {

    private final InvoiceRepository invoiceRepository;

    public InvoiceRepositoryAdapter(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public List<InvoiceModel> getAll() {
        List<InvoiceEntity> invoices = this.invoiceRepository.findAll();

        return invoices.stream().map(InvoiceMapper::toModel).collect(Collectors.toList());
    }

    @Override
    public Optional<InvoiceModel> findById(String uuid) {
        Optional<InvoiceEntity> invoice = this.invoiceRepository.findById(uuid);

        return invoice.map(InvoiceMapper::toModel);
    }

    @Override
    public InvoiceModel create(BigDecimal totalValue, LocalDate dueDate) {
        InvoiceEntity invoice = InvoiceEntity.InvoiceEntityBuilder.anInvoiceEntity().withTotalValue(totalValue)
                .withDueDate(dueDate).build();
        this.invoiceRepository.save(invoice);
        return InvoiceMapper.toModel(invoice);
    }
}
