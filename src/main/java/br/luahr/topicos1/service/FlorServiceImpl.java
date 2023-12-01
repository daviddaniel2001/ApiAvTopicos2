package br.luahr.topicos1.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

import br.luahr.topicos1.dto.FlorDTO;
import br.luahr.topicos1.dto.FlorResponseDTO;
import br.luahr.topicos1.model.Flor;
import br.luahr.topicos1.model.Fornecedor;
import br.luahr.topicos1.model.TipoFlor;
import br.luahr.topicos1.repository.FlorRepository;

@ApplicationScoped
public class FlorServiceImpl implements FlorService{

    @Inject
    FlorRepository florRepository;

    @Inject
    Validator validator;

    @Override
    public List<FlorResponseDTO> getAll(int page, int pageSize) {
        List<Flor> list = florRepository.findAll().page(page, pageSize).list();
        return list.stream().map(e -> FlorResponseDTO.valueOf(e)).collect(Collectors.toList());
    }

    @Override
    public FlorResponseDTO findById(Long id) {
        Flor flor = florRepository.findById(id);
        if(flor == null)
            throw new NotFoundException("Flor não encontrada.");
        return FlorResponseDTO.valueOf(flor);
    }

    @Override
    @Transactional
    public FlorResponseDTO create(FlorDTO florDTO) {
        //validar(florDTO);

        Flor entity = new Flor();

        entity.setNome(florDTO.nome());
        entity.setDescricao(florDTO.descricao());
        entity.setValorUnidade(florDTO.valorUnidade());
        entity.setCorPetalas(florDTO.corPetalas());
        entity.setAlturaCaule(florDTO.alturaCaule());
        entity.setTipoFlor(TipoFlor.valueOf(florDTO.tipoFlor()));
        entity.setFornecedor(new Fornecedor());
        entity.getFornecedor().setId(florDTO.idFornecedor());

        florRepository.persist(entity);

        return FlorResponseDTO.valueOf(entity);

    }

    @Override
    @Transactional
    public FlorResponseDTO update(Long id, FlorDTO florDTO) throws ConstraintViolationException {
        validar(florDTO);
        
        Flor entity = florRepository.findById(id);

        entity.setNome(florDTO.nome());
        entity.setDescricao(florDTO.descricao());
        entity.setValorUnidade(florDTO.valorUnidade());
        entity.setCorPetalas(florDTO.corPetalas());
        entity.setAlturaCaule(florDTO.alturaCaule());
        entity.setTipoFlor(TipoFlor.valueOf(florDTO.tipoFlor()));
        entity.setFornecedor(new Fornecedor());
        entity.getFornecedor().setId(florDTO.idFornecedor());

        return FlorResponseDTO.valueOf(entity);

    }

    private void validar(FlorDTO florDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<FlorDTO>> violations = validator.validate(florDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    @Override
    @Transactional
    public void delete(Long id) throws IllegalArgumentException, NotFoundException{
        florRepository.deleteById(id);
    }

    @Override
    public long count() {
        return florRepository.count();
    }
    
    @Override
    public List<FlorResponseDTO> findByNome(String nome, int page, int pageSize) {
        List<Flor> list = florRepository.findByNome(nome).page(page, pageSize).list();
        return list.stream().map(e -> FlorResponseDTO.valueOf(e)).collect(Collectors.toList());
    }

    @Override
    public long countByNome(String nome) {
        return florRepository.findByNome(nome).count();
    }

    @Override
    public FlorResponseDTO saveImage(Long id, String nomeImagem) {

        Flor entity = florRepository.findById(id);
        entity.setNomeImagem(nomeImagem);

        return FlorResponseDTO.valueOf(entity);
    }

    @Override
    public byte[] createReportFlor(String filter) {
        List<Flor> lista = florRepository.findByNome(filter).list();
        return gerarRelatorioPDF(lista);
    }

    public static byte[] gerarRelatorioPDF(List<Flor> florList) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    try (PdfDocument pdfDocument = new PdfDocument(new PdfWriter(baos))) {
      Document document = new Document(pdfDocument);
      pdfDocument.addEventHandler(PdfDocumentEvent.END_PAGE, new HeaderFooterHandler());

      PdfFont font = PdfFontFactory.createFont(StandardFonts.COURIER);

      float tamanhoFonte = 10f;

      document.add(new Paragraph("Relatório de Flor")
          .setFontSize(16)
          .setBold()
          .setFont(font));

      document.add(new Paragraph("Detalhes das flores")
          .setFontSize(14)
          .setItalic()
          .setFont(font));

      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
      String dataHora = sdf.format(new Date());
      document.add(new Paragraph("Data e Hora de Geração: " + dataHora).setFontSize(12).setFont(font));

      Table table = new Table(UnitValue.createPercentArray(new float[] { 1, 2, 1 })).useAllAvailableWidth();
      table.addHeaderCell(new Cell().add(new Paragraph("ID")
          .setFontSize(tamanhoFonte)
          .setFont(font)));
      table.addHeaderCell(new Cell().add(new Paragraph("Nome")
          .setFontSize(tamanhoFonte)
          .setFont(font)));
      table.addHeaderCell(new Cell().add(new Paragraph("Preço")
          .setFontSize(tamanhoFonte)
          .setFont(font)));

      for (Flor flor : florList) {
        table.addCell(new Cell().add(new Paragraph(String.valueOf(flor.getId()))
            .setFontSize(tamanhoFonte)
            .setFont(font)));
        table.addCell(
            new Cell().add(new Paragraph(flor.getNome())
                .setFontSize(tamanhoFonte)
                .setFont(font)));
        table.addCell(new Cell()
            .add(new Paragraph("R$" + flor.getValorUnidade())
                .setFontSize(tamanhoFonte)
                .setFont(font)));
      }

      document.add(table);

      document.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return baos.toByteArray();
  }
}

class HeaderFooterHandler implements IEventHandler {
  public void handleEvent(Event event) {
    PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
    PdfDocument pdf = docEvent.getDocument();
    PdfPage page = docEvent.getPage();
    int pageNum = pdf.getPageNumber(page);

    PdfCanvas canvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdf);
    canvas.beginText().setFontAndSize(pdf.getDefaultFont(), 12);

    canvas.moveText(34, 20).showText("Página " + pageNum);

    String dataHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm:ss"));
    canvas.moveText(500 - 80, 0).showText(dataHora);

    canvas.endText();
  }
}
