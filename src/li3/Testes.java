package li3; 

import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Testes implements Serializable
{
    private static RedeAutores utrede = new RedeAutores();
    private static Menu menuPrincipal, menuEstatisticas, menuIndexadas, menuGlobais, menuQuerie21c;
    private static Scanner ler= new Scanner(System.in);
    
    public static void main(String args[])
    {
        //long inicio = System.nanoTime();
        utrede.leFicheiro("publicx.txt");
        //long fim = System.nanoTime();
        //System.out.println("\n\nTempo: " + (fim - inicio)/1.0E09 + " segs.\n");
       
        
        
        
        carregaMenu();
        mmenuPrincipal(utrede);
        System.out.println("\n!!!!!PROGRAMA TERMINADO!!!!!");
       
    }
    
    public static void carregaMenu() {
            String[] smenuPrincipal={"Consultas estatísticas","Consultas indexadas por ano(s)","Consultas globais especiais","Gravar estado actual","Carregar um novo ficheiro .txt","Carregar um ficheiro .obj"};
            String[] smenuEstatisticas={"Querie 1.1","Querie 1.2","Querie 1.3","Descrições das Queries"};
            String[] smenuIndexadas={"Querie 2.1 a)","Querie 2.1 b)","Querie 2.1 c)","Querie 2.1 d)","Descrições das Queries"};
            String[] smenuGlobais={"Querie 2.2 a)","Querie 2.2 b)","Descrições das Queries"};
            String[] smenuQuerie21c={"Inserir um nome na lista","Limpar Lista","Executar Querie 2.1 c)"};
                    
            menuPrincipal= new Menu(smenuPrincipal);
            menuEstatisticas= new Menu(smenuEstatisticas);
            menuIndexadas= new Menu(smenuIndexadas);
            menuGlobais= new Menu(smenuGlobais);
            menuQuerie21c= new Menu(smenuQuerie21c);
   }
    
    public static void mmenuPrincipal(RedeAutores ra)
    {    
        String fich;
        String nome;
        do{
            System.out.println("\n<<<<<<<Menu Principal>>>>>>>");
            System.out.println("----------------------------");
            menuPrincipal.executa();
            switch(menuPrincipal.getOpcao()){
                case 1:
                    mmenuEstatisticas(ra);
                    break;
                case 2:
                    mmenuIndexadas(ra);
                    break;
                case 3:
                    mmenuGlobais(ra);
                    break;
                case 4:
                    try {
                        nome = ra.getNomeFicheiro();
                        nome = nome.replaceAll(".txt", "");
                        ra.guardaRedeAutoresObj(nome);
                        System.out.println("\nEstado actual guardado com sucesso!");
                    } catch (IOException ex) {
                         Logger.getLogger(Testes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                 case 5:
                    RedeAutores aux = new RedeAutores();
                    ra = aux;
                    System.out.print("\nNome do ficheiro: ");
                    fich = ler.nextLine();
                    ra.leFicheiro(fich);
                    break;
                 case 6:
                    RedeAutores aux2 = new RedeAutores();
                    ra = aux2;
                    System.out.print("\nNome do ficheiro: ");
                    fich = ler.nextLine();
                    try {
                        ra = ra.carregaRedeAutoresObj(fich);
                    } catch (IOException ex) {
                        Logger.getLogger(Testes.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                default:
                    break;
            }
        }while(menuPrincipal.getOpcao()!=0);
    }
    
    public static void mmenuEstatisticas(RedeAutores ra)
    {          
        do{
            System.out.println("\n<<<<<<<Consultas estatísticas>>>>>>");
            System.out.println("-----------------------------------");
            menuEstatisticas.executa();
            switch(menuEstatisticas.getOpcao()){
                case 1:
                    ra.Querie11();
                    break;
                case 2:
                    ra.Querie12();
                    ra.imprimeQuerie12();
                    break;
                case 3:
                    ra.Querie13();
                    break;
                case 4:
                    System.out.println("\n-Querie 1.1");
                    System.out.println("Apresenta ao utilizador os dados referentes ao último ficheiro de texto lido,\n" +
                    "designadamente, nome do ficheiro, número total de artigos, número total de nomes\n" +
                    "lidos, número total de nomes distintos e intervalo fechado dos anos dos artigos lidos\n" +
                    "(cf. Anos = [1971 a 2012]).");
                    System.out.println("\n-Querie 1.2");
                    System.out.println("Apresenta ao utilizador os números gerais respeitantes aos dados actuais na\n" +
                    "estrutura, designadamente: número total de autores, número total de artigos de um\n" +
                    "único autor, número total de autores que apenas publicaram a solo (sem coautores)\n" +
                    "e número total de autores que nunca publicaram a solo.");
                    System.out.println("\n-Querie 1.3");
                    System.out.println("Apresenta uma tabela com o número total de publicações registadas em cada\n" +
                    "ano a considerar, por ordem crescente do ano em questão.");
                    break;
                default:
                    break;
            }
        }while(menuEstatisticas.getOpcao()!=0);
    }
    
    public static void mmenuIndexadas(RedeAutores ra)
    {          
        int min, max, top;
        do{
            System.out.println("\n<<<<<<<Consultas indexadas>>>>>>");
            System.out.println("--------------------------------");
            menuIndexadas.executa();
            switch(menuIndexadas.getOpcao()){
                case 1:
                    System.out.print("\nTop que deseja visualizar: ");
                    top = ler.nextInt();
                    System.out.println("-Intervalo de anos-");
                    System.out.print("De: ");
                    min = ler.nextInt();
                    System.out.print("Até: ");
                    max = ler.nextInt();
                    try {      
                        ra.Querie21a(min, max, top);                                       
                    } catch (IntervaloErradoException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 2:
                    System.out.print("\nTop que deseja visualizar: ");
                    top = ler.nextInt();
                    System.out.println("-Intervalo de anos-");
                    System.out.print("De: ");
                    min = ler.nextInt();
                    System.out.print("Até: ");
                    max = ler.nextInt();
                    try {
                        ra.Querie21b(min, max, top);                      
                    } catch (IntervaloErradoException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 3:   
                    mmenuQuerie21c(ra);                        
                    break;
                case 4:
                    System.out.println("\n-Intervalo de anos-");
                    System.out.println("(não fazer para um único ano, mas apenas para intervalos)");
                    System.out.print("De: ");
                    min = ler.nextInt();
                    System.out.print("Até: ");
                    max = ler.nextInt();
                    try {                     
                        ra.Querie21d(min, max);                
                    } catch (IntervaloErradoException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("\nDado um intervalo fechado de anos (os limites podem ser iguais, cf. [1999,1999]).");
                    System.out.println("-Querie 2.1 a)");
                    System.out.println("O TOP X de número de publicações (ordem crescente dos nomes) sendo X\n" +
                    "dado pelo utilizador.");
                    System.out.println("\n-Querie 2.1 b)");
                    System.out.println("O TOP X de co-autorias, ou seja, os X pares de autores e respectivo número de\n" +
                    "artigos, com mais artigos publicados em co-autoria, sendo X dado pelo\n" +
                    "utilizador (ordem decrescente do número de artigos).");
                    System.out.println("\n-Querie 2.1 c)");
                    System.out.println("A listagem, por ordem alfabética crescente, de todos os co-autores comuns aos\n" +
                    "autores de uma dada lista de nomes a introduzir pelo utilizador.");
                    System.out.println("\n-Querie 2.1 d)");
                    System.out.println("A listagem dos nomes dos autores que publicaram artigos em todos os anos do\n" +
                    "intervalo de anos dado (não fazer para um único ano, mas apenas para\n" +
                    "intervalos).");
                    break;
                default:
                    break;
            }
        }while(menuIndexadas.getOpcao()!=0);
    }
    
    public static void mmenuGlobais(RedeAutores ra)
    {   
        int x;
        do{
            System.out.println("\n<<<<<<<Consultas globais>>>>>>");
            System.out.println("------------------------------");
            menuGlobais.executa();
            switch(menuGlobais.getOpcao()){
                case 1:
                    ra.Querie22a(ra.getNomeFicheiro());
                    break;
                case 2:
                    System.out.print("\nIntroduza um valor X: ");
                    x = ler.nextInt();
                    ra.Querie22b(x);
                    break;
                case 3:
                    System.out.println("\n-Querie 2.2 a)");
                    System.out.println("Contar o número de linhas em duplicado no ficheiro.");
                    System.out.println("\n-Querie 2.2 b)");
                    System.out.println("Tabela com todos os autores e respectivas redes de co-autores, mas apenas\n" +
                    "para associações em que o número de co-autores seja inferior a um número X dado.");
                    break;
                default:
                    break;
            }
        }while(menuGlobais.getOpcao()!=0);
    }
    
    public static void mmenuQuerie21c(RedeAutores ra)
    {   
        TreeSet<String> lista = new TreeSet<String>();
        String nome;
        int min, max;
        do{
            System.out.println("\n<<<<<<<<Querie 2.1 c)>>>>>>>");
            System.out.println("----------------------------");
            menuQuerie21c.executa();
            switch(menuQuerie21c.getOpcao()){
                case 1:
                    System.out.print("\nNome: ");
                    nome = ler.nextLine();
                    lista.add(nome);
                    break;
                case 2:
                    lista = new TreeSet<String>();
                    break;
                case 3:
                    System.out.println("\n-Intervalo de anos-");
                    System.out.print("De: ");
                    min = ler.nextInt();
                    System.out.print("Até: ");
                    max = ler.nextInt();
                    ler.nextLine();
                    try {
                        ra.Querie21c(lista, min, max);                
                    } catch (IntervaloErradoException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                default:
                    break;
            }
        }while(menuQuerie21c.getOpcao()!=0);
    }
}
