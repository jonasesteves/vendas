package br.com.jeweb.vendas.util;

import br.com.jeweb.vendas.entity.Frete;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Relatorio {

    public void gerarContrato(Frete frete) {

        try {
            List<Frete> fretes = new ArrayList<>();
            if (frete.getMotorista().getFoto() != null) {
                frete.setFoto(ImageIO.read(new ByteArrayInputStream(frete.getMotorista().getFoto())));
            }
            fretes.add(frete);
            String caminho = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/WEB-INF/relatorios/contrato.jasper");
            File file = new File(caminho);
            JasperPrint jasperPrint = JasperFillManager.fillReport(file.getPath(), null, new JRBeanCollectionDataSource(fretes, false));
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.addHeader("Content-disposition", "inline; filename=Contrato.pdf");
            ServletOutputStream stream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

            FacesContext.getCurrentInstance().responseComplete();

        } catch (JRException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
