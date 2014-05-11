package li3;  
  
import java.io.*;
import java.util.Scanner;
import static java.lang.System.out;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RedeAutores implements Serializable 
{
    private String nomeficheiro;
    private int nomesdistintos;
    private int totalartigos;
    private int anomin;
    private int anomax;
    private int nomeslidos;
    private TreeMap<Integer,Rede_Autor> rede_autores;
    private int n_artigos_de_um_autor;
    private int n_autores_solo;
    private int n_autores_nao_solo;
    private int n_autores;
   
   public RedeAutores()
   {
       this.nomeficheiro = new String();
       this.nomesdistintos = 0;
       this.totalartigos = 0;
       this.anomin = Integer.MAX_VALUE;
       this.anomax = Integer.MIN_VALUE;
       this.nomeslidos = 0;
       this.rede_autores = new TreeMap<Integer,Rede_Autor>();
       this.n_artigos_de_um_autor = 0;
       this.n_autores_solo = 0;
       this.n_autores_nao_solo = 0;
       this.n_autores = 0;
       
   }
   
   public RedeAutores(RedeAutores ra)
   {
       this.nomeficheiro = ra.getNomeFicheiro();
       this.nomesdistintos = ra.getNomesDistintos();
       this.totalartigos = ra.getTotalArtigos();
       this.anomin = ra.getAnoMin();
       this.anomax = ra.getAnoMax();
       this.nomeslidos = ra.getNomesLidos();
       this.rede_autores = ra.getRedeAutores();
       this.n_artigos_de_um_autor = ra.getNArtigosDeUmAutor();
       this.n_autores_solo = ra.getNAutoresSolo();
       this.n_autores_nao_solo = ra.getNAutoresNaoSolo();
       this.n_autores = ra.getNAutores();
   }
   
   public RedeAutores(String nomeficheiro, int nomedistintos, int totalartigos, int anomin, int anomax, TreeMap<Integer,Rede_Autor> rede)
   {
       this.rede_autores = new TreeMap<Integer,Rede_Autor>();
       
       this.nomeficheiro = nomeficheiro;
       this.nomesdistintos = nomedistintos;
       this.totalartigos = totalartigos;
       this.anomin = anomin;
       this.anomax = anomax;
       for(Integer i : rede.keySet())
       {
           this.rede_autores.put(i,rede.get(i).clone());
       }
   }
   
   //Metodos Get
   
   public String getNomeFicheiro(){ return this.nomeficheiro;}
   public int getNomesDistintos(){return this.nomesdistintos;}
   public int getTotalArtigos(){return this.totalartigos;}
   public int getAnoMin(){return this.anomin;}
   public int getAnoMax(){return this.anomax;}
   public int getNomesLidos(){return this.nomeslidos;}
   public TreeMap<Integer,Rede_Autor> getRedeAutores(){
        TreeMap<Integer,Rede_Autor> novo = new TreeMap<Integer,Rede_Autor>();
        for (Integer i : this.rede_autores.keySet()){
            novo.put(i,this.rede_autores.get(i).clone());       
        }
        return novo;
   }
   public int getNArtigosDeUmAutor(){return this.n_artigos_de_um_autor;}
   public int getNAutoresSolo(){return this.n_autores_solo;}
   public int getNAutoresNaoSolo(){return this.n_autores_nao_solo;}
   public int getNAutores(){return this.n_autores;}
   
   //Metodos Set
   public void setNomeFicheiro(String nomeficheiro){this.nomeficheiro = nomeficheiro;}
   public void setNomesDistintos(int nomesdistintos){this.nomesdistintos = nomesdistintos;}
   public void setTotalArtigos(int totalartigos){this.totalartigos = totalartigos;}
   public void setAnoMin(int anomin){this.anomin = anomin;}
   public void setAnoMax(int anomax){this.anomax = anomax;}
   public void setNomesLidos(int nomeslidos){this.nomeslidos = nomeslidos;}

   
   

   public void leFicheiro(String fichName) {
       Scanner fichScan;
       int ano;
       String entrada;
       HashSet<String> aux = new HashSet<String>();
       Rede_Autor re = new Rede_Autor();
       
       try {
            fichScan = new Scanner(new FileReader(fichName));
            this.setNomeFicheiro(fichName);
            fichScan.useDelimiter(System.getProperty("line.separator"));
            while (fichScan.hasNext()) 
            {
                entrada = fichScan.next();
                this.nomeslidos += contaAutores(entrada);
                this.totalartigos++;
                ano = daAno(entrada);
                
                if( ano < this.getAnoMin())
                    this.setAnoMin(ano);
                else if(ano > this.getAnoMax())
                        this.setAnoMax(ano);
               
                
                preencheRedeAutores(entrada,re,aux);
            }
            this.nomesdistintos = aux.size();
            fichScan.close();
       } 
       catch(IOException e) {out.println(e.getMessage());  }
       catch(Exception e) {out.println(e.getMessage());  }
   }
   
   private int contaAutores(String entrada)
   {
        int res = 0;
        for(int i = 0; i < entrada.length(); i++){
            if(entrada.charAt(i) == ',')
                res++;
        }
        return res;
   }
   
   private int daAno(String entrada)
   {
        Scanner in = new Scanner(entrada).useDelimiter("[^0-9]+");
        int integer = in.nextInt();
        return integer;
   }
   
   private boolean isNumeric(String str)
   {
        NumberFormat formatter = NumberFormat.getInstance();
        ParsePosition pos = new ParsePosition(0);
        formatter.parse(str, pos);
        return str.length() == pos.getIndex();
   }
   
   
   
 public void preencheRedeAutores(String str, Rede_Autor rede_aux, HashSet<String> nomesdistintos){
        String token;
        Scanner s = new Scanner(str);
        s.useDelimiter(",|\\n");
        int ano = 0;
        ArrayList<String> list = new ArrayList<String>();        
        
        while(s.hasNext()){
            token = s.next().trim();
            if(isNumeric(token)){
                ano = Integer.valueOf(token);
            }
            if (!isNumeric(token)){
                nomesdistintos.add(token);
                list.add(token);
            }
        }
        if (list.size() == 1)
            this.n_artigos_de_um_autor ++;
        
        if (!this.rede_autores.containsKey(ano)){
            Rede_Autor nova = new Rede_Autor();
            nova.criaRedeAutor(list);
            nova.incNumeroArtigos();
            this.rede_autores.put(ano,nova);
        }
        else{
            this.rede_autores.get(ano).incNumeroArtigos();
            this.rede_autores.get(ano).criaRedeAutor(list);
        }
      
   }
   
   
   
public void Querie11()
   {
       System.out.println("\n:::::::::Querie 1.1::::::::::");
       System.out.println("-----------------------------");
       System.out.println("Nome do ficheiro: "+this.getNomeFicheiro());
       System.out.println("Total de artigos: "+this.getTotalArtigos());
       System.out.println("Nomes lidos: "+this.getNomesLidos());
       System.out.println("Nomes distintos: "+this.getNomesDistintos());
       System.out.println("Anos=["+this.getAnoMin()+" a "+this.getAnoMax()+"]");
           
   }
   
public void Querie12(){
       TreeSet<String> n_autores = new TreeSet<String>();
       TreeSet<String> nao_solo = new TreeSet<String>();
       TreeSet<String> solo = new TreeSet<String>();
       TreeSet<String> n_autores_solo = new TreeSet<String>();
       TreeSet<String> n_autores_nao_solo = new TreeSet<String>();
       String autor = new String();
       for(Integer i : this.rede_autores.keySet()){
           for(String s : this.rede_autores.get(i).getRedeAutor().keySet())
               n_autores.add(s);
           for (Autor a : this.rede_autores.get(i).getRedeAutor().values()){
                if(!a.getCoAutores().isEmpty())
                    nao_solo.add(a.getNome());
                if(a.getSolo() == 1)
                   solo.add(a.getNome());
           }
       }
       for(Integer ii : this.rede_autores.keySet()){
           for(Autor a : this.rede_autores.get(ii).getRedeAutor().values()){
               if(a.getCoAutores().isEmpty()){
                   if(!nao_solo.contains(a.getNome()))
                       n_autores_solo.add(a.getNome());
               }
               if(!a.getCoAutores().isEmpty()){
                   Iterator<String> it = a.getCoAutores().iterator();
                   while(it.hasNext()){
                       autor = it.next();
                        if(!solo.contains(autor))
                            n_autores_nao_solo.add(autor);
                   }
               }       
           }
       }
       this.n_autores_solo = n_autores_solo.size();
       this.n_autores_nao_solo = n_autores_nao_solo.size();
       this.n_autores = n_autores.size();
   }
   
   public void imprimeQuerie12(){
       StringBuilder s = new StringBuilder();
       s.append("\n:::::::::Querie 1.2::::::::::\n");
       s.append("-----------------------------\n");
       s.append("Número Total de Autores: ").append(this.getNAutores()).append("\n");
       s.append("Número de Artigos de um único Autor: ").append(this.getNArtigosDeUmAutor()).append("\n");
       s.append("Número de Autores que apenas publicaram a solo: ").append(this.getNAutoresSolo()).append("\n");
       s.append("Número de Autores que nunca publicaram a solo: ").append(this.getNAutoresNaoSolo()).append("\n");
       System.out.println(s.toString());
   }
   
   public void Querie13()
   {
       System.out.println("\n:::::::::Querie 1.3::::::::::");
       System.out.println("-----------------------------");
       for(Integer i : this.rede_autores.keySet())
       {
           System.out.println("Ano: "+i+" | Publicações: "+this.rede_autores.get(i).getNumeroArtigos());
       }
           
   }
   
   public void Querie21a(int min, int max, int top) throws IntervaloErradoException
   {
       TreeMap<String,Autor> rede_autor = new TreeMap<String,Autor>();
       TreeMap<String,Integer> tabela = new TreeMap<String,Integer>();
       
       if((min <= max) && (this.anomin <= min) && (this.anomax >= min) && (this.anomin <= max) && (this.anomax >= max))
       {
           while(min <= max)
           {
               if(this.rede_autores.containsKey(min)){
                   rede_autor = this.rede_autores.get(min).getRedeAutor();
                   trataRede_Autor1(tabela, rede_autor);
               }
               min++;
            }
       }
       else
       {
           throw new IntervaloErradoException("\nErro! Intervalo de anos incorrecto!");
       }
       
       imprimeQuerie21a(tabela,top);
   }
   
   private void trataRede_Autor1(TreeMap<String,Integer> tabela, TreeMap<String,Autor> rede_autor)
   {
       for(String nomeautor : rede_autor.keySet())
       {
           if(tabela.containsKey(nomeautor))
               tabela.put(nomeautor,tabela.get(nomeautor) + rede_autor.get(nomeautor).getTotalArtigos());
           else{
               if(!tabela.containsKey(nomeautor))
                   tabela.put(nomeautor,rede_autor.get(nomeautor).getTotalArtigos());
           }
       }
   }
   
   private void imprimeQuerie21a(TreeMap<String,Integer> tabela, int top)
   {
        int i=1;
        System.out.println("\n::::::::::::::Querie 2.1 a):::::::::::::::");
        System.out.println("-----------------------------------------");
        Set<Map.Entry<String,Integer>> lista1 = tabela.entrySet();
        List<Map.Entry<String,Integer>> lista = new ArrayList<Map.Entry<String,Integer>>();
	lista.addAll(lista1);
	Collections.sort(lista,new CompareArtigos());
        for(Map.Entry<String,Integer> entry  : lista)
        {
            if(i<=top){
                System.out.println(i+"º - Autor: "+entry.getKey()+" | Publicações: "+entry.getValue());
		i++;
            }
        }
   }
   
   public void Querie21d(int min, int max) throws IntervaloErradoException
   {
       int dif = ((max - min)+1);
       TreeMap<String,Autor> rede_autor = new TreeMap<String,Autor>();
       TreeMap<String,Integer> tabela = new TreeMap<String,Integer>(); //o integer é para guardar o total de aparencias em cada ano
       
       if((min < max) && (this.anomin <= min) && (this.anomax >= min) && (this.anomin <= max) && (this.anomax >= max))
       {
           while(min <= max)
           {
               if(this.rede_autores.containsKey(min)){
                   rede_autor = this.rede_autores.get(min).getRedeAutor();
                   trataRede_Autor2(tabela, rede_autor);
               }
               min++;
           }
       }
       else
       {
           throw new IntervaloErradoException("\nErro! Intervalo de anos incorrecto!");
       }
       
       imprimeQuerie21d(tabela, dif); 
   }
   
   private void trataRede_Autor2(TreeMap<String,Integer> tabela, TreeMap<String,Autor> rede_autor)
   {
       for(String nomeautor : rede_autor.keySet())
       {
           
           if(tabela.containsKey(nomeautor)){
               tabela.put(nomeautor,tabela.get(nomeautor)+1);
           }
           else{
               if(!tabela.containsKey(nomeautor))                  
                   tabela.put(nomeautor,1);
           }
       }
   }
   
   private void imprimeQuerie21d(TreeMap<String,Integer> tabela, int dif)
   {
       System.out.println("\n::::::::::Querie 2.1 d):::::::::::");
       System.out.println("----------------------------------");
       for(String nome : tabela.keySet())
       {
           if(dif == tabela.get(nome))
               System.out.println(nome);
       }
   }
   
    public void Querie21c(TreeSet<String> lista, int min, int max) throws IntervaloErradoException{
      String nome = new String();
      String nome2 = new String();
      String nome3 = new String();
      TreeSet<String> co_autores_comuns = new TreeSet<String>();
      TreeMap<String,TreeSet<String>> autor_coautores = new TreeMap<String,TreeSet<String>>(); 
      TreeSet<String> aux = new TreeSet<String>();
      TreeSet<String> aux2 = new TreeSet<String>();
      
      if((min < max) && (this.anomin <= min) && (this.anomax >= min) && (this.anomin <= max) && (this.anomax >= max))
      {
            while(min <= max){
                if(this.rede_autores.containsKey(min)){
                    Iterator<String> it = lista.iterator();
                    while(it.hasNext()){
                        nome = it.next();
                        if(this.rede_autores.get(min).getRedeAutor().containsKey(nome)){
                            Iterator<String> it2 = this.rede_autores.get(min).getRedeAutor().get(nome).getCoAutores().iterator();
                            if(autor_coautores.containsKey(nome)){
                                while(it2.hasNext()){
                                    nome2=it2.next();
                                    autor_coautores.get(nome).add(nome2);
                                }
                            }
                            else{
                                aux = new TreeSet<String>();
                                while(it2.hasNext()){
                                    nome2 = it2.next();
                                    aux.add(nome2);
                                }
                                autor_coautores.put(nome,aux);
                            }
                        }     
                    }
                }
                min++;
            }
            
            int contem = 1;
      
            for(TreeSet<String> auts : autor_coautores.values()){
                for (String s : auts){
                    aux2.add(s);
                }
            }     
            contem = 1;
            Iterator<String> it4 = aux2.iterator();
            while(it4.hasNext()){
                nome3 = it4.next();
                for(String s : autor_coautores.keySet()){
                    if(!autor_coautores.get(s).contains(nome3))
                         contem = 0;
                }
                if(contem == 1)
                    co_autores_comuns.add(nome3);
                
                contem = 1;
            }   
      }
      else
      {
           throw new IntervaloErradoException("\nErro! Intervalo de anos incorrecto!");
      }
      
       imprimeQuerie21c(co_autores_comuns);
   }


   

    private void imprimeQuerie21c(TreeSet<String> res){
        StringBuilder s = new StringBuilder();
        System.out.println("\n::::::Querie 2.1 c):::::::");
        System.out.println("--------------------------");
        for (String ss : res){
            s.append(ss).append("\n");
        }
        System.out.println(s.toString());
    }
   
   public String imprimeRedeAutores(){
        StringBuilder s = new StringBuilder();
        s.append("Rede-Autores\n\n");
        for (Integer i : this.rede_autores.keySet()){
            s.append("Ano: ").append(i).append("\n");
            s.append(this.rede_autores.get(i).toString());
        }
        return s.toString();
   }
   
   public void Querie22b(int x)
   {
       TreeMap<String,HashSet<String>> global = new TreeMap<String,HashSet<String>>();
       System.out.println("\n::::::::Querie 2.2 b):::::::::");
       System.out.println("------------------------------");
       for (Integer i : this.rede_autores.keySet()){
            this.rede_autores.get(i).mostraRede_Autor(x,global);
       }
       imprimeQuerie22b(global,x);
   }
   
   private void imprimeQuerie22b(TreeMap<String,HashSet<String>> global, int x)
   {
       for(String s : global.keySet()){
           if(global.get(s).size() < x){
                System.out.println("******************************");
                System.out.println("Nome Autor: "+s);
                System.out.println("CoAutores:");
                for (String ss : global.get(s))
                    System.out.println("\tNome: "+ss);
           }
       }
   }
   
   public void Querie22a(String ficheiro)
   {
       int dif;
       Scanner fichScan;
       String linha;
       ArrayList<String> lista = new ArrayList<String>();
       HashSet<String> hset = new HashSet<String>();
       
       try {
            fichScan = new Scanner(new FileReader(ficheiro));
            fichScan.useDelimiter(System.getProperty("line.separator"));
            while (fichScan.hasNext()) 
            {
                linha = fichScan.next();
                lista.add(linha);
                hset.add(linha);
            }
            fichScan.close();
       } 
       catch(IOException e) {out.println(e.getMessage());  }
       catch(Exception e) {out.println(e.getMessage());  }
       
       dif = ((lista.size())-(hset.size()));
       System.out.println("\nExistem "+dif+" linhas em duplicado.");
   }
   
   public void Querie21b(int min, int max, int top) throws IntervaloErradoException
   {
        int ocorrencias;
        TreeMap<String,Autor> redeautor = new TreeMap<String,Autor>();
        Map<Map.Entry<String, String>, Integer> relacoes = new  HashMap<Map.Entry<String, String>, Integer>();
        HashSet<String> coautores = new HashSet<String>();
        
        if((min <= max) && (this.anomin <= min) && (this.anomax >= min) && (this.anomin <= max) && (this.anomax >= max))
       {
           while(min <= max)
           {
                if(this.rede_autores.containsKey(min))
                {
                    redeautor = this.rede_autores.get(min).getRedeAutor();
                    for(String s : redeautor.keySet())
                    {
                        coautores = redeautor.get(s).getCoAutores();
                        for(String ss : coautores)
                        {
                            Map.Entry<String, String> par1=new AbstractMap.SimpleEntry<>(s,ss);
                            Map.Entry<String, String> par2=new AbstractMap.SimpleEntry<>(ss,s);
                            if( (!relacoes.containsKey(par1)) && (!relacoes.containsKey(par2)) )
                                    relacoes.put(par1, 1);
                            else{
                                if( (relacoes.containsKey(par1)) && (!relacoes.containsKey(par2)) ){
                                    ocorrencias = relacoes.get(par1);
                                    relacoes.put(par1,(ocorrencias+1));
                                }
                            }
                        }
                    }
                }
                min++;
           }
        }
        else
       {
           throw new IntervaloErradoException("\nErro! Intervalo de anos incorrecto!");
       }
        imprimeQuerie21b(relacoes, top);
   }
   
   private void imprimeQuerie21b(Map<Map.Entry<String, String>, Integer> relacoes, int top)
   {
       int i=1;
        System.out.println("\n::::::::::::::Querie 2.1 b):::::::::::::::");
        System.out.println("-----------------------------------------");
        Set<Map.Entry<Map.Entry<String, String>, Integer>> lista1 = relacoes.entrySet();
        List<Map.Entry<Map.Entry<String, String>, Integer>> lista = new ArrayList<Map.Entry<Map.Entry<String, String>, Integer>>();
	lista.addAll(lista1);
	Collections.sort(lista,new CompareDecr());
        for(Map.Entry<Map.Entry<String, String>, Integer> entry  : lista)
        {
            if(i<=top){
                System.out.println(i+"º - Autores: "+entry.getKey().getKey()+" & "+entry.getKey().getValue()+" | Publicações: "+entry.getValue());
		i++;
            }
        }
   }
   
   public void guardaRedeAutoresObj(String fich) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fich))) {
            oos.writeObject(this);
            oos.flush();
            oos.close();
        }
    }
   
   public RedeAutores carregaRedeAutoresObj(String fich) throws IOException {
       RedeAutores aux = new RedeAutores(); 
       try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fich))) {
           try {
               aux = (RedeAutores) ois.readObject();
               ois.close();
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(RedeAutores.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
       return aux;
    }
   
   @Override
   public RedeAutores clone()
   {
       return new RedeAutores(this);
   }
}



