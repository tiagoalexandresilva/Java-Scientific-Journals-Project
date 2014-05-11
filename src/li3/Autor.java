package li3;

import java.io.Serializable;
import java.util.HashSet;
import java.util.TreeMap;


public class Autor implements Serializable
{
    private String nome; //Nome do autor
    private HashSet<String> coautores; //Conjunto com os coautores
    private int total_artigos;  // totalArtigos do Autor
    private int solo;
    
    //Construtores
    
    //Vazio
    public Autor (){
        this.nome = "";
        this.coautores = new HashSet<String>();
        this.total_artigos = 0;
        this.solo = 0;
    }
    
    //Parameterizado
    public Autor (String n, HashSet<String> ca, int ta, int ss){
        this.nome = n;
        this.coautores = new HashSet<String>();
        for (String s : ca)
            this.coautores.add(s);
        this.total_artigos = ta;
        this.solo = ss;
    }
    
    //Copia
    public Autor (Autor ra){
        this.nome = ra.getNome();
        this.coautores = ra.getCoAutores();
        this.total_artigos = ra.getTotalArtigos();
        this.solo = ra.getSolo();
    }
    
    //Metodos Get
    public String getNome(){return this.nome;}
    public HashSet<String> getCoAutores(){
        HashSet<String> novo = new HashSet<String>();
        for (String s : this.coautores)
            novo.add(s);
        return novo;
    }
    public int getTotalArtigos(){return this.total_artigos;}
    public int getSolo(){return this.solo;};
    
    //Metodos Set
    public void setNome(String n){this.nome = n;}
    public void setCoAutores(HashSet<String> ca){
        this.coautores = new HashSet<String>();
        for(String s : ca)
            this.coautores.add(s);
    }
    public void setTotalArtigos(int ta){this.total_artigos = ta;}
    public void setSolo(int s){this.solo = s;}
    //Metodos de Instancia
    
    //Adiciona CoAutor
    public void adicionaCoAutor(String s){
        this.coautores.add(s);
    }
    
    //Incrementa total Artigos
    public void incTotalArtigos(){
        this.total_artigos ++;
    }
    
    //Cria autor
    public void criaAutor(String s){
        if (!s.equals(this.nome))
            this.coautores.add(s);
    }
    
    
    //Clone
    public Autor clone(){return new Autor(this);}
    
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("Nome Autor: "+this.getNome()+"\n");
        s.append("Total Artigos: "+this.getTotalArtigos()+"\n");
        s.append("CoAutores\n");
        for (String ss : this.getCoAutores())
            s.append("\tNome: "+ss+"\n");
        return s.toString();
    }
    
    public void mostraCoautores(int x, TreeMap<String,HashSet<String>> global){
        HashSet<String> coautores = new HashSet<String>();
        
        if(this.coautores.size() < x){
            if(global.containsKey(this.getNome())){
                for (String ss : this.getCoAutores())
                    global.get(this.getNome()).add(ss);
            }
            else{
                if(!global.containsKey(this.getNome())){
                    for (String ss : this.getCoAutores())
                        coautores.add(ss);
                    
                    global.put(this.getNome(), coautores);
                }
            }
        }
    }
    
    
}
