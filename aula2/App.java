import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {
        //System.out.println( "Olá Mundo");

        // conexão HTTP (protocolo web) com o IMDB e buscar os top 250 filmes
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build(); //pegando os dados da API
        HttpResponse<String> response= client.send(request, BodyHandlers.ofString());
        String body = response.body();
        //System.out.println(body);
        

        // pegar só os dados que interessam (Título, poster, nota)
        var parser = new JsonParser();
        List<Map<String, String>> listadeFilmes = parser.parse(body);
        System.out.println(listadeFilmes.size());
        
        //diretorio
        var diretorio = new File("figurinhas/");
        diretorio.mkdir();
        
        //exibir e manipular os dados
        var gerador = new Geradordefigurinhas();
        for (int index = 0; index < 5; index++) {
            var filme = listadeFilmes.get(index);
            
            String urlImagem = filme.get("image");
            String titulo = filme.get("title");

            InputStream inputStream = new URL(urlImagem).openStream();
            String nomeArquivo = "figurinhas/" + titulo + ".png";

            gerador.criar(inputStream, nomeArquivo);
            
            
            System.out.println(" \u001b[1mTítulo: \u001b[m" + filme.get("title"));
            System.out.println( " \u001b[1mURL da imagem: \u001b[m" + filme.get("image"));
            System.out.println(filme.get("imDbRating"));
            double classificacao = Double.parseDouble(filme.get("imDbRating"));
            int numerocara = (int) classificacao;
            for (int n = 1; n <= numerocara; n++) {
                System.out.print("💙");
            }
            System.out.println("\n");
        }

    }
    
}
