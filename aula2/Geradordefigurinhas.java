import java.io.File;
import java.io.InputStream;


import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

public class Geradordefigurinhas {

    public void criar(InputStream inputStream, String nomeArquivo) throws Exception {

        // leitura da imagem
        // InputStream inputStream = new FileInputStream(new File("entrada/filme1.jpg"));
        //InputStream inputStream = new URL("https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies_1.jpg").openStream();
        BufferedImage ImagemOriginal = ImageIO.read(inputStream);

        // cria nova imagem em memória com transparência e redimensionada
        int largura = ImagemOriginal.getWidth();
        int altura = ImagemOriginal.getHeight();
        int novaaltura = altura + 200;
        BufferedImage novaImagem = new BufferedImage(largura, novaaltura, BufferedImage.TRANSLUCENT);

        // copiar a imagem original pra nova imagem (em memória)
        Graphics2D graphics = (Graphics2D) novaImagem.getGraphics();
        graphics.drawImage(ImagemOriginal, 0, 0, null);

        // configurar a fonte
        var fonte = new Font("Impact", Font.BOLD, 80);
        graphics.setColor(Color.RED);
        graphics.setFont(fonte);

        // escrever frase na nova imagem
        String texto = "BRABO";
        FontMetrics fontMetrics = graphics.getFontMetrics();
        Rectangle2D retangulo = fontMetrics.getStringBounds(texto, graphics);
        int larguraTexto = (int) retangulo.getWidth();
        int posicaoTextoX = (largura - larguraTexto)/2; 
        int posicaoTextoY = novaaltura - 100;
        graphics.drawString(texto, posicaoTextoX, posicaoTextoY);

        FontRenderContext render = graphics.getFontRenderContext();
        TextLayout textLayout = new TextLayout(texto, fonte, render);
        Shape outline = textLayout.getOutline(null);
        AffineTransform transform = graphics.getTransform();
        transform.translate(posicaoTextoX, posicaoTextoY);
        graphics.setTransform(transform);

        var outlineStroke = new BasicStroke(largura * 0.004f);
        graphics.setStroke(outlineStroke);

        graphics.setColor(Color.WHITE);
        graphics.draw(outline);


        // escrever a imagem em um arquivo
        ImageIO.write(novaImagem, "png", new File(nomeArquivo) );
    }


    }
    

