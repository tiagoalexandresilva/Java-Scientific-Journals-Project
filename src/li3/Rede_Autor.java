package li3;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;

public class Rede_Autor implements Serializable 
{
    //Variáveis de instância
    
    private int numero_artigos; // Numero total de artigos num ano
    private TreeMap<String,Autor> redeautor; // Autores que publicaram nesse ano
    
    //Construtores
    
    //Vazio
    public Rede_Autor (){
        this.numero_artigos = 0;
        this.redeautor = new TreeMap<String,Autor>();
    }
    
    //Parameterizado
    public Rede_Autor (int na,TreeMap<String,Autor> autor_novo){
        this.numero_artigos = na;
        this.redeautor = new TreeMap<String,Autor>();
        for(Autor a : autor_novo.values()){
            this.redeautor.put(a.getNome(),a.clone());  
        }
    }
    
    //Copia
    public Rede_Autor (Rede_Autor ra){
       this.numero_artigos = ra.getNumeroArtigos();
       this.redeautor = ra.getRedeAutor();
    }
    
    //Metodos Get
    public int getNumeroArtigos(){return this.numero_artigos;}
    public TreeMap<String,Autor> getRedeAutor(){
        TreeMap<String,Autor> novo = new TreeMap<String,Autor>();
        for(Autor a : this.redeautor.values())
            novo.put(a.getNome(),a.clone());
        return novo;
    }
    
    //Metodos Set
    public void setNumeroArtigos(int na){this.numero_artigos = na;}
    public void setAutor(TreeMap<String,Autor> novo_autor){
        //this.autor = new TreeMap<String,Autor>();
        for(Autor a : novo_autor.values())
            this.redeautor.put(a.getNome(),a.clone());
    }
    
    //Metodos de Instancia
    
    //Adiciona Autor
    public void adicionaAutor(Autor a){
        this.redeautor.put(a.getNome(),a.clone());
    }
    
    //Incrementa Numero Artigos 
    public void incNumeroArtigos(){
        this.numero_artigos ++;
    }
    
    public void criaRedeAutor(ArrayList<String> artigo){
        Autor a = new Autor();
        Iterator<String> it = artigo.iterator();
        String nome;
        String nome2;
        while(it.hasNext()){
            nome = it.next();
            if(!this.redeautor.containsKey(nome)){
                    a = new Autor();
                    Iterator<String> it2 = artigo.iterator();
                    a.incTotalArtigos();
                    while(it2.hasNext()){
                        nome2 = it2.next();
                        if(!nome2.equals(nome))
                            a.adicionaCoAutor(nome2);
                    }
                    a.setNome(nome);
                    if(artigo.size() == 1){
                        a.setSolo(1);
                    }
                    this.redeautor.put(nome,a.clone());
                }
            else{
                if(this.redeautor.containsKey(nome)){
                    
                    Iterator<String> it3 = artigo.iterator();
                    this.redeautor.get(nome).incTotalArtigos();
                    while(it3.hasNext()){
                        nome2 = it3.next();
                        if(!nome2.equals(nome))
                            this.redeautor.get(nome).adicionaCoAutor(nome2);
                    }
                    if(artigo.size() == 1){
                        this.redeautor.get(nome).setSolo(1);
                    }
                }
            }
        }
        
    }
    
    //Clone
    @Override
    public Rede_Autor clone(){return new Rede_Autor(this);}
    
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("Total Artigos Ano: ").append(this.getNumeroArtigos()).append("\n");
        s.append("                Rede Autores\n");
        for(Autor a : this.getRedeAutor().values())
            s.append(a.toString());
        return s.toString();
    }
    
    public void mostraRede_Autor(int x, TreeMap<String,HashSet<String>> global)
    {
        for(Autor a : this.getRedeAutor().values())
            a.mostraCoautores(x,global);
    }
    
    
}
