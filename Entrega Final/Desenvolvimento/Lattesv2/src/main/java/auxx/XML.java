/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package auxx;

import Model.Curriculo;
import Model.Usuario;
import DAO.CurriculoDAO;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author gusan
 */
public class XML {
    
    
    public static void exportarXml(Curriculo curriculoSelecionado, Usuario usuario) {
        try {
            // Criar a instância do DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            // Criar o documento XML
            Document doc = builder.newDocument();
            
            // Criar o elemento raiz
            Element rootElement = doc.createElement("curriculo");
            doc.appendChild(rootElement);

            // Criar elementos para os dados do currículo
            Element usuarioElement = doc.createElement("usuario");
            rootElement.appendChild(usuarioElement);
            
            // Adicionar CPF e Nome do usuário
            Element cpfUsuarioElement = doc.createElement("cpf");
            cpfUsuarioElement.appendChild(doc.createTextNode(usuario.getCpf()));
            usuarioElement.appendChild(cpfUsuarioElement);
            
            Element nomeUsuarioElement = doc.createElement("nome");
            nomeUsuarioElement.appendChild(doc.createTextNode(usuario.getNome()));
            usuarioElement.appendChild(nomeUsuarioElement);

            // Adicionar dados do currículo
            Element tituloElement = doc.createElement("titulo");
            tituloElement.appendChild(doc.createTextNode(curriculoSelecionado.getTitulo()));
            rootElement.appendChild(tituloElement);
            
            Element formacaoAcademicaElement = doc.createElement("formacaoAcademica");
            formacaoAcademicaElement.appendChild(doc.createTextNode(curriculoSelecionado.getFormacaoAcademica()));
            rootElement.appendChild(formacaoAcademicaElement);
            
            Element producaoAcademicaElement = doc.createElement("producaoAcademica");
            producaoAcademicaElement.appendChild(doc.createTextNode(curriculoSelecionado.getProducoesAcademicas()));
            rootElement.appendChild(producaoAcademicaElement);
            
            Element premiosElement = doc.createElement("premios");
            premiosElement.appendChild(doc.createTextNode(curriculoSelecionado.getPremios()));
            rootElement.appendChild(premiosElement);

            // Criar o arquivo XML
            File xmlFile = new File("curriculo_" + usuario.getCpf() + ".xml");
            FileWriter writer = new FileWriter(xmlFile);
            
            // Transformar o documento em uma string XML
            javax.xml.transform.Transformer transformer = javax.xml.transform.TransformerFactory.newInstance().newTransformer();
            javax.xml.transform.dom.DOMSource source = new javax.xml.transform.dom.DOMSource(doc);
            javax.xml.transform.stream.StreamResult result = new javax.xml.transform.stream.StreamResult(writer);
            
            // Escrever o conteúdo XML no arquivo
            transformer.transform(source, result);
            
            // Fechar o escritor
            writer.close();

            System.out.println("Arquivo XML exportado com sucesso!");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao exportar o XML: " + e.getMessage());
        }
    }

    public static String[] importarXml(File xmlFile, String usuarioCpf) throws Exception {
        // Inicializar o DocumentBuilder para processar o XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        // Obter o elemento raiz
        Element root = doc.getDocumentElement();

        // Inicializar as variáveis para armazenar as informações
        String dadosPessoais = "";
        String formacaoAcademica = "";
        String producoesAcademicas = "";
        String premios = "";
        String titulo = "";

        // Mapeamento do número identificador para o título
        String numeroIdentificador = root.getAttribute("NUMERO-IDENTIFICADOR");
        titulo = "Currículo " + numeroIdentificador;

        // Mapeamento dos dados gerais (dados pessoais)
        NodeList dadosGerais = root.getElementsByTagName("DADOS-GERAIS");
        if (dadosGerais.getLength() > 0) {
            Element dadosGeraisElement = (Element) dadosGerais.item(0);
            dadosPessoais = "Nome: " + dadosGeraisElement.getAttribute("NOME-COMPLETO") + "\n" +
                    "Nacionalidade: " + dadosGeraisElement.getAttribute("NACIONALIDADE") + "\n" +
                    "CPF: " + usuarioCpf + "\n" +
                    "Data de Nascimento: " + dadosGeraisElement.getAttribute("DATA-DE-NASCIMENTO") + "\n" +
                    "Sexo: " + dadosGeraisElement.getAttribute("SEXO") + "\n" +
                    "Endereço Residencial: " + dadosGeraisElement.getAttribute("ENDERECO-RESIDENCIAL") + "\n" +
                    "E-mail: " + dadosGeraisElement.getAttribute("EMAIL") + "\n" +
                    "Instituição: " + dadosGeraisElement.getAttribute("INSTITUICAO");
        }

        // Mapeamento da formação acadêmica
        NodeList formacaoAcademicaList = root.getElementsByTagName("FORMACAO-ACADEMICA-TITULACAO");
        if (formacaoAcademicaList.getLength() > 0) {
            Element formacaoElement = (Element) formacaoAcademicaList.item(0);
            NodeList graduacoes = formacaoElement.getElementsByTagName("GRADUACAO");
            StringBuilder formacaoStr = new StringBuilder();
            for (int i = 0; i < graduacoes.getLength(); i++) {
                Element graduacaoElement = (Element) graduacoes.item(i);
                formacaoStr.append(graduacaoElement.getAttribute("NOME-CURSO")).append(" (Iniciado em ").append(graduacaoElement.getAttribute("ANO-DE-INICIO")).append(", ").append(graduacaoElement.getAttribute("ANO-DE-CONCLUSAO")).append(graduacaoElement.getAttribute("INSTITUICAO")).append(")\n");
            }
            formacaoAcademica = formacaoStr.toString();
        }

        // Mapeamento das produções acadêmicas
        NodeList producoesAcademicasList = root.getElementsByTagName("PRODUCAO-TECNICA");
        if (producoesAcademicasList.getLength() > 0) {
            Element producaoElement = (Element) producoesAcademicasList.item(0);
            producoesAcademicas = "Participação em Olimpíadas:\n" + producaoElement.getTextContent().trim();
        }

        // Mapeamento dos prêmios
        NodeList premiosList = root.getElementsByTagName("PREMIOS-TITULOS");
        if (premiosList.getLength() > 0) {
            Element premiosElement = (Element) premiosList.item(0);
            NodeList premioList = premiosElement.getElementsByTagName("PREMIO-TITULO");
            StringBuilder premiosStr = new StringBuilder();
            for (int i = 0; i < premioList.getLength(); i++) {
                Element premioElement = (Element) premioList.item(i);
                premiosStr.append(premioElement.getAttribute("NOME-DO-PREMIO-OU-TITULO")).append(", ");
            }
            premios = premiosStr.toString();
        }

        // Retornar os dados na ordem solicitada
        return new String[]{usuarioCpf, titulo, dadosPessoais, formacaoAcademica, producoesAcademicas, premios};
    }

}
