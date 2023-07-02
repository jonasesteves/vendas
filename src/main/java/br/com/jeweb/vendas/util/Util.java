package br.com.jeweb.vendas.util;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;

public class Util {


    public Util() {

    }

    /**
     * Converte LocalDate para XMLGregorianCalendar.
     * @param localDate Valor para conversão.
     * @return Data convertida.
     */
    public static XMLGregorianCalendar localDateToXMLGC(LocalDate localDate) {
        try {
            GregorianCalendar g = GregorianCalendar.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            XMLGregorianCalendar x = DatatypeFactory.newInstance().newXMLGregorianCalendar(g);
            return x;
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Converte XMLGregorianCalendar para LocalDate.
     * @param data Valor para conversão
     * @return Data convertida.
     */
    public static LocalDate xmlGCToLocalDate(XMLGregorianCalendar data) {
        return data.toGregorianCalendar().toZonedDateTime().toLocalDate();
    }
}
