package sample.controller;

import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import sample.entity.Bilety;
import sample.entity.Miejsca;
import sample.entity.PDFDataModel;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Iterator;

public class BiletPrinter {

    private PDFDataModel model;

    private SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm");

    BiletPrinter(PDFDataModel model){
        this.model = model;
    }

    public void run(){
        drukujBilety(przygotujBiletyDoDruku());
    }

    private void drukujBilety(String sourceString){
        try {
            FopFactory fopFactory =
                    FopFactory.newInstance(new File(
                            "C:/Temp/fop.xconf"));

            OutputStream out = new BufferedOutputStream(new FileOutputStream(new File("./printOutput/printBilety-" + model.transakcja.getIdTransakcji() +".pdf")));

            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();

            Source src = new StreamSource(new StringReader(sourceString));
            Result res = new SAXResult(fop.getDefaultHandler());

            transformer.transform(src, res);

            out.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private String przygotujBiletyDoDruku(){
        StringBuilder xmlfoString = new StringBuilder();

        xmlfoString.append("<fo:root xmlns:fo=\"http://www.w3.org/1999/XSL/Format\">");

        xmlfoString.append(zwróćNagłówek());

        xmlfoString.append(zwróćBilety());

        xmlfoString.append("</fo:root>");

        return xmlfoString.toString();
    }

    private String zwróćNagłówek(){
        return "<fo:layout-master-set>\n" +
                "<fo:simple-page-master master-name=\"first\" page-height=\"4.5cm\" page-width=\"13cm\" margin-top=\"0.5cm\" margin-bottom=\"0.5cm\" margin-left=\"0.5cm\" margin-right=\"0.5cm\">\n" +
                "<fo:region-body />\n" +
                "<fo:region-before />\n" +
                "<fo:region-after />\n" +
                "</fo:simple-page-master>\n" +
                "</fo:layout-master-set>";
    }

    private String zwróćBilety(){

        Iterator biletyIterator = model.bilety.iterator();
        Iterator miejscaIterator = model.miejsca.iterator();
        StringBuilder sklejanyString = new StringBuilder();

        while(biletyIterator.hasNext() && miejscaIterator.hasNext()) {
            Bilety b = (Bilety) biletyIterator.next();
            Miejsca m = (Miejsca) miejscaIterator.next();

            sklejanyString.append("<fo:page-sequence master-reference=\"first\">\n" +
                    "<fo:flow flow-name=\"xsl-region-body\">\n" +
                    "\n" +
                    "<fo:table table-layout=\"fixed\" width=\"100%\">\n" +
                    "<fo:table-column column-width=\"5.5cm\" />\n" +
                    "<fo:table-column column-width=\"6.5cm\" />\n" +
                    "<fo:table-body>");

            sklejanyString.append("<fo:table-row>\n" +
                    "\t\t<fo:table-cell number-columns-spanned=\"2\" padding-bottom=\"10pt\">\n" +
                    "\t\t\t<fo:block font-family=\"Calibri\" font-size=\"12pt\" space-after=\"10pt\"> <fo:inline font-weight=\"bold\">FILM:</fo:inline> "  + model.film.getTytul() +" </fo:block>" +
                    "\t\t</fo:table-cell>\n" +
                    "\t</fo:table-row>");

            sklejanyString.append("<fo:table-row>\n" +
                    "\t\t<fo:table-cell padding-bottom=\"10pt\">\n" +
                    "\t\t\t<fo:block font-family=\"Calibri\" font-size=\"12pt\">\n" +
                    "\t\t\t\t<fo:inline font-weight=\"bold\">DATA:</fo:inline> "  + dateFormat.format(model.seans.getDataSeansu()) + "\n" +
                    "\t\t\t</fo:block>\n" +
                    "\t\t</fo:table-cell>\n" +
                    "\t\t<fo:table-cell padding-bottom=\"10pt\">\n" +
                    "\t\t\t<fo:block font-family=\"Calibri\" font-size=\"12pt\">\n" +
                    "\t\t\t\t<fo:inline font-weight=\"bold\">SALA:</fo:inline> " + model.seans.getIdSali() + "\n" +
                    "\t\t\t</fo:block>\n" +
                    "\t\t</fo:table-cell>\n" +
                    "\t</fo:table-row>");

            sklejanyString.append("<fo:table-row>\n" +
                    "\t\t<fo:table-cell padding-bottom=\"10pt\">\n" +
                    "\t\t\t<fo:block font-family=\"Calibri\" font-size=\"12pt\">\n" +
                    "\t\t\t\t<fo:inline font-weight=\"bold\">RZĄD:</fo:inline> "+ m.getRzad() +"\n" +
                    "\t\t\t</fo:block>\n" +
                    "\t\t</fo:table-cell>\n" +
                    "\t\t<fo:table-cell padding-bottom=\"10pt\">\n" +
                    "\t\t\t<fo:block font-family=\"Calibri\" font-size=\"12pt\">\n" +
                    "\t\t\t\t<fo:inline font-weight=\"bold\">MIEJSCE:</fo:inline> " + m.getNrMiejsca() +"\n" +
                    "\t\t\t</fo:block>\n" +
                    "\t\t</fo:table-cell>\n" +
                    "\t</fo:table-row>\n");

            sklejanyString.append("<fo:table-row>\n" +
                    "\t\t<fo:table-cell padding-bottom=\"10pt\">\n" +
                    "\t\t\t<fo:block font-family=\"Calibri\" font-size=\"12pt\"> <fo:inline font-weight=\"bold\">NR BILETU:</fo:inline>  #" + b.getIdBiletu() +"</fo:block>\n" +
                    "\t\t</fo:table-cell>\n" +
                    "\t\t<fo:table-cell padding-bottom=\"10pt\">\n" +
                    "\t\t\t<fo:block font-family=\"Calibri\" font-size=\"12pt\"> <fo:inline font-weight=\"bold\">NR TRANSAKCJI:</fo:inline>  #"+ model.transakcja.getIdTransakcji()+"</fo:block>\n" +
                    "\t\t</fo:table-cell>\n" +
                    "\t</fo:table-row>");

            sklejanyString.append("</fo:table-body>\n" +
                    "</fo:table>\n" +
                    "\n" +
                    "\n" +
                    "</fo:flow>\n" +
                    "</fo:page-sequence>");

        }

        return sklejanyString.toString();
    }

}
